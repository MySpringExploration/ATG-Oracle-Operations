package org.iqra.operationsapp.dao;

import java.util.List;

import org.iqra.operationsapp.entity.UserInfo;

public interface UserInfoDAO {
	UserInfo getActiveUser(String loginId);
	boolean modifyUserInfo(UserInfo userInfo);
	//List<UserInfo> getAllPendingApprovalUsers();
} 