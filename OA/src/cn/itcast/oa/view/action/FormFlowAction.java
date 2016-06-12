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

	/** ������루���ģ���б� */
	public String formTemplateList() {
		List<FormTemplate> formTemplates = formTamplateService.findAll();
		ActionContext.getContext().put("formTemplates", formTemplates);
		return "formTemplateList";
	}

	/** �ύ����ҳ�� */
	public String submitUI() {
		return "submitUI";
	}

	/** �ύ���� */
	public String submit() {
		// ����һ��Form���󣬱�ʾ����������Ϣ
		Form form = new Form();
		form.setPath(saveUploadFile(upload));
		form.setFormTemplate(formTamplateService.getById(formTemplateId));

		form.setApplicant(getCurrentUser());
		form.setApplyTime(new Date());

		// �����������ʼ��ת
		formService.submit(form);

		return "toMyApplicationList";// ת���ҵ������ѯ
	}

	/** �ҵ������ѯ */
	public String myApplicationList() {
		// 1,��ҳ��Ϣ
		new QueryHelper(Form.class, "f")//
				.addCondition("f.applicant=?", getCurrentUser())//
				.addCondition(formTemplateId != null, "f.formTemplate.id=?",formTemplateId)//
				.addCondition(status!=null&&!status.equals(""), "f.status=?", status)//
				.addOrderByProperty("f.applyTime", false)//  
				.preparePageBean(formService, pageNum, pageSize);
		
		// 2,��������֮һ����ģ����Ϣ    
		List<FormTemplate> formTemplates = formTamplateService.findAll();
		ActionContext.getContext().put("formTemplates", formTemplates);
		return "myApplicationList";
	}

	// ==============================================================================

	/** �����������ҵ������б� */
	public String myTaskList() {
		List<TaskView> taskViews = formService.findMyTaskList(getCurrentUser());
		ActionContext.getContext().put("taskViews", taskViews);
		return "myTaskList";
	}

	/** ��������ҳ�� */
	public String approveUI() {
		Set<String> outcomes = formService.getOutcomesByTaskId(taskId);
		ActionContext.getContext().put("outcomes", outcomes);
		return "approveUI";
	}

	/** �������� */
	public String approve() {
		// ����һ��ApproveInfo���󣬱�ʾ���ε�������Ϣ
		ApproveInfo approveInfo = new ApproveInfo();
		approveInfo.setApproval(approval);
		approveInfo.setComment(comment);
		approveInfo.setForm(formService.getById(formId));

		approveInfo.setApprover(getCurrentUser());
		approveInfo.setApproveTime(new Date());

		// �Ա�������������(����������Ϣ���������ά����״̬)
		formService.approve(approveInfo, taskId, outcome);
		return "toMyTaskList";// ת���ҵ������б�
	}

	/** �鿴��ת��¼ */
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
