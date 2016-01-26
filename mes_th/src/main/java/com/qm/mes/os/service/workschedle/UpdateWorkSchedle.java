package com.qm.mes.os.service.workschedle;

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
import com.qm.mes.os.bean.WorkSchedle;
import com.qm.mes.os.factory.WorkSchedleFactory;

/**更改工作时刻表
 * @author baobao
 *
 */
public class UpdateWorkSchedle extends AdapterService{
	private final Log log = LogFactory.getLog(UpdateWorkSchedle.class);
		
	private Connection con=null;
	private String int_id=null;
	/**
	  * 生产单元 号-名
	  */
	private String str_produceunit1=null;
	/**
	  * 生产单元 号
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
	  * 开工时间 小时：分钟
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
	  * 提前期 天：小时:分钟
	  */
	private String str_advanceTime=null;
	
	public boolean checkParameter(IMessage message, String processid) {
		con=(Connection)message.getOtherParameter("con");
		int_id=message.getUserParameterValue("int_id").trim();
		str_produceunit1=message.getUserParameterValue("str_produceunit").trim();
		String pp[];
		pp = str_produceunit1.split("-");
		str_produceunit=pp[0];
		str_workOrder=message.getUserParameterValue("str_workOrder").trim();
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
	    String debug="int_id:" +int_id 
		+ " str_produceunit1:"+str_produceunit1+ " " +
		"str_produceunit:"+str_produceunit+ " " +
		"str_workOrder:"+str_workOrder+ " " +
		"str_workSchedleH:"+str_workSchedleH+ " " +
		"str_workSchedleM:"+str_workSchedleM+ " " +
		"str_workSchedle:"+str_workSchedle+ " " +
		"str_advanceTimeD:"+str_advanceTimeD+ " " +
		"str_advanceTimeH:"+str_advanceTimeH+ " " +
		"str_advanceTimeM:"+str_advanceTimeM+ " " +
		"str_advanceTime:"+str_advanceTime+ "\n";
	    log.info("添加状态转换的参数: " + debug);
		if (str_produceunit == null) {
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
				WorkSchedle workschedle=new WorkSchedle();
				workschedle.setId(Integer.parseInt(int_id));
				workschedle.setProdunitid(Integer.parseInt(str_produceunit));
				workschedle.setWorkOrder(str_workOrder);
				workschedle.setWorkSchedle(str_workSchedle);
				workschedle.setAdvanceTime(str_advanceTime);
				WorkSchedleFactory factory=new WorkSchedleFactory();
				factory.updateWorkSchedle(workschedle, con);
				log.debug( "删除工作时刻表成功!");
				}catch (SQLException sqle) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
							.getId(), processid, new Date(), sqle));
					return ExecuteResult.fail;
				}
					
		}catch (Exception e) {
			message.addServiceException(new ServiceException(
	    			ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
	    			processid, new java.util.Date(), e));
	    	return ExecuteResult.fail;
	    }
	    
	return ExecuteResult.sucess;
	}

	public void relesase() throws SQLException {
		con = null;
		int_id=null;
		 /**
		  * 生产单元
		  */
		str_produceunit1=null;
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
