package cn.itcast.oa.base;

import java.util.List;

import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.util.QueryHelper;

public interface DaoSupport<T> {

	/**
	 * ����ʵ��
	 * 
	 * @param entity
	 */
	void save(T entity);

	/**
	 * ɾ��ʵ��
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * ����ʵ��
	 * 
	 * @param entity
	 */
	void update(T entity);

	/**
	 * ��id��ѯ
	 * 
	 * @param id
	 * @return
	 */
	T getById(Long id);

	/**
	 * ��id��ѯ
	 * 
	 * @param ids
	 * @return
	 */
	List<T> getByIds(Long[] ids);

	/**
	 * ��ѯ����
	 * 
	 * @return
	 */
	List<T> findAll();
	
    /**
     * 
     * @param pageNum
     * @param pageSize
     * @param hql
     *           ��ѯ�����б��HQL
     * @param parameters
     *           �����б���HQL���ʺ�һһ��Ӧ
     * @return  
     */
	@Deprecated
	public PageBean getPageBean(int pageNum, int pageSize, String hql, List<Object> parameters);
	
	public PageBean getPageBean(int pageNum, int pageSize, QueryHelper queryHelper); 

}
