package com.qm.mes.tg.dao;

import com.qm.mes.tg.bean.GatherItem;

public interface IDAO_GatherItem {

	/**
	 * 创建GatherItem对象
	 * 
	 * @param item
	 *            实体对象
	 * @return 创建GatherItem对象的sql语句
	 */
	String saveGatherItem(GatherItem item);

	/**
	 * 通过序号删除GatherItem对象
	 * 
	 * @param id
	 *            序号
	 * @return 删除的sql语句
	 */
	String delGatherItemById(int id);

	/**
	 * 通过序号取得GatherItem对象
	 * 
	 * @param id
	 *            序号
	 * @return 取得对象的sql语句
	 */
	String getGatherItemById(int id);

	/**
	 * 通过Gather序号取得GatherItem列表
	 * 
	 * @param id
	 *            Gather序号
	 * @return 取得对象列表的sql语句
	 */
	String getGatherItemByGid(int id);

	/**
	 * 通过Gather序号，删除关联的GatherItem
	 * 
	 * @param id
	 *            Gather序号
	 * @return 通过id删除的sql语句
	 */
	String delGatherItemByGid(int id);

	/**
	 * 更新GatherItem对象
	 * 
	 * @param item
	 *            实体对象
	 * @return 更新实体对象的sql语句
	 */
	String updateGatherItem(GatherItem item);

	/**
	 * 检测采集点属性gatherItem表中是否已有此顺序号
	 * 
	 * @param order
	 *            顺序号 <br>
	 * @param gatherid
	 *            采集点号 <br>
	 * @return 顺序号个数
	 */
	String checkGatherItemCountByorder(int gatherid, int order);

	/**
	 * 检测采集点属性gatherItem表中是否已有此物料标识规则号
	 * 
	 * @param materialId
	 *            子物料标识规则号 <br>
	 * @param gatherid
	 *            采集点号 <br>
	 * @return 顺序号个数
	 */
	String checkGatherItemCountBySubMaterialId(int gatherid, int materialId);

}
