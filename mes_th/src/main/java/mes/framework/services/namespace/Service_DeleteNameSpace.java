package mes.framework.services.namespace;

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
 * 服务：删除命名空间定义信息 输入依赖：需要在otherparameter中放置一个Connection对象，名为con。<br>
 * 需要在用户输入或服务输出中含有namespace. <br>
 * 执行 doService() <br>
 * 初始化 initFordo() <br>
 * 回退 undoService() <br>
 * 输出：无输出
 * 
 * @author 于丽达 2008-01-02
 */
public class Service_DeleteNameSpace extends DefService implements IService {

	// 数据库连接
	private Connection con = null;

	// 命名空间号
	private String id = null;

	public Service_DeleteNameSpace() {
		super();
	}

	private final Log log = LogFactory.getLog(Service_DeleteNameSpace.class);

	public ExecuteResult doService(IMessage message, String soa_processid) {
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		// 初始化服务运行参数，如失败，返回执行结果
		if (!initFordo(message, soa_processid)) {
			log.error(processInfo + ",删除命名空间,初始化服务运行失败!");
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
					log.error(processInfo + ",删除命名空间,加载数据库错误,未知的数据库类型");
					return ExecuteResult.fail;
				}
				log.debug(processInfo + ",删除命名空间,加载数据库成功");
				int nid = Integer.parseInt(id);
				stmt = con.createStatement();
				// 查询此命名空间号的所有信息,放入到Map中
				Map<String, String> data = new HashMap<String, String>();
				String map_cnamespace = null;
				String map_cdescription = null;
				String map_sql_select = dao
						.getSQL_QueryNameSpaceForNameSpace(nid);
				log.debug(processInfo
						+ ",删除命名空间,查询此命名空间号的所有信息的sql语句: map_sql_select = "
						+ map_sql_select);
				rs = stmt.executeQuery(map_sql_select);
				while (rs.next()) {
					map_cnamespace = rs.getString(1);
					map_cdescription = rs.getString(2);
				}
				String rs_debug = "map_cnamespace = " + map_cnamespace + "\n"
						+ "map_cdescription = " + map_cdescription;
				log.debug(processInfo + "删除命名回退操作所需参数: rs_debug = " + rs_debug);
				data.put("map_id", String.valueOf(nid));
				data.put("map_cnamespace", map_cnamespace);
				data.put("map_cdescription", map_cdescription);
				message.setOtherParameter(this.getClass().getName(), data);
				/*
				 * 删除命名空间
				 */
				String sql_del = dao.getSQL_DeleteNameSpace(nid);
				log.debug("删除命名空间的sql语句: sql_del = " + sql_del);
				stmt.executeUpdate(sql_del);

			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), soa_processid, new Date(), sqle));
				log.fatal(processInfo + ",删除命名空间,数据库操作异常" + sqle.toString());
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
			log.fatal(processInfo + ",删除命名空间,未知异常" + e.toString());
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ExecuteResult undoService(IMessage message, String soa_processid) {
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		Object obj = message.getOtherParameter(this.getClass().getName());
		if (obj instanceof Map) {
			Map<String, String> map = (Map<String, String>) obj;
			int map_id = Integer.parseInt(map.get("map_id"));
			String map_cnamespace = map.get("map_cnamespace");
			String map_cdescription = map.get("map_cdescription");
			String map_debug = "map_id = " + map_id + "\n"
					+ "map_cnamespace = " + map_cnamespace + "\n"
					+ "map_cdescription = " + map_cdescription;
			log.debug(processInfo + "删除命名空间回退操作Map获取的值: map_debug = "
					+ map_debug);
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
						log.error(processInfo + ",删除命名空间回退操作,加载数据库错误,未知的数据库类型");
						return ExecuteResult.fail;
					}
					log.debug(processInfo + ",删除命名空间回退操作,加载数据库成功");
					stmt = con.createStatement();
					String sql_insert = dao.getSQL_InsertNameSpace(map_id,
							map_cnamespace, map_cdescription);
					log.debug(processInfo
							+ "删除命名空间回退操作的sql语句: map_sql_insert = "
							+ sql_insert);
					stmt.executeUpdate(sql_insert);
				} catch (SQLException sqle) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
									.getId(), soa_processid, new Date(), sqle));
					log.fatal(processInfo + ",删除命名空间回退操作,数据库操作异常"
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
				log.fatal(processInfo + ",删除命名空间回退操作,未知异常" + e.toString());
				return ExecuteResult.fail;
			}
		}
		return ExecuteResult.sucess;
	}

	private boolean initFordo(IMessage message, String soa_processid) {
		// 数据库连接
		con = null;
		// 命名空间号
		id = null;

		// 获取该服务的输入参数的值
		con = (Connection) message.getOtherParameter("con");
		id = message.getUserParameterValue("id");
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();

		String debug_Service_DeleteNameSpace = "命名空间号: id = " + id + "\n";
		log.debug(processInfo + ",删除命名空间时用户提交的参数:"
				+ debug_Service_DeleteNameSpace);

		if (id == null || con == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "缺少输入参数", this.getId(),
					soa_processid, new java.util.Date(), null));
			log.error(processInfo + ",删除命名空间,缺少输入参数");
			return false;
		}
		return true;
	}
}
