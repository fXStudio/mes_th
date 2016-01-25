package mes.pm.service.device;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import mes.framework.AdapterService;
import mes.framework.DataBaseType;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.pm.dao.DAO_Device;
import mes.pm.factory.DeviceFactory;
import mes.system.dao.DAOFactoryAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 删除设备类型
 * 
 * @author xujia
 * 
 */

public class DeleteDeviceType extends AdapterService {
	/**
	 * 获得连接
	 */
	private Connection con = null;
	/**
	 *  规则id
	 */
	private String int_id = null;
	DeviceFactory factory = new DeviceFactory();
	Statement stmt = null;
	ResultSet rs = null;
	ResultSet rs1 = null;
	private final Log log = LogFactory.getLog(DeleteDeviceType.class);

	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		int_id = message.getUserParameterValue("int_id");
	
		if (int_id == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			return false;
		}
		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid) throws SQLException, Exception {
        try {
			try {
				DAO_Device dao = (DAO_Device) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Device.class);
		stmt = con.createStatement();	
		// 检查在设备里是否用到
		rs = stmt.executeQuery(dao.checkDeviceTypeById(Integer
				.parseInt(int_id)));
		int count3 = 0;
		if (rs.next()) {
			count3 = rs.getInt(1);
		}
		// 检查在异常记录里是否用到
		rs1 = stmt.executeQuery(dao.checkDTypeInrecordById(Integer.parseInt(int_id)));
		 log.info("检测ExceptionRecord表中是否有设备类型号的sql:"+ dao.checkDTypeInrecordById(Integer.parseInt(int_id)));
		 int count = 0;
		 if (rs1.next()) {
			count = rs1.getInt(1);
		  }		
		
		 //当用到时将不能删除
		if (count3 > 0 || count>1) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, "该设备类型已用，不能删除!",
					this.getId(), processid, new Date(), null));
			log.fatal("该设备类型已用，不能删除");
			return ExecuteResult.fail;
		} else {
				
				factory.delDeviceTypeById(new Integer(int_id), con);
				log.info("删除设备类型成功");
			} 
			}catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), processid, new Date(), sqle));
				log.error("删除设备类型时,未知异常" + sqle.toString());
				return ExecuteResult.fail;
			}
		} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error("删除设备类型时,未知异常" + e.toString());
			return ExecuteResult.fail;
		}finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
		}
		return ExecuteResult.sucess;
    }

	@Override
	public void relesase() throws SQLException {
		int_id = null;
		con = null;

	}

}