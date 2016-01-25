package th.tg.bean;


/**
 * 成都车
 * 
 * @author YuanPeng
 *
 */
public class ChengduCar {

	/**
	 * KIN号
	 */
	private String cCarNo;
	/**
	 * VIN
	 */
	private String cVinCode;
	/**
	 * 焊装上线时间
	 */
	private String dWBegin;
	public String getCCarNo() {
		return cCarNo;
	}
	public void setCCarNo(String carNo) {
		cCarNo = carNo;
	}
	public String getCVinCode() {
		return cVinCode;
	}
	public void setCVinCode(String vinCode) {
		cVinCode = vinCode;
	}
	public String getDWBegin() {
		return dWBegin;
	}
	public void setDWBegin(String begin) {
		dWBegin = begin;
	}
}
