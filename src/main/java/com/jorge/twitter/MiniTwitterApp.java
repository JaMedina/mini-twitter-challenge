package com.jorge.twitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class MiniTwitterApp {
  public static void main(String... args) {
    SpringApplication app = new SpringApplication(MiniTwitterApp.class);
    app.setShowBanner(false);
    app.setAdditionalProfiles("dev");
    app.run(args);
  }
}
