package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;

public interface TopicService extends DaoSupport<Topic> {
    
	/**
	 * ��ѯָ������е��������⣬���������ö����������棬����������ʱ������������״̬��������
	 * @param forum
	 * @return
	 */
	
	@Deprecated
	List<Topic> findByForum(Forum forum);

    @Deprecated
	PageBean getPageBeanByForum(int pageNum, int pageSize, Forum forum);

}
