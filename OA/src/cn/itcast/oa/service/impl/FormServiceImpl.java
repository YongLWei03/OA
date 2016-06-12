package cn.itcast.oa.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.map.HashedMap;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.ApproveInfo;
import cn.itcast.oa.domain.Form;
import cn.itcast.oa.domain.TaskView;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.service.FormService;

@Service("formService")
@Transactional
public class FormServiceImpl extends DaoSupportImpl<Form> implements FormService{
    @Resource
	private ProcessEngine processEngine;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	public void submit(Form form) {
		// TODO Auto-generated method stub
		
		//1，设置属性并保存表单
		form.setStatus(Form.STATUS_RUNNING);//正在审批中
		form.setTitle(form.getFormTemplate().getName()//格式为：{模板名称}_{申请人}_{申请时间}
				+"_"+form.getApplicant().getName()//
				+"_"+sdf.format(form.getApplyTime())); 
		getSession().save(form);
		//2,开始流转
		//>>a,启动流程实例
			String processDefinitionKey=form.getFormTemplate().getProcessDefinitionKey();
			Map<String, Object>variables=new HashMap<String,Object>();
			variables.put("form",form );
			ProcessInstance pi=processEngine.getExecutionService().startProcessInstanceByKey(processDefinitionKey, variables);
		//>>b,完成第一个任务“提交申请”
			String taskId=processEngine.getTaskService()//
					.createTaskQuery()//
					.processInstanceId(pi.getId())//
					.uniqueResult()//
					.getId();
			processEngine.getTaskService().completeTask(taskId);
		
	}

	public List<TaskView> findMyTaskList(User currentUser) {
		// TODO Auto-generated method stub
		//获取任务列表
		List<Task>tasks=processEngine.getTaskService().findPersonalTasks(currentUser.getLoginName());
		//获取与当前审批任务对应的待审批的表单，并放到要返回的结果集合中    
		List<TaskView>taskViews=new ArrayList<TaskView>();
		for(Task task:tasks){
			Form form=(Form) processEngine.getTaskService().getVariable(task.getId(), "form");
			taskViews.add(new TaskView(task, form));
		}
		return taskViews;
	}

	public void approve(ApproveInfo approveInfo, String taskId,String outcome) {
		// TODO Auto-generated method stub
		//1:保存审批信息
		 getSession().save(approveInfo); 
		//2:完成任务
		Task task=processEngine.getTaskService().getTask(taskId);//必须放在完成任务之前
		if(outcome==null){
			processEngine.getTaskService().completeTask(taskId);
		}else{
			processEngine.getTaskService().completeTask(taskId,outcome);
		}
		ProcessInstance pi=processEngine.getExecutionService().//如果流程实例已结束，返回null
				         findProcessInstanceById(task.getExecutionId());
		//3:维护表单状态
		Form form =approveInfo.getForm();
		//>>a:如果本次不同意，流程直接结束，表单状态为：未通过
		if(!approveInfo.isApproval()){
			if(pi!=null){
				processEngine.getExecutionService().endProcessInstance(pi.getId(), ProcessInstance.STATE_ENDED);
			}
			form.setStatus(Form.STATUS_REJECTED);
		}
		//>>b:如果本次同意且是最后一次审批，表示所有的环节都通过了，则流程正常结束，表单的状态为：已通过
		else if(pi==null){	
           form.setStatus(Form.STATUS_APPROVED);
			
		}
		getSession().update(form);
		
	}

	public Set<String> getOutcomesByTaskId(String taskId) {
		// TODO Auto-generated method stub
		return processEngine.getTaskService().getOutcomes(taskId);//获取当前任务所有流出的线的名称;
	}
  
}
