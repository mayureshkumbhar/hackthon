package com.afour.hackthon.wiki.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.afour.hackthon.wiki.commons.JWTFilter;

@Configuration
@EnableOAuth2Sso
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JWTFilter customFilter = new JWTFilter();
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
        .csrf().disable()
        .antMatcher("/**").authorizeRequests()
		.anyRequest().authenticated() // comment this line if you dont want google security and userprofile is in Mongo 
		.and().logout().permitAll();
	}
}
