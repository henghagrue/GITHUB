package com.upbchain.springmvc.listener;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("smsMessageDelegateListener3")
public class SmsMessageDelegateListener3 {
//	  @Autowired
//	    private SmsQueueService smsQueueService;

	    //监听Redis消息
	    public void handleMessage(Serializable message){
	    	System.out.println("channel3 接收到信息:" + message);
	    }
}
