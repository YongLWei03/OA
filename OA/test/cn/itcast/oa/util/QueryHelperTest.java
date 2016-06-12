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
		//过滤条件
		.addCondition("t.forum=?", forum)
		.addCondition((viewType==1),"t.type=?", Topic.TYPE_BEST)//1:表示只看精华帖
		//排序条件
		.addOrderByProperty((orderBy==1),"t.lastUpdateTime", asc)//1表示只按最后更新时间排序
		.addOrderByProperty((orderBy==2),"t.postTime", asc)//2:表示只按主题发表时间排序
		.addOrderByProperty((orderBy==3),"t.replyCount", asc)//3:表示只按回复数量排序
		.addOrderByProperty((orderBy==0),"( CASE t.type  WHEN 2 THEN 2 ELSE 0 END )", asc)//0:表示默认排序
		.addOrderByProperty((orderBy==0),"t.lastUpdateTime", asc);
	    
		System.out.println(queryHelper.getListQueryHql());
	    System.out.println(queryHelper.getCountQueryHql());
	    System.out.println(queryHelper.getParameters());
	}

}
