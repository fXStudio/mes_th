package com.qm.mes.ra.service.instruction;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.AdapterService;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.ServiceException;
import com.qm.mes.framework.ServiceExceptionType;
import com.qm.mes.ra.factory.InstructionCacheFactory;
/**
 * 作业指令全部发布
 *
 * @author YuanPeng
 */
public class IssuanceAllInstruction extends AdapterService {
    /**
     * 数据库连接对象
     */
    private Connection con;
	/**
	 * 生产单元号
	 */
	int ProduceUnitID;
	/**
	 * 字符串型日期
	 */
	String str_date = null;
	/**
	 * 字符串型班次
	 */
	String workOrder = null;
	/**
	 * 日志
	 */
	private final Log log = LogFactory.getLog(IssuanceAllInstruction.class);

	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		ProduceUnitID = Integer.parseInt(message
				.getUserParameterValue("str_ProduceUnitID"));
		str_date = message.getUserParameterValue("str_date");
		workOrder = message.getUserParameterValue("workOrder");
		log.debug("生产单元号："+ProduceUnitID+"；班次："+workOrder+"；生产日期："+str_date);
        return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			
			InstructionCacheFactory instructionCacheFactory = new InstructionCacheFactory();
			//发布该生产单元、生产日期的所有指令
            instructionCacheFactory.IssuanceAllByProduceUnitDateWorkorder(ProduceUnitID,str_date,workOrder, con);
			log.info("发布所有指令成功");
		} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error("未知异常");
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

	@Override
	public void relesase() throws SQLException {
		con = null;
	}

}

