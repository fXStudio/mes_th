package com.qm.mes.framework.services.service;

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
 * 服务：删除服务定义信息 输入依赖：需要在otherparameter中放置一个Connection对象，名为con。<br>
 * 需要在用户输入或服务输出中含有serviceid. <br>
 * 服务执行 doService() <br>
 * 服务初始化 initFordo() <br>
 * 服务回退 undoService() 如服务删除操作出现不通则回滚<br>
 * 输出：无输出
 * 
 * @author 于丽达 2007-12-20
 */
public class Service_DeleteService extends DefService implements IService {

	// 数据库连接
	private Connection con = null;

	// 服务号
	private String serviceid = null;

	public Service_DeleteService() {
		super();
	}

	private final Log log = LogFactory.getLog(Service_DeleteService.class);

	public ExecuteResult doService(IMessage message, String soa_processid) {
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();

		// 初始化服务运行参数，如失败，返回执行结果
		if (!initFordo(message, soa_processid)) {
			log.error(processInfo + " , 删除服务时,初始化服务运行失败!");
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
					log.error(processInfo + " , 删除服务时,加载数据库错误,未知的数据库类型");
					return ExecuteResult.fail;
				}
				log.debug(processInfo + " , 删除服务,加载数据库成功");
				stmt = con.createStatement();
				int sid = Integer.parseInt(serviceid);
				int count = 0;
				// 查看要删除的服务是否已被某一流程应用，如应用，则不能删除该服务
				String sql_check = dao.getSQL_QueryCountServiceIsUsed(sid);
			
				log.debug("查看要删除的服务是否已被某一流程应用，如应用，则不能删除该服务的sql语句: sql_check = "
						+ sql_check);
				rs = stmt.executeQuery(sql_check);
				if (rs.next()) {
					count = rs.getInt(1);
				}
				if (count > 0)// 服务已被流程使用
				{
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWN, " 该服务已被某流程使用，不能删除!",
							this.getId(), soa_processid, new Date(), null));
					log.error(processInfo
							+ " , 删除服务时,该服务已被某流程使用,不能删除! count = " + count);
					return ExecuteResult.fail;
				}
				// 查询此服务号的所有信息,放入到Map中
				Map<String, String> data = new HashMap<String, String>();
				int map_serviceid = 0;
				String map_cservername = null;
				String map_cclassname = null;
				String map_cdescription = null;
				int map_nnamespaceid = 0;
				String sql_selectService = dao.getSQL_QueryAllServices(String
						.valueOf(sid), "ById");
				log.debug("查询此服务号的所有信息,放入到Map中的sql语句: sql_selectService = "
						+ sql_selectService);
				result = stmt.executeQuery(sql_selectService);
				while (result.next()) {
					map_serviceid = result.getInt(1);
					map_cservername = result.getString(2);
					map_cclassname = result.getString(3);
					map_cdescription = result.getString(4);
					map_nnamespaceid = result.getInt(5);
				}
				String debug_map = "result_map_serviceid = " + map_serviceid
						+ "\n" + "result_map_cservername = " + map_cservername
						+ "\n" + "result_map_cclassname = " + map_cclassname
						+ "\n" + "reslut_map_cdescription = "
						+ map_cdescription + "\n"
						+ "result_map_nnamespaceid = " + map_nnamespaceid
						+ "\n";
				log.debug(processInfo + " , " + this.getClass().getName()
						+ " ,回退操作Map中值:    debug_map = " + debug_map);
				data.put("map_serviceid", String.valueOf(map_serviceid));
				data.put("map_cservername", map_cservername);
				data.put("map_cclassname", map_cclassname);
				data.put("map_cdescription", map_cdescription);
				data.put("map_nnamespaceid", String.valueOf(map_nnamespaceid));
				message.setOtherParameter(this.getClass().getName(), data);
				// 删除服务操作
				String sql_del = dao.getSQL_DeleteService(sid);
				log.debug(" 删除服务的sql语句: sql_del = " + sql_del);
				stmt.executeUpdate(sql_del);
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), soa_processid, new Date(), sqle));
				log.fatal(processInfo + " , 删除服务,数据库操作异常" + sqle.toString());
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
			log.fatal(processInfo + " , 删除服务,未知异常" + e.toString());
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

		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();

		String debug_Service_DeleteService = "用户提交的参数: 流程号: serviceid = "
				+ serviceid + "\n";
		log.debug(processInfo + " , 删除服务 " + debug_Service_DeleteService);

		if (serviceid == null || con == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "缺少输入参数", this.getId(),
					soa_processid, new java.util.Date(), null));
			log.error(processInfo + " , 删除服务,缺少输入参数");
			return false;
		}
		return true;
	}

	//
	@SuppressWarnings("unchecked")
	public ExecuteResult undoService(IMessage message, String soa_processid) {
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		// message是Object对象
		Object obj = message.getOtherParameter(this.getClass().getName());
		Statement stmt = null;
		// 如果Map中的key是Object为true
		log.trace(obj);
		if (obj instanceof Map) {
			Map<String, String> map = (Map<String, String>) obj;
			String map_serviceid = (String) map.get("map_serviceid");
			String map_cservername = (String) map.get("map_cservername");
			String map_cclassname = (String) map.get("map_cclassname");
			String map_cdescription = (String) map.get("map_cdescription");
			String map_nnamespaceid = (String) map.get("map_nnamespaceid");
			String debug_map = "map_serviceid = " + map_serviceid + "\n"
					+ "map_cservername = " + map_cservername + "\n"
					+ "map_cclassname = " + map_cclassname + "\n"
					+ "map_cdescription = " + map_cdescription + "\n"
					+ "map_nnamespaceid = " + map_nnamespaceid + "\n";
			log.debug(processInfo + " , " + this.getClass().getName()
					+ " ,删除服务的回退操作Map中值:    debug_map = " + debug_map);
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
					// 将删除的服务项重新添加进来
					String sql_insert = dao.getSQL_InsertService(Integer
							.parseInt(map_serviceid), map_cservername,
							map_cclassname, map_cdescription, map_nnamespaceid);
					log.debug(processInfo + " , " + this.getClass().getName()
							+ " ,回退操作的sql语句:  undo_sql_insert = " + sql_insert);
					stmt = con.createStatement();
					stmt.executeUpdate(sql_insert);
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
		return ExecuteResult.sucess;
	}
}
