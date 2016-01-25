package mes.os.bean;
import java.util.*;
/**
 * 
 * @author  谢静天 2009-5-13
 *
 */
public class MPSPlan {
	/**
	  * 序号
	  */
	private  int id ; 
	/**
	  * 开始日期
	  */
	private  Date startDate; 
	/**
	  * MPS单位（日、周、月、旬、季）
	  */
	private  String mpsUnit ;  
	/**
	  * 物料名
	  */
	private String materielName; 
	/**
	  *计划数量
	  */
	private  int planAmount; 
	/**
	  *预计库存
	  */
	private int intendStorage;  
	/**
	  *计划期类型（需求、计划、预测）
	  */
	private String planType; 
	/**
	  *版本
	  */
	private String version; 
	/**
	  *制定人
	  */
	private String userName; 
	/**
	  *合同号
	  */
	private String contractCode; 
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getMpsUnit() {
		return mpsUnit;
	}
	public void setMpsUnit(String mpsUnit) {
		this.mpsUnit = mpsUnit;
	}
	public String getMaterielName() {
		return materielName;
	}
	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}
	public int getPlanAmount() {
		return planAmount;
	}
	public void setPlanAmount(int planAmount) {
		this.planAmount = planAmount;
	}
	public int getIntendStorage() {
		return intendStorage;
	}
	public void setIntendStorage(int intendStorage) {
		this.intendStorage = intendStorage;
	}
	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}


}
