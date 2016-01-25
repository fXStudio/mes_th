package mes.ra.bean;
import java.util.Date;
public class Version {
	/**
	 * 序号
	 */
	private int id;
	/**
	 * 生成时间
	 */
	private Date versionDatime;
	/**
	 * 版本创建用户
	 */
	private String user;
	/**
	 * 生产单元
	 */
	private int produnitid;
	/**
	 *版本号
	 */
	private String versionCode;
	/**
	 * 删除标识
	 */
	private int Int_delete;
	/**
	 * 备注
	 */
	private String description;
	
	public int getInt_delete() {
		return Int_delete;
	}
	public void setInt_delete(int int_delete) {
		Int_delete = int_delete;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getVersionDatime() {
		return versionDatime;
	}
	public void setVersionDatime(Date versionDatime) {
		this.versionDatime = versionDatime;
	}
	public String getUser() {
		return user;
	}
	public int getProdunitid() {
		return produnitid;
	}
	public void setProdunitid(int produnitid) {
		this.produnitid = produnitid;
	}
	public void setUser(String user) {
		this.user = user;
	}

	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
