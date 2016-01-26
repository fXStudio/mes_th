package com.qm.mes.pm.dao;

import com.qm.mes.pm.bean.Device;
import com.qm.mes.pm.bean.DeviceType;

/**
 * @author Xujia
 *
 */
public interface DAO_Device {
	
	/**
	 * 创建设备的sql语句
	 * @param device
	 * @return
	 */
	String saveDevice(Device device);
	/**
	 * 创建设备类型的sql语句
	 * @param device
	 * @return
	 */
	String saveDeviceType(DeviceType deviceType);
	/**
	 * 创建设备-类型的sql语句
	 * @param d
	 * @param t
	 * @return
	 */
	String saveDevice_Type(int d,int t);	
	/**
	 * 创建设备-生产单元的sql语句
	 * @param d
	 * @param u
	 * @return
	 */
	String saveDevice_Unit(int d,int u);
	/**
	 * 通过序号查出设备的sql语句
	 * @param id
	 * @return
	 */
	String getDeviceById(int id);
	/**
	 * 通过序号查出设备类型的sql语句
	 * @param id
	 * @return
	 */
	String getDeviceTypeById(int id);
	/**
	 * 通过序号查出设备-类型的sql语句
	 * @param id
	 * @return
	 */
	String getDevice_TypeById(int id);
	/**
	 * 通过序号查出设备-单元的sql语句
	 * @param id
	 * @return
	 */
	String getDevice_UnitById(int id);
	/**
	 * 通过序号删除设备的sql语句
	 * @param id
	 * @return
	 */
	String delDeviceById(int id);
	/**
	 * 通过序号删除设备类型的sql语句
	 * @param id
	 * @return
	 */
	String delDeviceTypeById(int id);
	/**
	 * 通过序号删除设备-类型的sql语句
	 * @param id
	 * @return
	 */
	String delDevice_TypeById(int id);
	/**
	 * 通过序号删除设备-单元的sql语句
	 * @param id
	 * @return
	 */
	String delDevice_UnitById(int id);
	/**
	 * 更新设备的sql语句
	 * @param device
	 * @return
	 */
	String updateDevice(Device device);
	/**
	 * 更新设备类型的sql语句
	 * @param device
	 * @return
	 */
	String updateDeviceType(DeviceType deviceType);	
	/**
	 * 更新设备-类型的sql语句
	 * @param d
	 * @param t
	 * @return
	 */
	String updateDevice_Type(int type,int id);	
	/**
	 * 更新设备-单元的sql语句
	 * @param d
	 * @param t
	 * @return
	 */
	String updateDevice_Unit(int unit,int id);   
	/**
	 * 查询出全部设备的sql语句
	 * @return
	 */
	String getAllDevice();
	/**
	 * 查询出全部设备类型的sql语句
	 * @return
	 */
	String getAllDeviceType();
	/**
	 * 验证设备是否已用
	 *  徐嘉
	 * @param id
	 * @return 顺序号个数
	 */
	String checkDeviceTypeById(int id);
	/**
	 * 通过设备编号查出设备号sql语句
	 * @param name
	 * @return
	 */
	String getDeviceByCode(String code);
	
	/**
	 * @param id
	 * @return
	 */
	String getTypeidByid(int id);
	
	/**
	 * @param id
	 * @return
	 */
	String getUnitidByid(int id);
	
	/** 链接unit device两表
	 * @param id
	 * @return
	 */
	String getUnit_Device(int id);
	/**
	 * 链接type device两表
	 * @param id
	 * @return
	 */
	String getType_Device(int id);
	
	/**
	 * 由类型名得id
	 * @param name
	 * @return
	 */
	String getTypeidByname(String name);
	
	/**
	 * @param name
	 * @param id
	 * @return
	 */
	String getDeviceidBytype(String name,int id);
	/**
	 * 检测ExceptionRecord表中是否有设备类型
	 *  徐嘉
	 * @param id
	 * @return 关联个数
	 */
	String checkDTypeInrecordById(int id);
	/**
	 * 检测ExceptionRecord表中是否有设备号
	 *  徐嘉
	 * @param id
	 * @return 关联个数
	 */
	String checkDeviceInrecordById(int id);
	/**
	 * 通过设备号删除设备-类型的sql语句
	 * @param id
	 * @return
	 */
	String delDevice_TypeByDid(int deviceid);
	/**
	 * 通过设备号删除设备-单元的sql语句
	 * @param id
	 * @return
	 */
	String delDevice_UnitByDid(int deviceid); 
}
