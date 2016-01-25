package mes.ra.service.instruction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Level;
import java.util.logging.Logger;
import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.ra.factory.InstructionCacheFactory;
import mes.ra.bean.Instruction;

/**
 * 作业指令上移
 * 
 * @author YuanPeng
 */
public class UpInstruction extends AdapterService { 
	/**
	 * 数据库连接对象
	 */
	Connection con = null; 
	/**
	 * 作业指令顺序号
	 */
	int order =0;
	/**
	 * 生产单元号
	 */
	int ProduceUnitID; 
	/**
	 * 字符串型生产日期
	 */
	String str_date = null; 
	/**
	 * 字符串型班次
	 */
	String workOrder = null;
	Instruction instruction = new Instruction();
	Instruction instruction_new = new Instruction();
	InstructionCacheFactory instructionCacheFactory = new InstructionCacheFactory();
	int temp;
	boolean check;
	int sum; 
	/**
	 * 日志
	 */
	private final Log log = LogFactory.getLog(UpInstruction.class);
	/**
	 * 垃圾回收
	 * 
	 * @throws java.sql.SQLException
	 */
	@Override
	public void relesase() throws SQLException {
		con = null;
	}

	/**
	 * 检查参数
	 * 
	 * @param message
	 *            使用IMessage传递相关属性
	 * @param processid
	 *            流程ID
	 * @return boolean值 返回boolean值以显示成功与否
	 */
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		order = Integer.parseInt(message.getUserParameterValue("order"));
		ProduceUnitID = Integer.parseInt(message
				.getUserParameterValue("str_ProduceUnitID"));
		str_date = message.getUserParameterValue("str_date");
		workOrder = message.getUserParameterValue("workOrder");
		log.debug("指令顺序号："+order+"；生产单元号："+ProduceUnitID+"；班次："+workOrder+"；生产日期："+str_date);
		if (order == 0) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("顺序号为空");
			return false;
		}
		try {
				//通过生产单元号、生产日期、班次、指令顺序号查询指令
				instruction = instructionCacheFactory
						.getInstructionByProdUnitDateOrder(ProduceUnitID,str_date,workOrder,order, con);
				log.info("通过生产单元号、生产日期、班次、顺序号查询临时指令成功");
				if (instruction.getDelete() == 1) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.PARAMETERLOST,
							"该指令已经为删除状态！", this.getId(), processid,
							new java.util.Date(), null));
					log.fatal("该指令已经为删除状态");
					return false;
				}
				// 检测指令顺序号所在行是否为第一行
				check = instructionCacheFactory.checkFirst(ProduceUnitID,
						str_date, workOrder,instruction.getId(), con);
				if (check == true) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWN, "该记录为第一条，无法上移", this
									.getId(), processid, new Date(), null));
					log.fatal("该记录为第一条，无法上移");
					return false;
				}
		} catch (SQLException ex) {
			Logger.getLogger(DeleteInstruction.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		return true;
	}

	/**
	 * 执行服务
	 * 
	 * @param message
	 *            使用IMessage传递相关属性
	 * @param processid
	 *            流程ID
	 * @return ExecuteResult 执行结果
	 * @throws java.sql.SQLException
	 *             抛出SQL异常
	 * @throws java.lang.Exception
	 *             抛出其他异常
	 */
	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
		
				InstructionCacheFactory factory = new InstructionCacheFactory();
				//查询比Int_instructOrder小的对象
				instruction_new = factory.OrderMinus(ProduceUnitID,str_date,workOrder,
						instruction.getInstructionOrder(), con).get(0);
				log.info("查询上一顺序号指令成功");
				temp = instruction_new.getInstructionOrder();
				instruction_new.setInstructionOrder(instruction.getInstructionOrder());
				instruction.setInstructionOrder(temp);
				factory.updateInstructionCache(instruction_new, con);
				factory.updateInstructionCache(instruction, con);
				log.info("上移成功");
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), processid, new Date(), sqle));
				log.error("数据库操作异常");
				return ExecuteResult.fail;
			}
		} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error("未知异常");
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}
}
