package mes.framework;

/**
 * 异常处理方式，此类型自己作为自己的工厂
 * 
 * @author 张光磊 2007-6-21
 */
public enum ExceptionDispose {
	/**
	 * 忽略
	 */
	ignore,
	/**
	 * 退出
	 */
	exit,
	/**
	 * 回滚
	 */
	rollback;
	/**
	 * 通过传入的字符串返回对应的处理方式
	 * 
	 * @param string
	 * @return 默认为“退出”
	 */
	public static ExceptionDispose getInstance(String string) {
		if (string.toLowerCase().equals("rollback"))
			return rollback;
		if (string.toLowerCase().equals("iqnore"))
			return ignore;
		if (string.toLowerCase().equals("exit"))
			return exit;
		return exit;
	}
}
