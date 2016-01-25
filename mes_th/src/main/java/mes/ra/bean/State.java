package mes.ra.bean;

public class State {
	/**
	 * 序号
	 */
	private int id;
	/**
	 * 状态名称
	 */
	private String stateName;
	/**
	 * 样式代码
	 */
	private String style;
	/**
	 * 删除标识
	 */
	private int delete;
	/**
	 * 样式描述
	 */
	private String styledesc; 
	
	/**
	 * @return styledesc
	 */
	public String getStyledesc() {
		return styledesc;
	}
	/**
	 * @param styledesc 要设置的 styledesc
	 */
	public void setStyledesc(String styledesc) {
		this.styledesc = styledesc;
	}
	/**
	 * @return delete
	 */
	public int getDelete() {
		return delete;
	}
	/**
	 * @param delete 要设置的 delete
	 */
	public void setDelete(int delete) {
		this.delete = delete;
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
	 * @return stateName
	 */
	public String getStateName() {
		return stateName;
	}
	/**
	 * @param stateName 要设置的 stateName
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	/**
	 * @return style
	 */
	public String getStyle() {
		return style;
	}
	/**
	 * @param style 要设置的 style
	 */
	public void setStyle(String style) {
		this.style = style;
	}

}
