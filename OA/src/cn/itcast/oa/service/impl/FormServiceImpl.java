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
		
		//1���������Բ������
		form.setStatus(Form.STATUS_RUNNING);//����������
		form.setTitle(form.getFormTemplate().getName()//��ʽΪ��{ģ������}_{������}_{����ʱ��}
				+"_"+form.getApplicant().getName()//
				+"_"+sdf.format(form.getApplyTime())); 
		getSession().save(form);
		//2,��ʼ��ת
		//>>a,��������ʵ��
			String processDefinitionKey=form.getFormTemplate().getProcessDefinitionKey();
			Map<String, Object>variables=new HashMap<String,Object>();
			variables.put("form",form );
			ProcessInstance pi=processEngine.getExecutionService().startProcessInstanceByKey(processDefinitionKey, variables);
		//>>b,��ɵ�һ�������ύ���롱
			String taskId=processEngine.getTaskService()//
					.createTaskQuery()//
					.processInstanceId(pi.getId())//
					.uniqueResult()//
					.getId();
			processEngine.getTaskService().completeTask(taskId);
		
	}

	public List<TaskView> findMyTaskList(User currentUser) {
		// TODO Auto-generated method stub
		//��ȡ�����б�
		List<Task>tasks=processEngine.getTaskService().findPersonalTasks(currentUser.getLoginName());
		//��ȡ�뵱ǰ���������Ӧ�Ĵ������ı������ŵ�Ҫ���صĽ��������    
		List<TaskView>taskViews=new ArrayList<TaskView>();
		for(Task task:tasks){
			Form form=(Form) processEngine.getTaskService().getVariable(task.getId(), "form");
			taskViews.add(new TaskView(task, form));
		}
		return taskViews;
	}

	public void approve(ApproveInfo approveInfo, String taskId,String outcome) {
		// TODO Auto-generated method stub
		//1:����������Ϣ
		 getSession().save(approveInfo); 
		//2:�������
		Task task=processEngine.getTaskService().getTask(taskId);//��������������֮ǰ
		if(outcome==null){
			processEngine.getTaskService().completeTask(taskId);
		}else{
			processEngine.getTaskService().completeTask(taskId,outcome);
		}
		ProcessInstance pi=processEngine.getExecutionService().//�������ʵ���ѽ���������null
				         findProcessInstanceById(task.getExecutionId());
		//3:ά����״̬
		Form form =approveInfo.getForm();
		//>>a:������β�ͬ�⣬����ֱ�ӽ�������״̬Ϊ��δͨ��
		if(!approveInfo.isApproval()){
			if(pi!=null){
				processEngine.getExecutionService().endProcessInstance(pi.getId(), ProcessInstance.STATE_ENDED);
			}
			form.setStatus(Form.STATUS_REJECTED);
		}
		//>>b:�������ͬ���������һ����������ʾ���еĻ��ڶ�ͨ���ˣ���������������������״̬Ϊ����ͨ��
		else if(pi==null){	
           form.setStatus(Form.STATUS_APPROVED);
			
		}
		getSession().update(form);
		
	}

	public Set<String> getOutcomesByTaskId(String taskId) {
		// TODO Auto-generated method stub
		return processEngine.getTaskService().getOutcomes(taskId);//��ȡ��ǰ���������������ߵ�����;
	}
  
}
