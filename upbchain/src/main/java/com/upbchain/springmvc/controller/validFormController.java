package com.upbchain.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/valid")
public class validFormController {
	   //打开上传页面
	   @RequestMapping("/form")
	   public String test(){
	       return "validForm";
	   }
}
