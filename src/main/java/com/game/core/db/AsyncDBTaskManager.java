package com.game.core.db;

import java.io.File;
import java.util.Spliterator;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.game.core.utils.DateTimeUtil;
import com.game.core.utils.FileUtil;

class AsyncDBTaskManager {
	
	private final static String spliter = "@*@#(@)";
	private final static String Semicolon = ";";
	private final static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	
	private final static LinkedBlockingQueue<AsyncDbObj> queue = new LinkedBlockingQueue<AsyncDbObj>();
	
	private static volatile AtomicLong NUM = new AtomicLong();
	
	public static void start(){
		executor.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				AsyncDBTaskManager.run();
				
			}
		}, 0, 2, TimeUnit.SECONDS);
	}
	
	/**
	 * 执行入库入理
	 */
	private static void run(){
		AsyncDbObj obj = null;
		try {
			obj = queue.peek();
			if(obj==null){
				return;
			}
			boolean bol = obj.asyncUpdate();
			if(!bol){
				//FileUtil.append(obj.getNum()+spliter+getSql(obj), obj.getDate()+"recoder_err.sql");
				FileUtil.append(getSql(obj)+Semicolon, obj.getDate()+"_recoder_err.sql");
			}else{
				//FileUtil.append(obj.getNum()+spliter+getSql(obj), obj.getDate()+"recoder_suc.sql");
				FileUtil.append(getSql(obj)+Semicolon, obj.getDate()+"recoder_suc.sql");
			}
		} catch (Exception e) {
			if(obj!=null){
				//FileUtil.append(obj.getNum()+spliter+getSql(obj), obj.getDate()+"_recoder_err.sql");
				FileUtil.append(getSql(obj)+Semicolon, obj.getDate()+"_recoder_err.sql");
			}
		}finally{
			if(obj!=null){
				queue.remove();
			}
		}
		
	}
	
	public static void add(AsyncDbObj obj){
		//TODO 先记录到文件再添加到队列中，文件日志作为恢复数据的依据
		long num = NUM.getAndIncrement();
		obj.setNum(num);
		obj.setDate(DateTimeUtil.curDateTime());
		//FileUtil.append(obj.getNum()+spliter+getSql(obj), obj.getDate()+"_recoder.sql");
		FileUtil.append(getSql(obj)+Semicolon, obj.getDate()+"_recoder.sql");
		queue.add(obj);
	}
	
	private static String getSql(AsyncDbObj obj){
		String sql = obj.getSql();
		Object[] params = obj.getParams(obj.getUpdateFileds());
		for(Object obj1:params){
			if(obj1.getClass().getTypeName().equals("java.lang.String")){
				sql = sql.replace("?", "'"+obj1.toString()+"'");
			}else{
				sql = sql.replace("?", obj1.toString());
			}
		}
		return sql;
	}
}
