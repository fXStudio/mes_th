package mes.pm.bean;
/**
 * 实体Bean用于封装设备类别的实体信息
 * @author Xujia
 *
 */
public class DeviceType {

	/**
	 * 序号 
	 */
	private int id;
	/**
	 * 类别名称
	 */
	private String name; 
	
	
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
	
	
}
