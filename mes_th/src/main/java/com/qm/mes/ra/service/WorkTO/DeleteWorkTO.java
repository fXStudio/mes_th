package com.qm.mes.ra.service.WorkTO;

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
import com.qm.mes.ra.factory.WorkTOFactory;

/**删除班次管理
 * @author 包金旭
 *
 */
public class DeleteWorkTO extends AdapterService{
	private Connection con=null;
	private String int_id=null;
	/**
	  * 日志
	  */
	private final Log log = LogFactory.getLog(DeleteWorkTO.class);
	
	public boolean checkParameter(IMessage message, String processid) {
			con = (Connection) message.getOtherParameter("con");
			int_id=message.getUserParameterValue("int_id");
			String debug="删除序号：" + int_id;
		    log.debug("删除班组班次记录时用户提交的参数: " + debug);
			if (int_id== null) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
						processid, new java.util.Date(), null));
				log.fatal("序号为空，退出服务。");
				return false;
			}
			return true;
	 }
	 
	public ExecuteResult doAdapterService(IMessage message, String processid)
	throws SQLException, Exception {
		try {
			try {
				WorkTOFactory factory=new WorkTOFactory();
				factory.deleteWorkTOById(Integer.parseInt(int_id), con);
				log.info("添加班组班次记录服务成功！");
	 
			}catch(SQLException sqle){
				message.addServiceException(new ServiceException(
					ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
					.getId(), processid, new Date(), sqle));
				log.error("数据库异常，中断服务。");
				return ExecuteResult.fail;
			}
		}catch(Exception e){
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
		 con = null;
	     int_id=null;
	   	
	 }
	 
	 
	 
	 
}








