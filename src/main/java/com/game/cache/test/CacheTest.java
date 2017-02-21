package com.game.cache.test;

import com.game.cache.CacheManager;
import com.game.cache.ConcurrentLinkedListCache;
import com.game.cache.ConcurrentMapCache;
import com.game.core.Message;

public class CacheTest {
	private final static ConcurrentMapCache<String,User> MAPS = CacheManager.getConcurrentMapCache("user");
	private final static ConcurrentLinkedListCache<User> LISTS = CacheManager.getConcurrentLinkedListCache("users");
	public static void main(String[] args) throws InterruptedException {
		CacheManager.start();
		//Thread.sleep(7000);
		put();
		print();
		modify();
		print();
	}
	
	public static void put(){
		User user = new User();
		user.setUserid(10000);
		MAPS.put("msg1", user);
		LISTS.add(user);
	}
	
	public static void modify(){
		User message = MAPS.get("msg1");
		message.setLifeTime(10000);;
	}
	
	public static void print(){
		User message = MAPS.get("msg1");
		System.out.println(message.getUserid()+":"+message.getLifeTime());
		System.out.println(LISTS.peek().getUserid()+":"+LISTS.peek().getLifeTime());
	}
}
