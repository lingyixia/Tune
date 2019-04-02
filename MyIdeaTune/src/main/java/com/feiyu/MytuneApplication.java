package com.feiyu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

//@SpringBootApplication
//public class MytuneApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(MytuneApplication.class, args);
//    }
//}
@SpringBootApplication
public class MytuneApplication extends SpringBootServletInitializer {
    private static final Logger logger = LoggerFactory.getLogger(MytuneApplication.class);
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass()); }
}
