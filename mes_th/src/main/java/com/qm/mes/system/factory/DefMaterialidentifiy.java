package com.qm.mes.system.factory;

import com.qm.mes.system.elements.IMaterialidentify;

class DefMaterialidentifiy extends AElement implements IMaterialidentify {

	private int codeLength = 1;

	public int getCodeLength() {
		return codeLength;
	}

	public void setCodeLength(int codeLength) {
		this.codeLength = codeLength;
	}

	public String toString() {
		return super.toString() + "\n\tDefMaterialidentifiy¡ª¡ªcodeLength:"
				+ codeLength;
	}
}
