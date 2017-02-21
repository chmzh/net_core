package com.game.cache;

public interface Cache<K,V extends Cacheable> {
	//MAP类型数据缓存
	void put(K key,V value);
	V get(K key);
	
	//LIST类型数据缓存
	
	
	void evict();
	
	String getName();
}
