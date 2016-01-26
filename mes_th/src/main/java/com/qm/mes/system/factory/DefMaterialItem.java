package com.qm.mes.system.factory;

import com.qm.mes.system.elements.IMaterial;
import com.qm.mes.system.elements.IMaterialItem;

 class DefMaterialItem extends AElement implements IMaterialItem {
 
	private int num;
	 
	private IMaterial material;
	 
	private int sequence;

	public IMaterial getMaterial() {
		return material;
	}

	public void setMaterial(IMaterial material) {
		this.material = material;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	 
}
 
