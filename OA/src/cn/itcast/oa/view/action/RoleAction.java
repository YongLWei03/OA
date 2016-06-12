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
    //��ȡ�б�
	public String list() throws Exception{
		List<Role>roles=roleService.findAll();
		ActionContext.getContext().put("roles", roles); //����map
		return "list";
	}
	//ɾ��
	public String delete() throws Exception{
		roleService.delete(model.getId());
		return "toList";
	}
	//���
	public String add() throws Exception{
		//��װ��role��
	     Role role=new Role();
	     role.setName(model.getName());
	     role.setDescription(model.getDescription());	 
		//���浽���ݿ�
		roleService.save(role);
		return "toList";
	}
	//���ҳ��
	public String addUI() throws Exception{
		
		return "saveUI";
	}
	//�޸�
	public String edit() throws Exception{
		//�����ݿ��ȡroleԭ���Ķ���
		Role role=roleService.getById(model.getId());
		
		//���ø��µ��ֶ�
		role.setId(model.getId());
		role.setName(model.getName());
		role.setDescription(model.getDescription());
		//���µ����ݿ�
		roleService.update(role);
		return "toList";
	}
	//�޸�ҳ��
	public String editUI() throws Exception{
		//׼�����Ե�����
		Role role=roleService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(role); //��������ջ
		return "saveUI";
	}
	
	//����Ȩ��ҳ��
	public String setPrivilegeUI() throws Exception{
		//׼�����Ե�����
		Role role=roleService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(role); //��������ջ
		
		if(role.getPrivileges()!=null){
			privilegeIds=new Long[role.getPrivileges().size()];
			int index=0;
			for(Privilege priv:role.getPrivileges()){
				privilegeIds[index++]=priv.getId();
			}
		}
		
		//׼��privilege����
		List<Privilege> privileges=privilegeService.findAll();
		ActionContext.getContext().put("privileges", privileges);
		
		return "setPrivilegeUI";
		
	}
	//����Ȩ��                    
	public String setPrivilege() throws Exception{
		
		//�����ݿ��ȡroleԭ���Ķ���
		Role role=roleService.getById(model.getId());
		//���ø��µ��ֶ�
        List<Privilege> privileges=privilegeService.getByIds(privilegeIds);
		role.setPrivileges(new HashSet<Privilege>(privileges));
        //���µ����ݿ�
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
