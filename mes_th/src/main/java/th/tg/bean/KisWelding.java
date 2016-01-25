package th.tg.bean;

/**
 * 焊装KIS查询
 * 
 * @author YuanPeng
 *
 */
public class KisWelding {
	
	/**
	 * 顺序号
	 */
	private String seq;
	/**
	 * VIN
	 */
	private String vin;
	/**
	 * KIN
	 */
	private String kin;
	/**
	 * 焊装上线时间
	 */
	private String dWBegin;
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getKin() {
		return kin;
	}
	public void setKin(String kin) {
		this.kin = kin;
	}
	public String getDWBegin() {
		return dWBegin;
	}
	public void setDWBegin(String begin) {
		dWBegin = begin;
	}
	
}
