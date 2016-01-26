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
import com.qm.mes.pm.dao.DAO_Device;
import com.qm.mes.pm.factory.DeviceFactory;
import com.qm.mes.system.dao.DAOFactoryAdapter;
/**
 * 添加设备与生产单元/类型的关系
 * @author Xujia
 *
 */
public class AddDevice_u_t extends AdapterService {//连接数据库对象
    Connection con = null;
    /**
     * 生产单元号
     */
    private String unit_info  =null ;
    /**
     * 设备类型号
     */
    private String device_info = null;
    /**
     * 数据规则Id
     */
    private String int_id =null ;
	/**
	 * 日志
	 */
	private final Log log = LogFactory.getLog(AddDevice_u_t.class);
	
	DeviceFactory factory = new DeviceFactory();

    
    
    @Override
    public boolean checkParameter(IMessage message, String processid) {
    	String debug = "";
		con = (Connection) message.getOtherParameter("con");        
		int_id = message.getOutputParameterValue("int_id");
		//int_produnitid = message.getUserParameterValue("int_produnitid");
		//int_devicetype = message.getUserParameterValue("int_devicetype");
		device_info = message.getUserParameterValue("device_info");
		unit_info = message.getUserParameterValue("unit_info");
		
		//输出log信息
	    debug="设备号：" + int_id + "；"+ "生产单元号："+unit_info+ "；"+ "设备类型号" +  "："+device_info;
	
	    log.debug("添加异常类型时用户提交的参数: " + debug);

		if (unit_info == null || device_info == null || int_id == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("设备号、生产单元号、设备类型号中有为空参数，退出服务。");
			return false;
		}

		return true;
	}

    @Override
    public ExecuteResult doAdapterService(IMessage message, String processid){
        try {
			try {                				
                
				DAO_Device dao = (DAO_Device) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Device.class);
				Statement stmt = con.createStatement();
				String[] units = unit_info.split(","); // 单元拆分成组
				String[] devices = device_info.split(","); // 设备拆分成组
				//添加生产单元和设备的关系
				for (int j = 0; j < units.length; j++) {
				log.debug("创建Device_unit: "+dao.saveDevice_Unit((Integer.parseInt(int_id)),(Integer.parseInt(units[j]))));
				stmt.execute(dao.saveDevice_Unit((Integer.parseInt(int_id)),(Integer.parseInt(units[j]))));	
				}
				for (int k = 0; k <devices.length; k++) {
				log.debug("创建Device_type: "+dao.saveDevice_Type((Integer.parseInt(int_id)),(Integer.parseInt(devices[k]))));
				stmt.execute(dao.saveDevice_Type((Integer.parseInt(int_id)),(Integer.parseInt(devices[k]))));
				}
				if(stmt!=null){
					stmt.close();
					stmt=null;
				}
               
			}catch (SQLException sqle) {
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
    	device_info = null;
    	unit_info = null;		
    	int_id = null;
		con = null;

	}
    

}