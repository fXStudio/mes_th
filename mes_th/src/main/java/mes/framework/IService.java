package mes.framework;

/**
 * 企业服务 系统的原子业务。<br>
 * 对应一个实现类名和三个执行方法。
 * <p>
 * 
 * @author 张光磊 2007-6-4
 */
public interface IService extends IElement {

	public String getClassName();

	void setClassName(String classname);

	/**
	 * 执行流程
	 * 
	 * @param message
	 *            适配后的消息对象
	 * @param processid
	 *            流程id
	 * @return 返回执行状态
	 */
	public ExecuteResult doService(IMessage message, String processid);

	/**
	 * 回退操作
	 * 
	 * @param message
	 *            适配后的消息对象
	 * @param processid
	 *            流畅id
	 * @return 返回执行状态
	 */
	public ExecuteResult undoService(IMessage message, String processid);

	/**
	 * 先undoService再doService
	 * 
	 * @param message
	 * @return 执行失败返回false。
	 */
	public ExecuteResult redoService(IMessage message, String processid);

}
