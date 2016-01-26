package com.qm.mes.system.dao;

import com.qm.mes.system.elements.IMaterialType;

/**
 * 物料类型的SQL语句对象
 * 
 * @author 张光磊 2008-3-6
 */
public interface IDAO_MaterialType {

	String getSQL_innerElement(IMaterialType materialtype);

	String getSQL_queryElement(String name);

	String getSQL_UpdateElement(IMaterialType materialtype);

	String getSQL_queryElement(int id);

	/**
	 * @param name
	 *            物料类型名
	 * @return id 物料类型号<br>
	 *         name 物料类型名<br>
	 *         element_id 元素号
	 */
	String getSQL_queryElementAll(String name);

	/**
	 * @return id 物料类型号<br>
	 *         name 物料类型名<br>
	 *         element_id 元素号
	 */
	String getSQL_queryElementAll();

	/**
	 * @param name
	 *            物料类型名
	 * @return id 物料类型号 <br>
	 *         name 物料类型名 <br>
	 *         element_id 元素号
	 */
	String getSQL_queryElementOtherType(int id);

	/**
	 * @param id
	 *            物料类型号 <br>
	 *            parent_id 父物料类型号<br>
	 * @return id 物料类型号 <br>
	 *         name 物料类型名 <br>
	 *         element_id 元素号
	 */
	//String getSQL_queryElementNotOwner(int id, int parent_id);

	/**
	 * @param name
	 *            物料类型名 <br>
	 * @return count 个数
	 */
	String getSQL_queryElementCount(String name);

}
