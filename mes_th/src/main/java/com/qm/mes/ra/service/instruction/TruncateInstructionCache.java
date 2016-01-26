package com.qm.mes.ra.service.instruction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
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
import com.qm.mes.ra.factory.InstructionFactory;

/**
 * 删除作业指令临时表
 *
 * @author YuanPeng
 */
public class TruncateInstructionCache extends AdapterService {
    /**
     * 数据库连接对象
     */
    private Connection con;
    /**
     * 指令临时表工厂对象
     */
    InstructionCacheFactory instructionCacheFactory = null;
    /**
     * 指令工程对象
     */
    InstructionFactory instructionFactory = null;
    /**
     * 非锁定指令开始顺序号
     */
    int UnlockStartOrder;
    /**
     * 指令对象集合
     */
    List<Instruction> list = new ArrayList<Instruction>();
    /**
     * 生产单元号
     */
    int ProduceUnitID;
    /**
     * 生产日期
     */
    String str_date = null;
    /**
     * 班次
     */
    String workOrder = null;
	/**
	 * 日志
	 */
	private final Log log = LogFactory.getLog(TruncateInstructionCache.class);
    
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		ProduceUnitID = Integer.parseInt(message.getUserParameterValue("str_ProduceUnitID"));
		str_date = message.getUserParameterValue("str_date");
		workOrder = message.getUserParameterValue("workOrder");
        if(message.getUserParameterValue("str_UnlockStartOrder")==null||message.getUserParameterValue("str_UnlockStartOrder").equals("null"))
        	return false;
        UnlockStartOrder = Integer.parseInt(message.getUserParameterValue("str_UnlockStartOrder"));
    	log.debug("生产单元："+ProduceUnitID+"；生产日期："+str_date+"；班次："+workOrder+"；非锁定首顺序号："+UnlockStartOrder);
        instructionCacheFactory = new InstructionCacheFactory();
        try {
            //如果临时表中记录行数为0如何返回FALSE
            if (instructionCacheFactory.getInstructionCacheCount(con) == 0) {
                message.addServiceException(new ServiceException(ServiceExceptionType.DATABASEERROR, 
                        "临时表中没有数据", this.getId(), processid, new Date(), null));
                log.fatal("临时表中没有数据");
                return false;
            }
        } catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error("未知异常");
			return false;
		}
        return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
				instructionCacheFactory = new InstructionCacheFactory();
				instructionFactory = new InstructionFactory();
                //删除该生产单元所有数据
                instructionCacheFactory.DeleteInstructionCacheByProdUnitIdproducedateWorkorder(ProduceUnitID,str_date,workOrder, con);
                log.info("删除该生产单元该班次临时指令");
                //查询比Int_instructOrder大的对象
                list = instructionFactory.OrderPlus(ProduceUnitID,str_date,workOrder,UnlockStartOrder, con);
                log.info("查询未锁定指令完成");
                for(Instruction instruction_i:list)
                	//通过ID修改指令取消编辑状态
                	instructionFactory.uneditInstructionById(instruction_i.getId(), con);
                log.info("将未锁定指令解除编辑状态");
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

	@Override
	public void relesase() throws SQLException {
		try{
			con = null;
		}catch(Exception e){
			if(con!=null)
				con = null;
			e.printStackTrace();
		}
	}

}
