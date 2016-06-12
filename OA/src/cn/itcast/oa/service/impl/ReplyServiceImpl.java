package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.service.ReplyService;

@Service("replyService")
@Transactional
public class ReplyServiceImpl extends DaoSupportImpl<Reply> implements ReplyService{
	
	@SuppressWarnings("unchecked")
	@Deprecated
	public List<Reply> findByTopic(Topic topic) {
		// TODO Auto-generated method stub
		return getSession().createQuery(//
				"FROM Reply r WHERE r.topic=? ORDER BY r.postTime ASC")//
				.setParameter(0, topic)//
				.list();
	}


	@Override
	public void save(Reply reply) {
		// TODO Auto-generated method stub
		//保存
		getSession().save(reply);
		//维护相关的信息
		Topic topic=reply.getTopic();
		topic.setLastReply(reply);
		topic.setReplyCount(topic.getReplyCount()+1);
		topic.setLastUpdateTime(reply.getPostTime());
		Forum forum=topic.getForum();
		forum.setArticleCount(forum.getArticleCount()+1);
		
		getSession().update(topic);
		getSession().update(forum);
		
	}
    @Deprecated
	public PageBean getPageBeanByTopic(int pageNum, int pageSize, Topic topic) {
		// TODO Auto-generated method stub
		//查询列表
		List records=getSession().createQuery(//
				"FROM Reply r  WHERE r.topic=? ORDER By r.postTime ASC")//
				.setParameter(0, topic)//
				.setFirstResult((pageNum-1)*pageSize)
				.setMaxResults(pageSize)//  
				.list();
		//查询总数量
		Long count=(Long) getSession().createQuery(
				"SELECT COUNT(*) FROM Reply r  WHERE r.topic=? ")
				.setParameter(0, topic)
				.uniqueResult();
		return new PageBean(pageNum, pageSize, records, count.intValue());
	}
   
}
