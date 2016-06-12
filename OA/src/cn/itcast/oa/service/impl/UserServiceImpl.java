package cn.itcast.oa.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.service.UserService;

@Service("userService") 
public class UserServiceImpl extends DaoSupportImpl<User> implements UserService{

	public User findByLoginNameAndPassword(String loginName, String password) {
		// TODO Auto-generated method stub
		//ʹ�������MD5ժҪ���жԱ�
		String md5Digest=DigestUtils.md5Hex(password);
		return (User) getSession().createQuery(
				"FROM User u WHERE u.loginName=? AND u.password=?")
				.setParameter(0, loginName)
				.setParameter(1, md5Digest)
				.uniqueResult();
	}

}
