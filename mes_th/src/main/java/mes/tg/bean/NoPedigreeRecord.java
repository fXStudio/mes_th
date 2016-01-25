package mes.tg.bean;

public class NoPedigreeRecord {
	//非谱系记录的id
	private int id;
	//过点记录的id
	private int gatherRecordId;
	//非谱系扩展属性值
	private String value;
	//非谱系扩展属性名
	private String  gatheritemextname;
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getGatheritemextname() {
		return gatheritemextname;
	}
	public void setGatheritemextname(String gatheritemextname) {
		this.gatheritemextname = gatheritemextname;
	}
	
	

}
