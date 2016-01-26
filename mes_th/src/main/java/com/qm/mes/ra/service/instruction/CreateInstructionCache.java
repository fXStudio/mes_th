/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
import com.qm.mes.ra.bean.ProduceUnit;
import com.qm.mes.ra.factory.InstructionCacheFactory;
import com.qm.mes.ra.factory.InstructionFactory;
import com.qm.mes.ra.factory.ProduceUnitFactory;

import java.util.List;

/**
 * 创建作业指令临时表
 *
 * @author YuanPeng
 */
public class CreateInstructionCache extends AdapterService {
    /**
     * 数据库连接对象
     */
    Connection con = null;
    /**
     * 该生产单元锁定台份
     */
    int LockNum;
    /**
     * 指令临时表工厂
     */
    InstructionCacheFactory instructionCacheFactory = null;
    /**
     * 指令工厂
     */
    InstructionFactory instructionFactory = null;
    /**
     * 生产单元工厂
     */
    ProduceUnitFactory unitFactory = new ProduceUnitFactory();
    boolean check;
    int sum ;
    /**
     * 生产单元ID
     */
    int ProduckUnitID;
    /**
     * 日期
     */
    String str_date = null;
    /**
     * 班次
     */
    String workOrder=null;
    /**
     * 未被锁定的指令ID序列
     */
    List<Integer> list = null;
    /**
     * 生产单元对象
     */
    ProduceUnit produceunit = null;
    /**
     * 该生产单元的可编辑状态号
     */
    int stateid =0;
	/**
	 * 日志
	 */
	private final Log log = LogFactory.getLog(CreateInstructionCache.class);

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
    public boolean checkParameter(IMessage message, String processid){
        try {
        	produceunit = new ProduceUnit();
            con = (Connection) message.getOtherParameter("con");
            ProduckUnitID = Integer.parseInt(message.getUserParameterValue("str_ProduceUnitID"));
            str_date = message.getUserParameterValue("str_date");
            workOrder = message.getUserParameterValue("workOrder");
            instructionCacheFactory = new InstructionCacheFactory();
            produceunit = unitFactory.getProduceUnitbyId(ProduckUnitID, con);
            stateid = produceunit.getInt_instructStateID();
            LockNum = produceunit.getInt_instCount();
            log.debug("生产单元号："+ProduckUnitID+"；生产日期："+str_date+"；班次："+workOrder+"；生产单元未上线状态："+
            		stateid+"；生产单元锁定台份："+LockNum);
            if(produceunit.getInt_delete()==1){
            	message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "该生产单元已被删除", this
								.getId(), processid, new Date(), null));
            	log.fatal("该生产单元已被删除");
            }
        } catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), processid, new Date(), sqle));
				log.fatal("数据库异常");
				return false;
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
                instructionFactory = new InstructionFactory();
				instructionCacheFactory = new InstructionCacheFactory();
                //取得非锁定的指令集合
                list = instructionFactory.getInstructionsByUnlock(ProduckUnitID,str_date,workOrder,stateid, LockNum, con);
                if(list.size() == 0){
                    message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "该条件的数据不存在", this
								.getId(), processid, new Date(), null));
                    log.fatal("未被锁定的指令不存在");
				return ExecuteResult.fail;
                }
                for(int j:list){
                    //将非锁定的指令集合存入临时表，并在指令表表明处于编辑状态
                	Instruction instruction = new Instruction();
                    instruction = instructionFactory.getInstructionById(j, con);
                    log.info("通过指令号查询指令对象成功");
                    instructionCacheFactory.saveInstructionCache(instruction, con);
                    log.info("通过指令号创建指令成功");
                    instructionFactory.editInstructionById(j, con);
                    log.info("通过指令号将指令变更为编辑状态成功");
                }
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
