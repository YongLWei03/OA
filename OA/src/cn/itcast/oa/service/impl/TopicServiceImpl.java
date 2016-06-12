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
		//���������ö����������棬����������ʱ����������״̫������
		//TODO ����
		return getSession().createQuery(//
				"FROM Topic t WHERE t.forum=? ORDER BY ( CASE t.type  WHEN 2 THEN 2 ELSE 0 END ) DESC,t.lastUpdateTime DESC")//
				.setParameter(0, forum)//
				.list();
	}

	@Override
	public void save(Topic model) {
		// TODO Auto-generated method stub

		//>>�����ֶ�Ӧ�ŵ�ҵ���н���ά��
		model.setReplyCount(0);
		model.setLastReply(null);//Ĭ��
		model.setLastUpdateTime(model.getPostTime());
		//����
		getSession().save(model);
		//����Forum
		Forum forum=model.getForum();
		forum.setTopicCount(forum.getTopicCount()+1);
		forum.setArticleCount(forum.getArticleCount()+1);
		forum.setLastTopic(model);
		getSession().update(model);
	}

    @Deprecated
	public PageBean getPageBeanByForum(int pageNum, int pageSize, Forum forum) {
		// TODO Auto-generated method stub
		//��ѯ�б�
		List records=getSession().createQuery(//
				"FROM Topic t WHERE t.forum=? ORDER BY ( CASE t.type  WHEN 2 THEN 2 ELSE 0 END ) DESC,t.lastUpdateTime DESC")//
				.setParameter(0, forum)//
				.setFirstResult((pageNum-1)*pageSize)
				.setMaxResults(pageSize)//  
				.list();
		//��ѯ������
		Long count=(Long) getSession().createQuery(
				"SELECT COUNT(*) FROM Topic t WHERE t.forum=?")
				.setParameter(0, forum)
				.uniqueResult();
		return new PageBean(pageNum, pageSize, records, count.intValue());
	}
}
