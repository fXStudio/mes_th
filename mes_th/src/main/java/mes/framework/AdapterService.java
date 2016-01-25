package mes.framework;

import java.sql.SQLException;
import java.util.Date;

/**
 * 服务适配器<br>
 * 继承此服务后用户可以进一步考虑完成业务的代码，而不用为异常的捕获写冗余的代码。 <br>
 * <font color="red"><b>建议继承</b></font>
 * 
 * @author 张光磊 2007-6-28
 */
public abstract class AdapterService extends DefService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see zgl.realtool.mes.framework.IService#doService(zgl.realtool.mes.framework.IMessage,
	 *      java.lang.String)
	 */
	public final ExecuteResult doService(IMessage message, String processid) {

		return doWork(message, processid, true);
	}

	@Override
	public final ExecuteResult undoService(IMessage message, String processid) {
		return doWork(message, processid, false);
	}

	/**
	 * 回滚服务<br>
	 * 在这个服务中可以适当的加入少量的捕获异常的代码。 <br>
	 * <font color="red"><b>若服务希望实现回滚操作，覆盖此方法。</b></font>
	 * 
	 * @param message
	 * @param processid
	 * @return 返回服务运行结果
	 * @throws SQLException
	 * @throws Exception
	 */
	public ExecuteResult undoAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		return ExecuteResult.fail;
	}

	private ExecuteResult doWork(IMessage message, String processid,
			boolean isdo) {
		ExecuteResult result = ExecuteResult.fail;
		try {
			if (!checkParameter(message, processid)) {
				message
						.addServiceException(new ServiceException(
								ServiceExceptionType.PARAMETERLOST, "缺少输入参数",
								this.getId(), processid, new java.util.Date(),
								null));
				return result;
			}
			if (isdo)
				return doAdapterService(message, processid);
			else
				return undoAdapterService(message, processid);

		} // 捕获SQL异常
		catch (SQLException sqle) {
			sqle.printStackTrace();
			// 记录异常信息
			message.addServiceException(new ServiceException(
					ServiceExceptionType.DATABASEERROR, "数据库操作异常",
					this.getId(), processid, new Date(), null));

		}// 捕获未知一场
		catch (Exception e) {
			e.printStackTrace();
			// 记录异常信息
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, "未知异常：" + e.toString(), this
							.getId(), processid, new java.util.Date(), e));

		} finally {
			try {
				relesase();
			} catch (Exception e) {
				e.printStackTrace();
				message.addServiceException(new ServiceException(
						ServiceExceptionType.UNKNOWN, "释放资源时出现异常："
								+ e.toString(), this.getId(), processid,
						new java.util.Date(), e));
			}
		}
		return result;
	}

	/**
	 * 释放资源<br>
	 * 无论服务正常还是异常返回，都会调用此方法
	 * 
	 * @throws SQLException
	 */
	public abstract void relesase() throws SQLException;

	/**
	 * 验证参数<br>
	 * 若方法返回false则自动生成“缺少参数”的异常信息
	 * 
	 * @param message
	 * @param processid
	 * @return
	 */
	public abstract boolean checkParameter(IMessage message, String processid);

	/**
	 * 执行服务<br>
	 * 在这个服务中可以适当的加入少量的捕获异常的代码。
	 * 
	 * @param message
	 * @param processid
	 * @return 返回服务运行结果
	 * @throws SQLException
	 * @throws Exception
	 */
	public abstract ExecuteResult doAdapterService(IMessage message,
			String processid) throws SQLException, Exception;
}
