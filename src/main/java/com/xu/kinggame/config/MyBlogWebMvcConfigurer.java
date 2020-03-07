	package com.xu.kinggame.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.xu.kinggame.interceptor.AdminLoginInterceptor;

@Configuration
public class MyBlogWebMvcConfigurer implements WebMvcConfigurer {

	@Autowired
	private AdminLoginInterceptor adminLoginInterceptor;
	
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(adminLoginInterceptor).addPathPatterns("/admin/**").excludePathPatterns("/admin/login").excludePathPatterns("/admin/register").excludePathPatterns("/admin/dist/**").addPathPatterns("/admin/plugins/**");
	}
}
