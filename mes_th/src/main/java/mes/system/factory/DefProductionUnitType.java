package mes.system.factory;

import mes.system.elements.IProductionUnitType;

 class DefProductionUnitType extends AElement implements IProductionUnitType {
 
	private IProductionUnitType parent;

	public IProductionUnitType getParent() {
		return parent;
	}

	public void setParent(IProductionUnitType parent) {
		this.parent = parent;
	}
	 

	 
}
 
