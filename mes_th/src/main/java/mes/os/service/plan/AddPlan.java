package mes.os.service.plan;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.os.factory.PlanFactory;
import mes.os.bean.*;
/**
 * 添加一条计划
 * 在函数checkParameter中计划顺序号不能为空。
 * @author 谢静天
 * 
 */
public class AddPlan extends AdapterService {
	
	/**
	 * log 日志
	 */
	private final Log log = LogFactory.getLog(AddPlan.class);
   
    /**
     * con  //数据库连接对象
     */
    Connection con = null;
   
    /**
     * workOrder  //计划顺序号
     */
    private String workOrder=null;
   
    /**
     * produceDate  // 生产日期
     */ 
    private String produceDate=null;
   
    /**
     * produnitid  // 生产单元id
     */
    private String produnitid=null;
   
    /**
     * workTeam  // 班组
     */
    private String workTeam=null;
   
    /**
     * planOrder  // 计划顺序号
     */
    private String planOrder=null;
    
    /**
     * planDate // 计划日期
     */
    private String planDate=null;
    
	/**
	 * description // 描述
	 */
	private String description=null;
	
	/**
	 * planGroupId // 计划批次号
	 */
	private String planGroupId=null;
	
	/**
	 * produceType // 产品类别标识
	 */ 
	private String produceType=null;
	
	/**
	 * produceName // 产品名 
	 */
	private String produceName=null;
	
	/**
	 * count // 数量
	 */
	private String count=null;
	
	/**
	 * produceMarker // 产品标识
	 */ 
	private String produceMarker=null;
	
 
	 public boolean checkParameter(IMessage message, String processid) {
			
	        con = (Connection) message.getOtherParameter("con");
	        workOrder=message.getUserParameterValue("workOrder").trim();
	        produceDate=message.getUserParameterValue("produceDate").trim();
	        produnitid=message.getUserParameterValue("produnitid").trim();
	        workTeam=message.getUserParameterValue("workTeam").trim();
	        planOrder=message.getUserParameterValue("planOrder").trim();
	        planDate=message.getUserParameterValue("planDate").trim();
	        description=message.getUserParameterValue("description").trim();
			planGroupId=message.getUserParameterValue("planGroupId").trim();
			produceType=message.getUserParameterValue("produceType").trim();
			produceName=message.getUserParameterValue("produceName").trim();
			count=message.getUserParameterValue("count").trim();
			produceMarker=message.getUserParameterValue("produceMarker").trim();
			//输出log信息
		    String debug="workOrder:" +workOrder 
			+ " produceDate:"+produceDate+ " " +
			"produnitid:"+produnitid+ " " +
			"workTeam:"+workTeam+ " " +
			"planOrder:"+planOrder+ " " +
			"planDate:"+planDate+ " " +
			"description:"+description+ " " +
			"planGroupId:"+planGroupId+ " " +
			"produceType:"+produceType+ " " +
			"produceName:"+produceName+ " " +
			"count:"+count+ " " +
			"produceMarker:"+produceMarker+ "\n";
		    log.info("添加状态转换的参数: " + debug);
	        if(planOrder==null){
	        	   message.addServiceException(new ServiceException(
	   					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
	   					processid, new java.util.Date(), null));
	   			return false;
	           }
	        

	        return true;
	    }
	 public ExecuteResult doAdapterService(IMessage message, String processid) throws SQLException, Exception {
	        try {
				try {
					// 创建计划
					 PlanFactory factory=new PlanFactory();
					 Plan plan =new Plan();
				
					 plan.setPlanDate((new SimpleDateFormat("yyyy-MM-dd")).parse(planDate));
		             plan.setProduceDate((new SimpleDateFormat("yyyy-MM-dd")).parse(produceDate));
		             plan.setOrderFormId("");
		             plan.setPlanGroupId(Integer.parseInt(planGroupId));
		             plan.setProduceType(produceType);
		             plan.setProduceName(produceName);
		             plan.setProduceMarker(produceMarker);
		             plan.setProdunitid(Integer.parseInt(produnitid));
		             plan.setWorkTeam(workTeam);
		             plan.setWorkOrder(workOrder);
		             plan.setAmount(Integer.parseInt(count));
		             plan.setVersioncode("temp");
		             plan.setUpload(0);
		             plan.setPlanOrder(Integer.parseInt(planOrder));
		             plan.setDescription(description);
		             factory.savePlan(plan, con);
		             log.debug( "添加计划成功!");
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
	 
	 
	 public void relesase() throws SQLException {
		     con = null;
		     workOrder=null;
		     produceDate=null;
		     produnitid=null;
		     planOrder=null;
		     planDate=null;
		     description=null;
			 planGroupId=null;
			 produceType=null;
			 produceName=null;
			 count=null;
		     produceMarker=null;
	    }

}
