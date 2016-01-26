package com.qm.mes.pm.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.DataBaseType;
import com.qm.mes.pm.bean.DistriItem;
import com.qm.mes.pm.bean.DistributionDoc;
import com.qm.mes.pm.dao.DAO_DistributionDoc;
import com.qm.mes.system.dao.DAOFactoryAdapter;

/**
 * 配送指示单工厂类
 * 
 * @author YuanPeng
 *
 */
public class DistributionDocFactory {
	
	private final Log log = LogFactory.getLog(DistributionDocFactory.class);// 日志

	/**
	 * 创建配送指示单
	 * 
	 * @param DistributionDoc
	 *            配送指示单对象
	 * @param con
	 *            连接对象
	 * @throws SQLException
	 *             SQL异常
	 */
	public void saveDistributionDoc(DistributionDoc DistributionDoc, Connection con)
			throws SQLException {
		DAO_DistributionDoc dao_DistributionDoc = (DAO_DistributionDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_DistributionDoc.class);
		Statement stmt = con.createStatement();
		log.debug("保存配送指示单SQL：" + dao_DistributionDoc.saveDistributionDoc(DistributionDoc));
		stmt.execute(dao_DistributionDoc.saveDistributionDoc(DistributionDoc));
		if (stmt != null) {
			stmt.close();
		}
	}

	/**
	 * 通过ID查询配送指示单
	 * 
	 * @param id
	 *            配送指示单序列号
	 * @param con
	 *            连接对象
	 * @return 通过ID查询出的配送指示单对象
	 * @throws java.sql.SQLException
	 */
	public DistributionDoc getDistributionDocById(int id, Connection con)
			throws SQLException, ParseException {
		DistributionDoc DistributionDoc = new DistributionDoc();
		DAO_DistributionDoc dao_DistributionDoc = (DAO_DistributionDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_DistributionDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过ID查询配送指示单SQL：" + dao_DistributionDoc.getDistributionDocById(id));
		ResultSet rs = stmt
				.executeQuery(dao_DistributionDoc.getDistributionDocById(id));
		if (rs.next()) {
			DistributionDoc.setId(rs.getInt("int_id"));
			DistributionDoc.setName(rs.getString("str_name"));
			DistributionDoc.setMaterielType(rs.getString("Str_materiel"));
			DistributionDoc.setDescription(rs.getString("Str_description"));
			DistributionDoc.setCreateDate(rs.getString("Dat_createDate") == null ? null
							: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.parse(rs.getString("Dat_createDate")));
			DistributionDoc.setUpDate(rs.getString("Dat_upDate") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("Dat_upDate")));
			DistributionDoc.setCreateUID(rs.getInt("Int_CreateUID"));
			DistributionDoc.setUpdateUID(rs.getInt("Int_UpdateUID"));
			DistributionDoc.setRequest_proUnit(rs.getInt("Int_request"));
			DistributionDoc.setResponse_proUnit(rs.getInt("Int_response"));
			DistributionDoc.setTarget_proUnit(rs.getInt("Int_target"));
			DistributionDoc.setBomId(rs.getInt("Int_bomid"));
		}
		if (stmt != null)
			stmt.close();
		return DistributionDoc;
	}

	/**
	 * 通过配送指示单号删除该配送指示单
	 * 
	 * @param id
	 *            配送指示单号
	 * @param con
	 *            连接对象
	 * @throws SQLException
	 *             SQL异常
	 */
	public void delDistributionDocById(int id, Connection con) throws SQLException {
		DAO_DistributionDoc dao_DistributionDoc = (DAO_DistributionDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_DistributionDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过配送指示单号删除该配送指示单SQL："
				+ dao_DistributionDoc.delDistributionDocById(id));
		stmt.execute(dao_DistributionDoc.delDistributionDocById(id));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}

	/**
	 * 通过ID查询配送指示单
	 * 
	 * @param materiel
	 *            物料类型
	 * @param con
	 *            连接对象
	 * @return 通过ID查询配送指示单对象
	 * @throws SQLException
	 *             SQL异常
	 * @throws ParseException
	 *             类型转换异常
	 */
	public DistributionDoc getDistributionDocByMateriel(String materiel, Connection con)
			throws SQLException, ParseException {
		DistributionDoc DistributionDoc = new DistributionDoc();
		DAO_DistributionDoc dao_DistributionDoc = (DAO_DistributionDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_DistributionDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过ID查询配送指示单SQL：" + dao_DistributionDoc.getDistributionDocByMateriel(materiel));
		ResultSet rs = stmt
				.executeQuery(dao_DistributionDoc.getDistributionDocByMateriel(materiel));
		if (rs.next()) {
			DistributionDoc.setId(rs.getInt("int_id"));
			DistributionDoc.setName(rs.getString("str_name"));
			DistributionDoc.setMaterielType(rs.getString("Str_materiel"));
			DistributionDoc.setDescription(rs.getString("Str_description"));
			DistributionDoc.setCreateDate(rs.getString("Dat_createDate") == null ? null
							: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.parse(rs.getString("Dat_createDate")));
			DistributionDoc.setUpDate(rs.getString("Dat_upDate") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("Dat_upDate")));
			DistributionDoc.setCreateUID(rs.getInt("Int_CreateUID"));
			DistributionDoc.setUpdateUID(rs.getInt("Int_UpdateUID"));
			DistributionDoc.setRequest_proUnit(rs.getInt("Int_request"));
			DistributionDoc.setResponse_proUnit(rs.getInt("Int_response"));
			DistributionDoc.setTarget_proUnit(rs.getInt("Int_target"));
			DistributionDoc.setBomId(rs.getInt("Int_bomid"));
		}
		if (stmt != null)
			stmt.close();
		return DistributionDoc;
	}
	
	/**
	 * 倒叙查询配送指示单
	 * 
	 * @param con
	 *            连接对象
	 * @return 倒叙查询配送指示单列表
	 * @throws SQLException
	 *             SQL异常
	 * @throws ParseException
	 *             类型转换异常
	 */
	public List<DistributionDoc> getAllDistributionDocsByDESC(Connection con)throws SQLException, ParseException{
		List<DistributionDoc> list_DistributionDoc = new ArrayList<DistributionDoc>();
		DAO_DistributionDoc dao_DistributionDoc = (DAO_DistributionDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_DistributionDoc.class);
		Statement stmt = con.createStatement();
		log.debug("倒叙查询配送指示单SQL：" + dao_DistributionDoc.getAllDistributionDocsByDESC());
		ResultSet rs = stmt
				.executeQuery(dao_DistributionDoc.getAllDistributionDocsByDESC());
		while (rs.next()) {
			DistributionDoc DistributionDoc = new DistributionDoc();
			DistributionDoc.setId(rs.getInt("int_id"));
			DistributionDoc.setName(rs.getString("str_name"));
			DistributionDoc.setMaterielType(rs.getString("Str_materiel"));
			DistributionDoc.setDescription(rs.getString("Str_description"));
			DistributionDoc.setCreateDate(rs.getString("Dat_createDate") == null ? null
							: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.parse(rs.getString("Dat_createDate")));
			DistributionDoc.setUpDate(rs.getString("Dat_upDate") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("Dat_upDate")));
			DistributionDoc.setCreateUID(rs.getInt("Int_CreateUID"));
			DistributionDoc.setUpdateUID(rs.getInt("Int_UpdateUID"));
			DistributionDoc.setRequest_proUnit(rs.getInt("Int_request"));
			DistributionDoc.setResponse_proUnit(rs.getInt("Int_response"));
			DistributionDoc.setTarget_proUnit(rs.getInt("Int_target"));
			DistributionDoc.setBomId(rs.getInt("Int_bomid"));
			list_DistributionDoc.add(DistributionDoc);
		}
		if (stmt != null)
			stmt.close();
		return list_DistributionDoc;
	}
	
	/**
	 * 通过配送指示单名查询配送指示单号
	 * 
	 * @param name	配送指示单
	 * @param con	连接对象
	 * @return 配送指示单号
	 * @throws SQLException SQL异常
	 */
	public int getDistributionDocIdByName(String name ,Connection con)throws SQLException{
		int id=0;
		DAO_DistributionDoc dao_DistributionDoc = (DAO_DistributionDoc) DAOFactoryAdapter
			.getInstance(DataBaseType.getDataBaseType(con),DAO_DistributionDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过配送指示单名查询序号SQL：" + dao_DistributionDoc.getDistributionDocIdByName(name));
		ResultSet rs = stmt.executeQuery(dao_DistributionDoc.getDistributionDocIdByName(name));
		if (rs.next()) {
			id = rs.getInt("int_id");
		}
		if (stmt != null)
			stmt.close();
		return id;
	}
	
	/**
	 * 通过配送指示单名查询配送指示单数量
	 * 
	 * @param name 配送指示单名
	 * @param con 连接对象
	 * @return 配送指示单数量
	 * @throws SQLException SQL异常
	 */
	public int getDistributionDocCountByName(String name ,Connection con)throws SQLException{
		int count=0;
		DAO_DistributionDoc dao_DistributionDoc = (DAO_DistributionDoc) DAOFactoryAdapter
			.getInstance(DataBaseType.getDataBaseType(con),DAO_DistributionDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过配送指示单名查询配送指示单数量SQL：" + dao_DistributionDoc.getDistributionDocCountByName(name));
		ResultSet rs = stmt.executeQuery(dao_DistributionDoc.getDistributionDocCountByName(name));
		if (rs.next()) {
			count = rs.getInt(1);
		}
		if (stmt != null)
			stmt.close();
		return count;
	}
	
	/**
	 * 更新配送指示单
	 * 
	 * @param DistributionDoc 配送指示单对象
	 * @param con 连接对象
	 * @throws SQLException SQL异常
	 */
	public void updateDistributionDoc(DistributionDoc DistributionDoc, Connection con)
			throws SQLException {
		DAO_DistributionDoc dao_DistributionDoc = (DAO_DistributionDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),DAO_DistributionDoc.class);
		Statement stmt = con.createStatement();
		log.debug("更新配送指示单SQL："
				+ dao_DistributionDoc.updateDistributionDoc(DistributionDoc));
		stmt.execute(dao_DistributionDoc.updateDistributionDoc(DistributionDoc));
		if (stmt != null) {
			stmt.close();
		}
	}
	
	/**
	 * 通过请求生产单元号查询配送指示单
	 * 
	 * @param requestProUnitId 请求生产单元号
	 * @return 通过请求生产单元号查询配送指示单列表
	 */
	public List<DistributionDoc> getDistributionDocsByRequestProUnitId(int requestProUnitId, Connection con)
			throws SQLException, ParseException {
		List<DistributionDoc> list_DistributionDoc = new ArrayList<DistributionDoc>();
		DAO_DistributionDoc dao_DistributionDoc = (DAO_DistributionDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),DAO_DistributionDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过请求生产单元号查询配送指示单SQL："
				+ dao_DistributionDoc.getDistributionDocsByRequestProUnitId(requestProUnitId));
		ResultSet rs = stmt
			.executeQuery(dao_DistributionDoc.getDistributionDocsByRequestProUnitId(requestProUnitId));
		while (rs.next()) {
			DistributionDoc DistributionDoc = new DistributionDoc();
			DistributionDoc.setId(rs.getInt("int_id"));
			DistributionDoc.setName(rs.getString("str_name"));
			DistributionDoc.setMaterielType(rs.getString("Str_materiel"));
			DistributionDoc.setDescription(rs.getString("Str_description"));
			DistributionDoc.setCreateDate(rs.getString("Dat_createDate") == null ? null
							: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.parse(rs.getString("Dat_createDate")));
			DistributionDoc.setUpDate(rs.getString("Dat_upDate") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("Dat_upDate")));
			DistributionDoc.setCreateUID(rs.getInt("Int_CreateUID"));
			DistributionDoc.setUpdateUID(rs.getInt("Int_UpdateUID"));
			DistributionDoc.setRequest_proUnit(rs.getInt("Int_request"));
			DistributionDoc.setResponse_proUnit(rs.getInt("Int_response"));
			DistributionDoc.setTarget_proUnit(rs.getInt("Int_target"));
			DistributionDoc.setBomId(rs.getInt("Int_bomid"));
			list_DistributionDoc.add(DistributionDoc);
		}
		if (stmt != null) {
			stmt.close();
		}
		return list_DistributionDoc;
	}
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	/**
	 * 创建装配指示项
	 * 
	 * @param assDocItem 装配指示项对象
	 * @param con 连接对象
	 * @throws SQLException  SQL异常
	 */
	public void saveDistriItem(DistriItem DistriItem,Connection con)throws SQLException{
		DAO_DistributionDoc dao_DistributionDoc = (DAO_DistributionDoc) DAOFactoryAdapter
			.getInstance(DataBaseType.getDataBaseType(con),DAO_DistributionDoc.class);
		Statement stmt = con.createStatement();
		log.debug("保存装配指示项SQL：" + dao_DistributionDoc.saveDistriItem(DistriItem));
		stmt.execute(dao_DistributionDoc.saveDistriItem(DistriItem));
			if (stmt != null) {
				stmt.close();
			}
	}
	
	/**
	 * 通过配送指示单号查询装配指示项
	 * 
	 * @param id 配送指示单号
	 * @param con 连接对象
	 * @return 通过配送指示单号查询装配指示项列表
	 * @throws SQLException SQL异常
	 */
	public List<DistriItem> getDistriItemByDistributionDocId(int id,Connection con)throws SQLException{
		List<DistriItem> list_DistriItem = new ArrayList<DistriItem>();
		DAO_DistributionDoc dao_DistributionDoc = (DAO_DistributionDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_DistributionDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过配送指示单号查询装配指示项列表SQL：" + dao_DistributionDoc.getDistriItemByDistributionDocId(id));
		ResultSet rs = stmt
				.executeQuery(dao_DistributionDoc.getDistriItemByDistributionDocId(id));
		while (rs.next()) {
			DistriItem DistriItem = new DistriItem();
			DistriItem.setId(rs.getInt("int_id"));
			DistriItem.setDistributionDocId(rs.getInt("Int_DisDoc"));
			DistriItem.setName(rs.getString("str_name"));
			DistriItem.setCount(rs.getInt("Int_count"));
			DistriItem.setMatitem(rs.getString("Str_matitem"));
			DistriItem.setDescription(rs.getString("Str_description"));
			list_DistriItem.add(DistriItem);
		}
		if (stmt != null)
			stmt.close();
		return list_DistriItem;
	}
	
	/**
	 * 通过配送指示单号删除装配指示项
	 * 
	 * @param id 配送指示单号
	 * @param con 连接对象
	 * @throws SQLException SQL异常
	 */
	public void delDistriItemByDistributionDocId(int id,Connection con)throws SQLException{
		DAO_DistributionDoc dao_DistributionDoc = (DAO_DistributionDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_DistributionDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过配送指示单号删除物料项SQL：" + dao_DistributionDoc.delDistriItemByDistributionDocId(id));
		stmt.execute(dao_DistributionDoc.delDistriItemByDistributionDocId(id));
			if (stmt != null) {
				stmt.close();
			}
	}
}
