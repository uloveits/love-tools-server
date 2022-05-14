package com.uloveits.wx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.wf.jwtp.configuration.EnableJwtPermission;
/**
 * @author:lyrics
 */
@EnableJwtPermission
@SpringBootApplication
public class EasyWebApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EasyWebApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(EasyWebApplication.class, args);
    }
}
