package com.qm.mes.os.service.plan;


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
import com.qm.mes.os.factory.PlanFactory;


/**功能：上移下移作业计划
 * @author 谢静天
 *
 */
public class DownPlan extends AdapterService {
	 
	/**
	 * log 日志
	 */
	private final Log log = LogFactory.getLog(DownPlan.class);
   
    /**
     * con  //数据库连接对象
     */
    Connection con = null;
  
    /**
     * downid （与被选中计划相邻）被动互换计划的id
     */
    private  int downid=0;
    //要互换的id和顺序号
    /**
     * downorder  被动互换的顺序号
     */
    private int downorder=0;
       
    /**
     * selectid (当前选中的id)  主动互换计划id
     */
    private int selectid=0;
 
    /**
     * selectorder (当前选中计划顺序号) 主动互换的顺序号
     */
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
					// 下移计划
					PlanFactory factory=new PlanFactory();
					factory.down_or_upPlanOrderbyplanid(selectid, downorder, con);
					factory.down_or_upPlanOrderbyplanid(downid, selectorder, con);
					 log.debug( "下移成功!");
	                
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
	        selectid=0;
	        selectorder=0;
	    }
}
