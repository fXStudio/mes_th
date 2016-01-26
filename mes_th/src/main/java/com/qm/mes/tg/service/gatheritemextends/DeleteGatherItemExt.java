/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.qm.mes.tg.service.gatheritemextends;

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
import com.qm.mes.tg.factory.GatherItemExtFactory;

/**
 * 删除采集点属性
 *
 * @author YuanPeng
 */
public class DeleteGatherItemExt extends AdapterService {
    //数据库连接对象
    Connection con = null;
    //采集点属性序号
    String int_id = null;
	//日志
	private final Log log = LogFactory.getLog(DeleteGatherItemExt.class);

    /**
     * 垃圾回收
     *
     * @throws java.sql.SQLException
     */
    @Override
    public void relesase() throws SQLException {
        con = null;
        int_id = null;
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
		int_id = message.getUserParameterValue("int_id");
		//输出log信息
	    String debug="采集点扩展属性号：" + int_id;
	    log.debug("删除采集点扩展属性时用户提交的参数: " + debug);
		if (int_id == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("扩展属性号为空，退出服务。");
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
				GatherItemExtFactory factory = new GatherItemExtFactory();
				factory.delGatherItemExtById(new Integer(int_id), con);
				log.info("删除采集点扩展属性成功。");
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


}
