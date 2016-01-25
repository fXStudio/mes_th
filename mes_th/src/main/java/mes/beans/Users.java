/**
 * 
 */
package mes.beans;

/**
 * @author Administrator
 * 
 */
public class Users {
	// nusrno,cusrname,nroleno,cenabled
	// NROLENO,CROLENAME,CRANK,CPOWERSTRING,CWELCOMEPAGE,"
	// + "NLASTUPDATEUSER,DLASTUPDATETIME,CNOTE,CENABLED
	private String userno = null;

	private String username = null;

	private String roleno = null;

	private String user_enabled = null;

	private String rolename = null;

	//此处开始
	private String password = null;
	private String employeeid = null;
	private String state = null;
	private String lastupdateuser = null;
	private String lastupdatetime = null;
	private String note = null;
	//就到这里吧

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLastupdateuser() {
		return lastupdateuser;
	}

	public void setLastupdateuser(String lastupdateuser) {
		this.lastupdateuser = lastupdateuser;
	}

	public String getLastupdatetime() {
		return lastupdatetime;
	}

	public void setLastupdatetime(String lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRoleno() {
		return roleno;
	}

	public void setRoleno(String roleno) {
		this.roleno = roleno;
	}

	public String getUser_enabled() {
		return user_enabled;
	}

	public void setUser_enabled(String user_enabled) {
		this.user_enabled = user_enabled;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

}
