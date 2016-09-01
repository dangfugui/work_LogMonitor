package cn.ibm.cats.monitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.junit.Test;

import com.ibm.db2.jcc.a.e;

import cn.ibm.cats.classifier.FailLog;
import cn.ibm.cats.util.Config;
import cn.ibm.cats.util.DaoSuper;
import cn.ibm.cats.util.Log;

public class ResultDao extends DaoSuper {
	/**
	 *返回bat 文件中的Testcase name列表
	 * @return
	 */
	public Map<String, String> getBatTestcaseNames(){
		Map<String, String> caseMap=new HashMap<String, String>();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader=new BufferedReader(new FileReader(Config.BATCH_FILE_PATH));
			String line="";
			while(null!=(line=bufferedReader.readLine())){
				if(line.contains("call callTCs")&&line.contains(".")){
					String name=line.substring(line.indexOf(".")+1);
					caseMap.put(name, line.substring(line.lastIndexOf("TCs")+4));
				}
			}
			bufferedReader.close();
		} catch (IOException e) {
			Log.add(this.getClass(),"bufferedReader instance error");
			e.printStackTrace();
		}
		return caseMap;
	}
	/*
	 * 拿到数据库中对应的Result
	 */
	public Map<String, Result> getResultList(){
		Map<String,Result> resultMap=new HashMap<String, Result>();
		transaction=session.beginTransaction();
		Query query = session.createQuery("from Result as r where r.TCName = ?");
		for( Entry<String, String> entry:getBatTestcaseNames().entrySet()){
			String name=entry.getKey();
			query.setParameter(0,name);
			Iterator iterator = query.iterate();
			Result result=new Result();//////////////////////////////////////////////////////////////new result  for test
			result.setTCName(name);
			result.setComponent(entry.getValue());
			while (iterator.hasNext()) {
				result=(Result) iterator.next();
			}
			resultMap.put(result.getTCName(),result);
		}
		transaction.commit();
		return resultMap;
	}
	public BufferedReader getHtmlLogBufferedReader(Result result,boolean isFrame){
		String callString=result.getComponent();
		String name=callString.substring(callString.lastIndexOf('.')+1);
		//System.out.println(name);
		String parentPath;
		if(name.subSequence(0,2).equals("OC")){
			parentPath=Config.LOG_FOLDER+"db2admin.scripts.oc."+callString;
		}else{
			parentPath=Config.LOG_FOLDER+"db2admin.scripts.admin."+callString;
		}
		String logPath;
		if(isFrame){
			logPath=parentPath+"/rational_ft_logframe.html";
		}else {
			logPath=parentPath+"/rational_ft_log.html";
		}
		//System.out.println(logPath);
		File file=new File(logPath);
		if(file.exists()){
			try {
				BufferedReader bufferedReader=new BufferedReader(new FileReader(file));
				return bufferedReader;
			} catch (FileNotFoundException e) {
				Log.error("file open error:"+logPath);
				e.printStackTrace();
			}
		}
		return null;
	}
	public void save(Map<String, Result> resultMap) {
		transaction=session.beginTransaction();
		for (Result result : resultMap.values()) {
			session.saveOrUpdate(result);
		}
		transaction.commit();
	}
	
}
