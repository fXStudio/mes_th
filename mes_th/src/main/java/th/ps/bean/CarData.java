package th.ps.bean;

/**
 * 报文数据维护
 * 
 * @author Gaohf
 * @date 2010-4-19
 */
public class CarData {
	/** 主键 */
	private Integer id;

	/** 焊装顺序号 */
	private String cseqno;

	/** 总装顺序号 */
	private String cseqno_a;

	/** VIN码 */
	private String cvincode;

	/** KIN号 */
	private String ccarno;

	/** 车辆类型 */
	private String ccartype;

	/** 焊装上线时间 */
	private String dwbegin;

	/** 总装上线时间 */
	private String dabegin;

	/** CP6上线时间 */
	private String dcp6begin;

	/** 焊装上线次数 */
	private Integer wuptime;

	/** 备注 */
	private String cremark;

	/** 有效时间 */
	private String dtodate;

	/** 总装上线次数 */
	private Integer auptime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCseqno() {
		return cseqno;
	}

	public void setCseqno(String cseqno) {
		this.cseqno = cseqno;
	}

	public String getCseqno_a() {
		return cseqno_a;
	}

	public void setCseqno_a(String cseqno_a) {
		this.cseqno_a = cseqno_a;
	}

	public String getCvincode() {
		return cvincode;
	}

	public void setCvincode(String cvincode) {
		this.cvincode = cvincode;
	}

	public String getCcarno() {
		return ccarno;
	}

	public void setCcarno(String ccarno) {
		this.ccarno = ccarno;
	}

	public String getCcartype() {
		return ccartype;
	}

	public void setCcartype(String ccartype) {
		this.ccartype = ccartype;
	}

	public String getDwbegin() {
		return dwbegin;
	}

	public void setDwbegin(String dwbegin) {
		this.dwbegin = dwbegin;
	}

	public String getDabegin() {
		return dabegin;
	}

	public void setDabegin(String dabegin) {
		this.dabegin = dabegin;
	}

	public String getDcp6begin() {
		return dcp6begin;
	}

	public void setDcp6begin(String dcp6begin) {
		this.dcp6begin = dcp6begin;
	}

	public Integer getWuptime() {
		return wuptime;
	}

	public void setWuptime(Integer wuptime) {
		this.wuptime = wuptime;
	}

	public Integer getAuptime() {
		return auptime;
	}

	public void setAuptime(Integer auptime) {
		this.auptime = auptime;
	}

	public String getCremark() {
		return cremark;
	}

	public void setCremark(String cremark) {
		this.cremark = cremark;
	}

	public String getDtodate() {
		return dtodate;
	}

	public void setDtodate(String dtodate) {
		this.dtodate = dtodate;
	}
}
