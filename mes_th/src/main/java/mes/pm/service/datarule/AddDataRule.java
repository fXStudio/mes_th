package mes.pm.service.datarule;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.pm.bean.DataRule;
import mes.pm.factory.DataRuleFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 添加数据规则
 * 
 * @author Xujia
 * 
 */
public class AddDataRule  extends AdapterService {
	
	/**
	 *  获得连接
	 */
	private Connection con = null;
	  
	/**
	 *  数据规则名   
	 */
	private String str_name = null;	
	/**
	 *  规则编号
	 */
	private String str_code = null;
	/**
	 * 规则公式
	 */
	private String string_rule = null;
	/**
	 * 公式描述
	 */
	private String str_description = null;
	/**
	 * 数据规则工厂类
	 */
	DataRuleFactory factory = new DataRuleFactory();
	/**
	 * 日志 
	 */
	private final Log log = LogFactory.getLog(AddDataRule.class);
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		str_name = message.getUserParameterValue("str_name");
		str_code= message.getUserParameterValue("str_code");
		string_rule = message.getUserParameterValue("rule");
		str_description = message.getUserParameterValue("str_description");
		
		//输出log信息
	    String debug="数据规则名：" + str_name + "；"+ "规则编号："+str_code+ ";"
		+ "规则公式："+string_rule+ ";"+"采集点描述公式描述："+str_description;
	    log.debug("添加数据规则时用户提交的参数: " + debug);

		if (str_name == null || str_code == null || string_rule== null
				|| str_description == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("数据规则名、规则编号、规则公式、公式描述中有为空参数，退出服务。");
			return false;
		}

		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
				DataRule datarule = new DataRule();
				
				
				datarule.setName(str_name);
				datarule.setCode(str_code);
				datarule.setRule(string_rule);
				datarule.setDescription(str_description);				
				factory.createDataRule(datarule, con);
				log.info("添加数据规则服务成功！");
				DataRule d = new DataRule();
				d = factory.getDataRuleByName(str_name, con);
				message.setOutputParameter("int_dataruleid", String.valueOf(d
						.getId()));
				log.info("数据规则号int_dataruleid为："+d.getId()+" ");
				
			} catch (SQLException sqle) {
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
		str_name = null;
		str_code = null;
		string_rule = null;
		str_description = null;
		con = null;

	}

}

