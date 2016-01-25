package mes.tg.service.materielRule;

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

import mes.tg.bean.MaterielRule;
import mes.tg.factory.MaterielRuleFactory;

/**
 * 添加物料标识规则
 * 
 * @author lida
 * 
 */
public class AddMaterielRule extends AdapterService {
	// 获得连接
	private Connection con = null;
	// 物料标识规则名
	private String str_name = null;
	// 验证字符串
	private String str_validateclass = null;
	// 验证字符串的描述信息
	private String desc = null;
	//日志
	private final Log log = LogFactory.getLog(AddMaterielRule.class);

	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		str_name = message.getUserParameterValue("str_name");
		str_validateclass = message.getUserParameterValue("str_validateclass");
		desc = message.getUserParameterValue("desc");
		//输出log信息
	    String debug="物料标识规则名：" + str_name + "；验证字符串："+str_validateclass+
		";验证字符串的描述信息："+desc;
	    log.debug("添加物料标识规则时用户提交的参数: " + debug);
		if (str_name == null || str_validateclass == null || desc == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("物料标识规则名、验证字符串、验证字符串的描述信息中有为空参数，退出服务。");
			return false;
		}

		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
				MaterielRuleFactory factory = new MaterielRuleFactory();
				MaterielRule materielRule = new MaterielRule();
				materielRule.setName(str_name);
				materielRule.setValidate(str_validateclass);
				materielRule.setDesc(desc);
				factory.saveMaterielRule(materielRule, con);
				log.info("添加物料标识规则服务成功！");
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
		str_validateclass = null;
		con = null;

	}

}
