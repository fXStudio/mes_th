package com.qm.mes.pm.service.distributionaccept;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
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
import com.qm.mes.pm.bean.DistributionAccept;
import com.qm.mes.pm.bean.DistributionDoc;
import com.qm.mes.pm.factory.DistributionAcceptFactory;
import com.qm.mes.pm.factory.DistributionDocFactory;
import com.qm.mes.ra.factory.InstructionFactory;

/**
 * 添加配送确认单
 * 
 * @author Ypeng
 * 别名   AddDistriAccept
 */
public class AddDistributionAccept extends AdapterService {
	/**
	 * 获得连接
	 */
	private Connection con = null;
	/**
	 * 生产单元号
	 */
	private int produnitid;
	/**
	 * 物料值
	 */
	private String materielValue = null;
	/**
	 * 物料类型
	 */
	private String materiel = null;
	/**
	 * 配送指示单列表
	 */
	List<DistributionDoc> list_DistributionDoc = new ArrayList<DistributionDoc>();
	/**
	 * 日志
	 */
	private final Log log = LogFactory.getLog(AddDistributionAccept.class);
	/**
	 * 配送确认单工厂
	 */
	DistributionAcceptFactory factory = new DistributionAcceptFactory();
	/**
	 * 指令工厂
	 */
	InstructionFactory instructionFactory = new InstructionFactory();
	/**
	 * 配送指示单工厂
	 */
	DistributionDocFactory distributionDocFactory = new DistributionDocFactory();
	
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		produnitid = Integer.parseInt(message.getUserParameterValue("produnitid"));
		materielValue = message.getUserParameterValue("materielValue");
		try {
			materiel = instructionFactory.getInstructionbymaterile(materielValue,produnitid, con).getProduceType();
			list_DistributionDoc=distributionDocFactory.getDistributionDocsByRequestProUnitId(produnitid,con);
		
		
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
				for(DistributionDoc dd:list_DistributionDoc){
					if(dd.getMaterielType().equals(materiel)){
						DistributionAccept distributionAccept = new DistributionAccept();
						distributionAccept.setDisDocId(dd.getId());
						distributionAccept.setState(0);
						distributionAccept.setMateriel(materiel);
						factory.saveDistributionAccept(distributionAccept, con);
						log.debug("配送指示单号："+dd.getId()+"；状态号："+0+"；物料类型："+materiel);
					}
					
				}
				log.info("添加配送确认单服务成功！");
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
		con = null;

	}

}
