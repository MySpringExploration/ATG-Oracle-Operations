package org.iqra.operationsapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.iqra.operationsapp.dao.UserInfoDAO;
import org.iqra.operationsapp.ds.CustomDataSource;
import org.iqra.operationsapp.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserInfoDAOImpl implements UserInfoDAO {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public UserInfo getActiveUser(String loginId) {
		
		logger.debug("Begin : UserInfoDAOImpl --> getActiveUser() ");
		logger.debug("Login Attempted by User --> : "+loginId);
		
		String SQL_QUERY = "select * from users WHERE login = ?";
		UserInfo userInfo = new UserInfo();
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement pst = conn.prepareStatement( SQL_QUERY );
			pst.setString(1, loginId);
			ResultSet rs = pst.executeQuery();
			
			while ( rs.next() ) {
				if(rs.getShort("enabled")==1) {
					userInfo = new UserInfo();
					userInfo.setLoginId(rs.getString( "login" ));
					userInfo.setPassword(rs.getString( "password" ));
					userInfo.setFullName(rs.getString( "full_name" ));
					userInfo.setRole(rs.getString( "role" ));
					userInfo.setModifiedRole(rs.getString( "change_requested_role" ));
					userInfo.setEmail(rs.getString( "email" ));
					userInfo.setEnabled(rs.getShort( "enabled" ));
				}
			}
		}catch (SQLException e) {
			logger.error("SQLException caught in UserInfoDAOImpl --> : "+e.getMessage());
	        e.printStackTrace();
		   }catch (Exception e) {
				logger.error("Exception caught in UserInfoDAOImpl --> : "+e.getMessage());
		        e.printStackTrace();
			   }
		
		logger.debug("Logged In User --> : "+userInfo);
		
		logger.debug("End : UserInfoDAOImpl --> getActiveUser() ");
		return userInfo;
	}
	
	public boolean modifyUserInfo(UserInfo userInfo) {
		String SQL_QUERY = "UPDATE users SET email = ?, role = ?, change_requested_role = ?, fullName =? WHERE loginId = ?";
		boolean updateStatus = false;
		try {
			//CustomDataSource customDataSource = new CustomDataSource(populateDetailsHelper.getjDBCUrl(),populateDetailsHelper.getUserName(),populateDetailsHelper.getPassword());
			Connection conn = dataSource.getConnection();
			PreparedStatement pst = conn.prepareStatement( SQL_QUERY );
			pst.setString(1, userInfo.getEmail());
			pst.setString(2, userInfo.getRole());
			pst.setString(3, userInfo.getModifiedRole());
			pst.setString(5, userInfo.getLoginId());
			int result = pst.executeUpdate();
			if(result>0) {
				updateStatus = true;
			}
		}catch (SQLException e) {
	        e.printStackTrace();
		   }
		
		return updateStatus;
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
		
		logger.debug("Begin : UserInfoDAOImpl --> findByLoginId() ");
		//logger.debug("Login Attempted by User --> : "+loginId);
		
		String SQL_QUERY = "select * from users WHERE login = ?";
		UserInfo userInfo = new UserInfo();
		try {
			//CustomDataSource customDataSource = new CustomDataSource(populateDetailsHelper.getjDBCUrl(),populateDetailsHelper.getUserName(),populateDetailsHelper.getPassword());
			Connection conn = dataSource.getConnection();
			PreparedStatement pst = conn.prepareStatement( SQL_QUERY );
			pst.setString(1, loginId);
			ResultSet rs = pst.executeQuery();
			
			while ( rs.next() ) {
					userInfo = new UserInfo();
					userInfo.setLoginId(rs.getString( "login" ));
					userInfo.setFullName(rs.getString( "full_name" ));
					userInfo.setRole(rs.getString( "role" ));
					userInfo.setModifiedRole(rs.getString( "change_requested_role" ));
					userInfo.setEmail(rs.getString( "email" ));
					userInfo.setEnabled(rs.getShort( "enabled" ));
				
			}
		}catch (SQLException e) {
			logger.error("Exception caught in findByLoginId() --> : "+e.getMessage());
	        e.printStackTrace();
		   }
		
		//logger.debug("Logged In User --> : "+userInfo);
		
		logger.debug("End : UserInfoDAOImpl --> findByLoginId() ");
		return userInfo;
	}

	@Override
	public List<UserInfo> getAllUsers() {
		
		logger.debug("Begin : UserInfoDAOImpl --> getAllUsers() ");
		//logger.debug("Login Attempted by User --> : "+loginId);
		
		String SQL_QUERY = "select * from users";
		UserInfo userInfo;
		List<UserInfo> users = null;
		try {
			
			//CustomDataSource customDataSource = new CustomDataSource(populateDetailsHelper.getjDBCUrl(),populateDetailsHelper.getUserName(),populateDetailsHelper.getPassword());
			Connection conn = dataSource.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL_QUERY);
			users = new ArrayList<>();
			
			while ( rs.next() ) {
					userInfo = new UserInfo();
					userInfo.setLoginId(rs.getString( "login" ));
					userInfo.setFullName(rs.getString( "full_name" ));
					userInfo.setRole(rs.getString( "role" ));
					userInfo.setModifiedRole(rs.getString( "change_requested_role" ));
					userInfo.setEmail(rs.getString( "email" ));
					userInfo.setEnabled(rs.getShort( "enabled" ));
					users.add(userInfo);
				
			}
		}catch (SQLException e) {
			logger.error("Exception caught in getAllUsers() --> : "+e.getMessage());
	        e.printStackTrace();
		   }
		
		//logger.debug("Logged In User --> : "+userInfo);
		
		logger.debug("End : UserInfoDAOImpl --> getAllUsers() ");
		return users;
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

//	public UserInfo getActiveUser1(String loginId) {
//		UserInfo activeUserInfo = new UserInfo();
//		short enabled = 1;
//		List<?> list = entityManager
//				.createQuery("SELECT u FROM UserInfo u WHERE loginId= :loginId and enabled= :enabled")
//				.setParameter("loginId", loginId).setParameter("enabled", enabled).getResultList();
//		if (!list.isEmpty()) {
//			activeUserInfo = (UserInfo) list.get(0);
//		}
//		return activeUserInfo;
//	}
//
//	
//	public boolean modifyUserInfo(UserInfo userInfo) {
//		try {
//			Query query = entityManager.createQuery(
//					"UPDATE UserInfo u SET u.email = :email, u.role = :role, u.modifiedRole = :modifiedRole, u.fullName = :fullName "
//							+ "WHERE u.loginId = :loginId");
//			query.setParameter("email", userInfo.getEmail());
//			query.setParameter("role", userInfo.getRole());
//			query.setParameter("modifiedRole", userInfo.getModifiedRole());
//			query.setParameter("fullName", userInfo.getFullName());
//			query.setParameter("loginId", userInfo.getLoginId());
//			int updateCount = query.executeUpdate();
//			// updateTransaction.commit();
//			if (updateCount > 0) {
//				return true;
//			}
//			return false;
//		} catch (Exception ex) {
//			throw new RuntimeException(ex);
//		}
//
//	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<UserInfo> getAllPendingApprovalUsers() { short enabled
	 * = 0; List<UserInfo> pendingUserslist =
	 * entityManager.createQuery("SELECT u FROM UserInfo u WHERE enabled=?")
	 * .setParameter(1, enabled).getResultList(); return pendingUserslist;
	 * 
	 * }
	 */

}