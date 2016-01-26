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
 * 删除流程定义 输入依赖：需要在otherparameter中放置一个Connection对象，名为con。<br>
 * 需要在用户输入中含有processid值 <br>
 * 流程定义执行 doService() <br>
 * 流程定义初始化 initFordo() <br>
 * 流程定义回退 undoService() <br>
 * 输出：无输出
 * 
 * @author 于丽达 2007-12-25
 * 
 */

public class Service_DeleteProcessStatement extends DefService implements
		IService {

	private Connection con = null;

	private String processid = null;

	public Service_DeleteProcessStatement() {
		super();
	}

	private final Log log = LogFactory
			.getLog(Service_DeleteProcessStatement.class);

	public ExecuteResult doService(IMessage message, String soa_processid) {
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		if (!initFordo(message, soa_processid)) {
			log.error(processInfo + ",删除流程定义初始化加载失败");
			return ExecuteResult.fail;
		}

		try {
			Statement stmt = null;
			ResultSet rs = null;
			ResultSet result = null;
			try {
				IDAO_Core dao = DAOFactory_Core.getInstance(DataBaseType
						.getDataBaseType(con));
				if (dao == null) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWNDATABASETYPE, "dao为空",
							this.getId(), soa_processid, new Date(), null));
					log.error(processInfo + ",删除流程定义时,数据库加载错误,未知的数据库类型");
					return ExecuteResult.fail;
				}
				log.debug(processInfo + ",删除流程定义,数据库加载成功");
				stmt = con.createStatement();
				String sql_check = dao
						.getSQL_QueryCountProcessStatementForProcessid(processid);
				log.debug("检查流程定义表是否有重复数据的sql语句: sql_check = " + sql_check);
				rs = stmt.executeQuery(sql_check);
				int count = 0;
				if (rs.next()) {
					count = rs.getInt(1);
					rs.close();
				}
				if (count == 0) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWN, "要删除的数据不存在", this
									.getId(), soa_processid, new Date(), null));
					log.error(processInfo + ",删除流程定义时,要删除的数据不存在 count = "
							+ count);
					return ExecuteResult.fail;
				} else {
					Map<String, String> data = new HashMap<String, String>();
					String map_processid = null;
					String map_processname = null;
					String map_description = null;
					String map_namespace = null;
					String sql_select = dao.getSQL_QueryAllProcessStatementIds(
							processid, "ById");
					log.debug(processInfo + "查询此流程号的所有信息的sql语句: sql_select = "
							+ sql_select);
					result = stmt.executeQuery(sql_select);
					while (result.next()) {
						map_processid = result.getString(1);
						map_processname = result.getString(2);
						map_description = result.getString(3);
						map_namespace = result.getString(4);
					}
					String debug_result_map = "select_map_processid = "
							+ map_processid + "\n"
							+ "select_map_processname = " + map_processname
							+ "\n" + "select_map_description = "
							+ map_description + "\n"
							+ "select_map_namespace = " + map_namespace;
					log.debug(processInfo + " , " + this.getClass().getName()
							+ "查询此流程号的所有信息是: debug_result_map = "
							+ debug_result_map);
					data.put("map_processid", map_processid);
					data.put("map_processname", map_processname);
					data.put("map_description", map_description);
					data.put("map_namespace", map_namespace);
					message.setOtherParameter(this.getClass().getName(), data);
					/*
					 * 删除流程定义
					 */
					String sql_delete = dao
							.getSQL_DeleteProcessStatement(processid);
					log.debug("删除流程定义的sql语句: sql_delete = " + sql_delete);
					stmt.executeUpdate(sql_delete);
				}

			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库错误" + sqle,
						this.getId(), soa_processid, new Date(), null));
				log.fatal(processInfo + ",删除流程定义,数据库异常" + sqle.toString());
				return ExecuteResult.fail;
			} finally {
				if (result != null)
					result.close();
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			}
		} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					soa_processid, new java.util.Date(), e));
			log.fatal(processInfo + ",删除流程定义,未知异常" + e.toString());
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

	private boolean initFordo(IMessage message, String soa_processid) {

		con = null;
		// 流程号
		processid = null;
		con = (Connection) message.getOtherParameter("con");
		processid = message.getUserParameterValue("processid");
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();

		String debug_DeleteProcessStatement = "processid = " + processid + "\n";
		log.debug(processInfo + ",删除流程定义时用户提交的参数:"
				+ debug_DeleteProcessStatement);

		if (processid == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "传入参数为空", this.getId(),
					soa_processid, new java.util.Date(), null));
			log.error(processInfo + ",删除流程定义时,缺少输入参数");
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
			String map_processname = map.get("map_processname");
			String map_description = map.get("map_description");
			String map_namespace = map.get("map_namespace");
			String debug_map = "map_processid = " + map_processid + "\n"
					+ "map_processname = " + map_processname + "\n"
					+ "map_description = " + map_description + "\n"
					+ "map_namespace = " + map_namespace;
			log.debug(processInfo + "删除流程定义中回退操作Map接收到的值: debug_map = "
					+ debug_map);
			try {
				Statement st = null;
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
										+ ",删除流程定义回退操作时,数据库加载错误,未知的数据库类型");
						return ExecuteResult.fail;
					}
					log.debug(processInfo + ",删除流程定义回退操作,数据库加载成功");
					st = con.createStatement();
					String undo_sql_insert = dao.getSQL_InsertProcessStatement(
							map_processid, map_processname, map_description,
							map_namespace);
					log.debug(processInfo + " , " + this.getClass().getName()
							+ "删除流程定义回退操作的sql语句: undo_sql_insert = "
							+ undo_sql_insert);
					st.executeUpdate(undo_sql_insert);
				} catch (SQLException sqle) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.DATABASEERROR, "数据库错误" + sqle,
							this.getId(), soa_processid, new Date(), sqle));
					log.fatal(processInfo + ",删除流程定义回退操作,数据库异常" + sqle.toString());
					return ExecuteResult.fail;
				} finally {
					if (st != null)
						st.close();
				}
			} catch (Exception e) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.UNKNOWN, e.toString(), this
								.getId(), soa_processid, new java.util.Date(),
						e));
				log.fatal(processInfo + ",删除流程定义回退操作,未知异常" + e.toString());
				return ExecuteResult.fail;
			}
		}
		return ExecuteResult.sucess;
	}
}
