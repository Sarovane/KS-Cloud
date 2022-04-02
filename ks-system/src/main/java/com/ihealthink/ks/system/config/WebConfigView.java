package com.ihealthink.ks.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 跨域访问配置
 * @description: 跨域访问配置
 * @date Created in 19:39 2022/4/1
 */
@Configuration
public class WebConfigView implements WebMvcConfigurer {
    /**
     * 试图跳转功能
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/doc.html");
        //registry.addRedirectViewController("/","/swagger-ui.html");
    }

    /**
     * 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }


}
