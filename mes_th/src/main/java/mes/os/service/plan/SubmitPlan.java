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
import mes.os.factory.PlanFactory;
import mes.ra.bean.ProduceUnit;
import mes.ra.factory.*;
import mes.os.factory.*;
import mes.os.bean.*;
/**功能：提交编辑的计划
 * @author 谢静天
 *
 */
public class SubmitPlan extends AdapterService {

	/**
	 * log 日志
	 */
	private final Log log = LogFactory.getLog(SubmitPlan.class);
   
    /**
     * con  //数据库连接对象
     */
    Connection con = null;

    /**
    * workOrder // 班次
    */
    private String workOrder=null;

    /**
    * overTime // 生产日期
    */
    private String overTime=null;
   
    /**
     * produceUnit  // 生产单元id
     */
    private String produceUnit=null;
    
     /**
     * saveversion // 是否生成版本标识 true为真
     */
    private String saveversion=null;
   
    /**
     * user // 用户id
     */
    private String user=null;
   
    /**
     * stmt 向数据库发送sql语句
     */
    private Statement stmt=null;
	

	 public boolean checkParameter(IMessage message, String processid) {
			
	        con = (Connection) message.getOtherParameter("con");
	        workOrder=message.getUserParameterValue("workOrder");
	        overTime=message.getUserParameterValue("overTime");
	        produceUnit=message.getUserParameterValue("produceUnit");
	     
	        saveversion=message.getUserParameterValue("saveversion");
	        user=message.getUserParameterValue("user");
	      //输出log信息
		    String debug="workOrder:" +workOrder 
			+ " overTime:"+overTime+ " " +
			"produceUnit:"+produceUnit+ " " +
			"saveversion:"+saveversion+ " " +
			"user:"+user+ "\n";
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
					// 版本最后两位值
					String first="01";
					// 得到用户的名
					int  userId = Integer.parseInt(user);
			        IDAO_UserManager dao_UserManager = DAOFactory_UserManager.getInstance(DataBaseType.getDataBaseType(con));
			        String sql = dao_UserManager.getSQL_SelectUserById(userId);
			        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			        ResultSet rs = stmt.executeQuery(sql);
			        
			        if(rs.next())
			        	user = rs.getString("CUSRNAME");
			        else
			        	user = null;

					PlanFactory factory=new PlanFactory();
					VersionFactory versionfactory=new VersionFactory();
				     // 得到版本号后两位
				String code=factory.getversioncodewhensubmit(overTime, Integer.parseInt(produceUnit), workOrder, con);
				if(!code.equals("")&&code!=null)
				   {
						    int leng=code.length();
					    	//十位
			  	            String code1=code.substring(leng-2,leng-1);
					        //	个位
					    	String code2=code.substring(leng-1,leng);
					        int gewei=Integer.parseInt(code2)+1;
					    	if(gewei<=9){
					    		first=code1+String.valueOf(gewei);
					    	}
					    	else{
					    	  int shiwei=Integer.parseInt(code1)+1;
					    	   first=String.valueOf(shiwei)+"0";
					    		
					    	}
					    	
					    	
					    	
			       }
				 
				       ProduceUnitFactory unitfactory=new ProduceUnitFactory();
					   ProduceUnit produceunit=new ProduceUnit();
					      // 通过生产单元id获取生产单元名同时生成版本号
			           produceunit=unitfactory.getProduceUnitbyId(Integer.parseInt(produceUnit), con);
					     // 生产单元名
			           String str_name=produceunit.getStr_name();
					       
					   String [] date=new String [3];
					   date =overTime.split("-");
						     // 生产日期去掉“-”
				       String datestring= date[0]+date[1]+date[2];
				         // 得到版本号
				       String versioncode=datestring+str_name+workOrder+first;
				     // 如果用户是生成新版本的话
				       if(saveversion.equals("true")){
				    	// 生成新版本及版本信息
						factory.submitplan(overTime, Integer.parseInt(produceUnit),  workOrder, versioncode, con);
					    Version version=new Version();
					    version.setUser(user);
					    version.setVersionCode(versioncode);
					    versionfactory.saveVersion(version, con);
					    message.setOutputParameter("saveversion", versioncode);
					}// 否则
					else{
						// 先删除替换的版本内容
						factory.deletePlanbyversioncode(code, con);
						// 在把提交的内容作为上一个版本的内容
						factory.submitplan(overTime, Integer.parseInt(produceUnit),  workOrder, code, con);
						 message.setOutputParameter("saveversion", code);
					}
					  log.debug( "提交成功!");
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
		   if(stmt!=null){
		     stmt.close();
		     }
		  
	    }
}
