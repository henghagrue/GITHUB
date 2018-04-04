package com.upbchain.springmvc.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.upbchain.springmvc.ehcache.ehcacheTest;

@Controller
public class EhcacheController {
	
	@RequestMapping("ehcache")
	public void test() throws IOException{
		ehcacheTest.test(null);
	}

}
