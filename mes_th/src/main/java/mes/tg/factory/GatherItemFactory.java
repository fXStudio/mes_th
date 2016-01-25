package mes.tg.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mes.framework.DataBaseType;
import mes.system.dao.DAOFactoryAdapter;
import mes.tg.bean.GatherItem;
import mes.tg.dao.IDAO_GatherItem;

public class GatherItemFactory {
	
	//日志
	private final Log log = LogFactory.getLog(GatherItemFactory.class);

	/**
	 * 创建GatherItem对象
	 * 
	 * @param item
	 * @param con
	 * @throws SQLException
	 */
	public void saveGatherItem(GatherItem item, Connection con)
			throws SQLException {
		IDAO_GatherItem dao_GatherItem = (IDAO_GatherItem) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_GatherItem.class);
		Statement stmt = con.createStatement();
		log.debug("创建采集点属性SQL语句："+dao_GatherItem.saveGatherItem(item));
		stmt.execute(dao_GatherItem.saveGatherItem(item));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}

	/**
	 * 通过序号删除GatherItem对象
	 * 
	 * @param id
	 * @param con
	 * @throws SQLException
	 */
	public void delGatherItemById(int id, Connection con) throws SQLException {
		IDAO_GatherItem dao_GatherItem = (IDAO_GatherItem) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_GatherItem.class);
		Statement stmt = con.createStatement();
		log.debug("通过采集点属性号删除采集点属性SQL语句："+dao_GatherItem.delGatherItemById(id));
		stmt.execute(dao_GatherItem.delGatherItemById(id));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}

	/**
	 * 通过序号取得GatherItem对象
	 * 
	 * @param id
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public GatherItem getGatherItemById(int id, Connection con)
			throws SQLException {
		IDAO_GatherItem dao_GatherItem = (IDAO_GatherItem) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_GatherItem.class);
		Statement stmt = con.createStatement();
		log.debug("通过采集点属性号查询采集点属性SQL语句："+dao_GatherItem.getGatherItemById(id));
		ResultSet rs = stmt.executeQuery(dao_GatherItem.getGatherItemById(id));
		GatherItem gi = null;
		if (rs.next()) {
			gi = new GatherItem();
			gi.setId(rs.getInt("int_id"));
			gi.setGatherId(rs.getInt("int_gatherid"));
			gi.setOrder(rs.getInt("int_order"));
			gi.setMaterielruleId(rs.getInt("int_materielruleid"));
			log.debug("采集点属性号："+rs.getInt("int_id")+"；采集点号："+rs.getInt("int_gatherid")+"；采集点属性顺序号："
					+rs.getInt("int_order")+"；采集点属性物料标识规则号："+rs.getInt("int_materielruleid"));
		}
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
		return gi;
	}

	/**
	 * 通过Gather序号取得GatherItem列表
	 * 
	 * @param id
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public List<GatherItem> getGatherItemByGid(int id, Connection con)
			throws SQLException {
		IDAO_GatherItem dao_GatherItem = (IDAO_GatherItem) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_GatherItem.class);
		List<GatherItem> list = new ArrayList<GatherItem>();
		Statement stmt = con.createStatement();
		log.debug("通过采集点号查询采集点属性SQL语句："+dao_GatherItem.getGatherItemByGid(id));
		ResultSet rs = stmt.executeQuery(dao_GatherItem.getGatherItemByGid(id));
		log.debug("通过采集点号查询采集点属性列表--");
		while (rs.next()) {
			GatherItem gi = new GatherItem();
			gi.setId(rs.getInt("int_id"));
			gi.setGatherId(rs.getInt("int_gatherid"));
			gi.setOrder(rs.getInt("int_order"));
			gi.setMaterielruleId(rs.getInt("int_materielruleid"));
			log.debug("采集点属性号："+rs.getInt("int_id")+"；采集点号："+rs.getInt("int_gatherid")+"；采集点属性顺序号："
					+rs.getInt("int_order")+"；采集点属性物料标识规则号："+rs.getInt("int_materielruleid"));
			list.add(gi);
		}
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
		return list;
	}

	/**
	 * 通过Gather序号，删除关联的GatherItem
	 * 
	 * @param id
	 * @param con
	 * @throws SQLException
	 */
	public void delGatherItemByGid(int id, Connection con) throws SQLException {
		IDAO_GatherItem dao_GatherItem = (IDAO_GatherItem) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_GatherItem.class);
		Statement stmt = con.createStatement();
		log.debug("通过采集点号删除采集点属性SQL语句："+dao_GatherItem.delGatherItemByGid(id));
		stmt.execute(dao_GatherItem.delGatherItemByGid(id));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}

	/**
	 * 更新GatherItem对象
	 * 
	 * @param item
	 * @param con
	 * @throws SQLException
	 */
	public void updateGatherItem(GatherItem item, Connection con)
			throws SQLException {
		IDAO_GatherItem dao_GatherItem = (IDAO_GatherItem) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_GatherItem.class);
		Statement stmt = con.createStatement();
		log.debug("更新采集点属性SQL语句："+dao_GatherItem.saveGatherItem(item));
		stmt.execute(dao_GatherItem.updateGatherItem(item));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}

	/**
	 * 检测生产单元中是否已经存在此顺序号
	 * @param gatherid
	 * @param order
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public int checkGatherItemByOrder(int gatherid, int order, Connection con)
			throws SQLException {
		IDAO_GatherItem dao_GatherItem = (IDAO_GatherItem) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_GatherItem.class);
		Statement stmt = con.createStatement();
		int count = 0;
		log.debug("查询该采集点号、该顺序号采集点属性个数SQL语句："+
				dao_GatherItem.checkGatherItemCountByorder(gatherid, order));
		ResultSet rs = stmt.executeQuery(dao_GatherItem
				.checkGatherItemCountByorder(gatherid, order));
		if (rs.next()) {
			count = rs.getInt(1);
			log.debug("查询该采集点号、该顺序号采集点属性个数为："+count);
		}
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
		return count;
	}
	
	/**
	 * 检测生产单元中是否存在此子物料标识规则号
	 * @param gatherid
	 * @param materialId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public int checkGatherItemBySubMaterialId(int gatherid, int materialId,
			Connection con) throws SQLException {
		IDAO_GatherItem dao_GatherItem = (IDAO_GatherItem) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				IDAO_GatherItem.class);
		Statement stmt = con.createStatement();
		int count = 0;
		log.debug("查询该采集点号、该物料标识规则号的采集点属性个数SQL语句："+
				dao_GatherItem.checkGatherItemCountBySubMaterialId(gatherid, materialId));
		ResultSet rs = stmt.executeQuery(dao_GatherItem
				.checkGatherItemCountBySubMaterialId(gatherid, materialId));
		if (rs.next()) {
			count = rs.getInt(1);
			log.debug("查询该采集点号、该顺序号采集点属性个数为："+count);
		}
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
		return count;
	}

}
