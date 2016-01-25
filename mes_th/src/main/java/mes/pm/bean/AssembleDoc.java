package mes.pm.bean;

import java.util.Date;
import java.util.List;
/**
 * 装配指示单
 * 
 * @author YuanPeng
 *
 */
public class AssembleDoc {
	/**
	 * 装配指示单序号
	 */
	private int id;
	/**
	 * 名称
	 */
	private String name; 
	/**
	 * 物料类型标示
	 */
	private String materiel; 
	/**
	 * 装配子件集合
	 */
	private List<AssDocItem> items; 
	/**
	 * 描述
	 */
	private String description; 
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 最后修改时间
	 */
	private Date upDate;
	/**
	 * 创建用户ID
	 */
	private int CreateUID;
	/**
	 * 创建用户名
	 */
	private String createUName;
	/**最后修改用户ID
	 * 
	 */
	private int UpdateUID;//
	/**
	 * 最后修改用户名
	 */
	private String updateUName;//
	/**
	 * BOM序号
	 */
	private int bomId;//
	
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
	public String getMateriel() {
		return materiel;
	}
	public void setMateriel(String materiel) {
		this.materiel = materiel;
	}
	public List<AssDocItem> getItems() {
		return items;
	}
	public void setItems(List<AssDocItem> items) {
		this.items = items;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpDate() {
		return upDate;
	}
	public void setUpDate(Date upDate) {
		this.upDate = upDate;
	}
	public int getCreateUID() {
		return CreateUID;
	}
	public void setCreateUID(int createUID) {
		CreateUID = createUID;
	}
	public int getUpdateUID() {
		return UpdateUID;
	}
	public void setUpdateUID(int updateUID) {
		UpdateUID = updateUID;
	}
	public int getBomId() {
		return bomId;
	}
	public void setBomId(int bomId) {
		this.bomId = bomId;
	}
	public String getCreateUName() {
		return createUName;
	}
	public void setCreateUName(String createUName) {
		this.createUName = createUName;
	}
	public String getUpdateUName() {
		return updateUName;
	}
	public void setUpdateUName(String updateUName) {
		this.updateUName = updateUName;
	}

}
