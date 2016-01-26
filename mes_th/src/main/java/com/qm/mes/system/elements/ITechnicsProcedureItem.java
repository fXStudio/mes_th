package com.qm.mes.system.elements;

public interface ITechnicsProcedureItem extends IElement {

	public abstract ITechnicsProcedureItem getExceptionProcedure();

	public abstract void setExceptionProcedure(ITechnicsProcedureItem item);

	public abstract IWorkingProcedure getWorkingProcedure();

	public abstract void setWorkingProcedure(IWorkingProcedure wp);

	public abstract IProductionUnit getProductionUnit();

	public abstract void setProductionUnit(IProductionUnit pu);

	public abstract void setMaterials(IMaterialItem[] mc);

	public abstract IMaterialItem[] getMaterials();

	public abstract IMaterialidentify getNewIdentify();

	public abstract void setNewIdentify(IMaterialidentify identify);

	public abstract boolean isLock();

	public abstract void setLock(boolean lock);
}
