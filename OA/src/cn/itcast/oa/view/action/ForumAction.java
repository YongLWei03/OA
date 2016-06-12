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
	 * 0表示只查看全部主题
	 * 1表示只查看全部精华贴
	 */
	private int viewType=0;
	/**
	 *  0表示默认排序（按最后更新时间排序，但所有置顶帖都在前面）         
	 *  1表示按最后更新时间排序                               
	 *  2表示按主题发表时间排序                                
	 *  3表示按回复数量排序                               
	 * 
	 */
	private int orderBy=0;
	/**
	 * true 表示降序
	 * false表示升序
	 */
	private boolean asc=false;
    
	//显示板块列表
	public String list() throws Exception{
		List<Forum>forums=forumService.findAll();
		ActionContext.getContext().put("forums", forums);
		return "list";
	}
	
	//展现某个板块(主题列表)
	public String show() throws Exception{
		//准备数据：forum
		Forum forum=forumService.getById(model.getId());
		ActionContext.getContext().put("forum", forum);
		
		//准备数据：Topics
		//List<Topic> topics=topicService.findByForum(forum);
		//ActionContext.getContext().put("topics", topics);
		//准备分页信息v1
		//PageBean pageBean=topicService.getPageBeanByForum(pageNum, pageSize, forum);
		//ActionContext.getContext().getValueStack().push(pageBean);//放在值栈中，不用改原来的页面数据
		
		//准备分页信息v2
		//String hql="FROM Topic t WHERE t.forum=? ORDER BY ( CASE t.type  WHEN 2 THEN 2 ELSE 0 END ) DESC,t.lastUpdateTime DESC";
		//List<Object> parameters=new ArrayList<Object>();
		//parameters.add(forum);
		//PageBean pageBean=topicService.getPageBean(pageNum, pageSize, hql, parameters);
		//ActionContext.getContext().getValueStack().push(pageBean);//放在值栈中，不用改原来的页面数据
		
		//准备分页信息v3
		//String hql="FROM Topic t WHERE t.forum=? ";
		//List<Object> parameters=new ArrayList<Object>();
		//parameters.add(forum);
		//if(viewType==1){//1:表示只看精华帖
		//	hql+=" AND t.type=? ";
		//	parameters.add(Topic.TYPE_BEST);
		//}
		//if(orderBy==1){//1表示只按最后更新时间排序
		//	hql+=" ORDER BY t.lastUpdateTime "+(asc?"ASC":"DESC");
		//}else if(orderBy==2){//2:表示只按主题发表时间排序
		//	hql+=" ORDER BY t.postTime "+(asc?"ASC":"DESC");
		//}else if(orderBy==3){//3:表示只按回复数量排序
		//	hql+=" ORDER BY t.replyCount "+(asc?"ASC":"DESC");
		//}else{//0:表示默认排序
		//	hql+=" ORDER BY ( CASE t.type  WHEN 2 THEN 2 ELSE 0 END ) DESC,t.lastUpdateTime DESC ";
		//}
        //
		//PageBean pageBean=topicService.getPageBean(pageNum, pageSize, hql, parameters);
		//ActionContext.getContext().getValueStack().push(pageBean);//放在值栈中，不用改原来的页面数据		
		
		//准备分页信息最终版
		new QueryHelper(Topic.class, "t")
		//过滤条件
		.addCondition("t.forum=?", forum)
		.addCondition((viewType==1),"t.type=?", Topic.TYPE_BEST)//1:表示只看精华帖
		//排序条件
		.addOrderByProperty((orderBy==1),"t.lastUpdateTime", asc)//1表示只按最后更新时间排序
		.addOrderByProperty((orderBy==2),"t.postTime", asc)//2:表示只按主题发表时间排序
		.addOrderByProperty((orderBy==3),"t.replyCount", asc)//3:表示只按回复数量排序
		.addOrderByProperty((orderBy==0),"( CASE t.type  WHEN 2 THEN 2 ELSE 0 END )", asc)//0:表示默认排序
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
