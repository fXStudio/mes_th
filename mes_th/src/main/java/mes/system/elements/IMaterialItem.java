package mes.system.elements;

public interface IMaterialItem {

	public abstract void setNum(int num);

	public abstract int getNum();

	public abstract void setMaterial(IMaterial m);

	public abstract IMaterial getMaterial();

	public abstract void setSequence(int s);

	public abstract int getSequence();
}
