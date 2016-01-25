package mes.pm.service.exception;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.pm.bean.ExceptionCause;
import mes.pm.factory.ExceptionFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 添加异常原因
 * 
 * @author Xujia
 * 
 */

public class AddExceptionCause extends AdapterService{
	/**
	 * 获得连接
	 */
	private Connection con = null;
	/**
	 * 异常原因名
	 */
	private String str_name = null;
	/**
	 *   状态
	 */
	private String int_state = null;
	ExceptionFactory factory = new ExceptionFactory();
	
	
	//日志
	private final Log log = LogFactory.getLog(AddExceptionCause.class);
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		str_name = message.getUserParameterValue("str_name");
		int_state= message.getUserParameterValue("int_state");
		
		
		//输出log信息
	    String debug="异常类型名：" + str_name + "；"+ "状态："+int_state;
	
	    log.debug("添加异常类型时用户提交的参数: " + debug);

		if (str_name == null || int_state == null ) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("异常原因名、状态中有为空参数，退出服务。");
			return false;
		}

		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
				ExceptionCause ec = new ExceptionCause();							
				ec.setName(str_name);
				ec.setState(new Integer(int_state));	
				factory.createExceptionCause(ec, con);
				log.info("添加异常原因服务成功！");
				
				
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
		con = null;

	}

}
