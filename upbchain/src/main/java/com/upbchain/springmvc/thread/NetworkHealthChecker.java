package com.upbchain.springmvc.thread;

import java.util.concurrent.CountDownLatch;
/**
 * 继承了BaseHealthChecker，实现了verifyService()方法
 * @author upbchain12
 *
 */
public class NetworkHealthChecker extends BaseHealthChecker
{
    public NetworkHealthChecker (CountDownLatch latch)  {
        super("Network Service", latch);
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