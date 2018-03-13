package org.iqra.operationsapp.entity;

/**
 * @author Abdul
 * 21-Feb-2018
 * 
 */
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Entity
@Table(name="users")
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name="login")
	@Size(min=4, message="Your name should be minimum of 4 characters.")
	private String loginId;
	
	@Column(name="password")
	@Size(min=5, message="Your Password should be a minimum of 5 characters.")
	private String password;

	@NotNull(message="Please select a role type")
	@Column(name="role")	
	private String role;
	
	@Column(name="change_requested_role")	
	private String modifiedRole;
	
	@Column(name="full_name")	
	private String fullName;
	
	@Column(name="email")
	@Pattern(regexp=".+@.+\\..+", message="Please enter valid email address!")
	private String email;
	
	@Column(name="enabled")	
	private short enabled;
	
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getFullName() {
		return fullName;
	}
	public String getModifiedRole() {
		return modifiedRole;
	}
	public void setModifiedRole(String modifiedRole) {
		this.modifiedRole = modifiedRole;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public short getEnabled() {
		return enabled;
	}
	public void setEnabled(short enabled) {
		this.enabled = enabled;
	}
} 