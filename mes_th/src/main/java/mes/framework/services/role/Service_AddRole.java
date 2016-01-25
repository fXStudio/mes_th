package mes.framework.services.role;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

public class Service_AddRole extends AdapterService {

	private Connection con = null;

	private String rolename = null;

	private String rank = null;

	private String powerstring = null;

	private String welcomepage = null;

	private String userid = null;

	private String note = null;

	private final Log log = LogFactory.getLog(Service_AddRole.class);

	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");

		rolename = message.getUserParameterValue("rolename");
		rank = message.getUserParameterValue("rank");
		powerstring = message.getUserParameterValue("powerstring");
		welcomepage = message.getUserParameterValue("welcomepage");
		userid = message.getUserParameterValue("userid");
		note = message.getUserParameterValue("note");
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		String debug_AddRole = "rolename = " + rolename + "\n" + "rank = "
				+ rank + "\n" + "powerstring = " + powerstring + "\n"
				+ "welcomepage = " + welcomepage + "\n" + "userid = " + userid
				+ "\n" + "note = " + note + "\n";
		log.debug(processInfo + ",添加角色时用户提交的参数: " + debug_AddRole);

		if (rolename == null || rank == null || powerstring == null
				|| welcomepage == null || userid == null || note == null
				|| con == null) {
			log.error(processInfo + ",添加角色,缺少输入参数");
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
		String roleno = "0";

		IDAO_UserManager daor = DAOFactory_UserManager.getInstance(DataBaseType
				.getDataBaseType(con));

		Statement st = con.createStatement();
		synchronized (this) {
			log.debug(processInfo + ",查询下一个可用于插入操作的角色号的sql语句: sql = "
					+ daor.getSQL_QueryNextRoleNO());
			ResultSet set = st.executeQuery(daor.getSQL_QueryNextRoleNO());
			if (set.next()) {
				roleno = set.getString(1);
				set.close();
			}
			Map<String, String> data = new HashMap<String, String>();
			data.put("map_roleno", roleno);
			data.put("map_userid", userid);
			message.setOtherParameter(this.getClass().getName(), data);
			log.debug("插入角色信息的sql语句: sql = "
					+ daor.getSQL_InsertRole(roleno, rolename, rank,
							powerstring, welcomepage, userid, note));
			st.executeUpdate(daor.getSQL_InsertRole(roleno, rolename, rank,
					powerstring, welcomepage, userid, note));
		}
		st.close();
		return ExecuteResult.sucess;
	}

	@SuppressWarnings("unchecked")
	@Override
	
	public ExecuteResult undoAdapterService(IMessage message, String soa_processid) {
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		Object obj = message.getOtherParameter(this.getClass().getName());
		if (obj instanceof Map) {
			Map<String, String> map = (Map<String, String>) obj;
			String map_roleno = map.get("map_roleno");
			String map_userid = map.get("map_userid");
			String map_debug = "map_roleno = " + map_roleno + "\n"
					+ "map_userid = " + map_userid;
			log
					.debug(processInfo + "添加角色回退操作接收Map的值: map_debug = "
							+ map_debug);
			try {
				IDAO_UserManager daor = DAOFactory_UserManager
						.getInstance(DataBaseType.getDataBaseType(con));
				Statement st = con.createStatement();
				log.debug(processInfo + ",添加角色回退操作的sql语句 sql = "
						+ daor.getSQL_DeleteRole(map_roleno, map_userid));
				for (String sql : daor
						.getSQL_DeleteRole(map_roleno, map_userid)) {
					st.addBatch(sql);
				}
				synchronized (this) {
					st.executeBatch();
				}
				st.close();
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "提交错误", this
								.getId(), soa_processid, new Date(), sqle));
				log.fatal(processInfo + ",添加角色回退操作提交错误" + sqle.toString());
			}
		}
		return ExecuteResult.sucess;
	}

	@Override
	public void relesase() throws SQLException {
		rolename = null;
		rank = null;
		powerstring = null;
		welcomepage = null;
		userid = null;
		note = null;
		con = null;
	}
}
