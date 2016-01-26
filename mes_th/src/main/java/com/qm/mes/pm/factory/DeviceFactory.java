package com.qm.mes.pm.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.DataBaseType;
import com.qm.mes.pm.bean.Device;
import com.qm.mes.pm.bean.DeviceType;
import com.qm.mes.pm.dao.DAO_Device;
import com.qm.mes.system.dao.DAOFactoryAdapter;

/**
 * 设备工厂类
 * @author Xujia
 *
 */
public class DeviceFactory {
//	创建日志文件
	private final Log log = LogFactory.getLog(DeviceFactory.class);
	
	/** 徐嘉
	 * 通过序号删除DeviceType
	 * @param id
	 * @param con
	 * @throws SQLException 
	 */
	public void delDeviceTypeById(int id, Connection con) throws SQLException {
		DAO_Device dao = (DAO_Device) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Device.class);
		Statement stmt = con.createStatement();
		log.debug("通过序号删除DeviceType: "+dao.delDeviceTypeById(id));	
		stmt.execute(dao.delDeviceTypeById(id));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}
	/** 徐嘉
	 * 通过序号删除Device
	 * @param id
	 * @param con
	 * @throws SQLException 
	 */
	public void delDeviceById(int id, Connection con) throws SQLException {
		DAO_Device dao = (DAO_Device) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Device.class);
		Statement stmt = con.createStatement();
		log.debug("通过序号删除Device: "+dao.delDeviceById(id));	
		stmt.execute(dao.delDeviceById(id));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}
	
	/** 徐嘉
	 * 通过设备号删除设备-单元
	 * @param deviceid
	 * @param con
	 * @throws SQLException 
	 */
	public void delDevice_UnitByDid(int deviceid, Connection con) throws SQLException {
		DAO_Device dao = (DAO_Device) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Device.class);
		Statement stmt = con.createStatement();
		log.debug("通过序号删除Device_unit: "+dao.delDevice_UnitByDid(deviceid));	
		stmt.execute(dao.delDevice_UnitByDid(deviceid));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}	
	
	/** 徐嘉
	 * 通过设备号删除设备-类型
	 * @param deviceid
	 * @param con
	 * @throws SQLException 
	 */
	public void delDevice_TypeByDid(int deviceid, Connection con) throws SQLException {
		DAO_Device dao = (DAO_Device) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Device.class);
		Statement stmt = con.createStatement();
		log.debug("通过序号删除Device_type: "+dao.delDevice_TypeByDid(deviceid));	
		stmt.execute(dao.delDevice_TypeByDid(deviceid));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}	
	
	/** 徐嘉
	 * 通过序号删除Device_Type
	 * @param id
	 * @param con
	 * @throws SQLException 
	 */
	public void delDevice_TypeById(int id, Connection con) throws SQLException {
		int int_id=0;
		ResultSet rs = null;
		DAO_Device dao = (DAO_Device) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Device.class);
		Statement stmt = con.createStatement();		
		log.debug("通过序号查id: "+dao.getTypeidByid(id));	
		rs = stmt.executeQuery(dao.getTypeidByid(id));
		while(rs.next())
		{
	    int_id=rs.getInt(1);
	    log.info("取得id为："+int_id);
		}
		log.debug("删除device_type的信息: "+dao.delDevice_TypeById(int_id));	
		if (int_id!=0)
		{stmt.execute(dao.delDevice_TypeById(int_id));}
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}
	/** 徐嘉
	 * 通过序号删除Device_Unit
	 * @param id
	 * @param con
	 * @throws SQLException 
	 */
	public void delDevice_UnitById(int id, Connection con) throws SQLException {
		int int_id1=0;
		ResultSet rs = null;
		DAO_Device dao = (DAO_Device) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Device.class);
		Statement stmt = con.createStatement();		
		log.debug("通过序号查id: "+dao.getUnitidByid(id));	
		rs = stmt.executeQuery(dao.getUnitidByid(id));
		while(rs.next())
		{
	    int_id1=rs.getInt(1);
	    log.info("取得id为："+int_id1);
		}
		log.debug("删除device_unit的信息: "+dao.delDevice_UnitById(int_id1));	
		if (int_id1!=0)
		stmt.execute(dao.delDevice_UnitById(int_id1));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}
	
	/**  徐嘉
	 * 创建DeviceType
	 * @param DeviceType
	 * @param con
	 * @throws SQLException
	 */
	public void createDeviceType(DeviceType deviceType,Connection con) throws SQLException{
		DAO_Device dao = (DAO_Device) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_Device.class);
		Statement stmt = con.createStatement();
		log.debug("创建DeviceType: "+dao.saveDeviceType(deviceType));
		stmt.execute(dao.saveDeviceType(deviceType));		
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
	}
	/**  徐嘉
	 * 创建Device
	 * @param Device
	 * @param con
	 * @throws SQLException
	 */
	public void createDevice(Device device,Connection con) throws SQLException{
		DAO_Device dao = (DAO_Device) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_Device.class);
		Statement stmt = con.createStatement();
		log.debug("创建Device: "+dao.saveDevice(device));
		stmt.execute(dao.saveDevice(device));		
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
	}
	/**
	 *  通过设备编号查出设备
	 * @param id
	 * @param con
	 * @return
	 * @throws SQLException 
	 */
	public Device getDeviceByCode(String str_code,Connection con) throws SQLException{
		Device d = null;
		DAO_Device dao = (DAO_Device) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_Device.class);
		Statement stmt = con.createStatement();
		log.debug("通过设备编号查出设备号sql语句："+dao.getDeviceByCode(str_code));
		ResultSet rs = stmt.executeQuery(dao.getDeviceByCode(str_code));
		log.debug("通过设备编号获取设备列表---");
		while(rs.next()){
			d = new Device();
			d.setId(rs.getInt("int_id"));
			d.setName(rs.getString("str_devicename"));
			d.setCode(rs.getString("str_devicecode"));			
			d.setDescription(rs.getString("str_description"));
			log.debug("设备号："+rs.getInt("int_id")+"；设备名"+rs.getString("str_devicename")+"；设备编码："
					+rs.getString("str_devicecode")+"；描述："
					+rs.getString("str_description"));
		}
		if(rs!=null){
			rs.close();
			rs=null;
		}
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
		return d;
	}

	/**
	 * 通过ID查询设备
	 * 
	 * @param id
	 *            设备号
	 * @param con
	 *            连接对象
	 * @return 通过ID查询出的指令对象
	 * @throws java.sql.SQLException
	 */
	public Device getDeviceById(int id, Connection con)
			throws SQLException, ParseException {
		Device d = new Device();
		DAO_Device dao = (DAO_Device) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_Device.class);
		Statement stmt = con.createStatement();
		log.debug("通过ID查询设备SQL：" + dao.getDeviceById(id));
		ResultSet rs = stmt.executeQuery(dao.getDeviceById(id));
		if (rs.next()) {
			d.setId(rs.getInt("int_id"));
			d.setName(rs.getString("str_devicename"));
			d.setCode(rs.getString("str_devicecode"));
			d.setDescription(rs.getString("str_description"));			
		}
		if (stmt != null)
			stmt.close();
		return d;
	}
	
	/**
	 * 通过ID查询设备类型
	 * 
	 * @param id
	 *            设备号
	 * @param con
	 *            连接对象
	 * @return 通过ID查询出的指令对象
	 * @throws java.sql.SQLException
	 */
	public DeviceType getDeviceTypeById(int id, Connection con)
			throws SQLException, ParseException {
		DeviceType d = new DeviceType();
		DAO_Device dao = (DAO_Device) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_Device.class);
		Statement stmt = con.createStatement();
		log.debug("通过ID查询设备类型SQL：" + dao.getDeviceTypeById(id));
		ResultSet rs = stmt.executeQuery(dao.getDeviceTypeById(id));
		if (rs.next()) {
			d.setId(rs.getInt("int_id"));
			d.setName(rs.getString("str_statename"));
				
		}
		if (stmt != null)
			stmt.close();
		return d;
	}
	
	/**
	 * 通过ID查询id
	 * 
	 * @param id
	 *            设备号
	 * @param con
	 *            连接对象
	 * @return 通过ID查询出的指令对象
	 * @throws java.sql.SQLException
	 */
	public int getUnit_Device(int id, Connection con)
			throws SQLException, ParseException {
		int unitid=0;
		DAO_Device dao = (DAO_Device) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_Device.class);
		Statement stmt = con.createStatement();
		log.debug("通过ID查询生产单元号：" + dao.getUnit_Device(id));
		ResultSet rs = stmt.executeQuery( dao.getUnit_Device(id));
		if (rs.next()) {
			unitid=rs.getInt("id");			
		}
		log.info("单元id为："+unitid);
		if (stmt != null)
			stmt.close();
		return unitid;
	}
	/**
	 * 通过ID查询生产单元号
	 * 
	 * @param id
	 *            设备号
	 * @param con
	 *            连接对象
	 * @return 通过ID查询出的指令对象
	 * @throws java.sql.SQLException
	 */
	public String getUnitid_Device(int id, Connection con)
			throws SQLException, ParseException {
		String unitid="";
		DAO_Device dao = (DAO_Device) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_Device.class);
		Statement stmt = con.createStatement();
		log.debug("通过ID查询生产单元号：" + dao.getUnit_Device(id));
		ResultSet rs = stmt.executeQuery( dao.getUnit_Device(id));
	   while (rs.next()) {
			unitid+=rs.getString("int_unit")+",";		
		}
		log.info("单元号为："+unitid);
		if (stmt != null)
			stmt.close();
		return unitid;
	}
	/**
	 * 通过ID查询设备号
	 * 
	 * @param id
	 *            设备号
	 * @param con
	 *            连接对象
	 * @return 通过ID查询出的指令对象
	 * @throws java.sql.SQLException
	 */
	public String getTypeid_Device(int id, Connection con)
			throws SQLException, ParseException {
		String typeid="";
		DAO_Device dao = (DAO_Device) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_Device.class);
		Statement stmt = con.createStatement();
		log.debug("通过ID查询生产单元号：" + dao.getType_Device(id));
		ResultSet rs = stmt.executeQuery(dao.getType_Device(id));
		while(rs.next()) {
			typeid+=rs.getString("int_devicetype")+",";			
		}
		log.info("类型号为："+typeid);
		if (stmt != null)
			stmt.close();
		return typeid;
	}
	/**
	 * 通过ID查询id
	 * 
	 * @param id
	 *            设备号
	 * @param con
	 *            连接对象
	 * @return 通过ID查询出的指令对象
	 * @throws java.sql.SQLException
	 */
	public int getType_Device(int id, Connection con)
			throws SQLException, ParseException {
		int typeid=0;
		DAO_Device dao = (DAO_Device) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_Device.class);
		Statement stmt = con.createStatement();
		log.debug("通过ID查询生产单元号：" + dao.getType_Device(id));
		ResultSet rs = stmt.executeQuery(dao.getType_Device(id));
		if (rs.next()) {
			typeid=rs.getInt("id");			
		}
		log.info("类型id为："+typeid);
		if (stmt != null)
			stmt.close();
		return typeid;
	}
	 /**  徐嘉
	 * 更新Device对象
	 *
	 * @param Device
     * @param con
     *          数据库连接对象
	 * @throws SQLException
     *                  SQL异常
	 */
	public void updateDevice(Device device, Connection con)
			throws SQLException {
		DAO_Device dao = (DAO_Device) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_Device.class);
		Statement stmt = con.createStatement();
		log.debug("更新Device对象 "+dao.updateDevice(device));
		stmt.execute(dao.updateDevice(device));       
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}
	/**  徐嘉
	 * 更新Device_Type对象
	 *
	 * @param Device_Type
     * @param con
     *          数据库连接对象
	 * @throws SQLException
     *                  SQL异常
	 */
	public void updateDevice_Type(int type,int id, Connection con)
			throws SQLException {
		DAO_Device dao = (DAO_Device) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_Device.class);
		Statement stmt = con.createStatement();
		log.debug("更新Device_Type对象 "+dao.updateDevice_Type(type, id));
		stmt.execute(dao.updateDevice_Type(type, id));       
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}
	/**  徐嘉
	 * 更新Device_Unit对象
	 *
	 * @param Device_Unit
     * @param con
     *          数据库连接对象
	 * @throws SQLException
     *                  SQL异常
	 */
	public void updateDevice_Unit(int unit,int id, Connection con)
			throws SQLException {
		DAO_Device dao = (DAO_Device) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_Device.class);
		Statement stmt = con.createStatement();
		log.debug("更新Device_Unit对象 "+dao.updateDevice_Unit(unit, id));
		stmt.execute(dao.updateDevice_Unit(unit, id));       
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}
	/**  徐嘉
	 * 更新DeviceType对象
	 *
	 * @param DeviceType
     * @param con
     *          数据库连接对象
	 * @throws SQLException
     *                  SQL异常
	 */
	public void updateDeviceType(DeviceType devicetype, Connection con)
			throws SQLException {
		DAO_Device dao = (DAO_Device) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				DAO_Device.class);
		Statement stmt = con.createStatement();
		log.debug("更新Devicetype对象 "+dao.updateDeviceType(devicetype));
		stmt.execute(dao.updateDeviceType(devicetype));       
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}

}
