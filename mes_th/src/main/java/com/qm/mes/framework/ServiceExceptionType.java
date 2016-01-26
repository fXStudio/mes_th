package com.qm.mes.framework;

public enum ServiceExceptionType {
	/**
	 * 服务丢失
	 */
	SERVICELOST,
	/**
	 * 数据库错误
	 */
	DATABASEERROR,
	
	/**
	 * 参数错误
	 */
	PARAMETERERROR,
	/**
	 * 缺少关键参数
	 */
	PARAMETERLOST,
	/**
	 * 未知数据库类型
	 */
	UNKNOWNDATABASETYPE,
	/**
	 * 未知错误
	 */
	UNKNOWN;

	public String toString() {
		switch (this) {
		case SERVICELOST:
			return "服务丢失";
		case DATABASEERROR:
			return "数据库错误";
		case PARAMETERLOST:
			return "缺少关键参数";
		case UNKNOWN:
			return "未知错误";
		case UNKNOWNDATABASETYPE:
			return "未知数据库类型";
		case PARAMETERERROR:
			return "参数错误";
		default:
			return "";
		}
	}
}
