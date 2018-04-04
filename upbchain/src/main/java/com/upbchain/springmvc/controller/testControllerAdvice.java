package com.upbchain.springmvc.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class testControllerAdvice {
/**
 * 全局controller异常处理：方法1
 * 方法二：spring.xml增加配置
 * 
 */
	    @ExceptionHandler
	    public ModelAndView exceptionHandler(Exception ex){
	        ModelAndView mv = new ModelAndView("exceptionAll");
	        mv.addObject("exception", ex);
	        System.out.println("in testControllerAdvice all");
	        return mv;
	    }
}
