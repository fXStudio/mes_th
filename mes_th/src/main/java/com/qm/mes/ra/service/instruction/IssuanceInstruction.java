package com.qm.mes.ra.service.instruction;

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
import com.qm.mes.ra.factory.InstructionCacheFactory;
import com.qm.mes.util.SerializeAdapter;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 作业指令发布
 *
 * @author YuanPeng
 */
public class IssuanceInstruction extends AdapterService {
    /**
     * 数据库连接对象
     */
    private Connection con;
	/**
	 * 把并行数据变成串行数据的寄存器
	 */
	private SerializeAdapter sa = null;
    /**
     * 整型数组
     */
    int int_array[] ;
    /**
     * 选中的数组长度
     */
    int array_length ; 
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
	private final Log log = LogFactory.getLog(IssuanceInstruction.class);

	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		ProduceUnitID = Integer.parseInt(message
				.getUserParameterValue("str_ProduceUnitID"));
		str_date = message.getUserParameterValue("str_date");
		workOrder = message.getUserParameterValue("workOrder");
		log.debug("生产单元："+ProduceUnitID+"；生产日期："+str_date+"；班次："+workOrder+"；顺序号列表--\n");
        sa = new SerializeAdapter();
        //获取数组长度
        array_length = Integer.parseInt(message.getUserParameterValue("arr_length"));
        int_array = new int[array_length];
        try {
            //将字符串转换成整型数组类型
            int_array = (int[]) sa.toObject(message.getUserParameterValue("str_array"));
            
        } catch (IOException ex) {
            Logger.getLogger(IssuanceInstruction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IssuanceInstruction.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i =0 ;i<array_length;i++){
        	String debug = "";
        	if(i!=0||i!=array_length)debug+="；";
        	debug+="第"+i+"个顺序号为："+int_array[i];
        	log.debug(debug);
        }
        return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
				InstructionCacheFactory instructionCacheFactory = new InstructionCacheFactory();
                for(int j:int_array){
                	//指令发布，通过生产单元，生产日期，班次，指令顺序号
                    instructionCacheFactory.IssuanceByProduceUnitDateWorkorderOrder(ProduceUnitID,str_date,workOrder,j, con);
                }
                log.info("通过生产单元、生产日期、班次、顺序号发布临时指令成功");
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
		con = null;
	}

}

