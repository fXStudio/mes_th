package com.qm.mes.ra.service.state;

import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.Date;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.AdapterService;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.ServiceException;
import com.qm.mes.framework.ServiceExceptionType;
import com.qm.mes.ra.bean.ConversionState;
import com.qm.mes.ra.factory.StateManager;

/**
 * 修改状态转换信息
 * 
 * @author xujia
 * 
 */
public class UpdateConversionState extends AdapterService { 
	/**
	 * 获得连接
	 */
	private Connection con = null; 
	/**
	 * 原状态
	 */
	private String fromstate = null; 
	/**
	 * 跳转状态
	 */
	private String tostate = null;
	/**
	 * 转换描述
	 */
	private String string_desc = null;
	/**
	 * 序号
	 */
	String from_id = null;

	String to_id = null;

	private String int_id = null;

	private ResultSet rs1 = null;

	private String sql1 = "";

	private final Log log = LogFactory.getLog(UpdateConversionState.class);
	StateManager Manager = new StateManager();

	@Override
	public boolean checkParameter(IMessage message, String processid) {

		con = (Connection) message.getOtherParameter("con");

		Statement stmt = null;

		try {

			fromstate = message.getUserParameterValue("fromstate");
			tostate = message.getUserParameterValue("tostate");
			string_desc = message.getUserParameterValue("string_desc");
			int_id = message.getUserParameterValue("int_id");
			from_id = message.getUserParameterValue("from_id");
			to_id = message.getUserParameterValue("to_id");
			boolean fff = Integer.parseInt(fromstate) != Integer.parseInt(from_id);
			boolean fff1 = Integer.parseInt(tostate) != Integer.parseInt(to_id);
			boolean fff2 = fff || fff1;
			if (!fff2) {
				// 没有改变直接提交
				return true;
			}
			stmt = con.createStatement(); // 初始化
			sql1 = "select int_fromstate, int_tostate from t_ra_stateconversion where int_fromstate="
					+ Integer.parseInt(fromstate)
					+ " and int_tostate="
					+ Integer.parseInt(tostate);

			// 输出log信息
			String debug = "状态名：fromstate:" + fromstate + "  样式名：tostate:"
					+ tostate + "  描述：string_desc:" + string_desc + "\n";
			log.info("添加状态转换的参数: " + debug);

			// 校验是否信息重复
			rs1 = stmt.executeQuery(sql1);
			if (rs1.next()) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.PARAMETERLOST, "此跳转规则重复，请重新定义",
						this.getId(), processid, new java.util.Date(), null));
				return false;

			}
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {

			if ((Integer.parseInt(fromstate)) == (Integer.parseInt(tostate))) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.UNKNOWN, "相同状态之间不能转换，请重新选择", this
								.getId(), processid, new Date(), null));
                log.fatal("相同状态之间不能转换");
				return ExecuteResult.fail;
			} else {

				ConversionState state = new ConversionState();
				state.setFromstate(Integer.parseInt(fromstate));
				state.setTostate(Integer.parseInt(tostate));
				state.setDesc(string_desc);
				state.setId(Integer.parseInt(int_id));
				Manager.updateConversionState(state, con);
				log.debug("创建工厂成功!");

			}
		} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error("添加状态规则操作时,未知异常" + e.toString());
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
