/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mes.tg.factory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mes.tg.bean.GatherItemExt;
import mes.tg.dao.IDAO_GatherItemExt;
import mes.system.dao.DAOFactoryAdapter;
import mes.framework.DataBaseType;

/**
 * GatherItemExt工厂类
 *
 * @author YuanPeng
 */
public class GatherItemExtFactory {

	//日志
	private final Log log = LogFactory.getLog(GatherItemExtFactory.class);
	
    /**
	 * 创建GatherItemExt对象
	 *
	 * @param gatherItemExt
	 * @param con
	 * @throws SQLException
	 */
	public void saveGatherItemExt(GatherItemExt gatherItemExt, Connection con)
			throws SQLException {
		IDAO_GatherItemExt dao_GatherItemExt = (IDAO_GatherItemExt) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_GatherItemExt.class);
		Statement stmt = con.createStatement();
		log.debug("创建采集点扩展属性SQL语句："+dao_GatherItemExt.saveGatherItemExt(gatherItemExt));
		stmt.execute(dao_GatherItemExt.saveGatherItemExt(gatherItemExt));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}

    /**
	 * 通过序号删除GatherItemExt对象
	 *
	 * @param id
     *          序号（唯一）
	 * @param con
     *          数据库连接对象
	 * @throws SQLException
     *                     SQL异常
	 */
	public void delGatherItemExtById(int id, Connection con) throws SQLException {
		IDAO_GatherItemExt dao_GatherItemExt = (IDAO_GatherItemExt) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_GatherItemExt.class);
		Statement stmt = con.createStatement();
		log.debug("删除采集点扩展属性SQL语句："+dao_GatherItemExt.delGatherItemExtById(id));
		stmt.execute(dao_GatherItemExt.delGatherItemExtById(id));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}

    /**
     * 通过采集点ID删除对应的扩展属性
     *
     * @param gather_id
     * @return
     */
    public void delGatherItemExtByGatherId(int gather_id, Connection con) throws SQLException {
		IDAO_GatherItemExt dao_GatherItemExt = (IDAO_GatherItemExt) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_GatherItemExt.class);
		Statement stmt = con.createStatement();
		log.debug("通过采集点ID删除对应的采集点扩展属性SQL语句："+dao_GatherItemExt.delGatherItemExtByGatherId(gather_id));
		stmt.execute(dao_GatherItemExt.delGatherItemExtByGatherId(gather_id));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}

    /**
	 * 通过序号取得GatherItemExt对象
	 *
	 * @param id
     *          序号（唯一）
	 * @param con
     *          数据库连接对象
	 * @return GatherItemExt
     *                  通过ID查询得到的GatherItemExt对象
	 * @throws SQLException
	 */
	public GatherItemExt getGatherItemExtById(int id, Connection con)
			throws SQLException {
		IDAO_GatherItemExt dao_GatherItemExt = (IDAO_GatherItemExt) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_GatherItemExt.class);
		Statement stmt = con.createStatement();
		log.debug("通过采集点扩展属性号查询采集点扩展属性对象SQL语句："+dao_GatherItemExt.getGatherItemExtById(id));
		ResultSet rs = stmt.executeQuery(dao_GatherItemExt.getGatherItemExtById(id));
		GatherItemExt gatherItemExt = null;
		if (rs.next()) {
			gatherItemExt = new GatherItemExt();
			gatherItemExt.setId(rs.getInt("int_id"));
			gatherItemExt.setGatherId(rs.getInt("int_gather_id"));
			gatherItemExt.setOrder(rs.getInt("int_order"));
			gatherItemExt.setName(rs.getString("str_name"));
			log.debug("采集点扩展属性号："+rs.getInt("int_id")+"；采集点号："+rs.getInt("int_gather_id")+
					"；采集点扩展属性顺序号："+rs.getInt("int_order")+"；采集点扩展属性名："+rs.getString("str_name"));
		}
        if (stmt != null) {
			stmt.close();
			stmt = null;
		}
        if(rs != null){
            rs.close();
            rs = null;
        }
        return gatherItemExt;
	}

    /**
	 * 通过扩展属性名查询GatherItemExt
	 * @param str_name
	 * @param con
	 * @return GatherItemExt
	 * @throws SQLException
	 */
	public GatherItemExt getGatherItemExtByName(String str_name,Connection con) throws SQLException{
		GatherItemExt g = null;
		IDAO_GatherItemExt dao_GatherItemExt = (IDAO_GatherItemExt) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), IDAO_GatherItemExt.class);
		Statement stmt = con.createStatement();
		log.debug("通过扩展属性名查询采集点扩展属性SQL语句："+dao_GatherItemExt.getGatherItemExtByName(str_name));
		ResultSet rs = stmt.executeQuery(dao_GatherItemExt.getGatherItemExtByName(str_name));
		if(rs.next()){
			g = new GatherItemExt();
			g.setId(rs.getInt("int_id"));
			g.setName(rs.getString("str_name"));
			g.setGatherId(rs.getInt("int_gather_id"));
			g.setOrder(rs.getInt("int_order"));
			log.debug("采集点扩展属性号："+rs.getInt("int_id")+"；采集点号："+rs.getInt("int_gather_id")+
					"；采集点扩展属性顺序号："+rs.getInt("int_order")+"；采集点扩展属性名："+rs.getString("str_name"));
		}
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
        if(rs != null){
            rs.close();
            rs = null;
        }
		return g;
	}

    /**
	 * 更新GatherItemExt对象
	 *
	 * @param gatherItemExt
     *                  gatherItemExt对象
	 * @param con
     *          数据库连接对象
	 * @throws SQLException
     *                  SQL异常
	 */
	public void updateGatherItemExt(GatherItemExt gatherItemExt, Connection con)
			throws SQLException {
		IDAO_GatherItemExt dao_GatherItemExt = (IDAO_GatherItemExt) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_GatherItemExt.class);
		Statement stmt = con.createStatement();
		log.debug("更新采集点扩展属性对象SQL语句："+dao_GatherItemExt.updateGatherItemExt(gatherItemExt));
		stmt.execute(dao_GatherItemExt.updateGatherItemExt(gatherItemExt));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}

    /**
	 * 查询GatherItemExt表中所有记录
	 *
	 * @param con
     *          数据库连接对象
	 * @return List<GatherItemExt>
     *                  GatherItemExt格式的List
	 * @throws SQLException
     *                  SQL异常
	 */
	public List<GatherItemExt> getAllGatherItemExt( Connection con)
			throws SQLException {
		IDAO_GatherItemExt dao_GatherItemExt = (IDAO_GatherItemExt) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_GatherItemExt.class);
		List<GatherItemExt> list = new ArrayList<GatherItemExt>();
		Statement stmt = con.createStatement();
		log.debug("查询所有采集点扩展属性记录SQL语句："+dao_GatherItemExt.getAllGatherItemExt());
		ResultSet rs = stmt.executeQuery(dao_GatherItemExt.getAllGatherItemExt());
		log.debug("所有采集点扩展属性列表--");
		while (rs.next()) {
			GatherItemExt gatherItemExt = new GatherItemExt();
			gatherItemExt.setId(rs.getInt("int_id"));
			gatherItemExt.setGatherId(rs.getInt("int_gatherid"));
			gatherItemExt.setOrder(rs.getInt("int_order"));
			gatherItemExt.setName(rs.getString("str_name"));
			list.add(gatherItemExt);
			log.debug("采集点扩展属性号："+rs.getInt("int_id")+"；采集点号："+rs.getInt("int_gather_id")+
					"；采集点扩展属性顺序号："+rs.getInt("int_order")+"；采集点扩展属性名："+rs.getString("str_name"));
		}
        if (stmt != null) {
			stmt.close();
			stmt = null;
		}
        if(rs != null){
            rs.close();
            rs = null;
        }
		return list;
	}

    /**
     * 通过采集点序号查询得到GatherItemExt集合
     *
     * @param int_gather_id
     * @param con
     * @return List<GatherItemExt>
     * @throws java.sql.SQLException
     */
    public List<GatherItemExt> getGatherItemExtByGatherId(int int_gather_id,Connection con)
			throws SQLException {
		IDAO_GatherItemExt dao_GatherItemExt = (IDAO_GatherItemExt) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_GatherItemExt.class);
		List<GatherItemExt> list = new ArrayList<GatherItemExt>();
		Statement stmt = con.createStatement();
		log.debug("通过采集点序号查询得到采集点扩展属性集合SQL语句："+dao_GatherItemExt.getGatherItemExtByGatherId(int_gather_id));
		ResultSet rs = stmt.executeQuery(dao_GatherItemExt.getGatherItemExtByGatherId(int_gather_id));
		log.debug("通过采集点序号查询得到采集点扩展属性列表--");
		while (rs.next()) {
			GatherItemExt gatherItemExt = new GatherItemExt();
			gatherItemExt.setId(rs.getInt("int_id"));
			gatherItemExt.setGatherId(rs.getInt("int_gather_id"));
			gatherItemExt.setOrder(rs.getInt("int_order"));
			gatherItemExt.setName(rs.getString("str_name"));
			list.add(gatherItemExt);
			log.debug("采集点扩展属性号："+rs.getInt("int_id")+"；采集点号："+rs.getInt("int_gather_id")+
					"；采集点扩展属性顺序号："+rs.getInt("int_order")+"；采集点扩展属性名："+rs.getString("str_name"));
		}
        if (stmt != null) {
			stmt.close();
			stmt = null;
		}
        if(rs != null){
            rs.close();
            rs = null;
        }
		return list;
	}


}
