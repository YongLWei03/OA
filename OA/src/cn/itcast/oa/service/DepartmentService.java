package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.domain.Department;

public interface DepartmentService extends DaoSupport<Department>{

//	List<Department> findAll();
//	
//	Department getById(Long id);
//
//	void delete(Long id);
//
//	void save(Department department);
//
//	void update(Department department);
//
	List<Department> findTopList();

	List<Department> findChildren(Long parentId);


}
