package com.game.cache;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedListCache<E extends Cacheable> {
	private String name;
	private ConcurrentLinkedQueue<E> store;
	
	public ConcurrentLinkedListCache(String name){
		store = new ConcurrentLinkedQueue<E>();
	}
	public void add(E e) {
		store.offer(e);
		
	}
	public void get(){
		store.poll();
	}
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	public void evict(){
		for(E e: store){
			if(!e.isLife()){
				store.remove(e);
			}
		}
	}
}
