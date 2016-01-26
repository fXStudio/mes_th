package com.qm.mes.pm.dao;

import com.qm.mes.pm.bean.Device;
import com.qm.mes.pm.bean.DeviceType;

/**
 * @author Xujia
 *
 */
public class DAO_DeviceForSqlserver extends DAO_DeviceForOracle {
	
	public String saveDevice(Device device){
		 String sql = "insert into t_pm_device(str_devicename,str_devicecode,int_produceunit,str_description) "
				+ "values('"
				+ device.getName()
				+"','"+device.getCode()+"','"+device.getDescription()+"')";	   
		return sql;
	}	

	public String saveDeviceType(DeviceType deviceType){
		 String sql = "insert into t_pm_devicetype(t_pm_devicetype) "
				+ "values('"
				+deviceType.getName()+"')";	   
		return sql;
	}	
	public String saveDevice_Type(int d,int t){
		 String sql = "insert into t_pm_device_type(int_device,int_devicetype) "
				+ "values("
				+d+","+t+")";	   
		return sql;
	}	
	public String saveDevice_Unit(int d,int u){
		 String sql = "insert into t_pm_device_unit(int_device,int_unit) "
				+ "values("
				+d+","+u+")";	   
		return sql;
	}

}
