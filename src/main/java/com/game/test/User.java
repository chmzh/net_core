package com.game.test;

import com.game.cache.AbstractCacheable;

public class User extends AbstractCacheable {
	private int userid;

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}
}
