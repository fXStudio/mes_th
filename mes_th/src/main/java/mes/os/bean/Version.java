package mes.os.bean;
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
	  * 版本创建人
	  */
	private String user;
	/**
	  * 版本号
	  */
	private String versionCode;
	/**
	 * 
	 * @return
	 */
	private String description;
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
