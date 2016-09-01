package cn.ibm.cats.classifier;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
//@Entity
public class HtmlLog {
	@Id
	@GeneratedValue
	private int id;
	private String path;
	private String FailerType;
	//@OneToMany(cascade={CascadeType.ALL},fetch=FetchType.LAZY) //级联关系和抓取策略(积极)
	//@JoinColumn(name="hid")//指定外键    ,本表中的列名
	@ManyToMany(cascade={},fetch=FetchType.LAZY)//CascadeType.ALL
	@JoinTable(name="htmlLog_failLog",	//中间表名称
	joinColumns={@JoinColumn(name="hid")},	//本表主键
	inverseJoinColumns={@JoinColumn(name="fid")})//外表主键
	private List <FailLog> failLogs=new ArrayList<FailLog>();
	public HtmlLog(){}
	public HtmlLog(String path) {
		this.path=path;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFailerType() {
		return FailerType;
	}
	public void setFailerType(String failerType) {
		FailerType = failerType;
	}
	public List<FailLog> getFailLogs() {
		return failLogs;
	}
	public void setFailLogs(List<FailLog> failLogs) {
		this.failLogs = failLogs;
	}
	@Override
	public String toString() {
		return "HtmlLog [id=" + id + ", path=" + path + ", FailerType="
				+ FailerType + "\n, failLogs=" + failLogs + "]\n";
	}
	
}
