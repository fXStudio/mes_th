/**
 * 
 */
package mes.beans;

/**
 * @author lida
 * 
 */
public class Role {
	private String role_no = null;

	private String role_name = null;
	
	private String cenabled=null;

	public String getCenabled() {
		return cenabled;
	}

	public void setCenabled(String cenabled) {
		this.cenabled = cenabled;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getRole_no() {
		return role_no;
	}

	public void setRole_no(String role_no) {
		this.role_no = role_no;
	}

}
