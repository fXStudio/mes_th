package com.qm.mes.pm.service.device;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.AdapterService;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.ServiceException;
import com.qm.mes.framework.ServiceExceptionType;
import com.qm.mes.pm.bean.DeviceType;
import com.qm.mes.pm.factory.DeviceFactory;
/**
 * 添加设备类型
 * 
 * @author xujia
 * 
 */

public class AddDeviceType  extends AdapterService {
	/**
	 * 获得连接
	 */
	private Connection con = null;
	/**
	 * 设备名
	 */
	private String str_name = null;
	
	Statement stmt = null;
	ResultSet rs = null;
	DeviceFactory factory = new DeviceFactory();

	private final Log log = LogFactory.getLog(AddDeviceType.class);
	@Override
	public boolean checkParameter(IMessage message, String processid) {
	
		con = (Connection) message.getOtherParameter("con");
		str_name = message.getUserParameterValue("str_name").trim();		
		
	
		//输出log信息
	    String debug="设备类型名：str_name：" + str_name	+ "\n";
	    log.info("添加设备类型的参数: " + debug);
	    
		if (str_name == null  ) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			return false;
		}
		return true;
	}
	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {					
						
				DeviceType dt=new DeviceType();
				dt.setName(str_name);
				factory.createDeviceType(dt, con);
				
				log.debug( "添加设备类型成功!");
			
		}catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), processid, new Date(), sqle));
				log.error("添加设备类型操作时,数据库异常"	+ sqle.toString());
				return ExecuteResult.fail;
			} 
		} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.fatal( "添加设备类型操作时,未知异常" + e.toString());
			return ExecuteResult.fail;
			}
		return ExecuteResult.sucess;
	}
	@Override
	public void relesase() throws SQLException {
		str_name = null;		
		con = null;
	}
}
