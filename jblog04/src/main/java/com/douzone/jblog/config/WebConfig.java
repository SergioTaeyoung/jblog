package com.douzone.jblog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.douzone.jblog.config.web.FileUploadConfig;
import com.douzone.jblog.config.web.MessageConfig;
import com.douzone.jblog.config.web.MvcConfig;
import com.douzone.jblog.config.web.SecurityConfig;

@Configuration
@EnableWebMvc
@ComponentScan({"com.douzone.jblog.controller"})
@Import({MvcConfig.class, SecurityConfig.class, MessageConfig.class, FileUploadConfig.class})
public class WebConfig {

}
