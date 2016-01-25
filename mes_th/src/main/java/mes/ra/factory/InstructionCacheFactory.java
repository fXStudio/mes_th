package mes.ra.factory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.List;
import java.util.ArrayList;
import mes.system.dao.DAOFactoryAdapter;
import mes.framework.DataBaseType;
import java.text.SimpleDateFormat;

import mes.ra.bean.Instruction;
import mes.ra.dao.DAO_Instruction_cache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author YuanPeng
 */
public class InstructionCacheFactory {
	private final Log log = LogFactory.getLog(InstructionCacheFactory.class);
	/**
	 * 保存指令
	 * 
	 * @param instruction
	 *            指令对象
	 * @param con
	 *            连接对象
	 * @throws java.sql.SQLException
	 */
	public void saveInstructionCache(Instruction instruction, Connection con)
			throws SQLException {
		DAO_Instruction_cache dao_Instruction_cache = (DAO_Instruction_cache) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Instruction_cache.class);
		Statement stmt = con.createStatement();
		log.debug("保存指令"+dao_Instruction_cache.saveInstructionCache(instruction));
		stmt.execute(dao_Instruction_cache.saveInstructionCache(instruction));
		if (stmt != null) {
			stmt.close();
		}
	}

	/**
	 * 通过ID查询指令
	 * 
	 * @param id
	 *            指令序列号
	 * @param con
	 *            连接对象
	 * @return 通过ID查询出的指令对象
	 * @throws java.sql.SQLException
	 */
	public Instruction getInstructionCacheById(int id, Connection con)
			throws SQLException, ParseException {
		Instruction instruction = new Instruction();
		DAO_Instruction_cache dao_Instruction_cache = (DAO_Instruction_cache) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Instruction_cache.class);
		Statement stmt = con.createStatement();
		log.debug("通过ID查询指令"+dao_Instruction_cache.getInstructionCacheById(id));
		ResultSet rs = stmt.executeQuery(dao_Instruction_cache
				.getInstructionCacheById(id));
		if (rs.next()) {
			instruction.setId(rs.getInt("INT_ID"));
			instruction.setProdunitid(rs.getInt("INT_PRODUNITID"));
			instruction.setProduceDate(rs.getDate("DAT_PRODUCEDATE"));
			instruction.setVersionCode(rs.getString("STR_VERSIONCODE"));
			instruction.setInstructionOrder(rs.getInt("INT_INSTRUCTORDER"));
			instruction.setPlanDate(rs.getString("DAT_PLANDATE") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("DAT_PLANDATE")));
			instruction.setPlanOrder(rs.getInt("INT_PLANORDER"));
			instruction.setProduceType(rs.getString("STR_PRODUCETYPE"));
			instruction.setProduceName(rs.getString("STR_PRODUCENAME"));
			instruction.setProduceMarker(rs.getString("STR_PRODUCEMARKER"));
			instruction.setWorkOrder(rs.getString("STR_WORKORDER"));
			instruction.setWorkTeam(rs.getString("STR_WORKTEAM"));
			instruction.setPlanBegin(rs.getString("TIM_PLANBEGIN") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("TIM_PLANBEGIN")));
			instruction.setPlanFinish(rs.getString("TIM_PLANFINISH") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("TIM_PLANFINISH")));
			instruction.setCount(rs.getInt("INT_COUNT"));
			instruction.setInstructStateID(rs.getInt("INT_INSTRUCTSTATEID"));
			instruction.setIssuance(rs.getInt("INT_ISSUANCE"));
			instruction.setStaError(rs.getInt("INT_STAERROR"));

		}
		if (stmt != null)
			stmt.close();
		return instruction;

	}

	/**
	 * 通过生产单元号、生产日期、指令顺序号删除该指令
	 * 
	 * @param Int_produnitid
	 *            指令序列号
	 * @param str_date
	 *  		生产日期
	 * @param order
	 * 		指令顺序号
	 * @param con
	 *            连接对象
	 * @throws java.sql.SQLException
	 */
	public void delInstructionCacheByProduceUnitDateWorkorderOrder(int Int_produnitid, String str_date,String workOrder,int order, Connection con)
			throws SQLException {
		DAO_Instruction_cache dao_Instruction_cache = (DAO_Instruction_cache) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Instruction_cache.class);
		Statement stmt = con.createStatement();
		log.debug("通过生产单元号、生产日期、班次、指令顺序号删除该指令"+dao_Instruction_cache.delInstructionCacheByProduceUnitDateWorkorderOrder(Int_produnitid,str_date,workOrder,order));
		stmt.execute(dao_Instruction_cache.delInstructionCacheByProduceUnitDateWorkorderOrder(Int_produnitid,str_date,workOrder,order));
		if (stmt != null) {
			stmt.close();
		}
	}

	/**
	 * 更新指令对象
	 * 
	 * @param instruction
	 *            指令对象
	 * @param con
	 *            连接对象
	 * @throws java.sql.SQLException
	 */
	public void updateInstructionCache(Instruction instruction, Connection con)
			throws SQLException {
		DAO_Instruction_cache dao_Instruction_cache = (DAO_Instruction_cache) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Instruction_cache.class);
		Statement stmt = con.createStatement();
		log.debug("更新指令对象"+dao_Instruction_cache.updateInstructionCache(instruction));
		stmt.execute(dao_Instruction_cache.updateInstructionCache(instruction));
		if (stmt != null) {
			stmt.close();
		}
	}

	/**
	 * 查询记录集行数
	 * 
	 * @param con
	 *            连接对象
	 * @return 指令记录集行数
	 * @throws java.sql.SQLException
	 */
	public int getInstructionCacheCount(Connection con) throws SQLException {
		int count;
		DAO_Instruction_cache dao_Instruction_cache = (DAO_Instruction_cache) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Instruction_cache.class);
		Statement stmt = con.createStatement();
		log.debug("查询记录集行数"+dao_Instruction_cache.getInstructionCacheCount());
		ResultSet rs = stmt.executeQuery(dao_Instruction_cache
				.getInstructionCacheCount());
		rs.next();
		count = rs.getInt(1);
		if (stmt != null) {
			stmt.close();
		}
		return count;
	}

	/**
	 * 查询所有指令记录集
	 * 
	 * @param con
	 *            连接对象
	 * @return 所有指令对象集合
	 * @throws java.sql.SQLException
	 */
	public List<Instruction> getAllInstructionCache(Connection con)
			throws SQLException, ParseException {
		Instruction instruction = null;
		List<Instruction> list = new ArrayList<Instruction>();
		DAO_Instruction_cache dao_Instruction_cache = (DAO_Instruction_cache) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Instruction_cache.class);
		Statement stmt = con.createStatement();
		log.debug("查询所有指令记录集"+dao_Instruction_cache.getInstructionCacheCount());
		ResultSet rs = stmt.executeQuery(dao_Instruction_cache
				.getAllInstructionCache());
		while (rs.next()) {
			instruction.setId(rs.getInt("INT_ID"));
			instruction.setProdunitid(rs.getInt("INT_PRODUNITID"));
			instruction.setProduceDate(rs.getDate("DAT_PRODUCEDATE"));
			instruction.setVersionCode(rs.getString("STR_VERSIONCODE"));
			instruction.setInstructionOrder(rs.getInt("INT_INSTRUCTORDER"));
			instruction.setPlanDate(rs.getString("DAT_PLANDATE") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("DAT_PLANDATE")));
			instruction.setPlanOrder(rs.getInt("INT_PLANORDER"));
			instruction.setProduceType(rs.getString("STR_PRODUCETYPE"));
			instruction.setProduceName(rs.getString("STR_PRODUCENAME"));
			instruction.setProduceMarker(rs.getString("STR_PRODUCEMARKER"));
			instruction.setWorkOrder(rs.getString("STR_WORKORDER"));
			instruction.setWorkTeam(rs.getString("STR_WORKTEAM"));
			instruction.setPlanBegin(rs.getString("TIM_PLANBEGIN") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("TIM_PLANBEGIN")));
			instruction.setPlanFinish(rs.getString("TIM_PLANFINISH") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("TIM_PLANFINISH")));
			instruction.setCount(rs.getInt("INT_COUNT"));
			instruction.setInstructStateID(rs.getInt("INT_INSTRUCTSTATEID"));
			instruction.setIssuance(rs.getInt("INT_ISSUANCE"));
			instruction.setStaError(rs.getInt("INT_STAERROR"));

			list.add(instruction);
		}
		if (stmt != null) {
			stmt.close();
		}
		return list;
	}

	/**
	 * 检测指令顺序号所在行是否为第一行
	 * 
	 * @param order
	 *            指令顺序号
	 * @param con
	 *            连接对象
	 * @return boolean 顺序号是否已在第一行
	 * @throws java.sql.SQLException
	 * 
	 */
	public boolean checkFirst(int Int_produnitid, String str_date,String workOrder, int order,
			Connection con) throws SQLException {
		DAO_Instruction_cache dao_Instruction_cache = (DAO_Instruction_cache) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Instruction_cache.class);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		log.debug("检测指令顺序号所在行是否为第一行"+dao_Instruction_cache.getInstructionCacheByProduceUnitProduceDateOrder(Int_produnitid,
						str_date,workOrder));
		ResultSet rs = stmt.executeQuery(dao_Instruction_cache
				.getInstructionCacheByProduceUnitProduceDateOrder(Int_produnitid,
						str_date,workOrder));
		if (rs.next() && rs.getInt(5) == order)
			return true;
		if (stmt != null) {
			stmt.close();
		}
		return false;
	}

	/**
	 * 检测指令顺序号所在行是否为最后一行
	 * 
	 * @param order
	 *            指令顺序号
	 * @param con
	 *            连接对象
	 * @return boolean 顺序号是否已在最后一行
	 * @throws java.sql.SQLException
	 * 
	 */
	public boolean checkLast(int Int_produnitid, String str_date,String workOrder, int order,
			Connection con) throws SQLException {
		DAO_Instruction_cache dao_Instruction_cache = (DAO_Instruction_cache) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Instruction_cache.class);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		log.debug("查询所有指令记录集"+dao_Instruction_cache.getInstructionCacheByProduceUnitProduceDateOrder(Int_produnitid,str_date,workOrder));
		ResultSet rs = stmt.executeQuery(dao_Instruction_cache
				.getInstructionCacheByProduceUnitProduceDateOrder(Int_produnitid,
						str_date,workOrder));
		while (rs.isLast() && rs.getInt(5) == order)
			return true;
		if (stmt != null) {
			stmt.close();
		}
		return false;
	}

	/**
	 * 查询所有指令对象按照顺序号排序
	 * 
	 * @param con
	 *            连接对象
	 * @return 指令对象集合
	 * @throws java.sql.SQLException
	 */
	public List<Instruction> getAllInstructionCacheByOrder(Connection con)
			throws SQLException, ParseException {
		Instruction instruction = null;
		List<Instruction> list = new ArrayList<Instruction>();
		DAO_Instruction_cache dao_Instruction_cache = (DAO_Instruction_cache) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Instruction_cache.class);
		Statement stmt = con.createStatement();
		log.debug("查询所有指令对象按照顺序号排序"+dao_Instruction_cache.getAllInstructionCache());
		ResultSet rs = stmt.executeQuery(dao_Instruction_cache
				.getAllInstructionCache());
		while (rs.next()) {
			instruction.setId(rs.getInt("INT_ID"));
			instruction.setProdunitid(rs.getInt("INT_PRODUNITID"));
			instruction.setProduceDate(rs.getDate("DAT_PRODUCEDATE"));
			instruction.setVersionCode(rs.getString("STR_VERSIONCODE"));
			instruction.setInstructionOrder(rs.getInt("INT_INSTRUCTORDER"));
			instruction.setPlanDate(rs.getString("DAT_PLANDATE") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("DAT_PLANDATE")));
			instruction.setPlanOrder(rs.getInt("INT_PLANORDER"));
			instruction.setProduceType(rs.getString("STR_PRODUCETYPE"));
			instruction.setProduceName(rs.getString("STR_PRODUCENAME"));
			instruction.setProduceMarker(rs.getString("STR_PRODUCEMARKER"));
			instruction.setWorkOrder(rs.getString("STR_WORKORDER"));
			instruction.setWorkTeam(rs.getString("STR_WORKTEAM"));
			instruction.setPlanBegin(rs.getString("TIM_PLANBEGIN") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("TIM_PLANBEGIN")));
			instruction.setPlanFinish(rs.getString("TIM_PLANFINISH") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("TIM_PLANFINISH")));
			instruction.setCount(rs.getInt("INT_COUNT"));
			instruction.setInstructStateID(rs.getInt("INT_INSTRUCTSTATEID"));
			instruction.setIssuance(rs.getInt("INT_ISSUANCE"));
			instruction.setStaError(rs.getInt("INT_STAERROR"));

			list.add(instruction);
		}
		if (stmt != null) {
			stmt.close();
		}
		return list;
	}


	/**
	 * 查询比Int_instructOrder小的对象
	 * 
	 * @param Int_instructOrder
	 *            指令顺序号
	 * @param con
	 *            连接对象
	 * @throws java.sql.SQLException
	 *             return Instruction 返回比Int_instructOrder小的对象
	 */
	public List<Instruction> OrderMinus(int Int_produnitid, String str_date,String workOrder,int Int_instructOrder, Connection con)
			throws SQLException, ParseException {
		List<Instruction> list = new ArrayList<Instruction>();
		DAO_Instruction_cache dao_Instruction = (DAO_Instruction_cache) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Instruction_cache.class);
		Instruction instruction = new Instruction();
		Statement stmt = con.createStatement();
		log.debug("查询比Int_instructOrder小的对象"+dao_Instruction.getByOrderMinus(Int_produnitid,str_date,workOrder,Int_instructOrder));
		ResultSet rs = stmt.executeQuery(dao_Instruction
				.getByOrderMinus(Int_produnitid,str_date,workOrder,Int_instructOrder));
		if (rs.next()) {
			instruction.setId(rs.getInt("INT_ID"));
			instruction.setProdunitid(rs.getInt("INT_PRODUNITID"));
			instruction.setProduceDate(rs.getDate("DAT_PRODUCEDATE"));
			instruction.setVersionCode(rs.getString("STR_VERSIONCODE"));
			instruction.setInstructionOrder(rs.getInt("INT_INSTRUCTORDER"));
			instruction.setPlanDate(rs.getString("DAT_PLANDATE") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("DAT_PLANDATE")));
			instruction.setPlanOrder(rs.getInt("INT_PLANORDER"));
			instruction.setProduceType(rs.getString("STR_PRODUCETYPE"));
			instruction.setProduceName(rs.getString("STR_PRODUCENAME"));
			instruction.setProduceMarker(rs.getString("STR_PRODUCEMARKER"));
			instruction.setWorkOrder(rs.getString("STR_WORKORDER"));
			instruction.setWorkTeam(rs.getString("STR_WORKTEAM"));
			instruction.setPlanBegin(rs.getString("TIM_PLANBEGIN") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("TIM_PLANBEGIN")));
			instruction.setPlanFinish(rs.getString("TIM_PLANFINISH") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("TIM_PLANFINISH")));
			instruction.setCount(rs.getInt("INT_COUNT"));
			instruction.setInstructStateID(rs.getInt("INT_INSTRUCTSTATEID"));
			instruction.setIssuance(rs.getInt("INT_ISSUANCE"));
			instruction.setStaError(rs.getInt("INT_STAERROR"));

			list.add(instruction);
		}
		if (stmt != null) {
			stmt.close();
		}
		return list;
	}

	/**
	 * 查询比Int_instructOrder大的对象
	 * 
	 * @param Int_instructOrder
	 *            指令顺序号
	 * @param con
	 *            连接对象
	 * @throws java.sql.SQLException
	 *             return Instruction 返回比Int_instructOrder大的对象
	 */
	public List<Instruction> OrderPlus(int Int_produnitid, String str_date,String workOrder,int Int_instructOrder, Connection con)
			throws SQLException, ParseException {
		List<Instruction> list = new ArrayList<Instruction>();
		DAO_Instruction_cache dao_Instruction = (DAO_Instruction_cache) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Instruction_cache.class);
		Instruction instruction = new Instruction();
		Statement stmt = con.createStatement();
		log.debug("查询比Int_instructOrder大的对象"+dao_Instruction
				.getByOrderPlus(Int_produnitid,str_date,workOrder,Int_instructOrder));
		ResultSet rs = stmt.executeQuery(dao_Instruction
				.getByOrderPlus(Int_produnitid,str_date,workOrder,Int_instructOrder));
		if (rs.next()) {
			instruction.setId(rs.getInt("INT_ID"));
			instruction.setProdunitid(rs.getInt("INT_PRODUNITID"));
			instruction.setProduceDate(rs.getDate("DAT_PRODUCEDATE"));
			instruction.setVersionCode(rs.getString("STR_VERSIONCODE"));
			instruction.setInstructionOrder(rs.getInt("INT_INSTRUCTORDER"));
			instruction.setPlanDate(rs.getString("DAT_PLANDATE") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("DAT_PLANDATE")));
			instruction.setPlanOrder(rs.getInt("INT_PLANORDER"));
			instruction.setProduceType(rs.getString("STR_PRODUCETYPE"));
			instruction.setProduceName(rs.getString("STR_PRODUCENAME"));
			instruction.setProduceMarker(rs.getString("STR_PRODUCEMARKER"));
			instruction.setWorkOrder(rs.getString("STR_WORKORDER"));
			instruction.setWorkTeam(rs.getString("STR_WORKTEAM"));
			instruction.setPlanBegin(rs.getString("TIM_PLANBEGIN") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("TIM_PLANBEGIN")));
			instruction.setPlanFinish(rs.getString("TIM_PLANFINISH") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("TIM_PLANFINISH")));
			instruction.setCount(rs.getInt("INT_COUNT"));
			instruction.setInstructStateID(rs.getInt("INT_INSTRUCTSTATEID"));
			instruction.setIssuance(rs.getInt("INT_ISSUANCE"));
			instruction.setStaError(rs.getInt("INT_STAERROR"));

			list.add(instruction);
		}
		if (stmt != null) {
			stmt.close();
		}
		return list;
	}

	/**
	 * 指令发布
	 * 袁鹏
	 * @param Int_produnitid
	 *            生产单元号
	 * @param str_date
	 * 			生产日期
	 * @param Int_instructOrder
	 * 			作业指令顺序号
	 * @param con
	 *            连接对象
	 * @throws java.sql.SQLException
	 */
	public void IssuanceByProduceUnitDateWorkorderOrder(int Int_produnitid, String str_date,String workOrder,
			int Int_instructOrder, Connection con) throws SQLException {
		DAO_Instruction_cache dao_Instruction_cache = (DAO_Instruction_cache) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Instruction_cache.class);
		Statement stmt = con.createStatement();
		log.debug("查询比Int_instructOrder大的对象"+dao_Instruction_cache
				.IssuanceByProduceUnitDateWorkorderOrder(Int_produnitid,str_date,workOrder,Int_instructOrder));
		stmt.execute(dao_Instruction_cache
				.IssuanceByProduceUnitDateWorkorderOrder(Int_produnitid,str_date,workOrder,Int_instructOrder));
		if (stmt != null) {
			stmt.close();
		}
	}

	/**
	 * 根据生产时间和生产单元查看相关的指令。 谢静天
	 * 
	 * @param Str_produceUnit,String
	 *            str_date 生产单元 生产日期
	 * @return 返回相对应的指令
	 */
	public List<Instruction> getInstructionCacheByProduceUnitProduceDateOrder(
			int Int_produnitid, String str_date, String workOrder,Connection con)
			throws SQLException, ParseException {
		List<Instruction> list = new ArrayList<Instruction>();
		DAO_Instruction_cache dao_Instruction_cache = (DAO_Instruction_cache) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Instruction_cache.class);
		Statement stmt = con.createStatement();
		log.debug("根据生产时间和生产单元查看相关的指令"+dao_Instruction_cache
				.getInstructionCacheByProduceUnitProduceDateOrder(Int_produnitid,
						str_date,workOrder));
		ResultSet rs = stmt.executeQuery(dao_Instruction_cache
				.getInstructionCacheByProduceUnitProduceDateOrder(Int_produnitid,
						str_date,workOrder));
		while (rs.next()) {
			Instruction instruction = new Instruction();
			instruction.setId(rs.getInt("INT_ID"));
			instruction.setProdunitid(rs.getInt("INT_PRODUNITID"));
			instruction.setProduceDate(rs.getString("DAT_PRODUCEDATE") == null ? null
					: (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("DAT_PRODUCEDATE"))));
			instruction.setVersionCode(rs.getString("STR_VERSIONCODE"));
			instruction.setInstructionOrder(rs.getInt("INT_INSTRUCTORDER"));
			instruction.setPlanDate(rs.getString("DAT_PLANDATE") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("DAT_PLANDATE")));
			instruction.setPlanOrder(rs.getInt("INT_PLANORDER"));
			instruction.setProduceType(rs.getString("STR_PRODUCETYPE"));
			instruction.setProduceName(rs.getString("STR_PRODUCENAME"));
			instruction.setProduceMarker(rs.getString("STR_PRODUCEMARKER"));
			instruction.setWorkOrder(rs.getString("STR_WORKORDER"));
			instruction.setWorkTeam(rs.getString("STR_WORKTEAM"));
			instruction.setPlanBegin(rs.getString("TIM_PLANBEGIN") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("TIM_PLANBEGIN")));
			instruction.setPlanFinish(rs.getString("TIM_PLANFINISH") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
							.getString("TIM_PLANFINISH")));
			instruction.setCount(rs.getInt("INT_COUNT"));
			instruction.setInstructStateID(rs.getInt("INT_INSTRUCTSTATEID"));
			instruction.setIssuance(rs.getInt("INT_ISSUANCE"));
			instruction.setStaError(rs.getInt("INT_STAERROR"));
			list.add(instruction);
		}
		if (stmt != null) {
			stmt.close();
		}
		return list;
	}

	/**
	 * 删除临时表中所有数据
	 * 
	 * @param con
	 *            连接对象
	 * @throws java.sql.SQLException
	 */
	public void TruncateInstructionCache(Connection con) throws SQLException {
		DAO_Instruction_cache dao_Instruction_cache = (DAO_Instruction_cache) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Instruction_cache.class);
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			log.debug("删除临时表中所有数据"+dao_Instruction_cache.DeleteInstructionCache());
			stmt.execute(dao_Instruction_cache.DeleteInstructionCache());

		} catch (Exception e) {

		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	/**
	 * 通过生产单元号、生产日期查询记录数量
	 * 
	 * @param ProduceUnitID
	 *            生产单元号
	 * @param str_date
	 *            生产日期
	 * @return 返回通过生产单元号、生产日期查询记录的数量
	 */
	public int getCountByProUnitDateOrder(int ProduceUnitID, String str_date,String workOrder,
			Connection con) {
		int count = 0;
		try {
			
			DAO_Instruction_cache dao_Instruction_cache = (DAO_Instruction_cache) DAOFactoryAdapter
					.getInstance(DataBaseType.getDataBaseType(con),
							DAO_Instruction_cache.class);
			Statement stmt = con.createStatement();
			log.debug("通过生产单元号、生产日期查询记录数量"+dao_Instruction_cache
					.getCountByProUnitDateOrder(ProduceUnitID, str_date,workOrder));
			ResultSet rs = stmt.executeQuery(dao_Instruction_cache
					.getCountByProUnitDateOrder(ProduceUnitID, str_date,workOrder));
			if (rs.next()) {
				count = rs.getInt(1);
			}
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 删除临时表中该生产单元所有数据
	 * 
	 * @param ProduceUnitID
	 *            生产单元号
	 * @param con
	 *            连接对象
	 * @throws java.sql.SQLException
	 */
	public void DeleteInstructionCacheByProdUnitIdproducedateWorkorder(int ProduceUnitID,String str_date,String workOrder,
			Connection con) throws SQLException {
		DAO_Instruction_cache dao_Instruction_cache = (DAO_Instruction_cache) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Instruction_cache.class);
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			log.debug("删除临时表中该生产单元该班次所有数据"+dao_Instruction_cache
					.DeleteInstructionCacheByProdUnitIdproducedateWorkorder(ProduceUnitID,str_date,workOrder));
			stmt.executeUpdate(dao_Instruction_cache.DeleteInstructionCacheByProdUnitIdproducedateWorkorder(ProduceUnitID,str_date,workOrder));
		} catch (Exception e) {
              e.printStackTrace();
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}
	
	/**
     * 通过生产单元号、生产日期、班次、指令顺序号查询指令
     * 
     * @param ProduceUnitID
     * 			生产单元号
     * @param str_date
     * 			生产日期
     * * @param wrokOrder
     * 			班次
     * @param Int_instructOrder
     * 				指令顺序号
     * @return 作业指令对象
     */
	public Instruction getInstructionByProdUnitDateOrder(int ProduceUnitID,String str_date,String workOrder,int Int_instructOrder,Connection con){
		Instruction instruction = new Instruction();
		try{	
			DAO_Instruction_cache dao_Instruction = (DAO_Instruction_cache) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),DAO_Instruction_cache.class);
			Statement stmt = con.createStatement();
			log.debug("通过生产单元号、生产日期、班次、指令顺序号查询指令"+dao_Instruction
					.getInstructionByProdUnitDateWorkorderOrder(ProduceUnitID,str_date,workOrder,Int_instructOrder));
			ResultSet rs = stmt.executeQuery(dao_Instruction
					.getInstructionByProdUnitDateWorkorderOrder(ProduceUnitID,str_date,workOrder,Int_instructOrder));
			if (rs.next()) {
				instruction.setId(rs.getInt("INT_ID"));
				instruction.setProdunitid(rs.getInt("INT_PRODUNITID"));
				instruction.setProduceDate(rs.getDate("DAT_PRODUCEDATE"));
				instruction.setVersionCode(rs.getString("STR_VERSIONCODE"));
				instruction.setInstructionOrder(rs.getInt("INT_INSTRUCTORDER"));
				instruction.setPlanDate(rs.getString("DAT_PLANDATE") == null ? null
						: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
								.getString("DAT_PLANDATE")));
				instruction.setPlanOrder(rs.getInt("INT_PLANORDER"));
				instruction.setProduceType(rs.getString("STR_PRODUCETYPE"));
				instruction.setProduceName(rs.getString("STR_PRODUCENAME"));
				instruction.setProduceMarker(rs.getString("STR_PRODUCEMARKER"));
				instruction.setWorkOrder(rs.getString("STR_WORKORDER"));
				instruction.setWorkTeam(rs.getString("STR_WORKTEAM"));
				instruction.setPlanBegin(rs.getString("TIM_PLANBEGIN") == null ? null
						: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
								.getString("TIM_PLANBEGIN")));
				instruction.setPlanFinish(rs.getString("TIM_PLANFINISH") == null ? null
						: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
								.getString("TIM_PLANFINISH")));
				instruction.setCount(rs.getInt("INT_COUNT"));
				instruction.setInstructStateID(rs.getInt("INT_INSTRUCTSTATEID"));
				instruction.setIssuance(rs.getInt("INT_ISSUANCE"));
				instruction.setStaError(rs.getInt("INT_STAERROR"));
			}
			if (stmt != null) {
				stmt.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
			return instruction;
	}
	
		/**
	    * 该生产日期、生产单元、大于该指令顺序号的指令顺序号减1
	    * 
	    *  @param  ProduceUnitID
	    *  		生产单元号
	    *  @param  str_date
	    *  		生产日期号
	    *  @param  order
	    *  		指令顺序号
	    * @return  返回sql
	    */
	public void MinusInstructionOrder(int ProduceUnitID,String str_date,String workOrder,int order,Connection con){
		try{
		DAO_Instruction_cache dao_Instruction_cache = (DAO_Instruction_cache)DAOFactoryAdapter.getInstance(
	               DataBaseType.getDataBaseType(con),DAO_Instruction_cache.class);
			Statement stmt = con.createStatement();
			log.debug("该生产日期、生产单元、班次、大于该指令顺序号的指令顺序号减1"+dao_Instruction_cache.MinusInstructionOrder(ProduceUnitID,str_date,workOrder,order));
	       stmt.execute(dao_Instruction_cache.MinusInstructionOrder(ProduceUnitID,str_date,workOrder,order));
	       	if (stmt != null) {
				stmt.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
     * 发布该生产单元、生产日期的所有指令
     * 
     * @param Int_produnitid
     * @param str_date
     * @return
     */
    public void IssuanceAllByProduceUnitDateWorkorder(int Int_produnitid, String str_date,String workOrder,Connection con){
    	Statement stmt = null;
    	try{
	    	DAO_Instruction_cache dao_Instruction_cache = (DAO_Instruction_cache) DAOFactoryAdapter
			.getInstance(DataBaseType.getDataBaseType(con),
					DAO_Instruction_cache.class);
	    	stmt = con.createStatement();
	    	log.debug("发布该生产单元、生产日期、班次的所有指令"+dao_Instruction_cache
					.IssuanceAllByProduceUnitDateWorkorder(Int_produnitid,str_date,workOrder));
			stmt.execute(dao_Instruction_cache
					.IssuanceAllByProduceUnitDateWorkorder(Int_produnitid,str_date,workOrder));
			if (stmt != null) {
				stmt.close();
			}
    	}catch(Exception e){e.printStackTrace();}finally{
    		try{
    			if (stmt != null) stmt.close();
    		}catch(SQLException sqle){sqle.printStackTrace();}
    	}
    }
    
    /**
     * 查找指令最大的顺序号
	   进行指令追加
     * @param int_produnitid
     * @param str_date
     * @param wrokOrder
     * @param con
     * @return
     * @throws SQLException
     * @throws ParseException
     */

	   public int getInstructionMaxOrder(int int_produnitid,String str_date,String wrokOrder,Connection con)throws SQLException ,ParseException{
	      
	      
		   DAO_Instruction_cache dao_Instruction_cache = (DAO_Instruction_cache)DAOFactoryAdapter.getInstance(
	               DataBaseType.getDataBaseType(con),DAO_Instruction_cache.class);
	    
			Statement stmt = con.createStatement();
			log.debug("查找指令最大的顺序号"+dao_Instruction_cache
					.getInstructionMaxOrder(int_produnitid,str_date,wrokOrder));
			ResultSet rs = stmt.executeQuery(dao_Instruction_cache
					.getInstructionMaxOrder(int_produnitid,str_date,wrokOrder));
			 int n=0;
			if(rs.next()){
				n=rs.getInt(1);
				
			}
			if (stmt != null) {
				stmt.close();
			}
				return n;
			
	   }
	   
	   /**
	     * 通过生产单元号、生产日期、产品标识查询记录数量
	     *  袁鹏
	     * @param ProduceUnitID
	     * 			生产单元号
	     * @param str_date
	     * 			生产日期
	     * @param marker
	     * 			产品标识
	     * @return 返回通过生产单元号、生产日期、班次查询记录的数量
	     */
	    public int getCountByProUnitDateWorkorderMarker(int ProduceUnitID, String str_date,String workOrder,String marker, Connection con){
	    	int count = 0;
	    	try {
	    	DAO_Instruction_cache dao_Instruction = (DAO_Instruction_cache)DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con),DAO_Instruction_cache.class);
			
	 		Statement stmt = con.createStatement();
	 		log.debug("通过生产单元号、生产日期、班次、产品标识查询记录数量"+dao_Instruction.getCountByProUnitDateWorkorderMarker(ProduceUnitID, str_date,workOrder,marker));
	 		ResultSet rs  = stmt.executeQuery(dao_Instruction.getCountByProUnitDateWorkorderMarker(ProduceUnitID, str_date,workOrder,marker));
	         if(rs.next()){
	             count = rs.getInt(1);
	         }
	         if (stmt != null) {
	  			stmt.close();
	  		}
	    	} catch (SQLException e) {
				e.printStackTrace();
			}
	         return count;
	    }
	    
	    /**
	     * 通过生产单元、生产日期、产品标识查询指令对象
	     * 
	     * @param ProduceUnitID
	     * @param str_date
	     * @param marker
	     * @param con
	     * @return
	     */
	    public Instruction getInstructionByProUnitDateMarker(int ProduceUnitID,String str_date,String workOrder,String marker,Connection con){
			Instruction instruction = new Instruction();
			try{	
				DAO_Instruction_cache dao_Instruction = (DAO_Instruction_cache) DAOFactoryAdapter
					.getInstance(DataBaseType.getDataBaseType(con),DAO_Instruction_cache.class);
				Statement stmt = con.createStatement();
				log.debug("通过生产单元、生产日期、班次、产品标识查询指令对象"+dao_Instruction
						.getInstructionByProUnitDateWorkorderMarker(ProduceUnitID,str_date,workOrder,marker));
				ResultSet rs = stmt.executeQuery(dao_Instruction
						.getInstructionByProUnitDateWorkorderMarker(ProduceUnitID,str_date,workOrder,marker));
				if (rs.next()) {

					instruction.setId(rs.getInt("INT_ID"));
					instruction.setProdunitid(rs.getInt("INT_PRODUNITID"));
					instruction.setProduceDate(rs.getDate("DAT_PRODUCEDATE"));
					instruction.setVersionCode(rs.getString("STR_VERSIONCODE"));
					instruction.setInstructionOrder(rs.getInt("INT_INSTRUCTORDER"));
					instruction.setPlanDate(rs.getString("DAT_PLANDATE") == null ? null
							: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
									.getString("DAT_PLANDATE")));
					instruction.setPlanOrder(rs.getInt("INT_PLANORDER"));
					instruction.setProduceType(rs.getString("STR_PRODUCETYPE"));
					instruction.setProduceName(rs.getString("STR_PRODUCENAME"));
					instruction.setProduceMarker(rs.getString("STR_PRODUCEMARKER"));
					instruction.setWorkOrder(rs.getString("STR_WORKORDER"));
					instruction.setWorkTeam(rs.getString("STR_WORKTEAM"));
					instruction.setPlanBegin(rs.getString("TIM_PLANBEGIN") == null ? null
							: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
									.getString("TIM_PLANBEGIN")));
					instruction.setPlanFinish(rs.getString("TIM_PLANFINISH") == null ? null
							: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
									.getString("TIM_PLANFINISH")));
					instruction.setCount(rs.getInt("INT_COUNT"));
					instruction.setInstructStateID(rs.getInt("INT_INSTRUCTSTATEID"));
					instruction.setIssuance(rs.getInt("INT_ISSUANCE"));
					instruction.setStaError(rs.getInt("INT_STAERROR"));
				}
				if (stmt != null) {
					stmt.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
				return instruction;
		}
	
	
	/**
	    * 更新指令数量
	    * 徐嘉
	    * @param num,order
	    * @param con
	    *          连接对象
	    * @throws java.sql.SQLException
	    */
	   public void updateInstructionCacheNum(int num ,int order,Connection con,String str_date,int ProduceUnitID,String workOrder)	throws SQLException {
			DAO_Instruction_cache dao_Instruction_cache = (DAO_Instruction_cache)DAOFactoryAdapter.getInstance(
	               DataBaseType.getDataBaseType(con),DAO_Instruction_cache.class);
			Statement stmt = con.createStatement();
			log.debug("更新指令数量"+dao_Instruction_cache.updateInstructionCacheNum(num,order,str_date,ProduceUnitID,workOrder));
	       stmt.execute(dao_Instruction_cache.updateInstructionCacheNum(num,order,str_date,ProduceUnitID,workOrder));
	       	if (stmt != null) {
				stmt.close();
			}
	   }
	   /**
	    * 物理删除指令
	    * 徐嘉
	    * @param order
	    * @param con
	    *          连接对象
	    * @throws java.sql.SQLException
	    */
	   public void deleteInstructionCacheByOrder(int order,Connection con,String str_date,int ProduceUnitID,String workOrder)throws SQLException {
			DAO_Instruction_cache dao_Instruction_cache = (DAO_Instruction_cache)DAOFactoryAdapter.getInstance(
	               DataBaseType.getDataBaseType(con),DAO_Instruction_cache.class);
			Statement stmt = con.createStatement();
			log.debug("物理删除指令"+dao_Instruction_cache.deleteInstructionCacheByOrder(order,str_date,ProduceUnitID,workOrder));
	       stmt.execute(dao_Instruction_cache.deleteInstructionCacheByOrder(order,str_date,ProduceUnitID,workOrder));
	    	if (stmt != null) {
				stmt.close();
			}
	   }
	   /**
	    * 更新指令
	    * 徐嘉
	    * @param order
	    * @param con
	    *          连接对象
	    * @throws java.sql.SQLException
	    */
	   public void updateInstructionCacheOrder(int order,Connection con,String str_date,int ProduceUnitID,String workOrder)	throws SQLException {
			DAO_Instruction_cache dao_Instruction_cache = (DAO_Instruction_cache)DAOFactoryAdapter.getInstance(
	               DataBaseType.getDataBaseType(con),DAO_Instruction_cache.class);
			Statement stmt = con.createStatement();
			log.debug("更新指令"+dao_Instruction_cache.updateInstructionCacheOrder(order,str_date,ProduceUnitID,workOrder));
	       stmt.execute(dao_Instruction_cache.updateInstructionCacheOrder(order,str_date,ProduceUnitID,workOrder));
	      
			if (stmt != null) {
				stmt.close();
			}
	   }
	   /**
	    * 插入符合要求的指令
	    * 徐嘉
	    *
	    *  @param  order,order1,count,str
	    * @return  返回sql
	    */
	   public void insertInstructionCache(int order1,String str,int count,int order,Connection con)	throws SQLException {
			DAO_Instruction_cache dao_Instruction_cache = (DAO_Instruction_cache)DAOFactoryAdapter.getInstance(
	               DataBaseType.getDataBaseType(con),DAO_Instruction_cache.class);
			Statement stmt = con.createStatement();
			log.debug("插入符合要求的指令"+dao_Instruction_cache.insertInstructionCache(order1,str,count,order));
	       stmt.execute(dao_Instruction_cache.insertInstructionCache(order1,str,count,order));
	     
			if (stmt != null) {
				stmt.close();
			}
	   }
	   /***
        *  谢静天查询编辑空间里面主物料的值
        */
       public Instruction getInstructionbymateriel(String materiel,int int_produnitid,Connection con){
			Instruction instruction = new Instruction();
			try{	
				DAO_Instruction_cache dao_Instruction = (DAO_Instruction_cache) DAOFactoryAdapter
					.getInstance(DataBaseType.getDataBaseType(con),DAO_Instruction_cache.class);
				Statement stmt = con.createStatement();
				log.debug("通过生产单元、生产日期、班次、产品标识查询指令对象"+dao_Instruction.getInstructionbymateriel(materiel,int_produnitid));
						
				ResultSet rs = stmt.executeQuery(dao_Instruction.getInstructionbymateriel(materiel,int_produnitid));
				if (rs.next()) {

					instruction.setId(rs.getInt("INT_ID"));
					instruction.setProdunitid(rs.getInt("INT_PRODUNITID"));
					instruction.setProduceDate(rs.getDate("DAT_PRODUCEDATE"));
					instruction.setVersionCode(rs.getString("STR_VERSIONCODE"));
					instruction.setInstructionOrder(rs.getInt("INT_INSTRUCTORDER"));
					instruction.setPlanDate(rs.getString("DAT_PLANDATE") == null ? null
							: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
									.getString("DAT_PLANDATE")));
					instruction.setPlanOrder(rs.getInt("INT_PLANORDER"));
					instruction.setProduceType(rs.getString("STR_PRODUCETYPE"));
					instruction.setProduceName(rs.getString("STR_PRODUCENAME"));
					instruction.setProduceMarker(rs.getString("STR_PRODUCEMARKER"));
					instruction.setWorkOrder(rs.getString("STR_WORKORDER"));
					instruction.setWorkTeam(rs.getString("STR_WORKTEAM"));
					instruction.setPlanBegin(rs.getString("TIM_PLANBEGIN") == null ? null
							: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
									.getString("TIM_PLANBEGIN")));
					instruction.setPlanFinish(rs.getString("TIM_PLANFINISH") == null ? null
							: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs
									.getString("TIM_PLANFINISH")));
					instruction.setCount(rs.getInt("INT_COUNT"));
					instruction.setInstructStateID(rs.getInt("INT_INSTRUCTSTATEID"));
					instruction.setIssuance(rs.getInt("INT_ISSUANCE"));
					instruction.setStaError(rs.getInt("INT_STAERROR"));
				}
				if (stmt != null) {
					stmt.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
				return instruction;
    	   
       }
}
