package com.upbchain.springmvc.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upbchain.springmvc.model.User;
import com.upbchain.springmvc.services.ThreadServices;
import com.upbchain.springmvc.thread.ApplicationStartupUtil;

@Controller
@RequestMapping(value="thread")
public class ThreadController {
	
	
	@Resource
	ThreadServices threadService;
	
	@RequestMapping(value="task1")
	@ResponseBody
	public User thread1(){
		User user = new User();
		user.setBirth(new Date());
		user.setId(1);
		user.setName(threadService.task1());
		return user;
	}
	
	
	@RequestMapping(value="task2")
	@ResponseBody
	public String thread2(){
		  boolean result = false;
	        try {
	            result = ApplicationStartupUtil.checkExternalServices();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return "External services validation completed !! Result was :: "+ result;
	}
	

}
