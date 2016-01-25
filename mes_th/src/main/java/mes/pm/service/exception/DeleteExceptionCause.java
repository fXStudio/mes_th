package mes.pm.service.exception;

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
import mes.pm.dao.DAO_Exception;
import mes.pm.factory.ExceptionFactory;
import mes.system.dao.DAOFactoryAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 删除异常类型
 * 
 * @author Xujia
 * 
 */
public class DeleteExceptionCause  extends AdapterService  {
	/**
	 * 获得连接
	 */
	private Connection con = null;
	/**
	 * 规则id
	 */
	private String int_id = null;
	private final Log log = LogFactory.getLog(DeleteExceptionType.class);
	Statement stmt = null;
	ResultSet rs = null;
	ResultSet rs1=null;
	//工厂置前
	ExceptionFactory factory = new ExceptionFactory();		

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
				DAO_Exception dao = (DAO_Exception) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						DAO_Exception.class);
		 stmt = con.createStatement();
		 rs = stmt.executeQuery(dao.checkExceptionCauseById(Integer.parseInt(int_id)));
		 log.info("检测ExceptionRecord表中是否有异常原因号的sql:"+ dao.checkExceptionCauseById(Integer.parseInt(int_id)));
		 int count = 0;
		 if (rs.next()) {
			count = rs.getInt(1);
		  }
		 rs1=stmt.executeQuery(dao.getExceptionCauseById(Integer.parseInt(int_id)));
		 log.info("看是否启用："+dao.getExceptionCauseById(Integer.parseInt(int_id)));
		 int state=0;
		 if(rs1.next())
		 { state=rs1.getInt("int_state");
		 }
		 log.info("启用信息："+state);
		 if ((Integer.parseInt(int_id))==21 ||(Integer.parseInt(int_id))==6)  {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.UNKNOWN, "物料/设备原因不能删除!",
						this.getId(), processid, new Date(), null));
				log.fatal("物料/设备原因不能删除");
				return ExecuteResult.fail;
			} else if(count > 0 || state==1){
				message.addServiceException(new ServiceException(
						ServiceExceptionType.UNKNOWN, "该异常原因在异常记录中已用或者已启用，不能删除!",
						this.getId(), processid, new Date(), null));
				log.fatal("该异常原因在异常记录中已用或已启用，不能删除");
				return ExecuteResult.fail;
			}else {		
			
				factory.delExceptionCauseById(new Integer(int_id), con);
				log.debug("创建工厂成功!");
			}	
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), processid, new Date(), sqle));
				log.error("删除异常原因时,未知异常" + sqle.toString());
				return ExecuteResult.fail;
			}
		} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error("删除异常原因时,未知异常" + e.toString());
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
    }

	@Override
	public void relesase() throws SQLException {
		int_id = null;
		con = null;

	}

}