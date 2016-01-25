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
import mes.tg.bean.Gather;
import mes.tg.dao.IDAO_Gather;
import mes.tg.dao.IDAO_GatherItem;

public class GatherFactory {
	
	//日志
	private final Log log = LogFactory.getLog(GatherFactory.class);
	
	/**
	 * 创建gather
	 * @param gather
	 * @param con
	 * @throws SQLException
	 */
	public void saveGather(Gather gather,Connection con) throws SQLException{
		IDAO_Gather dao_Gather = (IDAO_Gather) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), IDAO_Gather.class);
		Statement stmt = con.createStatement();
		log.debug("创建采集点SQL语句："+dao_Gather.saveGather(gather));
		stmt.execute(dao_Gather.saveGather(gather));
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
	}
	
	/**
	 * 通过序号查询gather
	 * @param id
	 * @param con
	 * @return
	 * @throws SQLException 
	 */
	public Gather getGatherById(int id,Connection con) throws SQLException{
		Gather g = null;
		IDAO_Gather dao_Gather = (IDAO_Gather) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), IDAO_Gather.class);
		Statement stmt = con.createStatement();
		log.debug("通过采集点号获取采集点SQL语句："+dao_Gather.getGatherById(id));
		ResultSet rs = stmt.executeQuery(dao_Gather.getGatherById(id));
		log.debug("通过采集点号获取采集点列表---");
		while(rs.next()){
			g = new Gather();
			g.setId(rs.getInt("int_id"));
			g.setName(rs.getString("str_name"));
			g.setDesc(rs.getString("str_desc"));
			g.setProdunitId(rs.getInt("int_produnitid"));
			g.setMaterielruleId(rs.getInt("int_materielruleid"));
			g.setStartgo(rs.getInt("startgo"));
			g.setCompel(rs.getInt("compel"));
			log.debug("采集点名："+rs.getInt("int_id")+"；采集点名"+rs.getString("str_name")+"；采集点描述："
					+rs.getString("str_desc")+"；生产单元号："+rs.getInt("int_produnitid")+"；主物料验证规则："
					+rs.getInt("int_materielruleid")+"；是否启动状态规则验证："+rs.getInt("startgo")+
					"；是否强制启动状态规则验证："+rs.getInt("compel"));
		}
		if(rs!=null){
			rs.close();
			rs=null;
		}
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
		return g;
	}
	
	/**
	 * 通过采集点名查询gather
	 * @param id
	 * @param con
	 * @return
	 * @throws SQLException 
	 */
	public Gather getGatherByName(String str_name,Connection con) throws SQLException{
		Gather g = null;
		IDAO_Gather dao_Gather = (IDAO_Gather) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), IDAO_Gather.class);
		Statement stmt = con.createStatement();
		log.debug("通过采集点名获取采集点SQL语句："+dao_Gather.getGatherByName(str_name));
		ResultSet rs = stmt.executeQuery(dao_Gather.getGatherByName(str_name));
		log.debug("通过采集点名获取采集点列表---");
		while(rs.next()){
			g = new Gather();
			g.setId(rs.getInt("int_id"));
			g.setName(rs.getString("str_name"));
			g.setDesc(rs.getString("str_desc"));
			g.setProdunitId(rs.getInt("int_produnitid"));
			g.setMaterielruleId(rs.getInt("int_materielruleid"));
			log.debug("采集点名："+rs.getInt("int_id")+"；采集点名"+rs.getString("str_name")+"；采集点描述："
					+rs.getString("str_desc")+"；生产单元号："+rs.getInt("int_produnitid")+"；主物料验证规则："
					+rs.getInt("int_materielruleid"));
		}
		if(rs!=null){
			rs.close();
			rs=null;
		}
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
		return g;
	}
	
	/**
	 * 通过序号删除gather，注意级联删除GatherItem，原则上删除功能是不提供给用户的
	 * @param id
	 * @param con
	 * @throws SQLException 
	 */
	public void delGatherById(int id,Connection con) throws SQLException{
		IDAO_Gather dao_Gather = (IDAO_Gather) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), IDAO_Gather.class);
		IDAO_GatherItem dao_GatherItem = (IDAO_GatherItem) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), IDAO_GatherItem.class);
		Statement stmt = null;
		try {
			stmt= con.createStatement();

			log.debug("通过采集点号删除采集点SQL语句："+dao_Gather.delGatherById(id));
			//删除Gather
			stmt.execute(dao_Gather.delGatherById(id));
				con.commit();
			
		}catch(SQLException e){
			con.rollback();
			throw e;
		}finally{
			if(stmt!=null){
				stmt.close();
				stmt=null;
			}
		}
	}
	
	/**
	 * 更新gather对象
	 * @param gather
	 * @param con
	 * @throws SQLException 
	 */
	public void updateGather(Gather gather,Connection con) throws SQLException{
		IDAO_Gather dao_Gather = (IDAO_Gather) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), IDAO_Gather.class);
		Statement stmt = con.createStatement();
		log.debug("更新采集点SQL语句："+dao_Gather.updateGather(gather));
		stmt.execute(dao_Gather.updateGather(gather));
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
	}
	
	/**
	 * 查询全部gather对象
	 * @param con
	 * @return
	 * @throws SQLException 
	 */
	public List<Gather> getAllGather(Connection con) throws SQLException{
		List<Gather> list = new ArrayList<Gather>();
		IDAO_Gather dao_Gather = (IDAO_Gather) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), IDAO_Gather.class);
		Statement stmt = con.createStatement();
		log.debug("查询所有采集点SQL语句："+dao_Gather.getAllGather());
		ResultSet rs = stmt.executeQuery(dao_Gather.getAllGather());
		while(rs.next()){
			Gather g = new Gather();
			g.setId(rs.getInt("int_id"));
			g.setName(rs.getString("str_name"));
			g.setDesc(rs.getString("str_desc"));
			g.setProdunitId(rs.getInt("int_produnitid"));
			g.setMaterielruleId(rs.getInt("int_materielruleid"));
			log.debug("采集点名："+rs.getInt("int_id")+"；采集点名"+rs.getString("str_name")+"；采集点描述："
					+rs.getString("str_desc")+"；生产单元号："+rs.getInt("int_produnitid")+"；主物料验证规则："
					+rs.getInt("int_materielruleid"));
			list.add(g);
		}
		if(rs!=null){
			rs.close();
			rs=null;
		}
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
		return list;
	}
	
	/**
	 * 检测生产单元中是否存在此主物料标识规则号
	 * @param gatherid
	 * @param materialId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public int checkGatherItemByMaterialId(int gatherid, int materialId,
			Connection con) throws SQLException {
		IDAO_Gather dao_Gather = (IDAO_Gather) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				IDAO_Gather.class);
		Statement stmt = con.createStatement();
		log.debug("检测生产单元中存在此主物料标识规则号的个数SQL语句："+dao_Gather
				.checkGatherItemCountByMaterialId(gatherid, materialId));
		int count = 0;
		ResultSet rs = stmt.executeQuery(dao_Gather
				.checkGatherItemCountByMaterialId(gatherid, materialId));
		if (rs.next()) {
			count = rs.getInt(1);
			log.debug("检测生产单元中存在此主物料标识规则号的个数:"+count);
		}
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
		return count;
	}
	
}
