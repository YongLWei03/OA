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
	
    //=============MoelDriven的支持=======================
	protected T model;
	
	public BaseAction(){
	    try {
			//通过反射获取model真实的类型
			ParameterizedType pt=(ParameterizedType) this.getClass().getGenericSuperclass();
			Class<T>clazz=(Class<T>) pt.getActualTypeArguments()[0];
			//通过反射创建model的实例
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
	
	
	//=============service实例的声明=======================
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
	
	//获取当前登录的用户
	protected User getCurrentUser(){
		return (User)ActionContext.getContext().getSession().get("user");
	}
	
	//=================分页接收参数===================================
	    protected int pageNum=1;   //当前页
	    protected int pageSize=10;//每页显示多少条记录

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

		//=================文件上传（根据时间添加子文件夹解决因为文件过多导致打开文件夹的速度慢）===================================
		private SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");

		/**
		 * 保存上传的文件，使用UUID做为文件名，并返回文件存储的全路径
		 * 
		 * @param upload
		 * @return
		 */
		protected String saveUploadFile(File upload) {
			String basePath = ServletActionContext.getServletContext().getRealPath("/WEB-INF/upload_files/"); // 返回结果最后没有'/'
			String subPath = sdf.format(new Date());
			// 如果文件夹不存在，就创建
			File dir = new File(basePath + subPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			String path = basePath + subPath + UUID.randomUUID().toString(); // 使用UUID做为文件名，以解决重名的问题
			File destFile = new File(path);
			// 移动到目的地，return true if and only if the renaming succeeded; false otherwise
			// 如果目标文件存在就会失败返回false.
			// 如果目标文件所在的文件夹不存在，就会失败返回false.
			upload.renameTo(destFile);
			
			return path;
		}

}
