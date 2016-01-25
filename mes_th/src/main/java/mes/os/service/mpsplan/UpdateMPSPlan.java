package mes.os.service.mpsplan;

import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.os.bean.*;
import mes.os.factory.*;

import java.text.*;
import java.sql.*;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**功能：修改主计划
 * @author 谢静天
 *
 */
public class UpdateMPSPlan  extends AdapterService{
	
	/**
	 * log 日志
	 */
	private final Log log = LogFactory.getLog(UpdateMPSPlan.class);
     /**
	 * con 	// 获取连接
	 */
	private  Connection con=null;
	/**
	 * int_id  // 主计划id
	 */
	private String int_id=null;
	
	/**
	 * startDate // 计划日期
	 */
	private String startDate=null;
	
	/**
	 * mpsUnit // mps单位
	 */
	private String mpsUnit=null;
	
	/**
	 * materielName // 物料名
	 */
	private String materielName=null;
	
	/**
	 * planAmount // 计划数量
	 */
	private String planAmount=null;
	
	/**
	 * intendStorage // 预计库存
	 */
	private String intendStorage=null;
	/**
	 * planType // 计划期类型
	 */
	private String planType=null;
	/**
	 * version // 版本
	 */
	private String version=null;
	/**
	 * contractcode // 合同号
	 */
	private String contractcode=null;
	public boolean checkParameter(IMessage message, String processid) {
		con=(Connection)message.getOtherParameter("con");
		int_id=message.getUserParameterValue("int_id");
		startDate=message.getUserParameterValue("startDate");
		mpsUnit=message.getUserParameterValue("mpsUnit");
		materielName=message.getUserParameterValue("materielName");
		planAmount=message.getUserParameterValue("planAmount");
		intendStorage=message.getUserParameterValue("intendStorage");
		planType=message.getUserParameterValue("planType");
		version=message.getUserParameterValue("version");
		contractcode=message.getUserParameterValue("contractcode");
		
		//输出log信息
	    String debug="int_id:" +int_id 
		+ " startDate:"+startDate+ " " +
		"mpsUnit:"+mpsUnit+ " " +
		"materielName:"+materielName+ " " +
		"planAmount:"+planAmount+ " " +
		"intendStorage:"+intendStorage+ " " +
		"planType:"+planType+ " " +
		"version:"+version+ " " +
		"contractcode:"+contractcode+ "\n";
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
			 // 更新主计划通过主计划id
			 MPSPlan mpsplan=new MPSPlan();
			 mpsplan.setId((Integer.parseInt(int_id)));
             mpsplan.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
             mpsplan.setMpsUnit(mpsUnit);
             mpsplan.setMaterielName(materielName);
             mpsplan.setPlanAmount(Integer.parseInt(planAmount));
             mpsplan.setIntendStorage(Integer.parseInt(intendStorage));
             mpsplan.setPlanType(planType);
             mpsplan.setVersion(version);
             mpsplan.setContractCode(contractcode);
             
			 MPSPlanFactory factory=new MPSPlanFactory ();
			 factory.updateMPSPlanbyid(mpsplan, con);
			 log.debug( "更新主计划成功!");
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
          int_id=null;
    	  startDate=null;
    	  mpsUnit=null;
    	  materielName=null;
    	  planAmount=null;
    	  intendStorage=null;
    	  planType=null;
    	  version=null;
          contractcode=null;
   


     }

}
