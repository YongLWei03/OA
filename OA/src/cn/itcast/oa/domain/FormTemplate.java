package cn.itcast.oa.domain;

import java.util.Set;



public class FormTemplate {
	private Long id;
	private String name;
	private String processDefinitionKey;
	private String path;
	
	private Set<Form> forms;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}
	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Set<Form> getForms() {
		return forms;
	}
	public void setForms(Set<Form> forms) {
		this.forms = forms;
	}
	
	
}
