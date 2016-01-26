package com.qm.mes.system.factory;

import com.qm.mes.system.elements.IMaterialType;

class DefMaterialType extends AElement implements IMaterialType {

	private int parentId;

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return super.toString() + "\n\tDefMaterialType¡ª¡ªparentid:" + parentId;
	}

}
