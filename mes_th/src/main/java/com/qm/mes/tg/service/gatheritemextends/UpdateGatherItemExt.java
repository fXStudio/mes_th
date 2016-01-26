/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.qm.mes.tg.service.gatheritemextends;

import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;
import java.util.Date;
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

/**
 * 采集点属性更新
 *
 * @author YuanPeng
 */
public class UpdateGatherItemExt extends AdapterService {
    //数据库连接对象
    private Connection con;
    // 属性个数
	private String gie_count = null;
    //采集点属性号
    private int int_id;
    //采集点Id
    private String int_gatherId;
    //采集顺序号
    private String int_order;
    //属性名称
    private String str_name;
	//日志
	private final Log log = LogFactory.getLog(UpdateGatherItemExt.class);
	
    // 扩展属性存到map中(顺序号,扩展属性名),将map对象转化成String类型
	HashMap<String, String> gie_val = new HashMap<String, String>();
	SerializeAdapter sa = new SerializeAdapter();
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
        int_gatherId = message.getUserParameterValue("int_gatherId");
        //kind==batch种类为批量的
        if(message.getUserParameterValue("kind") == "batch"){
        //扩展属性数量
        gie_count = message.getUserParameterValue("gie_count");
        try {
        	//将参数转换为Map
			gie_val = (HashMap<String, String>) sa.toObject(message
					.getUserParameterValue("str_gie_val"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//输出log信息
	    String debug="采集点号：" + int_gatherId + "；"+"采集点扩展属性个数："+gie_count;
	    if(Integer.parseInt(gie_count)!=0)debug+=";采集点扩展属性：\n";
	    for(int j=1;j<=Integer.parseInt(gie_count);j++){
	    	debug+="采集点属性顺序号："+gie_val.get("int" + j + "_gieorder")+";";
	    	debug+="采集点属性名："+gie_val.get("str" + j + "_name");
	    	if(j!=1)debug+=";\n";
	    	if(j!=Integer.parseInt(gie_count))debug+="\n";
	    }
	    log.debug("更新采集点扩展属性时用户提交的参数: " + debug);
        }
        //种类为单一的
        else{
        	//单体更新采集点扩展属性
            int_id = new Integer(message.getUserParameterValue("int_id"));
            str_name = message.getUserParameterValue("str_name");
            int_order = message.getUserParameterValue("int_order");
          //输出log信息
    	    String debug="采集点扩展属性号：" + int_id + "；"+ "采集点扩展属性名："+str_name+ ";"
    		+ "采集点扩展属性顺序号："+int_order;
    	    log.debug("更新采集点扩展属性时用户提交的参数: " + debug);
        }
		if (int_gatherId == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("采集点号为空，退出服务。");
			return false;
		}
		return true;

	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {

				GatherItemExtFactory gatherItemExtFactory = new GatherItemExtFactory();
                if(message.getUserParameterValue("kind") == "batch"){
                	//批量更新--将之前采集点扩展属性删除并重新创建
                    gatherItemExtFactory.delGatherItemExtByGatherId(new Integer(int_gatherId), con);
                    for(int i = 1; i <= Integer.parseInt(gie_count); i++){
                    GatherItemExt gatherItemExt = new GatherItemExt();
                    gatherItemExt.setName(gie_val.get("str_name" + i));
                    gatherItemExt.setGatherId(new Integer(int_gatherId));
                    gatherItemExt.setOrder(new Integer(gie_val.get("int_gieorder" + i)));
                    gatherItemExtFactory.saveGatherItemExt(gatherItemExt, con);
                    }
    				log.info("更新采集点扩展属性服务成功！");
                }
                else{
                	//单体更新采集点扩展属性
                    GatherItemExt gatherItemExt = new GatherItemExt();
                    gatherItemExt.setId(new Integer(int_id));
                    gatherItemExt.setGatherId(new Integer(int_gatherId));
                    gatherItemExt.setOrder(new Integer(int_order));
                    gatherItemExt.setName(str_name);
                    gatherItemExtFactory.updateGatherItemExt(gatherItemExt, con);
    				log.info("更新采集点扩展属性服务成功！");
                }
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
//		int_id = null;
		str_name = null;
		int_gatherId = null;
		int_order = null;
		con = null;

	}

}
