package com.game.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CacheManager {
	
	private final static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	
	private final static ConcurrentMap<String, ConcurrentMapCache> MAPS = new ConcurrentHashMap<String, ConcurrentMapCache>();
	
	private final static ConcurrentMap<String, ConcurrentLinkedListCache> LISTS = new ConcurrentHashMap<String, ConcurrentLinkedListCache>();
	
	private CacheManager() {

	}
	
	public static void start(){
		executor.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				evictMap();
				evictList();
				
			}
		}, 0, 5, TimeUnit.SECONDS);
	}
	
	/**
	 * 移除到期的缓存
	 */
	private static void evictMap(){
		for (Map.Entry<String, ConcurrentMapCache> entry : MAPS.entrySet()) {
			entry.getValue().evict();
		}
	}
	/**
	 * 移除到期的缓存
	 */
	private static void evictList(){
		for (Map.Entry<String, ConcurrentLinkedListCache> entry : LISTS.entrySet()) {
			entry.getValue().evict();
		}
	}
	
	public static <K,V extends Cacheable> ConcurrentMapCache<K, V> getConcurrentMapCache(String name){
		 MAPS.putIfAbsent(name, new ConcurrentMapCache<K,V>(name));
		 return MAPS.get(name);
	}
	
	public static <E extends Cacheable> ConcurrentLinkedListCache<E> getConcurrentLinkedListCache(String name){
		LISTS.putIfAbsent(name, new ConcurrentLinkedListCache<E>(name));
		return LISTS.get(name);
	}
}
