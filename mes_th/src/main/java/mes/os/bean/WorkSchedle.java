package mes.os.bean;

public class WorkSchedle {
	/**
	  * 序号
	  */
	private int id;  
	/**
	  * 生产单元号
	  */
	private int produnitid;  
	/**
	  *班次
	  */
	private String workOrder;  
	/**
	  *开工时间
	  */
	private String workSchedle;  
	/**
	  *提前期
	  */
	private String advanceTime;  
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProdunitid() {
		return produnitid;
	}
	public void setProdunitid(int produnitid) {
		this.produnitid = produnitid;
	}
	public String getWorkOrder() {
		return workOrder;
	}
	public void setWorkOrder(String workOrder) {
		this.workOrder = workOrder;
	}
	public String getWorkSchedle() {
		return workSchedle;
	}
	public void setWorkSchedle(String workSchedle) {
		this.workSchedle = workSchedle;
	}
	public String getAdvanceTime() {
		return advanceTime;
	}
	public void setAdvanceTime(String advanceTime) {
		this.advanceTime = advanceTime;
	}
	
}
