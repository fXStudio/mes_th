package mes.framework.services.user;

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
import mes.framework.dao.DAOFactory_UserManager;
import mes.framework.dao.IDAO_UserManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Service_DeleteUser extends DefService implements IService {

	private Connection con = null;

	private String id = null;

	public Service_DeleteUser() {
		super();
	}

	private final Log log = LogFactory.getLog(Service_DeleteUser.class);

	public ExecuteResult doService(IMessage message, String soa_processid) {
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		if (!initFordo(message, soa_processid)) {
			log.error(processInfo + ",删除用户,初始化服务运行失败");
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
							ServiceExceptionType.UNKNOWNDATABASETYPE, "", this
									.getId(), soa_processid, new Date(), null));
					log.error(processInfo + ",删除用户,加载数据库错误,未知的数据库类型");
					return ExecuteResult.fail;
				}
				log.debug(processInfo + ",删除用户,加载数据库成功");
				stmt = con.createStatement();
				String sql_check = dao.getSQL_QueryCountForUserNo(id);
				log.debug("取得用户id是否重复的sql语句: sql_check = " + sql_check);
				rs = stmt.executeQuery(sql_check);
				int count = 0;
				if (rs.next()) {
					count = rs.getInt(1);
				}
				if (count == 0) // 数据库中已经不存在该数据
				{
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWN, "数据库中已经不存在该数据！", this
									.getId(), soa_processid, new Date(), null));
					log.error(processInfo + ",删除用户时,数据库中已经不存在该数据  count = "
							+ count);
					return ExecuteResult.fail;
				}
				/*
				 * 查询所有参数放入Map中
				 */
				Map<String, String> data = new HashMap<String, String>();
				String rs_usrno = null;
				String rs_usrname = null;
				String rs_password = null;
				String rs_roleno = "";
				String rs_cdefault ="";
				String rs_employeeid = null;
				String rs_state = null;
				String rs_lastupdateuser = null;
				String rs_lastupdatetime = null;
				String rs_note = null;
				String rs_enabled = null;
				String rs_cssfile = "blue";
				String map_sql_select = dao.getSQL_QueryUserInfoForUserID(id);
				log.debug(processInfo + "查询此用户所有参数的sql语句: map_sql_select = "
						+ map_sql_select);
				rs = stmt.executeQuery(map_sql_select);
				while (rs.next()) {
					rs_usrno = rs.getString(1);
					rs_usrname = rs.getString(2);
					rs_password = rs.getString(3);
					rs_employeeid = rs.getString(5);
					rs_state = rs.getString(6);
					rs_lastupdateuser = rs.getString(7);
					rs_lastupdatetime = rs.getString(8);
					rs_note = rs.getString(9);
					rs_enabled = rs.getString(10);
				}
				String map_sql_selectCss = dao.getSQL_QueryCssFileForUserID(id);
				log.debug(processInfo
						+ "查询此用户登录对应的样式表的sql语句: map_sql_selectCss = "
						+ map_sql_selectCss);
				rs = stmt.executeQuery(map_sql_selectCss);
				while (rs.next()) {
					rs_cssfile = rs.getString(1);
				}
				String map_sql_selectDataUserRole=dao.getSQL_selectDataUserRole(new Integer(id));
				rs = stmt.executeQuery(map_sql_selectDataUserRole);
				while(rs.next()){
					rs_roleno = rs_roleno+":"+rs.getString(2);
					rs_cdefault = rs_cdefault+":"+rs.getString(3);
				}
				System.out.println("rs_roleno = "+rs_roleno+"\n"+"rs_cdefault = "+rs_cdefault);
				String rs_debug = "rs_usrno = " + rs_usrno + "\n"
						+ "rs_usrname = " + rs_usrname + "\n"
						+ "rs_password = " + rs_password + "\n"
						+ "rs_roleno = " + rs_roleno + "\n"
						+ "rs_cdefault = " + rs_cdefault + "\n"
						+ "rs_employeeid = " + rs_employeeid + "\n"
						+ "rs_state = " + rs_state + "\n"
						+ "rs_lastupdateuser = " + rs_lastupdateuser + "\n"
						+ "rs_lastupdatetime = " + rs_lastupdatetime + "\n"
						+ "rs_note = " + rs_note + "\n" + "rs_enabled = "
						+ rs_enabled + "\n" + "rs_cssfile = " + rs_cssfile;
				log.debug(processInfo + "查询的所有信息为: rs_debug = " + rs_debug);
				data.put("rs_usrno", rs_usrno);
				data.put("rs_usrname", rs_usrname);
				data.put("rs_password", rs_password);
				data.put("rs_roleno", rs_roleno.trim());
				data.put("rs_cdefault",rs_cdefault.trim());
				data.put("rs_employeeid", rs_employeeid);
				data.put("rs_state", rs_state);
				data.put("rs_lastupdateuser", rs_lastupdateuser);
				data.put("rs_lastupdatetime", rs_lastupdatetime);
				data.put("rs_note", rs_note);
				data.put("rs_enabled", rs_enabled);
				data.put("rs_cssfile", rs_cssfile);
				message.setOtherParameter(this.getClass().getName(), data);

				/*
				 * 删除用户
				 */
				String sql_delete = dao.getSQL_DeleteUser(id);
				log.debug("根据用户id删除用户表的sql语句: sql_delete = " + sql_delete);
				String sql_deleteCss = dao.getSQL_DeleteCss(id);
				log.debug("根据用户id删除css样式的sql语句: sql_deleteCss = "
						+ sql_deleteCss);
				String sql_deleteDataUserRole = dao.getSQL_DeleteDataUserRole(new Integer(id));
				log.debug("根据用户id删除DataUserRole表的sql语句: sql_deleteDataUserRole = "
						+ sql_deleteDataUserRole);
				try {
					con.setAutoCommit(false);
					stmt.executeUpdate(sql_delete);
					stmt.executeUpdate(sql_deleteCss);
					stmt.executeUpdate(sql_deleteDataUserRole);
					con.commit();
					con.setAutoCommit(true);

				} catch (SQLException Ecommit) {
					try {
						con.rollback();
					} catch (SQLException Erollback) {
						message.addServiceException(new ServiceException(
								ServiceExceptionType.DATABASEERROR, "数据库回滚错误"
										+ Erollback, this.getId(),
								soa_processid, new Date(), null));
						log.fatal(processInfo + ",删除用户,数据库回滚错误"
								+ Erollback.toString());
						return ExecuteResult.fail;
					}
					finally {
						if (stmt != null)
							stmt.close();
					}
					message.addServiceException(new ServiceException(
							ServiceExceptionType.DATABASEERROR, "数据库提交错误"
									+ Ecommit, this.getId(), soa_processid,
							new Date(), null));
					log.fatal(processInfo + ",删除用户,数据库提交错误"
							+ Ecommit.toString());
					return ExecuteResult.fail;
				}
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库错误" + sqle,
						this.getId(), soa_processid, new Date(), sqle));
				log.fatal(processInfo + ",删除用户,数据库操作异常" + sqle.toString());
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
			log.fatal(processInfo + ",删除用户,未知异常" + e.toString());

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
			String map_usrno = map.get("rs_usrno");
			String map_usrname = map.get("rs_usrname");
			String map_password = map.get("rs_password");
			String map_roleno = map.get("rs_roleno");
			String map_cdefault = map.get("rs_cdefault");
			String map_employeeid = map.get("rs_employeeid");
			String map_state = map.get("rs_state");
			String map_lastupdateuser = map.get("rs_lastupdateuser");
			String map_lastupdatetime = map.get("rs_lastupdatetime");
			String map_note = map.get("rs_note");
			String map_enabled = map.get("rs_enabled");
			String map_cssfile = map.get("rs_cssfile");
			String map_debug = "map_usrno = " + map_usrno + "\n"
					+ "map_usrname = " + map_usrname + "\n" + "map_password = "
					+ map_password + "\n" + "map_roleno = " + map_roleno + "\n"
					+ "map_cdefault = " + map_cdefault + "\n"
					+ "map_employeeid = " + map_employeeid + "\n"
					+ "map_state = " + map_state + "\n"
					+ "map_lastupdateuser = " + map_lastupdateuser + "\n"
					+ "map_lastupdatetime = " + map_lastupdatetime + "\n"
					+ "map_note = " + map_note + "\n" + "map_enabled = "
					+ map_enabled + "\n" + "map_cssfile = " + map_cssfile;
			log.debug(processInfo + "删除用户回退操作Map接收的所有信息为: map_debug = "
					+ map_debug);
			try {
				Statement stmt = null;
				try {
					IDAO_UserManager dao = DAOFactory_UserManager
							.getInstance(DataBaseType.getDataBaseType(con));
					if (dao == null) {
						message.addServiceException(new ServiceException(
								ServiceExceptionType.UNKNOWNDATABASETYPE, "",
								this.getId(), soa_processid, new Date(), null));
						log.error(processInfo + ",删除用户回退操作,加载数据库错误,未知的数据库类型");
						return ExecuteResult.fail;
					}
					log.debug(processInfo + ",删除用户回退操作,加载数据库成功");
					stmt = con.createStatement();
					String map_sql_insert = dao.getSQL_InsertUser(map_usrno,
							map_usrname, map_password,"", map_employeeid,
							map_state, map_lastupdateuser, map_lastupdatetime,
							map_note, map_enabled);
					log.debug(processInfo + "删除用户回退操作的sql语句: map_sql_insert = "
							+ map_sql_insert);
					String map_sql_insertCss = dao.getSQL_insertCss(map_usrno,
							map_cssfile);
					log.debug(processInfo
							+ "删除用户回退操作样式的sql语句: map_sql_insertCss = "
							+ map_sql_insertCss);
					try {
						con.setAutoCommit(false);
						stmt.executeUpdate(map_sql_insert);
						stmt.executeUpdate(map_sql_insertCss);
						/*
						 * 此处为一个用户-角色多对多的修改处
						 */
						String[] str_role = map_roleno.split(":");
						String[] str_default = map_cdefault.split(":");
						String sql_insertDataUserRole = "";
						for (int i=0;i<str_role.length;i++) {
							if (str_role[i] != null && !str_role[i].equals("")) {
								sql_insertDataUserRole = dao
										.getSQL_insertDataUserRole(new Integer(
												map_usrno), new Integer(str_role[i]), str_default[i]);
								log
										.debug("插入data_user_role表的sql语句: sql_insertDataUserRole = "
												+ sql_insertDataUserRole);
								stmt.executeUpdate(sql_insertDataUserRole);
							}
						}
						con.commit();
						con.setAutoCommit(true);
					} catch (SQLException Ecommit) {
						try {
							con.rollback();
						} catch (SQLException Erollback) {
							message.addServiceException(new ServiceException(
									ServiceExceptionType.DATABASEERROR,
									"数据库回滚错误" + Erollback, this.getId(),
									soa_processid, new Date(), null));
							log.fatal(processInfo + ",删除用户回退操作,数据库回滚错误"
									+ Erollback.toString());
							return ExecuteResult.fail;
						}
						message.addServiceException(new ServiceException(
								ServiceExceptionType.DATABASEERROR, "数据库提交错误"
										+ Ecommit, this.getId(), soa_processid,
								new Date(), null));
						log.fatal(processInfo + ",删除用户回退操作,数据库提交错误"
								+ Ecommit.toString());
						return ExecuteResult.fail;
					}
					finally {
						if (stmt != null)
							stmt.close();
					}
				} catch (SQLException sqle) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.DATABASEERROR, "数据库错误" + sqle,
							this.getId(), soa_processid, new Date(), sqle));
					log.fatal(processInfo + ",删除用户回退操作,数据库操作异常"
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
				log.fatal(processInfo + ",删除用户回退操作,未知异常" + e.toString());

				return ExecuteResult.fail;
			}
		}
		return ExecuteResult.sucess;
	}

	private boolean initFordo(IMessage message, String soa_processid) {

		con = null;
		id = null;

		con = (Connection) message.getOtherParameter("con");
		id = message.getUserParameterValue("id");
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		String debug_del = "id = " + id + "\n";
		log.debug(processInfo + ",删除用户,用户提交的参数" + debug_del);
		if (id == null) {
			message
					.addServiceException(new ServiceException(
							ServiceExceptionType.PARAMETERLOST, "添加用户时参数为空",
							this.getId(), soa_processid, new java.util.Date(),
							null));
			log.error(processInfo + ",删除用户,缺少输入参数");
			return false;
		}
		return true;
	}
}
