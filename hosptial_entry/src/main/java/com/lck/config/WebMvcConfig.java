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
        registry.addViewController("/add.html").setViewName("/patients/add");
        registry.addViewController("/advice.html").setViewName("/suggestions/advice");
        registry.addViewController("/addAdvice.html").setViewName("/suggestions/addAdvice");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/","/index.html","/login","/css/*","/js/**","/img/**");
    }
}
