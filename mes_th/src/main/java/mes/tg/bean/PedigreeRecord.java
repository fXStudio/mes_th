package mes.tg.bean;

public class PedigreeRecord {
	private int id;//序号
	private int gatherRecordId;//过点表序号
	private String materielName;//物料名称
	private String materielValue;//物料值
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGatherRecordId() {
		return gatherRecordId;
	}
	public void setGatherRecordId(int gatherRecordId) {
		this.gatherRecordId = gatherRecordId;
	}
	public String getMaterielValue() {
		return materielValue;
	}
	public void setMaterielValue(String materielValue) {
		this.materielValue = materielValue;
	}
	public String getMaterielName() {
		return materielName;
	}
	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}
}
