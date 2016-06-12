package cn.itcast.oa.test;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.jbpm.api.ProcessEngine;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.oa.domain.User;

public class SpringTest {
    
	private ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
	
	
	@Test
	public void testSessionFactory(){
		SessionFactory sessionFactory=(SessionFactory) ac.getBean("sessionFactory");
		System.out.println(sessionFactory.openSession());
		
	}
	
	
	@Test
	public void testTransaction(){

		TestService testService=(TestService) ac.getBean("testService");
	   // testService.saveTwoUser();
		testService.saveUser25();
	}
	
	
	
	@Test
	public void testHibernate(){

		Configuration cfg=new Configuration();
		cfg.configure();
	   	ServiceRegistry sr=new StandardServiceRegistryBuilder()//
						         .applySettings(cfg.getProperties())//
						         .build();
	   	SessionFactory sf=cfg.buildSessionFactory(sr);
	   	
/*	   	Session session=sf.openSession();
	        session.beginTransaction();
	        session.save(new User());
	        session.getTransaction().commit();
	        session.close();
	     sf.close();*/
		
	}
	
	
	@Test
	public void testProcessEngine(){
		ProcessEngine processEngine=(ProcessEngine)ac.getBean("processEngine");
		System.out.println(processEngine);
	}
}
