package com.qm.mes.framework;

/**
 * 消息适配器
 * 
 * @author 张光磊 2007-6-21
 */
public interface IMessageAdapter extends IMessage {
	/**
	 * @param key
	 *            服务输入项名
	 * @return 获得适配后的name
	 */
	public String getAdapterName(String key);

	/**
	 * @return 返回流程id
	 */
	String getProcessid();

	/**
	 * @return 返回流程别名
	 */
	String getServiceName();

	/**
	 * @param m
	 *            设置适配器的数据源
	 */
	void setSource(IMessage m);

}
