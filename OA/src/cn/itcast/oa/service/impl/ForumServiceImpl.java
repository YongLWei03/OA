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
		//����
		super.save(forum);//�־û��Ѿ���ID��
		//����position��ֵ
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
				.setFirstResult(0)//��ҳ����ʼλ��
				.setMaxResults(1)//ÿҳ������
				.uniqueResult();
		//������Ĳ����ƶ�
		if(otherForum==null){
			return ;
		}
		//����position��ֵ
		int temp=forum.getPosition();
		forum.setPosition(otherForum.getPosition());
		otherForum.setPosition(temp);
		
		//���µ����ݿ�(���Բ�д����Ϊ���������ǳ־û�״̬)
		
		getSession().update(forum);
		getSession().update(otherForum);
	}

	public void moveDown(Long id) {
		// TODO Auto-generated method stub
		
		Forum forum=getById(id);
		Forum otherForum=(Forum) getSession().createQuery(//				
				"FROM Forum f WHERE f.position>? ORDER BY f.position ASC ")//
				.setParameter(0, forum.getPosition())//
				.setFirstResult(0)//��ҳ����ʼλ��
				.setMaxResults(1)//ÿҳ������
				.uniqueResult();
		//������Ĳ����ƶ�
		if(otherForum==null){
			return ;
		}
		//����position��ֵ
		int temp=forum.getPosition();
		forum.setPosition(otherForum.getPosition());
		otherForum.setPosition(temp);
		
		//���µ����ݿ�(���Բ�д����Ϊ���������ǳ־û�״̬)
		getSession().update(forum);
		getSession().update(otherForum);
	}

}
