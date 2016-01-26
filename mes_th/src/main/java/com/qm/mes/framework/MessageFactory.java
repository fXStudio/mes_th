package com.qm.mes.framework;

/**
 * 消息工厂
 * 
 * @author 张光磊 2007-6-21
 */
public final class MessageFactory {
	public static IMessage createInstance() {
		return new DefMessage();
	}
}
