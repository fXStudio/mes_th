package com.qm.mes.pm.dao;

import com.qm.mes.pm.bean.ExceptionRecord;

/**
 * @author Xujia
 *
 */
public class DAO_ExceptionRecordForOracle implements  DAO_ExceptionRecord{

	/**
	 * 创建异常记录的sql语句
	 * @param exRecord
	 * @return
	 */
	public String saveExceptionRecord (ExceptionRecord exRecord){
		String sql = "insert into t_pm_exceptionrecord(int_id,int_userid,int_produceunit,dat_start,str_description," +
		"int_exceptiontype,int_exceptioncause,int_state,int_deviceid,int_devicetypeid) " +
		"values(seq_pm_EXCEPTIONRECORD.nextval,"+exRecord.getUserId()+","+exRecord.getProduceUnitId()+",sysdate,'"+exRecord.getDescription()+"',"
		+exRecord.getExceptionTypeId()+","+exRecord.getExceptionCauseId()+","
		+"0,"+exRecord.getDeviceid()+","+exRecord.getDevicetypeid()
		+")";
      return sql;
	}
	
	/**
	 * 通过序号查出异常记录的sql语句
	 * @param id
	 * @return
	 */
	public String getExceptionRecordById(int id){
		String sql = "select * from t_pm_exceptionrecord"
			+ " where int_id = " + id + "";
	return sql;
	}
	
	/**
	 * 通过序号删除异常记录的sql语句
	 * @param id
	 * @return
	 */
	public String delExceptionRecordById(int id){
		String sql = "delete from  t_pm_exceptionrecord where int_id=" + id;
		return sql;
	}
	/**
	 * 查询出全部异常记录的sql语句
	 * @return
	 */
	public String getAllExceptionRecord (){
	String sql = "select t.int_id,t.int_userid,t.int_produceunit,t.dat_start,t.str_description,t.dat_close,t.int_state,t.int_exceptioncause,t.int_exceptiontype"		
		+",ec.str_name as causename,et.str_name as typename,u.str_name as unitname"
		+" from t_pm_exceptionrecord t"
		+" left join t_pm_exceptioncause ec on ec.int_id=t.int_exceptioncause"
		+" left join t_pm_exceptiontype et on et.int_id=t.int_exceptiontype"
		+" left join t_ra_produceunit u on u.int_id=t.int_produceunit";
return sql;
}
	/**
	 * 通过连接表查设备信息
	 * @return
	 */
	public String getDeviceByDevice_Unit(int unitid){
		String sql="select t.*,d.str_devicename" 
            +" from t_pm_device_unit t,t_ra_produceunit u,t_pm_device d"
            +" where t.int_device=d.int_id and t.int_unit=u.int_id and t.int_unit="+unitid;
		return sql;
	}
 	
	/**
	 * 通过设备名获取类型名
	 * @param id
	 * @return
	 */
	public String getdevicetypeBydevicename(String devicename){
		String sql="select t.*,d.str_devicename ,ty.str_statename"
		+" from t_pm_device_type t,t_pm_devicetype ty,t_pm_device d"
		+" where t.int_device=d.int_id and t.int_devicetype=ty.int_id and d.str_devicename='"+devicename+"'";
		return sql;
	}
	/**
	 * 关闭异常记录
	 * @param id
	 * @return  
	 */
	public String colseExceptionRecord(ExceptionRecord exRecord){ 
		String sql="update t_pm_exceptionrecord set str_rediscription ='"
			+ exRecord.getRediscription() + "' , int_closeuserid = "+ exRecord.getCloseuser()
			+ ",dat_close= sysdate,int_state=1 where int_id = "
			+ exRecord.getId();
		return sql;
	}
	/**
	 * 更改异常记录
	 * @param id
	 * @return  
	 */
	public String updateExceptionRecord(ExceptionRecord exRecord){ 		
		String sql="update t_pm_exceptionrecord set int_userid ="
			+ exRecord.getUserId() + " , int_produceunit = "+ exRecord.getProduceUnitId()+""
			+",str_description='"+exRecord.getDescription()+"',int_exceptiontype="+exRecord.getExceptionTypeId()+""
			+",int_exceptioncause="+exRecord.getExceptionCauseId()+",int_deviceid="+exRecord.getDeviceid()+""
			+",int_devicetypeid="+exRecord.getDevicetypeid()+ " where int_id = "
			+ exRecord.getId();
		return sql;
	}
	
	/**
	 * 通过开始、结束时间查询异常记录列表
	 * 
	 * @param str_starttime	开始时间
	 * @param str_endtime	结束时间
	 * @return	通过开始、结束时间查询异常记录列表
	 */
	public String getExcepRecordsByStartTimeEndTime(String str_starttime,String str_endtime){
		String sql = "select er.int_id as er_id,du.CUSRNAME,er.Int_produceUnit,er.Dat_start,er.Str_Description,"+
			"er.Dat_close,er.STR_REDISCRIPTION,er.Int_ExceptionType,er.Int_ExceptionCause,"+
			"pu.int_id as pu_id,pu.str_name as pu_name,"+
			"ec.int_id as ec_id,ec.str_name as ec_name,"+
			"et.int_id as et_id,et.str_name as et_name,"+
			"ROUND(TO_NUMBER(DAT_CLOSE - DAT_START) * 24 * 60 * 60) as timelang "+
			"from t_pm_ExceptionRecord er,T_RA_PRODUCEUNIT pu,T_PM_EXCEPTIONCAUSE ec,T_PM_EXCEPTIONTYPE et,DATA_USER du "+
			"where Int_produceUnit=pu.int_id and Int_ExceptionType=et.int_id and Int_ExceptionCause=ec.int_id and er.Int_UserID=du.NUSRNO"+
			" and to_char(Dat_start,'yyyy-MM-dd')>='"+str_starttime+ 
			"' and to_char(Dat_start,'yyyy-MM-dd')<='"+str_endtime+"'";
		return sql;
	}
}
