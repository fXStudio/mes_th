package mes.pm.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mes.framework.DataBaseType;
import mes.pm.bean.DataRule;
import mes.pm.bean.ExceptionCause;
import mes.pm.bean.ExceptionType;
import mes.pm.dao.DAO_Exception;
import mes.system.dao.DAOFactoryAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 异常工厂类
 * @author Xujia
 *
 */
public class ExceptionFactory {
	//创建日志文件
	private final Log log = LogFactory.getLog(ExceptionFactory.class);
	/**  徐嘉
	 * 创建ExceptionType
	 * @param ExceptionType
	 * @param con
	 * @throws SQLException
	 */
	public void createExceptionType(ExceptionType exceptionType,Connection con) throws SQLException{
		DAO_Exception dao_Exception = (DAO_Exception) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_Exception.class);
		Statement stmt = con.createStatement();
		log.debug("创建ExceptionType "+dao_Exception.saveExceptionType(exceptionType));
		stmt.execute(dao_Exception.saveExceptionType(exceptionType));		
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
	}
	/**  徐嘉
	 * 创建ExceptionCause
	 * @param ExceptionCause
	 * @param con
	 * @throws SQLException
	 */
	public void createExceptionCause(ExceptionCause exceptionCause,Connection con) throws SQLException{
		DAO_Exception dao_Exception = (DAO_Exception) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_Exception.class);
		Statement stmt = con.createStatement();
		log.debug("创建ExceptionCause "+dao_Exception.saveExceptionCause(exceptionCause));		
		stmt.execute(dao_Exception.saveExceptionCause(exceptionCause));		
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
	}
	/**  徐嘉
	 * 查询全部ExceptionType
	 * @param con
	 * @return
	 * @throws SQLException 
	 */
	public List<ExceptionType> getAllExceptionType(Connection con) throws SQLException{
		List<ExceptionType> list = new ArrayList<ExceptionType>();
		DAO_Exception dao_Exception = (DAO_Exception) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_Exception.class);
		Statement stmt = con.createStatement();
		log.debug("查询全部ExceptionType "+dao_Exception.getAllExceptionType());
		ResultSet rs = stmt.executeQuery(dao_Exception.getAllExceptionType());
		while(rs.next()){ 
			ExceptionType et = new ExceptionType();			
			et.setId(rs.getInt("int_id"));
			et.setName(rs.getString("str_name"));
			et.setState(rs.getInt("int_state"));
			et.setSysdefault(rs.getInt("int_sysdefault"));			
			list.add(et);
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
	/**  徐嘉
	 * 查询全部ExceptionCause
	 * @param con
	 * @return
	 * @throws SQLException 
	 */
	public List<ExceptionCause> getAllExceptionCause(Connection con) throws SQLException{
		List<ExceptionCause> list = new ArrayList<ExceptionCause>();
		DAO_Exception dao_Exception = (DAO_Exception) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_Exception.class);
		Statement stmt = con.createStatement();
		log.debug("查询全部ExceptionCause "+dao_Exception.getAllExceptionCause());
		ResultSet rs = stmt.executeQuery(dao_Exception.getAllExceptionCause());
		while(rs.next()){ 
			ExceptionCause ec = new ExceptionCause();			
			ec.setId(rs.getInt("int_id"));
			ec.setName(rs.getString("str_name"));
			ec.setState(rs.getInt("int_state"));					
			list.add(ec);
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
	/**  徐嘉
	 * 查询全部ExceptionType by id
	 * @param con
	 * @return
	 * @throws SQLException 
	 */
	public ExceptionType getExceptionTypebyid(int id,Connection con) throws SQLException{
		ExceptionType et = null;	
		DAO_Exception dao_Exception = (DAO_Exception) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_Exception.class);
		Statement stmt = con.createStatement();
		log.debug("查询全部ExceptionType by id "+dao_Exception.getExceptionTypeById(id));
		ResultSet rs = stmt.executeQuery(dao_Exception.getExceptionTypeById(id));
		while(rs.next()){ 
			et = new ExceptionType();			
			et.setId(rs.getInt("int_id"));
			et.setName(rs.getString("str_name"));
			et.setState(rs.getInt("int_state"));
			et.setSysdefault(rs.getInt("int_sysdefault"));			
			
		}
		if(rs!=null){
			rs.close();
			rs=null;
		}
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
		return et;
	}
	
	/**  徐嘉
	 * 查询全部ExceptionCause by id
	 * @param con
	 * @return
	 * @throws SQLException 
	 */
	public ExceptionCause getExceptionCausebyid(int id,Connection con) throws SQLException{
		ExceptionCause ec=null;
		DAO_Exception dao_Exception = (DAO_Exception) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_Exception.class);
		Statement stmt = con.createStatement();
		log.debug("查询全部ExceptionCause by id "+dao_Exception.getExceptionCauseById(id));
		ResultSet rs = stmt.executeQuery(dao_Exception.getExceptionCauseById(id));
		while(rs.next()){ 
			ec = new ExceptionCause();			
			ec.setId(rs.getInt("int_id"));
			ec.setName(rs.getString("str_name"));
			ec.setState(rs.getInt("int_state"));					
			
		}
		if(rs!=null){
			rs.close();
			rs=null;
		}
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
		return ec;
	}
	
	/** 徐嘉
	 * 通过序号删除ExceptionType
	 * @param id
	 * @param con
	 * @throws SQLException 
	 */
	public void delExceptionTypeById(int id, Connection con) throws SQLException {
		DAO_Exception dao_Exception = (DAO_Exception) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_Exception.class);
		Statement stmt = con.createStatement();
		log.debug("通过序号删除ExceptionType "+dao_Exception.delExceptionTypeById(id));	
		stmt.execute(dao_Exception.delExceptionTypeById(id));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}
	/** 徐嘉
	 * 通过序号删除ExceptionCause
	 * @param id
	 * @param con
	 * @throws SQLException 
	 */
	public void delExceptionCauseById(int id, Connection con) throws SQLException {
		DAO_Exception dao_Exception = (DAO_Exception) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_Exception.class);
		Statement stmt = con.createStatement();
		log.debug("通过序号删除ExceptionCause "+dao_Exception.delExceptionCauseById(id));	
		stmt.execute(dao_Exception.delExceptionCauseById(id));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}
	 /**  徐嘉
	 * 更新ExceptionType对象
	 *
	 * @param ExceptionType
     * @param con
     *          数据库连接对象
	 * @throws SQLException
     *                  SQL异常
	 */
	public void updateExceptionType(ExceptionType exceptionType, Connection con)
			throws SQLException {
		DAO_Exception dao_Exception = (DAO_Exception) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_Exception.class);
		Statement stmt = con.createStatement();
		log.debug("更新ExceptionType对象 "+dao_Exception.updateExceptionType(exceptionType));
		stmt.execute(dao_Exception.updateExceptionType(exceptionType));       
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}
	
	 /**  徐嘉
	 * 更新ExceptionCause对象
	 *
	 * @param ExceptionCause
     * @param con
     *          数据库连接对象
	 * @throws SQLException
     *                  SQL异常
	 */
	public void updateExceptionCause(ExceptionCause exceptionCause, Connection con)
			throws SQLException {
		DAO_Exception dao_Exception = (DAO_Exception) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_Exception.class);
		Statement stmt = con.createStatement();
		log.debug("更新ExceptionCause对象 "+dao_Exception.updateExceptionCause(exceptionCause));
		stmt.execute(dao_Exception.updateExceptionCause(exceptionCause));       
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}
	
	/**
	 * 通过名查询ExceptionType
	 * @param id
	 * @param con
	 * @return
	 * @throws SQLException 
	 */
	public DataRule getExceptionTypeByName(String str_name,Connection con) throws SQLException{
		DataRule d = null;
		DAO_Exception dao_Exception = (DAO_Exception) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_Exception.class);
		Statement stmt = con.createStatement();
		log.debug("通过异常名获取ExceptionTypeSQL语句："+dao_Exception.getExceptionTypeByName(str_name));
		ResultSet rs = stmt.executeQuery(dao_Exception.getExceptionTypeByName(str_name));
		log.debug("通过异常名获取ExceptionType列表---");
		while(rs.next()){
			ExceptionType et = new ExceptionType();			
			et.setId(rs.getInt("int_id"));
			et.setName(rs.getString("str_name"));
			et.setState(rs.getInt("int_state"));
			et.setSysdefault(rs.getInt("int_sysdefault"));	
			
			log.debug("ExceptionType号："+rs.getInt("int_id")+"；ExceptionType："
					+rs.getString("str_name")+"；int_state："+rs.getInt("int_state")+"；int_sysdefault："
					+rs.getInt("int_sysdefault"));
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

}
