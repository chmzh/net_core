package com.game.core.db;

public interface AsyncDbObj {
	boolean asyncUpdate();
	OperatorType getType();
	
	void setType(OperatorType type);
	
	public String getUpdateFileds();
	public Object[] getParams(String fields);
	void setSql(String sql);
	public String getSql();
}
