/**
 * 
 */
package com.qm.mes.beans;

/**
 * @author lida
 * 
 */
public class ProcessService {
	// 流程号
	private String processid = null;

	// 运行号
	private String sid = null;

	// 流程名
	private String processname = null;

	// 服务名
	private String servername = null;

	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProcessname() {
		return processname;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	public String getServername() {
		return servername;
	}

	public void setServername(String servername) {
		this.servername = servername;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
}
