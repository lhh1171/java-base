package com.cjs.sso.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 **/
@Component
public class LoginInterceptor implements HandlerInterceptor {


    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static   LoginInterceptor loginInterceptor;

    @PostConstruct
    public void init(){
        loginInterceptor=this;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("执行拦截器------------------");
        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //如果不删除 ThreadLocal中用完的信息 会有内存泄漏的风险
        System.out.println("拦截器执行完成------------------");
    }
}
