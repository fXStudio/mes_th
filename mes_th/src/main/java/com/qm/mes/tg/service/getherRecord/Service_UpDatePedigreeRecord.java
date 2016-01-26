package com.qm.mes.tg.service.getherRecord;


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
import com.qm.mes.tg.bean.PedigreeRecord;
import com.qm.mes.tg.factory.RecordFactory;

/**
 * 作者:谢静天
 * 更新采集点主物料的值
 * @param GatherRecord
 * @param con
 * @throws SQLException
 * gatherRecord_editing.jsp 页面用到
 */
public class Service_UpDatePedigreeRecord extends AdapterService{
	private Connection con = null;
	//谱系记录表的id
	private String PedigreeRecord_id;
	//子物料要修改的值
	private String value;

	
	String origid;
	String origvalue;
	String cause; 
	String userid;
	//日志
	private final Log log = LogFactory.getLog(Service_UpDatePedigreeRecord.class);
	
	public boolean checkParameter(IMessage message, String processid) {
		
		con = (Connection) message.getOtherParameter("con");
		PedigreeRecord_id = message.getUserParameterValue("PedigreeRecord_id");
		value = message.getUserParameterValue("value");
		 origid= message.getUserParameterValue("origid");
	     origvalue=message.getUserParameterValue("origvalue");
	     cause=message.getUserParameterValue("cause");
	     userid=message.getUserParameterValue("userid");
		  if(cause==null||cause.equals(""))
		  {
			  cause="无原因";
		  }
		
		 if(value==null||value.equals(""))
		 {
			 value = message.getUserParameterValue("origvalue");
		 }
	
		if (PedigreeRecord_id == null || value == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("更新谱系记录--谱系记录号、物料值中有参数为空");
			return false;
		}
		return true;
	}
	
	public ExecuteResult doAdapterService(IMessage message, String processid)
	throws SQLException, Exception {
try {
	try {
		PedigreeRecord PedigreeRecord = new PedigreeRecord ();
		PedigreeRecord .setId(new Integer(PedigreeRecord_id));
		PedigreeRecord.setMaterielValue(value);
		log.debug("谱系记录号："+PedigreeRecord_id+"；物料值："+value);
		RecordFactory factory = new RecordFactory();
		factory.upDatePedigreeRecord(PedigreeRecord , con);
		log.info("更新谱系记录成功");
		factory.savePEDIGREEHISTORY(Integer.parseInt(origid), origvalue, cause, userid, con);
		log.info("创建谱系历史记录成功");
	} catch (SQLException sqle) {
		message.addServiceException(new ServiceException(
				ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
						.getId(), processid, new Date(), sqle));
		log.info("数据库异常");
		return ExecuteResult.fail;
	}
} catch (Exception e) {
	message.addServiceException(new ServiceException(
			ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
			processid, new java.util.Date(), e));
	log.info("未知异常");
	return ExecuteResult.fail;
}
return ExecuteResult.sucess;
}
	
	
	
	
	public void relesase() throws SQLException {
		con = null;
		PedigreeRecord_id=null;
	    value=null;

	}
}
