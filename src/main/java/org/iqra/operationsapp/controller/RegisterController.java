package org.iqra.operationsapp.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.iqra.operationsapp.entity.UserInfo;
import org.iqra.operationsapp.helper.PopulateDetailsHelper;
import org.iqra.operationsapp.repo.UserInfoRepository;
import org.iqra.operationsapp.util.BCryptEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Abdul
 * 21-Feb-2018
 * 
 */
@Controller
public class RegisterController {
	
	@Autowired
	UserInfoRepository repository;
	
	@Autowired
	BCryptEncoderUtil encoder;
	
	@Autowired
	PopulateDetailsHelper populateDetailsHelper;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegisterPage(HttpServletRequest request, ModelMap model) {

		model.addAttribute("userInfo", new UserInfo());
		List<String> rolesList = populateDetailsHelper.getRoles();
		model.addAttribute("roles", rolesList);
		
		return "register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String showRegisterSuccessPage(ModelMap model, @Valid UserInfo userInfo, BindingResult result) {
		
		if (result.hasErrors()) {
			List<String> rolesList = populateDetailsHelper.getRoles();
			model.addAttribute("roles", rolesList);
			return "register";
		}
		userInfo.setPassword(encoder.encrypt(userInfo.getPassword()));
		repository.save(userInfo);
		model.addAttribute("successMsg", "Congratulations!! Registration Successful.<br>" + 
				"	An email will be sent to you once approved by Admin.");
		return "user-success";
	}
	
	

}
