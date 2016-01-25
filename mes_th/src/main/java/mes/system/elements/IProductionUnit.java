package mes.system.elements;

public interface IProductionUnit extends IElement {

	public abstract IProductionUnitType getType();

	public abstract void setType(IProductionUnitType type);

	public abstract IProductionUnit[] getTarget();

	public abstract void setTarget(IProductionUnit[] target);

	public abstract void setSubProductionUnits(IProductionUnit[] pus);

	public abstract IProductionUnit[] getSubProductionUnits();

	public abstract IProductionUnit getParent();

	public abstract void setParent(IProductionUnit parent);

	public abstract void setCapabilities(ITechnicsCapability[] caps);

	public abstract ITechnicsCapability[] getCapabilities();
}
