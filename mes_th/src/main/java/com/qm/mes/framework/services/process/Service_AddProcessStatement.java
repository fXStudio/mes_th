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
 * 添加流程定义 输入依赖：需要在otherparameter中放置一个Connection对象，名为con。<br>
 * 需要在用户输入中含有processid,processname,description,namespace值，即使是空字符串。 <br>
 * 流程定义执行 doService() <br>
 * 流程定义初始化 initFordo() <br>
 * 流程定义回退 undoService() 如流程定义添加操作出现不通则回滚<br>
 * 输出：无输出
 * 
 * @author 于丽达 2007-12-25
 * 
 */
public class Service_AddProcessStatement extends DefService implements IService {

	private Connection con = null;

	private String processid = null;

	private String processname = null;

	private String description = null;

	private String namespace = null;

	public Service_AddProcessStatement() {
		super();
	}

	private final Log log = LogFactory
			.getLog(Service_AddProcessStatement.class);

	public synchronized ExecuteResult doService(IMessage message,
			String soa_processid) {
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		if (!initFordo(message, soa_processid)) {
			log.error(processInfo + ",添加流程定义时,初始化加载失败");
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
					log.error(processInfo + ",添加流程定义时,数据库加载错误,未知的数据库类型");
					return ExecuteResult.fail;
				}
				log.debug(processInfo + ",添加流程定义,数据库加载成功");
				stmt = con.createStatement();
				String sql_maxid = dao.getSQL_QueryNextProcessid();
				log.debug("返回流程定义表中最大的流程号的sql语句: sql_maxid = " + sql_maxid);
				rs = stmt.executeQuery(sql_maxid);
				processid = "0";
				if (rs.next()) {
					processid = String.valueOf(rs.getInt(1));
				}

				String sql_check = dao
						.getSQL_QueryCountProcessStatementForProcessid(processid);
				log.debug("检查流程定义表是否有重复数据的sql语句: sql_check = " + sql_check);
				rs = stmt.executeQuery(sql_check);
				int count = 0;//
				if (rs.next()) {
					count = rs.getInt(1);
				}
				if (count > 0) //
				{
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWN, "数据已经存在", this
									.getId(), soa_processid, new Date(), null));
					log.error(processInfo + ",添加流程定义,数据已经存在 count = " + count);
					return ExecuteResult.fail;
				}
				// 将此流程号放入Map中
				Map<String, String> data = new HashMap<String, String>();
				data.put("map_processid", processid);
				message.setOtherParameter(this.getClass().getName(), data);
				/*
				 * 添加流程定义
				 */
				String sql_insert = dao.getSQL_InsertProcessStatement(
						processid, processname, description, namespace);
				log.debug("插入流程定义的sql语句: sql_insert = " + sql_insert);
				stmt.executeUpdate(sql_insert);

			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库异常" + sqle,
						this.getId(), soa_processid, new Date(), sqle));
				log.fatal(processInfo + ",添加流程定义时,数据库异常" + sqle.toString());
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
			log.fatal(processInfo + ",添加流程定义时,未知异常" + e.toString());
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

	private boolean initFordo(IMessage message, String soa_processid) {
		con = null;
		// 流程号
		processid = null;
		// 流程名
		processname = null;
		// 业务描述
		description = null;
		// 命名空间
		namespace = null;
		con = (Connection) message.getOtherParameter("con");
		processid = message.getUserParameterValue("processid");
		processname = message.getUserParameterValue("processname");
		description = message.getUserParameterValue("description");
		namespace = message.getUserParameterValue("namespace");
		// namespace = namespace == null ? "" : namespace;

		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();

		String debug_AddProcessStatement = "processid = " + processid + "\n"
				+ "processname = " + processname + "\n" + "description = "
				+ description + "\n" + "namespace = " + namespace;
		log.debug(processInfo + ",添加流程定义用户提交的参数: " + debug_AddProcessStatement);

		if (processid == null || processname == null || description == null
				|| namespace == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "", this.getId(),
					soa_processid, new java.util.Date(), null));
			log.error(processInfo + "添加流程定义时,缺少输入参数");
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
			String debug_map = "map_processid = " + map_processid;
			log.debug(processInfo + "添加流程定义回退操作Map接收的值: debug_map = "
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
						log.error(processInfo + "添加流程定义回退操作数据库加载失败");
					}
					log.debug(processInfo + "添加流程定义回退操作数据库加载成功");
					String undo_sql_delete = dao
							.getSQL_DeleteProcessStatement(map_processid);
					log.debug(processInfo + " , " + this.getClass().getName()
							+ "添加流程定义回退操作的sql语句: undo_sql_delete = "
							+ undo_sql_delete);
					stmt = con.createStatement();
					stmt.executeUpdate(undo_sql_delete);
				} catch (SQLException sqle) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.DATABASEERROR, "数据库异常" + sqle,
							this.getId(), soa_processid, new Date(), sqle));
					log.fatal(processInfo + ",添加流程定义回退时,数据库异常"
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
				log.fatal(processInfo + ",添加流程定义回退时,未知异常" + e.toString());
				return ExecuteResult.fail;
			}
		}
		return ExecuteResult.sucess;
	}

}
