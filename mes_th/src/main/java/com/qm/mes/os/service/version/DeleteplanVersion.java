package com.qm.mes.os.service.version;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.AdapterService;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.ServiceException;
import com.qm.mes.framework.ServiceExceptionType;
import com.qm.mes.os.bean.*;
import com.qm.mes.os.factory.*;
/**功能：删除计划版本
 * @author 谢静天
 *
 */
public class DeleteplanVersion extends AdapterService{
	
	/**
	 * log //日志
	 */
	private final Log log = LogFactory.getLog(DeleteplanVersion.class);
	
	/**
	 * con //数据库连接
	 */
	private Connection con=null;
	
	/**
	 * int_id//版本号id
	 */
	private String int_id=null;
	 
	/**
	 * versionfactory  //版本工厂
	 */
	private  VersionFactory versionfactory=new VersionFactory();
	 
	/**
	 * planfactory  //计划工厂
	 */
	private  PlanFactory planfactory=new PlanFactory();
	 public boolean checkParameter(IMessage message, String processid) {
		
		      con = (Connection) message.getOtherParameter("con");
			  int_id=message.getUserParameterValue("int_id");
			 
			  String debug="int_id:" +int_id+"\n";
		      log.info("添加状态转换的参数: " + debug);
			
		   if(int_id==null){
				message.addServiceException(new ServiceException(
						ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
						processid, new java.util.Date(), null));
				return false;
			}

		return true;
	 }
	 

		public ExecuteResult doAdapterService(IMessage message, String processid)
		throws SQLException, Exception {
	try {
		try {
			 
			 Version version=new Version();
			  //通过版本的id值 得到版本号到计划表中删除计划信息
			 version =versionfactory.getVersionbyid(Integer.parseInt(int_id), con);
			 String str_versioncode= version.getVersionCode();
			 planfactory.deletePlanbyversioncode(str_versioncode, con);
			 versionfactory.deleteversionbyid(Integer.parseInt(int_id), con);
			 log.debug( "删除计划版本成功!");
			 
  } catch (SQLException sqle) {
	message.addServiceException(new ServiceException(
			ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
					.getId(), processid, new Date(), sqle));
	return ExecuteResult.fail;
}
} catch (Exception e) {
message.addServiceException(new ServiceException(
		ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
		processid, new java.util.Date(), e));
return ExecuteResult.fail;
}
return ExecuteResult.sucess;
}
	 
	 @Override
	   public void relesase() throws SQLException {
	   	    con = null;
	        int_id=null;
	    
	   	
	   }
}
