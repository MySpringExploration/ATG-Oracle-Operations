
package org.iqra.operationsapp.entity;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author Abdul
 * 24-Feb-2018
 * 
 */
@Component
public class DynAdminInfo {
	
	public DynAdminInfo() {
		super();
	}
	
	private List<String> applications;
	
	private String applicationName;
	
	private String component;
	
	private String property; 
	
	private String repository;
	
	private String methodName;
	
	private String newValue;
	
	private String cmDetails;
	
	private String saveToFile;
	
	private String parallelExecution;
	
	private int parallelThreadCount;

	private String errorMsg;
	
	private String successMsg;
	
	private String mailStatusMsg;
	
	private List<String> reportList;
	
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

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getCmDetails() {
		return cmDetails;
	}

	public void setCmDetails(String cmDetails) {
		this.cmDetails = cmDetails;
	}

	public String getSaveToFile() {
		return saveToFile;
	}

	public void setSaveToFile(String saveToFile) {
		this.saveToFile = saveToFile;
	}

	public String getParallelExecution() {
		return parallelExecution;
	}

	public void setParallelExecution(String parallelExecution) {
		this.parallelExecution = parallelExecution;
	}

	public int getParallelThreadCount() {
		return parallelThreadCount;
	}

	public void setParallelThreadCount(int parallelThreadCount) {
		this.parallelThreadCount = parallelThreadCount;
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

	public List<String> getReportList() {
		return reportList;
	}

	public void setReportList(List<String> reportList) {
		this.reportList = reportList;
	}

	public String getMailStatusMsg() {
		return mailStatusMsg;
	}

	public void setMailStatusMsg(String mailStatusMsg) {
		this.mailStatusMsg = mailStatusMsg;
	}
	
}
