package com.ricoh.orders.config;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
            .headers()
            .frameOptions()
            .disable()
        .and()
        	.sessionManagement()
        	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .anyRequest().authenticated();
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
			.withUser("user").password("pass1234").roles("USER")
			.and()
			.withUser("admin").password("pass1234").roles("USER", "ADMIN");
	}
    
}