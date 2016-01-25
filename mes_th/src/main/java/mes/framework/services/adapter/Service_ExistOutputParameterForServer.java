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
 * 服务：返回服务中是否存在该输出参数名 输入依赖：需要在otherparameter中放置一个Connection对象，名为con。<br>
 * 需要在用户输入或服务输出中含有processid,serveralias parameter，sourceid 即使是空字符串。 <br>
 * 输出：无输出
 * 
 * @author 吕智 2007-6-22
 */
public class Service_ExistOutputParameterForServer extends DefService implements
		IService {

	// 数据库连接
	private Connection con = null;

	// 流程号
	private String processid = null;

	// 参数名
	private String parameter = null;

	// 服务别名
	private String serveralias = null;

	// 数据来源
	private String sourceid = null;

	public Service_ExistOutputParameterForServer() {
		super();
	}

	private final Log log = LogFactory
			.getLog(Service_ExistOutputParameterForServer.class);

	private boolean initFordo(IMessage message, String soa_processid) {
		// 数据库连接
		con = null;
		// 参数名
		parameter = null;
		// 服务别名
		serveralias = null;
		// 数据来源
		sourceid = null;
		// 流程号
		processid = null;

		// 获取该服务的输入参数的值
		con = (Connection) message.getOtherParameter("con");

		parameter = message.getUserParameterValue("parameter");

		serveralias = message.getUserParameterValue("serveralias");
		sourceid = message.getUserParameterValue("sourceid");
		processid = message.getUserParameterValue("processid");
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		String debug_Service_EOPFS = "参数名: parameter = " + parameter + "\n"
				+ "服务别名: serveralias = " + serveralias + "\n"
				+ "数据来源: sourceid = " + sourceid + "\n" + "流程号: processid = "
				+ processid + "\n";
		log.debug(processInfo + ",查询服务是否存在该输出参数名类用户提交的参数: "
				+ debug_Service_EOPFS);

		if (parameter == null || serveralias == null || processid == null
				|| sourceid == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "缺少输入参数", this.getId(),
					soa_processid, new java.util.Date(), null));
			log.error(processInfo + ",服务中是否存在该输出参数名类,初始化时缺少输入参数");
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
			log.error(processInfo + ",服务中是否存在该输出参数名类,初始化服务运行参数失败");
			return ExecuteResult.fail;
		}
		if (sourceid.trim().equals("1")) {
			return ExecuteResult.sucess;
		} else {
			log.error(processInfo + ",服务中是否存在该输出参数名类,服务号不是 1");
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
					log.error(processInfo
							+ ",服务中是否存在该输出参数名类,加载数据库出现错误,未知的数据库类型");
					return ExecuteResult.fail;
				}
				log.debug(processInfo + ",服务中是否存在该输出参数名类,加载数据库成功");
				stmt = con.createStatement();

				String sql_getServerID = dao
						.getSQL_QueryProcessServerForProcessIDServerAlias(
								processid, serveralias);
				log.debug("根据流程号和服务别名查询流程服务信息的sql语句: sql_getServerID = "
						+ sql_getServerID);
				String serverid = "";
				rs = stmt.executeQuery(sql_getServerID);
				if (rs.next()) {
					serverid = rs.getString(3);
				}

				String sql_check = dao.getSQL_QueryCountParameterInfo(serverid,
						parameter, "O");
				log.debug("根据服务号,参数及参数类型从服务参数表中获取记录数的sql语句: sql_check = "
						+ sql_check);
				int count = 0;
				rs = stmt.executeQuery(sql_check);
				if (rs.next()) {
					count = rs.getInt(1);
				}
				if (count <= 0) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWN, "输出端服务中无该输出参数！", this
									.getId(), soa_processid, new Date(), null));
					log.error(processInfo
							+ ",服务中是否存在该输出参数名类,输出端服务中无该输出参数 count = " + count);
					return ExecuteResult.fail;
				}
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), soa_processid, new Date(), null));
				log.fatal(processInfo + ",服务中是否存在该输出参数名类,出现数据库操作异常"
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
			log.fatal(processInfo + ",服务中是否存在该输出参数名类,出现未知异常" + e.toString());
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

}
