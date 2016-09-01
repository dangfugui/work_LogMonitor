package cn.ibm.cats.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.ibm.cats.classifier.FailLog;
import cn.ibm.cats.classifier.HtmlLog;

public class ResultService {
	private Map<String,Result> resultMap=new HashMap<String, Result>();
	private ResultDao resultDao=new ResultDao();
	public ResultService(){
		initBathResult();
	}
	private void initBathResult(){
		resultMap=resultDao.getResultList();
		
		System.out.println("resultMap.size:"+resultMap.size());
	}

	public Map<String, Result> updateResult() {
		Iterator<Map.Entry<String, Result>> it = resultMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Result> entry = it.next();
			Result result=entry.getValue();
			BufferedReader buff = resultDao.getHtmlLogBufferedReader(result,true);
			if(null==buff){
				result.setOperationResult("Not finish");
			}else{
				if(result.getFailLogs()==null||result.getFailLogs().size()==0){
					analyzeHTMLLog(buff,result);
				}
			}
		}
		resultDao.save(resultMap);
		return resultMap;
	}
	
	private Result analyzeHTMLLog(BufferedReader buff,Result result) {
		result.setOperationResult("pass");
		String line,type = "";
		try {
			while(null!=(line=buff.readLine())){
				if(line.contains(">FAIL</TD>")){
					extractFail(result,buff);//处理这个Fail
					if(result.getFailLogs().size()>0){
						result.setOperationResult("fail");
					}
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 定位到一个FAIL后 解析为FailLog 并添加到faillogs 里
	 * @param bufferedReader 用到的BufferReader引用
	 * @throws IOException	文件读取异常
	 */
	private void extractFail(Result result,BufferedReader bufferedReader) throws IOException {
		FailLog failLog=new FailLog();
		String time=bufferedReader.readLine();
		if(!time.contains("<TD CLASS=\"time\">")){
			return;
		}else {
			time=time.substring(time.indexOf("<TD CLASS=\"time\">")+17, time.indexOf("</TD>"));
			failLog.setTime(time);
		}
		String title=bufferedReader.readLine();
		if(!title.contains("<TD CLASS=\"note\">")){
			return;
		}else {
			title=title.substring(title.indexOf("<TD CLASS=\"note\">")+17, title.indexOf("</TD>"));
			title=title.replaceAll("<BR>", "\n");
			if(title.length()>250){
				title=title.substring(0,250);
			}
			failLog.setTitle(title);
		}
		//List<String> list=new ArrayList<String>();
		String line="",key="",value="";
		int beginIndex,endIndex;
		while(null!=(line=bufferedReader.readLine())){
			if(line.contains("<LI>")){//	每一个item
				beginIndex=line.indexOf("<LI><I>")+7;
				endIndex=line.indexOf(" = </I>");
				if(beginIndex==-1||endIndex==-1||beginIndex>=endIndex) {
					System.out.println("error line"+line);
					continue;
				}
				key=line.substring(beginIndex, endIndex);
				beginIndex=line.indexOf("= </I>")+6;
				endIndex=line.indexOf("</LI>");
				if(beginIndex==-1||endIndex==-1||beginIndex>=endIndex) {
					System.out.println("error line"+line);
					continue;
				}
				value=line.substring(beginIndex, endIndex);
				if(value.length()>250){
					value=value.substring(0,250);
				}
				//line=line.replaceAll("<LI>", "").replaceAll("</LI>", "").replaceAll("<I>", "").replace("</I>", "");
				//list.add(key+"=="+value);
				switch (key) {
				case "additional_info":
					failLog.setAdditional_info(value);
					break;
				case "line_number":
					failLog.setLine_number(value);
					break;
				default:
					///System.out.println("error key ="+key);
					break;
				}
			}else if(line.contains("</UL>")){//item遍历结束
				//failLog.setList(list);
				result.getFailLogs().add(failLog);
				return;
			}
		}
	}
	
	
	private void readFailLine(String string,Result result) {
		if (string.contains("The JOB failed.JobId")) {
			String jobId = string.substring(string.indexOf(":") + 2, string
					.indexOf(":") + 10);
			String rc = string.substring(string.indexOf("MAXCC=") + 6,
					string.indexOf("CN")).trim();
	//		String ip = "tolec" + ec + ".vmec.svl.ibm.com";
	//		String jobLogLink = downloadJesLog(jobId, ip);
	//		String jobJCLLink = downloadJCL(jobId, ip);
	//		FailedJob failedJob = new FailedJob();
	//		failedJob.setJobId(jobId);
	//		failedJob.setAnalyzeInformation("The Job " + jobId+ " failed with return code = " + rc + " on EC " + ec);
	//		failedJob.setFailedJobLogLink(jobLogLink);
	//		failedJob.setFailedJobJCLLink(jobJCLLink);
	//		failedTestCase.setFailedJob(failedJob);
			result.setFailedJobJCL("jobId="+jobId);
			result.setAnalysis("The Job " + jobId+ " failed with return code = " + rc + " on EC " );
		} else if (string.contains("The pre-req JOB")) {
			String jobId = string.substring(string.indexOf(":") + 2, string
					.indexOf(":") + 10);
			String rc = string.substring(string.indexOf("MAXCC=") + 6,
					string.indexOf("CN")).trim();
	//		String ip = "tolec" + ec + ".vmec.svl.ibm.com";
	//		String jobLogLink = downloadJesLog(jobId, ip);
	//		String jobJCLLink = downloadJCL(jobId, ip);
	//		FailedJob failedJob = new FailedJob();
	//		failedJob.setJobId(jobId);
	//		failedJob.setAnalyzeInformation("The pre-req Job " + jobId
	//				+ " failed with return code = " + rc + " on EC " + ec);
	//		failedJob.setFailedJobLogLink(jobLogLink);
	//		failedJob.setFailedJobJCLLink(jobJCLLink);
	//		failedTestCase.setFailedJob(failedJob);
			result.setFailedJobJCL("jobId="+jobId);
			result.setAnalysis("The Job " + jobId+ " failed with return code = " + rc + " on EC " );
		} else if (string.contains("Can not find text:")) {
			int num = 0; // the line of "can not find text:"
			int lineNum = 0; // the line of "can not find text:"'s
			// "line_number"
			int verifyNum = 0; // the line of "verify jobid"
			String strLineNum = null; // the string of
			// "can not find text:"'s
			// "line_number"
			String strVerify = null; // the string of "verify jobid"'s line
			String jobid = null; // the jobid
			// System.out.println(string);
			String analyzeInformation = string.substring(string
					.indexOf(">") + 1, string.indexOf("</OPTION>"));
			result.setAnalysis(analyzeInformation);
		}
	}
}
