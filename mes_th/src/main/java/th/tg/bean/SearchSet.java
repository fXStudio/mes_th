package th.tg.bean;

/**
 * 查询设置
 * 
 * @author YuanPeng
 *
 */
public class SearchSet {

	/**
	 * 序号
	 */
	private int id;
	/**
	 * 查询名称
	 */
	private String csearchName;
	/**
	 * 焊总装
	 */
	private String cwa;
	/**
	 * 工厂代码
	 */
	private String cfactory;
	/**
	 * 工厂描述
	 */
	private String cdscFactory;
	/**
	 * 车型代码
	 */
	private String ccarType;
	/**
	 * 车型描述
	 */
	private String cdscCarType;
	/**
	 * 描述
	 */
	private String cremark;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCsearchName() {
		return csearchName;
	}
	public void setCsearchName(String csearchName) {
		this.csearchName = csearchName;
	}
	public String getCwa() {
		return cwa;
	}
	public void setCwa(String cwa) {
		this.cwa = cwa;
	}
	public String getCfactory() {
		return cfactory;
	}
	public void setCfactory(String cfactory) {
		this.cfactory = cfactory;
	}
	public String getCdscFactory() {
		return cdscFactory;
	}
	public void setCdscFactory(String cdscFactory) {
		this.cdscFactory = cdscFactory;
	}
	public String getCcarType() {
		return ccarType;
	}
	public void setCcarType(String ccarType) {
		this.ccarType = ccarType;
	}
	public String getCdscCarType() {
		return cdscCarType;
	}
	public void setCdscCarType(String cdscCarType) {
		this.cdscCarType = cdscCarType;
	}
	public String getCremark() {
		return cremark;
	}
	public void setCremark(String cremark) {
		this.cremark = cremark;
	}
}
