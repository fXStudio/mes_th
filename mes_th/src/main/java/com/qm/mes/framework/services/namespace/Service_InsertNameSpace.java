package com.qm.mes.framework.services.namespace;

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
 * 服务：添加服务定义信息 输入依赖：需要在otherparameter中放置一个Connection对象，名为con。<br>
 * 需要在用户输入或服务输出中含有id,namespace,desc值，即使是空字符串。 <br>
 * 执行 doService() <br>
 * 初始化 initFordo() <br>
 * 回退 undoService() <br>
 * 输出：无输出
 * 
 * @author 于丽达 2008-01-02
 */
public class Service_InsertNameSpace extends DefService implements IService {

	// 数据库连接
	private Connection con = null;

	// 命名空间名称
	private String namespace = null;

	// 描述
	private String desc = null;

	public Service_InsertNameSpace() {
		super();
	}

	private final Log log = LogFactory.getLog(Service_InsertNameSpace.class);

	public synchronized ExecuteResult doService(IMessage message,
			String soa_processid) {
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		// 初始化服务运行参数，如失败，返回执行结果
		if (!initFordo(message, soa_processid)) {
			log.error(processInfo + ",添加命名空间,初始化服务运行失败!");
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
							ServiceExceptionType.UNKNOWNDATABASETYPE,
							"未知的数据库类型", this.getId(), soa_processid,
							new Date(), null));
					log.error(processInfo + ",添加命名空间,加载数据库错误,未知的数据库类型");
					return ExecuteResult.fail;
				}
				log.debug(processInfo + ",添加命名空间,加载数据库成功");
				stmt = con.createStatement();
				// 创建命名空间号
				String sql_create = dao.getSQL_QueryNextNameSpaceId();
				log.debug("创建命名空间号的sql语句: sql_create = " + sql_create);
				rs = stmt.executeQuery(sql_create);
				int id = 0;
				if (rs.next()) {
					id = rs.getInt(1);
				}
				// 将命名空间号放入到Map中
				Map<String, String> data = new HashMap<String, String>();
				data.put("map_id", String.valueOf(id));
				message.setOtherParameter(this.getClass().getName(), data);
				/*
				 * 添加命名空间
				 */
				String sql_insert = dao.getSQL_InsertNameSpace(id, namespace,
						desc);
				log.debug("添加命名空间的sql语句: sql_insert = " + sql_insert);
				stmt.executeUpdate(sql_insert);

			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), soa_processid, new Date(), sqle));
				log.fatal(processInfo + ",添加命名空间时,数据库操作异常" + sqle.toString());
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
			log.fatal(processInfo + ",添加命名空间时,未知异常" + e.toString());
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

	@SuppressWarnings("unchecked")
	public ExecuteResult undoService(IMessage message, String soa_processid) {
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		Object obj = message.getOtherParameter(this.getClass().getName());
		if (obj instanceof Map) {
			Map<String, String> map = (Map<String, String>) obj;
			int map_id = Integer.parseInt(map.get("map_id"));
			log.debug(processInfo + " , 添加命名空间Map获取的: map_id = " + map_id);
			try {
				Statement stmt = null;
				try {
					IDAO_Core dao = DAOFactory_Core.getInstance(DataBaseType
							.getDataBaseType(con));
					if (dao == null) {
						message.addServiceException(new ServiceException(
								ServiceExceptionType.UNKNOWNDATABASETYPE,
								"未知的数据库类型", this.getId(), soa_processid,
								new Date(), null));
						log.error(processInfo + ",添加命名空间回退操作,加载数据库错误,未知的数据库类型");
						return ExecuteResult.fail;
					}
					log.debug(processInfo + ",添加命名空间回退操作,加载数据库成功");
					stmt = con.createStatement();
					String sql_delete = dao.getSQL_DeleteNameSpace(map_id);
					log.debug(processInfo + " ,添加命名空间回退操作的sql语句: sql_delete = "
							+ sql_delete);
					stmt.executeUpdate(sql_delete);
				} catch (SQLException sqle) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.DATABASEERROR,
							"添加命名空间回退操作数据库异常", this.getId(), soa_processid,
							new Date(), sqle));
					log.fatal(processInfo + ",添加命名空间回退操作时,数据库操作异常"
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
				log.fatal(processInfo + ",添加命名空间回退操作时,未知异常" + e.toString());
				return ExecuteResult.fail;
			}
		}
		return ExecuteResult.sucess;
	}

	private boolean initFordo(IMessage message, String soa_processid) {
		// 数据库连接
		con = null;
		// 命名空间名称
		namespace = null;
		// 描述
		desc = null;

		// 获取该服务的输入参数的值
		con = (Connection) message.getOtherParameter("con");
		namespace = message.getUserParameterValue("namespace");
		desc = message.getUserParameterValue("desc");
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();

		String debug_Service_InsertNameSpace = "命名空间名称: namespace = "
				+ namespace + "\n" + "描述: desc = " + desc + "\n";
		log.debug(processInfo + ",添加命名空间时用户提交的参数: "
				+ debug_Service_InsertNameSpace);

		if (namespace == null || desc == null || con == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "缺少输入参数", this.getId(),
					soa_processid, new java.util.Date(), null));
			log.error(processInfo + ",添加命名空间时,缺少输入参数");
			return false;
		}
		return true;
	}
}
