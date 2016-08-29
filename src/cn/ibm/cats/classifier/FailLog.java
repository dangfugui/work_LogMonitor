package cn.ibm.cats.classifier;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
@Entity
public class FailLog {
	@Id
	@GeneratedValue
	private int id;
	private String title;
	private String time;
	private String additional_info;
	private String line_number;
	//@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@ManyToMany(mappedBy="failLogs") //指定被主控放控制  自己的句柄名
	private List<HtmlLog> htmlLogs=new ArrayList<HtmlLog>();
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAdditional_info() {
		return additional_info;
	}
	public void setAdditional_info(String additional_info) {
		this.additional_info = additional_info;
	}
	
	public String getLine_number() {
		return line_number;
	}
	public void setLine_number(String line_number) {
		this.line_number = line_number;
	}
	
	public List<HtmlLog> getHtmlLogs() {
		return htmlLogs;
	}
	public void setHtmlLogs(List<HtmlLog> htmlLogs) {
		this.htmlLogs = htmlLogs;
	}
	@Override
	public String toString() {
		return "FailLog [id=" + id + "\n, title=" + title + "\n, time=" + time
				+ "\n, additional_info=" + additional_info + "\n,  line_number=" + line_number + "\n,"
						+ "htmlLogs.size="+htmlLogs.size()+" ]\n";
	}
	
	
}
