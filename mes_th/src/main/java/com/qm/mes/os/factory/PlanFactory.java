package com.qm.mes.os.factory;

import java.util.*;
import java.sql.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.DataBaseType;
import com.qm.mes.os.bean.Plan;
import com.qm.mes.os.dao.DAO_Plan;
import com.qm.mes.system.dao.DAOFactoryAdapter;
/**
 * 
 * @author 谢静天2009-5-15
 *  计划工厂
 */


public class PlanFactory {
	private final Log log = LogFactory.getLog(PlanFactory.class);
	
	 /**创建作业计划  谢静天
	 * @param plan
	 * @param con
	 * @throws SQLException
	 */
	public  void savePlan(Plan plan,Connection con)throws SQLException{
		 DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
					DataBaseType.getDataBaseType(con), DAO_Plan.class);
			Statement stmt = con.createStatement();
			//log.debug("创建作业计划"+dao_plan.savePlan(plan));
			stmt.executeUpdate(dao_plan.savePlan(plan));
			if(stmt!=null)
			{
				stmt.close();
			}
			
	 }

	/**按照生产单元，生产日期，和班次查找作业计划 谢静天  在作业计划导入时调用获取版本号
	* @param produceDate
	* @param produnitid
	* @param workOrder
	* @param con
	* @return
	* @throws SQLException
	*/
	public List<Plan> getPlanbyProdateProidWorder(String produceDate,int produnitid,String workOrder,Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
						DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		log.debug("按照生产单元，生产日期，和班次查找作业计划"+dao_plan.getPlanbyProdateProidWorder(produceDate, produnitid,workOrder));
		ResultSet rs=stmt.executeQuery(dao_plan.getPlanbyProdateProidWorder(produceDate, produnitid,workOrder));
		List<Plan> planlist=new ArrayList<Plan>();
		while(rs.next()){
			Plan plan =new Plan();
			     plan.setId(rs.getInt("int_id"));
	             plan.setPlanDate(rs.getDate("dat_planDate"));
	             plan.setProduceDate(rs.getDate("dat_produceDate"));
	             plan.setOrderFormId(rs.getString("str_orderFormId"));
	             plan.setPlanGroupId(rs.getInt("int_planGroupId"));
	             plan.setProduceType(rs.getString("str_produceType"));
	             plan.setProduceName(rs.getString("str_produceName"));
	             plan.setProduceMarker(rs.getString("str_produceMarker"));
	             plan.setProdunitid(rs.getInt("int_produnitid"));
	             plan.setWorkTeam(rs.getString("str_workTeam"));
	             plan.setWorkOrder(rs.getString("str_workOrder"));
	             plan.setAmount(rs.getInt("int_count"));
	             plan.setVersioncode(rs.getString("str_versioncode"));
	             plan.setUpload(rs.getInt("int_upload"));
	             plan.setPlanOrder(rs.getInt("int_planOrder"));
	             plan.setDescription(rs.getString("str_description"));
	             planlist.add(plan);
		}
		if(stmt!=null)
		{
			stmt.close();
		}
		
		return planlist;
	}
		 
	/**通过版本号得到作业计划  谢静天
	* @param versioncode
	* @param con
	* @return
	* @throws SQLException
	*/
	public List<Plan> getPlanbyversioncord(String versioncode,Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
						DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		log.debug("通过版本号得到作业计划"+dao_plan.getPlanbyversioncord(versioncode));
		ResultSet rs=stmt.executeQuery(dao_plan.getPlanbyversioncord(versioncode));
		List<Plan> planlist=new ArrayList<Plan>();
		while(rs.next()){
			Plan plan =new Plan();
			     plan.setId(rs.getInt("int_id"));
	             plan.setPlanDate(rs.getDate("dat_planDate"));
	             plan.setProduceDate(rs.getDate("dat_produceDate"));
	             plan.setOrderFormId(rs.getString("str_orderFormId"));
	             plan.setPlanGroupId(rs.getInt("int_planGroupId"));
	             plan.setProduceType(rs.getString("str_produceType"));
	             plan.setProduceName(rs.getString("str_produceName"));
	             plan.setProduceMarker(rs.getString("str_produceMarker"));
	             plan.setProdunitid(rs.getInt("int_produnitid"));
	             plan.setWorkTeam(rs.getString("str_workTeam"));
	             plan.setWorkOrder(rs.getString("str_workOrder"));
	             plan.setAmount(rs.getInt("int_count"));
	             plan.setVersioncode(rs.getString("str_versioncode"));
	             plan.setUpload(rs.getInt("int_upload"));
	             plan.setPlanOrder(rs.getInt("int_planOrder"));
	             plan.setDescription(rs.getString("str_description"));
	             planlist.add(plan);
		}
		if(stmt!=null)
		{
			stmt.close();
		}
		
		return planlist;
	}
		
	/**删除指定版本号的作业计划 谢静天
	* @param versioncode
	* @param con
	* @throws SQLException
	*/
	public  void deletePlanbyversioncode(String versioncode,Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
						DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		log.debug("删除指定版本号的作业计划"+dao_plan.deletePlanbyversioncode(versioncode));
		stmt.executeUpdate(dao_plan.deletePlanbyversioncode(versioncode));
		if(stmt!=null)
		{
			stmt.close();
		}
		
	}
		
	/**获取当前生产日期生产单元和班次的最大版本计划顺序号按照降序排列
	* @param produceDate
	* @param produnitid
	* @param workOrder
	* @param con
	* @return
	* @throws SQLException
	*/
	public List<Integer> getPlanOrderbyProdateProidWorder(String produceDate,int produnitid,String workOrder,Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
						DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		List<Integer> list=new ArrayList<Integer>();
		log.debug("获取当前生产日期生产单元和班次的最大版本计划顺序号按照降序排列"+dao_plan.getmaxPlanexcepttemp(produceDate,produnitid,workOrder));
		ResultSet rs=stmt.executeQuery(dao_plan.getmaxPlanexcepttemp(produceDate,produnitid,workOrder));
		while(rs.next()){
			list.add(rs.getInt("int_planOrder"));
		}
		if(stmt!=null)
		{
			stmt.close();
		}
		
		return list;
	}
	
	/**通过版本int_id得到版本计划信息 按照计划顺序号升序排列 谢静天
	* @param int_id
	* @param con
	* @return
	* @throws SQLException
	*/
	public   List<Plan> getPlanbyversionid(int int_id,Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
						DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		log.debug("通过版本int_id得到版本计划信息 按照计划顺序号升序排列"+dao_plan.getPlanbyversionid(int_id));
		ResultSet rs=stmt.executeQuery(dao_plan.getPlanbyversionid(int_id));
		List<Plan> planlist=new ArrayList<Plan>();
		while(rs.next()){
			Plan plan =new Plan();
			     plan.setId(rs.getInt("int_id"));
	             plan.setPlanDate(rs.getDate("dat_planDate"));
	             plan.setProduceDate(rs.getDate("dat_produceDate"));
	             plan.setOrderFormId(rs.getString("str_orderFormId"));
	             plan.setPlanGroupId(rs.getInt("int_planGroupId"));
	             plan.setProduceType(rs.getString("str_produceType"));
	             plan.setProduceName(rs.getString("str_produceName"));
	             plan.setProduceMarker(rs.getString("str_produceMarker"));
	             plan.setProdunitid(rs.getInt("int_produnitid"));
	             plan.setWorkTeam(rs.getString("str_workTeam"));
	             plan.setWorkOrder(rs.getString("str_workOrder"));
	             plan.setAmount(rs.getInt("int_count"));
	             plan.setVersioncode(rs.getString("str_versioncode"));
	             plan.setUpload(rs.getInt("int_upload"));
	             plan.setPlanOrder(rs.getInt("int_planOrder"));
	             plan.setDescription(rs.getString("str_description"));
	             planlist.add(plan);
		}
		if(stmt!=null)
		{
			stmt.close();
		}
		
		return planlist;
	}
		
	/**得到指定id的计划   谢静天
	* @param id
	* @param con
	* @return
	* @throws SQLException
	*/
	public   Plan getplanbyid(int id ,Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
						DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		log.debug("得到指定id的计划"+dao_plan.getplanbyid(id));
		ResultSet rs=stmt.executeQuery(dao_plan.getplanbyid(id));
		Plan plan =new Plan();
		if(rs.next()){
			plan.setId(rs.getInt("int_id"));
	        plan.setPlanDate(rs.getDate("dat_planDate"));
	        plan.setProduceDate(rs.getDate("dat_produceDate"));
	        plan.setOrderFormId(rs.getString("str_orderFormId"));
	        plan.setPlanGroupId(rs.getInt("int_planGroupId"));
	        plan.setProduceType(rs.getString("str_produceType"));
	        plan.setProduceName(rs.getString("str_produceName"));
	        plan.setProduceMarker(rs.getString("str_produceMarker"));
	        plan.setProdunitid(rs.getInt("int_produnitid"));
	        plan.setWorkTeam(rs.getString("str_workTeam"));
	        plan.setWorkOrder(rs.getString("str_workOrder"));
	        plan.setAmount(rs.getInt("int_count"));
	        plan.setVersioncode(rs.getString("str_versioncode"));
	        plan.setUpload(rs.getInt("int_upload"));
	        plan.setPlanOrder(rs.getInt("int_planOrder"));
	        plan.setDescription(rs.getString("str_description"));
		}
		if(stmt!=null)
		{
			stmt.close();
		}
		
		return plan;
	}
		 //
	
	/**修改指定id值的计划  谢静天
	* @param plan
	* @param con
	* @throws SQLException
	*/
	public   void updatePlanbyid(Plan plan,Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
						DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		log.debug("修改指定id值的计划"+dao_plan.updatePlanbyid(plan));
		stmt.executeUpdate(dao_plan.updatePlanbyid(plan));
		if(stmt!=null)
		{
			stmt.close();
		}
		
	}
   
	/**删除生产单元，生产日期，班组和班次,顺序号的作业计划 谢静天
	 * @param produceDate
	 * @param produnitid
	 * @param workOrder
	 * @param planorder
	 * @param con
	 * @throws SQLException
	 */
	public   void deleteplanbyPlanOrder(String produceDate,int produnitid,String workOrder,int planorder,Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
					DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		log.debug("删除生产单元，生产日期，班组和班次,顺序号的作业计划"+dao_plan.deleteplanbyPlanOrder(produceDate, produnitid,workOrder, planorder));
		stmt.executeUpdate(dao_plan.deleteplanbyPlanOrder(produceDate, produnitid,workOrder, planorder));
		if(stmt!=null)
		{
			stmt.close();	
		}
		
	}

	 /**调整指令顺序方法  谢静天
	 * @param produceDate
	 * @param produnitid
	 * @param workOrder
	 * @param planorder
	 * @param con
	 * @throws SQLException
	 */
	public void updatePlanOrder(String produceDate,int produnitid,String workOrder,int planorder ,Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
					DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		log.debug("调整指令顺序方法"+dao_plan.updatePlanOrder(produceDate, produnitid, workOrder, planorder));
		stmt.executeUpdate(dao_plan.updatePlanOrder(produceDate, produnitid, workOrder, planorder));
		if(stmt!=null)
		{
			stmt.close();
		}
		
	}
   
	/**下移或者上移 计划顺序通过计划的id  和互换的顺序号谢静天
	* @param id
	* @param nextorder
	* @param con
	* @throws SQLException
	*/
	public   void   down_or_upPlanOrderbyplanid(int id,int nextorder,Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
						DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		log.debug("下移或者上移 计划顺序通过计划的id  和互换的顺序号"+dao_plan.down_or_upPlanOrderbyplanid(id,nextorder));
        stmt.executeUpdate(dao_plan.down_or_upPlanOrderbyplanid(id,nextorder));
  		if(stmt!=null)
  		{
  			stmt.close();
  		}
  		
	}
		
	/**撤销编辑计划 谢静天
	* @param produceDate
	* @param produnitid
	* @param workOrder
	* @param con
	* @throws SQLException
	*/
	public  void disfrockplan(String produceDate,int produnitid,String workOrder,Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
						DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		log.debug("撤销编辑计划"+dao_plan.disfrockplan(produceDate, produnitid,  workOrder));
        stmt.executeUpdate(dao_plan.disfrockplan(produceDate, produnitid,  workOrder));
        if(stmt!=null)
		{
			stmt.close();
		}
		
	}

	/**提交编辑计划 谢静天
	* @param produceDate
	* @param produnitid
	* @param workOrder
	* @param versioncode
	* @param con
	* @throws SQLException
	*/
	public  void  submitplan(String produceDate,int produnitid,String workOrder,String versioncode,Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
						DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		log.debug("提交编辑计划"+dao_plan.submitplan(produceDate, produnitid, workOrder, versioncode));
        stmt.executeUpdate(dao_plan.submitplan(produceDate, produnitid, workOrder, versioncode));
        if(stmt!=null)
		{
			stmt.close();
		}
		
	}

	/**查询提交的版本号 谢静天
	* @param produceDate
	* @param produnitid
	* @param workOrder
	* @param con
	* @return
	* @throws SQLException
	*/
	public String getversioncodewhensubmit(String produceDate,int produnitid,String workOrder,Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
						DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		log.debug("查询提交的版本号"+dao_plan.getversioncodewhensubmit(produceDate, produnitid,  workOrder));
        ResultSet rs=  stmt.executeQuery(dao_plan.getversioncodewhensubmit(produceDate, produnitid,  workOrder));
        String versioncode="";
        if(rs.next()){
        	versioncode=rs.getString("str_versioncode");
        }
        if(stmt!=null)
		{
			stmt.close();
		}
		
		return versioncode;
	}
	 
	/**发布作业计划 谢静天
	* @param produceDate
	* @param produnitid
	* @param workOrder
	* @param con
	* @throws SQLException
	*/
	public void uploadplan(String produceDate,int produnitid,String workOrder,Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
						DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		log.debug("发布作业计划"+dao_plan.uploadplan(produceDate, produnitid, workOrder));
		stmt.executeUpdate(dao_plan.uploadplan(produceDate, produnitid, workOrder));
        if(stmt!=null)
		{
			stmt.close();
		}
		
	}
	 
	/**取消作业计划发布 谢静天
	* @param produceDate
	* @param produnitid
	* @param workOrder
	* @param con
	* @throws SQLException
	*/
	public void canceluploadplan(String produceDate,int produnitid,String workOrder,Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
							DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		log.debug("取消作业计划发布"+dao_plan.canceluploadplan(produceDate, produnitid,  workOrder));
	    stmt.executeUpdate(dao_plan.canceluploadplan(produceDate, produnitid,  workOrder)); 
	    if(stmt!=null)
		{
			stmt.close();	
		}
		
	}
	  
	/**发布作业时只能看到最大的版本并且版本号不为temp 谢静天
	* @param produceDate
	* @param produnitid
	* @param workOrder
	* @param con
	* @return
	* @throws SQLException
	*/
	public  List<Plan> getmaxPlanexcepttemp(String produceDate,int produnitid,String workOrder,Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
							DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		log.debug("发布作业时只能看到最大的版本并且版本号不为temp "+dao_plan.getmaxPlanexcepttemp(produceDate, produnitid, workOrder));
	    ResultSet rs= stmt.executeQuery(dao_plan.getmaxPlanexcepttemp(produceDate, produnitid, workOrder)); 
	    List<Plan> planlist=new ArrayList<Plan>();
		while(rs.next()){
			Plan plan =new Plan();
			plan.setId(rs.getInt("int_id"));
	        plan.setPlanDate(rs.getDate("dat_planDate"));
	        plan.setProduceDate(rs.getDate("dat_produceDate"));
	        plan.setOrderFormId(rs.getString("str_orderFormId"));
	        plan.setPlanGroupId(rs.getInt("int_planGroupId"));
	        plan.setProduceType(rs.getString("str_produceType"));
	        plan.setProduceName(rs.getString("str_produceName"));
	        plan.setProduceMarker(rs.getString("str_produceMarker"));
	        plan.setProdunitid(rs.getInt("int_produnitid"));
	        plan.setWorkTeam(rs.getString("str_workTeam"));
	        plan.setWorkOrder(rs.getString("str_workOrder"));
	        plan.setAmount(rs.getInt("int_count"));
	        plan.setVersioncode(rs.getString("str_versioncode"));
	        plan.setUpload(rs.getInt("int_upload"));
	        plan.setPlanOrder(rs.getInt("int_planOrder"));
	        plan.setDescription(rs.getString("str_description"));
	        planlist.add(plan);
		}

	    if(stmt!=null)
		{
			stmt.close();	
		}
		
	    return planlist;
	}

	/**查询不同生产日期生产单元班组班次的最大版本信息 谢静天
	* @param con
	* @return
	* @throws SQLException
	*/
	public List<Plan> geteverydaymaxversioncodeplan(Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
							DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		log.debug("查询不同生产日期生产单元班组班次的最大版本信息"+dao_plan.geteverydaymaxversioncodeplan());		 
		ResultSet rs=stmt.executeQuery(dao_plan.geteverydaymaxversioncodeplan());
		List<Plan> planlist=new ArrayList<Plan>();
		while(rs.next()){
			Plan plan =new Plan();
			plan.setId(rs.getInt("int_id"));
		    plan.setPlanDate(rs.getDate("dat_planDate"));
		    plan.setProduceDate(rs.getDate("dat_produceDate"));
		    plan.setOrderFormId(rs.getString("str_orderFormId"));
		    plan.setPlanGroupId(rs.getInt("int_planGroupId"));
		    plan.setProduceType(rs.getString("str_produceType"));
		    plan.setProduceName(rs.getString("str_produceName"));
		    plan.setProduceMarker(rs.getString("str_produceMarker"));
		    plan.setProdunitid(rs.getInt("int_produnitid"));
		    plan.setWorkTeam(rs.getString("str_workTeam"));
		    plan.setWorkOrder(rs.getString("str_workOrder"));
		    plan.setAmount(rs.getInt("int_count"));
		    plan.setVersioncode(rs.getString("str_versioncode"));
		    plan.setUpload(rs.getInt("int_upload"));
		    plan.setPlanOrder(rs.getInt("int_planOrder"));
		    plan.setDescription(rs.getString("str_description"));
		    planlist.add(plan);
		}
		if(stmt!=null)
		{
			stmt.close();
		}
		
		return planlist;
	}

	/**替换时不核对被替掉内容的作业计划  谢静天
	* @param produceDate
	* @param produnitid
	* @param workOrder
	* @param con
	* @return
	* @throws SQLException
	*/
	public List<Plan>  geteverydaymaxversioncodeplanexceptnow(String produceDate,int produnitid,String workOrder,Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
								DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		log.debug("替换时不核对被替掉内容的作业计划"+dao_plan.geteverydaymaxversioncodeplanexceptnow(produceDate, produnitid,  workOrder));			 
		ResultSet rs=stmt.executeQuery(dao_plan.geteverydaymaxversioncodeplanexceptnow(produceDate, produnitid,  workOrder));			
		List<Plan> planlist=new ArrayList<Plan>();
		while(rs.next()){
			Plan plan =new Plan();
			plan.setId(rs.getInt("int_id"));
			plan.setPlanDate(rs.getDate("dat_planDate"));
			plan.setProduceDate(rs.getDate("dat_produceDate"));
			plan.setOrderFormId(rs.getString("str_orderFormId"));
			plan.setPlanGroupId(rs.getInt("int_planGroupId"));
			plan.setProduceType(rs.getString("str_produceType"));
			plan.setProduceName(rs.getString("str_produceName"));
			plan.setProduceMarker(rs.getString("str_produceMarker"));
			plan.setProdunitid(rs.getInt("int_produnitid"));
			plan.setWorkTeam(rs.getString("str_workTeam"));
			plan.setWorkOrder(rs.getString("str_workOrder"));
			plan.setAmount(rs.getInt("int_count"));
			plan.setVersioncode(rs.getString("str_versioncode"));
			plan.setUpload(rs.getInt("int_upload"));
			plan.setPlanOrder(rs.getInt("int_planOrder"));
			plan.setDescription(rs.getString("str_description"));
			planlist.add(plan);
		}
		if(stmt!=null)
		{
			stmt.close();
		}
		
		return planlist;
	}
	
	/**查询不同生产日期生产单元班组班次的最大版本信息 中是否有要查找的主物料值 谢静天
	 * @param materiel
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public Plan getmaterielfromeditplan(String produceDate,int produnitid,String workOrder,String materiel,Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
								DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		log.debug("查询不同生产日期生产单元班组班次的最大版本信息 中是否有要查找的主物料值"+dao_plan.getmaterielfromeditplan(produceDate,produnitid,workOrder,materiel));			 
		ResultSet rs=stmt.executeQuery(dao_plan.getmaterielfromeditplan(produceDate,produnitid,workOrder,materiel));
		Plan plan=new Plan();
		if(rs.next()){		 
			plan.setId(rs.getInt("int_id"));
			plan.setPlanDate(rs.getDate("dat_planDate"));
			plan.setProduceDate(rs.getDate("dat_produceDate"));
			plan.setOrderFormId(rs.getString("str_orderFormId"));
			plan.setPlanGroupId(rs.getInt("int_planGroupId"));
			plan.setProduceType(rs.getString("str_produceType"));
			plan.setProduceName(rs.getString("str_produceName"));
			plan.setProduceMarker(rs.getString("str_produceMarker"));
			plan.setProdunitid(rs.getInt("int_produnitid"));
			plan.setWorkTeam(rs.getString("str_workTeam"));
			plan.setWorkOrder(rs.getString("str_workOrder"));
			plan.setAmount(rs.getInt("int_count"));
			plan.setVersioncode(rs.getString("str_versioncode"));
			plan.setUpload(rs.getInt("int_upload"));
			plan.setPlanOrder(rs.getInt("int_planOrder"));
			plan.setDescription(rs.getString("str_description"));

		}
		if(stmt!=null)
		{
			stmt.close();
		}
		
		return plan;
	}

	/**编辑页面查找记录 版本号用temp  谢静天
	* @param produceDate
	* @param produnitid
	* @param workOrder
	* @param con
	* @return
	* @throws SQLException
	*/
	public  List<Plan> getplancontainstemp(String produceDate,int produnitid,String workOrder,Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
								DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		log.debug("编辑页面查找记录 版本号用temp"+dao_plan.getplancontainstemp(produceDate, produnitid, workOrder));			 
		ResultSet rs=stmt.executeQuery(dao_plan.getplancontainstemp(produceDate, produnitid, workOrder));		
		List<Plan> planlist=new ArrayList<Plan>();
		while(rs.next()){
			Plan plan =new Plan();
			plan.setId(rs.getInt("int_id"));
			plan.setPlanDate(rs.getDate("dat_planDate"));
			plan.setProduceDate(rs.getDate("dat_produceDate"));
			plan.setOrderFormId(rs.getString("str_orderFormId"));
			plan.setPlanGroupId(rs.getInt("int_planGroupId"));
			plan.setProduceType(rs.getString("str_produceType"));
			plan.setProduceName(rs.getString("str_produceName"));
			plan.setProduceMarker(rs.getString("str_produceMarker"));
			plan.setProdunitid(rs.getInt("int_produnitid"));
			plan.setWorkTeam(rs.getString("str_workTeam"));
			plan.setWorkOrder(rs.getString("str_workOrder"));
			plan.setAmount(rs.getInt("int_count"));
			plan.setVersioncode(rs.getString("str_versioncode"));
			plan.setUpload(rs.getInt("int_upload"));
			plan.setPlanOrder(rs.getInt("int_planOrder"));
			plan.setDescription(rs.getString("str_description"));
			planlist.add(plan);
		}
		if(stmt!=null)
		{
			stmt.close();
		}
		
		return planlist;
	}

	/**核对是否存在order的计划顺序号 谢静天  计划更改和添加页面
	* @param produceDate
	* @param produnitid
	* @param workOrder
	* @param order
	* @param con
	* @return
	* @throws SQLException
	*/
	public boolean checkplanorder(String produceDate,int produnitid,String workOrder,int order,Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
								DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		boolean f=false;
		log.debug("核对是否存在order的计划顺序号"+dao_plan.checkplanorder(produceDate, produnitid,workOrder,order));
		ResultSet rs=stmt.executeQuery(dao_plan.checkplanorder(produceDate, produnitid,workOrder,order));
		if(rs.next()){
			f=true;
		}
		if(stmt!=null)
		{
			stmt.close();		
		}
		
		return f;
	}
	
	/**通过生产单元班次查询作业计划用于删除工作时刻表做判断
	* author : 包金旭
	 * @param Produnitid
	 * @param Order
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public boolean getAllPlanByProdunitidOrder(int Produnitid,String Order,Connection con)throws SQLException{
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
							DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		boolean f=false;
		log.debug("通过生产单元班次查询作业计划用于删除工作时刻表做判断"+dao_plan.getAllPlanByProdunitidOrder(Produnitid, Order));
		ResultSet rs=stmt.executeQuery(dao_plan.getAllPlanByProdunitidOrder(Produnitid, Order));
		if(rs.next()){
			f=true;
		}
		if(stmt!=null){
			stmt.close();		
		}
		
		return f;
	}
			    		
	/**计划生成指令时 没发布的计划调度员看不见。谢静天
	 * @param produceDate
	 * @param produnitid
	 * @param workOrder
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public List<Plan> getmaxPlanexcepttempupload(String produceDate,int produnitid,String workOrder,Connection con)throws SQLException{	
		DAO_Plan dao_plan = (DAO_Plan) DAOFactoryAdapter.getInstance(
						DataBaseType.getDataBaseType(con), DAO_Plan.class);
		Statement stmt = con.createStatement();
		log.debug("计划生成指令时 没发布的计划调度员看不见"+dao_plan.getmaxPlanexcepttempupload(produceDate, produnitid, workOrder));
		ResultSet rs= stmt.executeQuery(dao_plan.getmaxPlanexcepttempupload(produceDate, produnitid, workOrder));
		List<Plan> planlist=new ArrayList<Plan>();
		while(rs.next()){
			Plan plan =new Plan();
			plan.setId(rs.getInt("int_id"));
			plan.setPlanDate(rs.getDate("dat_planDate"));
			plan.setProduceDate(rs.getDate("dat_produceDate"));
			plan.setOrderFormId(rs.getString("str_orderFormId"));
			plan.setPlanGroupId(rs.getInt("int_planGroupId"));
			plan.setProduceType(rs.getString("str_produceType"));
			plan.setProduceName(rs.getString("str_produceName"));
			plan.setProduceMarker(rs.getString("str_produceMarker"));
			plan.setProdunitid(rs.getInt("int_produnitid"));
			plan.setWorkTeam(rs.getString("str_workTeam"));
			plan.setWorkOrder(rs.getString("str_workOrder"));
			plan.setAmount(rs.getInt("int_count"));
			plan.setVersioncode(rs.getString("str_versioncode"));
			plan.setUpload(rs.getInt("int_upload"));
          	plan.setPlanOrder(rs.getInt("int_planOrder"));
          	plan.setDescription(rs.getString("str_description"));
          	planlist.add(plan);
		}
        if(stmt!=null)
		{
        	stmt.close();
		}
		
        return planlist;
	}
	
	
				 
}
			  
			  

