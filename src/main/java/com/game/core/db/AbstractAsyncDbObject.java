package com.game.core.db;

import java.lang.reflect.Field;

public abstract class AbstractAsyncDbObject implements AsyncDbObj {
	
	private OperatorType type;
	protected String sql;
	protected String updateFileds;
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
		this.sql = sql;
	}

	@Override
	public String getSql() {
		return sql;
	}

}
