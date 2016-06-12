package cn.itcast.oa.service;

import java.util.List;
import java.util.Set;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.domain.ApproveInfo;
import cn.itcast.oa.domain.Form;
import cn.itcast.oa.domain.TaskView;
import cn.itcast.oa.domain.User;

public interface FormService extends DaoSupport<Form>{

	void submit(Form form);

	List<TaskView> findMyTaskList(User currentUser);

	void approve(ApproveInfo approveInfo, String taskId, String outcome);

	Set<String> getOutcomesByTaskId(String taskId);
   
}
