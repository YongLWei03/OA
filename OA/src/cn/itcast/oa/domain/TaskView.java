package cn.itcast.oa.domain;

import org.jbpm.api.task.Task;
/**
 * ֻ���ڡ�����������ҳ������ʾ����ʱʹ�ã�����Ӧ���ݱ�
 * @author zhang hai sheng
 *
 */
public class TaskView {
	private Task task;//��ǰ����������
	private Form form;//��ǰ�������ı�
	
	public TaskView(Task task,Form form){
		this.task=task;
		this.form=form;
	}
	
	public TaskView(){
		
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

}
