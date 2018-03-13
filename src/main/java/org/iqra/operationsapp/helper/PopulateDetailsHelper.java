package org.iqra.operationsapp.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.iqra.operationsapp.dynadmin.props.ATGApplicationsProps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class PopulateDetailsHelper {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ATGApplicationsProps aTGApplicationsProps;

	@Value("${features.comingsoon.label}")
	private String comingSoonFeaturesLabel;

	public String getComingSoonFeaturesLabel() {
		return comingSoonFeaturesLabel;
	}

	@Value("${features.comingsoon.list}")
	private String comingSoonFeaturesList;

	public List<String> getComingSoonFeaturesList() {
		return propertiesStringToList(comingSoonFeaturesList);
	}

	@Value("${features.future.label}")
	private String futureFeaturesLabel;

	public String getFutureFeaturesLabel() {
		return futureFeaturesLabel;
	}

	@Value("${features.future.list}")
	private String futureFeaturesList;

	public List<String> getFutureFeaturesList() {
		return propertiesStringToList(futureFeaturesList);
	}
	
	@Value("${dynAdminUserName}")
	private String dynAdminUser;

	public String getDynAdminUser() {
		return dynAdminUser;
	}

	@Value("${dynAdminPassword}")
	private String dynAdminPwd;
	
	public String getDynAdminPwd() {
		return dynAdminPwd;
	}

	
	private List<String> propertiesStringToList(String propertiesString) {
		List<String> propertiesArrayList = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(propertiesString, ",");
		while (st.hasMoreTokens()) {
			propertiesArrayList.add(st.nextToken());
		}
		return propertiesArrayList;
	}

	@Value("${application.roles}")
	private String roles;

	public List<String> getRoles() {

		return propertiesStringToList(roles);

	}

	public List<String> getApplicatoinsList() {

		List<ATGApplicationsProps.ServerGroup> serverGroups = aTGApplicationsProps.getApplications();

		List<String> applicationsList = new ArrayList<>();
		
		for (ATGApplicationsProps.ServerGroup serverGroup : serverGroups) {
			applicationsList.add(serverGroup.getName());
		}
		
		return applicationsList;
	}
	
	public ATGApplicationsProps.ServerGroup getServerGroup(String applicationName) {
		
		logger.debug("Begin : PopulateDetailsHelper --> getServerGroup() --> for Application :: "+applicationName);
		
		List<ATGApplicationsProps.ServerGroup> serverGroups = aTGApplicationsProps.getApplications();
		
		logger.debug("Begin : PopulateDetailsHelper --> getServerGroup() --> for Application :: "+applicationName);
		
		ATGApplicationsProps.ServerGroup serverGroup = new ATGApplicationsProps.ServerGroup();
		
		for (ATGApplicationsProps.ServerGroup sg : serverGroups) {
		
			if(sg.getName().equals(applicationName)) {
				serverGroup = sg;
			}
		}
		
		return serverGroup;
	}
	
	public List<String> generateServers(String servers, String ports)
	{
		List<String> retList = new ArrayList<String>();
		List<String> serverList = new ArrayList<String>();
		List<String> portList = new ArrayList<String>();
		StringTokenizer serverToken = new StringTokenizer(servers, ",");
		StringTokenizer portToken = new StringTokenizer(ports, ",");
		while (serverToken.hasMoreElements())
		{
			serverList.add(serverToken.nextElement().toString());
		}

		while (portToken.hasMoreElements())
		{
			portList.add(portToken.nextElement().toString());
		}

		for (int i = 0; i < serverList.size(); i++)
		{
			for (int j = 0; j < portList.size(); j++)
			{
				retList.add(serverList.get(i) + ":" + portList.get(j));
			}
		}

		return retList;
	}
	
	public String cleanseUpComponent(String component) {
		component=component.trim();
		if(component.charAt(component.length()-1) == '/')
		{
			component=component.substring(0,component.length()-1);
		}
		else
		{
			return component;
		}
		return cleanseUpComponent(component);
	}

	
	public String getLoggedInUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}

		return principal.toString();
	}

}