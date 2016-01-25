package mes.pm.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import mes.framework.DataBaseType;
import mes.pm.bean.Bom;
import mes.pm.dao.DAO_Bom;
import mes.system.dao.DAOFactoryAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * BOM工厂类
 * 
 * @author YuanPeng
 *
 */
public class BomFactory {
	private final Log log = LogFactory.getLog(DistributionDocFactory.class);// 日志
	
	/**
	 * 通过ID查询BOM
	 * 
	 * @param id
	 *            BOM序号
	 * @param con
	 *            连接对象
	 * @return 通过ID查询BOM对象
	 * @throws java.sql.SQLException
	 */
	public Bom getBomById(int id, Connection con)
			throws SQLException, ParseException {
		Bom Bom = new Bom();
		DAO_Bom dao_Bom = (DAO_Bom) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),DAO_Bom.class);
		Statement stmt = con.createStatement();
		log.debug("通过ID查询Bom的SQL：" + dao_Bom.getBomById(id));
		ResultSet rs = stmt
				.executeQuery(dao_Bom.getBomById(id));
		if (rs.next()) {
			Bom.setId(rs.getInt("int_id"));
			Bom.setName(rs.getString("str_name"));
			Bom.setComponent(rs.getString("Str_component"));
			Bom.setParentid(rs.getInt("Int_parentid"));
			Bom.setDiscription(rs.getString("Str_discription"));
		}
		if (stmt != null)
			stmt.close();
		return Bom;
	}
	
	/**
	 * 通过组件标示查询Bom列表
	 * 
	 * @param component 组件标示
	 * @param con	连接对象
	 * @return 通过组件标示查询Bom列表
	 * @throws SQLException SQL异常
	 * @throws ParseException 类型转换异常
	 */
	public List<Bom> getBomByComponent(String component,Connection con)
			throws SQLException, ParseException {
		List<Bom> bom_list = new ArrayList<Bom>();
		DAO_Bom dao_Bom = (DAO_Bom) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),DAO_Bom.class);
		Statement stmt = con.createStatement();
		log.debug("通过组件标示查询Bom的SQL：" + dao_Bom.getBomByComponent(component));
		ResultSet rs = stmt
				.executeQuery(dao_Bom.getBomByComponent(component));
		while (rs.next()) {
			Bom Bom = new Bom();
			Bom.setId(rs.getInt("int_id"));
			Bom.setName(rs.getString("str_name"));
			Bom.setComponent(rs.getString("Str_component"));
			Bom.setParentid(rs.getInt("Int_parentid"));
			Bom.setDiscription(rs.getString("Str_discription"));
			Bom.setCount(rs.getInt("int_count"));
			bom_list.add(Bom);
		}
		if (stmt != null)
			stmt.close();
		return bom_list;
	}
	
	/**
	 * 通过上级序号标示查询Bom列表
	 * 
	 * @param component 组件标示
	 * @param con	连接对象
	 * @return 通过上级序号标示查询Bom列表
	 * @throws SQLException SQL异常
	 * @throws ParseException 类型转换异常
	 */
	public List<Bom> getBomsByParentId(int parentid,Connection con)
			throws SQLException, ParseException {
		List<Bom> bom_list = new ArrayList<Bom>();
		DAO_Bom dao_Bom = (DAO_Bom) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),DAO_Bom.class);
		Statement stmt = con.createStatement();
		log.debug("通过上级序号标示查询Bom的SQL：" + dao_Bom.getBomsByParentId(parentid));
		ResultSet rs = stmt
				.executeQuery(dao_Bom.getBomsByParentId(parentid));
		while (rs.next()) {
			Bom Bom = new Bom();
			Bom.setId(rs.getInt("int_id"));
			Bom.setName(rs.getString("str_name"));
			Bom.setComponent(rs.getString("Str_component"));
			Bom.setParentid(rs.getInt("Int_parentid"));
			Bom.setDiscription(rs.getString("Str_discription"));
			bom_list.add(Bom);
		}
		if (stmt != null)
			stmt.close();
		return bom_list;
	}
	
	/**
	 * 倒叙查询所有Bom列表
	 * 
	 * @param con	连接对象
	 * @return  倒叙查询所有Bom列表
	 * @throws SQLException	SQL异常
	 */
	public List<Bom> getAllBomsByDESC(Connection con)throws SQLException{
		List<Bom> bom_list = new ArrayList<Bom>();
		DAO_Bom dao_Bom = (DAO_Bom) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),DAO_Bom.class);
		Statement stmt = con.createStatement();
		log.debug("通过上级序号标示查询Bom的SQL：" + dao_Bom.getAllBomsByDESC());
		ResultSet rs = stmt.executeQuery(dao_Bom.getAllBomsByDESC());
		while (rs.next()) {
			Bom Bom = new Bom();
			Bom.setId(rs.getInt("int_id"));
			Bom.setName(rs.getString("str_name"));
			Bom.setComponent(rs.getString("Str_component"));
			Bom.setParentid(rs.getInt("INT_PARENTID"));
			Bom.setDiscription(rs.getString("STR_DISCRIPTION"));
			bom_list.add(Bom);
		}
		if (stmt != null)
			stmt.close();
		return bom_list;
	}
	
	/**
	 * 查询所有BOM组件标识按组件标识排序
	 * 
	 * @return 查询所有BOM组件标识按组件标识排序列表
	 */
	public List<Bom> getBomsGroupByComponent(Connection con)throws SQLException{
		List<Bom> bom_list = new ArrayList<Bom>();
		DAO_Bom dao_Bom = (DAO_Bom) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),DAO_Bom.class);
		Statement stmt = con.createStatement();
		log.debug("查询所有BOM组件标识按组件标识排序SQL：" + dao_Bom.getBomsGroupByComponent());
		ResultSet rs = stmt.executeQuery(dao_Bom.getBomsGroupByComponent());
		while (rs.next()) {
			Bom bom = new Bom();
			bom.setComponent(rs.getString("Str_component"));
			bom.setDiscription(rs.getString("STR_DISCRIPTION"));
			bom_list.add(bom);
		}
		if (stmt != null)
			stmt.close();
		return bom_list;
	}
	
}
