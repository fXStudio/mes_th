package com.qm.mes.ra.service.instruction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.sql.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.AdapterService;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.ServiceException;
import com.qm.mes.framework.ServiceExceptionType;
import com.qm.mes.ra.dao.*;
import com.qm.mes.ra.factory.*;
/**更新指令状态
 * author : 谢静天
 */
public class UpdateInstructState  extends AdapterService{
	private Connection con = null;
	/**
	 * 启动状态规则
	 */
	private String startstate=null;
	/**
	 * 强制启动状态规则
	 */
	private String powerstate=null;
	/**
	 * 主物料的值也就是指令表中的产品标识
	 */
	private String str_produceMarker=null;
	/**
	 * 用户的id
	 */
	private String  userId=null;
	/**
	 * 采集点的id
	 */
	private String INT_GATHERID=null;
    /**
     * 生产单元的id
     */
    private String produnitid=null;
	/**
	 * 日志
	 */
	private final Log log = LogFactory.getLog(UpdateInstructState.class);
	
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		
		con = (Connection) message.getOtherParameter("con");
		startstate= message.getUserParameterValue("startstate");
		powerstate=message.getUserParameterValue("powerstate");
		produnitid=message.getUserParameterValue("produnitid");
		str_produceMarker=message.getUserParameterValue("str_produceMarker");
		userId=message.getUserParameterValue("userId");
		INT_GATHERID=message.getUserParameterValue("gatherId");
		log.debug("是否启动状态规则："+startstate+"；是否强制启动状态规则："+powerstate+"；生产单元号："+produnitid+
				 "；产品标识："+str_produceMarker+"；用户号"+userId+"；采集点号："+INT_GATHERID);
		if (str_produceMarker==null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("产品标识为空");
			return false;
		}
		

		return true;
	}

	public ExecuteResult doAdapterService(IMessage message, String processid)
	throws SQLException, Exception {
try {
	try {
		Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		//当startstate=0 什么都不做
		//因为页面判断了符合强制转换。所以一定有满足符合的
		if(Integer.parseInt(startstate)==1){
			InstructionFactory factory=new InstructionFactory();
		    //查看当前指令的所初的状态id
		    int Int_instrucsttateid= factory.checkstr_produceMarker(str_produceMarker,Integer.parseInt(produnitid), con);
		    DAO_State state=new DAO_StateForOracle();
		    //通过采集点的id来查询与采集点的有关的状态的id号
	        String statesql=state.getstateIdbygatherid(new Integer(INT_GATHERID));
	        log.debug("通过采集点号查询状态号SQL语句："+statesql);
		    ResultSet stateConversionrs= stmt.executeQuery(statesql);
		    int m=0;
		    //先找默认的执行
		    while(stateConversionrs.next()){
		    	if(stateConversionrs.getInt("defaultexcute")==1)
				{          
		    		//主物料当前的状态等于转换前的成功
					if(Int_instrucsttateid==stateConversionrs.getInt("int_fromstate"))
					{
						m++;
						factory.updateInstructState(stateConversionrs.getInt("int_tostate"), 0, str_produceMarker,Integer.parseInt(produnitid), con);
						log.info("更新指令状态成功");
					 }
		         }
		    }
			//如果默认的不符何找其他的
		    if(m==0){
		    	stateConversionrs.beforeFirst();
		    	while(stateConversionrs.next()){ 
		    		if(Int_instrucsttateid==stateConversionrs.getInt("int_fromstate"))
		    		{
		    			m++;
		    			if(m==1){
		    				factory.updateInstructState(stateConversionrs.getInt("int_tostate"), 0, str_produceMarker, Integer.parseInt(produnitid),con);
		    				log.info("更新指令状态成功");
		    			}
					}   
		        }
		    	//如果还没有符合的就按默认的转换同时记录异常
		    	if(m==0){
		    		stateConversionrs.beforeFirst();
		    		while(stateConversionrs.next()){ 
		    			if(stateConversionrs.getInt("defaultexcute")==1)
						{
		    				//更新指令状态同时记录异常
				    		factory.updateInstructState(stateConversionrs.getInt("int_tostate"), 1, str_produceMarker,Integer.parseInt(produnitid), con);
				    		log.info("更新指令状态成功");
				    		//同时把异常的指令放到指令异常表中
				    		factory.savet_ra_Instructionexception(Int_instrucsttateid, Integer.parseInt(userId), str_produceMarker, Integer.parseInt(INT_GATHERID), con);
				    		log.info("创建指令异常成功");
				    	}   
		    		}
		    	}
		    }
		    if(stmt!=null){
		    	stmt.close();
		    }
		}

	} catch (SQLException sqle) {
		message.addServiceException(new ServiceException(
				ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
						.getId(), processid, new Date(), sqle));
		log.error("数据库异常");
		return ExecuteResult.fail;
	}
} catch (Exception e) {
	message.addServiceException(new ServiceException(
			ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
			processid, new java.util.Date(), e));
	log.error("未知异常");
	return ExecuteResult.fail;
}
return ExecuteResult.sucess;
}

@Override
public void relesase() throws SQLException {
	 con = null;
	 startstate=null;
	 powerstate=null;

	
}


}
