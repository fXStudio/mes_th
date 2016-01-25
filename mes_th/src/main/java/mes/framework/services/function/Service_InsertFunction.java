package mes.framework.services.function;

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

/**
 * 服务：添加服务定义信息 输入依赖：需要在otherparameter中放置一个Connection对象，名为con。<br>
 * 需要在用户输入或服务输出中含有functionname,url,upfunctionid,state,note,safemarkcode,nodetype,rank,userid,enable值，即使是空字符串。
 * <br>
 * 执行 doService() <br>
 * 初始化 initFordo() <br>
 * 回退 undoService() <br>
 * 输出：无输出
 * 
 * @author 于丽达 2008-01-02
 */
public class Service_InsertFunction extends DefService implements IService {

	// 数据库连接
	private Connection con = null;

	// 功能名称
	private String functionname = null;

	// 文件url
	private String url = null;

	// 父功能号
	private String upfunctionid = null;

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

	// 系统标识
	private String enable = null;

	// 功能顺序
	private String flo_Order = null;

	public Service_InsertFunction() {
		super();
	}

	private final Log log = LogFactory.getLog(Service_InsertFunction.class);

	public synchronized ExecuteResult doService(IMessage message,
			String soa_processid) {
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		// 初始化服务运行参数，如失败，返回执行结果
		if (!initFordo(message, soa_processid)) {
			log.error(processInfo + ",添加功能,初始化服务运行失败");
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
					log.error(processInfo + ",添加功能,加载数据库错误,未知的数据库类型");
					return ExecuteResult.fail;
				}
				log.debug(processInfo + ",添加功能,加载数据库成功");
				int uid = 0;// 用户ID
				int upfid = 0;// 父功能号
				uid = Integer.parseInt(userid);
				upfid = Integer.parseInt(upfunctionid);

				stmt = con.createStatement();
				// 生成功能号
				String sql_create = dao.getSQL_QueryNextFuntionId();
				log.debug("生成功能号的sql语句: sql_create = " + sql_create);
				rs = stmt.executeQuery(sql_create);
				int fid = 0;// 功能号
				if (rs.next()) {
					fid = rs.getInt(1);
				}
				int length = 0;
				// 获取权限串长度
				String sql_get = dao.getSQL_QueryPowerStringLength();
				log.debug("获取系统中权限串长度的sql语句: sql_get = " + sql_get);
				rs = stmt.executeQuery(sql_get);
				if (rs.next()) {
					length = rs.getInt(1);
				}
				String appendString = "";
				if (length < fid) {
					for (int i = length + 1; i <= fid; i++) {
						appendString = appendString + "0";
					}
				}
				log.debug(processInfo + ",添加功能的功能串为: appendString = "
						+ appendString);
				// 添加功能回退操作所需参数放入Map中
				Map<String, String> data = new HashMap<String, String>();
				data.put("map_userid", String.valueOf(uid));
				data.put("map_functionid", String.valueOf(fid));
				data.put("map_appendString", appendString);
				message.setOtherParameter(this.getClass().getName(), data);

				// 添加功能
				String sql_insert = dao.getSQL_InsertFunction(fid,
						functionname, nodetype, url, upfid, rank, state, uid,
						safemarkcode, note, enable, Float.valueOf(flo_Order));
				log.debug("添加功能的sql语句: sql_insert = " + sql_insert);
				// 更新权限串
				String sql_update = dao
						.getSQL_UpdatePowerStringWhenAdd(appendString);
				log.debug("添加功能后更新权限串的sql语句: sql_update = " + sql_update);
				con.setAutoCommit(false);
				stmt.executeUpdate(sql_insert);
				stmt.executeUpdate(sql_update);
				con.commit();
				con.setAutoCommit(true);

			} catch (SQLException sqle) {
				con.rollback();
				con.setAutoCommit(true);
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), soa_processid, new Date(), sqle));
				log.fatal(processInfo + ",添加功能,数据库操作异常" + sqle.toString());
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
			log.fatal(processInfo + ",添加功能,未知异常" + e.toString());
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
			int map_uid = Integer.parseInt(map.get("map_userid"));
			int map_functionid = Integer.parseInt(map.get("map_functionid"));
			String map_appendString = map.get("map_appendString");
			String map_debug = "map_uid = " + map_uid + "\n"
					+ "map_functionid = " + map_functionid + "\n"
					+ "map_appendString = " + map_appendString;
			log.debug(processInfo + "添加功能回退操作Map接收到的值: map_debug = "
					+ map_debug);
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
						log.error(processInfo + ",添加功能回退操作,加载数据库错误,未知的数据库类型");
						return ExecuteResult.fail;
					}
					log.debug(processInfo + ",添加功能回退操作,加载数据库成功");
					stmt = con.createStatement();
					String sql_delete = dao
							.getSQL_DeleteFunction(map_functionid);
					log.debug(processInfo + ",添加功能回退操作的sql语句: sql_delete = "
							+ sql_delete);
					String sql_update = dao.getSQL_UpdatePowerStringWhenDelete(
							map_uid, String.valueOf(map_functionid));
					log.debug(processInfo
							+ ",添加功能回退操作时更新权限串的sql语句: sql_update = "
							+ sql_update);
					con.setAutoCommit(false);
					stmt.executeUpdate(sql_delete);
					stmt.executeUpdate(sql_update);
					con.commit();
					con.setAutoCommit(true);
				} catch (SQLException sqle) {
					con.rollback();
					con.setAutoCommit(true);
					message.addServiceException(new ServiceException(
							ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
									.getId(), soa_processid, new Date(), sqle));
					log.fatal(processInfo + ",添加功能回退操作,数据库操作异常"
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
				log.fatal(processInfo + ",添加功能回退操作,未知异常" + e.toString());
				return ExecuteResult.fail;
			}
		}
		return ExecuteResult.sucess;
	}

	private boolean initFordo(IMessage message, String soa_processid) {
		// 数据库连接
		con = null;
		// 功能名称
		functionname = null;
		// 文件url
		url = null;
		// 父功能号
		upfunctionid = null;
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
		// 管理员
		userid = null;
		// 系统标识
		enable = null;
		// 功能顺序
		flo_Order = null;

		// 获取该服务的输入参数的值
		con = (Connection) message.getOtherParameter("con");
		functionname = message.getUserParameterValue("functionname");
		nodetype = message.getUserParameterValue("nodetype");
		url = message.getUserParameterValue("url");
		upfunctionid = message.getUserParameterValue("upfunctionid");
		safemarkcode = message.getUserParameterValue("safemarkcode");
		note = message.getUserParameterValue("note");
		state = message.getUserParameterValue("state");
		rank = message.getUserParameterValue("rank");
		userid = message.getUserParameterValue("userid");
		enable = message.getUserParameterValue("enable");
		flo_Order = message.getUserParameterValue("flo_Order");
		
		// 获取运行流程项
		IProcess process = ProcessFactory.getInstance(soa_processid);
		String processInfo = process.getNameSpace() + "." + process.getName();
		String debug_Service_InsertFunction = "功能名: functionname = "
				+ functionname + "\n" + "文件url: url = " + url + "\n"
				+ "父功能号: upfunctionid = " + upfunctionid + "\n"
				+ "状态: state = " + state + "\n" + "备注: note = " + note + "\n"
				+ "访问安全标记: safemarkcode = " + safemarkcode + "\n"
				+ "节点类型: nodetype = " + nodetype + "\n" + "级别: rank = " + rank
				+ "\n" + "管理员: userid = " + userid + "\n" + "系统标识: enable = "
				+ enable + "\n" + "功能顺序: flo_Order = " + flo_Order + "\n";
		log.debug(processInfo + ",添加功能时用户提交的参数: "
				+ debug_Service_InsertFunction);

		if (functionname == null || nodetype == null || url == null
				|| upfunctionid == null || safemarkcode == null || note == null
				|| state == null || rank == null || userid == null
				|| enable == null || con == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "缺少输入参数", this.getId(),
					soa_processid, new java.util.Date(), null));
			log.error(processInfo + ",添加功能,缺少输入参数");
			return false;
		}
		return true;
	}
}
