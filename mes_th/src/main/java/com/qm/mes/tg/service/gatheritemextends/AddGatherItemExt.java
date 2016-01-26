/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.qm.mes.tg.service.gatheritemextends;

import java.sql.Connection;
import java.util.Date;
import java.sql.SQLException;
import java.util.HashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.AdapterService;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.ServiceException;
import com.qm.mes.framework.ServiceExceptionType;
import com.qm.mes.tg.bean.GatherItemExt;
import com.qm.mes.tg.factory.GatherItemExtFactory;
import com.qm.mes.util.SerializeAdapter;

import java.io.IOException;

/**
 *添加采集点项属性
 *
 * @author YuanPeng
 */
public class AddGatherItemExt extends AdapterService{
    //连接数据库对象
    Connection con = null;
    //采集点Id
    private String int_gatherId =null ;
    //扩展属性个数
    private String gie_count = null;
    //采集点扩展属性顺序号
    private String order = null;
    //采集点扩展属性名
    private String str_name = null;
	//日志
	private final Log log = LogFactory.getLog(AddGatherItemExt.class);
	
    // 扩展属性存到map中(顺序号,扩展属性名),将map对象转化成String类型
	HashMap<String, String> gie_val = new HashMap<String, String>();
	SerializeAdapter sa = new SerializeAdapter();

    
    @Override
    public void relesase() throws SQLException {
    con = null;
    int_gatherId =null ;
    gie_count = null;
    
    }

    @Override
    public boolean checkParameter(IMessage message, String processid) {
    	String debug = "";
		con = (Connection) message.getOtherParameter("con");
        //通过两种不同的传递参数方式，判断用哪种方式接受
        if(message.getUserParameterValue("int_gatherId") == null){
            int_gatherId = message.getOutputParameterValue("int_gatherId");
        }
        else
            int_gatherId = message.getUserParameterValue("int_gatherId");
		gie_count = message.getUserParameterValue("gie_count");
        if(message.getUserParameterValue("str_gie_val") != null){
	        try {
	        	//将参数转换为map(顺序号,扩展属性名)
				gie_val = (HashMap<String, String>) sa.toObject(message
						.getUserParameterValue("str_gie_val"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			//输出log信息
		    debug ="采集点号：" + int_gatherId + "；"+"采集点扩展属性个数："+gie_count;
		    if(Integer.parseInt(gie_count)!=0)debug+=";采集点扩展属性：\n";
		    for(int j=1;j<=Integer.parseInt(gie_count);j++){
		    	debug+="采集点属性顺序号："+gie_val.get("int" + j + "_gieorder")+";";
		    	debug+="采集点属性名："+gie_val.get("str" + j + "_name");
		    	if(j!=1)debug+=";\n";
		    	if(j!=Integer.parseInt(gie_count))debug+="\n";
		    }
        }
        if(message.getUserParameterValue("kind") != "batch"){
			order = message.getUserParameterValue("int_order");
			str_name = message.getUserParameterValue("str_name");
			debug = "采集点号：" + int_gatherId + "；"+"采集点扩展属性个数："+gie_count+
			"；采集点扩展属性顺序号："+order+"；采集点扩展属性名："+str_name;
		}
      
	    log.debug("添加采集点扩展属性时用户提交的参数: " + debug);

		if (int_gatherId == null || gie_count == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("采集点号、采集点属性数量有为空参数，退出服务。");
			return false;
		}

		return true;
    }

    @Override
    public ExecuteResult doAdapterService(IMessage message, String processid){
        try {
			try {
                //kind==batch种类为批量的
                if(message.getUserParameterValue("kind") == "batch"){
                    for (int i = 1; i <= Integer.parseInt(gie_count); i++) {
                    GatherItemExtFactory gatherItemExtFactory = new GatherItemExtFactory();
                    GatherItemExt gatherItemExt = new GatherItemExt();
                    gatherItemExt.setGatherId(new Integer(int_gatherId));
                    gatherItemExt.setOrder(new Integer(gie_val.get("int" + i + "_gieorder")));
                    gatherItemExt.setName(gie_val.get("str" + i + "_name"));
                    gatherItemExtFactory.saveGatherItemExt(gatherItemExt, con);
                    }
					log.info("批量添加采集点扩展属性服务成功！");
                }
                else{
                	//单体添加采集点扩展属性
                    GatherItemExtFactory gatherItemExtFactory = new GatherItemExtFactory();
                    GatherItemExt gatherItemExt = new GatherItemExt();
					gatherItemExt.setGatherId(new Integer(int_gatherId));
                    gatherItemExt.setOrder(new Integer(order));
                    gatherItemExt.setName(str_name);
                    gatherItemExtFactory.saveGatherItemExt(gatherItemExt, con);
					log.info("单体添加采集点扩展属性服务成功！");
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
    

}
