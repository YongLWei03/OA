package cn.itcast.oa.view.action;

import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.itcast.oa.base.BaseAction;

import cn.itcast.oa.domain.Forum;

@Controller
@Scope("prototype")
public class ForumManageAction extends BaseAction<Forum>{
	 //��ȡ�б�
		public String list() throws Exception{
            List<Forum> forums=forumService.findAll();
            ActionContext.getContext().put("forums", forums);
			return "list";
		}
		//ɾ��
		public String delete() throws Exception{
            forumService.delete(model.getId());
			return "toList";
		}
		//���
		public String add() throws Exception{     
			//���浽���ݿ�
            forumService.save(model);
			return "toList";
		}
		//���ҳ��
		public String addUI() throws Exception{
              
			return "saveUI";
		}
		//�޸�
		public String edit() throws Exception{
			//�����ݿ��ȡԭ���Ķ���
            Forum forum=forumService.getById(model.getId());
			//���ø��µ��ֶ�   
            forum.setName(model.getName());
            forum.setDescription(model.getDescription());
			//���µ����ݿ�
            forumService.update(forum);
			return "toList";
		}
		//�޸�ҳ��
		public String editUI() throws Exception{
			//׼����������
             Forum forum=forumService.getById(model.getId());
             ActionContext.getContext().getValueStack().push(forum);
            
			return "saveUI";
		}
		
		public String moveUp() throws Exception{
			forumService.moveUp(model.getId());
			return "toList";
		}
		
		public String moveDown() throws Exception{
			forumService.moveDown(model.getId());
			return "toList";
		}
}
