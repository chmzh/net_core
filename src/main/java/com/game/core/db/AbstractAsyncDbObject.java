package com.game.core.db;

public abstract class AbstractAsyncDbObject implements AsyncDbObj {
	
	private OperatorType type;
	protected String sql;
	protected Object[] params;
	@Override
	public void setType(OperatorType type) {
		this.type = type;
	}
	
	@Override
	public boolean async() {
		return false;
	}

	@Override
	public OperatorType getType() {
		return type;
	}
	@Override
	public void setParams(Object[] params) {
		this.params = params;
	}

	@Override
	public Object[] getParams() {
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
