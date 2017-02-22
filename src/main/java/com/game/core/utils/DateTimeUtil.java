package com.game.core.utils;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.GJChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
/**
 * 时区参考 http://www.joda.org/joda-time/timezones.html
 * @author chenmingzhou
 *
 */
public class DateTimeUtil {
	public final static String pattern = "yyyy-MM-dd";
	
	public final static String timezone = "Asia/Shanghai";
	
	/**
	 * 当前时间 yyyy-MM-dd HH:mm:ss
	 * @param timezone
	 * @return
	 */
	public static String curDateTime(String timezone){
		DateTimeZone zone = DateTimeZone.forID(timezone);
		Chronology gregorianJuian = GJChronology.getInstance(zone);
		DateTime dt = new DateTime(gregorianJuian);
		DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
		return dt.toString(format);
	}
	
	/**
	 * 时间戳 转 时间 yyyy-MM-dd hh:mm:ss
	 * @param millis
	 * @param timezone
	 * @return
	 */
	public static String toDateTime(long millis,String timezone){
		DateTimeZone zone = DateTimeZone.forID(timezone);
		Chronology gregorianJuian = GJChronology.getInstance(zone);
		DateTime dt = new DateTime(millis,gregorianJuian);
		DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
		return dt.toString(format);
	}
	public static String toDateTime(long millis){
		return toDateTime(millis, timezone);
	}
	/**
	 * 当前时间戳 毫秒
	 * @param timezone
	 * @return
	 */
	public static long getMillis(String timezone){
		DateTimeZone zone = DateTimeZone.forID(timezone);
		Chronology gregorianJuian = GJChronology.getInstance(zone);
		DateTime dt = new DateTime(gregorianJuian);
		return dt.getMillis();
	}
	
	public static long getMillis(){
		return getMillis(timezone);
	}
	
	/**
	 * 当前时间戳 秒
	 * @param timezone
	 * @return
	 */
	public static int getSeconds(String timezone){
		DateTimeZone zone = DateTimeZone.forID(timezone);
		Chronology gregorianJuian = GJChronology.getInstance(zone);
		DateTime dt = new DateTime(gregorianJuian);
		return (int) (dt.getMillis()/1000);
	}
	
	public static int getSeconds(){
		return getSeconds(timezone);
	}
	
	/**
	 * 增加相应的天数
	 * @param someday
	 * @param days
	 * @return
	 */
	public static String plusDays(String someday,int days){
		DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
		DateTime dt = DateTime.parse(someday, formatter);
		dt = dt.plusDays(days);
		return dt.toString(formatter);
	}
	
	/**
	 * 默认返回 Asia/Shanghai 时区时间
	 * @return
	 */
	public static String curDateTime(){
		return curDateTime(timezone);
	}
	
	
	public static void main(String[] args) {
		//System.out.println(curDateTime());
		
		//毫秒
		long millis = getMillis("America/Dawson");
		System.out.println(toDateTime(millis,"America/Dawson"));
		
		
		//增加天数
		String curDateTime = curDateTime("Asia/Shanghai");
		System.out.println(curDateTime);
		System.out.println(plusDays(curDateTime, 2));
	}
}
