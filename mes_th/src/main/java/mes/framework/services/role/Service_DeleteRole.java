package mes.framework.services.role;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mes.beans.DataUserRole;
import mes.beans.Users;
import mes.framework.AdapterService;
import mes.framework.DataBaseType;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.IProcess;
import mes.framework.ProcessFactory;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.framework.dao.DAOFactory_UserManager;
import mes.framework.dao.IDAO_UserManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Service_DeleteRole extends AdapterService {

	// 数据库连接
	private Connection con = null;

	private Statement st = null;

	/**
	 * 角色号
	 */
	String roleno;

	/**
	 * 用户id
	 */
	String userid;

	List<DataUserRole> listdur = null;

	List<Users> listUser = null;

	private final Log log = LogFactory.getLog(Service_DeleteRole.class);

	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");

		roleno = message.getUserParameterValue("roleno");
		userid = message.getUserParameterValue("userid");
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		String debug_DeleteRole = "roleno = " + roleno + "\n" + "userid = "
				+ userid + "\n";
		log.debug(processInfo + ",删除角色 " + debug_DeleteRole);

		if (roleno == null || userid == null || con == null) {
			log.error(processInfo + ",删除角色,缺少输入参数");
			return false;
		}
		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		IDAO_UserManager daor = DAOFactory_UserManager.getInstance(DataBaseType
				.getDataBaseType(con));
		st = con.createStatement();
		// 回退操作所需参数
		Map<String, String> data = new HashMap<String, String>();
		String map_rolename = null;
		String map_rank = null;
		String map_powerstring = null;
		String map_welcomepage = null;
		String map_userid = null;
		String map_note = null;
		String map_cenabled = null;
		String sql_selectRole = daor.getSQL_QueryRoleForRoleNo(roleno);
		log.debug(processInfo + "" + sql_selectRole);
		ResultSet rs = st.executeQuery(sql_selectRole);
		while (rs.next()) {
			map_rolename = rs.getString(2);
			map_rank = rs.getString(3);
			map_powerstring = rs.getString(4);
			map_welcomepage = rs.getString(5);
			map_userid = rs.getString(6);
			map_note = rs.getString(8);
			map_cenabled = rs.getString(9);
		}
		// 此处开始
		String sql_selectDataUserRole_Roleno = daor
				.getSQL_selectDataUserRole_Roleno(new Integer(roleno));
		log
				.debug("根据角色号查询DataUserRole表的sql语句: sql_selectDataUserRole_Roleno = "
						+ sql_selectDataUserRole_Roleno);
		String sql_selectUser = "";
		listdur = new ArrayList<DataUserRole>();
		listUser = new ArrayList<Users>();
		rs = st.executeQuery(sql_selectDataUserRole_Roleno);
		while (rs.next()) {
			DataUserRole dur = new DataUserRole();
			dur.setUsrno(rs.getString(1));
			dur.setRoleno(rs.getString(2));
			dur.setCdefault(rs.getString(3));
			log.debug(processInfo + "此角色号返回的所有信息: usrno = " + dur.getUsrno() + "\n"
					+ "roleno = " + dur.getRoleno() + "\n" + "cdefault = "
					+ dur.getCdefault());
			listdur.add(dur);
		}
		//数据都放入到List中,再遍历一下进行以下操作
		for (DataUserRole dur : listdur) {
			sql_selectUser = daor.getSQL_QueryUserInfoForUserID(dur.getUsrno());
			log
			.debug("根据用户号查询DataUser表的sql语句:sql_selectUser = " + sql_selectUser);
			rs = st.executeQuery(sql_selectUser);
			while (rs.next()) {
				Users us = new Users();
				System.out.println("uname = " + rs.getString(2));
				us.setUserno(rs.getString(1));
				us.setUsername(rs.getString(2));

				us.setPassword(rs.getString(3));
				us.setEmployeeid(rs.getString(5));
				us.setState(rs.getString(6));
				us.setLastupdateuser(rs.getString(7));
				us.setLastupdatetime(rs.getString(8));
				us.setNote(rs.getString(9));
				us.setUser_enabled(rs.getString(10));
				log.debug(processInfo + "此角色号返回的所有信息:Userno = " + us.getUserno() + "\n"
						+ "username = " + us.getUsername() + "\n"
						+ "password = " + us.getPassword() + "\n"
						+ "employeeid = " + us.getEmployeeid() + "\n"
						+ "state = " + us.getState() + "\n"
						+ "lastupdateuser = " + us.getLastupdateuser() + "\n"
						+ "lastupdatetime = " + us.getLastupdatetime() + "\n"
						+ "note = " + us.getNote() + "\n" + "enabled = "
						+ us.getUser_enabled());
				listUser.add(us);
			}

		}
		// 就到这里吧
		String rs_debug = "rs_rolename = " + map_rolename + "\n" + "rs_rank = "
				+ map_rank + "\n" + "rs_powerstring = " + map_powerstring
				+ "\n" + "rs_welcomepage = " + map_welcomepage + "\n"
				+ "rs_userid = " + map_userid + "\n" + "rs_note = " + map_note
				+ "\n" + "rs_cenabled = " + map_cenabled;
		log.debug(processInfo + "此角色号返回的所有信息: rs_debug = " + rs_debug);
		data.put("map_roleno", roleno);
		data.put("map_rolename", map_rolename);
		data.put("map_rank", map_rank);
		data.put("map_powerstring", map_powerstring);
		data.put("map_welcomepage", map_welcomepage);
		data.put("map_userid", map_userid);
		data.put("map_note", map_note);
		data.put("map_cenabled", map_cenabled);
		message.setOtherParameter(this.getClass().getName(), data);

		String sql_User = daor.getSQL_DeleteUser_Roleno(new Integer(roleno));
		log.debug(processInfo + ",删除User表角色的sql语句 sql = " + sql_User);
		String sql_DataUserRole = daor
				.getSQL_DeleteDataUserRole_Roleno(new Integer(roleno));
		log.debug(processInfo + ",删除DataUserRole角色的sql语句 sql = "
				+ sql_DataUserRole);

		st.executeUpdate(sql_User);
		st.executeUpdate(sql_DataUserRole);

		log.debug(processInfo + ",删除角色的sql语句 sql = "
				+ daor.getSQL_DeleteRole(roleno, userid));
		for (String sql : daor.getSQL_DeleteRole(roleno, userid)) {
			st.addBatch(sql);
		}
		synchronized (this) {
			st.executeBatch();
		}
		return ExecuteResult.sucess;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ExecuteResult undoAdapterService(IMessage message,
			String soa_processid) {
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		Object obj = message.getOtherParameter(this.getClass().getName());
		if (obj instanceof Map) {
			Map<String, String> map = (Map<String, String>) obj;
			String map_roleno = map.get("map_roleno");
			String map_rolename = map.get("map_rolename");
			String map_rank = map.get("map_rank");
			String map_powerstring = map.get("map_powerstring");
			String map_welcomepage = map.get("map_welcomepage");
			String map_userid = map.get("map_userid");
			String map_note = map.get("map_note");
			String map_cenabled = map.get("map_cenabled");
			String map_debug = "map_roleno = " + map_roleno + "\n"
					+ "map_rolename = " + map_rolename + "\n" + "rs_rank = "
					+ map_rank + "\n" + "map_powerstring = " + map_powerstring
					+ "\n" + "map_welcomepage = " + map_welcomepage + "\n"
					+ "map_userid = " + map_userid + "\n" + "map_note = "
					+ map_note + "\n" + "map_cenabled = " + map_cenabled;
			log.debug(processInfo + "此角色号返回的所有信息: map_debug = " + map_debug);
			try {
				IDAO_UserManager daor = DAOFactory_UserManager
						.getInstance(DataBaseType.getDataBaseType(con));
				Statement st = con.createStatement();
				synchronized (this) {
					String undo_sql_insert = daor.getSQL_InsertRole(map_roleno,
							map_rolename, map_rank, map_powerstring,
							map_welcomepage, map_userid, map_note);
					log.debug(processInfo
							+ ",删除角色信息回退操作的sql语句: undo_sql_insert = "
							+ undo_sql_insert);
					st.executeUpdate(undo_sql_insert);
				}
				for (int j = 0; j < listUser.size(); j++) {
					Users u = (Users) listUser.get(j);
					String undo_sql_insertUser = daor.getSQL_InsertUser(u
							.getUserno(), u.getUsername(), u.getPassword(), u
							.getRoleno(), u.getEmployeeid(), u.getState(), u
							.getLastupdateuser(), u.getLastupdatetime(), u
							.getNote(), u.getUser_enabled());
					st.executeUpdate(undo_sql_insertUser);
				}
				for (int i = 0; i < listdur.size(); i++) {
					DataUserRole d = (DataUserRole) listdur.get(i);
					String undo_sql_insertDataUserRole = daor
							.getSQL_insertDataUserRole(
									new Integer(d.getUsrno()), new Integer(d
											.getRoleno()), d.getCdefault());
					st.executeUpdate(undo_sql_insertDataUserRole);
				}
				st.close();
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "提交错误", this
								.getId(), soa_processid, new Date(), sqle));
				log.fatal(processInfo + ",删除角色回退操作提交错误" + sqle.toString());
			}
		}
		return ExecuteResult.sucess;
	}

	@Override
	public void relesase() throws SQLException {
		con = null;
		roleno = null;
		userid = null;
		st.close();
	}

}
