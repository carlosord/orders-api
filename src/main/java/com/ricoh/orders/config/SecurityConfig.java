package com.ricoh.orders.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${spring.security.user}")
	private String user;
	
    @Value("${spring.security.user.password}")
	private String user_password;

	@Value("${spring.security.admin}")
	private String admin;

	@Value("${spring.security.admin.password}")
	private String admin_password;

	@Value("${spring.security.role_user}")
	private String role_user;

	@Value("${spring.security.role_admin}")
	private String role_admin;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .headers().frameOptions().disable()
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().authorizeRequests().anyRequest().authenticated();
    }
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//			.httpBasic().and()
//			.csrf().disable()
//	        .headers().frameOptions().disable()
//			.and().authorizeRequests()
//			.antMatchers("/order/**", "/article/**").access("hasRole('USER')")
//			.anyRequest().authenticated();
//	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers(
					"/v2/api-docs",
					"/configuration/ui",
					"/swagger-resources",
					"/configuration/security",
					"/swagger-ui.html",
					"/webjars/**");
	}
	
	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser(user).password(user_password).roles(role_user)
			.and()
			.withUser(admin).password(admin_password).roles(role_user, role_admin);
	}
    
}