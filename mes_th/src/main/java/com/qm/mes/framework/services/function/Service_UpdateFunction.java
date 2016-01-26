package com.qm.mes.framework.services.function;

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
import com.qm.mes.framework.dao.DAOFactory_UserManager;
import com.qm.mes.framework.dao.IDAO_UserManager;

/**
 * 服务：更新功能 输入依赖：需要在otherparameter中放置一个Connection对象，名为con。<br>
 * 需要在用户输入或服务输出中含有functionid,functionname,url,state,note,safemarkcode,nodetype,rank,userid
 * 值，即使是空字符串。 <br>
 * 执行 doService() <br>
 * 初始化 initFordo() <br>
 * 回退 undoService() <br>
 * 输出：无输出
 * 
 * @author 于丽达 2008-01-02
 */
public class Service_UpdateFunction extends DefService implements IService {

	// 数据库连接
	private Connection con = null;

	// 功能号
	private String functionid = null;

	// 功能名称
	private String functionname = null;

	// 文件url
	private String url = null;

	// 状态
	private String state = null;

	// 备注
	private String note = null;

	// 访问安全标记
	private String safemarkcode = null;

	// 节点类型
	private String nodetype = null;

	// 级别
	private String rank = null;

	// 用户ID
	private String userid = null;

	// 功能顺序号
	private String flo_Order = null;

	// 父功能号
	private String upfunctionid = null;

	public Service_UpdateFunction() {
		super();
	}

	private final Log log = LogFactory.getLog(Service_UpdateFunction.class);

	public ExecuteResult doService(IMessage message, String soa_processid) {
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		// 初始化服务运行参数，如失败，返回执行结果
		if (!initFordo(message, soa_processid)) {
			log.error(processInfo + ",修改功能,初始化服务运行失败");
			return ExecuteResult.fail;
		}

		try {
			Statement stmt = null;
			ResultSet rs = null;
			try {
				IDAO_UserManager dao = DAOFactory_UserManager
						.getInstance(DataBaseType.getDataBaseType(con));
				if (dao == null) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWNDATABASETYPE,
							"未知的数据库类型", this.getId(), soa_processid,
							new Date(), null));
					log.error(processInfo + ",修改功能,加载数据库错误,未知的数据库类型");
					return ExecuteResult.fail;
				}
				log.debug(processInfo + ",修改功能,加载数据库成功");
				stmt = con.createStatement();
				String sql_update = "";
				int fid = Integer.parseInt(functionid);
				int uid = Integer.parseInt(userid);
				if (nodetype.equals("3")) {// 更新功能(当节点为叶节点时)
					sql_update = dao.getSQL_UpdateFunctionWhenLeaf(fid,
							functionname, url, state, note, safemarkcode, uid,
							rank, Float.valueOf(flo_Order),Integer.parseInt(upfunctionid));
					log.debug("更新功能(当节点为叶节点时)的sql语句: sql_update = "
							+ sql_update);
				} else {
					// 更新功能(当节点为非叶节点时)
					sql_update = dao.getSQL_UpdateFunctionWhenNotLeaf(fid,
							functionname, note, uid, rank, Float
									.valueOf(flo_Order),new Integer(upfunctionid));
					log.debug("更新功能(当节点为非叶节点时)的sql语句: sql_update = "
							+ sql_update);
				}

				/*
				 * 查询此功能号的所有信息放入Map中
				 */
				Map<String, String> data = new HashMap<String, String>();
				String map_functionid = null;
				String map_functionname = null;
				String map_url = null;
				String map_rank = null;
				String map_nodetype = null;
				String map_state = null;
				String map_safemarkcode = null;
				String map_note = null;
				String map_userid = null;
				String map_flo_Order = null;
				String map_upfunctionid = null;
				String map_sql_select = dao
						.getSQL_QueryFuncitonInfoForFunctionID(this.functionid);
				log.debug(processInfo
						+ ",修改功能回退操作所需的所有参数的sql语句: map_sql_select = "
						+ map_sql_select);
				rs = stmt.executeQuery(map_sql_select);
				while (rs.next()) {
					map_functionid = rs.getString(1);
					map_functionname = rs.getString(2);
					map_url = rs.getString(3);
					map_rank = rs.getString(5);
					map_nodetype = rs.getString(6);
					map_state = rs.getString(7);
					map_safemarkcode = rs.getString(8);
					map_note = rs.getString(9);
					map_userid = rs.getString(11);
					map_flo_Order = rs.getString(12);
					map_upfunctionid = rs.getString(4);
				}
				String map_debug = "map_functionid = " + map_functionid + "\n"
						+ "map_functionname = " + map_functionname + "\n"
						+ "map_url = " + map_url + "\n" + "map_rank = "
						+ map_rank + "\n" + "map_nodetype = " + map_nodetype
						+ "\n" + "map_state = " + map_state + "\n"
						+ "map_safemarkcode = " + map_safemarkcode + "\n"
						+ "map_note = " + map_note + "\n" + "map_userid = "
						+ map_userid + "\n" + "map_flo_Order = "
						+ map_flo_Order;
				log.debug(processInfo + ",修改功能回退操作查询出的所有参数为: map_debug = "
						+ map_debug);
				data.put("map_functionid", map_functionid);
				data.put("map_functionname", map_functionname);
				data.put("map_url", map_url);
				data.put("map_rank", map_rank);
				data.put("map_nodetype", map_nodetype);
				data.put("map_state", map_state);
				data.put("map_safemarkcode", map_safemarkcode);
				data.put("map_note", map_note);
				data.put("map_userid", map_userid);
				data.put("map_flo_Order", map_flo_Order);
				data.put("map_upfunctionid", map_upfunctionid);
				message.setOtherParameter(this.getClass().getName(), data);

				stmt.executeUpdate(sql_update);

			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), soa_processid, new Date(), sqle));
				log.fatal(processInfo + ",修改功能,数据库操作异常" + sqle.toString());
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
			log.fatal(processInfo + ",修改功能,未知异常" + e.toString());
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
			String map_functionid = map.get("map_functionid");
			String map_functionname = map.get("map_functionname");
			String map_url = map.get("map_url");
			String map_rank = map.get("map_rank");
			String map_nodetype = map.get("map_nodetype");
			String map_state = map.get("map_state");
			String map_safemarkcode = map.get("map_safemarkcode");
			String map_note = map.get("map_note");
			String map_userid = map.get("map_userid");
			String map_flo_Order = map.get("map_flo_Order");
			String map_upfunctionid = map.get("map_upfunctionid");
			String debug_map = "map_functionid = " + map_functionid + "\n"
					+ "map_functionname = " + map_functionname + "\n"
					+ "map_url = " + map_url + "\n" + "map_rank = " + map_rank
					+ "\n" + "map_nodetype = " + map_nodetype + "\n"
					+ "map_state = " + map_state + "\n" + "map_safemarkcode = "
					+ map_safemarkcode + "\n" + "map_note = " + map_note + "\n"
					+ "map_userid = " + map_userid;
			log.debug(processInfo + ",修改功能回退操作查询出的所有参数为: map_debug = "
					+ debug_map);
			try {
				Statement stmt = null;
				try {
					IDAO_UserManager dao = DAOFactory_UserManager
							.getInstance(DataBaseType.getDataBaseType(con));
					if (dao == null) {
						message.addServiceException(new ServiceException(
								ServiceExceptionType.UNKNOWNDATABASETYPE,
								"未知的数据库类型", this.getId(), soa_processid,
								new Date(), null));
						log.error(processInfo + ",修改功能回退操作,加载数据库错误,未知的数据库类型");
						return ExecuteResult.fail;
					}
					log.debug(processInfo + ",修改功能回退操作,加载数据库成功");
					stmt = con.createStatement();
					String sql_update = "";
					if (nodetype.equals("3")) {// 更新功能(当节点为叶节点时)
						sql_update = dao.getSQL_UpdateFunctionWhenLeaf(Integer
								.parseInt(map_functionid), map_functionname,
								map_url, map_state, map_note, map_safemarkcode,
								Integer.parseInt(map_userid), rank, Float
										.valueOf(map_flo_Order),Integer.valueOf(map_upfunctionid));
						log
								.debug("更新功能回退操作(当节点为叶节点时)的sql语句: undo_sql_update = "
										+ sql_update);
					} else {
						// 更新功能(当节点为非叶节点时)
						sql_update = dao.getSQL_UpdateFunctionWhenNotLeaf(
								Integer.parseInt(map_functionid),
								map_functionname, map_note, Integer
										.parseInt(map_userid), map_rank, Float
										.valueOf(map_flo_Order),Integer.valueOf(map_upfunctionid));
						log
								.debug("更新功能回退操作(当节点为非叶节点时)的sql语句: undo_sql_update = "
										+ sql_update);
					}
					stmt.executeUpdate(sql_update);
				} catch (SQLException sqle) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
									.getId(), soa_processid, new Date(), sqle));
					log.fatal(processInfo + ",修改功能回退操作,数据库操作异常"
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
				log.fatal(processInfo + ",修改功能回退操作,未知异常" + e.toString());
				return ExecuteResult.fail;
			}
		}
		return ExecuteResult.sucess;
	}

	private boolean initFordo(IMessage message, String soa_processid) {
		// 数据库连接
		con = null;
		// 功能号
		functionid = null;
		// 功能名称
		functionname = null;
		// 文件url
		url = null;
		// 状态
		state = null;
		// 备注
		note = null;
		// 访问安全标记
		safemarkcode = null;
		// 节点类型
		nodetype = null;
		// 级别
		rank = null;
		// 用户ID
		userid = null;
		// 功能顺序
		flo_Order = null;
		// 父功能号
		upfunctionid = null;

		// 获取该服务的输入参数的值
		con = (Connection) message.getOtherParameter("con");
		functionid = message.getUserParameterValue("functionid");
		functionname = message.getUserParameterValue("functionname");
		url = message.getUserParameterValue("url");
		state = message.getUserParameterValue("state");
		note = message.getUserParameterValue("note");
		safemarkcode = message.getUserParameterValue("safemarkcode");
		nodetype = message.getUserParameterValue("nodetype");
		rank = message.getUserParameterValue("rank");
		userid = message.getUserParameterValue("userid");
		flo_Order = message.getUserParameterValue("flo_Order");
		upfunctionid = message.getUserParameterValue("upnodeid");
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		String debug_Service_UpdateFunction = "功能号: functionid = " + functionid
				+ "\n" + "功能名称: functionname = " + functionname + "\n"
				+ "文件url: url = " + url + "\n" + "状态: state = " + state + "\n"
				+ "备注: state = " + state + "\n" + "访问安全标记: safemarkcode = "
				+ safemarkcode + "\n" + "节点类型: nodetype = " + nodetype + "\n"
				+ "级别: rank = " + rank + "\n" + "用户ID: userid = " + userid
				+ "\n" + "功能顺序: flo_Order = " + flo_Order + "\n"
				+ "父功能号: upfunctionid = " + upfunctionid + "\n";
		log.debug(processInfo + ",修改功能时用户提交的参数: "
				+ debug_Service_UpdateFunction);

		if (functionid == null || functionname == null || url == null
				|| state == null || note == null || safemarkcode == null
				|| nodetype == null || rank == null || userid == null
				|| upfunctionid == null || con == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "缺少输入参数", this.getId(),
					soa_processid, new java.util.Date(), null));
			log.error(processInfo + ",修改功能,缺少输入参数");
			return false;
		}
		return true;
	}
}
