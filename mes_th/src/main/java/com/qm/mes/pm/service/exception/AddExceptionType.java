package com.qm.mes.pm.service.exception;

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
import com.qm.mes.pm.bean.ExceptionType;
import com.qm.mes.pm.factory.ExceptionFactory;

/**
 * 添加异常类型
 * 
 * @author Xujia
 * 
 */

public class AddExceptionType extends AdapterService{
	/**
	 *  获得连接
	 */
	private Connection con = null;
	/**
	 * 异常类型名  
	 */
	private String str_name = null;
	/**
	 * 状态
	 */
	private String int_state = null;	
	
	ExceptionFactory factory = new ExceptionFactory();
	
	//日志
	private final Log log = LogFactory.getLog(AddExceptionType.class);
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		str_name = message.getUserParameterValue("str_name");
		int_state= message.getUserParameterValue("int_state");
		//int_sysdefault = message.getUserParameterValue("int_sysdefault");		
		
		//输出log信息
	    String debug="异常类型名：" + str_name + "；"+ "状态："+int_state;
	    log.debug("添加异常类型时用户提交的参数: " + debug);

		if (str_name == null || int_state == null ) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("异常类型名、状态、系统标示中有为空参数，退出服务。");
			return false;
		}

		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
				ExceptionType et = new ExceptionType();							
				et.setName(str_name);
				et.setState(new Integer(int_state));
				//et.setSysdefault(new Integer(int_sysdefault));				
				factory.createExceptionType(et, con);
				log.info("添加异常类型服务成功！");
				
				
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
		str_name = null;
		int_state = null;
		//int_sysdefault = null;		
		con = null;

	}

}
