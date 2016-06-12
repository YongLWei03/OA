package cn.itcast.oa.view.action;

import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;

@Controller
@Scope("prototype")
public class ReplyAction extends BaseAction<Reply>{
	private Long topicId;
   //�ظ�ҳ��
	public String addUI() throws Exception{
        Topic topic=topicService.getById(topicId);;
        ActionContext.getContext().put("topic", topic);
		return "addUI";
	}
   //�ظ�
	public String add() throws Exception{
		//��װ
		//>>���ֶ�
		//model.setTitle();
		//model.setContent();
		model.setTopic(topicService.getById(topicId));
		
		//>>��ǰ��Ϣ
		model.setAuthor(getCurrentUser());
		model.setPostTime(new Date());
		model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());
		
		//����
		replyService.save(model);

		return "toTopicShow";
	}
	public Long getTopicId() {
		return topicId;
	}
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	
}
