package cn.ibm.cats.classifier;


import javax.persistence.*;

import cn.ibm.cats.monitor.Result;
@Entity
public class FailLog {
	@Id
	@GeneratedValue
	private int id;
	private String title;
	private String time;
	private String additional_info;
	private String line_number;
	@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="result_id")	//指定外键  
	private Result result;
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
	
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "FailLog [id=" + id + "\n, title=" + title + "\n, time=" + time
				+ "\n, additional_info=" + additional_info + "\n,  line_number=" + line_number + "\n,"
						+" ]\n";
	}
	
	
}
