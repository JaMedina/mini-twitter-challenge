package com.jorge.twitter;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;


public class MinitWitterAppWebXml extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.profiles("dev").showBanner(false).sources(MiniTwitterApp.class);
  }
}
