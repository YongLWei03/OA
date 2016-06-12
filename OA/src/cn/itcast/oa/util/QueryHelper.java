package cn.itcast.oa.util;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.domain.PageBean;

import com.opensymphony.xwork2.ActionContext;

public class QueryHelper {
	private String fromClause;
	private String whereClause="";
	private String orderByClause="";
	private List<Object>parameters=new ArrayList<Object>();
	
	/**
	 * 生成From子句
	 * @param clazz
	 * @param alias
	 */
    public QueryHelper(Class clazz,String alias){
    	fromClause=" FROM "+clazz.getSimpleName()+" "+alias;
    }
    /**
     * 拼接Where子句
     * @param condition
     * @param objects
     */
    public QueryHelper addCondition(String condition,Object... params ){//objects存放在数组中
    	//拼接
    	if(whereClause.length()==0){
       	  whereClause+=" WHERE "+condition;
 
    	}else{
       	  whereClause+=" AND "+condition;
    	}
    	//参数
    	 if(params!=null){
    		 for(Object p:params){
    			 parameters.add(p);
    		 }
    	 }
    	 return this;
    }
    /**
     * 如果第一个参数为true就拼接
     * @param append
     * @param condition
     * @param params
     */
    public QueryHelper addCondition(boolean append,String condition,Object... params ){
    	if(append){
    		addCondition(condition, params);
    	}
    	return this;
    }
    
    /**
     * 拼接orderBy子句
     * @param propertyName
     *        参与排序的属性名
     * @param asc
     *        true表示升序，false表示降序
     */
    public QueryHelper addOrderByProperty(String propertyName,boolean asc){
    	if(orderByClause.length()==0){
    		orderByClause+=" ORDER BY "+propertyName+(asc?" ASC ":" DESC ");
    	}else{
    		orderByClause+=" , "+propertyName+(asc?" ASC ":" DESC ");
    	}
    	return  this;
    }
    
    /**
     * 如果append为true就拼接
     * @param append
     * @param propertyName
     * @param asc
     */
    public QueryHelper addOrderByProperty(boolean append,String propertyName,boolean asc){
    	if(append){
    		addOrderByProperty(propertyName, asc);
    	}
    	return this;
    	
    }
    
    /**
     * 获取生成的用于查询数据列表的HQL语句
     * @return
     */
    public String getListQueryHql(){
    	return fromClause+whereClause+orderByClause;
    }
    /**
     * 获取生成的用于查询总记录数的HQL语句
     * @return
     */
    public String getCountQueryHql(){
    	return " SELECT COUNT(*) "+fromClause+ whereClause;
    }
    
    /**
     * 获取HQL中的参数列表
     * @return
     */
    public List<Object> getParameters(){
    	return parameters;
    }
      
    public void preparePageBean(DaoSupport<?>service,int pageNum,int pageSize){
		PageBean pageBean=service.getPageBean(pageNum, pageSize,this);
		ActionContext.getContext().getValueStack().push(pageBean);//放在值栈中，不用改原来的页面数据	
    }

}
