package org.iqra.operationsapp.service;

import java.util.List;

import org.iqra.operationsapp.entity.UserInfo;
import org.springframework.security.access.annotation.Secured;


public interface UserInfoService {
     @Secured ({"ROLE_USER","ROLE_EDITUSER","ROLE_ADMIN"})
     //List<UserInfo> getAllPendingApprovalUsers();
     boolean modifyUserInfo(UserInfo userInfo);
     boolean approveUser(String loginId);
     boolean deleteUser(String loginId);
     boolean rejectUser(String loginId);
     boolean addUser(UserInfo userInfo);
     UserInfo findByLoginId(String loginId);
     List<UserInfo> getAllUsers();
} 