package th.fx.bean;

/**
 * 配置单实体类
 * 
 * @author Ajaxfan
 */
public class COrderEntity {
	/** 组编号 */
	private String groupId = "";
	/** 描述 */
	private String descript = "";
	/** 打印次数 */
	private int perTimeCount;
	/** 零件数量 */
	private int tFassCount;
	/** 打印记录编号 */
	private String printSetId = "";
	/** 工厂编号 */
	private String factoryNo = "";
	/** 车型 */
	private String carType = "";
	/** 打印组 */
	private int printRadio;
	/** 自动打印标志 */
	private String auto = "";
	/** 页码 */
	private int pages;
	/** 最后一个VIN号 */
	private String lastVin = "";
	/** 总装上线时间 */
	private String dabegin = "";
	/** 序列号 */
	private String seq_a = "";
	
	private String lasttime;

	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

	private boolean isContinue = true;

	private int partCount;
	private int perTimeRow;
	private int minPartCount;
	private String maxCarno = "";
	private String openApp = "";
	private String cvinrule = "";

	public int getMinPartCount() {
		return minPartCount;
	}

	public void setMinPartCount(int minPartCount) {
		this.minPartCount = minPartCount;
	}

	public String getMaxCarno() {
		return maxCarno;
	}

	public void setMaxCarno(String maxCarno) {
		this.maxCarno = maxCarno;
	}

	public int getPartCount() {
		return partCount;
	}

	public void setPartCount(int partCount) {
		this.partCount = partCount;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public Integer getPerTimeCount() {
		return perTimeCount;
	}

	public void setPerTimeCount(Integer perTimeCount) {
		this.perTimeCount = perTimeCount;
	}

	public Integer getTFassCount() {
		return tFassCount;
	}

	public void setTFassCount(Integer fassCount) {
		tFassCount = fassCount;
	}

	public String getPrintSetId() {
		return printSetId;
	}

	public void setPrintSetId(String printSetId) {
		this.printSetId = printSetId;
	}

	public String getFactoryNo() {
		return factoryNo;
	}

	public void setFactoryNo(String factoryNo) {
		this.factoryNo = factoryNo;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public int getPrintRadio() {
		return printRadio;
	}

	public void setPrintRadio(int printRadio) {
		this.printRadio = printRadio;
	}

	public String getAuto() {
		return auto;
	}

	public void setAuto(String auto) {
		this.auto = auto;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public String getLastVin() {
		return lastVin;
	}

	public void setLastVin(String lastVin) {
		this.lastVin = lastVin;
	}

	public String getDabegin() {
		return dabegin;
	}

	public void setDabegin(String dabegin) {
		this.dabegin = dabegin;
	}

	public String getSeq_a() {
		return seq_a;
	}

	public void setSeq_a(String seq_a) {
		this.seq_a = seq_a;
	}

	public boolean isContinue() {
		return isContinue;
	}

	public void setContinue(boolean isContinue) {
		this.isContinue = isContinue;
	}

	public String getOpenApp() {
		return openApp;
	}

	public void setOpenApp(String openApp) {
		this.openApp = openApp;
	}

	public int getPerTimeRow() {
		return perTimeRow;
	}

	public void setPerTimeRow(int perTimeRow) {
		this.perTimeRow = perTimeRow;
	}

	public String getCvinrule() {
		return cvinrule;
	}

	public void setCvinrule(String cvinrule) {
		this.cvinrule = cvinrule;
	}
}
