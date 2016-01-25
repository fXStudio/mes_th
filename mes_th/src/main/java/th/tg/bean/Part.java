package th.tg.bean;

/**
 * 总成类
 * 
 * @author YuanPeng
 *
 */
public class Part {

	/**
	 * 序号
	 */
	private int id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 号
	 */
	private String code;
	/**
	 * 数量
	 */
	private int num;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
