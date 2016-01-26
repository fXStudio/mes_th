package com.qm.mes.ra.service.WorkTO;

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
import com.qm.mes.ra.bean.WorkTO;
import com.qm.mes.ra.factory.WorkTOFactory;

/**添加班次管理
 * @author 包金旭
 *
 */
public class AddWorkTO extends AdapterService{
	private Connection con=null;
	/**
	  * 生产单元
	  */
	private String str_produceunit=null;
	
	/**
	  * 班次
	  */
	private String str_workOrder=null;
	
	private final Log log = LogFactory.getLog(AddWorkTO.class);
	
	public boolean checkParameter(IMessage message, String processid) {
			
			con = (Connection) message.getOtherParameter("con");
			str_produceunit=message.getUserParameterValue("str_produceunit").trim();
			
			str_workOrder=message.getUserParameterValue("str_workOrder").trim();
			
			/**
			  * 输出log信息
			  */
		    String debug="生产单元名：" + str_produceunit + "；"
			+ "班次："+str_workOrder;
		    
		    log.debug("添加班次记录时用户提交的参数: " + debug);
			
			if (str_produceunit== null) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
						processid, new java.util.Date(), null));
				log.fatal("生产单元名、班次中有为空参数，退出服务。");
				return false;
			}
			return true;	
	     }
	
	public ExecuteResult doAdapterService(IMessage message, String processid)
	throws SQLException, Exception {
		try {
			WorkTO workto=new WorkTO();
			workto.setProdunitid(Integer.parseInt(str_produceunit));
			
			workto.setWorkOrder(str_workOrder);     
				
			WorkTOFactory f=new WorkTOFactory();
			f.saveWorkTO(workto, con);
			log.info("添加班次记录成功！");
			
		 }
	    catch (SQLException sqle) {
		message.addServiceException(new ServiceException(
				ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
						.getId(), processid, new Date(), sqle));
		log.error("数据库异常，中断服务。");
		return ExecuteResult.fail;
	    }
	     catch (Exception e) {
	    message.addServiceException(new ServiceException(
			ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
			processid, new java.util.Date(), e));
	    log.error("未知异常，中断服务。");
	      return ExecuteResult.fail;
	    }
	    return ExecuteResult.sucess;
	   }
	
	
	
	public void relesase() throws SQLException {
	    con = null;
	    /**
		  * 生产单元
		  */
	    str_produceunit=null;
	    /**
		  * 班次
		  */
	    str_workOrder=null;
	    
    }
	
	
	
	
}
