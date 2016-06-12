package cn.itcast.oa.util;

import cn.itcast.oa.domain.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CheckPrivilegeInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//��ȡ��Ϣ
		User user=(User) ActionContext.getContext().getSession().get("user");
		//���δ��¼,��ת����¼ҳ��
		String namespace=invocation.getProxy().getNamespace();
		String actionName=invocation.getProxy().getActionName();
		String privUrl=namespace+actionName;//��Ӧ��Ȩ��URL
		//����ѵ�¼,���ж�Ȩ��
		
		if(user==null){
			if(privUrl.startsWith("/user_login")){
				//�����ȥ��½���ͷ���
				return invocation.invoke(); 
			}else{
				//�������ȥ��½����ת����½ҳ��
				return "loginUI";
			}
		}else{
			
			if(user.hasPrivilegeByUrl(privUrl)){
				//�����Ȩ�ޣ��ͷ���
				return invocation.invoke();
			}else{
				//���û��Ȩ�ޣ���ת����ʾҳ��
				return "noPrivilegeError";
			}
			
		}
	}

}
