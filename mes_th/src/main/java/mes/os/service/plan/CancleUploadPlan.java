package mes.os.service.plan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mes.framework.AdapterService;
import mes.framework.DataBaseType;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.framework.dao.DAOFactory_UserManager;
import mes.framework.dao.IDAO_UserManager;
import mes.os.bean.UpLoadRecord;
import mes.os.factory.PlanFactory;
import mes.os.factory.UploadFactory;

/**功能：取消发布作业计划
 * 在checkParameter中生产单元id不能为空
 * @author 谢静天
 *
 */
public class CancleUploadPlan extends AdapterService {
	
	/**
	 * log 日志
	 */
	private final Log log = LogFactory.getLog(CancleUploadPlan.class);

	/**
	 * con 连接数据库
	 */
	private Connection con=null;
	
	/**
	 * stmt 向数据库发送sql语句
	 */
	private Statement stmt=null;
	
	/**
	 * produnitid // 生产单元id
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
	
	/**
	 * userid // 用户的id
	 */
	private String userid=null;

	/**
	 * versioncode 	// 版本号
	 */
	private String versioncode=null;
	
	public boolean checkParameter(IMessage message, String processid) {
		con=(Connection)message.getOtherParameter("con");
		produnitid=message.getUserParameterValue("int_id");
		overtime=message.getUserParameterValue("overtime");
		workOrder=message.getUserParameterValue("workOrder");
		userid=message.getUserParameterValue("userid");
		versioncode=message.getUserParameterValue("versioncode");
		//输出log信息
	    String debug="produnitid:" +produnitid 
		+ " overtime:"+overtime+ " " +
		"workOrder:"+workOrder+ " " +
		"userid:"+userid+ " " +
		"versioncode:"+versioncode+ "\n";
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
			
			// 通过用户的id得到用户名
		    IDAO_UserManager dao_UserManager = DAOFactory_UserManager.getInstance(DataBaseType.getDataBaseType(con));
   	        String sql = dao_UserManager.getSQL_SelectUserById(Integer.parseInt(userid));
   	        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
   	        ResultSet rs = stmt.executeQuery(sql);
   	        String user="";
   	        if(rs.next())
   	        	user = rs.getString("CUSRNAME");
   	  // 发布对象
   	         UpLoadRecord uploadrecord=new UpLoadRecord();
   	         uploadrecord.setUserName(user);
   	         uploadrecord.setUpload(0);
   	         uploadrecord.setVersionCode(versioncode);
   	         UploadFactory uploadfactory=new UploadFactory();
   	         uploadfactory.SaveUploadRecord(uploadrecord, con);
   	   // 取消发布计划
			planfactory.canceluploadplan(overtime, Integer.parseInt(produnitid),  workOrder, con);
			log.debug( "取消发布计划成功!");
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
        userid=null;
        versioncode=null;
        if(stmt!=null)stmt.close();
   
     }
}
