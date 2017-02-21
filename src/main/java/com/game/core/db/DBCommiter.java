package com.game.core.db;

public class DBCommiter {
	public void commit(AsyncDbObj obj){
		AsyncDBTaskManager.add(obj);
	}
}
