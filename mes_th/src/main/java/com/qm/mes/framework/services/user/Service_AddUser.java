package com.qm.mes.framework.services.user;

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

public class Service_AddUser extends DefService implements IService {

	private Connection con = null;

	private String usrno = null;

	private String usrname = null;

	private String password = null;

	private String employeeid = null;

	private String state = null;

	private String note = null;

	private String roleno = null;

	private String default_roleno = null;

	private String lastupdateuser = null;

	private String lastupdatetime = null;

	private String enabled = null;

	private String cssfile = "blue";

	public Service_AddUser() {
		super();
	}

	private final Log log = LogFactory.getLog(Service_AddUser.class);

	public synchronized ExecuteResult doService(IMessage message,
			String soa_processid) {
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		if (!initFordo(message, soa_processid)) {
			log.error(processInfo + ",添加用户,初始化服务运行失败");
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
							ServiceExceptionType.UNKNOWNDATABASETYPE, "dao空",
							this.getId(), soa_processid, new Date(), null));
					log.error(processInfo + ",添加用户,加载数据库错误,未知的数据库类型");
					return ExecuteResult.fail;
				}
				log.debug(processInfo + ",添加用户,加载数据库成功");
				stmt = con.createStatement();

				// 取用户id
				String sql_maxno = dao.getSQL_QueryMaxusrno();
				log.debug("查找最大的usrno的sql语句: sql_maxno = " + sql_maxno);
				rs = stmt.executeQuery(sql_maxno);
				if (rs.next()) {
					usrno = String.valueOf(rs.getInt(1) + 1);
				} else {
					usrno = "0";
				}

				// 检验用户id是否重复
				String sql_check = dao.getSQL_QueryCountForUserNo(usrno);
				log.debug("检验用户id是否重复的sql语句: sql_check = " + sql_check);
				rs = stmt.executeQuery(sql_check);
				int count = 0;
				if (rs.next()) {
					count = rs.getInt(1);
				}
				if (count > 0) // 用户id重复
				{
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWN, "用户id重复！", this
									.getId(), soa_processid, new Date(), null));
					log.error(processInfo + ",添加用户,用户id重复 count = " + count);
					return ExecuteResult.fail;
				}

				// 检验用户名是否重复
				sql_check = dao.getSQL_QueryCountForUserName(usrname);
				log.debug("检验用户名是否重复的sql语句: sql_check = " + sql_check);
				rs = stmt.executeQuery(sql_check);
				count = 0;
				if (rs.next()) {
					count = rs.getInt(1);
				}
				if (count > 0) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWN, "用户名重复！", this
									.getId(), soa_processid, new Date(), null));
					log.error(processInfo + ",添加用户,用户名重复 count = " + count);
					return ExecuteResult.fail;
				}
				/*
				 * 添加用户回退操作所需的参数
				 */
				Map<String, String> data = new HashMap<String, String>();
				data.put("map_id", usrno);
				message.setOtherParameter(this.getClass().getName(), data);
				/*
				 * 添加用户
				 */
				String sql_insert = dao.getSQL_InsertUser(usrno, usrname,
						password, "", employeeid, state, lastupdateuser,
						lastupdatetime, note, enabled);
				log.debug("插入用户信息表的sql语句: sql_insert = " + sql_insert);
				String sql_insertCss = dao.getSQL_insertCss(usrno, cssfile);
				log.debug("插入css样式表的sql语句: sql_insertCss = " + sql_insertCss);
				try {
					con.setAutoCommit(false);
					stmt.executeUpdate(sql_insert);
					stmt.executeUpdate(sql_insertCss);
					/*
					 * 此处为一个用户-角色多对多的修改处
					 */
					String[] str_role = roleno.split(":");
					String sql_insertDataUserRole = "";
					for (String one_role : str_role) {
						if (one_role != null && !one_role.equals("")) {
							if (default_roleno.equals(one_role)) {
								sql_insertDataUserRole = dao
										.getSQL_insertDataUserRole(new Integer(
												usrno), new Integer(one_role),
												"0");
							} else {
								sql_insertDataUserRole = dao
										.getSQL_insertDataUserRole(new Integer(
												usrno), new Integer(one_role),
												"1");
							}
							log
									.debug("插入data_user_role表的sql语句: sql_insertDataUserRole = "
											+ sql_insertDataUserRole);
							stmt.executeUpdate(sql_insertDataUserRole);
						}
					}
					con.commit();
					con.setAutoCommit(true);
				} catch (SQLException Ecommit) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.DATABASEERROR, "提交错误"
									+ Ecommit, this.getId(), soa_processid,
							new Date(), null));
					log.fatal(processInfo + ",添加用户提交错误" + Ecommit.toString());
                    
					try {
						con.rollback();
					} catch (SQLException Erollback) {
						message.addServiceException(new ServiceException(
								ServiceExceptionType.DATABASEERROR, "回滚错误"
										+ Ecommit, this.getId(), soa_processid,
								new Date(), null));
						log.fatal(processInfo + ",添加用户回滚错误"
								+ Erollback.toString());
						return ExecuteResult.fail;
					}finally {
						if (stmt != null)
							stmt.close();
					}

					return ExecuteResult.fail;
				}

			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库错误" + sqle,
						this.getId(), soa_processid, new Date(), sqle));
				log.fatal(processInfo + ",添加用户,数据库操作异常" + sqle.toString());
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
			log.fatal(processInfo + ",添加用户,未知异常" + e.toString());

			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

	@SuppressWarnings("unchecked")
	public ExecuteResult undoService(IMessage message, String soa_processid) {
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		Object obj = message.getOtherParameter(this.getClass().getName());
		if (obj instanceof Map) {
			Map<String, String> map = (Map<String, String>) obj;
			String map_id = map.get("map_id");
			log.debug(processInfo + "添加用户回退操作Map接收的值: map_id = " + map_id
					+ "\n");
			try {
				Statement stmt = null;
				try {
					IDAO_UserManager dao = DAOFactory_UserManager
							.getInstance(DataBaseType.getDataBaseType(con));
					if (dao == null) {
						message.addServiceException(new ServiceException(
								ServiceExceptionType.UNKNOWNDATABASETYPE,
								"dao空", this.getId(), soa_processid,
								new Date(), null));
						log.error(processInfo + ",添加用户,加载数据库错误,未知的数据库类型");
						return ExecuteResult.fail;
					}
					log.debug(processInfo + ",添加用户,加载数据库成功");
					stmt = con.createStatement();
					String map_sql_delete = dao.getSQL_DeleteUser(map_id);
					log.debug("添加用户回退操作的sql语句: map_sql_delete = "
							+ map_sql_delete);
					String map_sql_deleteCss = dao.getSQL_DeleteCss(map_id);
					log.debug("添加用户回退操作CSS样式的sql语句: map_sql_delete = "
							+ map_sql_deleteCss);

					try {
						con.setAutoCommit(false);
						stmt.executeUpdate(map_sql_delete);
						stmt.executeUpdate(map_sql_delete);
						/*
						 * 此处为一个用户-角色多对多的修改处
						 */
						String sql_deleteDataUserRole = dao
								.getSQL_DeleteDataUserRole(new Integer(map_id));
						log
								.debug("插入data_user_role表回退操作的sql语句: sql_deleteDataUserRole = "
										+ sql_deleteDataUserRole);
						stmt.executeUpdate(sql_deleteDataUserRole);
						con.commit();
						con.setAutoCommit(true);
					} catch (SQLException Ecommit) {
						message.addServiceException(new ServiceException(
								ServiceExceptionType.DATABASEERROR, "提交错误"
										+ Ecommit, this.getId(), soa_processid,
								new Date(), null));
						log.fatal(processInfo + ",添加用户回退操作提交错误"
								+ Ecommit.toString());

						try {
							con.rollback();
						} catch (SQLException Erollback) {
							message.addServiceException(new ServiceException(
									ServiceExceptionType.DATABASEERROR, "回滚错误"
											+ Ecommit, this.getId(),
									soa_processid, new Date(), null));
							log.fatal(processInfo + ",添加用户回退操作回滚错误"
									+ Erollback.toString());
							return ExecuteResult.fail;
						}
						finally {
							if (stmt != null)
								stmt.close();
						}
						return ExecuteResult.fail;
					}
				} catch (SQLException sqle) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.DATABASEERROR, "数据库错误" + sqle,
							this.getId(), soa_processid, new Date(), sqle));
					log.fatal(processInfo + ",添加用户回退操作,数据库操作异常"
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
				log.fatal(processInfo + ",添加用户回退操作,未知异常" + e.toString());

				return ExecuteResult.fail;
			}
		}
		return ExecuteResult.sucess;
	}

	private boolean initFordo(IMessage message, String soa_processid) {

		con = null;
		usrno = null;
		usrname = null;
		password = null;
		roleno = null;
		employeeid = null;
		state = null;
		lastupdateuser = null;
		lastupdatetime = null;
		note = null;
		enabled = null;

		con = (Connection) message.getOtherParameter("con");
		usrno = message.getUserParameterValue("usrno");
		usrname = message.getUserParameterValue("usrname");
		password = message.getUserParameterValue("password");
		roleno = message.getUserParameterValue("roleno");
		default_roleno = message.getUserParameterValue("default_roleno");
		employeeid = message.getUserParameterValue("employeeid");
		state = message.getUserParameterValue("state");
		lastupdateuser = message.getUserParameterValue("lastupdateuser");
		lastupdatetime = message.getUserParameterValue("lastupdatetime");
		note = message.getUserParameterValue("note");
		enabled = message.getUserParameterValue("enabled");
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		String debug_AddUser = "usrno = " + usrno + "\n" + "usrname = "
				+ usrname + "\n" + "password = " + password + "\n"
				+ "roleno = " + roleno + "\n" + "default_roleno = "
				+ default_roleno + "\n" + "employeeid = " + employeeid + "\n"
				+ "state = " + state + "\n" + "lastupdateuser = "
				+ lastupdateuser + "\n" + "lastupdatetime = " + lastupdatetime
				+ "\n" + "note = " + note + "\n" + "enabled = " + enabled
				+ "\n";
		log.debug(processInfo + ",添加用户时用户提交的参数: " + debug_AddUser);

		if (usrno == null || usrname == null || password == null
				|| roleno == null || default_roleno == null
				|| employeeid == null || state == null
				|| lastupdateuser == null || lastupdatetime == null
				|| note == null || enabled == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST,
					"service_AddUser添加用户时参数为空", this.getId(), soa_processid,
					new java.util.Date(), null));
			log.error(processInfo + ",添加用户,缺少输入参数");
			return false;
		}
		return true;
	}
}
