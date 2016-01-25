package mes.os.service.workschedle;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mes.os.bean.*;
import mes.os.factory.*;
import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;


/**添加工作时刻表
 * @author 包金旭
 *
 */
public class AddWorkSchedle extends AdapterService {
	private final Log log = LogFactory.getLog(AddWorkSchedle.class);
	private Connection con=null;
	/**
	  * 生产单元
	  */
	private String str_produceunit=null;
	/**
	  * 班次
	  */
	private String str_workOrder=null;
	/**
	  * 开工时间 小时
	  */
	private String str_workSchedleH=null;
	/**
	  * 开工时间 分钟
	  */
	private String str_workSchedleM=null;
	/**
	  * 开工时间 小时:分钟
	  */
	private String str_workSchedle=null;
	/**
	  * 提前期 天
	  */
	private String str_advanceTimeD=null;
	/**
	  * 提前期 小时
	  */
	private String str_advanceTimeH=null;
	/**
	  * 提前期 分钟
	  */
	private String str_advanceTimeM=null;
	/**
	  * 提前期 天：小时：分钟
	  */
	private String str_advanceTime=null;
	
	public boolean checkParameter(IMessage message, String processid) {
			
			con = (Connection) message.getOtherParameter("con");
			str_produceunit=message.getUserParameterValue("str_produceunit").trim();
			str_workOrder=message.getUserParameterValue("workOrder").trim();
			str_workSchedleH=message.getUserParameterValue("str_workSchedleH").trim();
			str_workSchedleM=message.getUserParameterValue("str_workSchedleM").trim();
			str_workSchedle=str_workSchedleH+":"+str_workSchedleM;
			str_advanceTimeD=message.getUserParameterValue("str_advanceTimeD").trim();
			str_advanceTimeH=message.getUserParameterValue("str_advanceTimeH").trim();
			str_advanceTimeM=message.getUserParameterValue("str_advanceTimeM").trim();
			str_advanceTime=str_advanceTimeD+":"+str_advanceTimeH+":"+str_advanceTimeM;
			/**
			  * 输出log信息
			  */
		    String debug="str_produceunit:" +str_produceunit 
			+ " str_workOrder:"+str_workOrder+ " " +
			"str_workSchedleH:"+str_workSchedleH+ " " +
			"str_workSchedleM:"+str_workSchedleM+ " " +
			"str_workSchedle:"+str_workSchedle+ " " +
			"str_advanceTimeD:"+str_advanceTimeD+ " " +
			"str_advanceTimeH:"+str_advanceTimeH+ " " +
			"str_advanceTimeM:"+str_advanceTimeM+ " " +
			"str_advanceTime:"+str_advanceTime+ "\n";
		    log.info("添加状态转换的参数: " + debug);
			if (str_produceunit== null) {
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
			
			WorkSchedle workschedle=new WorkSchedle();
			workschedle.setProdunitid(Integer.parseInt(str_produceunit));
			workschedle.setWorkOrder(str_workOrder);     
			workschedle.setWorkSchedle(str_workSchedle);
			workschedle.setAdvanceTime(str_advanceTime);	
			WorkSchedleFactory f=new WorkSchedleFactory();
			f.saveWorkSchedle(workschedle, con);
			log.debug( "添加工作时刻表成功!");
		 }
	    catch (SQLException sqle) {
		message.addServiceException(new ServiceException(
				ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
						.getId(), processid, new Date(), sqle));
		return ExecuteResult.fail;
	    }
	     catch (Exception e) {
	    message.addServiceException(new ServiceException(
			ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
			processid, new java.util.Date(), e));
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
	    /**
		  * 开工时间 小时
		  */
	    str_workSchedleH=null;
	    /**
		  * 开工时间 分钟
		  */
	    str_workSchedleM=null;
	    /**
		  * 开工时间 小时：分钟
		  */
	    str_workSchedle=null;
	    /**
		  * 提前期 天
		  */
	    str_advanceTimeD=null;
	    /**
		  * 提前期 小时
		  */
	    str_advanceTimeH=null;
	    /**
		  * 提前期 分钟
		  */
	    str_advanceTimeM=null;
	    /**
		  * 提前期 天：小时：分钟
		  */
	    str_advanceTime=null;
    }
	
	
	
	
}
