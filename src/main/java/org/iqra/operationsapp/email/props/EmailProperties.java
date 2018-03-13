/**
 * 
 */
package org.iqra.operationsapp.email.props;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Abdul
 * 05-Mar-2018
 * 
 */

@Component
@ConfigurationProperties("email")
public class EmailProperties {
	 
	        private String emailHost;
	        private String emailMessage;
	        private String toReciepients;
	        private String fromEmail;
	        private String cCedEmails;
	        private String emailPort;
	        private String username;
	        private String password;
	        
			public String getEmailHost() {
				return emailHost;
			}
			public void setEmailHost(String emailHost) {
				this.emailHost = emailHost;
			}
			public String getEmailMessage() {
				return emailMessage;
			}
			public void setEmailMessage(String emailMessage) {
				this.emailMessage = emailMessage;
			}
			
			public String getToReciepients() {
				return toReciepients;
			}
			public void setToReciepients(String toReciepients) {
				this.toReciepients = toReciepients;
			}
			public String getFromEmail() {
				return fromEmail;
			}
			public void setFromEmail(String fromEmail) {
				this.fromEmail = fromEmail;
			}
			public String getcCedEmails() {
				return cCedEmails;
			}
			public void setcCedEmails(String cCedEmails) {
				this.cCedEmails = cCedEmails;
			}
			public String getEmailPort() {
				return emailPort;
			}
			public void setEmailPort(String emailPort) {
				this.emailPort = emailPort;
			}
			public String getUsername() {
				return username;
			}
			public void setUsername(String username) {
				this.username = username;
			}
			public String getPassword() {
				return password;
			}
			public void setPassword(String password) {
				this.password = password;
			}
			
			
		
}
