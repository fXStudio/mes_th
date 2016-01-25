package mes.os.service.plan;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;

import mes.os.factory.PlanFactory;

/**功能：撤销编辑
 * @author 谢静天
 *
 */
public class DisfrockPlan extends AdapterService {

	/**
	 * log 日志
	 */
	private final Log log = LogFactory.getLog(DisfrockPlan.class);
   
    /**
     * con  //数据库连接对象
     */
    Connection con = null;
   /**
   * workOrder  // 班次
  */
   private String workOrder=null;
   
   /**
   * overTime // 生产日期
   */
   private String overTime=null;
   
   /**
   * produceUnit // 生产单元id
   */
  private String produceUnit=null;
  
   

	 public boolean checkParameter(IMessage message, String processid) {
			
	        con = (Connection) message.getOtherParameter("con");
	        workOrder=message.getUserParameterValue("workOrder");
	        overTime=message.getUserParameterValue("overTime");
	        produceUnit=message.getUserParameterValue("produceUnit");
	      //输出log信息
		    String debug="workOrder:" +workOrder 
			+ " overTime:"+overTime+ " " +
			"produceUnit:"+produceUnit+ "\n";
		    log.info("添加状态转换的参数: " + debug);

	        if(produceUnit==null){
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
					// 撤销计划编辑
					PlanFactory factory=new PlanFactory();
					factory.disfrockplan(overTime, Integer.parseInt(produceUnit),  workOrder, con);
					
		          
					 log.debug( "撤销成功!");
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
		   overTime=null;
		   produceUnit=null;
		 
		  
	    }
}
