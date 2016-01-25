package mes.pm.service.exception;

import java.sql.Connection;
import java.sql.SQLException;

import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.pm.bean.ExceptionType;
import mes.pm.factory.ExceptionFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 更新异常类型
 * 
 * @author Xujia
 * 
 */

public class UpdateExceptionType extends AdapterService {
	/**
	 * 获得连接 
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
	/**
	 * 序号 
	 */
	private String int_id=null;
	/**
	 * 工厂置前
	 */
	ExceptionFactory factory = new ExceptionFactory();		
   //日志
	private final Log log = LogFactory.getLog(UpdateExceptionType.class);
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		str_name = message.getUserParameterValue("str_name");
		int_state= message.getUserParameterValue("int_state");
		//int_sysdefault = message.getUserParameterValue("int_sysdefault");
		int_id=message.getUserParameterValue("int_id");
		
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
			    
			ExceptionType et = new ExceptionType();	
			et.setId(Integer.parseInt(int_id));
			et.setName(str_name);
			et.setState(new Integer(int_state));
			//et.setSysdefault(new Integer(int_sysdefault));					
			factory.updateExceptionType(et, con);
				log.debug( "更新异常类型成功");
				
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
		str_name = null;
		int_state = null;
		//int_sysdefault = null;
		int_id=null;
		con = null;

	}

}
