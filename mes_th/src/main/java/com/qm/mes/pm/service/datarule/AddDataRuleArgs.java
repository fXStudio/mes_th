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
import com.qm.mes.pm.bean.DataRuleArg;
import com.qm.mes.pm.factory.DataRuleFactory;
import com.qm.mes.util.SerializeAdapter;

/**
 * 添加数据规则参数
 * 
 * @author Xujia
 * 
 */
public class AddDataRuleArgs extends AdapterService {//连接数据库对象
    Connection con = null;
    /**
     * 数据规则Id
     */
    private String int_dataruleid =null ;
    /**
     * 参数个数
     */
    private String count=null;
	/**
	 * 日志
	 */
	private final Log log = LogFactory.getLog(AddDataRuleArgs.class);
	DataRuleFactory dataruleFactory = new DataRuleFactory();
	
    // 参数信息存到map中(参数名,描述),将map对象转化成String类型
	HashMap<String, String> args = new HashMap<String, String>();
	SerializeAdapter sa = new SerializeAdapter();

    
    @Override
    public void relesase() throws SQLException {
    con = null;
    
    
    }

    @Override
    public boolean checkParameter(IMessage message, String processid) {
    	String debug = "";
		con = (Connection) message.getOtherParameter("con");
        //通过两种不同的传递参数方式，判断用哪种方式接受
		int_dataruleid = message.getOutputParameterValue("int_dataruleid");
		count = message.getUserParameterValue("count");
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
		    debug ="数据规则Id：" + int_dataruleid + "；"+"参数个数："+count;
		    if(Integer.parseInt(count)!=0)debug+=";参数信息：\n";
		    for(int j=1;j<=Integer.parseInt(count);j++){
		    	debug+="参数名："+args.get("str_argsname"+j)+";";
		    	debug+="描述信息："+args.get("str_argsdescription"+j);
		    	if(j!=1)debug+=";\n";
		    	if(j!=Integer.parseInt(count))debug+="\n";
		    }
        }
        
      
	    log.debug("添加数据规则时用户提交的参数: " + debug);

		if (args == null ||count == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("数据规则名称、描述有为空参数，退出服务。");
			return false;
		}

		return true;
    }

    @Override
    public ExecuteResult doAdapterService(IMessage message, String processid){
        try {
			try {                				
                
                    for (int i = 1; i <= Integer.parseInt(count); i++) {                    
                    DataRuleArg dataruleArg = new DataRuleArg();                   
                    dataruleArg.setInt_dataruleid(new Integer(int_dataruleid));               
                    dataruleArg.setName(args.get("str_argsname"+i));
                    dataruleArg.setDescription(args.get("str_argsdescription"+i));
                    dataruleFactory.createDataRuleArgs(dataruleArg, con);
                    }
					log.info("批量添加参数成功！");
                
               
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
    

}
