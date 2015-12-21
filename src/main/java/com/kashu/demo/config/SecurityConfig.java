package com.kashu.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath:security-context.xml"})
public class SecurityConfig {

}
