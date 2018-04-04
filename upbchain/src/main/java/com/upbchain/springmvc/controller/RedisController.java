package com.upbchain.springmvc.controller;

import java.io.PrintWriter;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.upbchain.springmvc.listener.Listen;

@Controller
@RequestMapping("/redis")
public class RedisController {
	@Resource 
	private RedisTemplate redisTemplate;
	
	
	
	@Resource 
	private Listen listen;
	
	@RequestMapping("/sms")
    public void sms(PrintWriter pw){
		try{
			JSONObject json = new JSONObject();
			json.put("merge_from", "1");
			json.put("merge_to", "2");
			System.out.println(json.toJSONString());
			redisTemplate.convertAndSend("sms_queue_web_online", json.toJSONString());
			
//	        System.out.println("redis");
//	        Set keys = redisTemplate.keys("*");
//	        redisTemplate.boundValueOps("aaa").set("ggg");
//	        System.out.println(redisTemplate.boundValueOps("aaa").get());
//	        for(Object obj : keys){
//	        	
//	        	System.out.println(redisTemplate.boundValueOps(obj.toString()).get());
//	        	pw.write(obj.toString()+"="+redisTemplate.boundValueOps(obj.toString()).get());
//	        }
		}catch(Exception e){
			e.printStackTrace();
		}
        
    }
	
	
	@RequestMapping("/channel1")
    public void channel1(PrintWriter pw){
		try{
			JSONObject json = new JSONObject();
			json.put("merge_from", "33");
			json.put("merge_to", "444");
			System.out.println(json.toJSONString());
			redisTemplate.convertAndSend("channel1", json.toJSONString());
			
		}catch(Exception e){
			e.printStackTrace();
		}
        
    }
	
	@RequestMapping("/channel2")
    public void channel2(PrintWriter pw){
		try{
			JSONObject json = new JSONObject();
			json.put("merge_from", "55");
			json.put("merge_to", "66");
			System.out.println(json.toJSONString());
			redisTemplate.convertAndSend("channel2", json.toJSONString());
			
		}catch(Exception e){
			e.printStackTrace();
		}
        
    }
	
	
	@RequestMapping("/leftPush")
    public void leftPush(PrintWriter pw){
		try{
			Long size = redisTemplate.boundListOps("uuid").size();
			redisTemplate.boundListOps("uuid").leftPush(size+1);
		}catch(Exception e){
			e.printStackTrace();
		}
        
    }
	
	@RequestMapping("/rightPush")
    public void rightPush(PrintWriter pw){
		try{
			Long size = redisTemplate.boundListOps("uuid").size();
			redisTemplate.boundListOps("uuid").rightPush(size+1);
		}catch(Exception e){
			e.printStackTrace();
		}
        
    }
	
	
	@RequestMapping("/leftPop")
    public void leftPop(PrintWriter pw){
		try{
			Object obj = redisTemplate.boundListOps("uuid").leftPop();
			
			pw.write(String.valueOf((Integer)obj));
		}catch(Exception e){
			e.printStackTrace();
		}
        
    }
	
	
	@RequestMapping("/rightPop")
    public void rightPop(PrintWriter pw){
		try{
			Object obj = redisTemplate.boundListOps("uuid").rightPop();
			pw.write(String.valueOf((Integer)obj));
		}catch(Exception e){
			e.printStackTrace();
		}
        
    }
	
	
	@RequestMapping("/testnew")
	public void testnew() throws InterruptedException {
		redisTemplate.convertAndSend("java","hello world");
		System.out.println("ok!");
	  listen.getLatch().await();
	}	
	
	
	
	
	
	
	
	
}
