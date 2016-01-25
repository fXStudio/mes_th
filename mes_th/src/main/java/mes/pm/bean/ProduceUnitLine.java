package mes.pm.bean;

import java.util.List;
import mes.pm.bean.ProLineItem;
/**
 * 生产单元线性配置
 * 
 * @author YuanPeng
 *
 */
public class ProduceUnitLine {
	/**
	 * 序号
	 */
	private int id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String Description;
	/**
	 * 装配子件集合
	 */
	private List<ProLineItem> items; 
	
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
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public List<ProLineItem> getItems() {
		return items;
	}
	public void setItems(List<ProLineItem> items) {
		this.items = items;
	}
}
