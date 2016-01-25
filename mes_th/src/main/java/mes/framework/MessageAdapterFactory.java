package mes.framework;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Map;

import com.qm.mes.th.helper.Conn_MES;

import mes.framework.dao.DAOFactory_Core;
import mes.framework.dao.IDAO_Core;

/**
 * 消息适配器工厂
 * 
 * @author 张光磊 2007-6-21
 */
public final class MessageAdapterFactory {
	private static Map<String, IMessageAdapter> mamap = new Hashtable<String, IMessageAdapter>();

	/**
	 * 加载所有的适配器
	 * 
	 * @param con
	 * @throws SQLException
	 */
	public static void loadAllMessageAdapter(Connection con) throws SQLException {
		IDAO_Core ma = DAOFactory_Core.getInstance(DataBaseType.getDataBaseType(con));
		loadMessageAdapter(ma.getSQL_QueryAllAdapterInfos(), con);
	}

	/**
	 * 返回作为消息对象的<br>
	 * 通过流程id和服务别名查找到的<br>
	 * 以参数message为数据源的消息适配对象
	 * 
	 * @param processid
	 *            流程id
	 * @param serviceName
	 *            服务别名
	 * @param message
	 *            消息对象
	 * @return 若适配器不存在则直接返回消息对象
	 */
	public static IMessage getMessage(String processid, String serviceName, IMessage message) {
		return getMessage(processid, serviceName, message, true);
	}

	private static IMessage getMessage(String processid, String serviceName, IMessage message, boolean b) {
		if (b) {
			IMessageAdapter adapter = mamap.get(processid + "." + serviceName);
			if (adapter != null) {
				adapter.setSource(message);
				return adapter;
			}
		}

		Connection con = null;
		try {
			con = new Conn_MES().getConn();
			IDAO_Core ma = DAOFactory_Core.getInstance(DataBaseType.getDataBaseType(con));

			IMessageAdapter message2 = loadMessageAdapter(ma.getSQL_QueryAdepterInfo(processid, serviceName), con);
			if (message2 == null) {
				message2 = new DefMessageAdapter(processid, serviceName);
			}
			message2.setSource(message);
			return message2;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException sqle) {
			}
		}
		return message;
	}

	private static IMessageAdapter loadMessageAdapter(String sql, Connection con) throws SQLException {
		Statement st_base = con.createStatement();
		ResultSet set = st_base.executeQuery(sql);
		DefMessageAdapter temp = null;
		// 以下的部分虽然可用，还有改进的空间
		while (set.next()) {
			String temp_sname = set.getString("cialiasname");
			String temp_pid = set.getString("nprocessid");

			if (temp == null)
				temp = new DefMessageAdapter(temp_pid, temp_sname);

			if (!temp.getProcessid().equals(temp_pid) || !temp.getServiceName().equals(temp_sname))
				temp = new DefMessageAdapter(temp_pid, temp_sname);

			String targetServiceName = set.getString("COALIASNAME");
			// 若不是其它服务的输出
			if (targetServiceName == null || targetServiceName.trim().equals("")) {
				temp.setAdapterName(set.getString("ciparameter"), set.getString("COPARAMETER"));
			} else {
				temp.setAdapterName(set.getString("ciparameter"),
						targetServiceName + "." + set.getString("COPARAMETER"));
			}
			mamap.put(temp.getProcessid() + "." + temp.getServiceName(), temp);
		}
		st_base.close();
		return temp;
	}

}
