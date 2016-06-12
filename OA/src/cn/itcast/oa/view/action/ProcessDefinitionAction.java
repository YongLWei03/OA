package cn.itcast.oa.view.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.jbpm.api.ProcessDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseAction;

@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends BaseAction<Object> {// ����û�ж�Ӧʵ�壬��������ΪObject��ֹ����
	private File upload;// �ϴ����ļ�����
	// private String uploadFileName;//�ϴ����ļ�������
	private InputStream inputStream;// �����õ�
	private String key;
	private String id;// ProcessDefinition����String��id

	// �б���ʾ�����������̶��壨��ͬkey�������°汾
	public String list() {
		Collection<ProcessDefinition> processDefinitions = processDefinitionService
				.findAllLatestVersions();
		ActionContext.getContext()
				.put("processDefinitions", processDefinitions);
		return "list";
	}

	// ɾ����ָ��key���а汾�����̶��嶼�ᱻɾ��
	public String delete() throws UnsupportedEncodingException {
		key=new String (key.getBytes("iso-8859-1"),"utf-8");   
		processDefinitionService.deleteByKey(key);     
		return "toList";
	}

	// ���ҳ��
	public String addUI() {
		return "addUI";
	}

	// ��ӣ�zip�ļ���������.png��jpdl.xml�ļ���
	public String add() throws Exception {
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(upload));
		processDefinitionService.deployZip(zipInputStream);
		return "toList";
	}

	// �鿴����ͼ��XXX.png��
	public String showProcessImage() throws UnsupportedEncodingException {
		//id=new String (id.getBytes("iso-8859-1"),"utf-8");      
		id=URLDecoder.decode(id,"utf-8"); //���еڶ��ν��� 
		inputStream = processDefinitionService
				.getProcessImageResourceAsStream(id);
		return "showProcessImage";// stream
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
