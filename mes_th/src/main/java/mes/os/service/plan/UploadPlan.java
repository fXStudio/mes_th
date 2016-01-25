package mes.os.service.plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mes.os.bean.*;
import mes.framework.AdapterService;
import mes.framework.DataBaseType;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.framework.dao.DAOFactory_UserManager;
import mes.framework.dao.IDAO_UserManager;
import mes.os.factory.*;
import mes.ra.bean.ProduceUnit;
import mes.ra.factory.ProduceUnitFactory;

/** 功能：发布作业计划
 * 在checkParameter中生产单元id不能为空
 * @author 谢静天
 * 
 */
public class UploadPlan extends AdapterService {
	
	/**
	 * log 日志
	 */
	private final Log log = LogFactory.getLog(UploadPlan.class);
	  
	/**
	 * con 数据库连接
	 */
	private Connection con=null;
	
	/**
	 * stmt 向数据库发送sql语句
	 */
	private Statement stmt=null;
	
	/**
	 * produnitid   //生产单元id
	 */
	private String produnitid =null;
	 
	/**
	 * planfactory  //计划工厂
	 */
	private PlanFactory planfactory=new PlanFactory();
	
	/**
	 * overtime   //生产日期
	 */
	private String overtime=null;
	 
	/**
	 * workOrder  //班次
	 */
	private String workOrder=null;
	 
	/**
	 * userid  //用户id
	 */
	private String userid=null;
	
	/**
	 * versioncode   //版本号
	 */
	private String versioncode=null;
  /**
	 * sqlplanbymaterileproid_max_upload 计划表中已经发布的最大版本的计划预处理
	 */
	private  PreparedStatement  sqlplanbymaterileproid_max_upload=null;
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
		if(overtime==null||overtime.equals("")||produnitid==null||versioncode==null||versioncode.equals("")){
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			return false;
	      }
	    if(!checkmateriel(message, processid)){
	    	return false;
	    }
	    
		return true;
		
	}
	
	public boolean checkmateriel(IMessage message,String processid){
		 List<Plan> planlist=new ArrayList<Plan>();
		
		 try{
		 planlist=planfactory.getPlanbyversioncord(versioncode,con);
		 String  sqlplanstring="select p.*,to_char(p.Dat_produceDate,'yyyy-MM-dd') as producedate from t_os_plan p where str_versioncode in "
		        +"(select max(str_versioncode) from t_os_plan "
				+"  where int_upload=1  and int_produnitid="+Integer.parseInt(produnitid)+"  and not(to_char(dat_produceDate,'yyyy-MM-dd')='"+overtime+"' and str_workOrder='"+workOrder+"') group by int_produnitid,Dat_produceDate,str_workOrder)" 
				+"  and str_producemarker=?";
		
		 sqlplanbymaterileproid_max_upload=con.prepareStatement(sqlplanstring);
		 Iterator<Plan> iter=planlist.iterator();
		 ProduceUnitFactory producefactory=new  ProduceUnitFactory();//生产单元工厂
		 ProduceUnit produceunit=new ProduceUnit();//生产单元对象
		 produceunit=producefactory.getProduceUnitbyId(Integer.parseInt(produnitid), con);
		 ResultSet rs=null;
		 int num=0;//主物料重复的个数
		 while(iter.hasNext()){
		      Plan plan=(Plan)iter.next();
		      sqlplanbymaterileproid_max_upload.setString(1,plan.getProduceMarker());
		      rs=sqlplanbymaterileproid_max_upload.executeQuery();
		      if(rs.next()){
		       num++;
		       message.setOutputParameter("yes"+num,produceunit.getStr_name()+" "+rs.getString("producedate")+" "+rs.getString("str_workOrder")+" "+rs.getString("str_versioncode")+" "+"版本的计划中已经使用了此主物料值:"+plan.getProduceMarker()+"");
		      }
		     
		 }
		 message.setOutputParameter("num", String.valueOf(num));
		 if(num>0){
			  message.addServiceException(new ServiceException(
		  				ServiceExceptionType.PARAMETERLOST, "不能发布此计划", this
		  						.getId(), processid,
		  				new java.util.Date(), null));
				  return false;
		 }
		 }catch(Exception e){
			 e.printStackTrace();
			 return false;
		 }
		 return true;
	}
	public ExecuteResult doAdapterService(IMessage message, String processid)
	throws SQLException, Exception {
		try {
			  //通过用户id，得到用户名
		    IDAO_UserManager dao_UserManager = DAOFactory_UserManager.getInstance(DataBaseType.getDataBaseType(con));
   	        String sql = dao_UserManager.getSQL_SelectUserById(Integer.parseInt(userid));
   	        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
   	        ResultSet rs = stmt.executeQuery(sql);
   	        String user="";
   	        if(rs.next())
   	        	user = rs.getString("CUSRNAME");
   	         //发布计划
   	         UpLoadRecord uploadrecord=new UpLoadRecord();
   	         uploadrecord.setUserName(user);
   	         uploadrecord.setUpload(1);
   	         uploadrecord.setVersionCode(versioncode);
   	         UploadFactory uploadfactory=new UploadFactory();
   	         uploadfactory.SaveUploadRecord(uploadrecord, con);
   	       
			 planfactory.uploadplan(overtime, Integer.parseInt(produnitid), workOrder, con);
			log.debug( "计划发布成功!");
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
        versioncode=null;
    	overtime=null;
        workOrder=null;
        userid=null;
        if(stmt!=null)stmt.close();

     }
	
	

}
