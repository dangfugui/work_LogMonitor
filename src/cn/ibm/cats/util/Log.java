package cn.ibm.cats.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

import sun.reflect.generics.tree.VoidDescriptor;

public class Log {
	private static PrintWriter logPrint=new PrintWriter(System.err,true); //getPrint();
	private static File logFile;
	public static void add(Object object, String message) {
		logPrint.println(object.getClass().getSimpleName()+" :"+message);
	}
	public static void add(String message) {
		logPrint.println(message);
	}
	public static Class<Log> error(String error){
		logPrint.println(error);
		return Log.class;
	}
	
	
	
	
	private static PrintWriter  getPrint() {
		DateFormat dateFormat = DateFormat.getDateTimeInstance();// 可以精确到时分秒
		String filepath=Config.LOG_FOLDER+dateFormat.format(new Date()).replace(":", "-") + ".txt";
		logFile =new File(filepath);
		if(!logFile.exists()){
			 if(!logFile.getParentFile().exists()) {  //如果目标文件所在的目录不存在，则创建父目录  
				 if(!logFile.getParentFile().mkdirs()) {  
					 System.err.println("创建目标文件所在目录失败！:"+filepath);  
				 } 
			 }  
			 try {
				 logFile.createNewFile();
				return new PrintWriter(new FileWriter(logFile,true),true);
			} catch (IOException e) {
				System.err.println("创建目标文件所在目录失败！:"+filepath);  
				e.printStackTrace();
			}
		}
		return new PrintWriter(System.err,true);
	}
	
}
