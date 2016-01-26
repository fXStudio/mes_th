package com.qm.mes.ra.service.instruction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.qm.mes.framework.AdapterService;
import com.qm.mes.framework.DataBaseType;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.ServiceException;
import com.qm.mes.framework.ServiceExceptionType;
import com.qm.mes.framework.dao.DAOFactory_UserManager;
import com.qm.mes.framework.dao.IDAO_UserManager;
import com.qm.mes.os.bean.Plan;
import com.qm.mes.os.factory.PlanFactory;
import com.qm.mes.ra.bean.Instruction;
import com.qm.mes.ra.bean.ProduceUnit;
import com.qm.mes.ra.bean.Version;
import com.qm.mes.ra.factory.InstructionFactory;
import com.qm.mes.ra.factory.InstructionHistoryFactory;
import com.qm.mes.ra.factory.InstructionVersionFactory;
import com.qm.mes.ra.factory.ProduceUnitFactory;

/**计划生成指令
 * @author 谢静天
 *
 */
public class PlanToInstruction extends AdapterService {
	/**
	 * 获得连接
	 */
	private Connection con=null;
	private  Statement stmt=null;
	/**
	 * 生产单元号
	 */
	private String produnitid =null;
	/**
	 * 生产日期
	 */
	private String overtime=null;
	/**
	 * 班次
	 */
	private String workOrder=null;
	/**
	 * 用户序号
	 */
	private String userid=null;
	
	private PlanFactory planfactory=new PlanFactory();
	private InstructionHistoryFactory historyfactory=new InstructionHistoryFactory();
	private InstructionVersionFactory versionfactory=new InstructionVersionFactory();
	
	public boolean checkParameter(IMessage message, String processid) {
		con=(Connection)message.getOtherParameter("con");
		produnitid=message.getUserParameterValue("produceided");
		overtime=message.getUserParameterValue("overtime");
		workOrder=message.getUserParameterValue("workOrder");
		userid=message.getUserParameterValue("userid");
	
		
		if (produnitid== null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			return false;
		}
		return true;
		
	}
	public ExecuteResult doAdapterService(IMessage message, String processid)
	throws SQLException, Exception {
		try {
			//从计划生成指令就是替换直接删除指令表中 生产单元，班次和生产日期的指令。
			InstructionFactory instructionfactory=new InstructionFactory();
			 //预处理语句
			PreparedStatement sqlinstruction=null;
			//根据生产时间和生产单元删除指令
			instructionfactory.deleteInstructionByProduceUnitProduceDateWorkorder(Integer.parseInt(produnitid), overtime,workOrder, con);
			ProduceUnitFactory producefactory=new  ProduceUnitFactory();
			//通过生产单元的id查到生产单元的初始指令状态
			int instructstateid= producefactory.getInstructionstateIdByproduceunitid(Integer.parseInt(produnitid), con);//获取生产单元的状态
			List<Plan> planlist=new ArrayList<Plan>();
			String first="01"; 
			String second=null;
			String str_versioncodeinstruction=null;
			String str_versioncode=null;
			//获取历史表中 版本号 查找最大的版本号
			String code= historyfactory.checkcodebyproduceunitanddateWorkorder(Integer.parseInt(produnitid), overtime,workOrder, con);
			if(!code.equals("")){
				int leng=code.length();
				//十位
		  	    String code1=code.substring(leng-2,leng-1);
				//个位
				String code2=code.substring(leng-1,leng);
				int gewei=Integer.parseInt(code2)+1;
				if(gewei<=9){
					first=code1+String.valueOf(gewei);
				}
				else{
					int shiwei=Integer.parseInt(code1)+1;
				    first=String.valueOf(shiwei)+"0";
				}
		
		    }
			if(Integer.parseInt(first)+1<=9){
	    		second="0"+String.valueOf((Integer.parseInt(first)+1));
	    	}
	    	else{
	    		second=String.valueOf((Integer.parseInt(first)+1));
	    	}
			//获取用户的名
			String username=null;
      		int  userId = Integer.parseInt(userid);
   	        IDAO_UserManager dao_UserManager = DAOFactory_UserManager.getInstance(DataBaseType.getDataBaseType(con));
   	        String sql = dao_UserManager.getSQL_SelectUserById(userId);
   	        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
   	        ResultSet rs = stmt.executeQuery(sql);
   	        if(rs.next()){
   	        	username = rs.getString("CUSRNAME");
   	        }
   	        else{
   	        	username = null;
   	        }
 
			ProduceUnit produceunit=new ProduceUnit();
		    // 通过生产单元id获取生产单元名同时生成版本号
            produceunit=producefactory.getProduceUnitbyId(Integer.parseInt(produnitid), con);
		    String produce_name=produceunit.getStr_name();
		    String [] date=new String [3];
		    date =overtime.split("-");
	        String producedate= date[0]+date[1]+date[2];
	        str_versioncode=producedate+produce_name+workOrder+first;//当前指令表中的版本号
	        str_versioncodeinstruction=producedate+produce_name+workOrder+second;//要生成的版本号
			planlist=planfactory.getmaxPlanexcepttempupload(overtime, Integer.parseInt(produnitid), workOrder, con);
		    Iterator<Plan> iter=planlist.iterator();
		    String sqlinstructionstring="select st.*,to_char(st.dat_producedate,'yyyy-MM-dd') as producedate from t_ra_instruction st  where str_produceMarker=? and int_produnitid="+Integer.parseInt(produnitid)+" and int_delete=0 ";
		    sqlinstruction=con.prepareStatement(sqlinstructionstring);
		    int i=0;
		    while(iter.hasNext()){
		    	Plan plan=(Plan)iter.next();
		    	sqlinstruction.setString(1, plan.getProduceMarker());
				rs=sqlinstruction.executeQuery();
				if(rs.next())//如果指令表中由此主物料的指令则补全指令的信息。
				{
					 i++;
					 message.setOutputParameter("yes"+i,produceunit.getStr_name()+" "+rs.getString("producedate")+" "+rs.getString("str_workOrder")+" "+rs.getString("str_versioncode")+" "+"版本的指令中已经使用了此主物料值:"+plan.getProduceMarker());
					 Instruction instruction=new Instruction();
					 instruction.setId(rs.getInt(1));
					 instruction.setProdunitid(rs.getInt(2));
					 instruction.setProduceDate(rs.getDate(3));
					 instruction.setVersionCode(rs.getString(4));
					 instruction.setInstructionOrder(rs.getInt(5));
					 instruction.setPlanDate(plan.getPlanDate());
					 instruction.setPlanOrder(plan.getPlanOrder());
					 instruction.setProduceType(rs.getString(8));
					 instruction.setProduceName(rs.getString(9));
					 instruction.setProduceMarker(rs.getString(10));
					 instruction.setWorkOrder(rs.getString(11));
					 instruction.setWorkTeam(rs.getString(12));
					 instruction.setPlanBegin(rs.getString(13)==null?null:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString(13)));
					 instruction.setPlanFinish(rs.getString(14)==null?null:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString(14)));
					 instruction.setCount(rs.getInt(15));
					 instruction.setInstructStateID(rs.getInt(16));
					 instruction.setIssuance(rs.getInt(17));
					 instruction.setStaError(rs.getInt(18));
					 instruction.setDelete(rs.getInt(19));
					 instruction.setEdit(rs.getInt(20));
					 instructionfactory.updateInstruction(instruction, con);
					 continue;
				}//如果没有则按照计划生成指令
		    	Instruction instruction=new Instruction();
		    	instruction.setProdunitid(Integer.parseInt(produnitid));//生产单元
		    	instruction.setProduceDate(new SimpleDateFormat("yyyy-MM-dd").parse(overtime));//生产日期
		    	instruction.setVersionCode(str_versioncodeinstruction);//版本号
		    	instruction.setInstructionOrder(plan.getPlanOrder());//指令顺序号
		    	instruction.setPlanDate(plan.getPlanDate());//计划日期
		    	instruction.setPlanOrder(plan.getPlanOrder());//计划顺序号
		    	instruction.setProduceType(plan.getProduceType());//产品类别标识
		    	instruction.setProduceName(plan.getProduceName());//产品名称
		    	instruction.setProduceMarker(plan.getProduceMarker());//产品标识
		    	instruction.setWorkOrder(workOrder);//班次
		    	instruction.setWorkTeam(plan.getWorkTeam());//班组
		    	instruction.setPlanBegin(null);
		    	instruction.setPlanFinish(null);
		    	instruction.setCount(plan.getAmount());//数量
		    	instruction.setInstructStateID(instructstateid);//指令状态
		    	instruction.setIssuance(0);//发布标识
		    	instruction.setStaError(0);//异常
		    	instruction.setDelete(0);//删除标识
		    	instruction.setEdit(0);//编辑标识
		    	instructionfactory.saveInstruction(instruction, con);//生成指令
		    	historyfactory.saveInstruction(instruction, str_versioncode, con);//生成指令历史
		    }
		     message.setOutputParameter("num", String.valueOf(i));
		    //生成版本
		    if(planlist.size()!=i){
		    	Version version=new Version();
		    	version.setInt_delete(0);//删除标识
		    	version.setProdunitid(Integer.parseInt(produnitid));//生产单元 
		    	version.setVersionCode(str_versioncode);//版本号
		    	version.setUser(username);
		    	versionfactory.saveVersion(version, con);
		    }
	    }
	    catch (SQLException sqle) {
		message.addServiceException(new ServiceException(
				ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
						.getId(), processid, new Date(), sqle));
		return ExecuteResult.fail;
	    }
	     catch (Exception e) {
	    message.addServiceException(new ServiceException(
			ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
			processid, new java.util.Date(), e));
	      return ExecuteResult.fail;
	    }
	    return ExecuteResult.sucess;
	   }

    public void relesase() throws SQLException {
        con = null;
        produnitid =null;
    	overtime=null;
        workOrder=null;
      if(stmt!=null){
    	  stmt.close();
      }
      

     }
	
	
	

}
