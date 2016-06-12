package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.service.TopicService;

@Service("topicSerivce")
@Transactional
public class TopicServiceImpl extends DaoSupportImpl<Topic> implements TopicService{

	@SuppressWarnings("unchecked")
	@Deprecated
	public List<Topic> findByForum(Forum forum) {
		//排序：所有置顶帖在最上面，并按最后更新时间排序，让心状太的上面
		//TODO 排序
		return getSession().createQuery(//
				"FROM Topic t WHERE t.forum=? ORDER BY ( CASE t.type  WHEN 2 THEN 2 ELSE 0 END ) DESC,t.lastUpdateTime DESC")//
				.setParameter(0, forum)//
				.list();
	}

	@Override
	public void save(Topic model) {
		// TODO Auto-generated method stub

		//>>特殊字段应放到业务中进行维护
		model.setReplyCount(0);
		model.setLastReply(null);//默认
		model.setLastUpdateTime(model.getPostTime());
		//保存
		getSession().save(model);
		//更新Forum
		Forum forum=model.getForum();
		forum.setTopicCount(forum.getTopicCount()+1);
		forum.setArticleCount(forum.getArticleCount()+1);
		forum.setLastTopic(model);
		getSession().update(model);
	}

    @Deprecated
	public PageBean getPageBeanByForum(int pageNum, int pageSize, Forum forum) {
		// TODO Auto-generated method stub
		//查询列表
		List records=getSession().createQuery(//
				"FROM Topic t WHERE t.forum=? ORDER BY ( CASE t.type  WHEN 2 THEN 2 ELSE 0 END ) DESC,t.lastUpdateTime DESC")//
				.setParameter(0, forum)//
				.setFirstResult((pageNum-1)*pageSize)
				.setMaxResults(pageSize)//  
				.list();
		//查询总数量
		Long count=(Long) getSession().createQuery(
				"SELECT COUNT(*) FROM Topic t WHERE t.forum=?")
				.setParameter(0, forum)
				.uniqueResult();
		return new PageBean(pageNum, pageSize, records, count.intValue());
	}
}
