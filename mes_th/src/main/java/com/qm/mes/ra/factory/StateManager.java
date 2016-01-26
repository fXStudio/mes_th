package com.qm.mes.ra.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.DataBaseType;
import com.qm.mes.ra.bean.ConversionState;
import com.qm.mes.ra.bean.State;
import com.qm.mes.ra.dao.DAO_ConversionState;
import com.qm.mes.ra.dao.DAO_State;
import com.qm.mes.system.dao.DAOFactoryAdapter;


public class StateManager {
	private final Log log = LogFactory.getLog(StateManager.class);
	/**
	 * 查询全部状态信息
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public List<State> getAllState(Connection con) throws SQLException {
		List<State> list = new ArrayList<State>();
		DAO_State dao_State = (DAO_State) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_State.class);
		Statement stmt = con.createStatement();
		log.debug("查询全部状态信息"+dao_State.getAllState());
		ResultSet rs = stmt.executeQuery(dao_State.getAllState());
		while (rs.next()) {
			State s = new State();
			s.setId(rs.getInt("int_id"));
			s.setStateName(rs.getString("str_statename"));
			s.setStyle(rs.getString("str_style"));
			s.setDelete(rs.getInt("int_delete"));
			list.add(s);
		}
		if (stmt != null) {
			stmt.close();
		} 
		
		return list;
	}
	/**
	 * 查询全部状态转换信息
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public List<State> getAllConversionState(Connection con) throws SQLException {
		List<State> list = new ArrayList<State>();
		DAO_ConversionState  dao_State = (DAO_ConversionState) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_State.class);
		Statement stmt = con.createStatement();
		log.debug("查询全部状态转换信息"+ dao_State.getAllConversionState());
		ResultSet rs = stmt.executeQuery( dao_State.getAllConversionState());
		while (rs.next()) {
			State s = new State();
			s.setId(rs.getInt("int_id"));
			s.setStateName(rs.getString("str_statename"));
			s.setStyle(rs.getString("str_style"));
			s.setDelete(rs.getInt("int_delete"));
			list.add(s);
		}
		if (stmt != null) {
			stmt.close();
		} 
		
		return list;
	}
	/**
	 * 通过序号查询State
	 * @param id
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public State getStateById(int id, Connection con) throws SQLException {
		State s = null;
		DAO_State dao_State = (DAO_State) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_State.class);
		Statement stmt = con.createStatement();
		log.debug("通过序号查询State"+ dao_State.getStateById(id));
		ResultSet rs = stmt.executeQuery(dao_State.getStateById(id));
		while (rs.next()) {
			s = new State();
			s.setId(rs.getInt("int_id"));
			s.setStateName(rs.getString("str_statename"));
			s.setStyle(rs.getString("str_style"));
			s.setDelete(rs.getInt("int_delete"));	
			s.setStyledesc(rs.getString("str_styledesc"));

			}
		if (stmt != null) {
			stmt.close();
		}
		return s;
	}
	
	/**
	 * 通过状态名称返回状态信息
	 * @param id
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public State getStateByName(String name, Connection con)
			throws SQLException {
		State s = null;
		DAO_State dao_State = (DAO_State) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_State.class);
		Statement stmt = con.createStatement();
		log.debug("通过状态名称返回状态信息"+ dao_State.getStateByName(name));
		ResultSet rs = stmt.executeQuery(dao_State.getStateByName(name));
		while (rs.next()) {
			s = new State();
			s.setId(rs.getInt("int_id"));
			s.setStateName(rs.getString("str_statename"));
			s.setStyle(rs.getString("str_style"));
			s.setDelete(rs.getInt("int_delete"));
			s.setStyledesc(rs.getString("str_styledesc"));
			if (stmt != null) {
				stmt.close();
			}
		}
		return s;
	}

	/**
	 * 通过序号删除State
	 * @param id
	 * @param con
	 * @throws SQLException
	 */
	public void delStateById(int id, Connection con) throws SQLException {
		DAO_State dao_State = (DAO_State) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_State.class);
		Statement stmt = con.createStatement();
		log.debug("通过序号删除State"+ dao_State.delStateById(id));
		stmt.execute(dao_State.delStateById(id));
		if (stmt != null) {
			stmt.close();
		}
	}
	
	/**
	 * 通过序号删除ConversionState
	 * @param id
	 * @param con
	 * @throws SQLException
	 */
	public void delConversionStateById(int id, Connection con) throws SQLException {
		DAO_ConversionState dao_ConversioinState = (DAO_ConversionState) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_ConversionState.class);
		Statement stmt = con.createStatement();
		log.debug("通过序号删除ConversionState"+ dao_ConversioinState.delConversionStateById(id));
		stmt.execute(dao_ConversioinState.delConversionStateById(id));
		
		if (stmt != null) {
			stmt.close();
		}
	}

	/**
	 * 创建state
	 * @param state
	 * @param con
	 * @throws SQLException
	 */
	public void createState(State state, Connection con) throws SQLException {
		DAO_State dao_State = (DAO_State) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_State.class);
		Statement stmt = con.createStatement();
		log.debug("创建state"+ dao_State.saveState(state));
		stmt.execute(dao_State.saveState(state));
		if (stmt != null) {
			stmt.close();
		}
	}
	/**
	 * 创建conversionstate
	 * @param conversionstate
	 * @param con
	 * @throws SQLException
	 */
	public void createConversionState(ConversionState ConversionState, Connection con) throws SQLException {
		

		DAO_ConversionState dao_ConversionState = (DAO_ConversionState) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_ConversionState.class);
		Statement stmt = con.createStatement();
		log.debug("创建conversionstate"+ dao_ConversionState.saveConversionState(ConversionState));
		stmt.execute(dao_ConversionState.saveConversionState(ConversionState));
		
		if (stmt != null) {
			stmt.close();
		}
	}
	 /**
	 * 更新State对象
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
		log.debug("更新State对象"+ dao_State.updateState(state));
		stmt.execute(dao_State.updateState(state));
     
		if (stmt != null) {
			stmt.close();
		}
	}
	/**
	 * 更新ConversionState对象
	 *
	 * @param conversionstate
     * @param con
     *          数据库连接对象
	 * @throws SQLException
     *                  SQL异常
	 */
	public void updateConversionState(ConversionState ConversionState, Connection con)
			throws SQLException {
		DAO_ConversionState dao_Conversionstate = (DAO_ConversionState) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_ConversionState.class);
		Statement stmt = con.createStatement();
		log.debug("更新ConversionState对象"+ dao_Conversionstate.updateConversionState(ConversionState));
		stmt.execute(dao_Conversionstate.updateConversionState(ConversionState));
      
		if (stmt != null) {
			stmt.close();
		}
	}

}
