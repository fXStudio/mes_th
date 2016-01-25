package mes.system.dao;

import mes.system.elements.IMaterialidentify;

public interface IDAO_MaterialIdentify {

	String getSQL_queryElement(int id);

	String getSQL_queryElement(String name);

	String getSQL_innerElement(IMaterialidentify materialidentify);

	String getSQL_UpdateElement(IMaterialidentify materialidentify);

	/**
	 * @param name
	 *            物料标识名
	 * @return id 物料标识号<br>
	 *         name 物料标识名<br>
	 *         element_id 元素号
	 */
	String getSQL_queryElementAll(String name);

	/**
	 * @return id 物料标识号<br>
	 *         name 物料标识名<br>
	 *         element_id 元素号
	 */
	String getSQL_queryElementAll();

	/**
	 * 
	 * @param id
	 *            元素号<br>
	 * @return id 元素号 <br>
	 *         name 物料标识名
	 */
	String getSQL_queryIdentifyById(int id);

}
