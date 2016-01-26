package com.qm.mes.pm.service.exceptionrecord;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.AdapterService;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.ServiceException;
import com.qm.mes.framework.ServiceExceptionType;
import com.qm.mes.pm.bean.ExceptionRecord;
import com.qm.mes.pm.factory.ExceptionRecordFactory;
/**
 * 关闭异常记录
 * 
 * @author Xujia
 * 
 */
public class CloseExceptionRecord extends AdapterService {

	/**
	 * 获得连接
	 */
	private Connection con = null;
	/**
	 * 关闭描述
	 */
	private String str_rediscription = null;	
	/**
	 * 序号
	 */
	private String int_id=null;
	/**
	 * 用户号
	 */
	private String userid = null;
	//工厂置前
	ExceptionRecordFactory factory = new ExceptionRecordFactory();
    //日志
	private final Log log = LogFactory.getLog(CloseExceptionRecord.class);
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		str_rediscription = message.getUserParameterValue("str_rediscription");		
		int_id=message.getUserParameterValue("int_id");
		userid = message.getUserParameterValue("userid");
		
		//输出log信息
	    String debug="关闭描述：" + str_rediscription + "；"+ "用户号："+userid;
	    log.debug("关闭异常时用户提交的参数: " + debug);

		if (str_rediscription == null || userid == null ) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("关闭描述、用户号中有为空参数，退出服务。");
			return false;
		}

		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			    
			ExceptionRecord er = new ExceptionRecord();	
			er.setId(Integer.parseInt(int_id));
			er.setRediscription(str_rediscription);
			er.setCloseuser(new Integer(userid));	
			factory.colseExceptionRecord(er, con);
				log.debug( "关闭异常记录成功");
				
		} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error( "添加状态操作时,未知异常" + e.toString());
			
			
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

	@Override
	public void relesase() throws SQLException {
		str_rediscription = null;
		userid = null;
		int_id=null;
		con = null;

	}

}

