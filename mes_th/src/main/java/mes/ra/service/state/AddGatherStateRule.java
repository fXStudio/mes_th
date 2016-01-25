package mes.ra.service.state;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import mes.ra.factory.*;
import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**添加采集点状态规则
 * author : 谢静天
 */
public class AddGatherStateRule extends AdapterService{
	/**
	 *  采集点的id
	 */
	private String int_gatherid;
	/**
	 * 连接
	 */
	private Connection con = null;
	/**
	 * 其他的规则个数
	 */
	private  String sum;
	/**
	 * 默认的执行规则状态
	 */
	private String go;
	private String int_stateconversionid;
	private final Log log = LogFactory.getLog(AddGatherStateRule.class);

	public boolean checkParameter(IMessage message, String processid) {
		int_gatherid=message.getOutputParameterValue("int_gatherid");
		con=(Connection) message.getOtherParameter("con");
		sum=message.getUserParameterValue("sumnumber");
        go=message.getUserParameterValue("go");
        if(sum==null){
        	sum="0";
        }
		if (int_gatherid == null) {
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
			GatherStateRuleFactory factory=new GatherStateRuleFactory();
			factory.delgatherstaterule(Integer.parseInt(int_gatherid), con);			
			if(go!=null){
				factory.saveGatherStateRule(Integer.parseInt(int_gatherid), Integer.parseInt(go), 1, con);
			}
			for(int i=1;i<=Integer.parseInt(sum);i++){
				int_stateconversionid=message.getUserParameterValue("go"+i);   
				factory.saveGatherStateRule(Integer.parseInt(int_gatherid), Integer.parseInt(int_stateconversionid), 0, con);
				log.debug("创建工厂成功!");
			}
		}
		catch (SQLException sqle) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
					.getId(), processid, new Date(), sqle));
			log.error("删除状态时,未知异常" + sqle.toString());
			return ExecuteResult.fail;
		}
		catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error("删除状态时,未知异常" + e.toString());
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}
	public void relesase() throws SQLException {
	    con = null;
	    int_gatherid=null;
	    int_stateconversionid=null;
	    sum=null;
    }
}
