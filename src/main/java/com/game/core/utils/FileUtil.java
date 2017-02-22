package com.game.core.utils;

import java.io.File;
import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class FileUtil {
	public static void append(String content,String fileName){
		
		File to = new File(System.getProperty("user.dir")+File.separatorChar+"sql"+File.separatorChar+fileName);
		try {
			Files.append(content+"\r\n", to, Charsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		FileUtil.append("abc", "abc.txt");
	}
}
