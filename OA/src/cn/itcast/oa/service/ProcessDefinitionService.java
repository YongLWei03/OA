package cn.itcast.oa.service;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.jbpm.api.ProcessDefinition;

public interface ProcessDefinitionService {

	/**
	 * ��ѯ�������°汾�����̶���
	 * @return
	 */
	Collection<ProcessDefinition> findAllLatestVersions();

	/**
	 * ɾ��ָ��key���а汾�����̶���
	 * @param key
	 */
	void deleteByKey(String key);

	/**
	 * �������̶��壨zip�ķ�ʽ��
	 * @param zipInputStream
	 */
	void deployZip(ZipInputStream zipInputStream);

	/**
	 * ��ȡָ�����̶��������ͼ
	 * @param id
	 * @return
	 */
	InputStream getProcessImageResourceAsStream(String id);

}
