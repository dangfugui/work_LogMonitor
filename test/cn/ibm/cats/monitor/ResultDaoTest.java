package cn.ibm.cats.monitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class ResultDaoTest {
	ResultDao resultDao=new ResultDao();
	@Test
	public void getBatTestcase(){
		 Map<String, String> map = resultDao.getBatTestcaseNames();
		 for (String string : map.values()) {
			System.out.println(string);
		}
	}
	@Test
	public void getResultList(){
		Map<String, Result> list = resultDao.getResultList();
		for (Result result : list.values()) {
			System.out.println("result"+result.getTCName());
		}
	}
//	@Test
//	public void getHtmlLog(){
//		BufferedReader bufr = resultDao.getHtmlLogBufferedReader("oc71.OC71JA30", true);
//		try {
//			System.out.println(bufr.read());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
