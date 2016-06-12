package cn.itcast.oa.base;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import cn.itcast.oa.domain.User;
import cn.itcast.oa.service.DepartmentService;
import cn.itcast.oa.service.FormService;
import cn.itcast.oa.service.FormTamplateService;
import cn.itcast.oa.service.ForumService;
import cn.itcast.oa.service.ProcessDefinitionService;
import cn.itcast.oa.service.ReplyService;
import cn.itcast.oa.service.RoleService;
import cn.itcast.oa.service.TopicService;
import cn.itcast.oa.service.UserService;
import cn.itcast.oa.service.PrivilegeService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T>{
	
    //=============MoelDriven��֧��=======================
	protected T model;
	
	public BaseAction(){
	    try {
			//ͨ�������ȡmodel��ʵ������
			ParameterizedType pt=(ParameterizedType) this.getClass().getGenericSuperclass();
			Class<T>clazz=(Class<T>) pt.getActualTypeArguments()[0];
			//ͨ�����䴴��model��ʵ��
			model=clazz.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	public T getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	
	
	//=============serviceʵ��������=======================
	@Resource
	protected RoleService roleService;
	@Resource
	protected DepartmentService departmentService;
	@Resource
	protected UserService userService;
	@Resource
	protected PrivilegeService privilegeService;
	@Resource
	protected ForumService forumService;
	@Resource
	protected TopicService topicService;
	@Resource
	protected ReplyService replyService;
	@Resource
	protected ProcessDefinitionService processDefinitionService;
	@Resource
	protected FormTamplateService formTamplateService;
	@Resource
	protected FormService formService;
	
	//��ȡ��ǰ��¼���û�
	protected User getCurrentUser(){
		return (User)ActionContext.getContext().getSession().get("user");
	}
	
	//=================��ҳ���ղ���===================================
	    protected int pageNum=1;   //��ǰҳ
	    protected int pageSize=10;//ÿҳ��ʾ��������¼

		public int getPageNum() {
			return pageNum;
		}

		public void setPageNum(int pageNum) {
			this.pageNum = pageNum;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}

		//=================�ļ��ϴ�������ʱ��������ļ��н����Ϊ�ļ����ർ�´��ļ��е��ٶ�����===================================
		private SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");

		/**
		 * �����ϴ����ļ���ʹ��UUID��Ϊ�ļ������������ļ��洢��ȫ·��
		 * 
		 * @param upload
		 * @return
		 */
		protected String saveUploadFile(File upload) {
			String basePath = ServletActionContext.getServletContext().getRealPath("/WEB-INF/upload_files/"); // ���ؽ�����û��'/'
			String subPath = sdf.format(new Date());
			// ����ļ��в����ڣ��ʹ���
			File dir = new File(basePath + subPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			String path = basePath + subPath + UUID.randomUUID().toString(); // ʹ��UUID��Ϊ�ļ������Խ������������
			File destFile = new File(path);
			// �ƶ���Ŀ�ĵأ�return true if and only if the renaming succeeded; false otherwise
			// ���Ŀ���ļ����ھͻ�ʧ�ܷ���false.
			// ���Ŀ���ļ����ڵ��ļ��в����ڣ��ͻ�ʧ�ܷ���false.
			upload.renameTo(destFile);
			
			return path;
		}

}
