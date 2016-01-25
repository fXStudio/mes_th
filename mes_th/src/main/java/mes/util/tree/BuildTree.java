package mes.util.tree;

import java.sql.Connection;
import java.util.Vector;

public class BuildTree {
	// 功能树应用的
	private String powerstring = "";
	private DataServer_UserManage ds = null;

	// 角色树应用的
	private String sign = "";// 操作标志查看look 插入insert 更新update
	private String htmlcode = "";
	private String rank = "";
	private String roleno = "";

	// 角色树的方法
	public String getHtmlCode() {
		return this.htmlcode;
	}

	// 角色树的构造方法
	public BuildTree(Connection con, String userid, String sign,
			String lookroleno) throws Exception {
		// String roleno = "";
		if (sign.trim().equals("insert") || sign.trim().equals("update")
				|| sign.trim().equals("look")) {
			this.sign = sign;
		} else {
			throw new Exception("sign 操作标记有误！");
		}

		this.ds = new DataServer_UserManage(con);
		// roleno = ds.getRoleNo(userid);
		this.roleno = ds.getRoleNo(userid);

		if (roleno == null || roleno.trim().equals(""))
			throw new Exception("未找到用户的角色号！");
		// rank = ds.getRank(roleno);
		this.rank = ds.getRank(this.roleno);
		if (!sign.equals("insert")) {
			if (lookroleno == null || lookroleno.trim().equals("")) {
				throw new Exception("参数为空！");
			} else {
				this.powerstring = ds.getPowerString(lookroleno);
				if (powerstring == null || powerstring.trim().equals(""))
					throw new Exception("角色不存在或权限串为空！");
			}
		}
		build();
	}

	// 功能树用的构造方法
	public BuildTree(Connection con, String powerstring) throws Exception {
		this.powerstring = powerstring;
		ds = new DataServer_UserManage(con);
	}

	// 功能树用的方法, 根据权限串，检查用户是否有其功能
	private boolean checkPower(String functionid) throws Exception {
		int int_functionid = Integer.parseInt(functionid);
		if (powerstring.substring(int_functionid - 1, int_functionid).equals(
				"1")) {
			return true;
		} else {
			return false;
		}
	}

	// 功能树用的方法
	public String expand(Function upfunction) throws Exception {
		String code = "";
		if (upfunction.getNodeType().equals("3")) {
			throw new Exception("叶节点" + upfunction.getFunctionName() + "不能再分解！");
		} else {
			// 父元素名
			String elementname_fathernode = "";
			String elementname = "";
			elementname_fathernode = "node" + upfunction.getFunctionID() + "";
			// 获取其所有子节点和叶
			Vector<?> v_function = ds.getAllSubVFunction(upfunction
					.getFunctionID());
			for (int i = 0; i < v_function.size(); i++) {
				Function f = (Function) v_function.elementAt(i);
				if (f.getNodeType().equals("3")) {
					if (checkPower(f.getFunctionID())) {
						if (f.getState().equals("1")) {
							String url = f.getURL();
							if (url == null)
								url = "";
							// sb.append(elementname_fathernode).append(".addLink('"+url+"','"+f.getFunctionName()+"')").append("\n");
							code += elementname_fathernode + ".addNode('"
									+ f.getFunctionName() + "','" + url
									+ "','link');" + "\n";
						}
					}
				} else if (f.getNodeType().equals("2")) {
					elementname = "node" + f.getFunctionID() + "";
					// sb.append("var
					// ").append(elementname).append("=").append(elementname_fathernode).append(".add('"+f.getFunctionName()+"')").append("\n");
					// expand(sb,f);
					String s = expand(f);
					if (s == null || s.trim().equals(""))
						continue;
					code += "var " + elementname + " ="
							+ elementname_fathernode + ".addNode('"
							+ f.getFunctionName() + "',null,'sun');" + s;
				} else {
					throw new Exception(upfunction.getFunctionName()
							+ "节点类型数据有误！");
				}
			}
			return code;
		}
	}

	// 终端扫描用到,返回功能串
	public String expand_terminal(Function upfunction) throws Exception {
		String code = "";

		if (upfunction.getNodeType().equals("3")) {
			throw new Exception("叶节点" + upfunction.getFunctionName() + "不能再分解！");
		} else {
			// 父元素名
			String elementname_fathernode = "";
			String elementname = "";
			elementname_fathernode = "node" + upfunction.getFunctionID() + "";
			// 获取其所有子节点和叶
			Vector<?> v_function = ds.getAllSubVFunction(upfunction
					.getFunctionID());
			for (int i = 0; i < v_function.size(); i++) {
				Function f = (Function) v_function.elementAt(i);
				if (f.getNodeType().equals("3")) {
					if (checkPower(f.getFunctionID())) {
						if (f.getState().equals("1")) {
							String url = f.getURL();
							if (url == null)
								url = "";
							// sb.append(elementname_fathernode).append(".addLink('"+url+"','"+f.getFunctionName()+"')").append("\n");
							code += "'" + f.getFunctionName() + ":" + url + "'"
									+ ",";
						}
					}
				} else if (f.getNodeType().equals("2")) {
					elementname = "node" + f.getFunctionID() + "";
					// sb.append("var
					// ").append(elementname).append("=").append(elementname_fathernode).append(".add('"+f.getFunctionName()+"')").append("\n");
					// expand(sb,f);
					String s = expand_terminal(f);
					if (s == null || s.trim().equals(""))
						continue;
					code += s;
				} else {
					throw new Exception(upfunction.getFunctionName()
							+ "节点类型数据有误！");
				}
			}
			return code;
		}
	}

	// 角色树的方法
	private void build() throws Exception {
		StringBuffer sb = new StringBuffer("");
		Function root = ds.getFuncitonInfo("1");
		// 显示根节点
		sb.append(root.getFunctionName()).append("<br>").append("\n");
		sb.append(expandRole(root, 1));

		this.htmlcode = sb.toString();
	}

	// 角色树的方法
	public String expandRole(Function upfunction, int level) throws Exception {
		String code = "";
		if (upfunction.getNodeType().equals("3")) {
			throw new Exception("叶节点" + upfunction.getFunctionName() + "不能再分解！");
		} else {
			// 获取其所有子节点和叶
			Vector<?> v_function = ds.getAllSubVFunction(upfunction
					.getFunctionID());

			for (int i = 0; i < v_function.size(); i++) {
				Function f = (Function) v_function.elementAt(i);

				if (sign.trim().equals("insert")) {
					if (f.getNodeType().equals("3")) {
						if (f.getRank().equals("1") || rank.equals("0")) {
							for (int j = 0; j < level; j++) {
								code += "--";
							}
							code += ">";

							code += "<input type='checkbox' name='chk' value='"
									+ f.getFunctionID() + "' >";
							code += f.getFunctionName();

							if (f.getRank().equals("0"))
								code += "<font color='red'>*</font>";
							code += "<br>";
						}
					} else {
						String s = "";
						s = expandRole(f, level + 1);
						if (rank.equals("1")
								&& (s == null || s.trim().equals("")))
							continue;
						for (int j = 0; j < level; j++) {
							code += "--";
						}
						code += ">";
						code += "<b>";
						code += f.getFunctionName();
						code += "</b>" + "<br>";
						code += s;
					}
				} else if (sign.trim().equals("update")) {
					if (f.getNodeType().equals("3")) {
						if (f.getRank().equals("1") || rank.equals("0")) {
							for (int j = 0; j < level; j++) {
								code += "--";
							}
							code += ">";

							if (checkPower(f.getFunctionID())) {
								code += "<input type='checkbox' name='chk' value='"
										+ f.getFunctionID() + "' checked>";
							} else {
								code += "<input type='checkbox' name='chk' value='"
										+ f.getFunctionID() + "' >";
							}

							code += f.getFunctionName();
							if (f.getRank().equals("0"))
								code += "<font color='red'>*</font>";
							code += "<br>";
						}
					} else {
						String s = expandRole(f, level + 1);
						if (rank.equals("1")
								&& (s == null || s.trim().equals("")))
							continue;
						for (int j = 0; j < level; j++) {
							code += "--";
						}
						code += ">";
						code += "<b>";
						code += f.getFunctionName();
						code += "</b><br>";
						code += s;
					}
				} else if (sign.trim().equals("look")) {
					if (f.getNodeType().equals("3")) {
						if (f.getRank().equals("1") || rank.equals("0")) {
							for (int j = 0; j < level; j++) {
								code += "--";
							}
							code += ">";
							if (checkPower(f.getFunctionID())) {
								code += "<input type='checkbox' name='chk' value='"
										+ f.getFunctionID()
										+ "' checked disabled>";
							} else {
								code += "<input type='checkbox' name='chk' value='"
										+ f.getFunctionID() + "' disabled>";
							}

							code += f.getFunctionName();
							if (f.getRank().equals("0"))
								code += "<font color='red'>*</font>";
							code += "<br>";
						}
					} else {
						String s = expandRole(f, level + 1);
						if (rank.equals("1")
								&& (s == null || s.trim().equals("")))
							continue;
						for (int j = 0; j < level; j++) {
							code += "--";
						}
						code += ">";

						code += "<b>";
						code += f.getFunctionName();
						code += "</b><br>";
						code += s;
					}

				} else {

					throw new Exception("操作参数有误！");
				}
			}
			return code;

		}
	}
}
