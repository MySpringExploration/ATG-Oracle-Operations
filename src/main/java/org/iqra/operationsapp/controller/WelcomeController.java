package org.iqra.operationsapp.controller;
/**
 * @author Abdul
 * 19-Feb-2018
 * 
 */
import javax.servlet.http.HttpServletRequest;

import org.iqra.operationsapp.entity.UserInfo;
import org.iqra.operationsapp.helper.PopulateDetailsHelper;
import org.iqra.operationsapp.repo.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WelcomeController {

	@Autowired
	private PopulateDetailsHelper populateDetailsHelper;
	
	@Autowired
	UserInfoRepository repository;
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String showWelcomePage(HttpServletRequest request, ModelMap model) {
		
		
		String loginId = populateDetailsHelper.getLoggedInUserName();
		model.put("comingSoonFeaturesLabel", populateDetailsHelper.getComingSoonFeaturesLabel());
		model.put("comingSoonFeaturesList", populateDetailsHelper.getComingSoonFeaturesList());
		model.put("futureFeaturesLabel", populateDetailsHelper.getFutureFeaturesLabel());
		model.put("futureFeaturesList", populateDetailsHelper.getFutureFeaturesList());
		UserInfo userInfo = repository.findByLoginId(loginId);
		request.getSession().setAttribute("fullName", userInfo.getFullName());
		request.getSession().setAttribute("loginId", loginId);
		return "welcome";
	}

	

}
