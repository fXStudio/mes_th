package th.tg.bean;

/**
 * 焊装查询
 * 
 * @author YuanPeng
 *
 */
public class WeldingSearch {

	/**
	 * 顺序号
	 */
	private String sequence;
	/**
	 * KIN号
	 */
	private String kin;
	/**
	 * 零件号
	 */
	private String part_no;
	/**
	 * 数量
	 */
	private int num;
	public String getPart_no() {
		return part_no;
	}
	public void setPart_no(String part_no) {
		this.part_no = part_no;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getKin() {
		return kin;
	}
	public void setKin(String kin) {
		this.kin = kin;
	}
}
