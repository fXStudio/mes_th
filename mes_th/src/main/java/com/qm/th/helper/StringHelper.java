package com.qm.th.helper;

/**
 * 工具类
 * 
 * @author Administrator
 */
public class StringHelper {
	/**
	 * 去除字符串中的单引号
	 * 
	 * @param s
	 * @return
	 */
	public static String clearSingleQuotationMarksFlaw(String s) {
		return s.replaceAll("'", "''");
	}
}
