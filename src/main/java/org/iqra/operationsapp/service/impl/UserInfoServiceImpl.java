package org.iqra.operationsapp.service.impl;

import org.iqra.operationsapp.dao.UserInfoDAO;
import org.iqra.operationsapp.entity.UserInfo;
import org.iqra.operationsapp.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserInfoDAO userInfoDAO;
	
	@Override
	public boolean modifyUserInfo(UserInfo userInfo){
		try {
			return userInfoDAO.modifyUserInfo(userInfo);
		}catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		
	}
} 