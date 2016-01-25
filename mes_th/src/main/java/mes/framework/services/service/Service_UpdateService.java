package mes.framework.services.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
 * 服务：更新服务定义信息 输入依赖：需要在otherparameter中放置一个Connection对象，名为con。<br>
 * 需要在用户输入或服务输出中含有serviceid,servicename,classname,servicedesc,namespace值，即使是空字符串。
 * <br>
 * 服务执行 doService() <br>
 * 服务初始化 initFordo() <br>
 * 服务回退 undoService() 如服务修改操作出现不通则回滚<br>
 * 输出：无输出
 * 
 * @author 于丽达 2007-12-24
 */
public class Service_UpdateService extends DefService implements IService {

	// 数据库连接
	private Connection con = null;

	// 服务号
	private String serviceid = null;

	// 服务名
	private String servicename = null;

	// 对应类名
	private String classname = null;

	// 服务的业务描述
	private String servicedesc = null;

	// 命名空间
	private String namespace = null;

	public Service_UpdateService() {
		super();
	}

	private final Log log = LogFactory.getLog(Service_UpdateService.class);

	public ExecuteResult doService(IMessage message, String soa_processid) {
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();

		// 初始化服务运行参数，如失败，返回执行结果
		if (!initFordo(message, soa_processid)) {
			log.error(processInfo + " , 修改服务,初始化服务运行失败");
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
							ServiceExceptionType.UNKNOWNDATABASETYPE,
							"未知的数据库类型", this.getId(), soa_processid,
							new Date(), null));
					log.error(processInfo + " , 修改服务时,加载数据库错误,未知的数据库类型");
					return ExecuteResult.fail;
				}
				log.debug(processInfo + " , 修改服务,加载数据库成功");
				stmt = con.createStatement();
				int sid = Integer.parseInt(serviceid);
				int ns = Integer.parseInt(namespace);

				// 查询此服务号的所有信息,放入到Map中
				Map<String, String> data = new HashMap<String, String>();
				int map_serviceid = 0;
				String map_cservername = null;
				String map_cclassname = null;
				String map_cdescription = null;
				int map_nnamespaceid = 0;
				String sql_selectService = dao.getSQL_QueryAllServices(String
						.valueOf(sid), "ById");
				result = stmt.executeQuery(sql_selectService);
				while (result.next()) {
					map_serviceid = result.getInt(1);
					map_cservername = result.getString(2);
					map_cclassname = result.getString(3);
					map_cdescription = result.getString(4);
					map_nnamespaceid = result.getInt(5);
				}
				data.put("map_serviceid", String.valueOf(map_serviceid));
				data.put("map_cservername", map_cservername);
				data.put("map_cclassname", map_cclassname);
				data.put("map_cdescription", map_cdescription);
				data.put("map_nnamespaceid", String.valueOf(map_nnamespaceid));
				message.setOtherParameter(this.getClass().getName(), data);
				// 修改服务操作
				String sql_update = dao.getSQL_UpdateService(sid, servicename,
						classname, servicedesc, ns);
				log.debug("修改服务的sql语句: sql_update = " + sql_update);
				stmt.executeUpdate(sql_update);
				
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), soa_processid, new Date(), sqle));
				log.fatal(processInfo + " , 修改服务,数据库操作异常" + sqle.toString());
				return ExecuteResult.fail;
			} finally {
				if(result!=null)
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
			log.fatal(processInfo + " , 修改服务,未知异常" + e.toString());
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

	private boolean initFordo(IMessage message, String soa_processid) {
		// 数据库连接
		con = null;
		// 服务号
		serviceid = null;
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
		serviceid = message.getUserParameterValue("serviceid");
		servicename = message.getUserParameterValue("servicename");
		classname = message.getUserParameterValue("classname");
		servicedesc = message.getUserParameterValue("servicedesc");
		namespace = message.getUserParameterValue("namespace");

		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();

		String debug_Service_UpdateService = "用户提交的参数: 服务号: serviceid = "
				+ serviceid + "\n" + "服务名: servicename = " + servicename + "\n"
				+ "对应类名: classname = " + classname + "\n"
				+ "服务的业务描述: servicedesc = " + servicedesc + "\n"
				+ "命名空间: namespace = " + namespace + "\n";
		log.debug(processInfo + " , 修改服务" + debug_Service_UpdateService);

		if (serviceid == null || servicename == null || classname == null
				|| servicedesc == null || namespace == null || con == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "缺少输入参数", this.getId(),
					soa_processid, new java.util.Date(), null));
			log.error(processInfo + " , 修改服务,缺少输入参数");
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public ExecuteResult undoService(IMessage message, String soa_processid) {
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
 
		Object obj = message.getOtherParameter(this.getClass().getName());
		if (obj instanceof Map) {
			Map<String, String> map = (Map<String, String>)obj;
			String map_serviceid = map.get("map_serviceid");
			String map_cservername = map.get("map_cservername");
			String map_cclassname = map.get("map_cclassname");
			String map_cdescription = map.get("map_cdescription");
			String map_nnamespaceid = map.get("map_nnamespaceid");
			String debug_map = "map_serviceid = " + map_serviceid + "\n"
					+ "map_cservername = " + map_cservername + "\n"
					+ "map_cclassname = " + map_cclassname + "\n"
					+ "map_cdescription = " + map_cdescription + "\n"
					+ "map_nnamespaceid = " + map_nnamespaceid + "\n";
			log.debug(processInfo + " , " + this.getClass().getName()
					+ " ,回退操作Map中值:    debug_map = " + debug_map);
			try {
				Statement st = null;
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
					// 将修改的服务项重新修改回来
					String sql_update = dao.getSQL_UpdateService(Integer
							.parseInt(map_serviceid), map_cservername,
							map_cclassname, map_cdescription, Integer
									.parseInt(map_nnamespaceid));
					log
							.debug("修改服务回退操作的sql语句: undo_sql_update = "
									+ sql_update);
					st = con.createStatement();
					st.executeUpdate(sql_update);
				} catch (SQLException sqle) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
									.getId(), soa_processid, new Date(), sqle));
					log.fatal(processInfo + " , " + this.getClass().getName()
							+ " ,回退操作数据库操作异常" + sqle.toString());
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
				log.fatal(processInfo + " , " + this.getClass().getName()
						+ " ,回退操作数据库操作异常" + e.toString());
				return ExecuteResult.fail;
			}
		}
		return super.undoService(message, soa_processid);
	}
}
