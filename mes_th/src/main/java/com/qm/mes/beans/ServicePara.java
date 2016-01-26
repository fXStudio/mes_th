/**
 * 
 */
package com.qm.mes.beans;

/**
 * @author lida
 * 
 */
public class ServicePara {
	private String serviceid = null;

	private String servicename = null;

	private String paraname = null;

	private String paratype = null;

	public String getParaname() {
		return paraname;
	}

	public void setParaname(String paraname) {
		this.paraname = paraname;
	}

	public String getParatype() {
		return paratype;
	}

	public void setParatype(String paratype) {
		this.paratype = paratype;
	}

	public String getServiceid() {
		return serviceid;
	}

	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}
}
