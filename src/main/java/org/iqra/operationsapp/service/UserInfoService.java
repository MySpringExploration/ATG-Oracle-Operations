package org.iqra.operationsapp.service;

import org.iqra.operationsapp.entity.UserInfo;
import org.springframework.security.access.annotation.Secured;


public interface UserInfoService {
     @Secured ({"ROLE_USER","ROLE_EDITUSER","ROLE_ADMIN"})
     //List<UserInfo> getAllPendingApprovalUsers();
     boolean modifyUserInfo(UserInfo userInfo);
} 