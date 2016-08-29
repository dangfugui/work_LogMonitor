package cn.ibm.cats.monitor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//id bucket_name cycle_name tc_name result link user groups
@Entity
@Table(name = "result")
public class Result {
	@Id
	@GeneratedValue
	private int id;
	@Column(name = "tc_name", length = 30)
	private String TCName;
	@Column(name = "operation_reslut")
	private String operationResult;//not run ,pass,fail,Not finish
	private String link;
	private String scriptLink;

	private String user;
	private String group;
	private String component;
	private String server;
	@Column(length = 5)
	private String EC;
	private String library;
	private String date;
	private String analysis;
	@Column(length = 20)
	private String failedType;
	private String failedJobLog;
	private String failedJobJCL;

	@Column(length = 5, name = "admin_version")
	private String adminVersion;

	public String getAdminVersion() {
		return adminVersion;
	}

	public void setAdminVersion(String adminVersion) {
		this.adminVersion = adminVersion;
	}

	public String getLibrary() {
		return library;
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}

	public String getFailedJobLog() {
		return failedJobLog;
	}

	public void setFailedJobLog(String failedJobLog) {
		this.failedJobLog = failedJobLog;
	}

	public String getFailedJobJCL() {
		return failedJobJCL;
	}

	public void setFailedJobJCL(String failedJobJCL) {
		this.failedJobJCL = failedJobJCL;
	}

	public void setLibrary(String library) {
		this.library = library;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getEC() {
		return EC;
	}

	public void setEC(String ec) {
		EC = ec;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	@Column(name = "test_description")
	private String testDescription;

	public String getTestDescription() {
		return testDescription;
	}

	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTCName() {
		return TCName;
	}

	public void setTCName(String tCName) {
		TCName = tCName;
	}

	public String getOperationResult() {
		return operationResult;
	}

	public void setOperationResult(String operationResult) {
		this.operationResult = operationResult;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getScriptLink() {
		return scriptLink;
	}

	public void setScriptLink(String scriptLink) {
		this.scriptLink = scriptLink;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getFailedType() {
		return failedType;
	}

	public void setFailedType(String failedType) {
		this.failedType = failedType;
	}

}
