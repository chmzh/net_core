package com.game.core.db;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class AsyncDBTaskManager {
	
	private final static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	
	private final static LinkedBlockingQueue<AsyncDbObj> queue = new LinkedBlockingQueue<AsyncDbObj>();

	public static void start(){
		executor.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				run();
				
			}
		}, 0, 2, TimeUnit.SECONDS);
	}
	
	/**
	 * 执行入库入理
	 */
	public static void run(){
		AsyncDbObj obj = queue.poll();
		obj.async();
	}
	
	public static void add(AsyncDbObj obj){
		queue.add(obj);
	}
}
