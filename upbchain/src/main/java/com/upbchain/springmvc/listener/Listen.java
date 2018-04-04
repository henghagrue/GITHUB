package com.upbchain.springmvc.listener;

import java.util.concurrent.CountDownLatch;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class Listen {
	
  private static Logger logger = LoggerFactory.getLogger(Listen.class);
  private CountDownLatch latch = new CountDownLatch(1);
  public void handleMsg(String message) {
	  System.out.println("reciver msg :" + message);
    logger.info("reciver msg :" + message);
    latch.countDown();
  }
  
  public CountDownLatch getLatch() {
	  System.out.println("getLatch" );
    return latch;
  }
  
 
}

