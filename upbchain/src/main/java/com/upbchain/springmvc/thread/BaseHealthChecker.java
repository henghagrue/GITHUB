package com.upbchain.springmvc.thread;

import java.util.concurrent.CountDownLatch;
/**
 * 这个类是一个Runnable，负责所有特定的外部服务健康的检测。它删除了重复的代码和闭锁的中心控制代码
 * @author upbchain12
 *
 */
public abstract class BaseHealthChecker implements Runnable {
	private CountDownLatch _latch;
	private String _serviceName;
	private boolean _serviceUp;
	//Get latch Object in constructor so that completion the task,
	//thread can countDown() the latch 
	public BaseHealthChecker(String serviceName,CountDownLatch latch){
		super();
		this._latch = latch;
		this._serviceName = serviceName;
		this._serviceUp = false;
	}
	
	@Override
	public void run() {
		try{
			verifyService();
			_serviceUp = true;
		}catch(Throwable t){
			t.printStackTrace(System.err);
			_serviceUp = false;
			
		}finally{
			if(_latch != null){
				_latch.countDown();
			}
		}

	}

	public CountDownLatch get_latch() {
		return _latch;
	}


	public String get_serviceName() {
		return _serviceName;
	}


	public boolean is_serviceUp() {
		return _serviceUp;
	}


	public abstract void verifyService();
}
