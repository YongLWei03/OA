package cn.itcast.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.dao.DepartmentDao;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.service.DepartmentService;

@Service("departmentService")
@Transactional
@SuppressWarnings("unchecked")
public class DepartmentServiceImpl extends DaoSupportImpl<Department> implements DepartmentService{

    @Resource
    private SessionFactory sessionFactory; //暂时将sessionFactory放这先
	

	public List<Department> findTopList() {
		// TODO Auto-generated method stub

		return sessionFactory.getCurrentSession()//
				.createQuery("FROM Department d where d.parent is NULL")//
				.list();
	}

	public List<Department> findChildren(Long parentId) {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession()//
				.createQuery("FROM Department d where d.parent.id=?")//
				.setParameter(0, parentId)//
				.list();

	}

}
