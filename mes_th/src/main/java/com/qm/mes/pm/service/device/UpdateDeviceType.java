package com.qm.mes.pm.service.device;

import java.sql.Connection;
import java.sql.SQLException;
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
import com.qm.mes.pm.service.datarule.AddDataRule;
/**
 * 更新设备类型
 * @author Xujia
 *
 */
public class UpdateDeviceType extends AdapterService {
	/**
	 * 获得连接
	 */
	private Connection con = null;
	/**
	 * 设备类型名    
	 */
	private String str_name = null;
	/**
	 * 序号
	 */
	private String int_id=null;
	DeviceFactory factory = new DeviceFactory();
	//日志
	private final Log log = LogFactory.getLog(AddDataRule.class);
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		str_name = message.getUserParameterValue("string_statename");
		int_id= message.getUserParameterValue("int_id");
		
		//输出log信息
	    String debug="设备名：" + str_name;
	    log.debug("更新设备信息用户提交的参数: " + debug);

		if (str_name == null ) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("设备类型名为空参数，退出服务。");
			return false;
		}

		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
				DeviceType dt = new DeviceType();								
				dt.setName(str_name);
				dt.setId(new Integer(int_id));
				factory.updateDeviceType(dt, con);
				log.info("更新设备类型成功！");	
				
								
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), processid, new Date(), sqle));
				log.error("数据库异常，中断服务。");
				return ExecuteResult.fail;
			}
		} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error("未知异常，中断服务。");
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