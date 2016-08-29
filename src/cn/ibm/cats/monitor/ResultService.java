package cn.ibm.cats.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ResultService {
	private Map<String,Result> resultMap=new HashMap<String, Result>();
	private ResultDao resultDao=new ResultDao();
	public ResultService(){
		initBathResult();
	}
	private void initBathResult(){
		Map<String, String> testMap = resultDao.getBatTestcaseNames();
		for (Map.Entry<String, String> entry : testMap.entrySet()) {
			Result result=new Result();
			result.setTCName(entry.getKey());
			result.setComponent(entry.getValue());
			resultMap.put(entry.getKey(), result);
		}
		System.out.println("resultMap.size:"+resultMap.size());
	}

	public void updateResult() {
		Iterator<Map.Entry<String, Result>> it = resultMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Result> entry = it.next();
			Result result=entry.getValue();
			BufferedReader buff = resultDao.getHtmlLogBufferedReader(result.getComponent(),false);
			if(null==buff){
				result.setOperationResult("Not finish");
				System.out.println("file not find");
			}else{
				getOperationResult(buff);
			}
		}
	}
	private String getOperationResult(BufferedReader buff) {
		String line,result="pass",type = "";
		try {
			while(null!=(line=buff.readLine())){
				if(line.contains("FORM NAME=failures")){
					type="failures";
				}else if(line.contains("FORM NAME=warnings")){
					type="warnings";
				}else if(line.contains("FORM NAME=vps")){
					type="vps";
					return result;
				}else if(line.contains("OPTION TITLE=")){
					if("failures".equals(type)){
						result="fail";
						return result;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
