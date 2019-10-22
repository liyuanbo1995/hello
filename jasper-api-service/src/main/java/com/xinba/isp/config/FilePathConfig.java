package com.xinba.isp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class FilePathConfig implements WebMvcConfigurer {
    @Value("${file.path}")
    private String path;

    @Value("${file.address")
    private String address;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler(path).addResourceLocations("file"+"address");
    }
}

