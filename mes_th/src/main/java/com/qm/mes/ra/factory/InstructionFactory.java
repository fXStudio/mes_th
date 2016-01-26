package com.qm.mes.ra.factory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;
import java.text.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.DataBaseType;
import com.qm.mes.ra.bean.Instruction;
import com.qm.mes.ra.dao.DAO_Instruction;
import com.qm.mes.system.dao.DAOFactoryAdapter;

/**
 *
 * @author YuanPeng
 */
public class InstructionFactory {
	private final Log log = LogFactory.getLog(InstructionFactory.class);
	
	 /**
     * 保存指令
     * 袁鹏
     * @param instruction
     *          指令对象
     * @param con
     *          连接对象
     * @throws java.sql.SQLException
     */
    public void saveInstruction (Instruction instruction,Connection con) throws SQLException{
        DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
                DataBaseType.getDataBaseType(con),DAO_Instruction.class);
        Statement stmt = con.createStatement();
        log.debug("保存指令"+dao_Instruction.saveInstruction(instruction));
        stmt.execute(dao_Instruction.saveInstruction(instruction));
        if(stmt != null){
            stmt.close();
        }
    }

    /**
     * 通过ID查询指令
     * 袁鹏
     * @param id
     *          指令序列号
     * @param con
     *          连接对象
     * @return 通过ID查询出的指令对象
     * @throws java.sql.SQLException
     */
    public Instruction getInstructionById (int id,Connection con) throws SQLException, ParseException{
        Instruction instruction = new Instruction();
        DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
                DataBaseType.getDataBaseType(con),DAO_Instruction.class);
        Statement stmt = con.createStatement();
        log.debug("通过ID查询指令"+dao_Instruction.getInstructionById(id));
        ResultSet rs = stmt.executeQuery(dao_Instruction.getInstructionById(id));
        if(rs.next()){
            instruction.setId(rs.getInt("INT_ID"));
            instruction.setProdunitid(rs.getInt("INT_PRODUNITID"));
            instruction.setProduceDate(rs.getDate("DAT_PRODUCEDATE"));
            instruction.setVersionCode(rs.getString("STR_VERSIONCODE"));
            instruction.setInstructionOrder(rs.getInt("INT_INSTRUCTORDER"));
            instruction.setPlanDate(rs.getString("DAT_PLANDATE")==null?null:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("DAT_PLANDATE")));
            instruction.setPlanOrder(rs.getInt("INT_PLANORDER"));
            instruction.setProduceType(rs.getString("STR_PRODUCETYPE"));
            instruction.setProduceName(rs.getString("STR_PRODUCENAME"));
            instruction.setProduceMarker(rs.getString("STR_PRODUCEMARKER"));
            instruction.setWorkOrder(rs.getString("STR_WORKORDER"));
            instruction.setWorkTeam(rs.getString("STR_WORKTEAM"));
            instruction.setPlanBegin(rs.getString("TIM_PLANBEGIN")==null?null:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("TIM_PLANBEGIN")));
            instruction.setPlanFinish(rs.getString("TIM_PLANFINISH")==null?null:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("TIM_PLANFINISH")));
            instruction.setCount(rs.getInt("INT_COUNT"));
            instruction.setInstructStateID(rs.getInt("INT_INSTRUCTSTATEID"));
            instruction.setIssuance(rs.getInt("INT_ISSUANCE"));
            instruction.setStaError(rs.getInt("INT_STAERROR"));
            instruction.setDelete(rs.getInt("INT_DELETE"));
            instruction.setEdit(rs.getInt("INT_EDIT"));
        }
        if(stmt != null)
            stmt.close();
        return instruction;
    }

    /**
     * 查询比Int_instructOrder大的对象
     * 袁鹏
     * @param Int_instructOrder
     *                  指令顺序号
     * @param con
     *          连接对象
     * @throws java.sql.SQLException
     * return Instruction
     *              返回比Int_instructOrder大的对象
     */
    public List<Instruction> OrderPlus (int ProduceUnitID, String str_date,String workOrder,int Int_instructOrder,Connection con) throws SQLException, ParseException{
    	List<Instruction> list = new ArrayList<Instruction>();
        DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
                DataBaseType.getDataBaseType(con),DAO_Instruction.class);
        Statement stmt = con.createStatement();
        log.debug("查询比Int_instructOrder大的对象"+dao_Instruction.getByOrderPlus(ProduceUnitID,str_date,workOrder,Int_instructOrder));
        ResultSet rs = stmt.executeQuery(dao_Instruction.getByOrderPlus(ProduceUnitID,str_date,workOrder,Int_instructOrder));
        while(rs.next()){
            Instruction instruction = new Instruction();
            instruction.setId(rs.getInt("INT_ID"));
            instruction.setProdunitid(rs.getInt("INT_PRODUNITID"));
            instruction.setProduceDate(rs.getDate("DAT_PRODUCEDATE"));
            instruction.setVersionCode(rs.getString("STR_VERSIONCODE"));
            instruction.setInstructionOrder(rs.getInt("INT_INSTRUCTORDER"));
            instruction.setPlanDate(rs.getString("DAT_PLANDATE")==null?null:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("DAT_PLANDATE")));
            instruction.setPlanOrder(rs.getInt("INT_PLANORDER"));
            instruction.setProduceType(rs.getString("STR_PRODUCETYPE"));
            instruction.setProduceName(rs.getString("STR_PRODUCENAME"));
            instruction.setProduceMarker(rs.getString("STR_PRODUCEMARKER"));
            instruction.setWorkOrder(rs.getString("STR_WORKORDER"));
            instruction.setWorkTeam(rs.getString("STR_WORKTEAM"));
            instruction.setPlanBegin(rs.getString("TIM_PLANBEGIN")==null?null:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("TIM_PLANBEGIN")));
            instruction.setPlanFinish(rs.getString("TIM_PLANFINISH")==null?null:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("TIM_PLANFINISH")));
            instruction.setCount(rs.getInt("INT_COUNT"));
            instruction.setInstructStateID(rs.getInt("INT_INSTRUCTSTATEID"));
            instruction.setIssuance(rs.getInt("INT_ISSUANCE"));
            instruction.setStaError(rs.getInt("INT_STAERROR"));
            instruction.setDelete(rs.getInt("INT_DELETE"));
            instruction.setEdit(rs.getInt("INT_EDIT"));
            list.add(instruction);
        }
        if (stmt != null) {
			stmt.close();
		}
        return list;
    }

   /**
    * 通过生产单元号、生产日期、状态号、被锁定数量查询出可以被编辑的作业指令
    * 袁鹏
    * @param ProduceUnitID
    *           生产单元号
    * @param str_date
    *           生产日期
    * @param stateid
    *           状态号
    * @param LockNum
    *       锁定个数
    * @param con
    *       连接对象
    * @return 作业指令ID(List<Integer>)
    * @throws java.sql.SQLException
    */
   public List<Integer> getInstructionsByUnlock(int ProduceUnitID,String str_date,String workOrder, int stateid,int LockNum,Connection con)throws SQLException{
       int i=0;
       List<Integer> list = new ArrayList<Integer>();
       DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
               DataBaseType.getDataBaseType(con),DAO_Instruction.class);
		Statement stmt = con.createStatement();
		log.debug(" 通过生产单元号、生产日期、班次、状态号、被锁定数量查询出可以被编辑的作业指令"+dao_Instruction.getInstructionByProUnitProDateStateOrder(ProduceUnitID,str_date,workOrder,stateid));
		ResultSet rs  = stmt.executeQuery(dao_Instruction.getInstructionByProUnitProDateStateOrder(ProduceUnitID,str_date,workOrder,stateid));
        while(rs.next()){
            if(++i > LockNum){
                list.add(rs.getInt(1));
            }
        }
        if (stmt != null) {
			stmt.close();
		}
        return list;
   }

   /**
     * 通过ID修改指令编辑状态
     * 袁鹏
     * @param id
     *          指令序列号
     * @param con
     *          连接对象
     * @return 通过ID修改指令编辑状态
     * @throws java.sql.SQLException
     */
    public void editInstructionById (int id,Connection con) throws SQLException{
        DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
                DataBaseType.getDataBaseType(con),DAO_Instruction.class);
        Statement stmt = con.createStatement();
        log.debug(" 通过ID修改指令编辑状态"+dao_Instruction.editInstructionById(id));
        stmt.execute(dao_Instruction.editInstructionById(id));
        if(stmt != null)
            stmt.close();
    }

    /**
     * 通过ID修改指令取消编辑状态
     * 袁鹏
     * @param id
     *          指令序列号
     * @param con
     *          连接对象
     * @return 通过ID修改指令编辑状态
     * @throws java.sql.SQLException
     */
    public void uneditInstructionById (int id,Connection con) throws SQLException{
        DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
                DataBaseType.getDataBaseType(con),DAO_Instruction.class);
        Statement stmt = con.createStatement();
        log.debug(" 通过ID修改指令取消编辑状态"+dao_Instruction.uneditInstructionById(id));
        stmt.execute(dao_Instruction.uneditInstructionById(id));
        if(stmt != null)
            stmt.close();
    }
    
    /**
     * 通过生产单元号、生产日期、顺序号大于首个非编辑号来更改int_delete字段值为1
     *  袁鹏
     * @param ProduceUnitID 生产单元号
     * @param str_date 生产日期
     * @param UnlockStartOrder
     * @return 
     */
    public void DeleteInstructionByProUnitDateworkOrderUnlockOrder (int ProduceUnitID, String str_date,String workOrder, int UnlockStartOrder, Connection con) throws SQLException{
        DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
                DataBaseType.getDataBaseType(con),DAO_Instruction.class);
        Statement stmt = con.createStatement();
        log.debug(" 通过生产单元号、生产日期、班次、顺序号大于首个非编辑号来更改int_delete字段值为1"+dao_Instruction.DeleteInstructionByProUnitDateworkOrderUnlockOrder(ProduceUnitID, str_date,workOrder, UnlockStartOrder));
        stmt.execute(dao_Instruction.DeleteInstructionByProUnitDateworkOrderUnlockOrder(ProduceUnitID, str_date,workOrder, UnlockStartOrder));
        if(stmt != null)
            stmt.close();
    }
    
    /**
     * 通过生产单元号、生产日期查询记录数量
     *  袁鹏
     * @param ProduceUnitID
     * 			生产单元号
     * @param str_date
     * 			生产日期
     * @return 返回通过生产单元号、生产日期查询记录的数量
     */
    public int getCountByProUnitDateOrder(int ProduceUnitID, String str_date, String str_workOrder,Connection con){
    	int count = 0;
    	try {
    	DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
			DataBaseType.getDataBaseType(con),DAO_Instruction.class);
		
 		Statement stmt = con.createStatement();
 		log.debug(" 通过生产单元号、生产日期、班次，顺序号大于首个非编辑号来更改int_delete字段值为1"+dao_Instruction.getCountByProUnitDateOrder(ProduceUnitID, str_date,str_workOrder));
 		ResultSet rs  = stmt.executeQuery(dao_Instruction.getCountByProUnitDateOrder(ProduceUnitID, str_date,str_workOrder));
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
	    * 通过生产单元号、生产日期、状态号、被锁定数量查询出不可以被编辑的作业指令
	    * 袁鹏
	    * @param ProduceUnitID
	    *           生产单元号
	    * @param str_date
	    *           生产日期
	    * @param stateid
	    *           状态号
	    * @param LockNum
	    *       锁定个数
	    * @param con
	    *       连接对象
	    * @return 作业指令ID(List<Integer>)
	    * @throws java.sql.SQLException
	    */
	   public List<Integer> getInstructionsBylock(int ProduceUnitID,String str_date,String workOrder,int stateid,int LockNum,Connection con)throws SQLException{
	       int i=0;
	       List<Integer> list = new ArrayList<Integer>();
	       DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
	               DataBaseType.getDataBaseType(con),DAO_Instruction.class);
			Statement stmt = con.createStatement();
			log.debug("通过生产单元号、生产日期、状态号、被锁定数量查询出不可以被编辑的作业指令"+dao_Instruction.getInstructionByProUnitProDateStateOrder(ProduceUnitID,str_date,workOrder,stateid));
			ResultSet rs  = stmt.executeQuery(dao_Instruction.getInstructionByProUnitProDateStateOrder(ProduceUnitID,str_date,workOrder,stateid));
	        while(rs.next()){
	            if(++i <= LockNum){
	                list.add(rs.getInt(1));
	            }
	        }
	        if (stmt != null) {
				stmt.close();
			}
	        return list;
	   }

	   
	   /**
	     * 通过生产单元号、生产日期、顺序号大于首个非编辑号来更改int_delete字段值为1
	     *  袁鹏
	     * @param ProduceUnitID
	     * @param str_date
	     * @param UnlockStartOrder
	     * @return 
	     */
	    public void issuanceInstruction (int id, Connection con) throws SQLException{
	        DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
	                DataBaseType.getDataBaseType(con),DAO_Instruction.class);
	        Statement stmt = con.createStatement();
	        log.debug("通过生产单元号、生产日期、顺序号大于首个非编辑号来更改int_delete字段值为1"+dao_Instruction.issuanceInstruction(id));
	        stmt.execute(dao_Instruction.issuanceInstruction(id));
	        if(stmt != null)
	            stmt.close();
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
	    	DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con),DAO_Instruction.class);
			
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
	     * 通过生产单元号、生产日期查询记录数量
	     *  袁鹏
	     * @param ProduceUnitID
	     * 			生产单元号
	     * @param str_date
	     * 			生产日期
	     * @return 返回通过生产单元号、生产日期查询记录的数量
	     */
	    public int getCountByProUnitDateworkOrder(int ProduceUnitID, String str_date, String workOrder,Connection con){
	    	int count = 0;
	    	try {
	    	DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con),DAO_Instruction.class);
			
	 		Statement stmt = con.createStatement();
	 		log.debug(" 通过生产单元号、生产日期、顺序号大于首个非编辑号来更改int_delete字段值为1"+dao_Instruction.getCountByProUnitDateOrder(ProduceUnitID, str_date,workOrder));
	 		ResultSet rs  = stmt.executeQuery(dao_Instruction.getCountByProUnitDateOrder(ProduceUnitID, str_date,workOrder));
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
	     * 查询该生产单元，生产日期，多个状态号的指令对象
	     * 
	     * @param ProduceUnitID 生产单元号
	     * @param str_date	生产日期
	     * @param list_state	状态列表
	     * @param con	连接对象
	     * @return list<Instruction>指令列表
	     * @throws SQLException
	     * @throws ParseException
	     */
	    public List<Instruction> getCountByProUnitDateState (int ProduceUnitID,List<Integer> list_state,Connection con) throws SQLException, ParseException{
	    	List<Instruction> list_instruction = new ArrayList<Instruction>();
	        DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
	                DataBaseType.getDataBaseType(con),DAO_Instruction.class);
	        Statement stmt = con.createStatement();
	        log.debug("查询该生产单元，生产日期，多个状态号的指令对象"+dao_Instruction.getCountByProUnitDateState(ProduceUnitID,list_state));
	        ResultSet rs = stmt.executeQuery(dao_Instruction.getCountByProUnitDateState(ProduceUnitID,list_state));
	        while(rs.next()){
	        	Instruction instruction = new Instruction();
	        	instruction.setId(rs.getInt("INT_ID"));
	            instruction.setProdunitid(rs.getInt("INT_PRODUNITID"));
	            instruction.setProduceDate(rs.getDate("DAT_PRODUCEDATE"));
	            instruction.setVersionCode(rs.getString("STR_VERSIONCODE"));
	            instruction.setInstructionOrder(rs.getInt("INT_INSTRUCTORDER"));
	            instruction.setPlanDate(rs.getString("DAT_PLANDATE")==null?null:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("DAT_PLANDATE")));
	            instruction.setPlanOrder(rs.getInt("INT_PLANORDER"));
	            instruction.setProduceType(rs.getString("STR_PRODUCETYPE"));
	            instruction.setProduceName(rs.getString("STR_PRODUCENAME"));
	            instruction.setProduceMarker(rs.getString("STR_PRODUCEMARKER"));
	            instruction.setWorkOrder(rs.getString("STR_WORKORDER"));
	            instruction.setWorkTeam(rs.getString("STR_WORKTEAM"));
	            instruction.setPlanBegin(rs.getString("TIM_PLANBEGIN")==null?null:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("TIM_PLANBEGIN")));
	            instruction.setPlanFinish(rs.getString("TIM_PLANFINISH")==null?null:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("TIM_PLANFINISH")));
	            instruction.setCount(rs.getInt("INT_COUNT"));
	            instruction.setInstructStateID(rs.getInt("INT_INSTRUCTSTATEID"));
	            instruction.setIssuance(rs.getInt("INT_ISSUANCE"));
	            instruction.setStaError(rs.getInt("INT_STAERROR"));
	            instruction.setDelete(rs.getInt("INT_DELETE"));
	            instruction.setEdit(rs.getInt("INT_EDIT"));
	            list_instruction.add(instruction);
	        }
	        if(stmt != null)
	            stmt.close();
	        return list_instruction;
	    }

	    /**
	     * 修改指令状态
	     * 谢静天
	     * @param structStateID 状态id
	     *  @param staError 状态跳转的异常标识
	     *  @param producemarker 产品标识
	     * @return  
	     */
	    public void updateInstructState(int structStateID,int staError,String producemarker,int produnitid,Connection con)	throws SQLException {
			DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
	                DataBaseType.getDataBaseType(con),DAO_Instruction.class); 
			Statement stmt = con.createStatement();
			log.debug("修改指令状态"+dao_Instruction.updateInstructState(structStateID, staError, producemarker,produnitid ));
			stmt.executeUpdate(dao_Instruction.updateInstructState(structStateID, staError, producemarker,produnitid ));

			if (stmt != null) {
				stmt.close();
				
			}
	    }
	    
	    /**
	     * 查看主物料当前指令状态
	     * 谢静天
	     * @param str_produceMarker 产品标识
	     * @param produnitid
	     * @param con
	     * @return
	     * @throws SQLException
	     */
	   public int checkstr_produceMarker(String str_produceMarker,int produnitid,Connection con)throws SQLException {
	    	DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
	                DataBaseType.getDataBaseType(con),DAO_Instruction.class); 
			Statement stmt = con.createStatement();
			log.debug("查看主物料当前指令状态"+dao_Instruction.checkstr_produceMarker(str_produceMarker,produnitid));
			ResultSet rs=stmt.executeQuery(dao_Instruction.checkstr_produceMarker(str_produceMarker,produnitid));
			int Int_instrucsttateid=0;
			if(rs.next())
				Int_instrucsttateid=rs.getInt(1);
			if (stmt != null) {
				stmt.close();
			}
			return Int_instrucsttateid;
	    }
	    /**
	     * 指令状态出现异常时要保存出现的现在
	     * 谢静天
	     * 
	     *  @param producemarker 产品标识
	     *   @param userId用户id
	     *    @param Int_instructionStateID状态id
	     *     @param  采集点id
	     * @return  返回相对应的指令
	     */
	    public void savet_ra_Instructionexception(int Int_instructionStateID,int userId,String produceMarker,int INT_GATHERID,Connection con)throws SQLException {
	    	DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
	                DataBaseType.getDataBaseType(con),DAO_Instruction.class); 
			Statement stmt = con.createStatement();
			log.debug("指令状态出现异常时要保存出现的现在"+dao_Instruction.savet_ra_Instructionexception(Int_instructionStateID, userId, produceMarker, INT_GATHERID));
			stmt.executeUpdate(dao_Instruction.savet_ra_Instructionexception(Int_instructionStateID, userId, produceMarker, INT_GATHERID));

			if (stmt != null) {
				stmt.close();
			}
	    	
	    }
	    /**
	     * * 查看产品标识如果页面输入的主物料值在指令表中看是否存在。
	     * 谢静天
	     * @param producemarker
	     * @param con
	     * @return
	     * @throws SQLException
	     */

	    public boolean checkmaterielValue(String producemarker,Connection con) throws SQLException {
	    
	    	DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
	                DataBaseType.getDataBaseType(con),DAO_Instruction.class); 
			Statement stmt = con.createStatement();
			log.debug("查看产品标识如果页面输入的主物料值在指令表中看是否存在"+dao_Instruction.checkmaterielValue(producemarker));
			ResultSet rs=stmt.executeQuery(dao_Instruction.checkmaterielValue(producemarker));
              boolean f=false;
              if(rs.next()){
            	  f=true;
              }
  			if (stmt != null) {
  				stmt.close();
  			}
  	      
			return f;
	       }
	    /**
	     * 根据生产时间和生产单元查看相关的指令。
	     * 谢静天
	     * @param int_produnitid
	     * @param str_date
	     * @param workOrder
	     * @param con
	     * @return 返回相对应的指令
	     * @throws SQLException
	     * @throws ParseException
	     */
	     
	    
	   public  List<Instruction> getInstructionByProduceUnitProduceDateWorkorder(int int_produnitid,String str_date,String workOrder,Connection con) throws SQLException ,ParseException{
	      
	       List<Instruction> list = new ArrayList<Instruction>();
	       DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
	               DataBaseType.getDataBaseType(con),DAO_Instruction.class);
	    
			Statement stmt = con.createStatement();
			log.debug("根据生产时间和生产单元和班次查看相关的指令"+dao_Instruction.getInstructionByProduceUnitProduceDateWorkorder(int_produnitid, str_date,workOrder));
			ResultSet rs = stmt.executeQuery(dao_Instruction.getInstructionByProduceUnitProduceDateWorkorder(int_produnitid, str_date,workOrder));
	        while(rs.next()){
	        	 Instruction instruction = new Instruction();
	            instruction.setId(rs.getInt("INT_ID"));
	            instruction.setProdunitid(rs.getInt("INT_PRODUNITID"));
	            instruction.setProduceDate(rs.getDate("DAT_PRODUCEDATE"));
	            instruction.setVersionCode(rs.getString("STR_VERSIONCODE"));
	            instruction.setInstructionOrder(rs.getInt("INT_INSTRUCTORDER"));
	            instruction.setPlanDate(rs.getDate("DAT_PLANDATE"));
	            instruction.setPlanOrder(rs.getInt("INT_PLANORDER"));
	            instruction.setProduceType(rs.getString("STR_PRODUCETYPE"));
	            instruction.setProduceName(rs.getString("STR_PRODUCENAME"));
	            instruction.setProduceMarker(rs.getString("STR_PRODUCEMARKER"));
	            instruction.setWorkOrder(rs.getString("STR_WORKORDER"));
	            instruction.setWorkTeam(rs.getString("STR_WORKTEAM"));
	            instruction.setPlanBegin(rs.getDate("TIM_PLANBEGIN"));
	            instruction.setPlanFinish(rs.getDate("TIM_PLANFINISH"));
	            instruction.setCount(rs.getInt("INT_COUNT"));
	            instruction.setInstructStateID(rs.getInt("INT_INSTRUCTSTATEID"));
	            list.add(instruction);
	        }
			if (stmt != null) {
				stmt.close();			
			}
	        return list;
	   }
	   /**
	    * 更新指令版本
	    * 当创建新的版本时调用
	    * 更新指令表中的版本字段
	    * 谢静天
	    * @param instuction
	    * @param str_versioncode
	    * @param con
	    * @throws SQLException
	    * @throws ParseException
	    */
	   
	   public void updateInstructionVersioncode(Instruction instuction,String str_versioncode,Connection con)throws SQLException ,ParseException{
	       DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
	               DataBaseType.getDataBaseType(con),DAO_Instruction.class);
			Statement stmt = con.createStatement();
			log.debug("更新指令版本"+dao_Instruction.updateInstructionVersioncode(instuction, str_versioncode));
			stmt.executeUpdate(dao_Instruction.updateInstructionVersioncode(instuction, str_versioncode));
			if (stmt != null) {
				stmt.close();
			}
	   }
	   /**
	    *  * 核对是否可以进行版本保存
	    * 谢静天
	    * 根据生产单元，生产日期及生产单元的未上线指令状态
	    * @param int_produnitid
	    * @param str_date
	    * @param workOrder
	    * @param con
	    * @return
	    * @throws SQLException
	    * @throws ParseException
	    */
	   public boolean  checksaveVersion(int int_produnitid,String str_date,String workOrder,Connection con)throws SQLException ,ParseException{
	       DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
	               DataBaseType.getDataBaseType(con),DAO_Instruction.class);
			Statement stmt = con.createStatement();
			log.debug("核对是否可以进行版本保存"+dao_Instruction.checksaveVersion(int_produnitid, str_date,workOrder));
			ResultSet rs=	stmt.executeQuery(dao_Instruction.checksaveVersion(int_produnitid, str_date,workOrder));
			boolean f=false;
			if(rs.next()){
				f=true;
			}
			  if (stmt != null) {
					stmt.close();	
				}
				return f;
	   }

	   /**
	    * * 根据生产时间和生产单元和恢复状态查看相关的指令进行版本恢复
	    * 谢静天
	    * @param int_produnitid
	    * @param str_date
	    * @param int_stateid
	    * @param con
	    * @return
	    * @throws SQLException
	    * @throws ParseException
	    */

	   public boolean  comebackVersion(int int_produnitid,String str_date,int int_stateid,Connection con)throws SQLException ,ParseException{
	       DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
	               DataBaseType.getDataBaseType(con),DAO_Instruction.class);
			Statement stmt = con.createStatement();
			log.debug("根据生产时间和生产单元和恢复状态查看相关的指令进行版本恢复"+dao_Instruction.comebackVersion(int_produnitid, str_date, int_stateid));
			ResultSet rs=stmt.executeQuery(dao_Instruction.comebackVersion(int_produnitid, str_date, int_stateid));
			 int n=0;
				if(rs.next()){
					n++;
				}
				if (stmt != null) {
					stmt.close();	
				}
		     
				if(n!=0){
					return false;
				}
				else
					return true;
	   }
	   /**
	    * 根据生产时间和生产单元删除指令
	    * 谢静天
	    * @param int_produnitid
	    * @param str_date
	    * @param workOrder
	    * @param con
	    * @throws SQLException
	    * @throws ParseException
	    */

	   public void deleteInstructionByProduceUnitProduceDateWorkorder(int int_produnitid,String str_date,String workOrder,Connection con)throws SQLException ,ParseException{
	       DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
	               DataBaseType.getDataBaseType(con),DAO_Instruction.class);
			Statement stmt = con.createStatement();
			log.debug("根据生产时间和生产单元和班次删除指令"+dao_Instruction.deleteInstructionByProduceUnitProduceDateWorkorder(int_produnitid, str_date,workOrder));
			stmt.executeUpdate(dao_Instruction.deleteInstructionByProduceUnitProduceDateWorkorder(int_produnitid, str_date,workOrder));
			if (stmt != null) {
				stmt.close();
			}
	   }
	   /**
	    * 查找指令最大的顺序号
	    * 进行指令追加
	    * 谢静天
	    * @param int_produnitid
	    * @param str_date
	    * @param workOrder
	    * @param con
	    * @return
	    * @throws SQLException
	    * @throws ParseException
	    */
	 
	   public int getInstructionMaxOrder(int int_produnitid,String str_date,String workOrder,Connection con)throws SQLException ,ParseException{
	       DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
	               DataBaseType.getDataBaseType(con),DAO_Instruction.class);
			Statement stmt = con.createStatement();
			log.debug("查找指令最大的顺序号"+dao_Instruction.getInstructionMaxOrder(int_produnitid, str_date, workOrder));
			ResultSet rs=	stmt.executeQuery(dao_Instruction.getInstructionMaxOrder(int_produnitid, str_date, workOrder));
			 int n=1;
			if(rs.next()){
				n=rs.getInt(1);
			}
			if (stmt != null) {
				stmt.close();
			}
				return n;
	   }
	   /**
	    * 谢静天
	    * 查看指令表中是否存在生产单元和生产日期的指令
	    * @param int_produnitid
	    * @param str_date
	    * @param con
	    * @return
	    * @throws SQLException
	    * @throws ParseException
	    */
	   public boolean checkByProduceUnitProduceDateWorkorder(int int_produnitid,String str_date,String workOrder,Connection con) throws SQLException ,ParseException{
	      
	  
	       DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
	               DataBaseType.getDataBaseType(con),DAO_Instruction.class);
	    
			Statement stmt = con.createStatement();
			log.debug("查看指令表中是否存在生产单元和生产日期、班次的指令"+dao_Instruction.getInstructionByProduceUnitProduceDateWorkorder(int_produnitid, str_date,workOrder));
			ResultSet rs = stmt.executeQuery(dao_Instruction.getInstructionByProduceUnitProduceDateWorkorder(int_produnitid, str_date,workOrder));
			boolean f=false;
			if(rs.next()){
				f=true;
			}
			if (stmt != null) {
				stmt.close();
			}
	        return f;
	   }
	   /**
	    * * 查看生产单元和生产日期的主物料值
	    * 用于指令导入时判断
	    * 谢静天
	    * @param produceid
	    * @param str_date
	    * @param con
	    * @return
	    * @throws SQLException
	    * @throws ParseException
	    */

	   public  List<Instruction> getStr_produceMarkerbyproduceUnitandproduceDate(int produceid,String str_date,Connection con) throws SQLException ,ParseException{
	       List<Instruction> list = new ArrayList<Instruction>();
	       DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
	               DataBaseType.getDataBaseType(con),DAO_Instruction.class);
			Statement stmt = con.createStatement();
			log.debug("查看生产单元和生产日期的主物料值"+dao_Instruction.getStr_produceMarkerbyproduceUnitandproduceDate(produceid, str_date));
			ResultSet rs = stmt.executeQuery(dao_Instruction.getStr_produceMarkerbyproduceUnitandproduceDate(produceid, str_date));
	        while(rs.next()){
	        	 Instruction instruction = new Instruction();
		         instruction.setProdunitid(rs.getInt("INT_PRODUNITID"));
		         instruction.setProduceDate(rs.getDate("DAT_PRODUCEDATE"));
		         instruction.setVersionCode(rs.getString("STR_VERSIONCODE"));
		         instruction.setInstructionOrder(rs.getInt("INT_INSTRUCTORDER"));
		         instruction.setPlanDate(rs.getDate("DAT_PLANDATE"));
		         instruction.setPlanOrder(rs.getInt("INT_PLANORDER"));
		         instruction.setProduceType(rs.getString("STR_PRODUCETYPE"));
		         instruction.setProduceName(rs.getString("STR_PRODUCENAME"));
		         instruction.setProduceMarker(rs.getString("STR_PRODUCEMARKER"));
		         instruction.setWorkOrder(rs.getString("STR_WORKORDER"));
		         instruction.setWorkTeam(rs.getString("STR_WORKTEAM"));
		         instruction.setPlanBegin(rs.getDate("TIM_PLANBEGIN"));
		         instruction.setPlanFinish(rs.getDate("TIM_PLANFINISH"));
		         instruction.setCount(rs.getInt("INT_COUNT"));
		         instruction.setInstructStateID(rs.getInt("INT_INSTRUCTSTATEID"));
	            list.add(instruction);
	        }
			if (stmt != null) {
				stmt.close();
			}
	        return list;
	   }
	   /**
	    * 谢静天 查看指令历史表中是否使用生产单元
	    * 用于判断是否可以删除生产单元
	    * @param produceid
	    * @param con
	    * @return
	    * @throws SQLException
	    * @throws ParseException
	    */
	 public boolean checkproduceandproducedateWorkorder(int produceid,String workOrder,Connection con)throws SQLException ,ParseException{
	     
		 Calendar calendar=Calendar.getInstance();
	     calendar.setTime(new Date());
	     int hour=calendar.get(Calendar.HOUR_OF_DAY);
	     String year=String.valueOf(calendar.get(Calendar.YEAR));
	     String month=String.valueOf(calendar.get(Calendar.MONDAY)+1);
	      if(calendar.get(Calendar.MONDAY)+1<10){
	      	month="0"+month;
	      }
	     String day=null;
	     String str_date=null;
	     if(hour>=8)
	     {
	  	   day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
	  	   if(calendar.get(Calendar.DAY_OF_MONTH)<10){
	  		   day="0"+day;
	  	   }
	  	   str_date=year+"-"+month+"-"+day;
	     }
	     else
	     { 
	  	   day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)-1);
	  	   str_date=year+"-"+month+"-"+day;
	     }
	 
	     DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
	             DataBaseType.getDataBaseType(con),DAO_Instruction.class);
			Statement stmt = con.createStatement();
			log.debug("查看指令历史表中是否使用生产单元"+dao_Instruction.getInstructionByProduceUnitProduceDateWorkorder(produceid, str_date,workOrder));
			ResultSet rs = stmt.executeQuery(dao_Instruction.getInstructionByProduceUnitProduceDateWorkorder(produceid, str_date,workOrder));
			boolean f=false;
			if(rs.next()){
				f=true;
			}
			if (stmt != null) {
				stmt.close();
			}
	      return f;
	 } 
	  /**
	    * 谢静天 查看指令表中是否使用生产单元
	    * 用于判断是否可以删除生产单元
	    * @param produceid
	    * @param con
	    * @return
	    * @throws SQLException
	    * @throws ParseException
	    */
	 public boolean checkproduceandproducedate(int produceid,Connection con)throws SQLException ,ParseException{
	     
		 Calendar calendar=Calendar.getInstance();
	     calendar.setTime(new Date());
	     int hour=calendar.get(Calendar.HOUR_OF_DAY);
	     String year=String.valueOf(calendar.get(Calendar.YEAR));
	     String month=String.valueOf(calendar.get(Calendar.MONDAY)+1);
	      if(calendar.get(Calendar.MONDAY)+1<10){
	      	month="0"+month;
	      }
	     String day=null;
	     String str_date=null;
	     if(hour>=8)
	     {
	  	   day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
	  	   if(calendar.get(Calendar.DAY_OF_MONTH)<10){
	  		   day="0"+day;
	  	   }
	  	   str_date=year+"-"+month+"-"+day;
	     }
	     else
	     { 
	  	   day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)-1);
	  	   str_date=year+"-"+month+"-"+day;
	     }
	 
	     DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
	             DataBaseType.getDataBaseType(con),DAO_Instruction.class);
			Statement stmt = con.createStatement();
			log.debug("查看指令历史表中是否使用生产单元"+dao_Instruction.getInstructionByProduceUnitProduceDate(produceid, str_date));
			ResultSet rs = stmt.executeQuery(dao_Instruction.getInstructionByProduceUnitProduceDate(produceid, str_date));
			boolean f=false;
			if(rs.next()){
				f=true;
			}
			if (stmt != null) {
				stmt.close();
			}
	    
	      return f;
	 } 
	 /**
	  * 
	  *  谢静天
	  *  查看指令表中的相关生产单元和生产日期的指令顺序号
	  * @param int_produnitid
	  * @param str_date
	  * @param con
	  * @return
	  * @throws SQLException
	  * @throws ParseException
	  */
	 public  List<String > getallInstructionorder(int int_produnitid,String str_date,String workOrder,Connection con) throws SQLException ,ParseException{
	     List<String> list = new ArrayList<String>();
	     DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
	             DataBaseType.getDataBaseType(con),DAO_Instruction.class);
			Statement stmt = con.createStatement();
			log.debug("查看指令表中的相关生产单元和生产日期的指令顺序号"+dao_Instruction.checkAllInstructionByProdunitidDateWorkorder(int_produnitid, str_date,workOrder));
			ResultSet rs = stmt.executeQuery(dao_Instruction.checkAllInstructionByProdunitidDateWorkorder(int_produnitid, str_date,workOrder));
		    int n=0;
	      while(rs.next()){
	    	  n=rs.getInt("int_instructorder");
	    	  list.add(String.valueOf(n));
	      }
		if (stmt != null) {
			stmt.close();
		}
	      return list;
	   }
	 /**
	  *  * 谢静天
	  * 生产单元和生产时间
	  * 通过主物料的值查看指令是否处于编辑状态。
	  * @param producemarker
	  * @param int_produnitid
	  * @param con
	  * @return
	  * @throws SQLException
	  * @throws ParseException
	  */
	  public  boolean checkinstructionedit(String producemarker,int int_produnitid,Connection con) throws SQLException ,ParseException{
		  Calendar calendar=Calendar.getInstance();
		     calendar.setTime(new Date());
		     int hour=calendar.get(Calendar.HOUR_OF_DAY);
		     String year=String.valueOf(calendar.get(Calendar.YEAR));
		     String month=String.valueOf(calendar.get(Calendar.MONDAY)+1);
		      if(calendar.get(Calendar.MONDAY)+1<10){
		      	month="0"+month;
		      }
		     String day=null;
		     String str_date=null;
		     if(hour>=8)
		     {
		  	   day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		  	   if(calendar.get(Calendar.DAY_OF_MONTH)<10){
		  		   day="0"+day;
		  	   }
		  	   str_date=year+"-"+month+"-"+day;
		     }
		     else
		     { 
		  	   day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)-1);
		  	   str_date=year+"-"+month+"-"+day;
		     }
		 
		     DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
		             DataBaseType.getDataBaseType(con),DAO_Instruction.class);
		  
				Statement stmt = con.createStatement();
				log.debug("生产单元和生产时间"+dao_Instruction.checkinstructionedit(producemarker, int_produnitid, str_date));
				ResultSet rs = stmt.executeQuery(dao_Instruction.checkinstructionedit(producemarker, int_produnitid, str_date));
				boolean f=false;
				if(rs.next()){
					if(rs.getInt("int_edit")==1){
					f=true;
					}
				}
				if (stmt != null) {
					stmt.close();
				}
		   
		      return f;
	  }
	  
	  /**
		  * 查看指令表中的相关生产单元的指令对象
		  * 
		  * @param int_produnitid
		  * @param con
		  * @return
		  * @throws SQLException
		  * @throws ParseException
		  */
		 public  List<Instruction > getInstructionsbyproduceUnit(int int_produnitid,Connection con) throws SQLException ,ParseException{
		     List<Instruction> list = new ArrayList<Instruction>();
		     DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
		             DataBaseType.getDataBaseType(con),DAO_Instruction.class);
				Statement stmt = con.createStatement();
				log.debug("查看指令表中的相关生产单元的指令对象"+dao_Instruction.getInstructionsbyproduceUnit(int_produnitid));
				ResultSet rs = stmt.executeQuery(dao_Instruction.getInstructionsbyproduceUnit(int_produnitid));
			    while(rs.next()){
		        	 Instruction instruction = new Instruction();
			         instruction.setProdunitid(rs.getInt("INT_PRODUNITID"));
			         instruction.setProduceDate(rs.getDate("DAT_PRODUCEDATE"));
			         instruction.setVersionCode(rs.getString("STR_VERSIONCODE"));
			         instruction.setInstructionOrder(rs.getInt("INT_INSTRUCTORDER"));
			         instruction.setPlanDate(rs.getDate("DAT_PLANDATE"));
			         instruction.setPlanOrder(rs.getInt("INT_PLANORDER"));
			         instruction.setProduceType(rs.getString("STR_PRODUCETYPE"));
			         instruction.setProduceName(rs.getString("STR_PRODUCENAME"));
			         instruction.setProduceMarker(rs.getString("STR_PRODUCEMARKER"));
			         instruction.setWorkOrder(rs.getString("STR_WORKORDER"));
			         instruction.setWorkTeam(rs.getString("STR_WORKTEAM"));
			         instruction.setPlanBegin(rs.getDate("TIM_PLANBEGIN"));
			         instruction.setPlanFinish(rs.getDate("TIM_PLANFINISH"));
			         instruction.setCount(rs.getInt("INT_COUNT"));
			         instruction.setInstructStateID(rs.getInt("INT_INSTRUCTSTATEID"));
		            list.add(instruction);
		        }
			if (stmt != null) {
				stmt.close();
			}
		      return list;
		   }
		 
		 
		 /**
		  * 查看指令表中的该生产单元和不同生产日期的指令对象
		  *  
		  * @param int_produnitid
		  * @param str_date
		  * @param con
		  * @return
		  * @throws SQLException
		  * @throws ParseException
		  */
		 public  List<Instruction > getInstructionsByProdunitOtherProdDate(int int_produnitid,String str_date,Connection con) throws SQLException ,ParseException{
		     List<Instruction> list = new ArrayList<Instruction>();
		     DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
		             DataBaseType.getDataBaseType(con),DAO_Instruction.class);
				Statement stmt = con.createStatement();
				log.debug("查看指令表中的该生产单元和不同生产日期的指令对象"+dao_Instruction.getInstructionsByProdunitOtherProdDate(int_produnitid, str_date));
				ResultSet rs = stmt.executeQuery(dao_Instruction.getInstructionsByProdunitOtherProdDate(int_produnitid, str_date));
		      while(rs.next()){
		        	 Instruction instruction = new Instruction();
			         instruction.setProdunitid(rs.getInt("INT_PRODUNITID"));
			         instruction.setProduceDate(rs.getDate("DAT_PRODUCEDATE"));
			         instruction.setVersionCode(rs.getString("STR_VERSIONCODE"));
			         instruction.setInstructionOrder(rs.getInt("INT_INSTRUCTORDER"));
			         instruction.setPlanDate(rs.getDate("DAT_PLANDATE"));
			         instruction.setPlanOrder(rs.getInt("INT_PLANORDER"));
			         instruction.setProduceType(rs.getString("STR_PRODUCETYPE"));
			         instruction.setProduceName(rs.getString("STR_PRODUCENAME"));
			         instruction.setProduceMarker(rs.getString("STR_PRODUCEMARKER"));
			         instruction.setWorkOrder(rs.getString("STR_WORKORDER"));
			         instruction.setWorkTeam(rs.getString("STR_WORKTEAM"));
			         instruction.setPlanBegin(rs.getDate("TIM_PLANBEGIN"));
			         instruction.setPlanFinish(rs.getDate("TIM_PLANFINISH"));
			         instruction.setCount(rs.getInt("INT_COUNT"));
			         instruction.setInstructStateID(rs.getInt("INT_INSTRUCTSTATEID"));
			         list.add(instruction);
		      }
			if (stmt != null) {
				stmt.close();
			}
		      return list;
		   }

		 /**得到指令表中所有的指令 谢静天
		  * 
		  * @param con
		  * @return
		  * @throws SQLException
		  * @throws ParseException
		  */
		public List<Instruction > getallInstruction(Connection con) throws SQLException ,ParseException{
			List<Instruction> list = new ArrayList<Instruction>();
			DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
			DataBaseType.getDataBaseType(con),DAO_Instruction.class);
			Statement stmt = con.createStatement();
			log.debug("得到指令表中所有的指令 "+dao_Instruction.getallInstruction());
			ResultSet rs = stmt.executeQuery(dao_Instruction.getallInstruction());
			while(rs.next()){
				Instruction instruction = new Instruction();
				instruction.setProdunitid(rs.getInt("INT_PRODUNITID"));
				instruction.setProduceDate(rs.getDate("DAT_PRODUCEDATE"));
				instruction.setVersionCode(rs.getString("STR_VERSIONCODE"));
				instruction.setInstructionOrder(rs.getInt("INT_INSTRUCTORDER"));
				instruction.setPlanDate(rs.getDate("DAT_PLANDATE"));
				instruction.setPlanOrder(rs.getInt("INT_PLANORDER"));
				instruction.setProduceType(rs.getString("STR_PRODUCETYPE"));
				instruction.setProduceName(rs.getString("STR_PRODUCENAME"));
				instruction.setProduceMarker(rs.getString("STR_PRODUCEMARKER"));
				instruction.setWorkOrder(rs.getString("STR_WORKORDER"));
				instruction.setWorkTeam(rs.getString("STR_WORKTEAM"));
				instruction.setPlanBegin(rs.getDate("TIM_PLANBEGIN"));
				instruction.setPlanFinish(rs.getDate("TIM_PLANFINISH"));
				instruction.setCount(rs.getInt("INT_COUNT"));
				instruction.setInstructStateID(rs.getInt("INT_INSTRUCTSTATEID"));
				list.add(instruction);
			}
			if (stmt != null) {
				stmt.close();
			}
			return list; 
							 
		}
		
		/** 查找指定主物料的指令 谢静天
		* 
		* @param materile
		* @param produceid
		* @param con
		* @return
		* @throws SQLException
		* @throws ParseException
		*/
		public Instruction getInstructionbymaterile( String materile,int produceid,Connection con) throws SQLException ,ParseException{
			Instruction instruction = new Instruction();
			DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
			DataBaseType.getDataBaseType(con),DAO_Instruction.class);
			Statement stmt = con.createStatement();
			log.debug("查找指定主物料的指令  "+dao_Instruction.getInstructionbymaterile(materile,produceid));
			ResultSet rs = stmt.executeQuery(dao_Instruction.getInstructionbymaterile(materile,produceid));
			if(rs.next()){
				instruction.setId(rs.getInt("INT_ID"));
				instruction.setProdunitid(rs.getInt("INT_PRODUNITID"));
				instruction.setProduceDate(rs.getDate("DAT_PRODUCEDATE"));
				instruction.setVersionCode(rs.getString("STR_VERSIONCODE"));
				instruction.setInstructionOrder(rs.getInt("INT_INSTRUCTORDER"));
				instruction.setPlanDate(rs.getDate("DAT_PLANDATE"));
				instruction.setPlanOrder(rs.getInt("INT_PLANORDER"));
				instruction.setProduceType(rs.getString("STR_PRODUCETYPE"));
				instruction.setProduceName(rs.getString("STR_PRODUCENAME"));
				instruction.setProduceMarker(rs.getString("STR_PRODUCEMARKER"));
				instruction.setWorkOrder(rs.getString("STR_WORKORDER"));
				instruction.setWorkTeam(rs.getString("STR_WORKTEAM"));
				instruction.setPlanBegin(rs.getDate("TIM_PLANBEGIN"));
				instruction.setPlanFinish(rs.getDate("TIM_PLANFINISH"));
				instruction.setCount(rs.getInt("INT_COUNT"));
				instruction.setInstructStateID(rs.getInt("INT_INSTRUCTSTATEID"));
					           
			}
			if (stmt != null) {
				stmt.close();
			}
			return instruction; 
		}
		/** 更改指令，当调度插单时用到计划的vin时调用
		* @param instruction
		* @param con
		* @throws SQLException
		*/
		public void updateInstruction(Instruction instruction,Connection con) throws SQLException {	
			  DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
			             DataBaseType.getDataBaseType(con),DAO_Instruction.class);
					Statement stmt = con.createStatement();
					stmt.executeUpdate(dao_Instruction.updateInstruction(instruction));

					if (stmt != null) {
						stmt.close();
					}
		}
				  
	
		/** 包金旭
		 * 通过生产单元，班次，日期删除指令表
		 * @param int_produnitid
		 * @param str_date
		 * @param workOrder
		 * @param con
		 * @throws SQLException 
		 */
		public void deleteAllInstructionByProdunitidDateWorkorder(int int_produnitid,String str_date,String workOrder,Connection con) throws SQLException {
			DAO_Instruction dao_instruction = (DAO_Instruction) DAOFactoryAdapter.getInstance(
					DataBaseType.getDataBaseType(con),DAO_Instruction.class);
			Statement stmt = con.createStatement();
			stmt.execute(dao_instruction.deleteInstructionByProduceUnitProduceDateWorkorder(int_produnitid,str_date,workOrder));
			log.debug("通过通过生产单元，生产日期，班次删除指令表SQL语句："+dao_instruction.deleteInstructionByProduceUnitProduceDateWorkorder(int_produnitid,str_date,workOrder));
			if (stmt != null) {
				stmt.close();
			}
		}
		
		 /**
		  * * 核对指令表中是否有指令用于删除
		    * 包金旭
		    * 根据生产单元，生产日期及班次
		  * @param int_produnitid
		  * @param str_date
		  * @param workOrder
		  * @param con
		  * @return
		  * @throws SQLException
		  * @throws ParseException
		  */
		   public boolean  checkAllInstructionByProdunitidDateWorkorder(int int_produnitid,String str_date,String workOrder,Connection con)throws SQLException ,ParseException{
		       DAO_Instruction dao_Instruction = (DAO_Instruction)DAOFactoryAdapter.getInstance(
		               DataBaseType.getDataBaseType(con),DAO_Instruction.class);
				Statement stmt = con.createStatement();
				log.debug("核对指令表中是否有指令用于删除"+dao_Instruction.checkAllInstructionByProdunitidDateWorkorder(int_produnitid, str_date,workOrder));
				ResultSet rs=stmt.executeQuery(dao_Instruction.checkAllInstructionByProdunitidDateWorkorder(int_produnitid, str_date,workOrder));
				boolean f=false;
				if(rs.next()){
					f=true;
				}
				  if (stmt != null) {
						stmt.close();
					}
					return f;
		   }
		 
		
		
		
		
		
		
}
