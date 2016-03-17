package com.game.core;

import java.io.Serializable;

public class Message implements Serializable {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
