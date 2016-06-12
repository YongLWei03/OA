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

	
	//չ��ĳ������(����+�ظ�)
	public String show() throws Exception{
		//׼�����ݣ�topic
		Topic topic=topicService.getById(model.getId());
		ActionContext.getContext().put("topic", topic);
		//׼������:replies
		//List<Reply> replies=replyService.findByTopic(topic);
		//ActionContext.getContext().put("replies", replies);
		
		//׼����ҳ��Ϣv1
		//PageBean pageBean=replyService.getPageBeanByTopic(pageNum,pageSize,topic);
		//ActionContext.getContext().getValueStack().push(pageBean);//����ֵջ�У����ø�ԭ����ҳ������

		//׼����ҳ��Ϣv2
		//String hql="FROM Reply r  WHERE r.topic=? ORDER By r.postTime ASC";
		//List<Object>parameters=new ArrayList<Object>();
		//parameters.add(topic);
		//PageBean pageBean=replyService.getPageBean(pageNum, pageSize, hql, parameters);
		//ActionContext.getContext().getValueStack().push(pageBean);//����ֵջ�У����ø�ԭ����ҳ������
		
		//׼����ҳ��Ϣ���հ�
		new QueryHelper(Reply.class, "r")  
		    .addCondition("r.topic=?", topic)
            .addOrderByProperty("r.postTime", true)
            .preparePageBean(replyService, pageNum, pageSize);
		    
		return "show";
	}
	
	//�����������ҳ��
	public String addUI() throws Exception{
		//׼������
		Forum forum=forumService.getById(forumId);
		ActionContext.getContext().put("forum", forum);
		return "addUI";
	}
	

	//��������
	public String add() throws Exception{
		//��װ��model��
		//>>���л�ȡ
		//model.setTitle();
		//model.setContent();
		model.setForum(forumService.getById(forumId));
		//>>ֱ�ӻ�ȡ
		model.setAuthor((getCurrentUser()));
		model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());
		model.setPostTime(new Date());
		model.setType(Topic.TYPE_NORMAL);

		//����
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
