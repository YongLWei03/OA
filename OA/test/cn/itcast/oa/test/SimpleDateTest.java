package cn.itcast.oa.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class SimpleDateTest {
	@Test
	public void testSimpleDateFormat(){
		String pattern="/yyyy/MM/dd";
	//	String pattern="/yyyy/--MM/dd";
	//	String pattern="asgf/yyyy/MM/dd";//异常，格式不对
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(pattern);
		System.out.println(simpleDateFormat.format(new Date()));
	}
	
}
