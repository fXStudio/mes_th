package com.qm.mes.tg.service.gather;

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
import com.qm.mes.system.dao.DAOFactoryAdapter;
import com.qm.mes.tg.dao.IDAO_Gather;
import com.qm.mes.tg.factory.GatherFactory;

/**
 * 删除采集点
 * 
 * @author lida
 * 
 */
public class DeleteGather extends AdapterService {
	// 获得连接
	private Connection con = null;
	// 采集点号
	private String int_id = null;
	//日志
	private final Log log = LogFactory.getLog(DeleteGather.class);

	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		int_id = message.getUserParameterValue("int_id");
		//输出log信息
	    String debug="删除采集点号：" + int_id;
	    log.debug("删除采集点时用户提交的参数: " + debug);
		if (int_id == null) {
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
				GatherFactory factory = new GatherFactory();
				factory.delGatherById(new Integer(int_id), con);
				log.info("删除采集点服务成功！");
				IDAO_Gather dao = (IDAO_Gather) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_Gather.class);
				Statement stmt = con.createStatement();
				log.debug("删除qualitys: "+dao.delGather_Q(Integer.parseInt(int_id)));
				stmt.execute(dao.delGather_Q(Integer.parseInt(int_id)));
				
			if(stmt!=null){
				stmt.close();
				stmt=null;
			}
				
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
		int_id = null;
		con = null;

	}
}
