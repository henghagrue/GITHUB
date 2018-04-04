package com.upbchain.springmvc.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upbchain.springmvc.model.User;
@Controller
public class RestfulController {
/**
 * 返回json格式的字符串
 */
	 	@ResponseBody
	    @RequestMapping("/user")
	    public  User get(){
	        User u = new User();
	        u.setId(1);
	        u.setName("jayjay");
	        u.setBirth(new Date());
	        return u;
	    }
}
