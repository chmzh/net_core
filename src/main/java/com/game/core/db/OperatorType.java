package com.game.core.db;

public enum OperatorType {
	
	INSERT(1),
	UPDATE(2),
	DELETE(3);
	
	private int type;
	
	private OperatorType(int type){
		this.type = type;
	}
}
