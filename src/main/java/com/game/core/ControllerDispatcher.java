package com.game.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.game.core.annotation.Action;
import com.game.core.annotation.ActionParam;
import com.game.core.annotation.Controller;
import com.game.core.exception.MapNotContainKeyException;
import com.game.core.utils.ParamsUtils;

/**
 * 消息派发器
 * @author mingzhou.chen
 * 28458942@qq.com
 */
public abstract class ControllerDispatcher {
	
	public final static Logger LOG = Logger.getLogger(ClassPathControllerDispatcher.class);
	/**
	 * 控制器是否初始化完成
	 */
	
	private String basePackage;
	private ApplicationContext context;
	
	private static ControllerDispatcher instance;
	
	private final Map<Integer, ControllerInfo> controllerMap = new HashMap<Integer, ControllerInfo>();
	
	/**
	 * 只能由主线程调用一次
	 * @param context
	 */
	public static void initControllerDispatcher(ApplicationContext context,String controllerPackage){
		instance = new ClassPathControllerDispatcher();
		instance.context = context;
		instance.basePackage = controllerPackage;
		instance.initController();
	}
	
	/**
	 * 获取消息分发器实例
	 * @return
	 */
	public static ControllerDispatcher getInstance(){
		return instance;
	}
	
	/**
	 * 初始化控制器
	 */
	protected abstract void initController();
	
	/**
	 * 派发消息
	 * @param controller
	 * @param actionId
	 * @param params    参数 用,分隔的形式
	 */
	public void doDispatcher(int controllerId,int actionId,String params0){
		Object[] params = null;
		ControllerInfo controllerInfo = getControllerInfo(controllerId);
		Object obj = controllerInfo.getOwner();
		try {
			MethodInfo methodInfo = controllerInfo.getMethodInfo(actionId);
			Method method = methodInfo.getMethod();
			params = ParamsUtils.parse(method.getParameterTypes(), params0);
			invoke(obj,method, params);
			
			
		} catch (MapNotContainKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 派发消息
	 * @param controllerId   控制器标识 由@Controller 的 id值决定
	 * @param actionId       动作标识 由Action 的id值决定
	 * @param params
	 */
	private void invoke(Object obj,Method method,Object[] params){
		try {
			
			method.invoke(obj, params);
			
			
		}catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	 
	/**
	 * 获取对应的控制器
	 * @param key
	 * @return
	 */
	private ControllerInfo getControllerInfo(Integer key){
		ControllerInfo controllerInfo = null;
		try {
			controllerInfo = getControllerInfo0(key);
		} catch (MapNotContainKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return controllerInfo;
	}
	
	/**
	 * 获取消息的控制器信息
	 * @param key
	 * @return
	 * @throws MapNotContainKeyException
	 */
	private ControllerInfo getControllerInfo0(Integer key) throws MapNotContainKeyException{
		if(!controllerMap.containsKey(key)){
			throw new MapNotContainKeyException("不存在id为"+key+"的控制器");
		}
		return controllerMap.get(key);
	}
	
	/**
	 * 添加控制器信息到缓存
	 * @param controllerId
	 * @param actionId
	 * @param clazz
	 */
	protected void addController(int controllerId,ControllerInfo controllerInfo){
		controllerMap.put(controllerId, controllerInfo);
	}

	protected ApplicationContext getContext() {
		return context;
	}
	
	protected void initController0(Set<Class<?>> clazzs){
		for (Class<?> clazz : clazzs) {
			
			ControllerInfo controllerInfo = new ControllerInfo(getContext().getBean(clazz));
			//获取控制器注解
			Controller controller = clazz.getAnnotation(Controller.class);
			
			int controllerId = controller.id();
			
			if(controllerId == -1){
				//TODO 抛出异常或打印错误日志
				LOG.error(clazz + ":"+"请正确填写控制器ID",new Exception("controllerId不能为空"));
			}
			
			//.......
			//获取控制器所有方法
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method : methods) {
				
				MethodInfo methodInfo = new MethodInfo();
				Action action = method.getAnnotation(Action.class);
				if(null==action){
					LOG.error("请正确填写"+clazz+"控制器的【"+method.getName()+"】方法,actionId", new Exception("actionId不能为空"));
					continue;
				}
				methodInfo.setName(action.value());
				methodInfo.setMethod(method);
				
				//获取参数描述
				Parameter[] params = method.getParameters();
				if(null!=params && params.length>0){
					Annotation[][] annotations = method.getParameterAnnotations();
					
					String[] paramDes = null;
					if(null!=params && params.length>0){
						int paramsCount = params.length;
						paramDes = new String[paramsCount];
						for(int i=0;i<paramsCount;i++){
							
							Parameter parameter = params[i];
							String paramType = parameter.getType().getSimpleName();
							String paramName = parameter.getName();
							String des = "default";
							if(annotations[i].length>0){
								ActionParam actionParam = (ActionParam)annotations[i][0];
								des = actionParam.value();
							}
							paramDes[i] = des+"-"+paramName+"("+paramType+")";
						}
					}
					
					methodInfo.setParamDes(paramDes);
				}
				
				
				int actionId = action.id();
				if(actionId == -1){
					//TODO 抛出异常或打印错误日志
					LOG.error("请正确填写"+clazz+"控制器的【"+method.getName()+"】方法,actionId", new Exception("actionId不能为空"));
				}
				controllerInfo.addMethodInfo(actionId, methodInfo);
				
			}
			//......
			addController(controllerId, controllerInfo);
		}
	}

	public String getBasePackage() {
		return this.basePackage;
	}
}
