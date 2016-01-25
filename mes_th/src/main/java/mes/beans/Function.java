/**
 * 
 */
package mes.beans;

/**
 * @author lida
 * 
 */
public class Function {
	// nfunctionid,CFUNCTIONNAME,cnodetype,cstate,cenabled
	private String functionid = null;

	private String functionname = null;

	private String nodetype = null;

	private String state = null;

	private String enabled = null;

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getFunctionid() {
		return functionid;
	}

	public void setFunctionid(String functionid) {
		this.functionid = functionid;
	}

	public String getFunctionname() {
		return functionname;
	}

	public void setFunctionname(String functionname) {
		this.functionname = functionname;
	}

	public String getNodetype() {
		return nodetype;
	}

	public void setNodetype(String nodetype) {
		this.nodetype = nodetype;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
