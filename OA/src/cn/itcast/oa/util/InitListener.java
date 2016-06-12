package cn.itcast.oa.util;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.application.Application;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.service.PrivilegeService;

//@Component>>>>>>>>>>>>��Ч
public class InitListener implements ServletContextListener{
   
//	@Resource 
//	private PrivilegeService privilegeService;>>>>>>>��Ч

	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		//��ȡ��������ص�Service����
		ApplicationContext ac=WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext());
		PrivilegeService privilegeService=(PrivilegeService) ac.getBean("privilegeService");
		
		//׼�����ݣ�topPrivileges
		List<Privilege>topPrivileges=privilegeService.findTopList();
		sce.getServletContext().setAttribute("topPrivileges", topPrivileges);
		
		//׼�����ݣ�allPrivilegeUrls
		Collection<String>allPrivilegeUrls=privilegeService.getAllPrivilegeUrls();
		sce.getServletContext().setAttribute("allPrivilegeUrls", allPrivilegeUrls);
		System.out.println("--------------->��׼������<----------------");
		
		//
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
}
