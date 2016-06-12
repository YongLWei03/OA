package cn.itcast.oa.service.impl;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.service.ProcessDefinitionService;

@Service("processDefinitionService")
@Transactional
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {
	@Resource
	private ProcessEngine processEngine;

	public Collection<ProcessDefinition> findAllLatestVersions() {
		// TODO Auto-generated method stub
		// ��ѯ���������̶��壬�����İ汾���������
		List<ProcessDefinition> allList = processEngine.getRepositoryService()//
				.createProcessDefinitionQuery()//
				.orderAsc(ProcessDefinitionQuery.PROPERTY_VERSION)//
				.list();
		// ���˳����°汾
		Map<String, ProcessDefinition> map = new HashMap<String, ProcessDefinition>();
		for (ProcessDefinition pd : allList) {
			map.put(pd.getKey(), pd);
		}
		return map.values();

	}

	public void deleteByKey(String key) {
		// TODO Auto-generated method stub
		// ��ѯ��ָ��key�����а汾�����̶���
		List<ProcessDefinition> list = processEngine.getRepositoryService()//
				.createProcessDefinitionQuery()//
				.processDefinitionKey(key)//
				.list();
		// ѭ��ɾ��
		for (ProcessDefinition pd : list) {
			processEngine.getRepositoryService().deleteDeploymentCascade(pd.getDeploymentId());
		}
	}

	public void deployZip(ZipInputStream zipInputStream) {
		// TODO Auto-generated method stub
		processEngine.getRepositoryService()//
				.createDeployment()//
				.addResourcesFromZipInputStream(zipInputStream)//
				.deploy();
	}

	public InputStream getProcessImageResourceAsStream(String id) {
		// TODO Auto-generated method stub
		// ��ѯ��ProcessDefinition
		ProcessDefinition pd = processEngine.getRepositoryService()//
				.createProcessDefinitionQuery()//
				.processDefinitionId(id)//
				.uniqueResult();
		// �õ�ͼƬ��Դ������
		return processEngine.getRepositoryService().getResourceAsStream(
				pd.getDeploymentId(), pd.getImageResourceName());

	}
}
