package com.qm.mes.framework;

/**
 * 服务项
 * 
 * @author 张光磊 2007-6-7
 */
class DefServiceItem implements IServiceItem {

	/**
	 * 名
	 */
	private String name;

	/**
	 * 描述
	 */
	private String descr;

	public DefServiceItem(String name, String descr) {
		super();
		this.name = name;
		this.descr = descr;
	}

	/**
	 * @return the descr
	 */
	public String getDescr() {
		return descr;
	}

	/**
	 * @param descr
	 *            the descr to set
	 */
	public void setDescr(String descr) {
		this.descr = descr;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
