package com.sunny.handler;

import com.sunny.entity.UserEntity;
import com.sunny.exception.TokenException;
import com.sunny.service.TokenService;
import com.sunny.service.UserService;
import com.sunny.util.StringUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token过滤器
 */
@Configuration
public class TokenFilter implements HandlerInterceptor, WebMvcConfigurer {

    @Resource
    private TokenService tokenService;

    @Resource
    private TokenFilter tokenFilter;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        // 验证token对应的是哪一个用户
        String token = httpServletRequest.getHeader("token");
        UserEntity userEntity = tokenService.getUserByToken(token);

        // 找到的用户放入到请求中
        httpServletRequest.setAttribute("user", userEntity);

        return true;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenFilter).
                addPathPatterns("/api/**").     // 过滤所有接口
                excludePathPatterns("/api/user/**");   //排除user接口
    }

}