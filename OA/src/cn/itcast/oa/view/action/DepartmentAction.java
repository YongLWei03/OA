package cn.itcast.oa.view.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.service.DepartmentService;
import cn.itcast.oa.util.DepartmentUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class DepartmentAction extends BaseAction<Department>{

	private Long parentId;
    
    //��ȡ�б�
	public String list() throws Exception{
		List<Department>departments=departmentService.findAll();
		
		if(parentId==null){
			departments=departmentService.findTopList();
		}else{
			departments=departmentService.findChildren(parentId);
			//������һ��������
			Department parent=departmentService.getById(parentId);
			ActionContext.getContext().put("parent", parent);
		}
		ActionContext.getContext().put("departments", departments); //����map
		return "list";
	}
	//ɾ��
	public String delete() throws Exception{
		departmentService.delete(model.getId());
		return "toList";
	}
	//���
	public String add() throws Exception{
        Department parent=departmentService.getById(parentId);
        model.setParent(parent);
		//���浽���ݿ�
		departmentService.save(model);
		return "toList";
	}
	//���ҳ��
	public String addUI() throws Exception{
		//׼�����Ե�����
		List<Department> topDepartments=departmentService.findTopList();
		List<Department>departments=DepartmentUtils.getAllDepartments( topDepartments);
		ActionContext.getContext().put("departments", departments); 
		return "saveUI";
	}
	//�޸�
	public String edit() throws Exception{
		//�����ݿ��ȡDepartmentԭ���Ķ���
		Department department=departmentService.getById(model.getId());
		
		//���ø��µ��ֶ�
		department.setName(model.getName());
		department.setDescription(model.getDescription());
		department.setParent(departmentService.getById(parentId));
		
		//���µ����ݿ�
		departmentService.update(department);
		return "toList";
	}
	//�޸�ҳ��
	public String editUI() throws Exception{
		//׼�������б�Ļ�������
		List<Department> topDepartments=departmentService.findTopList();
		List<Department>departments=DepartmentUtils.getAllDepartments( topDepartments);
		ActionContext.getContext().put("departments", departments); 
		//׼����ǰ���Ż��Ե�����
		Department department=departmentService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(department); //��������ջ
		if(department.getParent()!=null){
			parentId=department.getParent().getId();//���Ե�ǰ�������ϼ�����
		}
		return "saveUI";
	}
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
