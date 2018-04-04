package com.upbchain.springmvc.listener;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("smsMessageDelegateListener1")
public class SmsMessageDelegateListener1 {
//	  @Autowired
//	    private SmsQueueService smsQueueService;

	    //监听Redis消息
	    public void handleMessage(Serializable message){
	    	
	    	System.out.println("channel1 接收到信息:" + message);
//	        if(message instanceof SmsMessageVo){
//	            SmsMessageVo messageVo = (SmsMessageVo) message;
//
//	            //发送短信
//	            SmsSender smsSender = SmsSenderFactory.buildEMaySender();
//	            smsSender.setMobile(messageVo.getMobile());
//	            smsSender.setContent(messageVo.getContent());
//	            boolean sendSucc = false;
//	            //判断短信类型
//	            //验证码短信
//	            if(messageVo.getType() == (byte)SmsType.VERIFICATION.getType()){
//	                sendSucc = smsSender.send();
//	            }
//
//
//	            if(!sendSucc){
//	                return;
//	            }
//
//	            // 异步更新短信表状态为发送成功
//	            final Integer smsId = messageVo.getSmsId();
//	            Executor executor = Executors.newSingleThreadExecutor();
//	            executor.execute(new Runnable() {
//	                public void run() {
//	                    SmsQueue smsQueue = new SmsQueue();
//	                    smsQueue.setSmsId(smsId);
//	                    smsQueue.setStatus((byte)SmsSendStatus.SEND.getType());
//	                    smsQueue.setProcessTime(new Date());
//	                    smsQueueService.updateSmsQueue(smsQueue);
//	                }
//	            });
//
//	        }
	    }
}
