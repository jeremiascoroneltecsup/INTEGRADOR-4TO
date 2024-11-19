package com.redsocial.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dfck2kpjm",
                "api_key", "513737931672627",
                "api_secret", "v5kPpSY7UMmSKgF7Lj5KDIydzEE"));
        return cloudinary;
    }
}