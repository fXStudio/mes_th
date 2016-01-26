package com.qm.mes.tg.service.gatheritem;

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
import com.qm.mes.tg.factory.GatherItemFactory;

/**
 * 删除采集点属性
 * 
 * @author lida
 * 
 */
public class DeleteGatherItem extends AdapterService {
	// 获得连接
	private Connection con = null;
	// 采集点属性号
	private String int_gatherid = null;
	//日志
	private final Log log = LogFactory.getLog(DeleteGatherItem.class);

	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		int_gatherid = message.getUserParameterValue("int_gatherid");
		//输出log信息
	    String debug="采集点号：" + int_gatherid;
	    log.debug("添加采集点时用户提交的参数: " + debug);
		if (int_gatherid == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("采集点号为空，退出服务。");
			return false;
		}

		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
				GatherItemFactory factory = new GatherItemFactory();
				factory.delGatherItemByGid(new Integer(int_gatherid), con);
				log.info("添加采集点服务成功！");
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
		int_gatherid = null;
		con = null;

	}

}
