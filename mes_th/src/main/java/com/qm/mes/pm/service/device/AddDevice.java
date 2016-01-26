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
import com.qm.mes.pm.bean.Device;
import com.qm.mes.pm.factory.DeviceFactory;

/**
 * 添加设备信息 
 * @author xujia
 * 
 */
public class AddDevice  extends AdapterService {
	/**
	 * 获得连接
	 */
	private Connection con = null;
	/**
	 * 设备名 
	 */
	private String str_name = null;
	/**
	 * 设备号
	 */
	private String str_code = null;	
	/**
	 *  设备描述
	 */
	private String str_description = null;
	DeviceFactory factory = new DeviceFactory();
	//日志
	private final Log log = LogFactory.getLog(AddDevice.class);
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		str_name = message.getUserParameterValue("str_name");
		str_code= message.getUserParameterValue("str_code");		
		str_description = message.getUserParameterValue("str_description");
		
		//输出log信息
	    String debug="设备名：" + str_name + "；"+ "设备编号："+str_code+ ";"
		+"设备描述："+str_description;
	    log.debug("添加设备时用户提交的参数: " + debug);

		if (str_name == null || str_code == null 	|| str_description == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("设备名、设备编号、设备描述中有为空参数，退出服务。");
			return false;
		}
		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
				Device d = new Device();					
				d.setName(str_name);
				d.setCode(str_code);
				d.setDescription(str_description);				
				factory.createDevice(d, con);
				log.info("添加设备信息服务成功！");
				Device dd = new Device();
				//得到添加后的序号传给其他服务
				dd = factory.getDeviceByCode(str_code, con);
				message.setOutputParameter("int_id", String.valueOf(dd
						.getId()));
				log.info("设备序号int_id为："+dd.getId()+" ");
				
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
		str_code = null;		
		str_description = null;
		con = null;

	}

}
