package cn.itcast.oa.test;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.domain.User;

@Service("testService")
public class TestService {

	private SessionFactory sessionFactory;



	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


    @Resource(name="sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
    
	@Transactional  
	public void saveTwoUser() {

		Session session = sessionFactory.getCurrentSession();
		session.save(new User());
	    //int a = 1 / 0;
		session.save(new User());
	}
	
	@Transactional  
	public void saveUser25() {

		Session session = sessionFactory.getCurrentSession();
		for(int i=0;i<25;i++){
			User user=new User();
			user.setName("u"+i);
			session.save(user);
		}

	}


}
