package com.upbchain.springmvc.interceptor;

import java.lang.reflect.Parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
/**
 * 自定义拦截器，在spring.xml中配置
 * @author upbchain12
 *
 */
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest arg0,
            HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        System.out.println("afterCompletion");
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {
        System.out.println("postHandle");
    }

    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2) throws Exception {
    	HandlerMethod hm = (HandlerMethod) arg2;
    	System.out.println(hm.getBeanType());
    	System.out.println(hm.getMethod().getName());
    	Parameter[] par = hm.getMethod().getParameters();
    	for(Parameter r : par){
    		System.out.println(r.getName()+":"+r.getType());
    	}
    	System.out.println();
        System.out.println("preHandle");
        return true;
    }

}