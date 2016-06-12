package cn.itcast.oa.service.impl;

import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.FormTemplate;
import cn.itcast.oa.service.FormTamplateService;

@Service("formTemplateService")
@Transactional
public class FormTemplateServiceImpl extends DaoSupportImpl<FormTemplate> implements FormTamplateService {

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		//删除数据库记录
		FormTemplate formTemplate=getById(id);
		getSession().delete(formTemplate);
		//删除文件
		File file=new File(formTemplate.getPath());
		if(file.exists()){
			file.delete(); 
		}
		super.delete(id);
	}
    
	
}
