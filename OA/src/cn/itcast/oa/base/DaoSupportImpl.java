package cn.itcast.oa.base;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.util.QueryHelper;



//@Transactional���Ա�����̳�
//@Transactional�Ը����������ķ�����Ч
//@Transactional����ǰ���������������з�private������Ч(��ȷ������protected,public ����Ч)
@Transactional
public abstract class DaoSupportImpl<T> implements DaoSupport<T> {

	@Resource
	private SessionFactory sessionFactory;
	private Class<T> clazz = null; 
	
	public DaoSupportImpl(){
        //ʹ�÷����ȡ����T����ʵ����
		ParameterizedType  pt=	(ParameterizedType) this.getClass().getGenericSuperclass();//�õ���ǰnew�Ķ���ĸ�����
		this.clazz=(Class<T>) pt.getActualTypeArguments()[0];//��ȡ��һ����������ʵ����
		System.out.println("----->"+clazz+"----canonicalName:"+clazz.getCanonicalName()+"----simpleName:"+clazz.getSimpleName()+"----name:"+clazz.getName());
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(T entity) {
		// TODO Auto-generated method stub
		getSession().save(entity);
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
		Object object = getById(id);
		if (object != null) {
			getSession().delete(object);
		}

	}

	public void update(T entity) {
		// TODO Auto-generated method stub
		getSession().update(entity);
	}

	@SuppressWarnings("unchecked")
	public T getById(Long id) {
		// TODO Auto-generated method stub
		if(id==null){
			return null;
		}else{	
			return (T) getSession().get(clazz, id);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> getByIds(Long[] ids) {
		// TODO Auto-generated method stub

		if(ids==null||ids.length==0){
			return Collections.EMPTY_LIST; //��Ϊnew������ArrayList����һ���ĳ���
		}else{
			return getSession().createQuery(//
					"FROM " + clazz.getSimpleName()+"  WHERE id IN (:ids)")//
					.setParameterList("ids", ids)//
					.list();
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		// TODO Auto-generated method stub
		return getSession().createQuery(// ע�ͷ�ֹformatʱ���ֱ��һ��
				"FROM " + clazz.getSimpleName())//
				.list();
	}
	@Deprecated
	public PageBean getPageBean(int pageNum, int pageSize, String hql, List<Object> parameters){
		// TODO Auto-generated method stub
		System.out.println("--------------->getPageBean");
		//��ѯ��ҳ�������б�
		Query listQuery=getSession().createQuery(hql);
		if(parameters!=null){
			for(int i=0;i<parameters.size();i++){
				listQuery.setParameter(i, parameters.get(i));
			}
		}
		listQuery.setFirstResult((pageNum-1)*pageSize);
		listQuery.setMaxResults(pageSize); 
		List records=listQuery.list();
		
		//��ѯ�ܼ�¼����
		Query countQuery=getSession().createQuery("SELECT COUNT(*) "+hql);
		if(parameters!=null){
			for(int i=0;i<parameters.size();i++){
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		Long count=(Long) countQuery.uniqueResult();
		return new PageBean(pageNum, pageSize, records, count.intValue());
		
	}
	
	public PageBean getPageBean(int pageNum, int pageSize, QueryHelper queryHelper){
		
		System.out.println("--------------->getPageBean(int pageNum, int pageSize, QueryHelper queryHelper)");
		//��ȡ�����б�
		List<Object> parameters=queryHelper.getParameters();  
		//��ѯ��ҳ�������б�
		Query listQuery=getSession().createQuery(queryHelper.getListQueryHql());
		if(parameters!=null){
			for(int i=0;i<parameters.size();i++){
				listQuery.setParameter(i, parameters.get(i));
			}
		}
		listQuery.setFirstResult((pageNum-1)*pageSize);
		listQuery.setMaxResults(pageSize); 
		List records=listQuery.list();
		
		//��ѯ�ܼ�¼����
		Query countQuery=getSession().createQuery(queryHelper.getCountQueryHql());
		if(parameters!=null){
			for(int i=0;i<parameters.size();i++){
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		Long count=(Long) countQuery.uniqueResult();
		return new PageBean(pageNum, pageSize, records, count.intValue());		
		
		
	}

}
