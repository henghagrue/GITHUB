package com.upbchain.springmvc.services;

import org.springframework.stereotype.Service;

@Service(value="threadService")
public class ThreadServices {
	
	public String task1(){
		return "task1";
	}
}
