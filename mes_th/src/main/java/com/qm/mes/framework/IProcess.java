package com.qm.mes.framework;

/**
 * 流程
 * 
 * @author 张光磊 2007-6-5
 */
public interface IProcess extends IElement {

	/**
	 * 执行流程
	 * 
	 * @param message
	 *            用户提供的参数
	 * @return 返回流程执性状态
	 */
	public ExecuteResult doProcess(IMessage message);

	/**
	 * 添加流程项，这里系统将按照用户添加的顺序执行服务，而不会再次排序。
	 * 
	 * @param pi
	 *            流程项对象
	 */
	void addProcessItem(IProcessItem pi);



}
