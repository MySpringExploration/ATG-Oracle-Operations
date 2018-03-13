/**
 * 
 */
package org.iqra.operationsapp.security;

import org.iqra.operationsapp.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Abdul
 * 22-Feb-2018
 * 
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomUserDetailsService userDetailsService;	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/register").permitAll()
		.antMatchers("/login").permitAll()
		.antMatchers("/").permitAll()
		.antMatchers("/default").permitAll()
		.antMatchers("/export*").hasAnyRole("ADMIN","USER","EDITUSER")
		.antMatchers("/welcome","/modify-user*","/reset-password*").hasAnyRole("ADMIN","USER","EDITUSER")
		.antMatchers("/methodInvocator","/componentValueSetter","/cacheInvalidation","/componentReportBuilder","/dbQueryPanel","/serverStatusDashboard").hasAnyRole("ADMIN","USER","EDITUSER")
		.antMatchers("/list-users","/approve-user*","/delete-user*","/reject-user*","/schemaConfigMngt","/dynAdminConfigMgmt").hasRole("ADMIN")
		.and().formLogin()  //login configuration
                .loginPage("/login")
                .failureUrl("/login?error")
                //.loginProcessingUrl("/app-login")
                .usernameParameter("loginId")
                .passwordParameter("password")
                .defaultSuccessUrl("/welcome", true)
		.and().logout()    //logout configuration
		.logoutUrl("/logout") 
		.logoutSuccessUrl("/login")
		.and().exceptionHandling() //exception handling configuration
		.accessDeniedPage("/403")
		.and()
		  .csrf();
	} 
    
	@Autowired
   	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
              auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
} 
