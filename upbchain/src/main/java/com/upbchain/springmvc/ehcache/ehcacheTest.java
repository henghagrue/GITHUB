package com.upbchain.springmvc.ehcache;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import com.upbchain.springmvc.model.User;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class ehcacheTest {
	
	public static void test(String[] args) throws IOException {
		// TODO Auto-generated method stub
		/**		
		 * 通过类路径的方式进行加载 
		 * */
		/* String filePath = "ehcache/ehcache.xml";
		Resource resource1 = new ClassPathResource(filePath);
		File file = resource1.getFile();
		InputStream is = resource1.getInputStream();
		*/
		/**
		 * 绝对路径的方式进行加载
		 */
		/*String filePath = "F:/Develop/Project/upbchain/src/main/resources/ehcache/ehcache.xml";
		Resource resource2 = new FileSystemResource(filePath);
		File file = resource2.getFile();
		InputStream is = resource2.getInputStream();*/
		/**
		 * ResourceUtils ,支持file:前缀和classpath:前缀
		 */
	/*	File file1 = ResourceUtils.getFile("classpath:ehcache/ehcache.xml"); //类路径
		InputStream is1 = new FileInputStream(file1);*/
		
	    File file2 = ResourceUtils.getFile("file:F:/Develop/Project/upbchain/src/main/resources/ehcache/ehcache.xml");     
	    System.out.println(file2.getPath());
	    InputStream is2 = new FileInputStream(file2);
		    
        // 1. 创建缓存管理器
        CacheManager cacheManager = CacheManager.create("file:ehcache/ehcache.xml");

        // 2. 获取缓存对象
        Cache cache = cacheManager.getCache("HelloWorldCache");

        // 3. 创建元素
        Element element = new Element("key1", "value1");

        // 4. 将元素添加到缓存
        cache.put(element);

        // 5. 获取缓存
        Element value = cache.get("key1");
        System.out.println(value);
        System.out.println(value.getObjectValue());

        // 6. 删除元素
        cache.remove("key1");

        User user = new User();
        user.setBirth(new Date());
        user.setId(1);
        user.setName(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6));
        Element element2 = new Element("user", user);
        cache.put(element2);
        Element value2 = cache.get("user");
        User user2 = (User) value2.getObjectValue();
        System.out.println(user2);

        System.out.println(cache.getSize());

        // 7. 刷新缓存
        cache.flush();

        // 8. 关闭缓存管理器
        cacheManager.shutdown();
	}

}
