package com.qm.mes.ra.service.instruction;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;
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
import com.qm.mes.util.SerializeAdapter;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 作业指令更新
 *
 * @author YuanPeng
 */
public class UpdateInstruction extends AdapterService {
    /**
     * 数据库连接对象
     */
    private Connection con;
    /**
     * 作业指令对象
     */
    private Instruction instruction ;
	SerializeAdapter sa = new SerializeAdapter();
	/**
	 * 日志
	 */
	private final Log log = LogFactory.getLog(UpdateInstruction.class);
	
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
            try{
            try{
                //将str_instruction转换成Instruction类型
                instruction = (Instruction) sa.toObject(message.getUserParameterValue("str_instruction"));
            } catch (IOException ex) {
                Logger.getLogger(UpdateInstruction.class.getName()).log(Level.SEVERE, null, ex);
            }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UpdateInstruction.class.getName()).log(Level.SEVERE, null, ex);
            }

		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
				InstructionCacheFactory instructionCacheFactory = new InstructionCacheFactory();
				//更新指令对象
                instructionCacheFactory.updateInstructionCache(instruction, con);
                log.info("更新临时指令成功");
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
		instruction = null;
		con = null;

	}

}
