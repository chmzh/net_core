package com.game.core.db;

import java.lang.reflect.Field;

public abstract class AbstractAsyncDbObject implements AsyncDbObj {
	
	private OperatorType type;
	protected String sql;
	protected String updateFileds;
	private long num;
	private String date;
	private int commitCount;
	@Override
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String getDate() {
		// TODO Auto-generated method stub
		return date;
	}
	
	@Override
	public long getNum() {
		// TODO Auto-generated method stub
		return num;
	}
	
	@Override
	public void setNum(long num) {
		this.num = num;
	}
	
	@Override
	public void setType(OperatorType type) {
		this.type = type;
	}
	
	@Override
	public boolean asyncUpdate() {
		return false;
	}

	@Override
	public OperatorType getType() {
		return type;
	}
	
	@Override
	public String getUpdateFileds() {
		// TODO Auto-generated method stub
		return updateFileds;
	}
	
	public Object[] getParams(String fields) {
		String[] args = fields.split(",");
		return getParams(args);
	}
	
	private Object[] getParams(String...fields){
		Object[] params = new Object[fields.length];
		for(int i=0;i<fields.length;i++){
			try {
				Field f = this.getClass().getDeclaredField(fields[i]);
				f.setAccessible(true);
				Object object = f.get(this);
				params[i] = object;
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return params;
	}
	
	@Override
	public void setSql(String sql) {
		String[] args = sql.split(":");
		this.sql = args[0];
		this.updateFileds = args[1];
	}

	@Override
	public String getSql() {
		return sql;
	}
	
	
	@Override
	public void incrementCommitCount() {
		// TODO Auto-generated method stub
		commitCount = commitCount+1;
	}

	@Override
	public int getCommitCount() {
		// TODO Auto-generated method stub
		return commitCount;
	}
}
