package com.qm.mes.os.factory;
import java.sql.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.DataBaseType;
import com.qm.mes.os.bean.*;
import com.qm.mes.os.dao.*;
import com.qm.mes.system.dao.DAOFactoryAdapter;
/**
 * 
 * @author 谢静天 2009-5-13
 *
 */

public class MPSPlanFactory {
	private final Log log = LogFactory.getLog(MPSPlanFactory.class);  
   /**创建主计划 谢静天 
 * @param mpsplan
 * @param con
 * @throws SQLException
 */
	public void	saveMPSPlan(MPSPlan mpsplan,Connection con)throws SQLException{
		DAO_MPSPlan dao_mpsplan = (DAO_MPSPlan) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_MPSPlan.class);
		Statement stmt = con.createStatement();
		log.debug("创建主计划"+dao_mpsplan.saveMPSPlan(mpsplan));
		stmt.executeUpdate(dao_mpsplan.saveMPSPlan(mpsplan));
		if(stmt!=null)
		{
			stmt.close();
		}
		
   }
  
   /**删除（替换）指定计划日期的主计划
 * @param startDate
 * @param con
 * @throws SQLException
 */
	public void deleteMPSPlanbystartDate(String startDate,Connection con)throws SQLException{
	  
	   DAO_MPSPlan dao_mpsplan = (DAO_MPSPlan) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_MPSPlan.class);
		Statement stmt = con.createStatement();
		log.debug("删除（替换）指定计划日期的主计划"+dao_mpsplan.deleteMPSPlanbystartDate(startDate));
		stmt.executeUpdate(dao_mpsplan.deleteMPSPlanbystartDate(startDate));
		if(stmt!=null)
		{
			stmt.close();
		}
		
   }
 
	 /**查看某一个主计划通过主计划号 谢静天
	 * @param id
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public  MPSPlan getMPSPlanbyid(int id,Connection con)throws SQLException{
			DAO_MPSPlan dao_mpsplan = (DAO_MPSPlan) DAOFactoryAdapter.getInstance(
					DataBaseType.getDataBaseType(con), DAO_MPSPlan.class);
			Statement stmt = con.createStatement();
			log.debug("查看某一个主计划通过主计划号"+dao_mpsplan.getMPSPlanbyid(id));
			ResultSet rs=stmt.executeQuery(dao_mpsplan.getMPSPlanbyid(id));
			MPSPlan mpsplan=new MPSPlan();
			while(rs.next()){
				  mpsplan.setId(rs.getInt("int_id"));
                  mpsplan.setStartDate(rs.getDate("dat_startDate"));
                  mpsplan.setMpsUnit(rs.getString("str_mpsUnit"));
                  mpsplan.setMaterielName(rs.getString("str_materielName"));
                  mpsplan.setPlanAmount(rs.getInt("int_planAmount"));
                  mpsplan.setIntendStorage(rs.getInt("int_intendStorage"));
                  mpsplan.setPlanType(rs.getString("str_planType"));
                  mpsplan.setVersion(rs.getString("str_version"));
                  mpsplan.setUserName(rs.getString("str_user"));
                  mpsplan.setContractCode(rs.getString("str_contractcode"));
			}
			if(stmt!=null)
			{
				stmt.close();
			}
			
		return mpsplan;
	 }
	 
	 /**更新主计划 通过id值 谢静天
	 * @param mpsplan
	 * @param con
	 * @throws SQLException
	 */
	public   void updateMPSPlanbyid(MPSPlan mpsplan ,Connection con)throws SQLException{
			DAO_MPSPlan dao_mpsplan = (DAO_MPSPlan) DAOFactoryAdapter.getInstance(
					DataBaseType.getDataBaseType(con), DAO_MPSPlan.class);
			Statement stmt = con.createStatement();
			log.debug("更新主计划 通过id值"+dao_mpsplan.updateMPSPlanbyid(mpsplan));
			stmt.executeUpdate(dao_mpsplan.updateMPSPlanbyid(mpsplan));
			if(stmt!=null)
			{
				stmt.close();
			}
			
			
	 }
	
		/**删除主计划 通过id值 谢静天
		 * @param int_id
		 * @param con
		 * @throws SQLException
		 */
		public  void  deleteMPSPlanbyid(int int_id,Connection con)throws SQLException{
			DAO_MPSPlan dao_mpsplan = (DAO_MPSPlan) DAOFactoryAdapter.getInstance(
					DataBaseType.getDataBaseType(con), DAO_MPSPlan.class);
			Statement stmt = con.createStatement();
			log.debug("删除主计划 通过id值"+dao_mpsplan.deleteMPSPlanbyid(int_id));
			stmt.executeUpdate(dao_mpsplan.deleteMPSPlanbyid(int_id));
			if(stmt!=null)
			{
				stmt.close();
			}
			
		}
}
