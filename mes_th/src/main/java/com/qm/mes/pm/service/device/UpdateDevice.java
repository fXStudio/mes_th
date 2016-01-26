package com.qm.mes.pm.service.device;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.AdapterService;
import com.qm.mes.framework.DataBaseType;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.ServiceException;
import com.qm.mes.framework.ServiceExceptionType;
import com.qm.mes.pm.bean.Device;
import com.qm.mes.pm.dao.DAO_Device;
import com.qm.mes.pm.factory.DeviceFactory;
import com.qm.mes.pm.service.datarule.AddDataRule;
import com.qm.mes.system.dao.DAOFactoryAdapter;
/**
 * 更改设备
 * @author xujia
 * 
 */

public class UpdateDevice extends AdapterService {
	/**
	 * 获得连接
	 */
	private Connection con = null;
	/**
	 * 设备名 
	 */
	private String str_devicename = null;
	/**
	 * 设备编号
	 */
	private String str_devicecode = null;
	/**
	 * 设备描述
	 */
	private String str_description = null;
	/**
	 *  生产单元信息
	 */
	private String unit_info=null;
	/**
	 *  设备信息
	 */
	private String device_info=null;
	/**
	 *  序号
	 */ 
	private String int_id=null;
	DeviceFactory factory = new DeviceFactory();
	//日志
	private final Log log = LogFactory.getLog(AddDataRule.class);
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		str_devicename = message.getUserParameterValue("str_name");
		str_devicecode= message.getUserParameterValue("str_code");
		int_id= message.getUserParameterValue("int_id");
		str_description = message.getUserParameterValue("str_description");
		//int_produnitid = message.getUserParameterValue("int_produnitid");
		//int_devicetype = message.getUserParameterValue("int_devicetype");
		device_info = message.getUserParameterValue("device_info");
		unit_info = message.getUserParameterValue("unit_info");
		
		//输出log信息
	    String debug="设备名：" + str_devicename + "；"+ "设备编号："+str_devicecode+ ";"
		+"生产单元号：" + unit_info + "；"+ "设备类型："+device_info+"设备描述："+str_description;
	    log.debug("更新设备信息用户提交的参数: " + debug);

		if (str_devicename == null || str_devicecode == null 	|| str_description == null) {
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
				String[] units = unit_info.split(","); // 单元拆分成组
				String[] devices = device_info.split(","); // 设备拆分成组		
				//更新设备信息
				Device d = new Device();								
				d.setName(str_devicename);
				d.setCode(str_devicecode);
				d.setDescription(str_description);	
				d.setId(new Integer(int_id));
				factory.updateDevice(d, con);
				log.info("更新设备信息成功！");	
				
				//更新生产单元信息
				/* 单条数据时操作方法
				 * int unitid=factory.getUnit_Device(new Integer(int_id), con);
				factory.updateDevice_Unit( new Integer(int_produnitid),unitid, con);
			       下面是批次处理  */
				DAO_Device dao = (DAO_Device) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Device.class);
				Statement stmt = con.createStatement();
				factory.delDevice_UnitByDid((new Integer(int_id)), con);  //先删除						
				for (int j = 0; j < units.length; j++) {    //添加生产单元和设备的关系
				    log.debug("添加Device_unit: "+dao.saveDevice_Unit((Integer.parseInt(int_id)),(Integer.parseInt(units[j]))));
				    stmt.execute(dao.saveDevice_Unit((Integer.parseInt(int_id)),(Integer.parseInt(units[j]))));	
				}
				log.info("更改device_unit成功");
				//更新设备类型信息
				factory.delDevice_TypeByDid((new Integer(int_id)), con); //先删除
				for (int k = 0; k <devices.length; k++) {   //添加设备类型和设备的关系
					log.debug("添加Device_type: "+dao.saveDevice_Type((Integer.parseInt(int_id)),(Integer.parseInt(devices[k]))));
					stmt.execute(dao.saveDevice_Type((Integer.parseInt(int_id)),(Integer.parseInt(devices[k]))));
					}
				log.info("更改device_type成功");
								
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
		str_devicename = null;
		str_devicecode = null;
		unit_info = null;
		device_info = null;		
		str_description = null;
		con = null;

	}

}

