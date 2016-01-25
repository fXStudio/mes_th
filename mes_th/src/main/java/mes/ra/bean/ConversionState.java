package mes.ra.bean;


public class ConversionState {
	/**
	 * 序号
	 */
	private int id;
	/**
	 * 原始状态
	 */
	private int fromstate;
	/**
	 * 跳转状态
	 */
	private int tostate;
	/**
	 * 描述信息
	 */
	private String desc;
	
	/**
	 * @return desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc 要设置的 desc
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * @return fromstate
	 */
	public int getFromstate() {
		return fromstate;
	}
	/**
	 * @param fromstate 要设置的 fromstate
	 */
	public void setFromstate(int fromstate) {
		this.fromstate = fromstate;
	}
	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id 要设置的 id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return tostate
	 */
	public int getTostate() {
		return tostate;
	}
	/**
	 * @param tostate 要设置的 tostate
	 */
	public void setTostate(int tostate) {
		this.tostate = tostate;
	}
	
}
