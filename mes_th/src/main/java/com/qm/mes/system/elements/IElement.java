package com.qm.mes.system.elements;

import java.util.Date;

public interface IElement {

	public abstract int getId();

	/**
	 * 设置元素id——唯一表示一个元素<br>
	 * <center><font color=ff0000 ><b>注：这个方法一定要慎用。</b></font></center>
	 * 
	 * @param id
	 *            元素id
	 */
	public abstract void setId(int id);

	public abstract String getName();

	/**
	 * 设置元素id——唯一表示一个元素<br>
	 * <center><font color=ff0000 ><b>注：这个方法一定要慎用。</b></font></center>
	 * 
	 * @param name
	 *            元素name
	 */
	public abstract void setName(String name);

	public abstract String getDescription();

	public abstract void setDescription(String desc);

	public abstract boolean isDiscard();

	public abstract void setDiscard(boolean discard);

	public abstract void setUpdateDateTime(Date date);

	public abstract Date getUpdateDateTime();

	public abstract int getUpdateUserId();

	public abstract void setUpdateUserId(int id);

	int getVersion();

	void setVersion(int version);
}
