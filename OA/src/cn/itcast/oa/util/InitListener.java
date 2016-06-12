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

//@Component>>>>>>>>>>>>无效
public class InitListener implements ServletContextListener{
   
//	@Resource 
//	private PrivilegeService privilegeService;>>>>>>>无效

	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		//获取容器与相关的Service对象
		ApplicationContext ac=WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext());
		PrivilegeService privilegeService=(PrivilegeService) ac.getBean("privilegeService");
		
		//准备数据：topPrivileges
		List<Privilege>topPrivileges=privilegeService.findTopList();
		sce.getServletContext().setAttribute("topPrivileges", topPrivileges);
		
		//准备数据：allPrivilegeUrls
		Collection<String>allPrivilegeUrls=privilegeService.getAllPrivilegeUrls();
		sce.getServletContext().setAttribute("allPrivilegeUrls", allPrivilegeUrls);
		System.out.println("--------------->已准备数据<----------------");
		
		//
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
}
