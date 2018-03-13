/**
 * 
 */
package org.iqra.operationsapp.dynadmin.props;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Abdul
 * 05-Mar-2018
 * 
 */

@Component
@ConfigurationProperties("dynadmin")
public class ATGApplicationsProps {
	
	 private List<ServerGroup> applications = new ArrayList<>();
	
	public List<ServerGroup> getApplications() {
		return applications;
	}

	public void setApplications(List<ServerGroup> applications) {
		this.applications = applications;
	}

	public static class ServerGroup {
	        private String name;
	        private String hosts;
	        private String ports;
	        
			
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getHosts() {
				return hosts;
			}
			public void setHosts(String hosts) {
				this.hosts = hosts;
			}
			public String getPorts() {
				return ports;
			}
			public void setPorts(String ports) {
				this.ports = ports;
			}
			
			@Override
			public String toString() {
				return "ServerGroup [name=" + name + ", hosts=" + hosts + ", ports=" + ports + "]";
			}
			
	    }
}
