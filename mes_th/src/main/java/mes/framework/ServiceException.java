package mes.framework;

import java.util.Date;

/**
 * 服务异常类，用于存储服务运行过程中出现的异常情况及异常描述信息。
 * 
 * @author 张光磊 2007-6-21
 */
public final class ServiceException extends java.lang.Throwable {

	private static final long serialVersionUID = -1688501331104688624L;

	/**
	 * 异常的描述信息
	 */
	private String descr = null;

	/**
	 * 异常类型
	 */
	private ServiceExceptionType type = ServiceExceptionType.UNKNOWN;

	/**
	 * 异常对象
	 */
	private Exception source = null;

	/**
	 * 对应服务
	 */
	private String es = null;

	/**
	 * 对应流程
	 */
	private String sp = null;

	/**
	 * 触发时间
	 */
	private Date date = null;

	public ServiceException(ServiceExceptionType set, String descr, String es,
			String sp, Date d, Exception s) {

		source = s;
		this.descr = descr;
		this.type = set;
		this.es = es;
		this.sp = sp;
		this.date = d;
	}

	/**
	 * 返回错误类型
	 * 
	 * @return 返回错误类型
	 */
	public ServiceExceptionType getServiceExceptionType() {
		return type;
	}

	/**
	 * 返回错误对象
	 * 
	 * @return 返回错误对象
	 */
	public Exception getSource() {
		return source;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	public String getEs() {
		return es;
	}

	public String getSp() {
		return sp;
	}

	/**
	 * @return the type
	 */
	public ServiceExceptionType getType() {
		return type;
	}

	/**
	 * @return the descr
	 */
	public String getDescr() {
		return descr == null ? "" : descr;
	}

	/**
	 * @param descr
	 *            the descr to set
	 */
	public void setDescr(String descr) {
		this.descr = descr;
	}

}
