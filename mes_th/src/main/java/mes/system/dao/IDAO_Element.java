package mes.system.dao;

import mes.system.elements.IElement;

public interface IDAO_Element {

	/**
	 * 插入元素基本信息：
	 * <p>
	 * discard：的值置“0”；<br>
	 * version：版本号“1”<br>
	 * updateDatetime：取系统时间
	 * 
	 * @param name
	 * @param description
	 * @param updateUserId
	 * @return
	 */
	public String getSQL_innerElement(String name, String description,
			int updateUserId);

	public String getSQL_innerElement(IElement element);

	public String getSQL_updateElement(IElement element);

	/**
	 * 更新元素基本信息：
	 * <p>
	 * discard：的值在删除操作时候修改；<br>
	 * version：版本号自动更新，不允许修改<br>
	 * updateDatetime：取系统时间
	 * 
	 * @param name
	 *            根据name标识要修改的对象
	 * @param description
	 * @param updateUserId
	 * @return 更新元素基本信息使用的SQL语句
	 */
	public String getSQL_updateElement(String name, String description,
			int updateUserId);

	/**
	 * 删除元素——将弃用值置“1”
	 * 
	 * @param id
	 *            目标元素id
	 * @return 返回删除目标物料类型的sql语句
	 */
	public String getSQL_deleteElement(int id);

	/**
	 * 删除元素——将弃用值置“1”
	 * 
	 * @param name
	 *            元素名
	 * @return 返回删除目标物料类型的sql语句
	 */
	public String getSQL_deleteElement(String name);

	/**
	 * 通过name值查找元素
	 * 
	 * @param name
	 * @return 返回sql语句
	 */
	public String getSQL_queryElement(String name);
	
	public String getSQL_queryElementCount(String name);
}
