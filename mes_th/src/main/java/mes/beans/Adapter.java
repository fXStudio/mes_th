/**
 * 
 */
package mes.beans;

/**
 * @author lida
 * 
 */
public class Adapter {
	private String processid = null;

	private String processname = null;

	private String serverid = null;

	private String servername = null;

	private String serveraliasname = null;

	private String parametername = null;

	public String getParametername() {
		return parametername;
	}

	public void setParametername(String parametername) {
		this.parametername = parametername;
	}

	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProcessname() {
		return processname;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	public String getServeraliasname() {
		return serveraliasname;
	}

	public void setServeraliasname(String serveraliasname) {
		this.serveraliasname = serveraliasname;
	}

	public String getServerid() {
		return serverid;
	}

	public void setServerid(String serverid) {
		this.serverid = serverid;
	}

	public String getServername() {
		return servername;
	}

	public void setServername(String servername) {
		this.servername = servername;
	}
}
