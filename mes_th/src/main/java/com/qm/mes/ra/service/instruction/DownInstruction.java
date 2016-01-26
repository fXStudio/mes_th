package com.qm.mes.ra.service.instruction;

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
import com.qm.mes.ra.bean.Instruction;
import com.qm.mes.ra.factory.InstructionCacheFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 作业指令下移
 *
 * @author YuanPeng
 */
public class DownInstruction extends AdapterService {
    /**
     * 数据库连接对象
     */
    Connection con = null;
    /**
     * 作业指令序号
     */
    int order = 0;
    /**
     * 生产单元号
     */
    int ProduceUnitID ;
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
    int temp ;
    InstructionCacheFactory instructionCacheFactory = new InstructionCacheFactory();
    boolean check;
    int sum ;
  
	/**
	 * 日志
	 */
	private final Log log = LogFactory.getLog(DownInstruction.class);

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
     *              使用IMessage传递相关属性
     * @param processid
     *              流程ID
     * @return boolean值
     *              返回boolean值以显示成功与否
     */
    @Override
    public boolean checkParameter(IMessage message, String processid) {
        con = (Connection) message.getOtherParameter("con");
        order = Integer.parseInt(message.getUserParameterValue("order"));
        ProduceUnitID = Integer.parseInt(message.getUserParameterValue("str_ProduceUnitID"));
        str_date = message.getUserParameterValue("str_date");
        workOrder = message.getUserParameterValue("workOrder");
        log.debug("作业指令顺序号："+order+"；生产单元号："+ProduceUnitID+"；班次："+workOrder+"；生产日期："+str_date);
        if (order == 0) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("指令顺序号为空");
			return false;
		}
        try {
        	//通过生产单元号、生产日期、班次、指令顺序号查询指令
        	instruction = instructionCacheFactory.getInstructionByProdUnitDateOrder(ProduceUnitID, str_date,workOrder, order, con);
            log.info("通过生产单元号、生产日期、班次、指令顺序号查询指令对象成功");
            if (instruction.getDelete() == 1) {
                message.addServiceException(new ServiceException(
                        ServiceExceptionType.PARAMETERLOST, "该指令已经为删除状态！", this.getId(),
                        processid, new java.util.Date(), null));
                log.fatal("当前指令处于被删除状态");
                return false;
            }
            //检测指令顺序号所在行是否为最后一行
            check = instructionCacheFactory.checkLast(ProduceUnitID,str_date,workOrder,instruction.getInstructionOrder(), con);
            log.info("通过生产单元号、生产日期、班次、指令顺序号查询指令是否处于末端成功");
            if(check == true){
            	message.addServiceException(new ServiceException(
                        ServiceExceptionType.UNKNOWN, "该记录为最后一条，无法下移", this
                                .getId(), processid, new Date(),null));
                log.fatal("指令处于末端，不允许下移");
                return false;
            }
         } catch (SQLException ex) {
            Logger.getLogger(DeleteInstruction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    /**
     * 执行服务
     *
     * @param message
     *              使用IMessage传递相关属性
     * @param processid
     *              流程ID
     * @return ExecuteResult
     *                  执行结果
     * @throws java.sql.SQLException
     *                          抛出SQL异常
     * @throws java.lang.Exception
     *                          抛出其他异常
     */
    @Override
    public ExecuteResult doAdapterService(IMessage message, String processid) throws SQLException, Exception {
        try {
			try {
				//查询比Int_instructOrder大的对象
                instruction_new = instructionCacheFactory.OrderPlus(ProduceUnitID,str_date,workOrder,
                instruction.getInstructionOrder(), con).get(0);
                temp = instruction_new.getInstructionOrder();
                instruction_new.setInstructionOrder(instruction.getInstructionOrder());
                instruction.setInstructionOrder(temp);
                //更新指令对象
                instructionCacheFactory.updateInstructionCache(instruction_new, con);
                instructionCacheFactory.updateInstructionCache(instruction, con);
                log.info("指令下移成功");
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), processid, new Date(), sqle));
				log.error("数据库异常");
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
