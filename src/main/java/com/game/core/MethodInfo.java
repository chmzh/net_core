package com.game.core;

import java.lang.reflect.Method;

/**
 * 方法信息
 * @author mingzhou.chen
 * 28458942@qq.com
 */
public class MethodInfo {
	//方法名
	private String name;
	//方法
	private Method method;
	//方法参数说明，用于客户端参考
	private String[] paramDes;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public String[] getParamDes() {
		return paramDes;
	}
	public void setParamDes(String[] paramDes) {
		this.paramDes = paramDes;
	}
	
}
