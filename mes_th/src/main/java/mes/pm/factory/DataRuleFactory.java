package mes.pm.factory;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.List;

import mes.framework.DataBaseType;
import mes.pm.bean.DataRule;
import mes.pm.bean.DataRuleArg;
import mes.pm.dao.DAO_DataRule;
import mes.system.dao.DAOFactoryAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 数据规则工厂类
 * @author Xujia
 *
 */
public class DataRuleFactory {
	//创建日志文件
	private final Log log = LogFactory.getLog(DataRuleFactory.class);
	/**  徐嘉
	 * 创建DataRule
	 * @param DataRule
	 * @param con
	 * @throws SQLException
	 */
	public void createDataRule(DataRule dataRule,Connection con) throws SQLException{
		DAO_DataRule dao_DataRule = (DAO_DataRule) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_DataRule.class);
		Statement stmt = con.createStatement();
		log.debug("创建datarule "+dao_DataRule.saveDataRule(dataRule));
		stmt.execute(dao_DataRule.saveDataRule(dataRule));		
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
	}
	/**  徐嘉
	 * 创建DataRuleArgs
	 * @param DataRuleArgs
	 * @param con
	 * @throws SQLException
	 */
	public void createDataRuleArgs(DataRuleArg dataRuleArg,Connection con) throws SQLException{
		DAO_DataRule dao_DataRule = (DAO_DataRule) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_DataRule.class);
		Statement stmt = con.createStatement();
		log.debug("创建dataruleargs "+dao_DataRule.saveDataRuleArg(dataRuleArg));		
		stmt.execute(dao_DataRule.saveDataRuleArg(dataRuleArg));		
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
	}
	/**  徐嘉
	 * 查询全部数据规则信息
	 * @param con
	 * @return
	 * @throws SQLException 
	 */
	public List<DataRule> getAllDataRule(Connection con) throws SQLException{
		List<DataRule> list = new ArrayList<DataRule>();
		DAO_DataRule dao_DataRule = (DAO_DataRule) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_DataRule.class);
		Statement stmt = con.createStatement();
		log.debug("查询数据规则信息 "+dao_DataRule.getAllDataRule());
		ResultSet rs = stmt.executeQuery(dao_DataRule.getAllDataRule());
		while(rs.next()){
			DataRule r = new DataRule();			
		     r.setId(rs.getInt("int_id"));
		     r.setName(rs.getString("str_name"));
		     r.setCode(rs.getString("str_code"));
		     r.setRule(rs.getString("string_rule"));
		     r.setDescription(rs.getString("str_description"));	
			list.add(r);
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
	
	
	
	/** 徐嘉
	 * 通过序号删除DataRule
	 * @param id
	 * @param con
	 * @throws SQLException 
	 */
	public void delDataRuleById(int id, Connection con) throws SQLException {
		DAO_DataRule dao_DataRule = (DAO_DataRule) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_DataRule.class);
		Statement stmt = con.createStatement();
		log.debug("通过序号删除数据规则 "+dao_DataRule.delDataRuleById(id));	
		stmt.execute(dao_DataRule.delDataRuleById(id));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}

	/** 徐嘉
	 * 通过序号删除DataRule参数
	 * @param id
	 * @param con
	 * @throws SQLException 
	 */
	public void delDataRuleArgsById(int id, Connection con) throws SQLException {
		DAO_DataRule dao_DataRule = (DAO_DataRule) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_DataRule.class);
		Statement stmt = con.createStatement();
		log.debug("通过序号删除数据规则 "+dao_DataRule.delDataRuleArgsById(id));	
		stmt.execute(dao_DataRule.delDataRuleArgsById(id));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}
	
	 /**  徐嘉
	 * 更新DataRule对象
	 *
	 * @param DataRule
     * @param con
     *          数据库连接对象
	 * @throws SQLException
     *                  SQL异常
	 */
	public void updateDataRule(DataRule dataRule, Connection con)
			throws SQLException {
		DAO_DataRule dao_DataRule = (DAO_DataRule) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_DataRule.class);
		Statement stmt = con.createStatement();
		log.debug("更新DataRule对象 "+dao_DataRule.updateDataRule(dataRule));
		stmt.execute(dao_DataRule.updateDataRule(dataRule));       
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}
	/**
	 * 通过数据规则名查询DataRule
	 * @param id
	 * @param con
	 * @return
	 * @throws SQLException 
	 */
	public DataRule getDataRuleByName(String str_name,Connection con) throws SQLException{
		DataRule d = null;
		DAO_DataRule dao_DataRule = (DAO_DataRule) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_DataRule.class);
		Statement stmt = con.createStatement();
		log.debug("通过数据规则名获取数据规则SQL语句："+dao_DataRule.getDataRuleByName(str_name));
		ResultSet rs = stmt.executeQuery(dao_DataRule.getDataRuleByName(str_name));
		log.debug("通过数据规则名获取数据规则列表---");
		while(rs.next()){
			d = new DataRule();
			d.setId(rs.getInt("int_id"));
			d.setName(rs.getString("str_name"));
			d.setCode(rs.getString("str_code"));
			d.setRule(rs.getString("string_rule"));
			d.setDescription(rs.getString("str_description"));
			log.debug("数据规则号："+rs.getInt("int_id")+"；数据规则名"+rs.getString("str_name")+"；数据规则编码："
					+rs.getString("str_code")+"；公式："+rs.getString("string_rule")+"；描述："
					+rs.getString("str_description"));
		}
		if(rs!=null){
			rs.close();
			rs=null;
		}
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
		return d;
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
	public DataRule getDataRuleById(int id, Connection con)
			throws SQLException, ParseException {
		DataRule dr = new DataRule();
		DAO_DataRule dao_DataRule = (DAO_DataRule) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_DataRule.class);
		Statement stmt = con.createStatement();
		log.debug("通过ID查询装配指示单SQL：" + dao_DataRule.getDataRuleById(id));
		ResultSet rs = stmt
				.executeQuery(dao_DataRule.getDataRuleById(id));
		if (rs.next()) {
			dr.setId(rs.getInt("int_id"));
			dr.setName(rs.getString("str_name"));
			dr.setCode(rs.getString("str_code"));
			dr.setDescription(rs.getString("str_description"));
			dr.setRule(rs.getString("string_rule"));
		}
		if (stmt != null)
			stmt.close();
		return dr;
	}
	
	/**
	 * 通过规则号查询规则参数
	 * 
	 * @param id 规则单号
	 * @param con 连接对象
	 * @return 规则参数列表
	 * @throws SQLException SQL异常
	 */
	public List<DataRuleArg> getDataRuleArgByDataRuleId(int id,Connection con)throws SQLException{
		List<DataRuleArg> list_DataRuleArg = new ArrayList<DataRuleArg>();
		DAO_DataRule dao_DataRule = (DAO_DataRule) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_DataRule.class);
		Statement stmt = con.createStatement();
		log.debug("通过规则号查询规则参数列表SQL：" + dao_DataRule.getDataRuleArgsById(id));
		ResultSet rs = stmt.executeQuery(dao_DataRule.getDataRuleArgsById(id));
		while (rs.next()) {
			DataRuleArg dataRuleArg = new DataRuleArg();
			dataRuleArg.setId(rs.getInt("int_id"));
			dataRuleArg.setName(rs.getString("str_argname"));
			dataRuleArg.setDescription(rs.getString("str_description"));
			dataRuleArg.setInt_dataruleid(rs.getInt("int_dataruleid"));			
			list_DataRuleArg.add(dataRuleArg);
		}
		if (stmt != null)
			stmt.close();
		return list_DataRuleArg;
	}

}
