package com.qm.mes.os.factory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.DataBaseType;
import com.qm.mes.os.bean.UpLoadRecord;
import com.qm.mes.os.dao.*;
import com.qm.mes.system.dao.DAOFactoryAdapter;
/**
 * ‘发布’和‘取消发布‘工厂
 * @author 谢静天
 *
 */
public class UploadFactory {

	private final Log log = LogFactory.getLog(UploadFactory.class);
	/**创建发布作业计划记录表  谢静天
	 * @param uploadrecord
	 * @param con
	 * @throws SQLException
	 */
	public void SaveUploadRecord(UpLoadRecord uploadrecord,Connection con)throws SQLException{
		 DAO_UploadRecord dao_uploadrecord = ( DAO_UploadRecord) DAOFactoryAdapter.getInstance(
					DataBaseType.getDataBaseType(con),  DAO_UploadRecord.class);
			Statement stmt = con.createStatement();
			log.debug("创建发布作业计划记录表"+dao_uploadrecord.SaveUploadRecord(uploadrecord));
			stmt.executeUpdate(dao_uploadrecord.SaveUploadRecord(uploadrecord));
			if(stmt!=null)
			{
				stmt.close();
			}
			
   }
	
	
	
	
	
	
	
	
}