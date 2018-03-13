package org.iqra.operationsapp.dao.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.iqra.operationsapp.dao.UserInfoDAO;
import org.iqra.operationsapp.entity.UserInfo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserInfoDAOImpl implements UserInfoDAO {
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	@Transactional
	public UserInfo getActiveUser(String loginId) {
		UserInfo activeUserInfo = new UserInfo();
		short enabled = 1;
		List<?> list = entityManager.createQuery("SELECT u FROM UserInfo u WHERE loginId= :loginId and enabled= :enabled")
				.setParameter("loginId", loginId).setParameter("enabled", enabled).getResultList();
		if(!list.isEmpty()) {
			activeUserInfo = (UserInfo)list.get(0);
		}
		return activeUserInfo;
	}
	
	@Transactional
	public boolean modifyUserInfo(UserInfo userInfo) {
		try {
			Query query = entityManager
					.createQuery("UPDATE UserInfo u SET u.email = :email, u.role = :role, u.modifiedRole = :modifiedRole, u.fullName = :fullName "
					+ "WHERE u.loginId = :loginId");
					query.setParameter("email", userInfo.getEmail());
					query.setParameter("role", userInfo.getRole());
					query.setParameter("modifiedRole", userInfo.getModifiedRole());
					query.setParameter("fullName", userInfo.getFullName());
					query.setParameter("loginId", userInfo.getLoginId());
					int updateCount = query.executeUpdate();
					//updateTransaction.commit();
					if (updateCount > 0) {
						return true;
					}
					return false;
		}catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		
			
	}
	
	/*
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> getAllPendingApprovalUsers() {
		short enabled = 0;
		List<UserInfo> pendingUserslist = entityManager.createQuery("SELECT u FROM UserInfo u WHERE enabled=?")
				.setParameter(1, enabled).getResultList();
		return pendingUserslist;
	
	}*/
		
} 