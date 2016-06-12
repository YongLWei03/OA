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
	// ��ȡ�б�
	public String list() throws Exception {
		
        //List<User> users=userService.findAll();
        //ActionContext.getContext().put("users", users);
		//׼����ҳ��Ϣ
		new QueryHelper(User.class, "u")
		    .preparePageBean(userService, pageNum, pageSize);
		return "list";
	}
  
	// ɾ��
	public String delete() throws Exception {
        userService.delete(model.getId());  
		return "toList";
	}

	// ���
	public String add() throws Exception {
		//��װ��������(ע�⣺Ҫ����δ��װ������)
		//>>����department����
		  model.setDepartment(departmentService.getById(departmentId));
		//>>����roles����
		  List<Role> roles=roleService.getByIds(roleIds);
		  model.setRoles(new HashSet<Role>(roles));
		//>>����Ĭ�ϵ�����
	      String md5Digest=DigestUtils.md5Hex("1234");
	      model.setPassword(md5Digest);
        //���浽���ݿ�
		  userService.save(model);
		return "toList";
	}

	// ���ҳ��
	public String addUI() throws Exception {
		// ׼�����Ե�����,departments
		List<Department> topDepartments=departmentService.findTopList();
		List<Department>departments=DepartmentUtils.getAllDepartments( topDepartments);
		ActionContext.getContext().put("departments", departments); 
		
		//׼�����Ե�����,roles
        List<Role> roles=roleService.findAll();
        ActionContext.getContext().put("roles", roles);
        
		return "saveUI";
	}

	// �޸�
	public String edit() throws Exception {
		// �����ݿ��ȡԭ���Ķ���
         User user=userService.getById(model.getId());
		// ���ø��µ��ֶ�
         user.setName(model.getName());
         user.setLoginName(model.getLoginName());
         user.setGender(model.getGender());
         user.setPhoneNumber(model.getPhoneNumber());
         user.setEmail(model.getEmail());
         user.setDescription(model.getDescription());
         //���������Ĳ��� 
         user.setDepartment(departmentService.getById(departmentId));
         //���ù����Ľ�ɫ 
         List<Role> roles=roleService.getByIds(roleIds);
         user.setRoles(new HashSet<Role>(roles));
         
		// ���µ����ݿ�
         userService.update(user);

		 return "toList";
	}

	// �޸�ҳ��
	public String editUI() throws Exception {
		// ׼�����Ե�����,departments
		List<Department> topDepartments=departmentService.findTopList();
		List<Department>departments=DepartmentUtils.getAllDepartments( topDepartments);
		ActionContext.getContext().put("departments", departments); 
		
		//׼�����Ե�����,roles
        List<Role> roles=roleService.findAll();
        ActionContext.getContext().put("roles", roles);
        
        //׼�����Ե����ݣ���ǰuser
        User user=userService.getById(model.getId());
        ActionContext.getContext().getValueStack().push(user);
        if(user.getDepartment()!=null){
        	departmentId=user.getDepartment().getId(); //�����ʣ�����������ô��ʾ
        }
        if(user.getRoles()!=null){
        	roleIds=new Long[user.getRoles().size()];
        	int index=0;
        	for(Role role:user.getRoles()){
        		roleIds[index++]=role.getId(); //������:��λ������ô��ʾ
        	}
        }
        
		return "saveUI";
	}
	
	//��ʼ������Ϊ1234
	public String initPassword() throws Exception{
		// �����ݿ��ȡԭ���Ķ���
        User user=userService.getById(model.getId());
        String md5Digest=DigestUtils.md5Hex("1234");
		// ���ø��µ��ֶ�
        user.setPassword(md5Digest);
        
		// ���µ����ݿ�
        userService.update(user);

		 return "toList";
	}
	//��½ҳ��
	public String loginUI() throws Exception{

		return "loginUI";
	}
	//��½
	public String login() throws Exception{
		
		User user=userService.findByLoginNameAndPassword(model.getLoginName(),model.getPassword());
		if(user==null){
			addFieldError("login", "�û��������벻��ȷ!");
			return "loginUI";
		}else{
			ActionContext.getContext().getSession().put("user", user);
			return "toIndex";
		}
	}
	//ע��
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
