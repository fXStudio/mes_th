package th.tg.bean;

/**
 * 总装查询类
 * 
 * @author YuanPeng
 *
 */
public class AssemblySearch {

	/**
	 * 焊装顺序号
	 */
	private String seq_W;
	/**
	 * 顺序号
	 */
	private String seq;
	/**
	 * VIN(底盘号)
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
	/**
	 * 总装上线时间
	 */
	private String dABegin;
	/**
	 * CP6上线时间
	 */
	private String dCp6Begin;
	/**
	 * 焊装报文名称
	 */
	private String cfilename_w;
	/**
	 * 总装报文名称
	 */
	private String cfilename_a;
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
	public String getDABegin() {
		return dABegin;
	}
	public void setDABegin(String begin) {
		dABegin = begin;
	}
	public String getDCp6Begin() {
		return dCp6Begin;
	}
	public void setDCp6Begin(String cp6Begin) {
		dCp6Begin = cp6Begin;
	}
	public String getSeq_W() {
		return seq_W;
	}
	public void setSeq_W(String seq_W) {
		this.seq_W = seq_W;
	}
	public String getCfilename_w() {
		return cfilename_w;
	}
	public void setCfilename_w(String cfilename_w) {
		this.cfilename_w = cfilename_w;
	}
	public String getCfilename_a() {
		return cfilename_a;
	}
	public void setCfilename_a(String cfilename_a) {
		this.cfilename_a = cfilename_a;
	}
}
