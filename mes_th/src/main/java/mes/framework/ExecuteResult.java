package mes.framework;

/**
 * 运行结果（状态）
 * 
 * @author 张光磊 2007-6-21
 */
public enum ExecuteResult {
	/**
	 * 成功
	 */
	sucess,
	/**
	 * 失败
	 */
	fail;

	public String toString() {
		switch (this) {
		case sucess:
			return "成功";
		case fail:
			return "失败";
		}
		return "成功";
	}
}
