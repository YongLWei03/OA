package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.service.ForumService;

@Service("forumService")
@Transactional
@SuppressWarnings("unchecked")
public class ForumServiceImpl extends DaoSupportImpl<Forum> implements ForumService{
	

	@Override
	public void save(Forum forum) {
		// TODO Auto-generated method stub
		//保存
		super.save(forum);//持久化已经有ID了
		//设置position的值
		forum.setPosition(forum.getId().intValue());
		
	}


	@Override
	public List<Forum> findAll() {
		// TODO Auto-generated method stub
		return getSession().createQuery(//
				"FROM Forum f ORDER BY f.position")//
				.list();
	}


	public void moveUp(Long id) {
		// TODO Auto-generated method stub
		Forum forum=getById(id);
		Forum otherForum=(Forum) getSession().createQuery(//				
				"FROM Forum f WHERE f.position<? ORDER BY f.position DESC ")//
				.setParameter(0, forum.getPosition())//
				.setFirstResult(0)//分页的起始位置
				.setMaxResults(1)//每页的数量
				.uniqueResult();
		//最上面的不能移动
		if(otherForum==null){
			return ;
		}
		//交换position的值
		int temp=forum.getPosition();
		forum.setPosition(otherForum.getPosition());
		otherForum.setPosition(temp);
		
		//更新到数据库(可以不写，因为对象现在是持久化状态)
		
		getSession().update(forum);
		getSession().update(otherForum);
	}

	public void moveDown(Long id) {
		// TODO Auto-generated method stub
		
		Forum forum=getById(id);
		Forum otherForum=(Forum) getSession().createQuery(//				
				"FROM Forum f WHERE f.position>? ORDER BY f.position ASC ")//
				.setParameter(0, forum.getPosition())//
				.setFirstResult(0)//分页的起始位置
				.setMaxResults(1)//每页的数量
				.uniqueResult();
		//最下面的不能移动
		if(otherForum==null){
			return ;
		}
		//交换position的值
		int temp=forum.getPosition();
		forum.setPosition(otherForum.getPosition());
		otherForum.setPosition(temp);
		
		//更新到数据库(可以不写，因为对象现在是持久化状态)
		getSession().update(forum);
		getSession().update(otherForum);
	}

}
