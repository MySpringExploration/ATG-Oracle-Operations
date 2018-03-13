package org.iqra.operationsapp.controller;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.iqra.operationsapp.dynadmin.service.DynAdminOperationsService;
import org.iqra.operationsapp.entity.DashboardInfo;
import org.iqra.operationsapp.entity.DynAdminInfo;
import org.iqra.operationsapp.helper.PopulateDetailsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Controller
public class DynAdminController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PopulateDetailsHelper populateDetailsHelper;
	
	@Autowired
	DynAdminOperationsService dynAdminOperationsServiceImpl;
	
	@RequestMapping(value = "/cacheInvalidation", method = RequestMethod.GET)
	public String showCacheInvalidationPage(ModelMap model) {
		
		logger.debug("Begin : DynAdminController --> /cacheInvalidation --> GET");
		
		DynAdminInfo dynAdminInfo = new DynAdminInfo();
		dynAdminInfo.setApplications(populateDetailsHelper.getApplicatoinsList());
		model.addAttribute("dynAdminInfo",dynAdminInfo);
		
		logger.debug("End : DynAdminController --> /cacheInvalidation --> GET");
		
		return "dyn-admin/cache-invalidation";
     
	}
	
	@RequestMapping(value = "/cacheInvalidation", method = RequestMethod.POST)
	public String applyCacheInvalidation(ModelMap model, @Valid DynAdminInfo dynAdminInfo) {
		
		logger.debug("Begin : DynAdminController --> /cacheInvalidation --> POST");
		
		DynAdminInfo resultDynAdminInfo = dynAdminOperationsServiceImpl.invalidateCache(dynAdminInfo);
		resultDynAdminInfo.setApplications(populateDetailsHelper.getApplicatoinsList());
		model.addAttribute("dynAdminInfo",resultDynAdminInfo);
		
		logger.debug("End : DynAdminController --> /cacheInvalidation --> POST");
		
			return "dyn-admin/cache-invalidation";
     }
	
	@RequestMapping(value = "/methodInvocator", method = RequestMethod.GET)
	public String showMethodInvocationPage(ModelMap model) {
		
		logger.debug("Begin : DynAdminController --> /methodInvocator --> GET");
		
		DynAdminInfo dynAdminInfo = new DynAdminInfo();
		dynAdminInfo.setApplications(populateDetailsHelper.getApplicatoinsList());
		model.addAttribute("dynAdminInfo",dynAdminInfo);
		
		logger.debug("End : DynAdminController --> /methodInvocator --> GET");
		
			return "dyn-admin/method-invocation";
     }
	
	@RequestMapping(value = "/methodInvocator", method = RequestMethod.POST)
	public String applyMethodInvocation(ModelMap model, @Valid DynAdminInfo dynAdminInfo) {
		
		
		logger.debug("Begin : DynAdminController --> /methodInvocator --> POST");
		
		DynAdminInfo resultDynAdminInfo = dynAdminOperationsServiceImpl.invokeMethod(dynAdminInfo);
		resultDynAdminInfo.setApplications(populateDetailsHelper.getApplicatoinsList());
		model.addAttribute("dynAdminInfo",resultDynAdminInfo);
		
		logger.debug("End : DynAdminController --> /methodInvocator --> POST");
		
			return "dyn-admin/method-invocation";
     }
	
	@RequestMapping(value = "/componentValueSetter", method = RequestMethod.GET)
	public String showComponentValueSetterPage(ModelMap model) {
		
		logger.debug("Begin : DynAdminController --> /componentValueSetter --> GET");
		
		DynAdminInfo dynAdminInfo = new DynAdminInfo();
		dynAdminInfo.setApplications(populateDetailsHelper.getApplicatoinsList());
		model.addAttribute("dynAdminInfo",dynAdminInfo);
		
		logger.debug("End : DynAdminController --> /componentValueSetter --> GET");
		
			return "dyn-admin/value-setter";
     }
	
	@RequestMapping(value = "/componentValueSetter", method = RequestMethod.POST)
	public String componentValueSetter(ModelMap model, @Valid DynAdminInfo dynAdminInfo ) {
		
		logger.debug("Begin : DynAdminController --> /componentValueSetter --> POST");
		
		DynAdminInfo resultDynAdminInfo = dynAdminOperationsServiceImpl.setComponentValues(dynAdminInfo);
		resultDynAdminInfo.setApplications(populateDetailsHelper.getApplicatoinsList());
		model.addAttribute("dynAdminInfo",resultDynAdminInfo);
		
		logger.debug("End : DynAdminController --> /componentValueSetter --> POST");
		
			return "dyn-admin/value-setter";
     }
	
	@RequestMapping(value = "/serverStatusDashboard", method = RequestMethod.GET)
	public String showDashboardPage(ModelMap model) {
		
		logger.debug("Begin : serverStatusDashboard --> /showDashboardPage --> GET");
		
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setApplications(populateDetailsHelper.getApplicatoinsList());
		model.addAttribute("dashboardInfo",dashboardInfo);
		
		logger.debug("End : serverStatusDashboard --> /showDashboardPage --> GET");
		
			return "dyn-admin/servers-dashboard";
     }
	
	@RequestMapping(value = "/serverStatusDashboard", method = RequestMethod.POST)
	public String serverStatusDashboardPage(ModelMap model, @Valid DashboardInfo dashboardInfo ) {
		
		logger.debug("Begin : serverStatusDashboard --> /showDashboardPage --> POST");
		
		DashboardInfo resultDashboardInfo = dynAdminOperationsServiceImpl.getServersStatus(dashboardInfo);
		resultDashboardInfo.setApplications(populateDetailsHelper.getApplicatoinsList());
		model.addAttribute("dashboardInfo",resultDashboardInfo);
		
		logger.debug("Begin : serverStatusDashboard --> /showDashboardPage --> POST");
		
			return "dyn-admin/servers-dashboard";
     }
	
	@RequestMapping(value = "/componentReportBuilder", method = RequestMethod.GET)
	public String showComponentReportGenPage(ModelMap model) {
		
		logger.debug("Begin : DynAdminController --> /componentReportBuilder --> GET");
		
		DynAdminInfo dynAdminInfo = new DynAdminInfo();
		dynAdminInfo.setApplications(populateDetailsHelper.getApplicatoinsList());
		model.addAttribute("dynAdminInfo",dynAdminInfo);
		
		logger.debug("End : DynAdminController --> /componentReportBuilder --> GET");
		
			return "dyn-admin/component-reportGen";
     }
	
	/*
	@RequestMapping(value = "/componentReportBuilder", method = RequestMethod.POST)
	public String generateComponentReport(ModelMap model, @Valid DynAdminInfo dynAdminInfo, HttpServletRequest request, HttpServletResponse response) {
		
		logger.debug("Begin : DynAdminController --> /componentReportBuilder --> POST");
		
		DynAdminInfo resultDynAdminInfo = dynAdminOperationsServiceImpl.getComponentValue(dynAdminInfo);
		resultDynAdminInfo.setApplications(populateDetailsHelper.getApplicatoinsList());
		model.addAttribute("dynAdminInfo",resultDynAdminInfo);
		
		logger.debug("End : DynAdminController --> /componentReportBuilder --> POST");
		
			return "dyn-admin/component-reportGen";
     }*/
	
	@RequestMapping(value = "/componentReportBuilder", method = RequestMethod.POST)
	public StreamingResponseBody generateComponentReport(HttpServletResponse response, @Valid DynAdminInfo dynAdminInfo) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"Report.html\"");
        
        //DynAdminInfo resultDynAdminInfo = dynAdminOperationsServiceImpl.getComponentValue(dynAdminInfo);
        DynAdminInfo resultDynAdminInfo = dynAdminOperationsServiceImpl.getComponentValueWithConcurrentThreads(dynAdminInfo);
		resultDynAdminInfo.setApplications(populateDetailsHelper.getApplicatoinsList());
		resultDynAdminInfo.setComponent(dynAdminInfo.getComponent());
		resultDynAdminInfo.setProperty(dynAdminInfo.getProperty());
        StringBuilder reportbuilder = new StringBuilder();
        for(String eachLine : resultDynAdminInfo.getReportList()) {
        		reportbuilder.append(eachLine);
        }
        InputStream inputStream = new ByteArrayInputStream(reportbuilder.toString().getBytes());

        return outputStream -> {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, nRead);
            }
        };
    }
	
		
} 