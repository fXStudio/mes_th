package mes.ra.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mes.framework.DataBaseType;
import mes.system.dao.DAOFactoryAdapter;
import mes.ra.bean.State;
import mes.ra.dao.DAO_State;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StateFactory {
	private final Log log = LogFactory.getLog(StateFactory.class);
	/**  徐嘉
	 * 查询全部状态信息
	 * @param con
	 * @return
	 * @throws SQLException 
	 */
	public List<State> getAllState(Connection con) throws SQLException{
		List<State> list = new ArrayList<State>();
		DAO_State dao_State = (DAO_State) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_State.class);
		Statement stmt = con.createStatement();
		log.debug("查询全部状态信息"+dao_State.getAllState());
		ResultSet rs = stmt.executeQuery(dao_State.getAllState());
		while(rs.next()){
			State s = new State();
			s.setId(rs.getInt("int_id"));
			s.setStateName(rs.getString("str_statename"));
			s.setStyle(rs.getString("str_style"));
			s.setDelete(rs.getInt("int_delete"));
			list.add(s);
		}
		if(stmt!=null){
			stmt.close();
		}
		return list;
	}
	
	/** 徐嘉
	 * 通过序号删除State
	 * @param id
	 * @param con
	 * @throws SQLException 
	 */
	public void delStateById(int id, Connection con) throws SQLException {
		DAO_State dao_State = (DAO_State) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_State.class);
		Statement stmt = con.createStatement();
		log.debug("通过序号删除State"+dao_State.delStateById(id));	
		stmt.execute(dao_State.delStateById(id));
		if (stmt != null) {
			stmt.close();
		}
	}
	
	/**  徐嘉
	 * 创建state
	 * @param state
	 * @param con
	 * @throws SQLException
	 */
	public void createState(State state,Connection con) throws SQLException{
		DAO_State dao_State = (DAO_State) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_State.class);
		Statement stmt = con.createStatement();
		log.debug("创建state"+dao_State.saveState(state));
		stmt.execute(dao_State.saveState(state));		
		if(stmt!=null){
			stmt.close();
		}
	}
	
	 /**  徐嘉
	 * 更新State对象
	 *
	 * @param state
     * @param con
     *          数据库连接对象
	 * @throws SQLException
     *                  SQL异常
	 */
	public void updateState(State state, Connection con)
			throws SQLException {
		DAO_State dao_State = (DAO_State) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_State.class);
		Statement stmt = con.createStatement();
		log.debug("更新State对象"+dao_State.updateState(state));
		stmt.execute(dao_State.updateState(state));
       
		if (stmt != null) {
			stmt.close();
		}
	}
	
	/**  徐嘉
	 * 检测状态是否已经创建
	 * @param gatherid
	 
	 * @throws SQLException
	 */
	public int checkState1(int id, 
			Connection con) throws SQLException {
		DAO_State dao_State = (DAO_State) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_State.class);
		Statement stmt = con.createStatement();
		log.debug("检测状态是否已经创建"+dao_State.checkStateById1(id));
		int count = 0;
		ResultSet rs = stmt.executeQuery(dao_State
				.checkStateById1(id));
		if (rs.next()) {
			count = rs.getInt(1);
		}
		if (stmt != null) {
			stmt.close();
		}
		return count;
	}
	/**  徐嘉
	 * 检测状态是否已经创建
	 * @param gatherid
	 
	 * @throws SQLException
	 */
	public int checkState2(int id, 
			Connection con) throws SQLException {
		DAO_State dao_State = (DAO_State) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_State.class);
		Statement stmt = con.createStatement();
		log.debug("检测状态是否已经创建"+dao_State.checkStateById2(id));
		int count = 0;
		ResultSet rs = stmt.executeQuery(dao_State
				.checkStateById2(id));
		if (rs.next()) {
			count = rs.getInt(1);
		}
		if (stmt != null) {
			stmt.close();
		}
		return count;
	}
	
	/**  徐嘉
	 * 验证状态名称为name的状态是否已经创建
	 * @param name
	 
	 * @throws SQLException
	 */
	public int checkState(String name, 
			Connection con) throws SQLException {
		DAO_State dao_State = (DAO_State) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_State.class);
		Statement stmt = con.createStatement();
		int count = 0;
		log.debug("验证状态名称为name的状态是否已经创建"+dao_State	.checkStateByName(name));
		ResultSet rs = stmt.executeQuery(dao_State
				.checkStateByName(name));
		if (rs.next()) {
			count = rs.getInt(1);
		}
		if (stmt != null) {
			stmt.close();
		}
		return count;
	}


	/**
	 * 通过采集点的id查看默认状态装换关系号
	 * 谢静天
	 * @param int_gatherid
	 * @param con
	 * @return
	 * @throws SQLException
	 */
    public int getconversionidBy(int int_gatherid,Connection con) throws SQLException{
    	DAO_State dao_State = (DAO_State) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_State.class);
    	int n=0;
    	Statement stmt = con.createStatement();
    	log.debug("通过采集点的id查看默认状态装换关系号"+dao_State.getconversionidBy(int_gatherid));
    	ResultSet rs = stmt.executeQuery(dao_State.getconversionidBy(int_gatherid));
    	if(rs.next())
    		n=rs.getInt(1);
		if(stmt!=null){
			stmt.close();
		}
    	return n;
    		}
    
	/**
	 * 通过状态id
	 * 查看状态名
	 * 谢静天
	 * @param id
	 * @param con
	 * @return
	 * @throws SQLException
	 */
    public String getStateById(int id,Connection con) throws SQLException{
    	DAO_State dao_State = (DAO_State) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_State.class);	
    	Statement stmt = con.createStatement();
    	log.debug("通过状态id查看状态名"+dao_State.getStateById(id));
    	ResultSet rs=stmt.executeQuery(dao_State.getStateById(id));
    	String name=null;
    	if(rs.next())
    		 name=rs.getString("str_statename");
		if(stmt!=null){
			stmt.close();
		}
    	return name;
    }
	
    
    /**
     * 通过状态ID号查询状态对象
     * 
     * @param id
     * 		状态序号
     * @param con
     * 		连接对象
     * @return 状态对象
     */
    public State getStateById2(int id ,Connection con){
    	State state = new State();
    	try{
	    	DAO_State dao_State = (DAO_State) DAOFactoryAdapter
			.getInstance(DataBaseType.getDataBaseType(con),
					DAO_State.class);	
	    	Statement stmt = con.createStatement();
	    	log.debug("通过状态ID号查询状态对象"+dao_State.getStateById(id));
	    	ResultSet rs=stmt.executeQuery(dao_State.getStateById(id));
	    	if(rs.next()){
	    		state.setId(rs.getInt("int_id"));
	    		state.setStateName(rs.getString("str_statename"));
	    		state.setStyle(rs.getString("str_style"));
	    		state.setDelete(rs.getInt("int_delete"));
	    	}
			if(stmt!=null){
				stmt.close();
			}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return state;
    }
    }
