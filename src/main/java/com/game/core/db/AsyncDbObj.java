package com.game.core.db;

public interface AsyncDbObj {
	boolean async();
	OperatorType getType();
	
	void setType(OperatorType type);
	
	void setParams(Object[] params);
	public Object[] getParams();
	
	void setSql(String sql);
	public String getSql();
}
