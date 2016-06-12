package cn.itcast.oa.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.dao.RoleDao;
import cn.itcast.oa.domain.Role;

@Repository("roleDao")
@Deprecated
public class RoleDaoImpl extends DaoSupportImpl<Role> implements RoleDao{

}
