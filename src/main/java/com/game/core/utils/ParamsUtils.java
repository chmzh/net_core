package com.game.core.utils;

import org.springframework.util.StringUtils;

/**
 * 参数解析工具
 * @author mingzhou.chen
 * 28458942@qq.com
 */
public class ParamsUtils {
	public static Object[] parse(Class<?>[] paramTypes,String params0){
		
		Object[] params = null;
		if(!StringUtils.isEmpty(params0) && null!=paramTypes){
			String[] s = params0.split(",");
			int count = paramTypes.length;
			params = new Object[count];
			for(int i=0;i<count;i++){
				if(paramTypes[i].getTypeName().equalsIgnoreCase("int") || paramTypes[i].getTypeName().equalsIgnoreCase("java.lang.Integer")){
					params[i] = Integer.valueOf(s[i]);
				}else if(paramTypes[i].getTypeName().equalsIgnoreCase("java.lang.String")){
					params[i] = s[i];
				}
				
				//TODO 解析其它类型
			}
		}
		
		return params;
	}
}
