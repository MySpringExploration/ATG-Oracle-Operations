/**
 * 
 */
package org.iqra.operationsapp.repo;

import java.util.List;

import org.iqra.operationsapp.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Abdul
 * 22-Feb-2018
 * 
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>{
	
	List<UserInfo> findByEnabled(short enabled);
	
	List<UserInfo> deleteByLoginId(String loginId);
	UserInfo findByLoginId(String loginId);
	
}
