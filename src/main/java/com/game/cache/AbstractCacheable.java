package com.game.cache;

public abstract class AbstractCacheable implements Cacheable {
	private long createTime;
	//默认存活一分钟
	private int lifeTime = 1000 * 60;
	public AbstractCacheable() {
		createTime = now();
	}
	
	@Override
	public long getCreateTime() {
		return createTime;
	}

	@Override
	public void setLifeTime(int lifeTime) {
		this.lifeTime = lifeTime;
	}

	@Override
	public long getLifeTime() {
		return lifeTime;
	}

	@Override
	public boolean isLife() {
		return (now() - createTime) < lifeTime;
	}
	
	public long now(){
		return System.currentTimeMillis();
	}
}
