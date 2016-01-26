package com.qm.mes.framework;

import java.util.List;

/**
 * 消息对象，数据载体<br>
 * 包括：<br>
 * 用户提交的请求参数：即业务关键参数。<br>
 * 服务间接输出参数：即服务的输出参数。<br>
 * 其它参数信息：用户信息、连接池名等。<br>
 * 异常列表，记录流程执行过程中出现的异常。
 * 
 * @author 张光磊 2007-6-4
 */
public interface IMessage {
	/**
	 * 获得某一个参数的值，同时从三种信息种查找。<br>
	 * 顺序是：系统参数、用户提交参数、输出参数。
	 * 
	 * @param key
	 * @return
	 */
	public String getUserParameterValue(String key);

	public String getOutputParameterValue(String key);

	public Object getOtherParameter(String key);

	/**
	 * 设置用户提交参数
	 * 
	 * @param key
	 *            参数名
	 * @param value
	 *            值
	 */
	public void setUserParameter(String key, String value);

	/**
	 * 设置间接输出参数
	 * 
	 * @param key
	 *            参数名
	 * @param value
	 *            值
	 */
	public void setOutputParameter(String key, String value);

	/**
	 * 返回异常列表
	 * 
	 * @return
	 */
	public List<ServiceException> getServiceException();

	/**
	 * 添加一个异常对象
	 * 
	 * @param se
	 */
	void addServiceException(ServiceException se);

	/**
	 * 其他参数——用户扩展
	 * 
	 * @return
	 */
	// Map<String, Object> getOtherParameter();
	void setOtherParameter(String key, Object value);

	/**
	 * 从所有值Map中查找 key 的值。
	 * 
	 * @param key
	 *            参数名
	 * @return 找不到返回null。
	 */
	Object getValue(String key);
}
