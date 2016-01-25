package mes.pm.bean;

import java.awt.Image;



/**
 * 实体Bean用于工艺说明书的内容信息
 * 
 * @author YuanPeng
 *
 */
public class TechDocItem {
	/**
	 * 序号
	 */
	private int id; 
	/**
	 * 工艺操作说明书号
	 */
	private int TechDocId;
	/**
	 * 输出工艺操作项序号
	 */
	private int code;
	/**
	 * 生产单元号
	 */
	private int produceUnitId; 
	/**
	 * 生产单元名
	 */
	private String prodUnitName;
	/**
	 * 相关单元的具体工艺操作描述
	 */
	private String Content; 
	/**
	 * 工艺操作项文件
	 */
	private TechItemFile techItemFile = null;
	/**
	 * 文件路径
	 */
	private Image itemImage = null;
	
	private String filePathName = null;
	
	public String getFilePathName() {
		return filePathName;
	}
	public void setFilePathName(String filePathName) {
		this.filePathName = filePathName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProduceUnitId() {
		return produceUnitId;
	}
	public void setProduceUnitId(int produceUnitId) {
		this.produceUnitId = produceUnitId;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public int getTechDocId() {
		return TechDocId;
	}
	public void setTechDocId(int TechDocId) {
		this.TechDocId = TechDocId;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getProdUnitName() {
		return prodUnitName;
	}
	public void setProdUnitName(String prodUnitName) {
		this.prodUnitName = prodUnitName;
	}
	public TechItemFile getTechItemFile() {
		return techItemFile;
	}
	public void setTechItemFile(TechItemFile techItemFile) {
		this.techItemFile = techItemFile;
	}
	public Image getItemImage() {
		return itemImage;
	}
	public void setItemImage(Image itemImage) {
		this.itemImage = itemImage;
	}
}
