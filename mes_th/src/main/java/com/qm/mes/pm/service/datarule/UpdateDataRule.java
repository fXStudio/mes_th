package com.qm.mes.pm.service.datarule;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.AdapterService;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.ServiceException;
import com.qm.mes.framework.ServiceExceptionType;
import com.qm.mes.pm.bean.DataRule;
import com.qm.mes.pm.bean.DataRuleArg;
import com.qm.mes.pm.factory.DataRuleFactory;
import com.qm.mes.util.SerializeAdapter;
/**
 * 更新数据规则
 * @author Xujia
 *
 */
public class UpdateDataRule  extends AdapterService {
	/**
	 * 获得连接
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
	 * int_id
	 */
	private String int_id=null;
    /**
     * 参数个数
     */
    private String count=null;
    HashMap<String, String> args = new HashMap<String, String>();
	SerializeAdapter sa = new SerializeAdapter();
	DataRuleFactory factory = new DataRuleFactory();
	//日志
	private final Log log = LogFactory.getLog(AddDataRule.class);
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		str_name = message.getUserParameterValue("str_name");
		str_code= message.getUserParameterValue("str_code");
		int_id= message.getUserParameterValue("int_id");
		string_rule = message.getUserParameterValue("string_rule");
		str_description = message.getUserParameterValue("str_description");
		count = message.getUserParameterValue("count");
		 // 参数信息存到map中(参数名,描述),将map对象转化成String类型
		

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
		
	    if(message.getUserParameterValue("argss") != null){
	        try {
	        	//将参数转换为map(顺序号,扩展属性名)
				args = (HashMap<String, String>) sa.toObject(message
						.getUserParameterValue("argss"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			//输出log信息
		    debug ="数据规则Id：" + int_id + "；"+"参数个数："+count;
		    if(Integer.parseInt(count)!=0)debug+=";参数信息：\n";
		    for(int j=1;j<=Integer.parseInt(count);j++){
		    	debug+="参数名："+args.get("str_argsname"+j)+";";
		    	debug+="描述信息："+args.get("str_argsdescription"+j);
		    	if(j!=1)debug+=";\n";
		    	if(j!=Integer.parseInt(count))debug+="\n";
		    }
		    log.debug("添加数据规则时用户提交的参数: " + debug);

			if (args == null ||count == null) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
						processid, new java.util.Date(), null));
				log.fatal("数据规则名称、描述有为空参数，退出服务。");
				return false;
			}

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
				datarule.setId(new Integer(int_id));
				factory.updateDataRule(datarule, con);
				log.info("更新数据规则服务成功！");		
//				先删除原有参数信息
				factory.delDataRuleArgsById(new Integer(int_id), con);
			    log.debug("删除参数成功!");
			    //再重新添加参数
                for (int i = 1; i <= Integer.parseInt(count); i++) {                    
                DataRuleArg dataruleArg = new DataRuleArg();                   
                dataruleArg.setInt_dataruleid(new Integer(int_id));               
                dataruleArg.setName(args.get("str_argsname"+i));
                dataruleArg.setDescription(args.get("str_argsdescription"+i));
                factory.createDataRuleArgs(dataruleArg, con);
                }
				log.info("批量添加参数服务成功！");				
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
