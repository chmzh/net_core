package com.game.core;

import java.util.Set;

import org.apache.log4j.Logger;

import com.game.core.annotation.Controller;

/**
 * 消息分发器
 * @author mingzhou.chen
 * 28458942@qq.com
 */
public class ClassPathControllerDispatcher extends ControllerDispatcher {
	
	public final static Logger LOG = Logger.getLogger(ClassPathControllerDispatcher.class);
	
	
	@Override
	protected void initController() {
		ClassScan classScan = new ClassScan(getBasePackage());
		Set<Class<?>> clazzs = classScan.getClassesByAnnotation(Controller.class);
		initController0(clazzs);
		
	}

}
