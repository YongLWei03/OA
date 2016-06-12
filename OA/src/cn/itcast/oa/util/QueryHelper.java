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
	 * ����From�Ӿ�
	 * @param clazz
	 * @param alias
	 */
    public QueryHelper(Class clazz,String alias){
    	fromClause=" FROM "+clazz.getSimpleName()+" "+alias;
    }
    /**
     * ƴ��Where�Ӿ�
     * @param condition
     * @param objects
     */
    public QueryHelper addCondition(String condition,Object... params ){//objects�����������
    	//ƴ��
    	if(whereClause.length()==0){
       	  whereClause+=" WHERE "+condition;
 
    	}else{
       	  whereClause+=" AND "+condition;
    	}
    	//����
    	 if(params!=null){
    		 for(Object p:params){
    			 parameters.add(p);
    		 }
    	 }
    	 return this;
    }
    /**
     * �����һ������Ϊtrue��ƴ��
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
     * ƴ��orderBy�Ӿ�
     * @param propertyName
     *        ���������������
     * @param asc
     *        true��ʾ����false��ʾ����
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
     * ���appendΪtrue��ƴ��
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
     * ��ȡ���ɵ����ڲ�ѯ�����б��HQL���
     * @return
     */
    public String getListQueryHql(){
    	return fromClause+whereClause+orderByClause;
    }
    /**
     * ��ȡ���ɵ����ڲ�ѯ�ܼ�¼����HQL���
     * @return
     */
    public String getCountQueryHql(){
    	return " SELECT COUNT(*) "+fromClause+ whereClause;
    }
    
    /**
     * ��ȡHQL�еĲ����б�
     * @return
     */
    public List<Object> getParameters(){
    	return parameters;
    }
      
    public void preparePageBean(DaoSupport<?>service,int pageNum,int pageSize){
		PageBean pageBean=service.getPageBean(pageNum, pageSize,this);
		ActionContext.getContext().getValueStack().push(pageBean);//����ֵջ�У����ø�ԭ����ҳ������	
    }

}
