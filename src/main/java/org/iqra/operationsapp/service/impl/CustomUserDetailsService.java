package org.iqra.operationsapp.service.impl;

import java.util.Arrays;
import java.util.Collection;

import org.iqra.operationsapp.dao.UserInfoDAO;
import org.iqra.operationsapp.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserInfoDAO userInfoDAO;

	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		UserInfo activeUserInfo = userInfoDAO.getActiveUser(loginId);
		GrantedAuthority authority = new SimpleGrantedAuthority(activeUserInfo.getRole());
		UserDetails userDetails = (UserDetails) new User(activeUserInfo.getLoginId(), activeUserInfo.getPassword(),
				Arrays.asList(authority));
		return userDetails;
	}

	public boolean hasRole(String role) {
		boolean hasRole = false;
		UserDetails userDetails = getUserDetails();
		if (userDetails != null) {
			Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) userDetails.getAuthorities();
			if (isRolePresent(authorities, role)) {
				hasRole = true;
			}
		}
		return hasRole;
	}

	protected UserDetails getUserDetails() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		return userDetails;
	}

	private boolean isRolePresent(Collection<GrantedAuthority> authorities, String role) {
		boolean isRolePresent = false;
		for (GrantedAuthority grantedAuthority : authorities) {
			isRolePresent = grantedAuthority.getAuthority().equals(role);
			if (isRolePresent)
				break;
		}
		return isRolePresent;
	}
}