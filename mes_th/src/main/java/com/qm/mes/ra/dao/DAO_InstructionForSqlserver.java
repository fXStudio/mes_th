/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.qm.mes.ra.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.qm.mes.ra.bean.Instruction;

/**
 * 
 * @author YuanPeng
 */
public class DAO_InstructionForSqlserver extends DAO_InstructionForOracle {

	/**
	 * 袁鹏 对指令进行更新操作的sql语句
	 * 
	 * @param instruction
	 * @return 更新T_RA_INSTRUCTION中该ID的记录
	 */
	public String updateInstruction(Instruction instruction) {
		String a = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
				.format(instruction.getProduceDate());
		String sql = "update T_RA_INSTRUCTION set Int_produnitid = "
				+ instruction.getProdunitid()
				+ " , Dat_produceDate = '"
				+ a
				+ "' , Str_versionCode = '"
				+ instruction.getVersionCode()
				+ "' , Int_instructOrder = "
				+ instruction.getInstructionOrder()
				+ " , Dat_planDate = "
				+ (instruction.getPlanDate() == null
						|| instruction.getPlanDate().toString().equals("null") ? "''"
						: "convert(datetime,'" + instruction.getPlanDate()
								+ "',20)")
				+ " , Int_planOrder = "
				+ instruction.getPlanOrder()
				+ " , Str_produceType = '"
				+ instruction.getProduceType()
				+ "' , Str_produceName = '"
				+ instruction.getProduceName()
				+ "' , Str_produceMarker = '"
				+ instruction.getProduceMarker()
				+ "' , Str_workOrder = '"
				+ instruction.getWorkOrder()
				+ "' , Str_workTeam = '"
				+ instruction.getWorkTeam()
				+ "' , Tim_planBegin = "
				+ (instruction.getPlanBegin() == null
						|| instruction.getPlanBegin().toString().equals("null") ? "''"
						: "convert(datetime,'" + instruction.getPlanBegin()
								+ "',20)")
				+ " , Tim_planFinish = "
				+ (instruction.getPlanFinish() == null
						|| instruction.getPlanFinish().toString()
								.equals("null") ? "''" : "convert(datetime,'"
						+ instruction.getPlanBegin() + "',20)")
				+ " , Int_count = " + instruction.getCount()
				+ " , Int_instructStateID = "
				+ instruction.getInstructStateID() + " , Int_issuance = "
				+ instruction.getIssuance() + " , Int_StaError = "
				+ instruction.getStaError() + " , Int_delete = "
				+ instruction.getDelete() + " where int_id = "
				+ instruction.getId();
		return sql;

	}

	/**
	 * 袁鹏 查找比该顺序号小的顺序的指令
	 * 
	 * @param ProduceUnitID
	 *            生产单元
	 * @param str_date
	 *            生产日期
	 * @param Int_instructOrder
	 *            指令顺序
	 * @return 比该顺序号小的顺序的指令
	 */
	public String getByOrderMinus(int ProduceUnitID, String str_date,String workOrder,
			int Int_instructOrder) {
		String sql = "select * from T_RA_INSTRUCTION where int_instructorder <="
				+ Int_instructOrder
				+ " and Int_produnitid = "
				+ ProduceUnitID
				+ " and str_workOrder='"+workOrder+"' and convert(datetime,Dat_produceDate,20)=convert(datetime,'"
				+ str_date
				+ "',20) and int_delete<>1 order by int_instructorder desc";
		return sql;
	}

	/**
	 * 袁鹏 查找比该顺序号大的顺序的指令
	 * 
	 * @param ProduceUnitID
	 *            生产单元
	 * @param str_date
	 *            生产日期
	 * @param Int_instructOrder
	 *            指令顺序
	 * @return 比该顺序号小的顺序的指令
	 */
	public String getByOrderPlus(int ProduceUnitID, String str_date,String workOrder,
			int Int_instructOrder) {
		String sql = "select * from T_RA_INSTRUCTION where int_instructorder >="
				+ Int_instructOrder
				+ " and Int_produnitid = "
				+ ProduceUnitID
				+ " and str_workorder ='"
				+ workOrder
				+ "' and convert(datetime,Dat_produceDate,20)=convert(datetime,'"
				+ str_date
				+ "',20) and int_delete<>1 order by int_instructorder";
		return sql;
	}

	/**
	 * 通过生产单元号、生产日期、状态号查询作业指令 袁鹏
	 * 
	 * @param Int_produnitid
	 *            生产单元号
	 * @param str_date
	 *            生产日期
	 * @param stateid
	 *            状态号
	 * @return 通过生产单元号、生产日期、状态号查询的作业指令
	 */
	public String getInstructionByProUnitProDateState(int Int_produnitid,
			String str_date,String  workOrder ,int stateid) {
		String sql = "select * from t_ra_instruction where Int_produnitid = "
				+ Int_produnitid + " and convert(datetime,Dat_produceDate,20)="
				+ "convert(datetime,'" + str_date + "',20)"
				+ " and int_instructstateid = " + stateid
				+ " and str_wrokorder = '" + workOrder
				+ "' and int_delete<>1 order by int_instructorder";
		return sql;
	}

	/**
	 * 创建指令 袁鹏
	 * 
	 * @param instruction
	 * @return
	 */
	public String saveInstruction(Instruction instruction) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				

		String sql = null;
		String sql1 = "insert into T_RA_INSTRUCTION(Int_produnitid,"
				+ "Dat_produceDate,Str_versionCode,Int_instructOrder,Dat_planDate"
				+ ",Int_planOrder,Str_produceType,Str_produceName,Str_produceMarker,"
				+ "Str_workOrder,Str_workTeam,Tim_planBegin,Tim_planFinish,Int_count,"
				+ "Int_instructStateID,Int_issuance,Int_StaError,Int_delete,Int_Edit) "
				+ "values("
				+ instruction.getProdunitid()
				+ ","
				+ "convert(datetime,'"
				+ sdf.format(instruction.getProduceDate())
				+ "',20)"
				+ ","
				+ (instruction.getVersionCode() == null
						|| instruction.getVersionCode().equals("null") ? "''"
						: ("'" + instruction.getVersionCode().toString()) + "'")
				+ ","
				+ instruction.getInstructionOrder()
				+ ","
				+ (instruction.getPlanDate() == null
						|| instruction.getPlanDate().toString().equals("null") ? "''"
						: "convert(datetime,'"
								+ sdf.format(instruction.getPlanDate())
								+ "',20)")
				+ ","
				+ instruction.getPlanOrder()
				+ ","
				+ (instruction.getProduceType() == null
						|| instruction.getProduceType().equals("null") ? ""
						: ("'" + instruction.getProduceType())) + "'" + ",";
		String sql3 = (instruction.getProduceName() == null
				|| instruction.getProduceName().equals("null") ? "" : ("'"
				+ instruction.getProduceName() + "'"))
				+ ","
				+ (instruction.getProduceMarker() == null
						|| instruction.getProduceMarker().equals("null") ? "''"
						: ("'" + instruction.getProduceMarker() + "'"))
				+ ","
				+ (instruction.getWorkOrder() == null
						|| instruction.getWorkOrder().equals("null") ? ""
						: ("'" + instruction.getWorkOrder() + "'"))
				+ ","
				+ (instruction.getWorkTeam() == null
						|| instruction.getWorkTeam().equals("null") ? "" : ("'"
						+ instruction.getWorkTeam() + "'"))
				+ ","
				+ (instruction.getPlanBegin() == null
						|| instruction.getPlanBegin().toString().equals("null") ? null
						: "convert(datetime,'"
								+ sdf.format(instruction.getPlanBegin()).toString()
								+ "',20)") + ",";
		String sql4 = (instruction.getPlanFinish() == null
				|| instruction.getPlanFinish().toString().equals("null") ? "''"
				: "convert(datetime,'" + sdf.format(instruction.getPlanFinish()).toString()
						+ "',20)")
				+ ","
				+ instruction.getCount()
				+ ","
				+ instruction.getInstructStateID()
				+ ","
				+ instruction.getIssuance()
				+ ","
				+ instruction.getStaError()
				+ ","
				+ instruction.getDelete()
				+ ","
				+ instruction.getEdit()
				+ ")";
		sql = sql1 + sql3 + sql4;
		return sql;
	}

	/**
	 * 通过生产单元号、生产日期、顺序号大于首个非编辑号来更改int_delete字段值为1 袁鹏
	 * 
	 * @param ProduceUnitID
	 * @param str_date
	 * @param UnlockStartOrder
	 * @return
	 */
	public String DeleteInstructionByProUnitDateworkOrderUnlockOrder(int ProduceUnitID,
			String str_date,String workOrder, int UnlockStartOrder) {
		String sql = "update t_ra_Instruction set Int_delete = 1 where Int_produnitid = "
				+ ProduceUnitID
				+ " and str_workOrder = '"
				+ workOrder
				+ "' and convert(datetime,Dat_produceDate,20)=convert(datetime,'"
				+ str_date
				+ "',20) and Int_instructOrder >= "
				+ UnlockStartOrder;
		return sql;
	}

	/**
	 * 通过生产单元号、生产日期查询记录数量 袁鹏
	 * 
	 * @param ProduceUnitID
	 *            生产单元号
	 * @param str_date
	 *            生产日期
	 * @return 返回通过生产单元号、生产日期查询记录的数量
	 */
	public String getCountByProUnitDateOrder(int ProduceUnitID, String str_date,String str_workOrder) {
		String sql = "select count(*) from t_ra_instruction where Int_produnitid="
				+ ProduceUnitID
				+ " and convert(datetime,Dat_produceDate,20)=convert(datetime,'"
				+ str_date + "',20) and str_workOrder='"
				+ str_workOrder + "'and int_delete <> 1";
		return sql;
	}

	/**
	 * 通过生产单元号和生产日期查询未被删除的指令
	 * 
	 * @param produnitid
	 *            生产单元号
	 * @param str_date
	 *            生产日期
	 * @return 指令集合
	 */
	public String getInstructionsByProdunitDateOrder(int produnitid, String str_date,String str_workorder) {
		String sql = "select * from t_ra_instruction where convert(datetime,Dat_produceDate,20)=convert(datetime,'"
				+ str_date
				+ "',20) and int_produnitid="
				+ produnitid
				+ " and str_workorder="
				+ str_workorder
				+ "  and int_delete<>1 order by Int_instructOrder";
		return sql;
	}

	/**
	 * 判断该生产单元、生产日期、产品标识的指令数量
	 * 
	 * @param produnitid
	 * @param str_date
	 * @param marker
	 * @return
	 */
	public String getCountByProUnitDateMarker(int produnitid, String str_date,String workOrder,
			String marker) {
		String sql = "select count(*) from t_ra_instruction where convert(datetime,Dat_produceDate,20)=convert(datetime,'"
				+ str_date
				+ "',20) and int_produnitid="
				+ produnitid
				+ " and str_workorder='"
				+ workOrder
				+ "' and Str_produceMarker='" + marker + "' and int_delete<>1";
		return sql;
	}

	/**
	 * 通过生产单元号、生产日期查询记录 袁鹏
	 * 
	 * @param ProduceUnitID
	 *            生产单元号
	 * @param str_date
	 *            当天
	 * @param List
	 *            <Integer> list_state 状态集合
	 * @return 返回通过生产单元号、生产日期查询记录
	 */
	public String getCountByProUnitDateState(int ProduceUnitID,
			List<Integer> list_state) {
		String str = "";
		Iterator<Integer> iter = list_state.iterator();
		if (list_state.size() > 0) {
			str += list_state.get(0);
		} else {
			str += "-1";
		}
		while (iter.hasNext()) {
			int n = Integer.parseInt(iter.next().toString());
			str += ',';
			str += n;
		}
		
		String sql = "select * from t_ra_instruction  where Int_produnitid="
				+ ProduceUnitID			
				+" and datediff(day,convert(datetime,getdate(),20),convert(datetime,Dat_produceDate,20))=0"
				+ " and Int_instructStateID in(" + str
				+ ") and int_delete <> 1 and INT_ISSUANCE = 1";
		
		return sql;
	}

	/**
	 * 修改指令状态 谢静天
	 * 
	 * @param structStateID
	 *            状态id
	 * @param staError
	 *            状态跳转的异常标识
	 * @param producemarker
	 *            产品标识 int_produnitid 生产单元的id
	 * 
	 * @return 返回于该生产单元相对应的指令
	 */
	public String updateInstructState(int structStateID, int staError,
			String producemarker, int produnitid, String str_date) {
		String sql = "update t_ra_Instruction set INT_INSTRUCTSTATEID="
				+ structStateID
				+ ", INT_STAERROR="
				+ staError
				+ " where convert(datetime,Dat_produceDate,20)=convert(datetime,'"
				+ str_date + "',20) and STR_PRODUCEMARKER='" + producemarker
				+ "' and int_produnitid=" + produnitid;

		return sql;
	}

	/**
	 * 查看单个指令状态 谢静天
	 * 
	 * @param producemarker
	 *            产品标识
	 * @return 返回相对应的指令
	 */
	public String checkstr_produceMarker(String str_produceMarker,
			int produnitid, String str_date) {
		String sql = "select ra.Int_instructstateid from t_ra_instruction ra where str_produceMarker='"
				+ str_produceMarker
				+ "' and convert(datetime,Dat_produceDate,20)=convert(datetime,'"
				+ str_date
				+ "',20) and ra.int_delete=0 and int_Edit<>1 and ra.int_issuance=1 and  ra.int_produnitid="
				+ produnitid;

		return sql;
	}

	/**
	 * 指令状态出现异常时要保存出现的现在 谢静天
	 * 
	 * @param producemarker
	 *            产品标识
	 * @param userId用户id
	 * @param Int_instructionStateID状态id
	 * @param produceUnit生产单元
	 * @return 返回相对应的指令
	 */
	public String savet_ra_Instructionexception(int Int_instructionStateID,
			int userId, String produceMarker, int INT_GATHERID) {

		String sql = "insert into t_ra_Instructionexception values("
				+ INT_GATHERID + ",convert(varchar,getdate(),20),"
				+ Int_instructionStateID + ",'" + produceMarker + "'," + userId
				+ ")";

		return sql;
	}

	/**
	 * 查看产品标识如果页面输入的主物料值在指令表中看是否存在。 谢静天
	 * 
	 * @param producemarker
	 *            产品标识
	 * @param produnitid
	 *            生产单元号
	 * @param str_date
	 *            生产日期
	 * @return 返回相对应的指令
	 */

	public String checkmaterielValue(String producemarker, int produnitid,
			String str_date) {
		String sql = "select Str_producemarker  from  t_ra_instruction ra  where   ra.int_issuance=1 and ra.int_produnitid="
				+ produnitid
				+ ""
				+ " and convert(datetime,Dat_produceDate,20)=convert(datetime,'"
				+ str_date
				+ "',20) and ra.int_delete=0 and Str_producemarker='"
				+ producemarker + "' order by int_instructorder  ";

		return sql;
	}

	/**
	 * 根据生产时间和生产单元查看相关的指令。 谢静天
	 * 
	 * @param int_produnitid,String
	 *            str_date,workOrder
	 * @return 返回相对应的指令
	 */

	public String getInstructionByProduceUnitProduceDateWorkorder(int int_produnitid,
			String str_date,String workOrder) {

		String sql = "select * from t_ra_instruction where int_produnitid="
				+ int_produnitid
				+ " and str_workorder='"
				+ workOrder
				+ "' and convert(datetime,Dat_produceDate,20)=convert(datetime,'"
				+ str_date
				+ "',20) and int_delete=0 and int_Edit<>1  order by int_instructorder asc";

		return sql;
	}
	/**
	 * 根据生产时间和生产单元查看相关的指令。 谢静天
	 * 
	 * @param int_produnitid,String
	 *            str_date
	 * @return 返回相对应的指令
	 */

	public String getInstructionByProduceUnitProduceDate(int int_produnitid,
			String str_date) {

		String sql = "select * from t_ra_instruction where int_produnitid="
				+ int_produnitid
				+ " and  convert(datetime,Dat_produceDate,20)=convert(datetime,'"
				+ str_date
				+ "',20) and int_delete=0 and int_Edit<>1  order by int_instructorder asc";

		return sql;
	}

	/**
	 * 核对是否可以进行版本保存 谢静天
	 * 
	 * @param int_produnitid
	 *            生产单元号
	 * @param str_date
	 *            生产日期
	 * 
	 */
	public String checksaveVersion(int int_produnitid, String str_date,String workOrder) {
		String sql = "select * from t_ra_instruction where  int_delete=0  and int_produnitid="
				+ int_produnitid
				+ "  and str_workorder='"
				+ workOrder
				+ "' and  convert(datetime,Dat_produceDate,20)=convert(datetime,'"
				+ str_date
				+ "',20) and (int_instructstateid<>(select int_instructStateid from t_ra_produceunit where int_id="
				+ int_produnitid + " and int_delete=0)or int_Edit=1)  ";
		return sql;
	}

	/**
	 * 根据生产时间和生产单元和恢复状态查看相关的指令进行版本恢复 谢静天
	 * 
	 * @param int_produnitid
	 *            生产单元号
	 * @param str_date
	 *            生产日期
	 * @param int_stateid
	 *            状态号
	 * 
	 */
	public String comebackVersion(int int_produnitid, String str_date,
			int int_stateid) {
		String sql = "select * from t_ra_instruction where int_produnitid="
				+ int_produnitid
				+ " and convert(datetime,Dat_produceDate,20)=convert(datetime,'"
				+ str_date + "',20)" + " and int_instructstateid<>"
				+ int_stateid + " and int_delete=0 and int_Edit<>1 ";

		return sql;
	}

	/**
	 * 根据生产时间和生产单元删除指令 谢静天
	 * 
	 * @param int_produnitid
	 *            生产单元号
	 * @param str_date
	 *            生产日期
	 * 
	 */
	public String deleteInstructionByProduceUnitProduceDateWorkorder(int int_produnitid,
			String str_date,String workOrder) {
		String sql = "update t_ra_instruction set int_delete=1 where int_delete=0 and int_edit<>1 and int_produnitid="
				+ int_produnitid
				+ " and str_workorder="
				+ workOrder
				+ " and convert(datetime,Dat_produceDate,20)=convert(datetime,'"
				+ str_date + "',20)";

		return sql;
	}

	/**
	 * 通过采集点id和系统时间查询相关的指令。 谢静天
	 * 
	 * 
	 * 
	 */
	public String getGatherwork(int gatherid) {
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
	     
	     String sql="select ra.*  from t_ra_instruction ra  , t_tg_gather g ,t_os_workSchedle ws where  ws.int_produnitid=g.int_produnitid and g.int_produnitid=ra.int_produnitid and ws.str_workorder=ra.str_workOrder and ra.int_issuance=1 and g.int_id="+gatherid+""
		 +" and convert(datetime,Dat_produceDate,20)=convert(datetime,'"
				+ str_date
				+ "',20)"
				+ " and ra.int_delete=0 and int_Edit<>1 and ra.Int_instructStateID in(select int_fromstate from t_ra_gatherstaterule gsr,t_ra_stateconversion sv where  gsr.int_gatherid="+gatherid+"" +
		 		" and gsr.int_stateconversionid=sv.int_id)  order by  ws.str_workschedle,   int_instructorder    " ;

		return sql;
		
	}

	/**
	 * 查找指令最大的顺序号 进行指令追加 谢静天
	 */
	public String getInstructionMaxOrder(int int_produnitid, String str_date,String workOrder) {
		String sql = "select max(int_instructorder) from t_ra_instruction where  int_delete=0  and int_produnitid="
				+ int_produnitid
				+ " and str_workorder="
				+ workOrder
				+ " and convert(datetime,Dat_produceDate,20)=convert(datetime,'"
				+ str_date + "',20)";

		return sql;
	}

	/**
	  * 通过从开始时间到结束时间及生产单元查询相关的指令
	  * 谢静天
	  */
	public String getInstructionByStartAndEndProduceunitOrder(int produceid,String starttime,String endtime,String workorder){
		String sql = "select * from t_ra_instruction where int_delete=0 and int_produnitid="
				+ produceid
				+ " and  str_workorder='"+workorder+"' and "
				+ "convert(datetime,Dat_produceDate,20)>="
				+ "convert(datetime,'"
				+ starttime
				+ "',20)"
				+ " and convert(datetime,Dat_produceDate,20)<= "
				+ "convert(datetime,'" + endtime + "',20)";
		
		return sql;
	}

	/**
	 * 查看生产单元和生产日期的主物料值 用于指令导入时判断 谢静天
	 */
	public String getStr_produceMarkerbyproduceUnitandproduceDate(
			int produceid, String str_date) {
		String sql = "select * from t_ra_instruction where  int_delete=0  and int_produnitid="
				+ produceid
				+ " and convert(datetime,Dat_produceDate,20)=convert(datetime,'"
				+ str_date + "',20)";
		return sql;
	}

	/**
	 * 谢静天 生产单元和时间 通过主物料的值查看指令是否处于编辑状态。
	 */
	public String checkinstructionedit(String producemarker, int produnitid,
			String str_date) {
		String sql = "select * from t_ra_instruction ra where str_produceMarker='"
				+ producemarker
				+ "'and ra.int_delete=0  and  ra.int_produnitid="
				+ produnitid
				+ "  and convert(datetime,Dat_produceDate,20)=convert(datetime,'"
				+ str_date + "',20)";

		return sql;
	}

	/**
	 * 查询当前生产单元、非当前生产日期的指令对象
	 * 
	 * @param produnitid
	 * @param str_date
	 * @return
	 */
	public String getInstructionsByProdunitOtherProdDate(int produnitid,
			String str_date) {
		String sql = "select * from t_ra_instruction ra where ra.int_delete=0  and  ra.int_produnitid="
				+ produnitid
				+ "  and convert(datetime,Dat_produceDate,20)<>convert(datetime,'"
				+ str_date + "',20)";
		return sql;
	}
	 public String export(String int_id ,String overtime,String endtime,String workOrder){
		 
		 String sql="select INT_PRODUNITID,STR_WORKTEAM,STR_WORKORDER,DAT_PRODUCEDATE,INT_INSTRUCTORDER," +
		 		"STR_PRODUCETYPE,STR_PRODUCENAME,STR_PRODUCEMARKER,INT_COUNT,TIM_PLANBEGIN,TIM_PLANFINISH," +
		 		"DAT_PLANDATE,INT_PLANORDER,STR_VERSIONCODE,INT_STAERROR from t_ra_instruction " +
		 		"where int_delete=0 and int_produnitid="+Integer.parseInt(int_id)+"" 		 		
		 		+" and datediff(day,convert(datetime,'"+overtime+"',20),convert(datetime,Dat_produceDate,20))<=0"
		 		+" and datediff(day,convert(datetime,'"+endtime+"',20),convert(datetime,Dat_produceDate,20))>=0"
		 		+" and str_workorder='"+workOrder+"' order by Dat_produceDate desc,Int_instructOrder asc";
		 
		 
		 return sql;
	 }

	 /**
	  * 核对指令表中是否有指令用于删除
	  * 包金旭
	  * 根据生产单元，生产日期及班次
	  *  @param  instuction
	  *
	  */
	 public String checkAllInstructionByProdunitidDateWorkorder(int int_produnitid,String str_date,String workOrder){
		 
		 String sql = "select * fromt_ra_instruction where int_delete=0 and int_edit<>1 and int_produnitid="
				+ int_produnitid
				+ " and str_workorder="
				+ workOrder
				+ " and convert(datetime,Dat_produceDate,20)=convert(datetime,'"
				+ str_date + "',20)";

		return sql;
		 
		 
	 }
	 
}
