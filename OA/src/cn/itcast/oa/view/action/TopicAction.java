package cn.itcast.oa.view.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.util.QueryHelper;

@Service
@Scope("prototype")
public class TopicAction extends BaseAction<Topic>{
	private Long forumId;

	
	//展现某个主题(主题+回复)
	public String show() throws Exception{
		//准备数据：topic
		Topic topic=topicService.getById(model.getId());
		ActionContext.getContext().put("topic", topic);
		//准备数据:replies
		//List<Reply> replies=replyService.findByTopic(topic);
		//ActionContext.getContext().put("replies", replies);
		
		//准备分页信息v1
		//PageBean pageBean=replyService.getPageBeanByTopic(pageNum,pageSize,topic);
		//ActionContext.getContext().getValueStack().push(pageBean);//放在值栈中，不用改原来的页面数据

		//准备分页信息v2
		//String hql="FROM Reply r  WHERE r.topic=? ORDER By r.postTime ASC";
		//List<Object>parameters=new ArrayList<Object>();
		//parameters.add(topic);
		//PageBean pageBean=replyService.getPageBean(pageNum, pageSize, hql, parameters);
		//ActionContext.getContext().getValueStack().push(pageBean);//放在值栈中，不用改原来的页面数据
		
		//准备分页信息最终版
		new QueryHelper(Reply.class, "r")  
		    .addCondition("r.topic=?", topic)
            .addOrderByProperty("r.postTime", true)
            .preparePageBean(replyService, pageNum, pageSize);
		    
		return "show";
	}
	
	//发表新主题的页面
	public String addUI() throws Exception{
		//准备数据
		Forum forum=forumService.getById(forumId);
		ActionContext.getContext().put("forum", forum);
		return "addUI";
	}
	

	//发表主题
	public String add() throws Exception{
		//封装到model中
		//>>表单中获取
		//model.setTitle();
		//model.setContent();
		model.setForum(forumService.getById(forumId));
		//>>直接获取
		model.setAuthor((getCurrentUser()));
		model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());
		model.setPostTime(new Date());
		model.setType(Topic.TYPE_NORMAL);

		//保存
		topicService.save(model);
		return "toShow";
	}

	public Long getForumId() {
		return forumId;
	}

	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}



}
