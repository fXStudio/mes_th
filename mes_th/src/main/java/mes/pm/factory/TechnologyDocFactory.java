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
import mes.pm.bean.TechDocItem;
import mes.pm.bean.TechItemFile;
import mes.pm.bean.TechnologyDoc;
import mes.pm.dao.DAO_TechnologyDoc;
import mes.system.dao.DAOFactoryAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 工艺操作说明书工厂类
 * 
 * @author YuanPeng 
 *
 */
public class TechnologyDocFactory {
	private final Log log = LogFactory.getLog(TechnologyDocFactory.class);// 日志

	/**
	 * 创建工艺操作说明书
	 * 
	 * @param technologyDoc
	 *            工艺操作说明书对象
	 * @param con
	 *            连接对象
	 * @throws SQLException
	 *             SQL异常
	 */
	public void saveTechnologyDoc(TechnologyDoc technologyDoc, Connection con)
			throws SQLException {
		DAO_TechnologyDoc dao_TechnologyDoc = (DAO_TechnologyDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_TechnologyDoc.class);
		Statement stmt = con.createStatement();
		log.debug("创建工艺操作说明书对象SQL：" + dao_TechnologyDoc.saveTechnologyDoc(technologyDoc));
		stmt.execute(dao_TechnologyDoc.saveTechnologyDoc(technologyDoc));
		if (stmt != null) {
			stmt.close();
		}
	}

	/**
	 * 通过ID查询工艺操作说明书
	 * 
	 * @param id
	 *            工艺操作说明书序列号
	 * @param con
	 *            连接对象
	 * @return 通过ID查询出的指令对象
	 * @throws java.sql.SQLException
	 */
	public TechnologyDoc getTechnologyDocById(int id, Connection con)
			throws SQLException, ParseException {
		TechnologyDoc technologyDoc = new TechnologyDoc();
		DAO_TechnologyDoc dao_TechnologyDoc = (DAO_TechnologyDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_TechnologyDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过ID查询工艺操作说明书SQL：" + dao_TechnologyDoc.getTechnologyDocById(id));
		ResultSet rs = stmt
				.executeQuery(dao_TechnologyDoc.getTechnologyDocById(id));
		if (rs.next()) {
			technologyDoc.setId(rs.getInt("int_id"));
			technologyDoc.setName(rs.getString("str_name"));
			technologyDoc.setMateriel(rs.getString("STR_MATERIEL"));
			technologyDoc.setDescription(rs.getString("Str_description"));
			technologyDoc.setCreateDate(rs.getString("Dat_createDate") == null ? null
							: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.parse(rs.getString("Dat_createDate")));
			technologyDoc.setUpDate(rs.getString("Dat_upDate") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("Dat_upDate")));
			technologyDoc.setCreateUID(rs.getInt("Int_CreateUID"));
			technologyDoc.setUpdateUID(rs.getInt("Int_UpdateUID"));
		}
		if (stmt != null)
			stmt.close();
		return technologyDoc;
	}

	/**
	 * 通过工艺操作说明书号删除该装配指示单
	 * 
	 * @param id
	 *            工艺操作说明书号
	 * @param con
	 *            连接对象
	 * @throws SQLException
	 *             SQL异常
	 */
	public void delTechnologyDocById(int id, Connection con) throws SQLException {
		DAO_TechnologyDoc dao_TechnologyDoc = (DAO_TechnologyDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_TechnologyDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过工艺操作说明书号删除该工艺操作说明书SQL："
				+ dao_TechnologyDoc.delTechnologyDocById(id));
		stmt.execute(dao_TechnologyDoc.delTechnologyDocById(id));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}

	/**
	 * 倒叙查询工艺操作说明书
	 * 
	 * @param con
	 *            连接对象
	 * @return 倒叙查询工艺操作说明书列表
	 * @throws SQLException
	 *             SQL异常
	 * @throws ParseException
	 *             类型转换异常
	 */
	public List<TechnologyDoc> getAllTechnologyDocsByDESC(Connection con)throws SQLException, ParseException{
		List<TechnologyDoc> list_TechnologyDoc = new ArrayList<TechnologyDoc>();
		DAO_TechnologyDoc dao_TechnologyDoc = (DAO_TechnologyDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_TechnologyDoc.class);
		Statement stmt = con.createStatement();
		log.debug("倒叙查询工艺操作说明书SQL：" + dao_TechnologyDoc.getAllTechnologyDocsByDESC());
		ResultSet rs = stmt
				.executeQuery(dao_TechnologyDoc.getAllTechnologyDocsByDESC());
		while (rs.next()) {
			TechnologyDoc technologyDoc = new TechnologyDoc();
			technologyDoc.setId(rs.getInt("int_id"));
			technologyDoc.setName(rs.getString("str_name"));
			technologyDoc.setMateriel(rs.getString("STR_MATERIEL"));
			technologyDoc.setDescription(rs.getString("Str_description"));
			technologyDoc.setCreateDate(rs.getString("Dat_createDate") == null ? null
							: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.parse(rs.getString("Dat_createDate")));
			technologyDoc.setUpDate(rs.getString("Dat_upDate") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("Dat_upDate")));
			technologyDoc.setCreateUID(rs.getInt("Int_CreateUID"));
			technologyDoc.setUpdateUID(rs.getInt("Int_UpdateUID"));
			list_TechnologyDoc.add(technologyDoc);
		}
		if (stmt != null)
			stmt.close();
		return list_TechnologyDoc;
	}
	
	/**
	 * 通过工艺操作说明书名查询工艺操作说明书号
	 * 
	 * @param name	工艺操作说明书名
	 * @param con	连接对象
	 * @return 工艺操作说明书号
	 * @throws SQLException SQL异常
	 */
	public int getTechnologyDocIdByName(String name ,Connection con)throws SQLException{
		int id=0;
		DAO_TechnologyDoc dao_TechnologyDoc = (DAO_TechnologyDoc) DAOFactoryAdapter
			.getInstance(DataBaseType.getDataBaseType(con),DAO_TechnologyDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过装配指示单名查询序号SQL：" + dao_TechnologyDoc.getTechnologyDocIdByName(name));
		ResultSet rs = stmt.executeQuery(dao_TechnologyDoc.getTechnologyDocIdByName(name));
		if (rs.next()) {
			id = rs.getInt("int_id");
		}
		if (stmt != null)
			stmt.close();
		return id;
	}
	
	/**
	 * 通过工艺操作说明书名查询工艺操作说明书数量
	 * 
	 * @param name 工艺操作说明书名
	 * @param con 连接对象
	 * @return 工艺操作说明书数量
	 * @throws SQLException SQL异常
	 */
	public int getTechnologyDocCountByName(String name ,Connection con)throws SQLException{
		int count=0;
		DAO_TechnologyDoc dao_TechnologyDoc = (DAO_TechnologyDoc) DAOFactoryAdapter
			.getInstance(DataBaseType.getDataBaseType(con),DAO_TechnologyDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过工艺操作说明书名查询工艺操作说明书数量SQL：" + 
				dao_TechnologyDoc.getTechnologyDocCountByName(name));
		ResultSet rs = stmt.executeQuery(dao_TechnologyDoc.getTechnologyDocCountByName(name));
		if (rs.next()) {
			count = rs.getInt(1);
		}
		if (stmt != null)
			stmt.close();
		return count;
	}
	
	/**
	 * 更新工艺操作说明书
	 * 
	 * @param assembleDoc 工艺操作说明书对象
	 * @param con 连接对象
	 * @throws SQLException SQL异常
	 */
	public void updateTechnologyDoc(TechnologyDoc technologyDoc, Connection con)
			throws SQLException {
		DAO_TechnologyDoc dao_TechnologyDoc = (DAO_TechnologyDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),DAO_TechnologyDoc.class);
		Statement stmt = con.createStatement();
		log.debug("更新工艺操作说明书SQL："
				+ dao_TechnologyDoc.updateTechnologyDoc(technologyDoc));
		stmt.execute(dao_TechnologyDoc.updateTechnologyDoc(technologyDoc));
		if (stmt != null) {
			stmt.close();
		}
	}
	
	/**
	 * 通过产品类别标示查询工艺操作说明书数量
	 * 
	 * @param materiel	物料类别标示
	 * @param con 连接对象
	 * @return 通过产品类别标示查询工艺操作说明书数量
	 * @throws SQLException SQL异常
	 */
	public int getTechnologyDocCountByMateriel(String materiel,Connection con)throws SQLException{
		int count=0;
		DAO_TechnologyDoc dao_TechnologyDoc = (DAO_TechnologyDoc) DAOFactoryAdapter
			.getInstance(DataBaseType.getDataBaseType(con),DAO_TechnologyDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过物料类别标示查询工艺操作说明书数量SQL：" + 
				dao_TechnologyDoc.getTechnologyDocCountByMateriel(materiel));
		ResultSet rs = stmt.executeQuery(dao_TechnologyDoc.getTechnologyDocCountByMateriel(materiel));
		if (rs.next()) {
			count = rs.getInt(1);
		}
		if (stmt != null)
			stmt.close();
		return count;
	}
	
	
	
	

	
	
	
	
	
	
	
	/**
	 * 创建工艺操作项
	 * 
	 * @param techDocItem 工艺操作项对象
	 * @param con 连接对象
	 * @throws SQLException  SQL异常
	 */
	public void saveTechDocItem(TechDocItem techDocItem,Connection con)throws SQLException{
		DAO_TechnologyDoc dao_TechnologyDoc = (DAO_TechnologyDoc) DAOFactoryAdapter
			.getInstance(DataBaseType.getDataBaseType(con),DAO_TechnologyDoc.class);
		Statement stmt = con.createStatement();
		log.debug("保存工艺操作项SQL：" + dao_TechnologyDoc.saveTechDocItem(techDocItem));
		stmt.execute(dao_TechnologyDoc.saveTechDocItem(techDocItem));
			if (stmt != null) {
				stmt.close();
			}
	}
	
	/**
	 * 通过工艺操作说明书号查询工艺操作项
	 * 
	 * @param id 工艺操作说明书号
	 * @param con 连接对象
	 * @return 通过工艺操作说明书号查询工艺操作项列表
	 * @throws SQLException SQL异常
	 */
	public List<TechDocItem> getTechDocItemByTechnologyDocId(int id,Connection con)throws SQLException{
		List<TechDocItem> list_TechDocItem = new ArrayList<TechDocItem>();
		DAO_TechnologyDoc dao_AssembleDoc = (DAO_TechnologyDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_TechnologyDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过工艺操作说明书号查询工艺操作项列表SQL：" + dao_AssembleDoc.getTechDocItemByTechnologyDocId(id));
		ResultSet rs = stmt
				.executeQuery(dao_AssembleDoc.getTechDocItemByTechnologyDocId(id));
		while (rs.next()) {
			TechDocItem techDocItem = new TechDocItem();
			techDocItem.setId(rs.getInt("int_id"));
			techDocItem.setTechDocId(rs.getInt("Int_TechDocId"));
			techDocItem.setProduceUnitId(rs.getInt("Int_produceUnit"));
			techDocItem.setContent(rs.getString("Str_Content"));
			list_TechDocItem.add(techDocItem);
		}
		if (stmt != null)
			stmt.close();
		return list_TechDocItem;
	}
	
	/**
	 * 通过工艺操作说明书号删除工艺操作项
	 * 
	 * @param id 工艺操作说明书号
	 * @param con 连接对象
	 * @throws SQLException SQL异常
	 */
	public void delTechDocItemByTechnologyDocId(int id,Connection con)throws SQLException{
		DAO_TechnologyDoc dao_TechnologyDoc = (DAO_TechnologyDoc) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_TechnologyDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过工艺操作说明书号删除工艺操作项SQL：" + dao_TechnologyDoc.delTechDocItemByTechnologyDocId(id));
		stmt.execute(dao_TechnologyDoc.delTechDocItemByTechnologyDocId(id));
			if (stmt != null) {
				stmt.close();
			}
	}
	
	
	/**
	 * 查询工艺操作项最大序号
	 * 
	 * @param con 连接对象
	 * @return 工艺操作项最大序号
	 */
	public int getTechDocItemMaxId(Connection con)throws SQLException{
		int id=0;
		DAO_TechnologyDoc dao_TechnologyDoc = (DAO_TechnologyDoc) DAOFactoryAdapter
			.getInstance(DataBaseType.getDataBaseType(con),DAO_TechnologyDoc.class);
		Statement stmt = con.createStatement();
		log.debug("查询工艺操作项最大序号SQL：" + dao_TechnologyDoc.getTechDocItemMaxId());
		ResultSet rs = stmt.executeQuery(dao_TechnologyDoc.getTechDocItemMaxId());
		if (rs.next()) {
			id = rs.getInt(1);
		}
		if (stmt != null)
			stmt.close();
		return id;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 通过工艺操作项序号查询工艺操作项文件
	 * 
	 * @param tech 工艺操作项序号
	 * @param con 连接对象
	 * @return 通过工艺操作项序号查询工艺操作项文件
	 * @throws SQLException
	 */
	public TechItemFile getTechItemFileByTechDocItemId(int tech,Connection con)throws SQLException{
		TechItemFile techItemFile = null;
		DAO_TechnologyDoc dao_TechnologyDoc = (DAO_TechnologyDoc) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),DAO_TechnologyDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过工艺操作项序号查询工艺操作项文件列表SQL：" + dao_TechnologyDoc.getTechItemFileByTechDocItemId(tech));
		ResultSet rs = stmt.executeQuery(dao_TechnologyDoc.getTechItemFileByTechDocItemId(tech));
		if(rs.next()){
			techItemFile = new TechItemFile();
			techItemFile.setId(rs.getInt("int_id"));
			techItemFile.setTechDocItemId(rs.getInt("INT_TECHITEMID"));
			techItemFile.setPathName(rs.getString("STR_PATHNAME"));
		}
		if (stmt != null)
			stmt.close();
		return techItemFile;
	}
	
	/**
	 * 创建工艺操作项文件
	 * 
	 * @param tif 工艺操作项文件对象
	 * @param con 连接对象
	 * @throws SQLException SQL异常
	 */
	public void saveTechItemFile(TechItemFile tif,Connection con)throws SQLException{
		DAO_TechnologyDoc dao_TechnologyDoc = (DAO_TechnologyDoc) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),DAO_TechnologyDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过工艺操作项序号查询工艺操作项文件列表SQL：" + dao_TechnologyDoc.saveTechItemFile(tif));
		stmt.execute(dao_TechnologyDoc.saveTechItemFile(tif));
		if (stmt != null)
			stmt.close();
	}
	
	/**
	 * 通过工艺操作项删除工艺操作项文件
	 * 
	 * @param techDocItemId	工艺操作项
	 * @param con 连接对象
	 * @throws SQLException sql异常
	 */
	public void delTechItemFile(int techDocItemId,Connection con)throws SQLException{
		DAO_TechnologyDoc dao_TechnologyDoc = (DAO_TechnologyDoc) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),DAO_TechnologyDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过工艺操作项删除工艺操作项文件SQL：" + dao_TechnologyDoc.delTechItemFile(techDocItemId));
		stmt.execute(dao_TechnologyDoc.delTechItemFile(techDocItemId));
		if (stmt != null)
			stmt.close();
	}
	
	/**
	 * 通过工艺操作说明书序号删除工艺操作项文件
	 * 
	 * @param techDocId 工艺操作说明书序号
	 * @param con 连接对象
	 * @throws SQLException sql异常
	 */
	public void delTechItemFileByTechDoc(int techDocId,Connection con)throws SQLException{
		DAO_TechnologyDoc dao_TechnologyDoc = (DAO_TechnologyDoc) DAOFactoryAdapter
			.getInstance(DataBaseType.getDataBaseType(con),DAO_TechnologyDoc.class);
		Statement stmt = con.createStatement();
		log.debug("过工艺操作说明书序号删除工艺操作项文件SQL：" + dao_TechnologyDoc.delTechItemFileByTechDoc(techDocId));
		stmt.execute(dao_TechnologyDoc.delTechItemFileByTechDoc(techDocId));
		if (stmt != null)
			stmt.close();
	}
	
	/**
	 * 通过旧工艺操作项序号更新新工艺操作项文件
	 * 
	 * @param oldItemId	旧工艺操作项
	 * @param con 连接对象
	 * @throws SQLException sql异常
	 */
	public void updateTechItemId(int oldItemId,Connection con)throws SQLException{
		DAO_TechnologyDoc dao_TechnologyDoc = (DAO_TechnologyDoc) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),DAO_TechnologyDoc.class);
		Statement stmt = con.createStatement();
		log.debug("通过旧工艺操作项序号更新新工艺操作项SQL：" + dao_TechnologyDoc.updateTechItemId(oldItemId));
		stmt.execute(dao_TechnologyDoc.updateTechItemId(oldItemId));
		if (stmt != null)
			stmt.close();
	}
}