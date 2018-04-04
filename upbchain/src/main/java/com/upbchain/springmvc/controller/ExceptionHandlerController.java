package com.upbchain.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class ExceptionHandlerController {
	/**
	 * controller内部异常处理（范围：当前controller）
	 * @param ex
	 * @return
	 */
	@ExceptionHandler
    public ModelAndView exceptionHandler(Exception ex){
        ModelAndView mv = new ModelAndView("exception");//异常时跳转jsp
        mv.addObject("exception", ex);
        System.out.println("in testExceptionHandler");
        return mv;
    }
    
    @RequestMapping("/error")
    public String error(){
        int i = 5/0;//异常
        return "hello";
    }
}
