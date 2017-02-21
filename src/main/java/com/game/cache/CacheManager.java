package com.game.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CacheManager {
	
	private final static ExecutorService executor = Executors.newScheduledThreadPool(1);
	
	private final static ConcurrentMap<String, ConcurrentMapCache> MAPS = new ConcurrentHashMap<String, ConcurrentMapCache>();
	
	private final static ConcurrentMap<String, ConcurrentLinkedCache> LISTS = new ConcurrentHashMap<String, ConcurrentLinkedCache>();
	
	public CacheManager() {

	}
	
	
	
	public static <K,V extends Cacheable> ConcurrentMapCache<K, V> getConcurrentMapCache(String name){
		 MAPS.putIfAbsent(name, new ConcurrentMapCache<K,V>(name));
		 return MAPS.get(name);
	}
	
	public static <E extends Cacheable> ConcurrentLinkedCache<E> getConcurrentLinkedCache(String name){
		LISTS.putIfAbsent(name, new ConcurrentLinkedCache<E>(name));
		return LISTS.get(name);
	}
}
