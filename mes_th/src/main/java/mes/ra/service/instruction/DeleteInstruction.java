package mes.ra.service.instruction;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Level;
import java.util.logging.Logger;
import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.ra.factory.InstructionCacheFactory;
import mes.util.SerializeAdapter;

/**
 * 删除作业指令
 *
 * @author YuanPeng
 */
public class DeleteInstruction extends AdapterService {
    /**
     * 数据库连接对象
     */
    Connection con = null;
    /**
     * 作业指令序号
     */
    String int_id = null;
    /**
     * 整型数组
     */
    int int_array[] ;
    /**
     * 选中的数组长度
     */
    int array_length ; 
	/**
	 * 生产单元号
	 */
	int ProduceUnitID;
	/**
	 * 字符串型日期
	 */
	String str_date = null; 
	/**
	 * 字符串型班次
	 */
	String workOrder = null;
	SerializeAdapter sa = new SerializeAdapter();
	/**
	 * 日志
	 */
	private final Log log = LogFactory.getLog(DeleteInstruction.class);
	
    /**
     * 垃圾回收
     *
     * @throws java.sql.SQLException
     */
    @Override
    public void relesase() throws SQLException {
        con = null;
        int_id = null;
    }

    /**
     * 检查参数
     *
     * @param message
     *              使用IMessage传递相关属性
     * @param processid
     *              流程ID
     * @return boolean值
     *              返回boolean值以显示成功与否
     */
    @Override
    
    public boolean checkParameter(IMessage message, String processid) {
        con = (Connection) message.getOtherParameter("con");
        ProduceUnitID = Integer.parseInt(message.getUserParameterValue("str_ProduceUnitID"));
		str_date = message.getUserParameterValue("str_date");
		workOrder = message.getUserParameterValue("workOrder");
        //获取数组长度
        array_length = Integer.parseInt(message.getUserParameterValue("arr_length"));
        int_array = new int[array_length];
        
        try {
            //将str_instruction转换成int数组类型
            int_array = (int[]) sa.toObject(message.getUserParameterValue("str_array"));
        } catch (IOException ex) {
            Logger.getLogger(IssuanceInstruction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IssuanceInstruction.class.getName()).log(Level.SEVERE, null, ex);
        }
        String debug = "生产单元号："+ProduceUnitID+"；班次："+workOrder+"；生产日期为："+str_date;
        for(int i=0;i<array_length;i++){
        	if(i!=array_length)debug+="；";
        	debug+="第"+i+"个顺序号："+int_array[i];
        }
       
        return true;
    }

    /**
     * 执行服务
     *
     * @param message
     *              使用IMessage传递相关属性
     * @param processid
     *              流程ID
     * @return ExecuteResult
     *                  执行结果
     * @throws java.sql.SQLException
     *                          抛出SQL异常
     * @throws java.lang.Exception
     *                          抛出其他异常
     */
    @Override
    public ExecuteResult doAdapterService(IMessage message, String processid) throws SQLException, Exception {
        try {
			try {
				InstructionCacheFactory instructionCacheFactory = new InstructionCacheFactory();
				int flag=0;
				int j=0;
				for(int i=1;i<int_array.length;i++){
					for(j=int_array.length-1;j>=i;j--){
						if(int_array[j]>int_array[j-1]) { 
							flag = int_array[j-1]; 
							int_array[j-1] = int_array[j]; 
							int_array[j] = flag; 
						}
					}
				}
				for(int k : int_array){
					// 通过生产单元号、生产日期、指令顺序号删除临时指令
                    instructionCacheFactory.delInstructionCacheByProduceUnitDateWorkorderOrder(ProduceUnitID,str_date,workOrder,k, con);
                    log.info("删除临时指令成功");
                    //调整指令顺序:该生产日期、生产单元、大于该指令顺序号的指令顺序号减1
                    instructionCacheFactory.MinusInstructionOrder(ProduceUnitID, str_date,workOrder, k, con);
                    log.info("调整临时指令顺序成功");
                }
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), processid, new Date(), sqle));
				log.error("数据库异常");
				return ExecuteResult.fail;
			}
		} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error("未知异常");
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
    }
}
