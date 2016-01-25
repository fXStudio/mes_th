package mes.pm.bean;

/**
 * Bom实体Bean
 * 
 * @author YuanPeng
 *
 */
public class Bom implements java.io.Serializable {
	/**
	 * 区分，序列化版本
	 */
	private static final long serialVersionUID = 7052467200620035657L;
	/**
	 * Bom序号
	 */
	private int id;
	/**
	 * 组件标示
	 */
	private String component;
	/**
	 * 上级序号标示
	 */
	private int parentid;
	/**
	 * 描述
	 */
	private String discription;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 输出序号
	 */
	private int code;
	/**
	 * 数量
	 */
	private int count;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
