package mes.system.dao;

import mes.system.elements.IMaterialCharacter;

public interface IDAO_MaterialCharacter {

	String getSQL_innerElement(IMaterialCharacter e);

	String getSQL_queryElement(String name);

	String getSQL_queryElement(int id);

	/**
	 * @param name
	 *            物料特征名
	 * @return id 物料特征号<br>
	 *         name 物料特征名<br>
	 *         element_id 元素号
	 */
	String getSQL_queryElementAll(String name);

	/**
	 * @return id 物料特征号<br>
	 *         name 物料特征名<br>
	 *         element_id 元素号
	 */
	String getSQL_queryElementAll();

	/**
	 * 
	 * @param id
	 *            元素号<br>
	 * @return id 元素号 <br>
	 *         name 物料特征名
	 */
	String getSQL_queryCharactersById(int id);

}
