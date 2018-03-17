package org.iqra.operationsapp.dao;


import java.util.List;

import org.iqra.operationsapp.entity.UserInfo;

public interface UserInfoDAO {
	UserInfo getActiveUser(String loginId);
	 boolean modifyUserInfo(UserInfo userInfo);
     boolean approveUser(String loginId);
     boolean deleteUser(String loginId);
     boolean rejectUser(String loginId);
     boolean addUser(UserInfo userInfo);
     UserInfo findByLoginId(String loginId);
     List<UserInfo> getAllUsers();
} 