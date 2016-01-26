package com.qm.mes.framework;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 消息适配器，对消息进行包装<br>
 * 针对Service建立的<br>
 * 向service提供通过映射规则后的参数信息。 并针对service撤销相关功能。
 * 
 * @author 张光磊 2007-6-7
 */
class DefMessageAdapter implements IMessageAdapter {

	/**
	 * 服务输入名字和消息中名字的对应关系
	 */
	private Map<String, String> adapterNames = new Hashtable<String, String>();

	/**
	 * 适配器的流程标识
	 */
	private String processid = "";

	/**
	 * 适配器对应的流程中的服务的别名
	 */
	private String serviceName = "";

	/**
	 * 源消息对象
	 */
	private IMessage source = null;

	public DefMessageAdapter(String processid, String serviceName) {
		super();
		this.processid = processid;
		this.serviceName = serviceName;
	}

	public String getAdapterName(String key) {
		return adapterNames.get(key);
	}

	public String getProcessid() {
		return processid;
	}

	void setAdapterName(String key, String value) {
		adapterNames.put(key, value);
	}

	public void setSource(IMessage m) {
		source = m;
	}

	public void addServiceException(ServiceException se) {
		source.addServiceException(se);
	}

	public List<ServiceException> getServiceException() {
		return source.getServiceException();
	}

	/**
	 * @deprecated 针对服务不提供设置用户参数的功能。
	 */
	public void setUserParameter(String key, String value) {
	}

	public String getUserParameterValue(String key) {
		String sname = this.getAdapterName(key);
		Object o = null;
		if (sname != null)// 适配成功
			o = source.getValue(sname);
		else
			o = source.getValue(key);
		if (o != null && o instanceof String)
			return (String) o;
		return null;
	}

	public void setOutputParameter(String key, String value) {
		// 设置输出参数的时候加服务别名的前缀
		source.setOutputParameter(serviceName + "." + key, value);
	}

	public String getOutputParameterValue(String key) {
		return getUserParameterValue(key);
	}

	public void setOtherParameter(String key, Object value) {
		// 服务自己输出的其他对象应该应用命名规则：服务别名.键名
		source.setOtherParameter(serviceName + "." + key, value);
	}

	public Object getOtherParameter(String key) {
		// otherParameter 是没有映射问题的。
		// 若是服务自己输出的那么名为（serviceName + "." + key）
		// 否则，若是用户输入的那么名为（key）
		String skey = serviceName + "." + key;
		return source.getValue(skey) != null ? source.getValue(skey) : source
				.getValue(key);
	}

	public Object getValue(String key) {
		return source.getValue(key);
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}
}
