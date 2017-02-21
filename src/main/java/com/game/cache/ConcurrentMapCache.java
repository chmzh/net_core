package com.game.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentMapCache<K,V extends Cacheable> implements Cache<K,V> {
	
	private String name;
	private ConcurrentMap<K, V> store;
	
	public ConcurrentMapCache(String name) {
		this.name = name;
		store = new ConcurrentHashMap<K, V>();
	}
	
	
	@Override
	public void put(K key, V value) {
		store.putIfAbsent(key, value);
	}

	@Override
	public V get(K key) {
		return store.get(key);
	}

	@Override
	public void evict() {
		//System.out.println("缓存大小:"+store.size());
		for (Map.Entry<K, V> entry : store.entrySet()) {
			//System.out.println(entry.getValue().getLifeTime());
			if(!entry.getValue().isLife()){
				store.remove(entry.getKey());
				//System.out.println(entry.getValue()+"被移除");
			}
		}
	}

	@Override
	public String getName() {
		return name;
	}
	 
}
