package mes.pm.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import mes.framework.DataBaseType;
import mes.pm.bean.AssDocItem;
import mes.pm.bean.AssembleDoc;
import mes.pm.dao.DAO_AssembleDoc;
import mes.system.dao.DAOFactoryAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 装配指示单工厂类
 * 
 * @author YuanPeng
 *
 */
public class AssembleDocFactory {
	private final Log log = LogFactory.getLog(AssembleDocFactory.class);// 日志

	/**
	 * 创建装配指示单
	 * 
	 * @param assembleDoc
	 *            装配指示单对象
	 * @param con
	 *            连接对象
	 * @throws SQLException
	 *             SQL异常
	 */
	public void saveAssembleDoc(AssembleDoc assembleDoc, Connection con)
			throws SQLException {
		DAO_AssembleDoc dao_AssembleDoc = (DAO_AssembleDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_AssembleDoc.class);
		Statement stmt = con.createStatement();
		log.debug("保存装配指示单SQL：" + dao_AssembleDoc.saveAssembleDoc(assembleDoc));
		stmt.execute(dao_AssembleDoc.saveAssembleDoc(assembleDoc));
		if (stmt != null) {
			stmt.close();
		}
	}

	/**
	 * 通过ID查询装配指示单
	 * 
	 * @param id
	 *            装配指示单序列号
	 * @param con
	 *            连接对象
	 * @return 通过ID查询出的装配指示单对象
	 * @throws java.sql.SQLException
	 */
	public AssembleDoc getAssembleDocById(int id, Connection con)
			throws SQLException, ParseException {
		AssembleDoc assembleDoc = new AssembleDoc();
		DAO_AssembleDoc dao_AssembleDoc = (DAO_AssembleDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_AssembleDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过ID查询装配指示单SQL：" + dao_AssembleDoc.getAssembleDocById(id));
		ResultSet rs = stmt
				.executeQuery(dao_AssembleDoc.getAssembleDocById(id));
		if (rs.next()) {
			assembleDoc.setId(rs.getInt("int_id"));
			assembleDoc.setName(rs.getString("str_name"));
			assembleDoc.setMateriel(rs.getString("Str_materiel"));
			assembleDoc.setDescription(rs.getString("Str_description"));
			assembleDoc.setCreateDate(rs.getString("Dat_createDate") == null ? null
							: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.parse(rs.getString("Dat_createDate")));
			assembleDoc.setUpDate(rs.getString("Dat_upDate") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("Dat_upDate")));
			assembleDoc.setCreateUID(rs.getInt("Int_CreateUID"));
			assembleDoc.setUpdateUID(rs.getInt("Int_UpdateUID"));
		}
		if (stmt != null)
			stmt.close();
		return assembleDoc;
	}

	/**
	 * 通过装配指示单号删除该装配指示单
	 * 
	 * @param id
	 *            装配指示单号
	 * @param con
	 *            连接对象
	 * @throws SQLException
	 *             SQL异常
	 */
	public void delAssembleDocById(int id, Connection con) throws SQLException {
		DAO_AssembleDoc dao_AssembleDoc = (DAO_AssembleDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_AssembleDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过装配指示单号删除该装配指示单SQL："
				+ dao_AssembleDoc.delAssembleDocById(id));
		stmt.execute(dao_AssembleDoc.delAssembleDocById(id));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}

	/**
	 * 通过ID查询装配指示单
	 * 
	 * @param materiel
	 *            物料类型
	 * @param con
	 *            连接对象
	 * @return 通过ID查询装配指示单对象
	 * @throws SQLException
	 *             SQL异常
	 * @throws ParseException
	 *             类型转换异常
	 */
	public AssembleDoc getAssembleDocByMateriel(String materiel, Connection con)
			throws SQLException, ParseException {
		AssembleDoc assembleDoc = new AssembleDoc();
		DAO_AssembleDoc dao_AssembleDoc = (DAO_AssembleDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_AssembleDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过ID查询装配指示单SQL：" + dao_AssembleDoc.getAssembleDocByMateriel(materiel));
		ResultSet rs = stmt
				.executeQuery(dao_AssembleDoc.getAssembleDocByMateriel(materiel));
		if (rs.next()) {
			assembleDoc.setId(rs.getInt("int_id"));
			assembleDoc.setName(rs.getString("str_name"));
			assembleDoc.setMateriel(rs.getString("Str_materiel"));
			assembleDoc.setDescription(rs.getString("Str_description"));
			assembleDoc.setCreateDate(rs.getString("Dat_createDate") == null ? null
							: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.parse(rs.getString("Dat_createDate")));
			assembleDoc.setUpDate(rs.getString("Dat_upDate") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("Dat_upDate")));
			assembleDoc.setCreateUID(rs.getInt("Int_CreateUID"));
			assembleDoc.setUpdateUID(rs.getInt("Int_UpdateUID"));
		}
		if (stmt != null)
			stmt.close();
		return assembleDoc;
	}
	
	/**
	 * 倒叙查询装配指示单
	 * 
	 * @param con
	 *            连接对象
	 * @return 倒叙查询装配指示单列表
	 * @throws SQLException
	 *             SQL异常
	 * @throws ParseException
	 *             类型转换异常
	 */
	public List<AssembleDoc> getAllAssembleDocsByDESC(Connection con)throws SQLException, ParseException{
		List<AssembleDoc> list_AssembleDoc = new ArrayList<AssembleDoc>();
		DAO_AssembleDoc dao_AssembleDoc = (DAO_AssembleDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_AssembleDoc.class);
		Statement stmt = con.createStatement();
		log.debug("倒叙查询装配指示单SQL：" + dao_AssembleDoc.getAllAssembleDocsByDESC());
		ResultSet rs = stmt
				.executeQuery(dao_AssembleDoc.getAllAssembleDocsByDESC());
		while (rs.next()) {
			AssembleDoc assembleDoc = new AssembleDoc();
			assembleDoc.setId(rs.getInt("int_id"));
			assembleDoc.setName(rs.getString("str_name"));
			assembleDoc.setMateriel(rs.getString("Str_materiel"));
			assembleDoc.setDescription(rs.getString("Str_description"));
			assembleDoc.setCreateDate(rs.getString("Dat_createDate") == null ? null
							: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.parse(rs.getString("Dat_createDate")));
			assembleDoc.setUpDate(rs.getString("Dat_upDate") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("Dat_upDate")));
			assembleDoc.setCreateUID(rs.getInt("Int_CreateUID"));
			assembleDoc.setUpdateUID(rs.getInt("Int_UpdateUID"));
			list_AssembleDoc.add(assembleDoc);
		}
		if (stmt != null)
			stmt.close();
		return list_AssembleDoc;
	}
	
	/**
	 * 通过装配指示单名查询装配指示单号
	 * 
	 * @param name	装配指示单
	 * @param con	连接对象
	 * @return 装配指示单号
	 * @throws SQLException SQL异常
	 */
	public int getAssembleDocIdByName(String name ,Connection con)throws SQLException{
		int id=0;
		DAO_AssembleDoc dao_AssembleDoc = (DAO_AssembleDoc) DAOFactoryAdapter
			.getInstance(DataBaseType.getDataBaseType(con),DAO_AssembleDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过装配指示单名查询序号SQL：" + dao_AssembleDoc.getAssembleDocIdByName(name));
		ResultSet rs = stmt.executeQuery(dao_AssembleDoc.getAssembleDocIdByName(name));
		if (rs.next()) {
			id = rs.getInt("int_id");
		}
		if (stmt != null)
			stmt.close();
		return id;
	}
	
	/**
	 * 通过装配指示单名查询装配指示单数量
	 * 
	 * @param name 装配指示单名
	 * @param con 连接对象
	 * @return 装配指示单数量
	 * @throws SQLException SQL异常
	 */
	public int getAssembleDocCountByName(String name ,Connection con)throws SQLException{
		int count=0;
		DAO_AssembleDoc dao_AssembleDoc = (DAO_AssembleDoc) DAOFactoryAdapter
			.getInstance(DataBaseType.getDataBaseType(con),DAO_AssembleDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过装配指示单名查询装配指示单数量SQL：" + dao_AssembleDoc.getAssembleDocCountByName(name));
		ResultSet rs = stmt.executeQuery(dao_AssembleDoc.getAssembleDocCountByName(name));
		if (rs.next()) {
			count = rs.getInt(1);
		}
		if (stmt != null)
			stmt.close();
		return count;
	}
	
	/**
	 * 更新装配指示单
	 * 
	 * @param assembleDoc 装配指示单对象
	 * @param con 连接对象
	 * @throws SQLException SQL异常
	 */
	public void updateAssembleDoc(AssembleDoc assembleDoc, Connection con)
			throws SQLException {
		DAO_AssembleDoc dao_AssembleDoc = (DAO_AssembleDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),DAO_AssembleDoc.class);
		Statement stmt = con.createStatement();
		log.debug("更新装配指示单SQL："
				+ dao_AssembleDoc.updateAssembleDoc(assembleDoc));
		stmt.execute(dao_AssembleDoc.updateAssembleDoc(assembleDoc));
		if (stmt != null) {
			stmt.close();
		}
	}
	
	
	

	
	
	
	
	
	
	
	/**
	 * 创建装配指示项
	 * 
	 * @param assDocItem 装配指示项对象
	 * @param con 连接对象
	 * @throws SQLException  SQL异常
	 */
	public void saveAssDocItem(AssDocItem assDocItem,Connection con)throws SQLException{
		DAO_AssembleDoc dao_AssembleDoc = (DAO_AssembleDoc) DAOFactoryAdapter
			.getInstance(DataBaseType.getDataBaseType(con),DAO_AssembleDoc.class);
		Statement stmt = con.createStatement();
		log.debug("保存装配指示项SQL：" + dao_AssembleDoc.saveAssDocItem(assDocItem));
		stmt.execute(dao_AssembleDoc.saveAssDocItem(assDocItem));
			if (stmt != null) {
				stmt.close();
			}
	}
	
	/**
	 * 通过装配指示单号查询装配指示项
	 * 
	 * @param id 装配指示单号
	 * @param con 连接对象
	 * @return 通过装配指示单号查询装配指示项列表
	 * @throws SQLException SQL异常
	 */
	public List<AssDocItem> getAssDocItemByAssembleDocId(int id,Connection con)throws SQLException{
		List<AssDocItem> list_AssDocItem = new ArrayList<AssDocItem>();
		DAO_AssembleDoc dao_AssembleDoc = (DAO_AssembleDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_AssembleDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过装配指示单号查询装配指示项列表SQL：" + dao_AssembleDoc.getAssDocItemByAssembleDocId(id));
		ResultSet rs = stmt
				.executeQuery(dao_AssembleDoc.getAssDocItemByAssembleDocId(id));
		while (rs.next()) {
			AssDocItem assDocItem = new AssDocItem();
			assDocItem.setId(rs.getInt("int_id"));
			assDocItem.setAssDocId(rs.getInt("Int_AssDocId"));
			assDocItem.setName(rs.getString("str_name"));
			assDocItem.setCode(rs.getString("str_code"));
			assDocItem.setDescription(rs.getString("Str_description"));
			list_AssDocItem.add(assDocItem);
		}
		if (stmt != null)
			stmt.close();
		return list_AssDocItem;
	}
	
	/**
	 * 通过装配指示单号删除装配指示项
	 * 
	 * @param id 装配指示单号
	 * @param con 连接对象
	 * @throws SQLException SQL异常
	 */
	public void delAssDocItemByAssembleDocId(int id,Connection con)throws SQLException{
		DAO_AssembleDoc dao_AssembleDoc = (DAO_AssembleDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_AssembleDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过装配指示单号删除装配指示项SQL：" + dao_AssembleDoc.delAssDocItemByAssembleDocId(id));
		stmt.execute(dao_AssembleDoc.delAssDocItemByAssembleDocId(id));
			if (stmt != null) {
				stmt.close();
			}
	}
}
