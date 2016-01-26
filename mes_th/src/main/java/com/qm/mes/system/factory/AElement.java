package com.qm.mes.system.factory;

import java.util.Date;

import com.qm.mes.system.elements.IElement;


abstract class AElement implements IElement {

	@Override
	public String toString() {
		return super.toString() + "\nAElement¡ª¡ªid:" + id + ",name=" + name
				+ ",discard=" + discard + ",version=" + version
				+ ",updateDateTime=" + updateDateTime + ",updateUserId:"
				+ updateUserId + ",description=" + description;
	}

	private int id = -1;

	private String name = "";

	private Date updateDateTime;

	private String description = "";

	private int updateUserId = -1;

	private boolean discard = true;

	private int version = 1;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDiscard() {
		return discard;
	}

	public void setDiscard(boolean discard) {
		this.discard = discard;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public int getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(int updateUserId) {
		this.updateUserId = updateUserId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
