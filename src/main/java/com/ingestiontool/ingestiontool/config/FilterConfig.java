package com.ingestiontool.ingestiontool.config;

import com.ingestiontool.ingestiontool.filter.JwtAuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<com.ingestiontool.ingestiontool.filter.JwtAuthFilter> jwtFilter() {
        FilterRegistrationBean<JwtAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthFilter());
        registrationBean.addUrlPatterns("/ingestion/*"); // protect these endpoints
        return registrationBean;
    }
}
