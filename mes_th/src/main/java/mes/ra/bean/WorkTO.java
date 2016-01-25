package mes.ra.bean;

public class WorkTO {
	/**
	 * 序号
	 */
	private int id;  
	/**
	 * 生产单元号
	 */
	private int produnitid;  
	/**
	 * 班次
	 */
	private String workOrder; 

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
}