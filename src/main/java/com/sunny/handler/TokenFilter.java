package com.sunny.handler;

import com.sunny.exception.TokenException;
import com.sunny.util.StringUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token过滤器
 * */
@Configuration
public class TokenFilter implements HandlerInterceptor, WebMvcConfigurer {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
//        String token = httpServletRequest.getHeader("token");
//        if (StringUtil.isEmpty(token)) {
//            throw new TokenException();
//        }
        return true;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        System.out.println("看到这句话，就是进到第二个方法了");
        registry.addInterceptor(new TokenFilter()).
                addPathPatterns("/**").     // 过滤所有
                excludePathPatterns("/user/**");   //排除user/**
    }

}