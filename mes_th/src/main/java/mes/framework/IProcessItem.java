package mes.framework;

/**
 * 流程项
 * 
 * @author 张光磊 2007-6-21
 */
public interface IProcessItem {

	/**
	 * @return 返回流程针对此项的异常处理方式
	 */
	ExceptionDispose getExceptionDisposeType();

	/**
	 * @return 返回对应的服务别名
	 */
	String getServiceName();

	/**
	 * @return 返回对应的服务id
	 */
	String getServicdId();

	/**
	 * @return 返回顺序值
	 */
	int getSort();

}
