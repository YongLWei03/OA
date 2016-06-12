package cn.itcast.oa.view.action;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.org.apache.bcel.internal.generic.NEW;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.domain.Role;
import cn.itcast.oa.service.RoleService;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
	private Long[] privilegeIds;
    //获取列表
	public String list() throws Exception{
		List<Role>roles=roleService.findAll();
		ActionContext.getContext().put("roles", roles); //操作map
		return "list";
	}
	//删除
	public String delete() throws Exception{
		roleService.delete(model.getId());
		return "toList";
	}
	//添加
	public String add() throws Exception{
		//封装到role中
	     Role role=new Role();
	     role.setName(model.getName());
	     role.setDescription(model.getDescription());	 
		//保存到数据库
		roleService.save(role);
		return "toList";
	}
	//添加页面
	public String addUI() throws Exception{
		
		return "saveUI";
	}
	//修改
	public String edit() throws Exception{
		//从数据库获取role原来的对象
		Role role=roleService.getById(model.getId());
		
		//设置更新的字段
		role.setId(model.getId());
		role.setName(model.getName());
		role.setDescription(model.getDescription());
		//更新到数据库
		roleService.update(role);
		return "toList";
	}
	//修改页面
	public String editUI() throws Exception{
		//准备回显的数据
		Role role=roleService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(role); //操作对象栈
		return "saveUI";
	}
	
	//设置权限页面
	public String setPrivilegeUI() throws Exception{
		//准备回显的数据
		Role role=roleService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(role); //操作对象栈
		
		if(role.getPrivileges()!=null){
			privilegeIds=new Long[role.getPrivileges().size()];
			int index=0;
			for(Privilege priv:role.getPrivileges()){
				privilegeIds[index++]=priv.getId();
			}
		}
		
		//准备privilege数据
		List<Privilege> privileges=privilegeService.findAll();
		ActionContext.getContext().put("privileges", privileges);
		
		return "setPrivilegeUI";
		
	}
	//设置权限                    
	public String setPrivilege() throws Exception{
		
		//从数据库获取role原来的对象
		Role role=roleService.getById(model.getId());
		//设置更新的字段
        List<Privilege> privileges=privilegeService.getByIds(privilegeIds);
		role.setPrivileges(new HashSet<Privilege>(privileges));
        //更新到数据库
		roleService.update(role);
		
		
		
		return "toList";
		
	}
	public Long[] getPrivilegeIds() {
		return privilegeIds;
	}
	public void setPrivilegeIds(Long[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}
	
	
	
	
	



}
