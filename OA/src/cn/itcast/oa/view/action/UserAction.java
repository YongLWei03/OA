package cn.itcast.oa.view.action;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.sun.org.apache.commons.digester.Digester;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.domain.Role;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.util.DepartmentUtils;
import cn.itcast.oa.util.QueryHelper;
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	private Long departmentId;
	private Long[] roleIds;
	// 获取列表
	public String list() throws Exception {
		
        //List<User> users=userService.findAll();
        //ActionContext.getContext().put("users", users);
		//准备分页信息
		new QueryHelper(User.class, "u")
		    .preparePageBean(userService, pageNum, pageSize);
		return "list";
	}
  
	// 删除
	public String delete() throws Exception {
        userService.delete(model.getId());  
		return "toList";
	}

	// 添加
	public String add() throws Exception {
		//封装到对象中(注意：要设置未封装的属性)
		//>>设置department属性
		  model.setDepartment(departmentService.getById(departmentId));
		//>>设置roles属性
		  List<Role> roles=roleService.getByIds(roleIds);
		  model.setRoles(new HashSet<Role>(roles));
		//>>设置默认的密码
	      String md5Digest=DigestUtils.md5Hex("1234");
	      model.setPassword(md5Digest);
        //保存到数据库
		  userService.save(model);
		return "toList";
	}

	// 添加页面
	public String addUI() throws Exception {
		// 准备回显的数据,departments
		List<Department> topDepartments=departmentService.findTopList();
		List<Department>departments=DepartmentUtils.getAllDepartments( topDepartments);
		ActionContext.getContext().put("departments", departments); 
		
		//准备回显的数据,roles
        List<Role> roles=roleService.findAll();
        ActionContext.getContext().put("roles", roles);
        
		return "saveUI";
	}

	// 修改
	public String edit() throws Exception {
		// 从数据库获取原来的对象
         User user=userService.getById(model.getId());
		// 设置更新的字段
         user.setName(model.getName());
         user.setLoginName(model.getLoginName());
         user.setGender(model.getGender());
         user.setPhoneNumber(model.getPhoneNumber());
         user.setEmail(model.getEmail());
         user.setDescription(model.getDescription());
         //设置所属的部门 
         user.setDepartment(departmentService.getById(departmentId));
         //设置关联的角色 
         List<Role> roles=roleService.getByIds(roleIds);
         user.setRoles(new HashSet<Role>(roles));
         
		// 更新到数据库
         userService.update(user);

		 return "toList";
	}

	// 修改页面
	public String editUI() throws Exception {
		// 准备回显的数据,departments
		List<Department> topDepartments=departmentService.findTopList();
		List<Department>departments=DepartmentUtils.getAllDepartments( topDepartments);
		ActionContext.getContext().put("departments", departments); 
		
		//准备回显的数据,roles
        List<Role> roles=roleService.findAll();
        ActionContext.getContext().put("roles", roles);
        
        //准备回显的数据，当前user
        User user=userService.getById(model.getId());
        ActionContext.getContext().getValueStack().push(user);
        if(user.getDepartment()!=null){
        	departmentId=user.getDepartment().getId(); //有疑问：部门名称怎么显示
        }
        if(user.getRoles()!=null){
        	roleIds=new Long[user.getRoles().size()];
        	int index=0;
        	for(Role role:user.getRoles()){
        		roleIds[index++]=role.getId(); //有疑问:岗位名称怎么显示
        	}
        }
        
		return "saveUI";
	}
	
	//初始化密码为1234
	public String initPassword() throws Exception{
		// 从数据库获取原来的对象
        User user=userService.getById(model.getId());
        String md5Digest=DigestUtils.md5Hex("1234");
		// 设置更新的字段
        user.setPassword(md5Digest);
        
		// 更新到数据库
        userService.update(user);

		 return "toList";
	}
	//登陆页面
	public String loginUI() throws Exception{

		return "loginUI";
	}
	//登陆
	public String login() throws Exception{
		
		User user=userService.findByLoginNameAndPassword(model.getLoginName(),model.getPassword());
		if(user==null){
			addFieldError("login", "用户名或密码不正确!");
			return "loginUI";
		}else{
			ActionContext.getContext().getSession().put("user", user);
			return "toIndex";
		}
	}
	//注销
	public String logout() throws Exception{
		
		ActionContext.getContext().getSession().remove("user");
		return "logout";
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

}
