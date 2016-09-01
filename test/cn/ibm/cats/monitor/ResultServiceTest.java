package cn.ibm.cats.monitor;


import java.util.Map;

import org.junit.Test;



public class ResultServiceTest {
	public ResultService resultService=new ResultService();
	@Test
	public void updateResult(){
		Map<String, Result> map=resultService.updateResult();
		for (Result result:map.values()) {
			System.out.println(result.getTCName()+"->"+result.getOperationResult());
			
		}
	}
}
