package org.iqra.operationsapp.security;
import org.iqra.operationsapp.util.DecryptPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class SecurityConfigurationTemp{
	
	@Value("${ldap.urls}")
	private String ldapUrls;
	
	@Value("${ldap.base.dn}")
	private String ldapBaseDn;
	
	@Value("${ldap.username}")
	private String ldapSecurityPrincipal;
	
	@Value("${ldap.password}")
	private String ldapPrincipalPassword;
	
	@Value("${ldap.user.dn.pattern}")
	private String ldapUserDnPattern;
	
	@Value("${ldap.enabled}")
	private String ldapEnabled;
	
	//Create User - in28Minutes/dummy
//	@Autowired
//    public void configureGlobalSecurity(AuthenticationManagerBuilder auth)
//            throws Exception {
		//auth.ldapAuthentication();
//		auth.inMemoryAuthentication().withUser("Abdul.Muhaimin").password("password").roles("USER", "ADMIN");
//		auth.inMemoryAuthentication().withUser("Abdul.Shaik").password("password").roles("ADMIN");
//		auth.inMemoryAuthentication().withUser("in28Minutes").password("dummy").roles("USER");
//    }
	
//	@Autowired
//	DecryptPassword decryptPassword;
	
//	@Override
   // protected void configure(HttpSecurity http) throws Exception {
     //   http.authorizeRequests().antMatchers("/login", "/h2-console/**", "/register").permitAll()
       //         .antMatchers("/", "/*todo*/**").access("hasRole('USER') or hasRole('ADMIN')").and()
         //       .formLogin();
        
       // http.csrf().disable();
       // http.headers().frameOptions().disable();
    //}
//	@SuppressWarnings("deprecation")
//	@Bean
	//	public static NoOpPasswordEncoder passwordEncoder() {
	//	return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//	}
	
	/*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		if(Boolean.parseBoolean(ldapEnabled)) {
			auth
				.ldapAuthentication()
				.contextSource()
					.url(ldapUrls + ldapBaseDn)
						.managerDn(ldapSecurityPrincipal)
						.managerPassword(decryptPassword.decrypt(ldapPrincipalPassword))
						
					.and()
						.userDnPatterns(ldapUserDnPattern);
		} else {
	        auth
	        .inMemoryAuthentication()
	            .withUser("user").password("password").roles("USER")
	            .and()
	            .withUser("admin").password("admin").roles("ADMIN");
		}
	}*/
}
