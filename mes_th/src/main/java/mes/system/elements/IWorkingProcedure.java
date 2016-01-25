package mes.system.elements;

public interface IWorkingProcedure extends IElement {

	public String getExplain();

	public abstract void setExplain(String explain);

	public abstract ITechnicsCapability[] getRequestCapabilities();

	public abstract void setRequestCapabilities(ITechnicsCapability[] rc);

	public abstract IProductionUnit[] getProductionUnits();

	public abstract void setProductionUnits(IProductionUnit[] pus);

	public abstract void setMaterials(IMaterialItem[] mc);

	public abstract IMaterialItem[] getMaterials();
}
