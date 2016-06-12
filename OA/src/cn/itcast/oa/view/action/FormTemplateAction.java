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
	
	private InputStream inputStream;//���ػ�ȡ��������
	private File upload;//�ϴ����ļ�
	private String uploadFileName; //�ϴ����ļ�������
	
    //��ȡ�б�
	public String list() throws Exception{
		List<FormTemplate> formTemplates=formTamplateService.findAll();
		ActionContext.getContext().put("formTemplates", formTemplates);
		return "list";
	}
	//ɾ��
	public String delete() throws Exception{
        formTamplateService.delete(model.getId());
		return "toList";
	}
	//���
	public String add() throws Exception{
        //�����ϴ����ļ����õ�·��		  
		String path=saveUploadFile(upload); 
		
		//������ ���󣬲��������ԣ�Ҳ����ʹ��model��
		model.setPath(path);     
		
		//����  
		formTamplateService.save(model);
		return "toList";
	}
	//���ҳ��
	public String addUI() throws Exception{
        //׼�����Ե�����
		Collection<ProcessDefinition> processDefinitions=processDefinitionService.findAllLatestVersions();
		ActionContext.getContext().put("processDefinitions", processDefinitions);
		return "saveUI";
	}
	//�޸�
	public String edit() throws Exception{
		// �����ݿ��л�ȡԭ����
		FormTemplate formTemplate=formTamplateService.getById(model.getId());

		// ����Ҫ�޸ĵ�����	
		formTemplate.setName(model.getName());
		formTemplate.setProcessDefinitionKey(model.getProcessDefinitionKey());
		if(upload!=null){
			//ɾ�����ļ�
			File file=new File(formTemplate.getPath());
            if(file.exists()){
            	file.delete();
            }
			//�������ļ�(���¼���·��)
            String path=saveUploadFile(upload);
            formTemplate.setPath(path);    
		}
		// ����	
		formTamplateService.update(formTemplate);
		return "toList";
	}
	//�޸�ҳ��
	public String editUI() throws Exception{
		// ׼�����ݣ����µ����̶����б�
		Collection<ProcessDefinition> processDefinitions=processDefinitionService.findAllLatestVersions();
		ActionContext.getContext().put("processDefinitions", processDefinitions);		
		// ׼�����Ե�����
		FormTemplate formTemplate=formTamplateService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(formTemplate);
		return "saveUI";
	}  

	//����
	public String download() throws Exception{
		FormTemplate formTemplate=formTamplateService.getById(model.getId());
		inputStream=new FileInputStream(formTemplate.getPath());
		String fileName=URLEncoder.encode(formTemplate.getName(), "utf-8"); //ע�⣺Ҫ���б���
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
