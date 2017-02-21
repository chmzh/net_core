package com.game.test;

import com.game.cache.CacheManager;
import com.game.cache.ConcurrentMapCache;
import com.game.core.Message;

public class CacheTest {
	private final static ConcurrentMapCache<String,User> CACHE_MANAGER = CacheManager.getConcurrentMapCache("user");
	
	public static void main(String[] args) {
		put();
		print();
		modify();
		print();
	}
	
	public static void put(){
		User user = new User();
		user.setUserid(10000);
		CACHE_MANAGER.put("msg1", user);
	}
	
	public static void modify(){
		User message = CACHE_MANAGER.get("msg1");
		message.setLifeTime(10000);;
	}
	
	public static void print(){
		User message = CACHE_MANAGER.get("msg1");
		System.out.println(message.getUserid()+":"+message.getLifeTime());
	}
}
