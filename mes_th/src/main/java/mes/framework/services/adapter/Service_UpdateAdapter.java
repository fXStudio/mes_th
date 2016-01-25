package mes.framework.services.adapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mes.framework.DataBaseType;
import mes.framework.DefService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.IProcess;
import mes.framework.IService;
import mes.framework.MessageAdapterFactory;
import mes.framework.ProcessFactory;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.framework.dao.DAOFactory_Core;
import mes.framework.dao.IDAO_Core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 服务：更新适配器 输入依赖：需要在otherparameter中放置一个Connection对象，名为con。<br>
 * 需要在用户输入或服务输出中含有processid,i_serveralias,i_parameter,source,o_serveralias和o_parameter值，即使是空字符串。
 * <br>
 * 适配器执行 doService() <br>
 * 适配器初始化 initFordo() <br>
 * 适配器回退 undoService()<br>
 * 输出：无输出
 * 
 * @author 于丽达 2007-12-25
 */
public class Service_UpdateAdapter extends DefService implements IService {

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

	public Service_UpdateAdapter() {
		super();
	}

	// 设置log4j日志
	private final Log log = LogFactory.getLog(Service_UpdateAdapter.class);

	public ExecuteResult doService(IMessage message, String soa_processid) {
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		// 初始化服务运行参数，如失败，返回执行结果
		if (!initFordo(message, soa_processid)) {
			log.error(processInfo + ",修改适配器,初始化服务运行参数失败");
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
					log.error(processInfo + ",修改适配器中,数据库加载类型错误,未知的数据库类型");
					return ExecuteResult.fail;
				}
				log.debug(processInfo + ",修改适配器中,数据库加载成功");
				stmt = con.createStatement();

				Map<String, String> data = new HashMap<String, String>();
				String map_processid = null;
				String map_i_serveralias = null;
				String map_i_parameter = null;
				String map_source = null;
				String map_o_serveralias = null;
				String map_o_parameter = null;
				String data_sql_select = dao.getSQL_QueryAdepterInfo(processid);
				log.debug(processInfo + "查询此流程号的所有信息的sql语句: data_sql_select = "
						+ data_sql_select);
				rs = stmt.executeQuery(data_sql_select);
				while (rs.next()) {
					map_processid = rs.getString(1);
					map_i_serveralias = rs.getString(2);
					map_i_parameter = rs.getString(3);
					map_source = rs.getString(4);
					map_o_serveralias = rs.getString(5);
					map_o_parameter = rs.getString(6);
				}
				String debug_result_map = "select_map_processid = "
						+ map_processid + "\n" + "select_map_i_serveralias = "
						+ map_i_serveralias + "\n"
						+ "select_map_i_parameter = " + map_i_parameter + "\n"
						+ "select_map_source = " + map_source + "\n"
						+ "select_map_o_serveralias = " + map_o_serveralias
						+ "\n" + "select_map_o_parameter = " + map_o_parameter;
				log.debug(processInfo + " , " + this.getClass().getName()
						+ "查询此流程号的所有信息是: debug_result_map = "
						+ debug_result_map);
				data.put("map_processid", map_processid);
				data.put("map_i_serveralias", map_i_serveralias);
				data.put("map_i_parameter", map_i_parameter);
				data.put("map_source", map_source);
				data.put("map_o_serveralias", map_o_serveralias);
				data.put("map_o_parameter", map_o_parameter);
				message.setOtherParameter(this.getClass().getName(), data);
				/*
				 * 修改适配器
				 */
				String sql_update = dao.getSQL_UpdateAdapterInfo(processid,
						i_serveralias, i_parameter, source, o_serveralias,
						o_parameter);
				log.debug("根据流程号,输入服务别名,输入参数名,更新数据来源号,输出服务别名,输出参数名的sql语句: "
						+ "sql_update = " + sql_update);
				stmt.executeUpdate(sql_update);
				/**
				 * 启用新适配器
				 */
				MessageAdapterFactory.loadAllMessageAdapter(con);
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), soa_processid, new Date(), sqle));
				log.fatal(processInfo + ",修改适配器_数据库操作异常:" + sqle.toString());
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
			log.fatal(processInfo + ",修改适配器时出现未知异常" + e.toString());
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
		String debug_Service_UpdateAdapter = "流程号: processid = " + processid
				+ "\n\r" + "输入端服务别名: i_serveralias = " + i_serveralias + "\n\r"
				+ "输入端的参数名: i_parameter = " + i_parameter + "\n\r"
				+ "数据来源: source = " + source + "\n\r"
				+ "输出端的服务别名: o_serveralias = " + o_serveralias + "\n\r"
				+ "输出端的参数名: o_parameter = " + o_parameter + "\n\r"
				+ "数据库连接: con = " + con + "\n\r";

		log
				.debug(processInfo + ",修改适配器: " + debug_Service_UpdateAdapter
						+ "\n");

		if (processid == null || i_serveralias == null || i_parameter == null
				|| source == null || o_serveralias == null
				|| o_parameter == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "缺少输入参数", this.getId(),
					soa_processid, new java.util.Date(), null));
			log.error(processInfo + ",修改适配器操作中,缺少输入参数" + "\n");

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
			String map_source = map.get("map_source");
			String map_o_serveralias = map.get("map_o_serveralias");
			String map_o_parameter = map.get("map_o_parameter");
			String debug_map = "map_processid = " + map_processid + "\n"
					+ "map_i_serveralias = " + map_i_serveralias + "\n"
					+ "map_i_parameter = " + map_i_parameter + "\n"
					+ "map_source = " + map_source + "\n"
					+ "map_o_serveralias = " + map_o_serveralias + "\n"
					+ "map_o_parameter = " + map_o_parameter;
			log.debug(processInfo + " , " + this.getClass().getName()
					+ "修改适配器中回退操作Map接收到的值: debug_map = " + debug_map);
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
										+ ",修改适配器回退操作时,数据库加载错误,未知的数据库类型");
						return ExecuteResult.fail;
					}
					log.debug(processInfo + ",修改流程服务回退操作,数据库加载成功");
					stmt = con.createStatement();
					String undo_sql_update = dao.getSQL_UpdateAdapterInfo(
							map_processid, map_i_serveralias, map_i_parameter,
							map_source, map_o_serveralias,map_o_parameter);
					log.debug(processInfo + " , " + this.getClass().getName()
							+ "修改适配器回退操作的sql语句: undo_sql_update = "
							+ undo_sql_update);
					stmt.executeUpdate(undo_sql_update);
				} catch (SQLException sqle) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.DATABASEERROR, "数据库错误" + sqle,
							this.getId(), soa_processid, new Date(), sqle));
					log.fatal(processInfo + ",修改适配器回退操作,数据库异常"
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
				log.fatal(processInfo + ",修改适配器回退操作,未知异常" + e.toString());
				return ExecuteResult.fail;
			}
		}
		return ExecuteResult.sucess;
	}
}
