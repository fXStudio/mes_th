package com.qm.mes.ra.factory;
import java.sql.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.DataBaseType;
import com.qm.mes.ra.dao.DAO_State;
import com.qm.mes.system.dao.DAOFactoryAdapter;
/**
 * author : 谢静天
 */
public class GatherStateRuleFactory {
	private final Log log = LogFactory.getLog(GatherStateRuleFactory.class);
	/**
	 * 创建采集点的规则
	 * 谢静天
	 * @param int_gatherid  采集点的id
	 * @param Stateconversionid
	 * @param defaultgo
	 * @param con
	 * @throws SQLException
	 */
	public void saveGatherStateRule(int int_gatherid,int Stateconversionid,int defaultgo,Connection con)throws SQLException {
		DAO_State dao_State = (DAO_State) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_State.class);
		Statement stmt = con.createStatement();
		log.debug("创建采集点状态规则"+dao_State.saveGatherStateRule(int_gatherid, Stateconversionid, defaultgo));
		stmt.execute(dao_State.saveGatherStateRule(int_gatherid, Stateconversionid, defaultgo));

		if (stmt != null) {
			stmt.close();
		}
	}
	/**
	 * 先删除采集点的规则
	 * 在采集点修改页面当用户要修改采集点规则时应先删除以前的规则在进行创建新的规则。
	 * @param int_gatherid 采集点的id
	 * @param con
	 * @throws SQLException
	 */
	public void delgatherstaterule(int int_gatherid ,Connection con) throws SQLException {
		DAO_State dao_State = (DAO_State) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_State.class);
		Statement stmt = con.createStatement();
		log.debug("先删除采集点的规则"+dao_State.delgatherstaterule(int_gatherid));
		stmt.executeUpdate(dao_State.delgatherstaterule(int_gatherid));
		if(stmt!=null){
			stmt.close();
		}
		
	}

}
