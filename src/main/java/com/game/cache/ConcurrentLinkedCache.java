package com.game.cache;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedCache<E> {
	private String name;
	private ConcurrentLinkedQueue<E> store;
	
	public ConcurrentLinkedCache(String name){
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
	
}
