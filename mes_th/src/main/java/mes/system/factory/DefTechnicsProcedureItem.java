package mes.system.factory;

import mes.system.elements.IMaterialItem;
import mes.system.elements.IMaterialidentify;
import mes.system.elements.IProductionUnit;
import mes.system.elements.ITechnicsProcedureItem;
import mes.system.elements.IWorkingProcedure;

 class DefTechnicsProcedureItem extends AElement implements ITechnicsProcedureItem {
 
	private int sequence;
	 
	private ITechnicsProcedureItem exceptionProcedure;
	 
	private IWorkingProcedure workingProcedure;
	 
	private IProductionUnit productionUnit;
	 
	private IMaterialItem[] materials;
	 
	private IMaterialidentify newIdentify = null;
	 
	private boolean lock;

	public ITechnicsProcedureItem getExceptionProcedure() {
		return exceptionProcedure;
	}

	public void setExceptionProcedure(ITechnicsProcedureItem exceptionProcedure) {
		this.exceptionProcedure = exceptionProcedure;
	}

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	public IMaterialItem[] getMaterials() {
		return materials;
	}

	public void setMaterials(IMaterialItem[] materials) {
		this.materials = materials;
	}

	public IMaterialidentify getNewIdentify() {
		return newIdentify;
	}

	public void setNewIdentify(IMaterialidentify newIdentify) {
		this.newIdentify = newIdentify;
	}

	public IProductionUnit getProductionUnit() {
		return productionUnit;
	}

	public void setProductionUnit(IProductionUnit productionUnit) {
		this.productionUnit = productionUnit;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public IWorkingProcedure getWorkingProcedure() {
		return workingProcedure;
	}

	public void setWorkingProcedure(IWorkingProcedure workingProcedure) {
		this.workingProcedure = workingProcedure;
	}
}
 
