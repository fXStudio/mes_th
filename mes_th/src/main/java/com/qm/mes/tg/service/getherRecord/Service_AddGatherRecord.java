/*
 * create by : chenpeng
 * date:20080721
 * description: about gatherRecord 
 */
package com.qm.mes.tg.service.getherRecord;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.AdapterService;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.ServiceException;
import com.qm.mes.framework.ServiceExceptionType;
import com.qm.mes.tg.bean.GatherRecord;
import com.qm.mes.tg.bean.MaterielRule;
import com.qm.mes.tg.factory.MaterielRuleFactory;
import com.qm.mes.tg.factory.RecordFactory;

public class Service_AddGatherRecord extends AdapterService {
	private Connection con = null;
	// 序号
	private String id;
	// 采集点序号
	private String gatherId = null;
	// 主物料名称
	private String materielName = null;
	// 主物料值
	private String materielValue = null;
	// 用户id
	private String userId = null;
	
	private String strPrs = null;
	
	// 采集的数据列表，第一位是主物料的值，依次向下是子物料的值
	private List<String> prs = new ArrayList<String>();
	//日志
	private final Log log = LogFactory.getLog(Service_AddGatherRecord.class);

	// 用";"隔开的String转化为List;
	public List<String> strToList(String prsStr) {
		List<String> inPrs = new ArrayList<String>();
		while (prsStr.indexOf(";") != -1) {
			inPrs.add(prsStr.substring(0, prsStr.indexOf(";")));
			prsStr = prsStr.substring((prsStr.indexOf(";") + 1));
		}
		inPrs.add(prsStr);
		return inPrs;
	}

	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		id = message.getUserParameterValue("id");
		gatherId = message.getUserParameterValue("gatherId");
		materielName = message.getUserParameterValue("materielName").trim();
		materielValue = message.getUserParameterValue("materielValue").trim();
		userId = message.getUserParameterValue("userId");
		strPrs = message.getUserParameterValue("strPrs");
		
		if (id == null || gatherId == null || materielName == null
				|| materielValue == null || userId == null || strPrs == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("添加过点记录中-记录号、采集点号、物料名、物料值、用户序号、主物料子物料值中有为空参数");
			return false;
		}
		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			RecordFactory recordFactory = new RecordFactory();
			// 物料验证规则工厂
			MaterielRuleFactory materielRuleFactroy = new MaterielRuleFactory();

			// 验证规则列表，第一位是主物料的验证规则，依次向下是子物料的验证规则
			List<MaterielRule> mrs = new ArrayList<MaterielRule>();

			GatherRecord gr = new GatherRecord(); // 过点记录
			gr.setId(Integer.valueOf(id));
			gr.setGatherId(Integer.valueOf(gatherId));
			gr.setMaterielName(materielName);
			gr.setMaterielValue(materielValue);
			gr.setUserId(Integer.valueOf(userId));
			// String型数据转化为list型
			prs = this.strToList(strPrs);
			mrs = materielRuleFactroy.getListByGid(Integer.valueOf(gatherId),
					con);
			log.debug("过点记录号："+id+"；采集点号："+gatherId+"；物料名："+materielName+"；物料值："+materielValue+
					"；用户序号："+userId+"；主子物料名："+strPrs+"；验证规则列表："+mrs);
			recordFactory.saveRecord(gr, prs, mrs, con);
			log.info("添加过点记录成功");
			return ExecuteResult.sucess;
		} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.DATABASEERROR, "数据库操作异常",
					this.getId(), processid, new Date(), null));
			log.error("数据库异常");
			e.printStackTrace();
			return ExecuteResult.fail;
		}
	}

	@Override
	public void relesase() throws SQLException {
		id = null;// 序号
		gatherId = null;// 采集点序号
		materielName = null;// 主物料名称
		materielValue = null;// 主物料值
		userId = null;// 用户id
		con = null;

	}
}
