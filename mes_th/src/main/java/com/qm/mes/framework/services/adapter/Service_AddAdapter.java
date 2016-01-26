package com.qm.mes.framework.services.adapter;

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
 * 服务：添加适配器 输入依赖：需要在otherparameter中放置一个Connection对象，名为con。<br>
 * 需要在用户输入或服务输出中含有processid,i_serveralias,i_parameter,source,o_serveralias和o_parameter值，即使是空字符串。
 * <br>
 * 适配器执行 doService() <br>
 * 适配器初始化 initFordo() <br>
 * 适配器回退 undoService()<br>
 * 输出：无输出
 * 
 * @author 于丽达 2007-12-25
 */
public class Service_AddAdapter extends DefService implements IService {

	// 数据库连接
	private Connection con = null;

	// 流程号
	private String processid = null;

	// 输入端服务别名
	private String i_serveralias = null;

	// 输入端的参数名
	private String i_parameter = null;

	// 数据来源
	private String source = null;

	// 输出端的服务别名
	private String o_serveralias = null;

	// 输出端的参数名
	private String o_parameter = null;

	public Service_AddAdapter() {
		super();
	}

	// 设置Log4j日志
	private final Log log = LogFactory.getLog(Service_AddAdapter.class);

	public synchronized ExecuteResult doService(IMessage message,
			String soa_processid) {
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		// 初始化服务运行参数，如失败，返回执行结果
		if (!initFordo(message, soa_processid)) {
			log.error(processInfo + ",添加适配器,初始化服务运行参数失败");
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
					log.error(processInfo + ",数据库加载类型错误,未知的数据库类型");
					return ExecuteResult.fail;
				}
				log.debug(processInfo + ",数据库加载成功");
				stmt = con.createStatement();
				String sql_check = dao.getSQL_QueryCountAdeptInfo(processid,
						i_serveralias, i_parameter);
				log.debug("根据流程号,输入服务别名,输入参数,从适配器信息表中获取记录数的sql语句: sql_check = "
						+ sql_check);
				rs = stmt.executeQuery(sql_check);
				int count = 0;
				if (rs.next()) {
					count = rs.getInt(1);
				}
				if (count > 0) // 信息重复
				{
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWN, "该适配器信息已存在!", this
									.getId(), soa_processid, new Date(), null));
					log.error(processInfo + ",该适配器信息已经存在 count = " + count);
					return ExecuteResult.fail;
				}
				Map<String, String> data = new HashMap<String, String>();
				data.put("map_processid", processid);
				data.put("map_i_serveralias", i_serveralias);
				data.put("map_i_parameter", i_parameter);
				message.setOtherParameter(this.getClass().getName(), data);
				String sql_insert = dao.getSQL_InsertAdapterInfo(processid,
						i_serveralias, i_parameter, source, o_serveralias,
						o_parameter);
				log.debug("添加适配器信息的sql语句: sql_insert = " + sql_insert);
				stmt.executeUpdate(sql_insert);

			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), soa_processid, new Date(), sqle));
				log.fatal(processInfo + ",添加适配器_数据库操作异常:" + sqle.toString());
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
			log.fatal(processInfo + ",添加适配器时出现未知异常" + e.toString());
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

	private boolean initFordo(IMessage message, String soa_processid) {
		// 数据库连接
		con = null;
		// 流程号
		processid = null;
		// 输入端服务别名
		i_serveralias = null;
		// 输入端的参数名
		i_parameter = null;
		// 数据来源
		source = null;
		// 输出端的服务别名
		o_serveralias = null;
		// 输出端的参数名
		o_parameter = null;

		// 获取该服务的输入参数的值
		con = (Connection) message.getOtherParameter("con");

		processid = message.getUserParameterValue("processid");
		i_serveralias = message.getUserParameterValue("i_serveralias");
		i_parameter = message.getUserParameterValue("i_parameter");
		source = message.getUserParameterValue("source");
		o_serveralias = message.getUserParameterValue("o_serveralias");
		o_parameter = message.getUserParameterValue("o_parameter");
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		String debug_Service_AddAdapter = "流程号: processid = " + processid
				+ "\n" + "输入端服务别名: i_serveralias = " + i_serveralias + "\n"
				+ "输入端的参数名: i_parameter = " + i_parameter + "\n"
				+ "数据来源: source = " + source + "\n"
				+ "输出端的服务别名: o_serveralias = " + o_serveralias + "\n"
				+ "输出端的参数名: o_parameter = " + o_parameter + "\n";

		log.debug(processInfo + ",添加适配器时用户提交的参数: " + debug_Service_AddAdapter);

		if (processid == null || i_serveralias == null || i_parameter == null
				|| source == null || o_serveralias == null
				|| o_parameter == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "缺少输入参数", this.getId(),
					soa_processid, new java.util.Date(), null));
			log.error(processInfo + ",添加适配器操作中,缺少输入参数");
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ExecuteResult undoService(IMessage message, String soa_processid) {
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		Object obj = message.getOtherParameter(this.getClass().getName());
		if (obj instanceof Map) {
			Map<String, String> map = (Map<String, String>) obj;
			String map_processid = map.get("map_processid");
			String map_i_serveralias = map.get("map_i_serveralias");
			String map_i_parameter = map.get("map_i_parameter");
			String debug_map = "map_processid = " + map_processid + "\n"
					+ "map_i_serveralias = " + map_i_serveralias + "\n"
					+ "map_i_parameter = " + map_i_parameter;
			log.debug(processInfo + "添加适配器回退操作接收到的值: debug_map = " + debug_map);
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
						log.error(processInfo + ",添加适配器回退操作时,数据库加载错误,未知的数据库类型");
						return ExecuteResult.fail;
					}
					log.debug(processInfo + ",添加适配器回退操作时,数据库加载成功" + "\n");
					String undo_sql_delete = dao.getSQL_DeleteAdapterInfo(
							map_processid, map_i_serveralias, map_i_parameter);
					log.debug(processInfo + " , " + this.getClass().getName()
							+ "添加适配器回退操作的sql语句: undo_sql_delete  = "
							+ undo_sql_delete);
					stmt = con.createStatement();
					stmt.executeUpdate(undo_sql_delete);
				} catch (SQLException sqle) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.DATABASEERROR, "数据库错误" + sqle,
							this.getId(), soa_processid, new Date(), sqle));
					log.fatal(processInfo + ",添加适配器回退操作时,数据库异常"
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
