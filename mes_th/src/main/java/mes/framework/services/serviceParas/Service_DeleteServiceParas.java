package mes.framework.services.serviceParas;

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
 * 服务：删除适配器 输入依赖：需要在otherparameter中放置一个Connection对象，名为con。<br>
 * 需要在用户输入或服务输出中含有serviceid，paratype,paraname. <br>
 * 服务执行 doService() <br>
 * 服务初始化 initFordo() <br>
 * 服务回退 undoService()<br>
 * 输出：无输出
 * 
 * @author 于丽达 2007-12-25
 */
public class Service_DeleteServiceParas extends DefService implements IService {

	// 数据库连接
	private Connection con = null;

	// 服务号
	private String serviceid = null;

	// 参数类型
	private String paratype = null;

	// 参数名
	private String paraname = null;

	public Service_DeleteServiceParas() {
		super();
	}

	private final Log log = LogFactory.getLog(Service_DeleteServiceParas.class);

	public ExecuteResult doService(IMessage message, String soa_processid) {
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();

		// 初始化服务运行参数，如失败，返回执行结果
		if (!initFordo(message, soa_processid)) {
			log.error(processInfo + ",删除服务参数时,初始化服务运行失败");
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
					log.error(processInfo + ",删除服务参数时,加载数据库错误,未知的数据库类型");
					return ExecuteResult.fail;
				}
				log.debug(processInfo + ",删除服务参数时,加载数据库成功!");
				stmt = con.createStatement();
				int sid = Integer.parseInt(serviceid);
				/*
				 * 回退操作所需参数
				 */
				Map<String, String> data = new HashMap<String, String>();
				String map_paraname = null;
				String map_paratype = null;
				String map_paradesc = null;
				String sql_selectPara = dao.getSQL_QueryServicePara(sid,
						paratype, paraname);
				log.debug(processInfo + "查询此服务号的原有参数,放入到Map中的sql语句:"
						+ sql_selectPara);
				rs = stmt.executeQuery(sql_selectPara);
				while (rs.next()) {
					map_paraname = rs.getString(1);
					map_paratype = rs.getString(2);
					map_paradesc = rs.getString(3);
				}
				String debug_map = "result_map_paraname = " + map_paraname
						+ "\n" + "result_map_paratype = " + map_paratype + "\n"
						+ "result_map_paradesc = " + map_paradesc;
				log.debug(processInfo + " , " + this.getClass().getName()
						+ " ,回退操作Map中值:    debug_map = " + debug_map);
				// 将服务号,参数类型,参数名放入Map中
				data.put("map_serviceid", String.valueOf(sid));
				data.put("map_paratype", map_paratype);
				data.put("map_paraname", map_paraname);
				data.put("map_paradesc", map_paradesc);
				message.setOtherParameter(this.getClass().getName(), data);
				/*
				 * 删除参数
				 */
				String sql_del = dao.getSQL_DeleteServicePara(sid, paratype,
						paraname);
				log.debug("删除参数的sql语句: sql_del = " + sql_del);
				stmt.executeUpdate(sql_del);

			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), soa_processid, new Date(), sqle));
				log.fatal(processInfo + ",删除服务参数,数据库操作异常" + sqle.toString());
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
			log.fatal(processInfo + ",删除服务参数,未知异常" + e.toString());
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

	private boolean initFordo(IMessage message, String soa_processid) {
		// 数据库连接
		con = null;
		// 流程号
		serviceid = null;

		// 获取该服务的输入参数的值
		con = (Connection) message.getOtherParameter("con");
		serviceid = message.getUserParameterValue("serviceid");
		paratype = message.getUserParameterValue("paratype");
		paraname = message.getUserParameterValue("paraname");
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();

		String debug_DeleteServicePara = "流程号: serviceid = " + serviceid + "\n";
		log.debug(processInfo + ",删除服务参数时用户提交的参数: " + debug_DeleteServicePara);

		if (serviceid == null || paratype == null || paraname == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "缺少输入参数", this.getId(),
					soa_processid, new java.util.Date(), null));
			log.error(processInfo + ",删除服务参数,缺少输入参数");
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
			int map_serviceid = Integer.parseInt(map.get("map_serviceid"));
			String map_paraname = map.get("map_paraname");
			String map_paratype = map.get("map_paratype");
			String map_paradesc = map.get("map_paradesc");
			String debug_undo = "map_serviceid = " + map_serviceid + "\n"
					+ "map_paraname = " + map_paraname + "\n"
					+ "map_paratype = " + map_paratype + "\n"
					+ "map_paradesc = " + map_paradesc;
			log
					.debug(processInfo + "回退操作中接收到的map值: debug_undo = "
							+ debug_undo);
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
						log.error(processInfo + ",删除服务参数的回退操作加载数据库时,未知的数据库类型");
						return ExecuteResult.fail;
					}
					log.debug(processInfo + "删除服务参数的回退操作加载数据库成功");
					//删除服务参数回退操作的插入
					String undo_sql_insert = dao.getSQL_InsertServicePara(
							map_serviceid, map_paraname, map_paratype,
							map_paradesc);
					log.debug(processInfo + " , " + this.getClass().getName()
							+ "删除服务参数的回退操作的sql语句: undo_sql_insert = "
							+ undo_sql_insert);
					stmt = con.createStatement();
					stmt.executeUpdate(undo_sql_insert);
				} catch (SQLException sqle) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
									.getId(), soa_processid, new Date(), sqle));
					log.fatal("删除服务参数的回退操作中数据库操作异常" + sqle.toString());
					return ExecuteResult.fail;
				} finally {
					if (stmt != null)
						stmt.close();
				}
			} catch (Exception e) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.UNKNOWN, e.toString(), this
								.getId(), soa_processid, new Date(), null));
				log.fatal("删除服务参数的回退中出现未知异常" + e.toString());
				return ExecuteResult.fail;
			}
		}
		return ExecuteResult.sucess;
	}
}
