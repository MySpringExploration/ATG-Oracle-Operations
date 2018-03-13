
package org.iqra.operationsapp.entity;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * @author Abdul
 * 24-Feb-2018
 * 
 */
@Component
public class DashboardInfo {
	
	public DashboardInfo() {
		super();
	}
	
	private List<String> applications;
	
	private String applicationName;
	
	private String errorMsg;
	
	private String successMsg;
	
	private String mailStatusMsg;
	
	private Map<String, String> serversStatusMap;
	
	public List<String> getApplications() {
		return applications;
	}

	public void setApplications(List<String> applications) {
		this.applications = applications;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getSuccessMsg() {
		return successMsg;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}

	

	public String getMailStatusMsg() {
		return mailStatusMsg;
	}

	public void setMailStatusMsg(String mailStatusMsg) {
		this.mailStatusMsg = mailStatusMsg;
	}

	public Map<String, String> getServersStatusMap() {
		return serversStatusMap;
	}

	public void setServersStatusMap(Map<String, String> serversStatusMap) {
		this.serversStatusMap = serversStatusMap;
	}
	
}
