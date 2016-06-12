package cn.itcast.oa.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope(value="prototype")
public class TestAction extends ActionSupport{
    private TestService service;
    
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		service.saveTwoUser();
		return "success";
	}

	public TestService getService() {
		return service;
	}
    
	@Resource(name="testService")
	public void setService(TestService service) {
		this.service = service;
	}
	

	

}
