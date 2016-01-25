package mes.ra.service.state;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mes.framework.AdapterService;

import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;

import mes.ra.bean.State;

import mes.ra.factory.StateFactory;

	/**
	 * 创建状态
	 * 
	 * @author 徐嘉
	 * 
	 */
	public class CreateState extends AdapterService {
		/**
		 * 获得连接
		 */
		private Connection con = null;
		/**
		 * 状态名
		 */
		private String string_statename = null;
		/**
		 * 样式代码
		 */
		private String string_style= null;
		// 
		/**
		 * 样式描述
		 */
		private String string_styledesc= null;
		Statement stmt = null;
		ResultSet rs = null;
		StateFactory factory = new StateFactory();
		private final Log log = LogFactory.getLog(CreateState.class);
		@Override
		
		public boolean checkParameter(IMessage message, String processid) {
		
			con = (Connection) message.getOtherParameter("con");
			string_statename = message.getUserParameterValue("string_statename").trim();
			string_style = message.getUserParameterValue("string_style");
			string_styledesc = message.getUserParameterValue("string_styledesc");
			//输出log信息
		    String debug="状态名：string_statename：" + string_statename
			+ " 样式名：string_style："+string_style+ "\n";
		    log.info("添加状态的参数: " + debug);
			if (string_statename == null || string_style == null ) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
						processid, new java.util.Date(), null));
				return false;
			}
			return true;
		}
		@Override
		public ExecuteResult doAdapterService(IMessage message, String processid)
				throws SQLException, Exception {
			try {
				try {					
					State state = new State();
					state.setStateName(string_statename);
					state.setStyledesc(string_styledesc);
					state.setStyle(string_style);
					state.setDelete(0);
					factory.createState(state, con);
					log.debug( "添加创建工厂成功!");
				
				}catch (SQLException sqle) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
									.getId(), processid, new Date(), sqle));
					log.error("添加状态操作时,数据库异常"	+ sqle.toString());
					return ExecuteResult.fail;
				} 
			} catch (Exception e) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
						processid, new java.util.Date(), e));
				log.fatal( "添加状态操作时,未知异常" + e.toString());
				return ExecuteResult.fail;
			}
			return ExecuteResult.sucess;
		}
		@Override
		public void relesase() throws SQLException {
			string_statename = null;
			string_style = null;
			con = null;
		}
	}


