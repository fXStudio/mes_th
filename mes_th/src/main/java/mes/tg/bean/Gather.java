package mes.tg.bean;

public class Gather {
	private int id;//序号
	private String name;//采集点名称
	private String desc;//采集点描述
	private int produnitId;//生产单元id，系统外部取得
	private int materielruleId;//主物料验证规则
	//启动状态规则验证
	private int startgo;
	//强制启动状态规则验证
	private int  compel;
	
	public int getCompel(){
		return compel;
	}
	
	public void setCompel(int compel){
		this.compel=compel;
	}
	
	public int getStartgo(){
		return startgo;
		
	}
	public void setStartgo(int startgo){
		 this.startgo=startgo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getProdunitId() {
		return produnitId;
	}
	public void setProdunitId(int produnitId) {
		this.produnitId = produnitId;
	}
	public int getMaterielruleId() {
		return materielruleId;
	}
	public void setMaterielruleId(int materielruleId) {
		this.materielruleId = materielruleId;
	}
}
