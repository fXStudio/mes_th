package mes.framework;

/**
 * 流程项
 * 
 * @author 张光磊 2007-6-21
 */
class DefProcessItem implements IProcessItem {
	/**
	 * 异常处理方式
	 */
	private ExceptionDispose exceptionDisposeType;

	/**
	 * 服务别名
	 */
	private String serviceName = "";

	/**
	 * 服务id
	 */
	private String servicdId = "";

	/**
	 * 顺序值
	 */
	private int sort = 0;

	/**
	 * 构造函数
	 * 
	 * @param servicdId
	 *            服务id
	 * @param serviceName
	 *            服务别名
	 * @param sort
	 *            顺序值
	 * @param exceptionDisposeType
	 *            异常处理方式
	 */
	DefProcessItem(String servicdId, String serviceName, int sort,
			ExceptionDispose exceptionDisposeType) {
		super();
		this.servicdId = servicdId;
		this.serviceName = serviceName;
		this.sort = sort;
		this.exceptionDisposeType = exceptionDisposeType;
	}

	/**
	 * @return the exceptionDisposeType
	 */
	public ExceptionDispose getExceptionDisposeType() {
		return exceptionDisposeType;
	}

	public void setExceptionDisposeType(ExceptionDispose exceptionDisposeType) {
		this.exceptionDisposeType = exceptionDisposeType;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName
	 *            the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the servicdId
	 */
	public String getServicdId() {
		return servicdId;
	}

	/**
	 * @param servicdId
	 *            the servicdId to set
	 */
	public void setServicdId(String servicdId) {
		this.servicdId = servicdId;
	}

	/**
	 * @return the sort
	 */
	public int getSort() {
		return sort;
	}

	/**
	 * @param sort
	 *            the sort to set
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}

	
}
