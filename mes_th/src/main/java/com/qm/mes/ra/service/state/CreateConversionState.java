package com.qm.mes.ra.service.state;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.AdapterService;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.ServiceException;
import com.qm.mes.framework.ServiceExceptionType;
import com.qm.mes.ra.bean.ConversionState;
import com.qm.mes.ra.dao.DAO_ConversionState;
import com.qm.mes.ra.dao.DAO_ConversionStateForOracle;
import com.qm.mes.ra.factory.StateManager;
/**
 * 添加指令状态信息
 * 
 * @author xujia
 * 
 */
public class CreateConversionState extends AdapterService {
	/**
	 * 获得连接
	 */
	private Connection con = null;
	/**
	 *  原状态
	 */
	private String fromstate = null;
	/**
	 * 跳转状态
	 */
	private String tostate= null;
	/**
	 * 转换描述
	 */
	private String string_desc=null;
	StateManager Manager = new StateManager();
	Statement stmt = null;
	DAO_ConversionState dao2=new DAO_ConversionStateForOracle();	
  	private ResultSet rs1 = null;
  	private String sql1="";
	private final Log log = LogFactory.getLog(CreateConversionState.class);
	
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		//接收message传过来的参数
		try{
			//获取连接	
			con = (Connection) message.getOtherParameter("con"); 
			stmt = con.createStatement();   //初始化
			fromstate = message.getUserParameterValue("fromstate");
			tostate = message.getUserParameterValue("tostate");
			string_desc = message.getUserParameterValue("string_desc");
			sql1=dao2.getAllConversionState();
			//	输出log信息
			String debug="状态名：fromstate:" + fromstate 
			+ " 样式名：tostate:"+tostate+ " 描述：string_desc="+string_desc+ "\n";
			log.info("添加状态转换的参数: " + debug);
			// 校验是否信息重复
			rs1=stmt.executeQuery(sql1);
			while(rs1.next())	    
			{  
				if((rs1.getInt(2)==Integer.parseInt(fromstate)) && (rs1.getInt(3)==Integer.parseInt(tostate)))
				{  
					message.addServiceException(new ServiceException(
							ServiceExceptionType.PARAMETERLOST, "此跳转规则重复，请重新定义", this.getId(),
							processid, new java.util.Date(), null));
					return false;
				}
			}
			rs1.close();	   
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {   //同状态不能转换
				if ((Integer.parseInt(fromstate)) == (Integer.parseInt(tostate)) ) {
						message.addServiceException(new ServiceException(
								ServiceExceptionType.UNKNOWN, "相同状态之间不能转换，请重新选择",
								this.getId(), processid, new Date(), null));
						log.fatal("相同状态之间不能转换");
						return ExecuteResult.fail;
				} else {
					//调用工厂，执行创建
					ConversionState state = new ConversionState();
					state.setFromstate(Integer.parseInt(fromstate));
					state.setTostate(Integer.parseInt(tostate));
					state.setDesc(string_desc);
					Manager.createConversionState(state, con);
					log.debug( "添加创建工厂成功!");
				}
			}catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), processid, new Date(), sqle));
				log.error("添加状态规则操作时,数据库异常"	+ sqle.toString());
				return ExecuteResult.fail;
			} 
		} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error( "添加状态规则操作时,未知异常" + e.toString());
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

	@Override
	public void relesase() throws SQLException {
		fromstate = null;
		tostate = null;
		string_desc = null;
	}
}


