package cn.itcast.oa.service;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.jbpm.api.ProcessDefinition;

public interface ProcessDefinitionService {

	/**
	 * 查询所有最新版本的流程定义
	 * @return
	 */
	Collection<ProcessDefinition> findAllLatestVersions();

	/**
	 * 删除指定key所有版本的流程定义
	 * @param key
	 */
	void deleteByKey(String key);

	/**
	 * 部署流程定义（zip的方式）
	 * @param zipInputStream
	 */
	void deployZip(ZipInputStream zipInputStream);

	/**
	 * 获取指定流程定义的流程图
	 * @param id
	 * @return
	 */
	InputStream getProcessImageResourceAsStream(String id);

}
