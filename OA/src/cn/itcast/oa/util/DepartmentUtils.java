package cn.itcast.oa.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.itcast.oa.domain.Department;

public class DepartmentUtils {
    
	/**
	 * �����������������еĲ��ű��������ŵ�ͬһ�������зŻأ������������е����ƶ��޸��ˣ��Ա�ʾ���
	 * @param topDepartments
	 * @return
	 */
	public static List<Department> getAllDepartments(List<Department> topDepartments) {
		// TODO Auto-generated method stub
		List<Department> departments=new ArrayList<Department>();
		walkDepartmentTreeList(topDepartments,"��",departments);
		return departments;
	}
	
    /**
     * ����������
     * @param topDepartments
     * @param prefix 
     */
	public static void walkDepartmentTreeList(Collection<Department> topDepartments,String prefix, List<Department> departments){
		
		for(Department top:topDepartments){
			//����
			Department copy=new Department();//ʹ�ø�������Ϊԭ������session��
			copy.setId(top.getId());
			copy.setName(prefix+top.getName());
			departments.add(copy);
			//����
			walkDepartmentTreeList(top.getChildren(), "��"+prefix, departments);//�ո�ʹ������ȫ�Ƿ��ŷ�ֹ��ҳ���и�ʽ��

		}
	}
  
}
