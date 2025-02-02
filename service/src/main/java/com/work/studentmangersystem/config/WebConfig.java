package com.work.studentmangersystem.config;

import com.work.studentmangersystem.interceptor.AuthInterceptor;
import com.work.studentmangersystem.interceptor.RoleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @FileName studentmangersystem
 * @Description
 * @Author chaos
 * @Date 2024/6/10 下午7:58
 */
@Configuration//配置类
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;
    @Autowired
    private RoleInterceptor roleInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**").excludePathPatterns("login","/login/**");
        registry.addInterceptor(roleInterceptor).addPathPatterns("/manager/**","/operator/**");
    }
}
