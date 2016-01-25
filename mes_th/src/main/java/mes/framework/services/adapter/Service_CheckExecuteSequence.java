package mes.framework.services.adapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mes.framework.DataBaseType;
import mes.framework.DefService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.IProcess;
import mes.framework.IService;
import mes.framework.ProcessFactory;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.framework.dao.DAOFactory_Core;
import mes.framework.dao.IDAO_Core;

/**
 * 服务：检查输出服务是否在输入服务之前 输入依赖：需要在otherparameter中放置一个Connection对象，名为con。<br>
 * 需要在用户输入或服务输出中含有processid,i_serveralias, sourceid,o_serveralias 即使是空字符串。 <br>
 * 输出：无输出
 * 
 * @author 吕智 2007-6-22
 */
public class Service_CheckExecuteSequence extends DefService implements
		IService {

	// 数据库连接
	private Connection con = null;

	// 流程号
	private String processid = null;

	// 输入端服务别名
	private String i_serveralias = null;

	// 输出端服务别名
	private String o_serveralias = null;

	// 数据来源
	private String sourceid = null;

	public Service_CheckExecuteSequence() {
		super();
	}

	private final Log log = LogFactory
			.getLog(Service_CheckExecuteSequence.class);

	private boolean initFordo(IMessage message, String soa_processid) {

		// 数据库连接
		con = null;
		// 流程号
		processid = null;
		// 输入端服务别名
		i_serveralias = null;
		// 输出端服务别名
		o_serveralias = null;
		// 数据来源
		sourceid = null;

		// 获取该服务的输入参数的值
		con = (Connection) message.getOtherParameter("con");

		processid = message.getUserParameterValue("processid");
		i_serveralias = message.getUserParameterValue("i_serveralias");
		o_serveralias = message.getUserParameterValue("o_serveralias");
		sourceid = message.getUserParameterValue("sourceid");
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		String debug_Service_CES = "流程号: processid = " + processid + "\n\r"
				+ "输入端服务别名: i_serveralias = " + i_serveralias + "\n\r"
				+ "输出端服务别名: o_serveralias = " + o_serveralias + "\n\r"
				+ "数据来源: sourceid = " + sourceid + "\n\r" + "数据库连接: con = "
				+ con + "\n\r";
		log.debug(processInfo + ",检查服务执行顺序时用户提交的参数: " + debug_Service_CES);

		if (processid == null || i_serveralias == null || o_serveralias == null
				|| sourceid == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "缺少输入参数", this.getId(),
					soa_processid, new java.util.Date(), null));
			log.error(processInfo + ",检查服务执行顺序中,缺少输入参数");
			return false;
		}
		return true;
	}

	public synchronized ExecuteResult doService(IMessage message,
			String soa_processid) {
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		// 初始化服务运行参数，如失败，返回执行结果
		if (!initFordo(message, soa_processid)) {
			log.error(processInfo + ",检查服务执行顺序中,初始化服务运行参数失败");
			return ExecuteResult.fail;
		}

		if (sourceid.trim().equals("1")) {
			return ExecuteResult.sucess;
		} else {
			log.error(processInfo + ",检查服务执行顺序中,服务来源不为 1");
		}

		try {
			Statement stmt = null;
			ResultSet rs = null;
			try {
				IDAO_Core dao = DAOFactory_Core.getInstance(DataBaseType
						.getDataBaseType(con));
				if (dao == null) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWNDATABASETYPE,
							"未知的数据库类型", this.getId(), soa_processid,
							new Date(), null));
					log.error(processInfo + ",检查服务执行顺序中,连接数据库错误,未知的数据库类型");
					return ExecuteResult.fail;
				}
				log.debug(processInfo + ",检查服务执行顺序类,加载数据库成功");
				stmt = con.createStatement();

				String i_sid = "";
				String o_sid = "";

				String sql_getSID = dao.getSQL_QuerySID(processid,
						i_serveralias);
				log.debug("根据流程号,服务别名(输入)获取运行顺序号的sql语句: sql_getSID = "
						+ sql_getSID);
				rs = stmt.executeQuery(sql_getSID);
				if (rs.next()) {
					i_sid = rs.getString(1);
				}

				String sql_getSID2 = dao.getSQL_QuerySID(processid,
						o_serveralias);
				log.debug("根据流程号,服务别名(输出)获取运行顺序号的sql语句: sql_getSID2 = "
						+ sql_getSID2);
				rs = stmt.executeQuery(sql_getSID2);
				if (rs.next()) {
					o_sid = rs.getString(1);
				}

				if (Integer.parseInt(i_sid) <= Integer.parseInt(o_sid)) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWN, "输出端服务运行滞后，请检查配置",
							this.getId(), soa_processid, new Date(), null));
					log.error(processInfo + ",检查服务执行顺序中,输出端服务运行滞后，请检查配置");
					return ExecuteResult.fail;
				}

			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), soa_processid, new Date(), null));
				log.fatal(processInfo + ",检查服务执行顺序中,数据库操作异常: "
						+ sqle.toString());
				return ExecuteResult.fail;
			} finally {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			}
		} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					soa_processid, new java.util.Date(), e));
			log.fatal(processInfo + ",检查服务执行顺序中,未知异常: " + e.toString());
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

}
