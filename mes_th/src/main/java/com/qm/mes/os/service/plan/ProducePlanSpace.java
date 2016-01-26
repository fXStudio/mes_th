package com.qm.mes.os.service.plan;

import java.util.*;
import java.sql.*;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.AdapterService;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.ServiceException;
import com.qm.mes.framework.ServiceExceptionType;
import com.qm.mes.os.bean.*;
import com.qm.mes.os.factory.*;
/**功能：生成编辑空间
 * @author 谢静天
 *
 */
public class ProducePlanSpace extends AdapterService {

	/**
	 * log 日志
	 */
	private final Log log = LogFactory.getLog(ProducePlanSpace.class);

	/**
	 * con 连接数据库
	 */
	private Connection con=null;
	
	/**
	 * produnitid // 生产单元
	 */
	private String produnitid =null;
	
	/**
	 * planfactory // 计划工厂
	 */
	private PlanFactory planfactory=new PlanFactory();
	
	/**
	 * overtime // 生产日期
	 */
	private String overtime=null;
	
	/**
	 * workOrder // 班次
	 */
	private String workOrder=null;

	public boolean checkParameter(IMessage message, String processid) {
		con=(Connection)message.getOtherParameter("con");
		produnitid=message.getUserParameterValue("int_id");
		overtime=message.getUserParameterValue("overtime");
		workOrder=message.getUserParameterValue("workOrder");
		//输出log信息
	    String debug="produnitid:" +produnitid 
		+ " overtime:"+overtime+ " " +
		"workOrder:"+workOrder+ "\n";
	    log.info("添加状态转换的参数: " + debug);
		if (produnitid== null) {
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
			List<Plan>  listplan=new ArrayList<Plan>();
			listplan=planfactory.getPlanbyProdateProidWorder(overtime, Integer.parseInt(produnitid), workOrder, con);
			// 到查找生产日期和生产单元班次的计划，copy生成新的计划
			 Iterator<Plan> iter=listplan.iterator();
			// 生产版本号为temp的编辑计划
		      while(iter.hasNext()){
		    	  Plan plan=(Plan)iter.next();
		    	  plan.setUpload(0);
		    	  plan.setOrderFormId(" ");
		    	  plan.setVersioncode("temp");//admin 为版本号
		    	  planfactory.savePlan(plan, con);
		      }
		      log.debug( "计划保存成功!");
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
        produnitid =null;
    	overtime=null;
        workOrder=null;
      
   

     }
	
	
	
}
