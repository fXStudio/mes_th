package mes.system.factory;

import mes.system.elements.IBillOfMaterial;
import mes.system.elements.IMaterial;

class DefBillOfMaterial extends AElement implements IBillOfMaterial {
 
	private IMaterial material;
	 
	private IBillOfMaterial parent;
	 
	private int num;

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

	public IBillOfMaterial getParent() {
		return parent;
	}

	public void setParent(IBillOfMaterial parent) {
		this.parent = parent;
	}

	 
	 
}
 
