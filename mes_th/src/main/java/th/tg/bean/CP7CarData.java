package th.tg.bean;

/**
 * QAD车辆总成信息类
 * 
 * @author YuanPeng
 *
 */
public class CP7CarData {

	/**
	 * 车型
	 */
	private String cartype;
	/**
	 * 零件号
	 */
	private String part_no;
	/**
	 * 零件名
	 */
	private String part_name;
	/**
	 * 数量
	 */
	private float num;
	public String getCartype() {
		return cartype;
	}
	public void setCartype(String cartype) {
		this.cartype = cartype;
	}
	public String getPart_no() {
		return part_no;
	}
	public void setPart_no(String part_no) {
		this.part_no = part_no;
	}
	public String getPart_name() {
		return part_name;
	}
	public void setPart_name(String part_name) {
		this.part_name = part_name;
	}
	public float getNum() {
		return num;
	}
	public void setNum(float num) {
		this.num = num;
	}
}
