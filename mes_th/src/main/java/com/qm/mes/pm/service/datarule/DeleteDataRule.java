package com.qm.mes.pm.service.datarule;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.AdapterService;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.ServiceException;
import com.qm.mes.framework.ServiceExceptionType;
import com.qm.mes.pm.factory.DataRuleFactory;
import com.qm.mes.ra.service.state.DeleteConversionState;
/**
 * 删除数据规则
 * @author Xujia
 *
 */
public class DeleteDataRule extends AdapterService {
	/**
	 * 获得连接
	 */
	private Connection con = null;
	/**
	 * 规则id
	 */
	private String int_id = null;
	private final Log log = LogFactory.getLog(DeleteConversionState.class);
	Statement stmt = null;
	ResultSet rs = null;
	DataRuleFactory dataruleFactory = new DataRuleFactory();

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
				
				dataruleFactory.delDataRuleById(new Integer(int_id), con);
				log.debug("删除数据规则成功!");
				
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), processid, new Date(), sqle));
				log.error("删除规则时,未知异常" + sqle.toString());
				return ExecuteResult.fail;
			}
		} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error("删除规则时,未知异常" + e.toString());
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
