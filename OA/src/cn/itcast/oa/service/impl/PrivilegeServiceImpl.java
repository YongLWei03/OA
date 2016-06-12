package cn.itcast.oa.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.service.PrivilegeService;

@Service("privilegeService")
@Transactional
public class PrivilegeServiceImpl extends DaoSupportImpl<Privilege> implements PrivilegeService {

	public List<Privilege> findTopList() {
		// TODO Auto-generated method stub
		return getSession().createQuery(//				
				"FROM Privilege p WHERE p.parent IS NULL")//
				.list();
	}

	public Collection<String> getAllPrivilegeUrls() {
		// TODO Auto-generated method stub
		return getSession().createQuery(//				
				"SELECT DISTINCT p.url FROM Privilege p WHERE p.parent IS NOT NULL")//
				.list();
	}

}
