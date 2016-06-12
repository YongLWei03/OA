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
	 //获取列表
		public String list() throws Exception{
            List<Forum> forums=forumService.findAll();
            ActionContext.getContext().put("forums", forums);
			return "list";
		}
		//删除
		public String delete() throws Exception{
            forumService.delete(model.getId());
			return "toList";
		}
		//添加
		public String add() throws Exception{     
			//保存到数据库
            forumService.save(model);
			return "toList";
		}
		//添加页面
		public String addUI() throws Exception{
              
			return "saveUI";
		}
		//修改
		public String edit() throws Exception{
			//从数据库获取原来的对象
            Forum forum=forumService.getById(model.getId());
			//设置更新的字段   
            forum.setName(model.getName());
            forum.setDescription(model.getDescription());
			//更新到数据库
            forumService.update(forum);
			return "toList";
		}
		//修改页面
		public String editUI() throws Exception{
			//准备回显数据
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
