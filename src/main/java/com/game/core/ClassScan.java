package com.game.core;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 类扫描器
 * @author mingzhou.chen
 * 28458942@qq.com
 */
public class ClassScan {

	private Set<Class<?>> classes;
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private Set<Class<?>> initClass(String pack){
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        boolean recursive = true;  
        String packageName = pack;  
        String packageDirName = packageName.replace('.', '/');    
        Enumeration<URL> dirs;  
        try {  
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {  
                try {
					URL url = dirs.nextElement();
					String protocol = url.getProtocol();
					if ("file".equals(protocol)) {
						String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
						classes.addAll(findClassesInFile(packageName, filePath, recursive));
					} else if ("jar".equals(protocol)) {
						classes.addAll(findClassesInJar(url, packageDirName, packageName, recursive));
					}
				} catch (Exception e) {
				}
            }
        }catch (Exception e) {
		}
		return classes;
	}
	
	public ClassScan(String pack){
        this.classes = initClass(pack);
	}

	private Set<Class<?>> findClassesInJar(URL url, String packageDirName, String packageName, boolean recursive){
    	Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        // 如果是jar包文件   定义一个JarFile  
        JarFile jar;  
        try {  
            // 获取jar  
            jar = ((JarURLConnection) url.openConnection()).getJarFile();  
            // 从此jar包 得到一个枚举类  
            Enumeration<JarEntry> entries = jar.entries();  
            // 同样的进行循环迭代  
            while (entries.hasMoreElements()) {  
                // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件  
                JarEntry entry = entries.nextElement();  
                String name = entry.getName();  
                // 如果是以/开头的  
                if (name.charAt(0) == '/') {  
                    // 获取后面的字符串  
                    name = name.substring(1);  
                }  
                // 如果前半部分和定义的包名相同  
                if (name.startsWith(packageDirName)) {  
                    int idx = name.lastIndexOf('/');  
                    // 如果以"/"结尾 是一个包  
                    if (idx != -1) {   // 获取包名 把"/"替换成"."  
                        packageName = name.substring(0, idx).replace('/', '.');  
                    }  
                    // 如果可以迭代下去 并且是一个包  
                    if ((idx != -1) || recursive) {  
                        // 如果是一个.class文件 而且不是目录  
                        if (name.endsWith(".class") && !entry.isDirectory()) {  
                            // 去掉后面的".class" 获取真正的类名  
                            String className = name.substring(  
                                    packageName.length() + 1, name.length() - 6);  
                        	Class<?> nameForClass = null;
                        	try {
                        		nameForClass = Class.forName(packageName + '.' + className);
							} catch (NoClassDefFoundError e) {
								log.warn("SCANNING CLASS|WARN|NoClassDefFoundError:"+packageName+'.'+className);
							}
                            // 添加到classes  
							if (nameForClass != null) classes.add(nameForClass);  
                        }  
                    }  
                }  
            }  
        } catch (Exception e) {  
             log.error("SCANNING CLASS|ERROR|"+e);  
        }
		return classes;  
	}
	
    private Set<Class<?>> findClassesInFile(String packageName, String packagePath, final boolean recursive) {  
    	Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        // 获取此包的目录 建立一个File  
        File dir = new File(packagePath);  
        if (!dir.exists() || !dir.isDirectory())  
            return classes;  
        File[] dirfiles = dir.listFiles(new FileFilter() {   
            public boolean accept(File file) {  
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));  
            }  
        });  
        for (File file : dirfiles) {  
            if (file.isDirectory()) {  
            	classes.addAll(findClassesInFile(packageName + "."  
                        + file.getName(), file.getAbsolutePath(), recursive));  
            } else {  
                String className = file.getName().substring(0, file.getName().length() - 6);  
                try {  
                	//classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className)); 
                	classes.add(Class.forName(packageName + '.' + className));
                } catch (ClassNotFoundException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return classes;
    }  
	
    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annClazz){
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();  
    	for (Class<?> clazz : this.classes) {
			if (clazz.getAnnotation(annClazz) != null)
				classes.add(clazz);
		}
    	return classes;
    }
    
}
