package mes.tg.service.gather;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import mes.framework.AdapterService;
import mes.framework.DataBaseType;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.pm.factory.DeviceFactory;
import mes.system.dao.DAOFactoryAdapter;
import mes.tg.dao.IDAO_Gather;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AddGather_Q extends AdapterService {//连接数据库对象
    Connection con = null;
  
    /**
     * 质量状态号
     */
    private String quality_check = null;
    /**
     * 采集点id
     */
    private String int_gatherid =null ;
	/**
	 * 日志
	 */
	private final Log log = LogFactory.getLog(AddGather_Q.class);
	
	DeviceFactory factory = new DeviceFactory();

    
    @Override
    public boolean checkParameter(IMessage message, String processid) {
    	String debug = "";
		con = (Connection) message.getOtherParameter("con");        
		int_gatherid = message.getOutputParameterValue("int_gatherid");
		//int_produnitid = message.getUserParameterValue("int_produnitid");
		//int_devicetype = message.getUserParameterValue("int_devicetype");
		quality_check = message.getUserParameterValue("quality_check");
		
		//输出log信息
	    debug="采集点id：" + int_gatherid+ "；"+ "质量状态号" +  "："+quality_check;
	
	    log.debug("添加异常类型时用户提交的参数: " + debug);

		if (quality_check == null  || int_gatherid == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("设备号、质量状态号有为空参数，退出服务。");
			return false;
		}

		return true;
	}

    @Override
    public ExecuteResult doAdapterService(IMessage message, String processid){
        try {
			try {                				
                
				IDAO_Gather dao = (IDAO_Gather) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_Gather.class);
				Statement stmt = con.createStatement();
				String[] qualitys = quality_check.split(","); // 质量拆分成组
				//添加采集点和质量的关系
				for (int j = 0; j < qualitys.length; j++) {
				log.debug("创建qualitys: "+dao.saveGather_Q((Integer.parseInt(int_gatherid)),(Integer.parseInt(qualitys[j])),j+1));
				stmt.execute(dao.saveGather_Q((Integer.parseInt(int_gatherid)),(Integer.parseInt(qualitys[j])),j+1));
				}
				
				if(stmt!=null){
					stmt.close();
					stmt=null;
				}
               
			}catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), processid, new Date(), sqle));
				log.error("数据库异常，中断服务。");
				return ExecuteResult.fail;
			}
		} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error("未知异常，中断服务。");
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}
    @Override
	public void relesase() throws SQLException {
    	quality_check = null;
    	int_gatherid = null;
		con = null;

	}
    

}
