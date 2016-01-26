package com.qm.mes.tg.service.gather;

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
import com.qm.mes.tg.bean.Gather;
import com.qm.mes.tg.factory.GatherFactory;

/**
 * 添加采集点
 * 
 * @author lida
 * 
 */
public class AddGather extends AdapterService {
	// 获得连接
	private Connection con = null;
	// 采集点名
	private String str_name = null;
	// 描述信息
	private String str_desc = null;
	// 生产单元号
	private String int_produnitid = null;
	// 主物料标识规则号
	private String int_materielruleid = null;
	//日志
	private final Log log = LogFactory.getLog(AddGather.class);
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		str_name = message.getUserParameterValue("str_name");
		str_desc = message.getUserParameterValue("str_desc");
		int_produnitid = message.getUserParameterValue("int_produnitid");
		int_materielruleid = message.getUserParameterValue("int_materielruleid");

		//输出log信息
	    String debug="采集点名：" + str_name + "；"+ "生产单元号："+int_produnitid+ ";"
		+ "物料标识规则号："+int_materielruleid+ ";"+"采集点描述："+str_desc;
	    log.debug("添加采集点时用户提交的参数: " + debug);

		if (str_name == null || str_desc == null || int_produnitid == null
				|| int_materielruleid == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("采集点名、生产单元号、物料标识规则号、采集点描述中有为空参数，退出服务。");
			return false;
		}

		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
				Gather gather = new Gather();
				//谢静天添加
				gather.setName(str_name);
				gather.setDesc(str_desc);
				gather.setProdunitId(new Integer(int_produnitid));
				gather.setMaterielruleId(new Integer(int_materielruleid));
				GatherFactory factory = new GatherFactory();
				factory.saveGather(gather, con);
				log.info("添加采集点服务成功！");
				Gather g = new Gather();
				g = factory.getGatherByName(str_name, con);
				message.setOutputParameter("int_gatherid", String.valueOf(g
						.getId()));
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
		str_name = null;
		str_desc = null;
		int_produnitid = null;
		int_materielruleid = null;
		con = null;

	}

}
