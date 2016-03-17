package com.game.core;

import java.util.HashMap;
import java.util.Map;

import com.game.core.exception.MapNotContainKeyException;

/**
 * 控制器信息持有者
 * @author mingzhou.chen
 * 28458942@qq.com
 */
public class ControllerInfo {
	private Object owner;
	private Map<Integer, MethodInfo> methodInfoMap;
	
	public ControllerInfo(Object owner) {
		this.owner = owner;
		methodInfoMap = new HashMap<Integer, MethodInfo>();
	}
	
	
	/**
	 * 拥有者
	 * @return
	 */
	public Object getOwner() {
		return owner;
	}

	public void addMethodInfo(int methodId,MethodInfo methodInfo){
		methodInfoMap.put(methodId, methodInfo);
	}
	
	/**
	 * 获取方法名
	 * @param methodId 方法ID
	 * @return
	 * @throws MapNotContainKeyException
	 */
	public MethodInfo getMethodInfo(int methodId) throws MapNotContainKeyException{
		if(!methodInfoMap.containsKey(methodId)){
			throw new MapNotContainKeyException(owner+" actionId为:("+methodId+")的方法不存在");
		}
		return methodInfoMap.get(methodId);
	}
}
