package mes.os.service.workschedle;
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

/**删除工作时刻表
 * @author 包金旭
 *
 */

public class DeleteWorkSchedle extends AdapterService {
	 
	/**
	 * log 日志
	 */
	private final Log log = LogFactory.getLog(DeleteWorkSchedle.class);
	/**
	 *con 数据库连接
	 */
		private Connection con=null;
		/**
		 *int_id 工作时刻表id
		 */
		private String int_id=null;
		public boolean checkParameter(IMessage message, String processid) {
				con = (Connection) message.getOtherParameter("con");
				int_id=message.getUserParameterValue("int_id");
				/**
				  * 输出log信息
				  */
			    String debug="int_id:" +int_id +"\n";
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
				try {
					WorkSchedleFactory factory=new WorkSchedleFactory();
					factory.deleteWorkSchedleById(Integer.parseInt(int_id), con);
					log.debug( "删除工作时刻表成功!");
				}catch(SQLException sqle){
					message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
						.getId(), processid, new Date(), sqle));
					return ExecuteResult.fail;
				}
			}catch(Exception e){
				message.addServiceException(new ServiceException(
						ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
						processid, new java.util.Date(), e));
				return ExecuteResult.fail;
			}
			return ExecuteResult.sucess;
	}
		 
		 @Override
		 public void relesase() throws SQLException {
			 con = null;
		     int_id=null;
		   	
		 }
		 
		 
		 
		 
	}

	
	
	
	
	


