package mes.os.service.mpsplan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;

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
import mes.os.bean.MPSPlan;
import mes.os.factory.MPSPlanFactory;

/**功能：添加一条主计划
 * @author 谢静天
 *
 */
public class AddMPSPlan extends AdapterService{
	private final Log log = LogFactory.getLog(AddMPSPlan.class);
	
	/**
	 * con  获取连接
	 */
	private  Connection con=null;
	
	/**
	 * stmt  向数据库发送sql语句
	 */
	private Statement stmt=null;
	
	/**
	 * userid  制定人
	 */
	private String userid=null;
	 
	
	/**
	 * startDate  计划日期  
	 */
	private String startDate=null;
	
	
	/**
	 *  mpsUnit  mps单位
	 */
	private String mpsUnit=null;
	 
	/**
	 *  materielName  物料名
	 */
	private String materielName=null;
	
	/**
	 * planAmount  计划数量
	 */
	private String planAmount=null;
	
	/**
	 *intendStorage  预计库存
	 */
	private String intendStorage=null;
	
	/**
	 * planType  计划期类型
	 */
	private String planType=null;
	
	/**
	 * version   版本
	 */
	private String version=null;
	
	/**
	 * contractcode 合同号
	 */
	private String contractcode=null;
	public boolean checkParameter(IMessage message, String processid) {
		con=(Connection)message.getOtherParameter("con");
		userid=message.getUserParameterValue("userid");
		startDate=message.getUserParameterValue("startDate");
		mpsUnit=message.getUserParameterValue("mpsUnit");
		materielName=message.getUserParameterValue("materielName");
		planAmount=message.getUserParameterValue("planAmount");
		intendStorage=message.getUserParameterValue("intendStorage");
		planType=message.getUserParameterValue("planType");
		version=message.getUserParameterValue("version");
		contractcode=message.getUserParameterValue("contractcode");
		String debug="userid:"+userid+" startDate:"+startDate+" mpsUnit:"+mpsUnit+" " +
				"materielName:"+materielName+" planAmount:"+planAmount+"" +
						" intendStorage:"+intendStorage+" planType:"+planType+" " +
								"version:"+version+" contractcode:"+contractcode+ "\n";
		    log.info("添加主计划的参数: " + debug);
		if (startDate== null) {
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
		
			
			//  通过用户id得到用户名
		    IDAO_UserManager dao_UserManager = DAOFactory_UserManager.getInstance(DataBaseType.getDataBaseType(con));
   	        String sql = dao_UserManager.getSQL_SelectUserById(Integer.parseInt(userid));
   	        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
   	        ResultSet rs = stmt.executeQuery(sql);
   	        String user="";
   	        if(rs.next())
   	        	user = rs.getString("CUSRNAME");
   	       // 得到主计划
			MPSPlan mpsplan=new MPSPlan();
		
             mpsplan.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
             mpsplan.setMpsUnit(mpsUnit);
             mpsplan.setMaterielName(materielName);
             mpsplan.setPlanAmount(Integer.parseInt(planAmount));
             mpsplan.setIntendStorage(Integer.parseInt(intendStorage));
             mpsplan.setPlanType(planType);
             mpsplan.setVersion(version);
             mpsplan.setUserName(user);
             mpsplan.setContractCode(contractcode);
			
			MPSPlanFactory factory=new MPSPlanFactory ();
			// 生成一个主计划
			 factory.saveMPSPlan(mpsplan, con);
			 log.debug( "保存更改主计划成功!");
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
        userid=null;
    	startDate=null;
    	mpsUnit=null;
    	materielName=null;
    	planAmount=null;
    	intendStorage=null;
    	planType=null;
    	version=null;
        contractcode=null;
       if(stmt!=null){
    	stmt.close();
        }


     }
}
