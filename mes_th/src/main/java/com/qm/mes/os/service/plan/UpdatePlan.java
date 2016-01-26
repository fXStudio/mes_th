package com.qm.mes.os.service.plan;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
/**功能:修改编辑页面的计划
 * @author 谢静天
 *
 */
public class UpdatePlan extends AdapterService{

	/**
	 * log 日志
	 */
	private final Log log = LogFactory.getLog(UpdatePlan.class);
	
	/**
	 * con 数据库连接
	 */
	private  Connection con=null;
	 
	/**
	 * int_id  //计划的id
	 */
	private String int_id=null;
	 
	/**
	 * description  //描述信息
	 */
	private String description=null;
	 
	/**
	 * planGroupId  //计划批次号
	 */
	private String planGroupId=null;
	 
	/**
	 * produceType  //产品类别标识
	 */
	private String produceType=null;
	
	/**
	 * produceName   //产品名称
	 */
	private String produceName=null;
	 
	/**
	 * count  //个数
	 */
	private String count=null;
	 
	/**
	 * planDate  //计划日期
	 */
	private String planDate=null;
	  
	/**
	 * produceMarker //产品标识
	 */
	private String produceMarker=null;
	  
	/**
	 * planOrder //计划顺序号
	 */
	private String planOrder=null;
	 
	/**
	 * workTeam  //班组
	 */
	private String workTeam=null;
	public boolean checkParameter(IMessage message, String processid) {
		con=(Connection)message.getOtherParameter("con");
		int_id=message.getUserParameterValue("int_id").trim();
		description=message.getUserParameterValue("description").trim();
		planGroupId=message.getUserParameterValue("planGroupId").trim();
		produceType=message.getUserParameterValue("produceType").trim();
		produceName=message.getUserParameterValue("produceName").trim();
		count=message.getUserParameterValue("count").trim();
		planDate=message.getUserParameterValue("planDate").trim();
		produceMarker=message.getUserParameterValue("produceMarker").trim();
		planOrder=message.getUserParameterValue("planOrder").trim();
		workTeam=message.getUserParameterValue("workTeam").trim();
		//输出log信息
	    String debug="int_id:" +int_id 
		+ " description:"+description+ " " +
		"planGroupId:"+planGroupId+ " " +
		"produceType:"+produceType+ " " +
		"produceName:"+produceName+ " " +
		"count:"+count+ " " +
		"planDate:"+planDate+ " " +
		"produceMarker:"+produceMarker+ " " +
		"planOrder:"+planOrder+ " " +
		"workTeam:"+workTeam+ "\n";
	    log.info("添加状态转换的参数: " + debug);
		if (int_id== null) {
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
			  //更新计划
			 Plan plan=new Plan();
			 
			 plan.setPlanDate((new SimpleDateFormat("yyyy-MM-dd")).parse(planDate));
			 plan.setPlanOrder(Integer.parseInt(planOrder));
             plan.setOrderFormId("");
             plan.setPlanGroupId(Integer.parseInt(planGroupId));
             plan.setProduceType(produceType);
             plan.setProduceName(produceName);
             plan.setAmount(Integer.parseInt(count));
             plan.setId(Integer.parseInt(int_id));
             plan.setDescription(description);
             plan.setProduceMarker(produceMarker);
             plan.setWorkTeam(workTeam);
			
             PlanFactory planfactory=new PlanFactory();
			 planfactory.updatePlanbyid(plan, con);
			log.debug( "更新计划成功!");
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
	        int_id=null;
	        description=null;
	      	planGroupId=null;
	        produceType=null;
	      	produceName=null;
	      	count=null;
	      	planDate=null;
	     }
}
