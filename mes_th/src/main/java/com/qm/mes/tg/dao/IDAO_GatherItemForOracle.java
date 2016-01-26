package com.qm.mes.tg.dao;

import com.qm.mes.tg.bean.GatherItem;

public class IDAO_GatherItemForOracle implements IDAO_GatherItem {
	/**
	 * 通过Gather序号，删除关联的GatherItem
	 * 
	 * @param id
	 *            Gather序号
	 * @return 通过id删除的sql语句
	 */
	public String delGatherItemByGid(int id) {
		String sql = "delete from t_tg_GatherItem where int_gatherid = " + id;
		return sql;
	}

	/**
	 * 通过序号删除GatherItem对象
	 * 
	 * @param id
	 *            序号
	 * @return 删除的sql语句
	 */
	public String delGatherItemById(int id) {
		String sql = "delete from t_tg_GatherItem where int_id = " + id;
		return sql;
	}

	/**
	 * 通过Gather序号取得GatherItem列表
	 * 
	 * @param id
	 *            Gather序号
	 * @return 取得对象列表的sql语句
	 */
	public String getGatherItemByGid(int id) {
		String sql = "select int_id,int_gatherid,int_order,int_materielruleid from t_tg_GatherItem where int_gatherid = "
				+ id + " order by int_order";
		return sql;
	}

	/**
	 * 通过序号取得GatherItem对象
	 * 
	 * @param id
	 *            序号
	 * @return 取得对象的sql语句
	 */
	public String getGatherItemById(int id) {
		String sql = "select int_id,int_gatherid,int_order,int_materielruleid from t_tg_GatherItem where int_id = "
				+ id + " order by int_order";
		return sql;
	}

	/**
	 * 创建GatherItem对象
	 * 
	 * @param item
	 *            实体对象
	 * @return 创建GatherItem对象的sql语句
	 */
	public String saveGatherItem(GatherItem item) {
		String sql = "insert into t_tg_GatherItem(int_id,int_gatherid,int_order,int_materielruleid) "
				+ "values(seq_tg_GatherItem.nextval,"
				+ item.getGatherId()
				+ "," + item.getOrder() + "," + item.getMaterielruleId() + ")";
		return sql;
	}

	/**
	 * 更新GatherItem对象
	 * 
	 * @param item
	 *            实体对象
	 * @return 更新实体对象的sql语句
	 */
	public String updateGatherItem(GatherItem item) {
		String sql = "update t_tg_GatherItem set int_gahterid = "
				+ item.getGatherId() + " , int_order = " + item.getOrder()
				+ " , int_materielrulid = " + item.getMaterielruleId()
				+ "where int_id = " + item.getId();
		return sql;
	}

	public String checkGatherItemCountByorder(int gatherid, int order) {
		String sql = "select count(*) from  t_tg_GatherItem "
				+ " where int_order=" + order + " and int_gatherid=" + gatherid
				+ "";
		return sql;
	}
	
	public String checkGatherItemCountBySubMaterialId(int gatherid, int materialId) {
		String sql = "select count(*) from  t_tg_GatherItem "
				+ " where INT_MATERIELRULEID=" + materialId + " and int_gatherid=" + gatherid
				+ "";
		return sql;
	}

}
