package mes.system.factory;

import mes.system.elements.IMaterialItem;
import mes.system.elements.IProductionUnit;
import mes.system.elements.ITechnicsCapability;
import mes.system.elements.IWorkingProcedure;

 class DefWorkingProcedure extends AElement implements IWorkingProcedure {
 
	private String explain;
	 
	private ITechnicsCapability[] requestCapabilities;
	 
	private IProductionUnit[] productionUnits;
	 
	private IMaterialItem[] materials;

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public IMaterialItem[] getMaterials() {
		return materials;
	}

	public void setMaterials(IMaterialItem[] materials) {
		this.materials = materials;
	}

	public IProductionUnit[] getProductionUnits() {
		return productionUnits;
	}

	public void setProductionUnits(IProductionUnit[] productionUnits) {
		this.productionUnits = productionUnits;
	}

	public ITechnicsCapability[] getRequestCapabilities() {
		return requestCapabilities;
	}

	public void setRequestCapabilities(ITechnicsCapability[] requestCapabilities) {
		this.requestCapabilities = requestCapabilities;
	}
	 
	
	 
}
 
