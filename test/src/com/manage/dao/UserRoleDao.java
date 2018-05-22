package com.manage.dao;

import com.manage.model.UserRole;
@Deprecated
public interface UserRoleDao {

	Integer save(UserRole userRole);

	Integer delete(Integer userId);

}
