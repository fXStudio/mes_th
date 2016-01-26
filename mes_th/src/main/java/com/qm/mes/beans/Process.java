package com.qm.mes.beans;

public class Process {
	private String description;

	private String id;

	private String name;

	private String namespace;

	private String namespaceid;

	public String getNamespaceid() {
		return namespaceid;
	}

	public void setNamespaceid(String namespaceid) {
		this.namespaceid = namespaceid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
}
