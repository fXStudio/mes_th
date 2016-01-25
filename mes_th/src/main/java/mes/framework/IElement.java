package mes.framework;

/**
 * 基础接口
 * 
 * @author 张光磊 2007-6-21
 */
interface IElement {
	public String getId();

	public String getName();

	public String getNameSpace();

	public String getDescr();

	void setDescr(String descr);

	void setName(String name);

	void setNameSpace(String ns);

	void setId(String id);

}
