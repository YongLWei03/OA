package cn.itcast.oa.view.action;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.ApproveInfo;
import cn.itcast.oa.domain.Form;
import cn.itcast.oa.domain.FormTemplate;
import cn.itcast.oa.domain.TaskView;
import cn.itcast.oa.util.QueryHelper;

@Controller
@Scope("prototype")
public class FormFlowAction extends BaseAction<Object> {
	private Long formTemplateId;
	private File upload;

	private Long formId;
	private String taskId;

	private boolean approval;
	private String comment;
	private String outcome;

	private String status;

	// ==============================================================================

	/** 起草申请（表达模板列表） */
	public String formTemplateList() {
		List<FormTemplate> formTemplates = formTamplateService.findAll();
		ActionContext.getContext().put("formTemplates", formTemplates);
		return "formTemplateList";
	}

	/** 提交申请页面 */
	public String submitUI() {
		return "submitUI";
	}

	/** 提交申请 */
	public String submit() {
		// 生成一个Form对象，表示本次申请信息
		Form form = new Form();
		form.setPath(saveUploadFile(upload));
		form.setFormTemplate(formTamplateService.getById(formTemplateId));

		form.setApplicant(getCurrentUser());
		form.setApplyTime(new Date());

		// 保存表单，并开始流转
		formService.submit(form);

		return "toMyApplicationList";// 转到我的申请查询
	}

	/** 我的申请查询 */
	public String myApplicationList() {
		// 1,分页信息
		new QueryHelper(Form.class, "f")//
				.addCondition("f.applicant=?", getCurrentUser())//
				.addCondition(formTemplateId != null, "f.formTemplate.id=?",formTemplateId)//
				.addCondition(status!=null&&!status.equals(""), "f.status=?", status)//
				.addOrderByProperty("f.applyTime", false)//  
				.preparePageBean(formService, pageNum, pageSize);
		
		// 2,过滤条件之一，表单模板信息    
		List<FormTemplate> formTemplates = formTamplateService.findAll();
		ActionContext.getContext().put("formTemplates", formTemplates);
		return "myApplicationList";
	}

	// ==============================================================================

	/** 待我审批（我的任务列表） */
	public String myTaskList() {
		List<TaskView> taskViews = formService.findMyTaskList(getCurrentUser());
		ActionContext.getContext().put("taskViews", taskViews);
		return "myTaskList";
	}

	/** 审批处理页面 */
	public String approveUI() {
		Set<String> outcomes = formService.getOutcomesByTaskId(taskId);
		ActionContext.getContext().put("outcomes", outcomes);
		return "approveUI";
	}

	/** 审批处理 */
	public String approve() {
		// 生成一个ApproveInfo对象，表示本次的审批信息
		ApproveInfo approveInfo = new ApproveInfo();
		approveInfo.setApproval(approval);
		approveInfo.setComment(comment);
		approveInfo.setForm(formService.getById(formId));

		approveInfo.setApprover(getCurrentUser());
		approveInfo.setApproveTime(new Date());

		// 对表单进行审批处理(保存审批信息，完成任务，维护表单状态)
		formService.approve(approveInfo, taskId, outcome);
		return "toMyTaskList";// 转到我的任务列表
	}

	/** 查看流转记录 */
	public String approvedHistory() {
		Form form = formService.getById(formId);
		System.out.println("------"+form.getApproveInfos().size());   
		ActionContext.getContext().put("approveInfos", form.getApproveInfos());
		return "approvedHistory";         
	}

	// ====================================================================
	public Long getFormTemplateId() {
		return formTemplateId;
	}

	public void setFormTemplateId(Long formTemplateId) {
		this.formTemplateId = formTemplateId;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public boolean isApproval() {
		return approval;
	}

	public void setApproval(boolean approval) {
		this.approval = approval;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
