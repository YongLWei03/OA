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
    
    //获取列表
	public String list() throws Exception{
		List<Department>departments=departmentService.findAll();
		
		if(parentId==null){
			departments=departmentService.findTopList();
		}else{
			departments=departmentService.findChildren(parentId);
			//返回上一级的数据
			Department parent=departmentService.getById(parentId);
			ActionContext.getContext().put("parent", parent);
		}
		ActionContext.getContext().put("departments", departments); //操作map
		return "list";
	}
	//删除
	public String delete() throws Exception{
		departmentService.delete(model.getId());
		return "toList";
	}
	//添加
	public String add() throws Exception{
        Department parent=departmentService.getById(parentId);
        model.setParent(parent);
		//保存到数据库
		departmentService.save(model);
		return "toList";
	}
	//添加页面
	public String addUI() throws Exception{
		//准备回显的数据
		List<Department> topDepartments=departmentService.findTopList();
		List<Department>departments=DepartmentUtils.getAllDepartments( topDepartments);
		ActionContext.getContext().put("departments", departments); 
		return "saveUI";
	}
	//修改
	public String edit() throws Exception{
		//从数据库获取Department原来的对象
		Department department=departmentService.getById(model.getId());
		
		//设置更新的字段
		department.setName(model.getName());
		department.setDescription(model.getDescription());
		department.setParent(departmentService.getById(parentId));
		
		//更新到数据库
		departmentService.update(department);
		return "toList";
	}
	//修改页面
	public String editUI() throws Exception{
		//准备下拉列表的回显数据
		List<Department> topDepartments=departmentService.findTopList();
		List<Department>departments=DepartmentUtils.getAllDepartments( topDepartments);
		ActionContext.getContext().put("departments", departments); 
		//准备当前部门回显的数据
		Department department=departmentService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(department); //操作对象栈
		if(department.getParent()!=null){
			parentId=department.getParent().getId();//回显当前所属的上级部门
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
