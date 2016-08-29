package cn.ibm.cats.classifier;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExtractHtml {
	public List<HtmlLog> extracts(String path){
		List<HtmlLog> htmlLogList=new ArrayList<HtmlLog>();
		File directory=new File(path);
		if(!directory.exists()){
			System.out.println("error file is not exists");
			return null;
		}
		for(File file:directory.listFiles()){
			if(file.isDirectory()){
				for(File f:file.listFiles()){
					if("rational_ft_logframe.html".equals(f.getName())){
						//System.out.println(f.getPath());
						HtmlLog htmlLog=new HtmlLog(f.getPath());
						htmlLogList.add(extract(htmlLog));
					}
				}
			}
		}
		return htmlLogList;
		
	}
	/**
	 * 开始分析HTML文件
	 * @param path html文件路径
	 * @return	FailLog List
	 */
	public HtmlLog extract(HtmlLog htmlLog){
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(htmlLog.getPath()));
			String line="";
			while(null!=(line=bufferedReader.readLine())){
				if(line.contains(">FAIL</TD>")){
					extractFail(htmlLog,bufferedReader);//处理这个Fail
				}
			}
		} catch (Exception e) {
			System.out.println("open file error");
			e.printStackTrace();
		}
		return htmlLog;
	}
	/**
	 * 定位到一个FAIL后 解析为FailLog 并添加到faillogs 里
	 * @param bufferedReader 用到的BufferReader引用
	 * @throws IOException	文件读取异常
	 */
	private void extractFail(HtmlLog htmlLog,BufferedReader bufferedReader) throws IOException {
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
				htmlLog.getFailLogs().add(failLog);
				return;
			}
		}
	}	
}