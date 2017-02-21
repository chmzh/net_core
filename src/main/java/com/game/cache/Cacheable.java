package com.game.cache;

public interface Cacheable {
	
	/**
	 * 创建时间
	 * @param time
	 */
	long getCreateTime();
	/**
	 * 存活时间
	 * @param lifeTime
	 */
	void setLifeTime(int lifeTime);
	
	long getLifeTime();
	
	/**
	 * 是否可以存活下来
	 * @return
	 */
	boolean isLife();
}
