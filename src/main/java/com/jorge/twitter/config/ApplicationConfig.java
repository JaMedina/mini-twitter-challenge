package com.jorge.twitter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jorge.twitter.security.SecurityInterceptor;

@Configuration
public class ApplicationConfig extends WebMvcConfigurerAdapter {

  @Autowired
  private SecurityInterceptor securityInterceptor;


  @Override
  public void addInterceptors(InterceptorRegistry interceptorRegistry) {
    interceptorRegistry.addInterceptor(securityInterceptor).addPathPatterns("/rest/**");
  }
}
