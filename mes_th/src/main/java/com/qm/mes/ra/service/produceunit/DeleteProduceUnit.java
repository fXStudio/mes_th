package com.qm.mes.ra.service.produceunit;

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
import com.qm.mes.ra.factory.ProduceUnitFactory;

/**删除生产单元
 * @author 谢静天
 *
 */
public class DeleteProduceUnit extends AdapterService {
	private final Log log = LogFactory.getLog(DeleteProduceUnit.class);
	private Connection con=null;
	/**
	 * 版本号
	 */
	private String int_id=null;
	public boolean checkParameter(IMessage message, String processid) {
			con = (Connection) message.getOtherParameter("con");
			int_id=message.getUserParameterValue("int_id");
           //输出log信息
		    String debug="int_id:" +int_id + "\n";
		    log.info("添加状态转换的参数: " + debug);
			if (int_id== null) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
						processid, new java.util.Date(), null));
				return false;
			}
			return true;
	 
	 }
	
	public ExecuteResult doAdapterService(IMessage message, String processid)
	throws SQLException, Exception {
		try {
			try {
				ProduceUnitFactory factory=new ProduceUnitFactory();
				factory.deleteProduceUnitById(Integer.parseInt(int_id), con);
				log.debug( "添加删除单元工厂成功!");
				
				new ProduceUnitFactory().delCunit(int_id, con);
				log.debug("删除子生产单元成功！");
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
						.getId(), processid, new Date(), sqle));
				log.error("删除单元操作时,数据库异常"	+ sqle.toString());
				return ExecuteResult.fail;
			}
		} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error( "删除单元操作时,未知异常" + e.toString());
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
