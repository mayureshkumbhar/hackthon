package com.afour.hackthon.wiki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.afour.hackthon.wiki.commons.SimpleCORSFilter;

@SpringBootApplication
public class AfourWikiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AfourWikiApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public FilterRegistrationBean<SimpleCORSFilter> loggingFilter() {
		FilterRegistrationBean<SimpleCORSFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(corsFilter());
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}
	
	@Bean
	public SimpleCORSFilter corsFilter() {
		return new SimpleCORSFilter();
	}
}
