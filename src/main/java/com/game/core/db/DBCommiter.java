package com.game.core.db;

public class DBCommiter {
	public static void commit(AsyncDbObj obj){
		AsyncDBTaskManager.add(obj);
	}
	
	public static void startTask(){
		AsyncDBTaskManager.start();
	}
}
