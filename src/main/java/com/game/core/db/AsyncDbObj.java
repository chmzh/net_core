package com.game.core.db;

public interface AsyncDbObj {
	boolean asyncUpdate() throws RuntimeException;
	OperatorType getType();
	
	void setType(OperatorType type);
	
	public String getUpdateFileds();
	public Object[] getParams(String fields);
	void setSql(String sql);
	public String getSql();
	
	/**
	 * 在队列中的编号
	 * @param num
	 */
	public void setNum(long num);
	/**
	 * 在队列中的编号
	 * @return
	 */
	public long getNum();
	
	public void setDate(String date);
	
	public String getDate();
	
	public void incrementCommitCount();
	
	public int getCommitCount();
}
