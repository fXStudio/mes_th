package mes.framework.services.adapter;

import java.sql.*;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mes.framework.*;
import mes.framework.dao.*;

/**
 * 服务：返回流程中是否存在该服务别名,如数据来源于用户则直接返回成功。
 * 输入依赖：需要在otherparameter中放置一个Connection对象，名为con。<br>
 * 需要在用户输入或服务输出中含有processid,serveralias, sourceid 即使是空字符串。 <br>
 * 输出：无输出
 * 
 * @author 吕智 2007-6-22
 */
public class Service_ExistServerForProcess_SourceID extends DefService
		implements IService {

	// 数据库连接
	private Connection con = null;

	// 流程号
	private String processid = null;

	// 服务别名
	private String serveralias = null;

	// 数据来源
	private String sourceid = null;

	public Service_ExistServerForProcess_SourceID() {
		super();
	}

	private final Log log = LogFactory
			.getLog(Service_ExistServerForProcess_SourceID.class);

	private boolean initFordo(IMessage message, String soa_processid) {
		// 数据库连接
		con = null;
		// 流程号
		processid = null;
		// 服务别名
		serveralias = null;
		// 数据来源
		sourceid = null;

		// 获取该服务的输入参数的值
		con = (Connection) message.getOtherParameter("con");

		processid = message.getUserParameterValue("processid");
		serveralias = message.getUserParameterValue("serveralias");
		sourceid = message.getUserParameterValue("sourceid");
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		String debug_Service_ESFP_SourceID = "流程号: processid = " + processid
				+ "\n" + "服务别名: serveralias = " + serveralias + "\n"
				+ "数据来源: sourceid = " + sourceid + "\n";
		log.debug(processInfo + ",返回流程中是否存在该服务别名类用户提交的参数: "
				+ debug_Service_ESFP_SourceID);

		if (processid == null || serveralias == null || sourceid == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "缺少输入参数", this.getId(),
					soa_processid, new java.util.Date(), null));
			log.error(processInfo + ",流程中是否存在该服务别名类,初始化时缺少输入参数");
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
			log.error(processInfo + ",流程中是否存在该服务别名类,加载初始化失败");
			return ExecuteResult.fail;
		}

		if (sourceid.trim().equals("1")) {
			return ExecuteResult.sucess;
		} else {
			log.error(processInfo + ",流程中是否存在该服务别名类,服务号不是 1");
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
					log
							.error(processInfo
									+ ",流程中是否存在该服务别名类,加载数据库出现错误,未知的数据库类型");
					return ExecuteResult.fail;
				}
				log.debug(processInfo + ",流程中是否存在该服务别名类,数据库加载成功");
				stmt = con.createStatement();
				String sql_check = dao.getSQL_QueryCountProcessServerInfo(
						processid, serveralias);
				log.debug("根据流程号,服务别名从流程服务表中获取记录数的sql语句: sql_check = "
						+ sql_check);
				rs = stmt.executeQuery(sql_check);
				int count = 0;
				if (rs.next()) {
					count = rs.getInt(1);
				}
				if (count <= 0) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWNDATABASETYPE,
							"该流程中不存在该服务别名", this.getId(), soa_processid,
							new Date(), null));
					log.error(processInfo
							+ ",流程中是否存在该服务别名类,该流程中不存在该服务别名 count = " + count);
					return ExecuteResult.fail;
				}
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), soa_processid, new Date(), null));
				log.fatal(processInfo + ",流程中是否存在该服务别名类,数据库操作异常"
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
			log.fatal(processInfo + ",流程中是否存在该服务别名类,未知异常" + e.toString());
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

}
