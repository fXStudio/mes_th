package com.qm.mes.pm.dao;

/**
 * Bom  DAO接口类
 * 
 * @author YuanPeng
 *
 */
public interface DAO_Bom {
	
	/**
	 * 通过BOM序号查询BOM对象
	 * 
	 * @param id	BOM序号
	 * @return	BOM对象
	 */
	public String getBomById(int id);
	
	/**
	 * 通过组件标示查询BOM对象列表
	 * 
	 * @param component 组件标示
	 * @return  BOM对象列表
	 */
	public String getBomsBycomponent(String component);
	
	/**
	 *  通过上级序号标示查询BOM对象
	 * 
	 * @param parentid 上级序号标示
	 * @return BOM对象
	 */
	public String getBomsByParentId(int parentid);
	
	/**
	 * 	倒叙查询所有Bom
	 * 
	 * @return  倒叙查询所有Bom列表  
	 */
	public String getAllBomsByDESC();
	
	/**
	 * 通过组件标示查询下级BOM子项
	 * 
	 * @param Component	组件标示
	 * @return 通过组件标示查询下级BOM子项列表
	 */
	public String getBomByComponent(String Component);
	
	/**
	 * 查询所有BOM组件标识按组件标识排序
	 * 
	 * @return 查询所有BOM组件标识按组件标识排序列表
	 */
	public String getBomsGroupByComponent();
	
}
