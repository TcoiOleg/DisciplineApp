package com.disciplineapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * OLTS on 16.05.2017.
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("user");
        registry.addViewController("/user").setViewName("user");
        registry.addViewController("/habit").setViewName("all-habits");
        registry.addViewController("/edit").setViewName("edit-user");
        registry.addViewController("/user-habit").setViewName("user-habits");
        registry.addViewController("/challenge").setViewName("challenges");
        registry.addViewController("/summary").setViewName("summary");
     }
}