/**
 * create by chenpeng
 * function: add a process 
 * process: insert a record into table process_services
 */
package com.qm.mes.framework.services.process;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.DataBaseType;
import com.qm.mes.framework.DefService;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.IProcess;
import com.qm.mes.framework.IService;
import com.qm.mes.framework.ProcessFactory;
import com.qm.mes.framework.ServiceException;
import com.qm.mes.framework.ServiceExceptionType;
import com.qm.mes.framework.dao.DAOFactory_Core;
import com.qm.mes.framework.dao.IDAO_Core;

/**
 * 添加流程服务 输入依赖：需要在otherparameter中放置一个Connection对象，名为con。<br>
 * 需要在用户输入中含有processid,sid,serverid,aliasname,actid值，即使是空字符串。 <br>
 * 流程服务执行 doService() <br>
 * 流程服务初始化 initFordo() <br>
 * 流程服务回退 undoService() 如流程服务添加操作出现不通则回滚<br>
 * 输出：无输出
 * 
 * @author 于丽达 2007-12-25
 * 
 */
public class Service_AddProcess extends DefService implements IService {

	private Connection con = null;

	private String processid = null;

	private String sid = null;

	private String serverid = null;

	private String aliasname = null;

	private String actid = null;

	public Service_AddProcess() {
		super();
	}

	private final Log log = LogFactory.getLog(Service_AddProcess.class);

	public synchronized ExecuteResult doService(IMessage message,
			String soa_processid) {
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		if (!initFordo(message, soa_processid)) {
			log.error(processInfo + ",添加流程服务初始化加载失败!");
			return ExecuteResult.fail;
		}

		try {
			Statement stmt = null;
			ResultSet rs = null;
			try {
				IDAO_Core dao = DAOFactory_Core.getInstance(DataBaseType
						.getDataBaseType(con));
				if (dao == null) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWNDATABASETYPE, "dao为空",
							this.getId(), soa_processid, new Date(), null));
					log.error(processInfo + ",添加流程服务时,数据库加载错误,未知的数据库类型");
					return ExecuteResult.fail;
				}
				log.debug(processInfo + ",添加流程服务时,数据库加载成功" + "\n");
				stmt = con.createStatement();
				String sql_check = dao
						.getSQL_QueryCountProcessServerForProcessidAndSid(
								processid, sid);
				log.debug("检查流程服务表是否有重复数据的sql语句: sql_check = " + sql_check);
				rs = stmt.executeQuery(sql_check);
				int count = 0;
				if (rs.next()) {
					count = rs.getInt(1);
				}
				if (count > 0) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWN, "数据已经存在", this
									.getId(), soa_processid, new Date(), null));
					log.error(processInfo + ",添加流程服务时,数据已经存在 count = " + count);
					return ExecuteResult.fail;
				}
				// 将流程号,运行号放入Map中
				Map<String, String> data = new HashMap<String, String>();
				data.put("map_processid", processid);
				data.put("map_sid", sid);
				message.setOtherParameter(this.getClass().getName(), data);
				/*
				 * 添加流程服务
				 */
				String sql_insert = dao.getSQL_InsertProcess(processid, sid,
						serverid, aliasname, actid);
				log.debug("插入流程服务的sql语句: sql_insert = " + sql_insert);
				stmt.executeUpdate(sql_insert);

			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库错误" + sqle,
						this.getId(), soa_processid, new Date(), sqle));
				log.fatal(processInfo + ",添加流程服务时,数据库异常" + sqle.toString());
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
			log.fatal(processInfo + ",添加流程服务时,未知异常" + e.toString());
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

	private boolean initFordo(IMessage message, String soa_processid) {
		con = null;
		// 流程号
		processid = null;
		// 运行号
		sid = null;
		// 服务号
		serverid = null;
		// 服务别名
		aliasname = null;
		// 异常类型号
		actid = null;
		con = (Connection) message.getOtherParameter("con");
		processid = message.getUserParameterValue("processid");
		sid = message.getUserParameterValue("sid");
		serverid = message.getUserParameterValue("serverid");
		aliasname = message.getUserParameterValue("aliasname");
		actid = message.getUserParameterValue("actid");

		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();

		String debug_AddProcess = "流程号: processid = " + processid + "\n"
				+ "运行号:sid = " + sid + "\n" + "服务号: serverid = " + serverid
				+ "\n" + " 服务别名:aliasname = " + aliasname + "\n"
				+ "异常类型号:actid = " + actid + "\n";
		log.debug(processInfo + ",添加流程服务时用户提交的参数: " + debug_AddProcess);

		if (processid == null || sid == null || serverid == null
				|| aliasname == null || actid == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "", this.getId(),
					soa_processid, new java.util.Date(), null));
			log.error(processInfo + ",添加流程服务时,缺少输入参数!");
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public ExecuteResult undoService(IMessage message, String soa_processid) {
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		Object obj = message.getOtherParameter(this.getClass().getName());
		if (obj instanceof Map) {
			Map<String, String> map = (Map<String, String>) obj;
			String map_processid = map.get("map_processid");
			String map_sid = map.get("map_sid");
			String debug_map = "map_processid = " + map_processid + "\n"
					+ "map_sid = " + map_sid;
			log
					.debug(processInfo + "添加流程服务回退操作接收到的值: debug_map = "
							+ debug_map);
			try {
				Statement stmt = null;
				try {
					IDAO_Core dao = DAOFactory_Core.getInstance(DataBaseType
							.getDataBaseType(con));
					if (dao == null) {
						message.addServiceException(new ServiceException(
								ServiceExceptionType.UNKNOWNDATABASETYPE,
								"dao为空", this.getId(), soa_processid,
								new Date(), null));
						log
								.error(processInfo
										+ ",添加流程服务回退操作时,数据库加载错误,未知的数据库类型");
						return ExecuteResult.fail;
					}
					log.debug(processInfo + ",添加流程服务回退操作时,数据库加载成功" + "\n");
					String undo_sql_delete = dao.getSQL_DeleteProcess(
							map_processid, map_sid);
					log.debug(processInfo + " , " + this.getClass().getName()
							+ "添加流程服务回退操作的sql语句: undo_sql_delete  = "
							+ undo_sql_delete);
					stmt = con.createStatement();
					stmt.executeUpdate(undo_sql_delete);
				} catch (SQLException sqle) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.DATABASEERROR, "数据库错误" + sqle,
							this.getId(), soa_processid, new Date(), sqle));
					log.fatal(processInfo + ",添加流程服务回退操作时,数据库异常"
							+ sqle.toString());
					return ExecuteResult.fail;
				} finally {
					if (stmt != null)
						stmt.close();
				}
			} catch (Exception e) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.UNKNOWN, e.toString(), this
								.getId(), soa_processid, new java.util.Date(),
						e));
				log.fatal(processInfo + ",添加流程服务回退操作时,未知异常" + e.toString());
				return ExecuteResult.fail;
			}
		}
		return ExecuteResult.sucess;
	}
}
