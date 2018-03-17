package org.iqra.operationsapp.service.impl;

import java.util.List;

import org.iqra.operationsapp.dao.DBOperationsDAO;
import org.iqra.operationsapp.dao.UserInfoDAO;
import org.iqra.operationsapp.entity.UserInfo;
import org.iqra.operationsapp.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
	
	//@Autowired
	private UserInfoDAO userInfoDAO;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public UserInfoServiceImpl() {
		super();
		logger.debug("Begin : UserInfoServiceImpl --> UserInfoServiceImpl() -->");
		
		ApplicationContext context =
				new ClassPathXmlApplicationContext("Spring-Module.xml");
		UserInfoDAO userInfoDAO = (UserInfoDAO)context.getBean("userInfoDAO");
		this.userInfoDAO = userInfoDAO;
		((ConfigurableApplicationContext)context).close();
		
		logger.debug("End : UserInfoServiceImpl --> UserInfoServiceImpl() -->");
	}

	@Override
	public boolean modifyUserInfo(UserInfo userInfo){
		
		try {
			return userInfoDAO.modifyUserInfo(userInfo);
		}catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		
	}

	@Override
	public boolean approveUser(String loginId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(String loginId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserInfo findByLoginId(String loginId) {
		
		return userInfoDAO.findByLoginId(loginId);
		
	}

	@Override
	public List<UserInfo> getAllUsers() {
		
		logger.debug("Begin : UserInfoServiceImpl --> getAllUsers() -->");
		
		return userInfoDAO.getAllUsers();
		
		
	}

	@Override
	public boolean rejectUser(String loginId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addUser(UserInfo userInfo) {
		// TODO Auto-generated method stub
		return false;
	}
} 