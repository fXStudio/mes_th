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
/***
 *  暂时不用，原因是与DownPlan 功能重复。
 * @author 谢静天
 *
 */

public class UpPlan extends AdapterService {
	private final Log log = LogFactory.getLog(UpPlan.class);
    //数据库连接对象
    Connection con = null;
    //作业指令序号
   
  //要互换的id和顺序号
    
    private  int downid=0;
    private int downorder=0;
       //当前选中的id和顺序号
    private int selectid=0;
    private int selectorder=0;
 

	 public boolean checkParameter(IMessage message, String processid) {
	        con = (Connection) message.getOtherParameter("con");
	        downid=Integer.parseInt(message.getUserParameterValue("downid"));
	        downorder=Integer.parseInt(message.getUserParameterValue("downorder"));
	        selectid=Integer.parseInt(message.getUserParameterValue("selectid"));
	        selectorder=Integer.parseInt(message.getUserParameterValue("selectorder"));
	      //输出log信息
		    String debug="downid:" +downid 
			+ " downorder:"+downorder+ " " +
			"selectid:"+selectid+ " " +
			"selectorder:"+selectorder+ "\n";
		    log.info("添加状态转换的参数: " + debug);
	        if(downid==0||downorder==0||selectid==0||selectorder==0){
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
					  //上移计划
					PlanFactory factory=new PlanFactory();
					
					factory.down_or_upPlanOrderbyplanid(selectid, downorder, con);
					factory.down_or_upPlanOrderbyplanid(downid, selectorder, con);
					log.debug( "上移计划成功!");
	                
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
	        
		       downid=0;
		         downorder=0;
		           //当前选中的id和顺序号
		      selectid=0;
		        selectorder=0;
	    }
}
