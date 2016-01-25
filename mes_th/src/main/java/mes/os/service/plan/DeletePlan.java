package mes.os.service.plan;

import java.io.IOException;
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
import mes.os.factory.*;

import mes.util.SerializeAdapter;

/**功能：删除计划
 * @author 谢静天
 *
 */
public class DeletePlan extends AdapterService {
	 
	/**
	 * log 日志
	 */
	private final Log log = LogFactory.getLog(DeletePlan.class);
   
    /**
     * con  //数据库连接对象
     */
    Connection con = null;
    
    /**
     * int_id //作业指令序号 
     */
    String int_id = null;
  
	/**
	 *  sa 接收数组参数
	 */
	SerializeAdapter sa = new SerializeAdapter();
    
    /**
     * int_array[] //整型数组
     */
    int int_array[] ;
   
    /**
     * array_length  //选中的数组长度
     */
    int array_length ;
 
   /**
     * workOrder // 班次
    */
    private String workOrder=null;
   
    /**
     * overTime  // 生产时间
     */
    private String overTime=null;
   
    /**
     * produceUnit  // 生产单元的id
     */
    private String produceUnit=null;
  
    /**
     * all   // 页面所以计划的个数
     */
    private int all=0;
    
	 public boolean checkParameter(IMessage message, String processid) {
	        con = (Connection) message.getOtherParameter("con");
	        workOrder=message.getUserParameterValue("workOrder");
	        overTime=message.getUserParameterValue("overTime");
	        produceUnit=message.getUserParameterValue("produceUnit");
		    all=Integer.parseInt(message.getUserParameterValue("all"));
	                //获取数组长度
	                array_length = Integer.parseInt(message.getUserParameterValue("arr_length"));
                    int_array = new int[array_length];
	               //输出log信息
	    		    String debug="workOrder:" +workOrder 
	    			+ " overTime:"+overTime+ " " +
	    			"produceUnit:"+produceUnit+ " " +
	    			"all:"+all+ " " +
	    			"array_length:"+array_length+ "\n";
	    		    log.info("添加状态转换的参数: " + debug);
	        try {
	            //将str_instruction转换成Instruction类型
	            int_array = (int[]) sa.toObject(message.getUserParameterValue("str_array"));
	        } catch (IOException ex) {
	           ex.getStackTrace();
	        } catch (ClassNotFoundException ex) {
	        	ex.getMessage();
	            
	        }
	        

	        return true;
	    }
	 public ExecuteResult doAdapterService(IMessage message, String processid) throws SQLException, Exception {
	        try {
				try {
					// 计划工厂
					PlanFactory factory=new PlanFactory();
					int flag=0;
					int j=0;
					// 按照从大到小排序
					for(int i=1;i<int_array.length;i++){
					 for(j=int_array.length-1;j>=i;j--)
						  { if(int_array[j]>int_array[j-1]) 
						  { flag = int_array[j-1]; 
						  int_array[j-1] = int_array[j]; 
						  int_array[j] = flag; }
					  }
					}
					
		         for(int k : int_array){
						//删除临时计划planorder=k
						factory.deleteplanbyPlanOrder(overTime, Integer.parseInt(produceUnit),  workOrder, k, con);
					     
						//调整计划顺序all为这个编辑对象的个数当全删除时当all《0时就不调顺序
						all--;
						if(all>0){
	                    factory.updatePlanOrder(overTime, Integer.parseInt(produceUnit), workOrder, k, con);
	              
						}
	                }
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
	        int_id = null;
	        workOrder=null;
	        overTime=null;
	        produceUnit=null;
	     
	    }
}
