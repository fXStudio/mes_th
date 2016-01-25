package mes.system.elements;

public interface IBillOfMaterial extends IElement {

	public abstract IMaterial getMaterial();

	public abstract void setMaterial(IMaterial m);

	public abstract int getNum();

	public abstract void setNum(int n);

	public abstract IBillOfMaterial getParent();

	public abstract void setParent(IBillOfMaterial parent);
}
