package cn.itcast.oa.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class LogTest {
   private Log  log=LogFactory.getLog(this.getClass());
   
   @Test 
   public void test(){
	  log.debug("����debug��Ϣ");
	  log.info("����info��Ϣ"); 
	  log.warn("����warn��Ϣ"); 
	  log.error("����error��Ϣ"); 
	  log.fatal("����fatal��Ϣ"); 
	  
	  
	   
   }
}
