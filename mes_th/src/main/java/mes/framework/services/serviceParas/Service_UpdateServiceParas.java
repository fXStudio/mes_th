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
 * 服务：更新服务参数 输入依赖：需要在otherparameter中放置一个Connection对象，名为con。<br>
 * 需要在用户输入或服务输出中含有serviceid,paraname,setparaname,paratype,setparatype,paradesc值，即使是空字符串。
 * <br>
 * 服务执行 doService() <br>
 * 服务初始化 initFordo() <br>
 * 服务回退 undoService() <br>
 * 输出：无输出
 * 
 * @author 于丽达0 2007-12-25
 */
public class Service_UpdateServiceParas extends DefService implements IService {

	// 数据库连接
	private Connection con = null;

	// 服务号
	private String serviceid = null;

	// 原参数名
	private String setparaname = null;

	// 原参数类型
	private String setparatype = null;

	// 更新后的参数名
	private String paraname = null;

	// 更新后的参数类型
	private String paratype = null;

	// 参数描述
	private String paradesc = null;

	public Service_UpdateServiceParas() {
		super();
	}

	private final Log log = LogFactory.getLog(Service_UpdateServiceParas.class);

	public ExecuteResult doService(IMessage message, String soa_processid) {
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();

		// 初始化服务运行参数，如失败，返回执行结果
		if (!initFordo(message, soa_processid)) {
			log.error(processInfo + ",修改服务参数时,初始化服务运行失败");
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
					log.error(processInfo + ",修改服务参数时,加载数据库错误,未知的数据库类型");
					return ExecuteResult.fail;
				}
				log.debug(processInfo + ",修改服务参数,加载数据库成功");
				stmt = con.createStatement();
				int sid = Integer.parseInt(serviceid);
				if (!paraname.equals(setparaname)) {
					String sql_check = dao
							.getSQL_QueryCountServiceParaIsUnique(sid,
									paraname, paratype);
					log.debug("验证同一服务同类型的参数是否唯一的sql语句: sql_check = "
							+ sql_check);
					rs = stmt.executeQuery(sql_check);
					int count = 0;
					if (rs.next()) {
						count = rs.getInt(1);
					}
					if (count > 0)// 同一服务同类型的参数不唯一
					{
						message.addServiceException(new ServiceException(
								ServiceExceptionType.UNKNOWN, "该参数已存在!", this
										.getId(), soa_processid, new Date(),
								null));
						log.error(processInfo + ",修改服务参数时,该参数已存在,count = "
								+ count);
						return ExecuteResult.fail;
					}
				}
				/*
				 * 回退操作所需参数
				 */
				Map<String, String> data = new HashMap<String, String>();
				String map_paraname = null;
				String map_paratype = null;
				String map_paradesc = null;
				String sql_selectPara = dao.getSQL_QueryServicePara(sid,
						setparatype, setparaname);
				log.debug(processInfo + "查询此服务号的原有参数,放入到Map中的sql语句:"
						+ sql_selectPara);
				result = stmt.executeQuery(sql_selectPara);
				while (result.next()) {
					map_paraname = result.getString(1);
					map_paratype = result.getString(2);
					map_paradesc = result.getString(3);
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
				data.put("map_setparatype", paratype);
				data.put("map_setparaname", paraname);
				message.setOtherParameter(this.getClass().getName(), data);
				/*
				 * 更新参数
				 */
				String sql_update = dao.getSQL_UpdateServicePara(sid, paratype,
						setparatype, paraname, setparaname, paradesc);
				log.debug("更新参数的sql语句: sql_update = " + sql_update);
				stmt.executeUpdate(sql_update);
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), soa_processid, new Date(), sqle));
				log.fatal(processInfo + ",修改服务参数,数据库操作异常" + sqle.toString());
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
			log.fatal(processInfo + "修改服务参数,未知异常" + e.toString());
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

	private boolean initFordo(IMessage message, String soa_processid) {
		// 数据库连接
		con = null;
		// 服务号
		serviceid = null;
		// 目标参数名
		paraname = null;
		// 更新后的参数名
		setparaname = null;
		// 更新后的参数类型
		paratype = null;
		// 目标参数类型
		setparatype = null;
		// 参数描述
		paradesc = null;

		// 获取该服务的输入参数的值
		con = (Connection) message.getOtherParameter("con");
		serviceid = message.getUserParameterValue("serviceid");
		paraname = message.getUserParameterValue("paraname");
		setparaname = message.getUserParameterValue("setparaname");
		paratype = message.getUserParameterValue("paratype");
		setparatype = message.getUserParameterValue("setparatype");
		paradesc = message.getUserParameterValue("paradesc");
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();

		String debug_UpdateServicePara = "服务号: serviceid = " + serviceid + "\n"
				+ "目标参数名: paraname = " + paraname + "\n"
				+ "更新后的参数名：setparaname = " + setparaname + "\n"
				+ "更新后的参数类型：paratype = " + paratype + "\n"
				+ "目标参数类型：setparatype = " + setparatype + "\n"
				+ "参数描述：paradesc = " + paradesc + "\n";
		log.debug(processInfo + ",修改服务参数时用户提交的参数: " + debug_UpdateServicePara);

		if (serviceid == null || paraname == null || paratype == null
				|| paradesc == null || setparatype == null
				|| setparaname == null || con == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "缺少输入参数", this.getId(),
					soa_processid, new java.util.Date(), null));
			log.error(processInfo + ",修改服务参数时,缺少输入参数");
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
			String map_paratype = map.get("map_paratype");
			String map_paraname = map.get("map_paraname");
			String map_paradesc = map.get("map_paradesc");
			String map_setparatype = map.get("map_setparatype");
			String map_setparaname = map.get("map_setparaname");
			String debug_map = "map_serviceid = " + map_serviceid + "\n"
					+ "map_paratype = " + map_paratype + "\n"
					+ "map_paraname = " + map_paraname + "\n"
					+ "map_paradesc = " + map_paradesc + "\n"
					+ "map_setparatype = " + map_setparatype + "\n"
					+ "map_setparaname = " + map_setparaname;
			log.debug(processInfo + " , " + this.getClass().getName()
					+ "修改服务参数的回退操作接收的参数: debug_map = " + debug_map);
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
						log.error(processInfo + ",修改服务参数的回退操作加载数据库时,未知的数据库类型");
						return ExecuteResult.fail;
					}
					log.debug(processInfo + "修改服务参数的回退操作加载数据库成功");
					String undo_sql_update = dao.getSQL_UpdateServicePara(
							map_serviceid, map_paratype, map_setparatype,
							map_paraname, map_setparaname, map_paradesc);
					log.debug(processInfo
							+ "修改服务参数回退操作的sql语句: undo_sql_update = "
							+ undo_sql_update);
					stmt = con.createStatement();
					stmt.executeUpdate(undo_sql_update);
				} catch (SQLException sqle) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
									.getId(), soa_processid, new Date(), sqle));
					log.fatal("修改服务参数的回退操作中数据库操作异常" + sqle.toString());
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
