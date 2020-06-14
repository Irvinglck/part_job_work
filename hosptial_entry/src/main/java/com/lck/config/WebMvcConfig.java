package com.lck.config;

import com.lck.util.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    //视图跳转
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/list.html").setViewName("/patients/list");
        registry.addViewController("/filedata.html").setViewName("/patients/filedata");
        registry.addViewController("/detail.html").setViewName("/patients/detail");
        registry.addViewController("/detail1.html").setViewName("/patients/detail1");
        registry.addViewController("/detail2.html").setViewName("/patients/detail2");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/","/index.html","/login","/css/*","/js/**","/img/**");
    }
}
