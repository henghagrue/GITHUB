package com.upbchain.springmvc.thread;

import java.util.concurrent.CountDownLatch;

public class DatabaseHealthChecker extends BaseHealthChecker{
	  public DatabaseHealthChecker (CountDownLatch latch)  {
	        super("Database Service", latch);
	    }
	 
	    @Override
	    public void verifyService()
	    {
	        System.out.println("Checking " + this.get_serviceName());
	        try
	        {
	            Thread.sleep(7000);
	        }
	        catch (InterruptedException e)
	        {
	            e.printStackTrace();
	        }
	        System.out.println(this.get_serviceName() + " is UP");
	    }
}
