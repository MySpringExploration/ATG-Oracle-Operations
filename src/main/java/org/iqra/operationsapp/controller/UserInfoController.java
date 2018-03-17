package org.iqra.operationsapp.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.iqra.operationsapp.entity.UserInfo;
import org.iqra.operationsapp.helper.PopulateDetailsHelper;
import org.iqra.operationsapp.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserInfoController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserInfoService userInfoService;

	//@Autowired
	//UserInfoRepository repository;
	
	@Autowired
	PopulateDetailsHelper populateDetailsHelper;
	
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String showLoginPage(ModelMap model, Principal principal) {
//		
//		if(principal==null) {
//			return "default";
//		}
//		
//		return "redirect:/welcome";
//			
//     }
//	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showWelcomeOrLoginPage(ModelMap model, Principal principal) {
		
		if(principal==null) {
			return "default";
		}
		
		return "redirect:/welcome";
			
     }
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginFormPage(@RequestParam(value = "error", required = false) String error, ModelMap model) {
		
		logger.debug("Begin : UserInfoController --> /login --> GET");
		
		if (error != null) {
			model.addAttribute("error", "Invalid username and password!");
		  }
		
		model.addAttribute("userInfo", new UserInfo());
		
		logger.debug("End : UserInfoController --> /login --> GET");
	    		return "custom-login";
     }
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, @RequestParam(value = "error", required = false) String error, ModelMap model) {
		
		logger.debug("Begin : UserInfoController --> /login --> POST");
		
		if (error != null) {
			model.addAttribute("error", "Invalid username and password!");
		  }
		
		logger.debug("End : UserInfoController --> /login --> POST");
		
		return "welcome";
		
     }
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String show403Page(HttpServletRequest request, @RequestParam(value = "error", required = false) String error, ModelMap model) {
		if (error != null) {
			model.addAttribute("errorMsg", error);
		  }
		
		model.addAttribute("userName", request.getSession().getAttribute("loginId").toString());
		
	    		return "403";
     }
	
	@RequestMapping(value = "/modify-user", method = RequestMethod.GET)
	public String showModifyUserPage(HttpServletRequest request, ModelMap model) {
		
//		UserInfo userInfo = repository.findByLoginId(request.getSession().getAttribute("loginId").toString());
		UserInfo userInfo = userInfoService.findByLoginId(request.getSession().getAttribute("loginId").toString());
		request.getSession().setAttribute("prevRole", userInfo.getRole());
		model.addAttribute("userInfo", userInfo);
		List<String> rolesList = populateDetailsHelper.getRoles();
		model.addAttribute("roles", rolesList);
		
		//return "redirect:/modify-user?loginId="+loginId;
		return "modify-user";
	}
	
	
	@RequestMapping(value = "/modify-user", method = RequestMethod.POST)
	public String showRegisterSuccessPage(HttpServletRequest request, ModelMap model, @Valid UserInfo userInfo, BindingResult result) {
		
		if (result.hasErrors()) {
			model.addAttribute("userInfo", userInfo);
			List<String> rolesList = populateDetailsHelper.getRoles();
			model.addAttribute("roles", rolesList);
			return "modify-user";
		}
		String prevRole = request.getSession().getAttribute("prevRole").toString();
		if(userInfo.getRole().equals(prevRole)) {
			model.addAttribute("successMsg", "Congratulations!! Updation Successful.");
		}else {
			model.addAttribute("successMsg", "Congratulations!! Updation Successful.<br>" + 
					"	Since request to modify role raised. An email will be sent to you once approved by Admin.");
			
			userInfo.setModifiedRole(userInfo.getRole());
			userInfo.setRole(prevRole);
		}
		try {
			if(userInfoService.modifyUserInfo(userInfo)) {
				request.getSession().setAttribute("fullName", userInfo.getFullName());
				return "user-success";
			}
		}catch (Exception ex) {
			model.addAttribute("successMsg", "");
			model.addAttribute("failureMsg",ex.getMessage());
		}
		
			
		return "user-success";
	}
	
	@RequestMapping(value = "/list-users", method = RequestMethod.GET)
	public String showTodos(ModelMap model) {
		
		//model.put("users", repository.findAll());
		model.put("users", userInfoService.getAllUsers());
		
		return "list-users";
	}
	
	@Transactional
	@RequestMapping(value = "/approve-user", method = RequestMethod.GET)
	public String updateUser(@RequestParam String loginId, ModelMap model) {
		
		//UserInfo userInfo = repository.findByLoginId(loginId);
		UserInfo userInfo = userInfoService.findByLoginId(loginId);
		if(userInfo.getEnabled()==(short)0) {
			userInfo.setEnabled((short)1);
		}
		if(userInfo.getModifiedRole()!=null) {
			userInfo.setRole(userInfo.getModifiedRole());
			userInfo.setModifiedRole(null);
		}
		userInfoService.approveUser(userInfo.getLoginId());
		return "redirect:/list-users";
	}
	
	@Transactional
	@RequestMapping(value = "/reject-user", method = RequestMethod.GET)
	public String rejectUser(@RequestParam String loginId, ModelMap model) {
		
		//UserInfo userInfo = repository.findByLoginId(loginId);
		UserInfo userInfo = userInfoService.findByLoginId(loginId);
		userInfo.setModifiedRole(null);
		//repository.save(userInfo);
		userInfoService.rejectUser(userInfo.getLoginId());
		return "redirect:/list-users";
	}
	
	@Transactional
	@RequestMapping(value = "/delete-user", method = RequestMethod.GET)
	public String deleteUser(@RequestParam String loginId, ModelMap model) {
		//model.put("users",repository.deleteByLoginId(loginId));
		userInfoService.deleteUser(loginId);
		return "redirect:/list-users";
	}
	
	@RequestMapping(value = "/error", method = RequestMethod.POST)
	public String showErrorPage(ModelMap model) {
	    
	    String errorMessage= "You are not authorized for the requested data.";
	    model.addAttribute("errorMsg", errorMessage);
	    return "403";
	    
        }
	
} 