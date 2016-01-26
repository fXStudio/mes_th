package com.qm.mes.pm.dao;

import com.qm.mes.pm.bean.ExceptionRecord;
/**
 * @author Xujia
 *
 */
public interface DAO_ExceptionRecord {
	/**
	 * 创建异常记录的sql语句
	 * @param exRecord
	 * @return
	 */
	String saveExceptionRecord (ExceptionRecord exRecord);
	
	/**
	 * 通过序号查出异常记录的sql语句
	 * @param id
	 * @return
	 */
	String getExceptionRecordById(int id);
	
	/**
	 * 通过序号删除异常记录的sql语句
	 * @param id
	 * @return
	 */
	String delExceptionRecordById(int id);
	/**
	 * 查询出全部异常记录的sql语句
	 * @return
	 */
	String getAllExceptionRecord ();
	/**
	 * 通过连接表查设备信息
	 * @return
	 */
	String getDeviceByDevice_Unit(int unitid);
	/**
	 * 通过设备名获取类型名
	 * @param devicename
	 * @return
	 */
	String getdevicetypeBydevicename(String devicename);
	
	/**
	 * 关闭异常记录
	 * @param id
	 * @return
	 */
	String colseExceptionRecord(ExceptionRecord exRecord);
	/**
	 * 更改异常记录
	 * @param id
	 * @return  
	 */
	String updateExceptionRecord(ExceptionRecord exRecord);
	
	/**
	 * 通过开始、结束时间查询异常记录列表
	 * 
	 * @param str_starttime	开始时间
	 * @param str_endtime	结束时间
	 * @return	通过开始、结束时间查询异常记录列表
	 */
	public String getExcepRecordsByStartTimeEndTime(String str_starttime,String str_endtime);
}
