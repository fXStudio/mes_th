package com.qm.mes.pm.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.DataBaseType;
import com.qm.mes.pm.bean.ProLineItem;
import com.qm.mes.pm.bean.ProduceUnitLine;
import com.qm.mes.pm.dao.DAO_ProduceUnitLine;
import com.qm.mes.system.dao.DAOFactoryAdapter;


/**
 * 生产单元线性配置工厂类
 * 
 * @author YuanPeng
 *
 */
public class ProduceUnitLineFactory {
	private final Log log = LogFactory.getLog(ProduceUnitLineFactory.class);// 日志

	/**
	 * 创建生产单元线性配置
	 * 
	 * @param ProduceUnitLine
	 *            生产单元线性配置对象
	 * @param con
	 *            连接对象
	 * @throws SQLException
	 *             SQL异常
	 */
	public void saveProduceUnitLine(ProduceUnitLine ProduceUnitLine, Connection con)
			throws SQLException {
		DAO_ProduceUnitLine dao_ProduceUnitLine = (DAO_ProduceUnitLine) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_ProduceUnitLine.class);
		Statement stmt = con.createStatement();
		log.debug("保存生产单元线性配置SQL：" + dao_ProduceUnitLine.saveProduceUnitLine(ProduceUnitLine));
		stmt.execute(dao_ProduceUnitLine.saveProduceUnitLine(ProduceUnitLine));
		if (stmt != null) {
			stmt.close();
		}
	}

	/**
	 * 通过ID查询生产单元线性配置
	 * 
	 * @param id
	 *            生产单元线性配置序列号
	 * @param con
	 *            连接对象
	 * @return 通过ID查询出的生产单元线性配置对象
	 * @throws java.sql.SQLException
	 */
	public ProduceUnitLine getProduceUnitLineById(int id, Connection con)
			throws SQLException, ParseException {
		ProduceUnitLine ProduceUnitLine = new ProduceUnitLine();
		DAO_ProduceUnitLine dao_ProduceUnitLine = (DAO_ProduceUnitLine) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_ProduceUnitLine.class);
		Statement stmt = con.createStatement();
		log.debug("通过ID查询生产单元线性配置SQL：" + dao_ProduceUnitLine.getProduceUnitLineById(id));
		ResultSet rs = stmt
				.executeQuery(dao_ProduceUnitLine.getProduceUnitLineById(id));
		if (rs.next()) {
			ProduceUnitLine.setId(rs.getInt("int_id"));
			ProduceUnitLine.setName(rs.getString("str_name"));
			ProduceUnitLine.setDescription(rs.getString("Str_description"));
		}
		if (stmt != null)
			stmt.close();
		return ProduceUnitLine;
	}

	/**
	 * 通过生产单元线性配置号删除该生产单元线性配置
	 * 
	 * @param id
	 *            生产单元线性配置号
	 * @param con
	 *            连接对象
	 * @throws SQLException
	 *             SQL异常
	 */
	public void delProduceUnitLineById(int id, Connection con) throws SQLException {
		DAO_ProduceUnitLine dao_ProduceUnitLine = (DAO_ProduceUnitLine) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_ProduceUnitLine.class);
		Statement stmt = con.createStatement();
		log.debug("通过生产单元线性配置号删除该生产单元线性配置SQL："
				+ dao_ProduceUnitLine.delProduceUnitLineById(id));
		stmt.execute(dao_ProduceUnitLine.delProduceUnitLineById(id));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}
	
	/**
	 * 倒叙查询生产单元线性配置
	 * 
	 * @param con
	 *            连接对象
	 * @return 倒叙查询生产单元线性配置列表
	 * @throws SQLException
	 *             SQL异常
	 * @throws ParseException
	 *             类型转换异常
	 */
	public List<ProduceUnitLine> getAllProduceUnitLinesByDESC(Connection con)throws SQLException, ParseException{
		List<ProduceUnitLine> list_ProduceUnitLine = new ArrayList<ProduceUnitLine>();
		DAO_ProduceUnitLine dao_ProduceUnitLine = (DAO_ProduceUnitLine) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_ProduceUnitLine.class);
		Statement stmt = con.createStatement();
		log.debug("倒叙查询生产单元线性配置SQL：" + dao_ProduceUnitLine.getAllProduceUnitLinesByDESC());
		ResultSet rs = stmt
				.executeQuery(dao_ProduceUnitLine.getAllProduceUnitLinesByDESC());
		while (rs.next()) {
			ProduceUnitLine ProduceUnitLine = new ProduceUnitLine();
			ProduceUnitLine.setId(rs.getInt("int_id"));
			ProduceUnitLine.setName(rs.getString("str_name"));
			ProduceUnitLine.setDescription(rs.getString("Str_description"));
			list_ProduceUnitLine.add(ProduceUnitLine);
		}
		if (stmt != null)
			stmt.close();
		return list_ProduceUnitLine;
	}
	
	/**
	 * 通过生产单元线性配置名查询生产单元线性配置号
	 * 
	 * @param name	生产单元线性配置
	 * @param con	连接对象
	 * @return 生产单元线性配置号
	 * @throws SQLException SQL异常
	 */
	public int getProduceUnitLineIdByName(String name ,Connection con)throws SQLException{
		int id=0;
		DAO_ProduceUnitLine dao_ProduceUnitLine = (DAO_ProduceUnitLine) DAOFactoryAdapter
			.getInstance(DataBaseType.getDataBaseType(con),DAO_ProduceUnitLine.class);
		Statement stmt = con.createStatement();
		log.debug("通过生产单元线性配置名查询序号SQL：" + dao_ProduceUnitLine.getProduceUnitLineIdByName(name));
		ResultSet rs = stmt.executeQuery(dao_ProduceUnitLine.getProduceUnitLineIdByName(name));
		if (rs.next()) {
			id = rs.getInt("int_id");
		}
		if (stmt != null)
			stmt.close();
		return id;
	}
	
	/**
	 * 通过生产单元线性配置名查询生产单元线性配置数量
	 * 
	 * @param name 生产单元线性配置名
	 * @param con 连接对象
	 * @return 生产单元线性配置数量
	 * @throws SQLException SQL异常
	 */
	public int getProduceUnitLineCountByName(String name ,Connection con)throws SQLException{
		int count=0;
		DAO_ProduceUnitLine dao_ProduceUnitLine = (DAO_ProduceUnitLine) DAOFactoryAdapter
			.getInstance(DataBaseType.getDataBaseType(con),DAO_ProduceUnitLine.class);
		Statement stmt = con.createStatement();
		log.debug("通过生产单元线性配置名查询生产单元线性配置数量SQL：" + dao_ProduceUnitLine.getProduceUnitLineCountByName(name));
		ResultSet rs = stmt.executeQuery(dao_ProduceUnitLine.getProduceUnitLineCountByName(name));
		if (rs.next()) {
			count = rs.getInt(1);
		}
		if (stmt != null)
			stmt.close();
		return count;
	}
	
	/**
	 * 更新生产单元线性配置
	 * 
	 * @param ProduceUnitLine 生产单元线性配置对象
	 * @param con 连接对象
	 * @throws SQLException SQL异常
	 */
	public void updateProduceUnitLine(ProduceUnitLine ProduceUnitLine, Connection con)
			throws SQLException {
		DAO_ProduceUnitLine dao_ProduceUnitLine = (DAO_ProduceUnitLine) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),DAO_ProduceUnitLine.class);
		Statement stmt = con.createStatement();
		log.debug("更新生产单元线性配置SQL："
				+ dao_ProduceUnitLine.updateProduceUnitLine(ProduceUnitLine));
		stmt.execute(dao_ProduceUnitLine.updateProduceUnitLine(ProduceUnitLine));
		if (stmt != null) {
			stmt.close();
		}
	}
	
	
	

	
	
	
	
	
	
	
	/**
	 * 创建装生产单元线性内容
	 * 
	 * @param ProLineItem 装生产单元线性内容对象
	 * @param con 连接对象
	 * @throws SQLException  SQL异常
	 */
	public void saveProLineItem(ProLineItem ProLineItem,Connection con)throws SQLException{
		DAO_ProduceUnitLine dao_ProduceUnitLine = (DAO_ProduceUnitLine) DAOFactoryAdapter
			.getInstance(DataBaseType.getDataBaseType(con),DAO_ProduceUnitLine.class);
		Statement stmt = con.createStatement();
		log.debug("保存装生产单元线性内容SQL：" + dao_ProduceUnitLine.saveProLineItem(ProLineItem));
		stmt.execute(dao_ProduceUnitLine.saveProLineItem(ProLineItem));
			if (stmt != null) {
				stmt.close();
			}
	}
	
	/**
	 * 通过生产单元线性配置号查询装生产单元线性内容
	 * 
	 * @param id 生产单元线性配置号
	 * @param con 连接对象
	 * @return 通过生产单元线性配置号查询装生产单元线性内容列表
	 * @throws SQLException SQL异常
	 */
	public List<ProLineItem> getProLineItemByProduceUnitLineId(int id,Connection con)throws SQLException{
		List<ProLineItem> list_ProLineItem = new ArrayList<ProLineItem>();
		DAO_ProduceUnitLine dao_ProduceUnitLine = (DAO_ProduceUnitLine) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_ProduceUnitLine.class);
		Statement stmt = con.createStatement();
		log.debug("通过生产单元线性配置号查询装生产单元线性内容列表SQL：" + dao_ProduceUnitLine.getProLineItemByProduceUnitLineId(id));
		ResultSet rs = stmt
				.executeQuery(dao_ProduceUnitLine.getProLineItemByProduceUnitLineId(id));
		while (rs.next()) {
			ProLineItem ProLineItem = new ProLineItem();
			ProLineItem.setId(rs.getInt("int_id"));
			ProLineItem.setProduceUnitId(rs.getInt("Int_produceUnitId"));
			ProLineItem.setOrder(rs.getInt("Int_Order"));
			ProLineItem.setLineId(rs.getInt("Int_LineId"));
			list_ProLineItem.add(ProLineItem);
		}
		if (stmt != null)
			stmt.close();
		return list_ProLineItem;
	}
	
	/**
	 * 通过生产单元线性配置号删除装生产单元线性内容
	 * 
	 * @param id 生产单元线性配置号
	 * @param con 连接对象
	 * @throws SQLException SQL异常
	 */
	public void delProLineItemByProduceUnitLineId(int id,Connection con)throws SQLException{
		DAO_ProduceUnitLine dao_ProduceUnitLine = (DAO_ProduceUnitLine) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_ProduceUnitLine.class);
		Statement stmt = con.createStatement();
		log.debug("通过生产单元线性配置号删除装生产单元线性内容SQL：" + dao_ProduceUnitLine.delProLineItemByProduceUnitLineId(id));
		stmt.execute(dao_ProduceUnitLine.delProLineItemByProduceUnitLineId(id));
			if (stmt != null) {
				stmt.close();
			}
	}
}