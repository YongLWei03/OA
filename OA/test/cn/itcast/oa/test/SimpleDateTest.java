package cn.itcast.oa.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class SimpleDateTest {
	@Test
	public void testSimpleDateFormat(){
		String pattern="/yyyy/MM/dd";
	//	String pattern="/yyyy/--MM/dd";
	//	String pattern="asgf/yyyy/MM/dd";//�쳣����ʽ����
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(pattern);
		System.out.println(simpleDateFormat.format(new Date()));
	}
	
}
