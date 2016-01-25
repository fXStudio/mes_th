package mes.framework;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import common.Conn;

/**
 * 服务总线工厂，此工厂应用单例模式
 * 
 * @author 张光磊 2007-6-21
 */
public final class ServiceBusFactory {
	/**
	 * 服务总线对象
	 */
	private static IServiceBus esb = null;

	/**
	 * 默认的服务总线类，此类禁止其他类访问，所以做成内部类。
	 * 
	 * @author 张光磊 2007-6-21
	 */
	private static class DefServiceBus implements IServiceBus {

		/**
		 * 通过传入的数据库连接初始化系统环境
		 * 
		 * @param con
		 *            数据库连接
		 * @throws SQLException
		 */
		public DefServiceBus(Connection con) throws SQLException {
			// 加载所有的服务
			// ServiceFactory.loadAllService(con);
			// 加载所有的流程
			ProcessFactory.loadAllProcess(con);
			// 加载所有的消息适配器
			MessageAdapterFactory.loadAllMessageAdapter(con);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see zgl.realtool.mes.framework.IServiceBus#doService(java.lang.String,
		 *      java.lang.String, zgl.realtool.mes.framework.IMessage)
		 */
		public ExecuteResult doService(String serviceid, String processid,
				IMessage message) {
			ExecuteResult result = ExecuteResult.fail;
			// 获得对应的服务
			IService s = ServiceFactory.getInstance(serviceid);
			if (s == null) {
				// 若服务不存在，则返回运行失败，并记录异常信息。
				message.addServiceException(new ServiceException(
						ServiceExceptionType.SERVICELOST, "服务不存在", serviceid,
						processid, new Date(), null));
				return ExecuteResult.fail;
			}
			// 这里接受到的message是已经包装过的适配器对象了，所以这里直接调用即可。
			result = s.doService(message, processid);
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see zgl.realtool.mes.framework.IServiceBus#doProcess(java.lang.String,
		 *      zgl.realtool.mes.framework.IMessage)
		 */
		public synchronized ExecuteResult doProcess(String processid, IMessage message) {
			IProcess process = ProcessFactory.getInstance(processid);
			if (process == null) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.UNKNOWN, "流程不存在", "-1", processid,
						new Date(), null));
				return ExecuteResult.fail;
			}
			ExecuteResult result = process.doProcess(message);
			return result;
		}

	}

	public static IServiceBus getInstance() {
		if (esb != null)
			return esb;
		// TODO 系统用户构建环境的连接和用户使用的连接是通过不同用户登录到数据库的连接。
		try {
			//加载配置文件
			//PropertyConfigurator.configure("properties.lcf");
			Connection con =  (new Conn()).getConn();
			esb = new DefServiceBus(con);
			con.close();
			return esb;
		} catch (SQLException e) {
			e.printStackTrace();
			// 初始化环境失败，系统无法正常运行
			esb = null;
			return esb;
		}
	}
}
