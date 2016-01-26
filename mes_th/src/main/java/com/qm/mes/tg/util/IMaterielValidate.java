package com.qm.mes.tg.util;

/**
 * 物料验证规则接口，具体验证字符串需要实现他的validate方法
 * 
 * @author Administrator
 * 
 */
public interface IMaterielValidate {
	// 字符串验证方法
	boolean validate(String str);
}
