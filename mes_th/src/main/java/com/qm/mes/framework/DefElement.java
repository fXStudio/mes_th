package com.qm.mes.framework;

public abstract class DefElement implements IElement {

	private String id = "";

	private String descr = "";

	private String name = "";

	private String nameSpace = "";

	public String getDescr() {
		return descr;
	}

	public String getName() {
		return name;
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameSpace(String ns) {
		nameSpace = ns;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
