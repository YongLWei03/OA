package cn.itcast.oa.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Order;

import org.junit.Test;

import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Topic;

import com.opensymphony.xwork2.ActionContext;

public class QueryHelperTest {
    private int orderBy=1;
    private int viewType=1;
    private Forum forum=new Forum();
    private boolean asc=false;
    
    
	@Test
	public void testQueryHelper() {
		QueryHelper queryHelper=new QueryHelper(Forum.class, "t")
		//��������
		.addCondition("t.forum=?", forum)
		.addCondition((viewType==1),"t.type=?", Topic.TYPE_BEST)//1:��ʾֻ��������
		//��������
		.addOrderByProperty((orderBy==1),"t.lastUpdateTime", asc)//1��ʾֻ��������ʱ������
		.addOrderByProperty((orderBy==2),"t.postTime", asc)//2:��ʾֻ�����ⷢ��ʱ������
		.addOrderByProperty((orderBy==3),"t.replyCount", asc)//3:��ʾֻ���ظ���������
		.addOrderByProperty((orderBy==0),"( CASE t.type  WHEN 2 THEN 2 ELSE 0 END )", asc)//0:��ʾĬ������
		.addOrderByProperty((orderBy==0),"t.lastUpdateTime", asc);
	    
		System.out.println(queryHelper.getListQueryHql());
	    System.out.println(queryHelper.getCountQueryHql());
	    System.out.println(queryHelper.getParameters());
	}

}
