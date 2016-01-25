package mes.ra.service.version;

import mes.framework.AdapterService;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.framework.ExecuteResult;
import java.util.*;
import java.sql.*;
import java.util.ArrayList;
import mes.ra.factory.*;
import mes.os.factory.*;
import mes.ra.bean.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**版本恢复
 * @author 谢静天
 *
 */

public class Comeback extends AdapterService {
	private Connection con=null;
	/**
	 * 生产单元id  号
	 */
	private String Str_produceUnit=null ;
	/**
	 * 生产日期
	 */
	private String str_date=null;
	/**
	 * 版本号
	 */
	private String versioncode=null;
	/**
	 * 班次 
	 */
	private String workOrder=null;
	 /**
	 * sqlinstruction 指令预处理语句
	 */
	PreparedStatement sqlinstruction=null;
	 /**
	 * sqlplanall 计划预处理语句
	 */
	PreparedStatement sqlplanall=null;
	private final Log log = LogFactory.getLog(Comeback.class);
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		Str_produceUnit=message.getUserParameterValue("Str_produceUnit");
		str_date=message.getUserParameterValue("str_date");
	    versioncode=message.getUserParameterValue("versioncode");
	    workOrder=message.getUserParameterValue("workOrder");
	    //输出log信息
		String debug="Str_produceUnit:" + Str_produceUnit 
			+ "versioncode:"+versioncode+ "str_date"+str_date+ "\n";
		    log.info("添加状态转换的参数: " + debug);
		if (str_date== null||Str_produceUnit==null) {
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
		try {
			//预处理语句
			ResultSet rs=null;
			List<Instruction> list_instruction = new ArrayList<Instruction>();
			//删除指令表中与生产单元和生产日期相同的指令
			InstructionFactory instructionfactory=new InstructionFactory();
			ProduceUnitFactory produceunitfactory=new ProduceUnitFactory();
			WorkSchedleFactory workschedlefactory=new WorkSchedleFactory();
			ProduceUnit produceuint=new ProduceUnit();
			produceuint=produceunitfactory.getProduceUnitbyId(Integer.parseInt(Str_produceUnit), con);
			InstructionHistoryFactory instructionhistoryfactory=new InstructionHistoryFactory();
			String str_versioncode="";
			String first=null;
			list_instruction=instructionfactory.getInstructionByProduceUnitProduceDateWorkorder(Integer.parseInt(Str_produceUnit), str_date,workOrder, con);
			if(list_instruction.size()!=0){
				Instruction instruction1=(Instruction)list_instruction.get(0);
				str_versioncode=instruction1.getVersionCode();
			}
			else{
				str_versioncode=instructionhistoryfactory.checkcodebyproduceunitanddateWorkorder(Integer.parseInt(Str_produceUnit), str_date, workOrder, con);
				if (!str_versioncode.equals("") && str_versioncode != null) {
					int leng = str_versioncode.length();
					// 十位
					String code1 = str_versioncode.substring(leng - 2,
							leng - 1);
					// 个位
					String code2 = str_versioncode.substring(leng - 1, leng);
					int gewei = Integer.parseInt(code2) + 1;
					if (gewei <= 9) {
						first = code1 + String.valueOf(gewei);
					} else {
						int shiwei = Integer.parseInt(code1) + 1;
						first = String.valueOf(shiwei) + "0";
					}
				}
				String[] name = new String[3];
				name = str_date.split("-");
				String namess = name[0] + name[1] + name[2];
				str_versioncode = namess + produceuint.getStr_name() + workOrder + first;
			}
			 
			list_instruction.clear();

			//循环把list中的指令添加到指令表中
			list_instruction=instructionhistoryfactory.getInstructionbyversioncode(versioncode, con);
			String s="(";
			for(int i=0;i<list_instruction.size();i++){
				if(i==list_instruction.size()-1){
					s=s+"?";
				}
				else 
					s=s+"?,";
			}
			s=s+")";
			
			String sql="select * from t_ra_instruction where  int_delete=0   and str_producemarker in"+s+" and str_versioncode in(select "
					+"max(str_versioncode) from t_ra_instruction where int_produnitid="+Integer.parseInt(Str_produceUnit)+"   group by  int_produnitid ,dat_producedate,str_workOrder) and str_versioncode"
					+"<>'"+str_versioncode+"' ";
			sqlinstruction=con.prepareStatement(sql);
			
			for(int i=0;i<list_instruction.size();i++){
				Instruction instruction=(Instruction)list_instruction.get(i);
				sqlinstruction.setString(i+1, instruction.getProduceMarker());
			}
			rs=sqlinstruction.executeQuery();
			if(rs.next()){
				message.addServiceException(new ServiceException(
						ServiceExceptionType.PARAMETERLOST, "主物料值有重复不能进行版本恢复", this.getId(),
						processid, new java.util.Date(), null));
				return ExecuteResult.fail;
			}
			else{
				instructionfactory.deleteInstructionByProduceUnitProduceDateWorkorder(Integer.parseInt(Str_produceUnit), str_date, workOrder,con);
				String  sqlplanstring="select p.*,to_char(p.Dat_produceDate,'yyyy-MM-dd') as producedate from t_os_plan p where str_versioncode in(select max(str_versioncode) from t_os_plan  where int_upload=1  and int_produnitid="+Integer.parseInt(Str_produceUnit)+" group by int_produnitid,Dat_produceDate,str_workOrder "
		            +") and  str_producemarker=? ";
				
				sqlplanall =con.prepareStatement(sqlplanstring);
				for(int i=0;i<list_instruction.size();i++){
					Instruction instruction=(Instruction)list_instruction.get(i);
					//如果计划信息为空 按照主物料值看计划中是否有最大版本且事实锁定的计划与它相关。
					if(instruction.getPlanOrder()==0||instruction.getPlanDate()==null){
						sqlplanall.setString(1,instruction.getProduceMarker());
						ResultSet rsprepare= sqlplanall.executeQuery();
						if(rsprepare.next()){
							//看是否事实锁定 				 
							long locked= workschedlefactory.getworkschedleadtime(rsprepare.getInt("int_produnitid"),rsprepare.getString("producedate"),rsprepare.getString("str_workorder"),con);
							if(locked==0){
								//如果有相同的主物料并且事实锁定则补全相关的计划信息
								instruction.setPlanDate(rsprepare.getDate("dat_planDate")); 
								instruction.setPlanOrder(rsprepare.getInt("int_planOrder"));
							}
						}
					}
					instruction.setVersionCode(str_versioncode);
					instructionfactory.saveInstruction(instruction, con);
				}
			}
		} catch (SQLException sqle) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
							.getId(), processid, new java.util.Date(), sqle));
			log.error("版本恢复时数据库异常"	+ sqle.toString());
			return ExecuteResult.fail;
		}
	} catch (Exception e) {
		message.addServiceException(new ServiceException(
				ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
				processid, new java.util.Date(), e));
		log.error("版本恢复时数据库未知异常"	+ e.toString());
		return ExecuteResult.fail;
	}
	return ExecuteResult.sucess;
	}

	public void relesase() throws SQLException {
	   	     con = null;
	   	      //生产单元
	   		 Str_produceUnit=null ;
	   		 //生产日期
	   		 str_date=null;
	   		 workOrder=null;
	   		 if(sqlinstruction!=null)
	   		 sqlinstruction.close();
	   		 if(sqlplanall!=null)
	         sqlplanall.close();
	   }
}
