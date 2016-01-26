package com.qm.mes.framework.services.serviceParas;

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
 * 服务：添加服务参数 输入依赖：需要在otherparameter中放置一个Connection对象，名为con。<br>
 * 需要在用户输入或服务输出中含有serviceid,paraname,paratype,paradesc值，即使是空字符串。 <br>
 * 服务执行 doService() <br>
 * 服务初始化 initFordo() <br>
 * 服务回退 undoService()<br>
 * 输出：无输出
 * 
 * @author 于丽达 2007-12-25
 */
public class Service_InsertServiceParas extends DefService implements IService {

	// 数据库连接
	private Connection con = null;

	// 服务号
	private String serviceid = null;

	// 参数名
	private String paraname = null;

	// 参数类型
	private String paratype = null;

	// 参数描述
	private String paradesc = null;

	public Service_InsertServiceParas() {
		super();
	}

	private final Log log = LogFactory.getLog(Service_InsertServiceParas.class);

	public synchronized ExecuteResult doService(IMessage message,
			String soa_processid) {
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		// 初始化服务运行参数，如失败，返回执行结果
		if (!initFordo(message, soa_processid)) {
			log.error(processInfo + ",添加服务参数,初始化服务运行失败");
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
					log.error(processInfo + ",添加服务参数,加载数据库错误,未知的数据库类型");
					return ExecuteResult.fail;
				}
				log.debug(processInfo + ",添加服务参数,加载数据库成功");
				stmt = con.createStatement();
				int sid = Integer.parseInt(serviceid);
				// 验证参数是否唯一
				String sql_check = dao.getSQL_QueryCountServiceParaIsUnique(
						sid, paraname, paratype);
				log.debug("验证参数是否唯一的sql语句: sql_check = " + sql_check);
				rs = stmt.executeQuery(sql_check);
				int count = 0;
				if (rs.next()) {
					count = rs.getInt(1);
				}
				if (count > 0)// 同一服务的同类型的参数不唯一
				{
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWN, "该参数已存在!", this
									.getId(), soa_processid, new Date(), null));
					log.error(processInfo + ",验证参数,同一服务的同类型的参数不唯一 count = "
							+ count);
					return ExecuteResult.fail;
				}
				// 将服务号,参数名,参数类型放入Map中
				Map<String, String> data = new HashMap<String, String>();
				data.put("map_serviceid", String.valueOf(sid));
				data.put("map_paraname", paraname);
				data.put("map_paratype", paratype);
				message.setOtherParameter(this.getClass().getName(), data);
				// 添加参数
				String sql_insert = dao.getSQL_InsertServicePara(sid, paraname,
						paratype, paradesc);
				log.debug("添加参数的sql语句: sql_insert = " + sql_insert);
				stmt.executeUpdate(sql_insert);

			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), soa_processid, new Date(), sqle));
				log.fatal(processInfo + ",添加服务参数时,数据库操作异常" + sqle.toString());
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
			log.fatal(processInfo + ",添加服务参数时,出现未知异常" + e.toString());
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

	private boolean initFordo(IMessage message, String soa_processid) {
		// 数据库连接
		con = null;
		// 服务号
		serviceid = null;
		// 参数名
		paraname = null;
		// 参数类型
		paratype = null;
		// 参数描述
		paradesc = null;

		// 获取该服务的输入参数的值
		con = (Connection) message.getOtherParameter("con");
		serviceid = message.getUserParameterValue("serviceid");
		paraname = message.getUserParameterValue("paraname");
		paratype = message.getUserParameterValue("paratype");
		paradesc = message.getUserParameterValue("paradesc");

		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();

		String debug_InsertServicePara = "用户提交的参数: 服务号: serviceid = "
				+ serviceid + "\n" + "参数名: paraname = " + paraname + "\n"
				+ "参数类型: paratype = " + paratype + "\n" + "参数描述: paradesc = "
				+ paradesc + "\n";
		log.debug(processInfo + ",添加服务参数 " + debug_InsertServicePara);

		if (serviceid == null || paraname == null || paratype == null
				|| paradesc == null || con == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "缺少输入参数", this.getId(),
					soa_processid, new java.util.Date(), null));
			log.error(processInfo + ",添加服务参数,缺少输入参数");
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
			String debug_map = "map_serviceid = " + map_serviceid + "\n"
					+ "map_paraname = " + map_paraname + "\n"
					+ "map_paratype = " + map_paratype;
			log.debug(processInfo + "添加服务参数的undoService方法: "
					+ this.getClass().getName() + "  debug_map = " + debug_map);
			try {
				Statement stmt = null;
				try {
					IDAO_Core dao = DAOFactory_Core.getInstance(DataBaseType
							.getDataBaseType(con));
					if (dao == null) {
						message.addServiceException(new ServiceException(
								ServiceExceptionType.UNKNOWNDATABASETYPE,
								"未知数据库类型", this.getId(), soa_processid,
								new Date(), null));
						log.error(processInfo + ",回退操作加载数据库错误,未知的数据库类型");
						return ExecuteResult.fail;
					}
					log.debug(processInfo + ",回退操作加载数据库成功");
					String sql_delete = dao.getSQL_DeleteServicePara(
							map_serviceid, map_paratype, map_paraname);
					log.debug(this.getClass().getName()
							+ "添加服务参数的回退操作的sql语句: undo_sql_delete = "
							+ sql_delete);
					stmt = con.createStatement();
					stmt.executeUpdate(sql_delete);
				} catch (SQLException sqle) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
									.getId(), soa_processid, new Date(), sqle));
					log.fatal(processInfo + ",添加服务参数回退操作时,数据库操作异常"
							+ sqle.toString());
					return ExecuteResult.fail;
				} finally {
					if (stmt != null)
						stmt.close();
				}
			} catch (Exception e) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.UNKNOWN, e.toString(), this
								.getId(), soa_processid, new Date(), null));
				log.fatal(processInfo + "添加服务参数回退操作时,出现未知异常" + e.toString());
				return ExecuteResult.fail;
			}
		}
		return ExecuteResult.sucess;
	}
}
