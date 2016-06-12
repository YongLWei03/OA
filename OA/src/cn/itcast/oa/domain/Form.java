package cn.itcast.oa.domain;

import java.util.Date;
import java.util.Set;

public class Form {
	/** 表单状态常量：审批中 */
	public static final String STATUS_RUNNING = "审批中";

	/** 表单状态常量：已通过 */
	public static final String STATUS_APPROVED = "已通过";

	/** 表单状态常量：未通过 */
	public static final String STATUS_REJECTED = "未通过";
	
	private Long id;
	private String title;
	private String path;
	private Date applyTime;
	private String status;

	private FormTemplate formTemplate;
	private Set<ApproveInfo> approveInfos;
	private User applicant;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public FormTemplate getFormTemplate() {
		return formTemplate;
	}

	public void setFormTemplate(FormTemplate formTemplate) {
		this.formTemplate = formTemplate;
	}

	public Set<ApproveInfo> getApproveInfos() {
		return approveInfos;
	}

	public void setApproveInfos(Set<ApproveInfo> approveInfos) {
		this.approveInfos = approveInfos;
	}

	public User getApplicant() {
		return applicant;
	}

	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}
}
