package com.qm.mes.os.dao;

import com.qm.mes.os.bean.UpLoadRecord;

/**
 * 
 * @author 谢静天
 *
 */
public class DAO_UploadRecordForOracle implements DAO_UploadRecord{
	
	/* (non-Javadoc)
	 * 创建发布作业计划记录表  谢静天
	 * @see mes.os.dao.DAO_UploadRecord#SaveUploadRecord(mes.os.bean.UpLoadRecord)
	 */
	public String SaveUploadRecord(UpLoadRecord uploadrecord){
		String sql="insert into t_os_upLoadRecord values(SEQ_os_upLoadRecord.nextval,"
		  +"sysdate,'"
		  +uploadrecord.getUserName()+"','"
		  +uploadrecord.getVersionCode()+"',"
		  +uploadrecord.getUpload()+")";
			
		return sql;
	}
}
