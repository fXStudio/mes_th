package com.qm.mes.system.factory;

import com.qm.mes.system.elements.IProductionUnit;
import com.qm.mes.system.elements.IProductionUnitType;
import com.qm.mes.system.elements.ITechnicsCapability;

 class DefProductionUnit extends AElement implements IProductionUnit {
 
	private IProductionUnit[] subProductionUnits;
	 
	private ITechnicsCapability[] capabilities;
	 
	private IProductionUnit parent;
	 
	private IProductionUnit[] target;

	public ITechnicsCapability[] getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(ITechnicsCapability[] capabilities) {
		this.capabilities = capabilities;
	}

	public IProductionUnit getParent() {
		return parent;
	}

	public void setParent(IProductionUnit parent) {
		this.parent = parent;
	}


	public IProductionUnit[] getTarget() {
		return target;
	}

	public void setTarget(IProductionUnit[] target) {
		this.target = target;
	}

	public IProductionUnitType getType() {
		return null;
	}

	public void setType(IProductionUnitType type) {
	}
                                    	


	public void setSubProductionUnits(IProductionUnit[] subProductionUnits) {
		this.subProductionUnits = subProductionUnits;
	}

	public IProductionUnit[] getSubProductionUnits() {
		return subProductionUnits;
	}
	
	 
}
 
