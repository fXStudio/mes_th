package mes.framework.services.service;

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
import mes.framework.ProcessFactory;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.framework.dao.DAOFactory_Core;
import mes.framework.dao.IDAO_Core;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 服务：添加服务定义信息 输入依赖：需要在otherparameter中放置一个Connection对象，名为con。<br>
 * 需要在用户输入或服务输出中含有servicename,classname,servicedesc,namespace值，即使是空字符串。 <br>
 * 服务执行 doService() <br>
 * 服务初始化 initFordo() <br>
 * 服务回退 undoService() 如服务添加操作出现不通则回滚<br>
 * 输出：无输出
 * 
 * @author 于丽达 2007-12-24
 */
public class Service_InsertService extends DefService implements IService {

	// 数据库连接
	private Connection con = null;

	// 服务名
	private String servicename = null;

	// 对应类名
	private String classname = null;

	// 服务的业务描述
	private String servicedesc = null;

	// 命名空间
	private String namespace = null;

	public Service_InsertService() {
		super();
	}

	private final Log log = LogFactory.getLog(Service_InsertService.class);

	public synchronized ExecuteResult doService(IMessage message,
			String soa_processid) {
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		// 初始化服务运行参数，如失败，返回执行结果
		if (!initFordo(message, soa_processid)) {
			log.error(processInfo + " , 添加服务,初始化服务运行失败");
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
					log.error(processInfo + " , 添加服务时,加载数据库错误,未知的数据库类型");
					return ExecuteResult.fail;
				}
				log.debug(processInfo + " , 添加服务,加载数据库成功");
				stmt = con.createStatement();
				// 生成服务号
				String sql_create = dao.getSQL_QueryNextServiceId();
				log.debug(" 生成服务号的sql语句: sql_create = " + sql_create);
				rs = stmt.executeQuery(sql_create);
				int sid = 0;// 服务号
				if (rs.next()) {
					sid = rs.getInt(1);
				}
				log.debug(" 生成服务号: sid = " + sid);

				// 将此服务号放入到Map中
				Map<String, String> data = new HashMap<String, String>();
				data.put("map_serviceid", String.valueOf(sid));
				message.setOtherParameter(this.getClass().getName(), data);
				// 添加服务
				String sql_insert = dao.getSQL_InsertService(sid, servicename,
						classname, servicedesc, namespace);
				log.debug(" 添加服务的sql语句: sql_insert = " + sql_insert);
				stmt.executeUpdate(sql_insert);
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), soa_processid, new Date(), sqle));
				log.fatal(processInfo + " , 添加服务时,数据库操作异常" + sqle.toString());
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
			log.fatal(processInfo + " , 添加服务,未知异常" + e.toString());

			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

	private boolean initFordo(IMessage message, String soa_processid) {
		// 数据库连接
		con = null;
		// 服务名
		servicename = null;
		// 对应类名
		classname = null;
		// 服务的业务描述
		servicedesc = null;
		// 命名空间
		namespace = null;

		// 获取该服务的输入参数的值
		con = (Connection) message.getOtherParameter("con");
		servicename = message.getUserParameterValue("servicename");
		classname = message.getUserParameterValue("classname");
		servicedesc = message.getUserParameterValue("servicedesc");
		namespace = message.getUserParameterValue("namespace");

		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();

		String debug_Service_InsertService = "用户提交的参数: \n "
				+ "服务名: servicename = " + servicename + "\n"
				+ "对应类名: classname = " + classname + "\n"
				+ "服务的业务描述: servicedesc = " + servicedesc + "\n"
				+ "命名空间: namespace = " + namespace + "\n";
		log.debug(processInfo + " , 添加服务: " + debug_Service_InsertService);

		if (servicename == null || classname == null || servicedesc == null
				|| namespace == null || con == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "缺少输入参数", this.getId(),
					soa_processid, new java.util.Date(), null));
			log.error(processInfo + " , 添加服务,缺少输入参数");

			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public synchronized ExecuteResult undoService(IMessage message,
			String soa_processid) {
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		Statement stmt = null;
		Object obj = message.getOtherParameter(this.getClass().getName());
		if (obj instanceof Map) {
			Map<String, String> map = (Map<String, String>) obj;
			String map_serviceid = map.get("map_serviceid");

			String debug_map = "map_serviceid = " + map_serviceid + "\n";
			log.debug(processInfo + " , " + this.getClass().getName()
					+ " ,回退操作Map中值:    debug_map = " + debug_map);
			try {
				try {
					IDAO_Core dao = DAOFactory_Core.getInstance(DataBaseType
							.getDataBaseType(con));
					if (dao == null) {
						message.addServiceException(new ServiceException(
								ServiceExceptionType.UNKNOWNDATABASETYPE,
								"未知的数据库类型", this.getId(), soa_processid,
								new Date(), null));
						log.error(processInfo + " , "
								+ this.getClass().getName()
								+ " ,回退操作,加载数据库错误,未知的数据库类型");
						return ExecuteResult.fail;
					}
					log.debug(processInfo + " , " + this.getClass().getName()
							+ " ,回退操作数据库加载成功");
					// 将添加的服务项删除下去
					String sql_del = dao.getSQL_DeleteService(Integer
							.parseInt(map_serviceid));
					log.debug(processInfo + " , " + this.getClass().getName()
							+ " ,回退操作的sql语句:  undo_sql_del = " + sql_del);
					stmt = con.createStatement();
					stmt.executeUpdate(sql_del);
				} catch (SQLException sqle) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
									.getId(), soa_processid, new Date(), sqle));
					log.fatal(processInfo + " , " + this.getClass().getName()
							+ " ,回退操作数据库操作异常" + sqle.toString());
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
				log.fatal(processInfo + " , " + this.getClass().getName()
						+ " ,回退操作数据库操作异常" + e.toString());
				return ExecuteResult.fail;
			}
		}
		return super.undoService(message, soa_processid);
	}
}
