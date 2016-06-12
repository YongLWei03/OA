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
public class ProcessDefinitionAction extends BaseAction<Object> {// 由于没有对应实体，所以声明为Object防止报错
	private File upload;// 上传的文件名称
	// private String uploadFileName;//上传的文件的名称
	private InputStream inputStream;// 下载用的
	private String key;
	private String id;// ProcessDefinition的是String的id

	// 列表，显示的是所有流程定义（不同key）的最新版本
	public String list() {
		Collection<ProcessDefinition> processDefinitions = processDefinitionService
				.findAllLatestVersions();
		ActionContext.getContext()
				.put("processDefinitions", processDefinitions);
		return "list";
	}

	// 删除，指定key所有版本的流程定义都会被删除
	public String delete() throws UnsupportedEncodingException {
		key=new String (key.getBytes("iso-8859-1"),"utf-8");   
		processDefinitionService.deleteByKey(key);     
		return "toList";
	}

	// 添加页面
	public String addUI() {
		return "addUI";
	}

	// 添加（zip文件，里面有.png与jpdl.xml文件）
	public String add() throws Exception {
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(upload));
		processDefinitionService.deployZip(zipInputStream);
		return "toList";
	}

	// 查看流程图（XXX.png）
	public String showProcessImage() throws UnsupportedEncodingException {
		//id=new String (id.getBytes("iso-8859-1"),"utf-8");      
		id=URLDecoder.decode(id,"utf-8"); //进行第二次解码 
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
