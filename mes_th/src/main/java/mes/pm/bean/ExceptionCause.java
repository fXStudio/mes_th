package mes.pm.bean;
/**
 * 异常原因
 * @author Xujia
 *
 */
public class ExceptionCause {

	/**
	 * 序号
	 */
	private int id; 
	/**
	 * 名称
	 */
	private String name; 
	/**
	 *  状态（使用/停用）
	 */
	private int state;
	
	
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
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name 要设置的 name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return state
	 */
	public int getState() {
		return state;
	}
	/**
	 * @param state 要设置的 state
	 */
	public void setState(int state) {
		this.state = state;
	}
	
	
	

}
