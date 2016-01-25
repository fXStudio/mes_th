package mes.framework;

/**
 * 企业服务总线<br>
 * 负责监控&执行流程和服务，并将相关信息记入日志<br>
 * 接收用户对流程的请求。<br>
 * 接收流程对服务的请求。<br>
 * 监控服务执行的状态，并将结果反馈给流程。
 * 
 * @author 张光磊 2007-6-4
 */
public interface IServiceBus {
	/**
	 * 接受流程的请求，请求执行一个服务
	 * 
	 * @param serviceid
	 *            服务id
	 * @param processid
	 *            流程id
	 * @param message
	 *            消息对象，流程提供经过适配后的对象
	 * @return 返回服务运行状态
	 */
	public ExecuteResult doService(String serviceid, String processid,
			IMessage message);

	/**
	 * 接受 用户的请求执行流程
	 * 
	 * @param processid
	 *            流程ID
	 * @param message
	 *            消息对象，用户提供
	 * @return 返回流程执行结果
	 */
	public ExecuteResult doProcess(String processid, IMessage message);
}
