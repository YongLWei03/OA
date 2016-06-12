package cn.itcast.oa.view.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.jbpm.api.ProcessDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.FormTemplate;

@Controller
@Scope("prototype")
public class FormTemplateAction extends BaseAction<FormTemplate>{
	
	private InputStream inputStream;//下载获取的输入流
	private File upload;//上传的文件
	private String uploadFileName; //上传的文件的名称
	
    //获取列表
	public String list() throws Exception{
		List<FormTemplate> formTemplates=formTamplateService.findAll();
		ActionContext.getContext().put("formTemplates", formTemplates);
		return "list";
	}
	//删除
	public String delete() throws Exception{
        formTamplateService.delete(model.getId());
		return "toList";
	}
	//添加
	public String add() throws Exception{
        //保存上传的文件，得到路径		  
		String path=saveUploadFile(upload); 
		
		//创建新 对象，并设置属性（也可以使用model）
		model.setPath(path);     
		
		//保存  
		formTamplateService.save(model);
		return "toList";
	}
	//添加页面
	public String addUI() throws Exception{
        //准备回显的数据
		Collection<ProcessDefinition> processDefinitions=processDefinitionService.findAllLatestVersions();
		ActionContext.getContext().put("processDefinitions", processDefinitions);
		return "saveUI";
	}
	//修改
	public String edit() throws Exception{
		// 从数据库中获取原对象
		FormTemplate formTemplate=formTamplateService.getById(model.getId());

		// 设置要修改的属性	
		formTemplate.setName(model.getName());
		formTemplate.setProcessDefinitionKey(model.getProcessDefinitionKey());
		if(upload!=null){
			//删除老文件
			File file=new File(formTemplate.getPath());
            if(file.exists()){
            	file.delete();
            }
			//保存新文件(重新计算路径)
            String path=saveUploadFile(upload);
            formTemplate.setPath(path);    
		}
		// 更新	
		formTamplateService.update(formTemplate);
		return "toList";
	}
	//修改页面
	public String editUI() throws Exception{
		// 准备数据：最新的流程定义列表
		Collection<ProcessDefinition> processDefinitions=processDefinitionService.findAllLatestVersions();
		ActionContext.getContext().put("processDefinitions", processDefinitions);		
		// 准备回显的数据
		FormTemplate formTemplate=formTamplateService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(formTemplate);
		return "saveUI";
	}  

	//下载
	public String download() throws Exception{
		FormTemplate formTemplate=formTamplateService.getById(model.getId());
		inputStream=new FileInputStream(formTemplate.getPath());
		String fileName=URLEncoder.encode(formTemplate.getName(), "utf-8"); //注意：要进行编码
		ActionContext.getContext().put("fileName", fileName);
		return "download";        
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

   
}
