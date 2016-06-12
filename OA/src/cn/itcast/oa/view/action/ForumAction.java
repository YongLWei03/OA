package cn.itcast.oa.view.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.util.QueryHelper;

@Controller
@Scope("prototype")
public class ForumAction extends BaseAction<Forum>{
	/**
	 * 0��ʾֻ�鿴ȫ������
	 * 1��ʾֻ�鿴ȫ��������
	 */
	private int viewType=0;
	/**
	 *  0��ʾĬ�����򣨰�������ʱ�����򣬵������ö�������ǰ�棩         
	 *  1��ʾ��������ʱ������                               
	 *  2��ʾ�����ⷢ��ʱ������                                
	 *  3��ʾ���ظ���������                               
	 * 
	 */
	private int orderBy=0;
	/**
	 * true ��ʾ����
	 * false��ʾ����
	 */
	private boolean asc=false;
    
	//��ʾ����б�
	public String list() throws Exception{
		List<Forum>forums=forumService.findAll();
		ActionContext.getContext().put("forums", forums);
		return "list";
	}
	
	//չ��ĳ�����(�����б�)
	public String show() throws Exception{
		//׼�����ݣ�forum
		Forum forum=forumService.getById(model.getId());
		ActionContext.getContext().put("forum", forum);
		
		//׼�����ݣ�Topics
		//List<Topic> topics=topicService.findByForum(forum);
		//ActionContext.getContext().put("topics", topics);
		//׼����ҳ��Ϣv1
		//PageBean pageBean=topicService.getPageBeanByForum(pageNum, pageSize, forum);
		//ActionContext.getContext().getValueStack().push(pageBean);//����ֵջ�У����ø�ԭ����ҳ������
		
		//׼����ҳ��Ϣv2
		//String hql="FROM Topic t WHERE t.forum=? ORDER BY ( CASE t.type  WHEN 2 THEN 2 ELSE 0 END ) DESC,t.lastUpdateTime DESC";
		//List<Object> parameters=new ArrayList<Object>();
		//parameters.add(forum);
		//PageBean pageBean=topicService.getPageBean(pageNum, pageSize, hql, parameters);
		//ActionContext.getContext().getValueStack().push(pageBean);//����ֵջ�У����ø�ԭ����ҳ������
		
		//׼����ҳ��Ϣv3
		//String hql="FROM Topic t WHERE t.forum=? ";
		//List<Object> parameters=new ArrayList<Object>();
		//parameters.add(forum);
		//if(viewType==1){//1:��ʾֻ��������
		//	hql+=" AND t.type=? ";
		//	parameters.add(Topic.TYPE_BEST);
		//}
		//if(orderBy==1){//1��ʾֻ��������ʱ������
		//	hql+=" ORDER BY t.lastUpdateTime "+(asc?"ASC":"DESC");
		//}else if(orderBy==2){//2:��ʾֻ�����ⷢ��ʱ������
		//	hql+=" ORDER BY t.postTime "+(asc?"ASC":"DESC");
		//}else if(orderBy==3){//3:��ʾֻ���ظ���������
		//	hql+=" ORDER BY t.replyCount "+(asc?"ASC":"DESC");
		//}else{//0:��ʾĬ������
		//	hql+=" ORDER BY ( CASE t.type  WHEN 2 THEN 2 ELSE 0 END ) DESC,t.lastUpdateTime DESC ";
		//}
        //
		//PageBean pageBean=topicService.getPageBean(pageNum, pageSize, hql, parameters);
		//ActionContext.getContext().getValueStack().push(pageBean);//����ֵջ�У����ø�ԭ����ҳ������		
		
		//׼����ҳ��Ϣ���հ�
		new QueryHelper(Topic.class, "t")
		//��������
		.addCondition("t.forum=?", forum)
		.addCondition((viewType==1),"t.type=?", Topic.TYPE_BEST)//1:��ʾֻ��������
		//��������
		.addOrderByProperty((orderBy==1),"t.lastUpdateTime", asc)//1��ʾֻ��������ʱ������
		.addOrderByProperty((orderBy==2),"t.postTime", asc)//2:��ʾֻ�����ⷢ��ʱ������
		.addOrderByProperty((orderBy==3),"t.replyCount", asc)//3:��ʾֻ���ظ���������
		.addOrderByProperty((orderBy==0),"( CASE t.type  WHEN 2 THEN 2 ELSE 0 END )", asc)//0:��ʾĬ������
		.addOrderByProperty((orderBy==0),"t.lastUpdateTime", asc)
		.preparePageBean(topicService, pageNum, pageSize);  
		return "show";
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}
}
