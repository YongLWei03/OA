package cn.itcast.oa.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.itcast.oa.domain.Department;

public class DepartmentUtils {
    
	/**
	 * 遍历部门树，把所有的部门便利出来放到同一个集合中放回，并且其中所有的名称都修改了，以表示层次
	 * @param topDepartments
	 * @return
	 */
	public static List<Department> getAllDepartments(List<Department> topDepartments) {
		// TODO Auto-generated method stub
		List<Department> departments=new ArrayList<Department>();
		walkDepartmentTreeList(topDepartments,"┣",departments);
		return departments;
	}
	
    /**
     * 遍历部门树
     * @param topDepartments
     * @param prefix 
     */
	public static void walkDepartmentTreeList(Collection<Department> topDepartments,String prefix, List<Department> departments){
		
		for(Department top:topDepartments){
			//顶点
			Department copy=new Department();//使用副本，因为原对象在session中
			copy.setId(top.getId());
			copy.setName(prefix+top.getName());
			departments.add(copy);
			//子树
			walkDepartmentTreeList(top.getChildren(), "　"+prefix, departments);//空格使用中文全角符号防止在页面中格式化

		}
	}
  
}
