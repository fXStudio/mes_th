package com.qm.mes.util.tree;

import com.qm.mes.util.tree.IDAO_UserManage;

public class ADAO_UserManage implements IDAO_UserManage {
	// 检查用户名是否存在
	public String getSQL_ExistUser(String username) {
		return "select count(*) from data_user where cusrname='" + username
				+ "'";
	}

	// 返回用户状态 是否已停用
	public String getSQL_UserState(String userid) {
		return "select cstate from data_user where nusrno=" + userid + "";
	}

	// 检查用户名，密码(md5加密后的密码)是否正确
	public String getSQL_UserProof(String username, String password) {
		return "select count(*) from data_user where cusrname='" + username
				+ "' and cpassword='" + password + "'";
	}

	// 根据用户名，获取其对应的样式表
	public String getSQL_CssFile(String userid) {
		return "select ccssfile from interface_user where nusrno=" + userid
				+ "";
	}

	// 根据用户名，获取其角色号
	public String getSQL_RoleNo(String userid) {
		return "select nroleno from data_user where nusrno=" + userid + "";
	}

	// 根据角色号，获取角色的级别
	public String getSQL_Rank(String roleno) {
		return "select crank from data_role where nroleno=" + roleno + "";
	}

	// 获取系统参数值
	public String getSQL_ParameterValue(String key) {
		return "select cvalue from system_parameter where CKEY='" + key + "'";
	}

	// 根据角色号，获取欢迎页
	public String getSQL_WelcomePage(String roleno) {
		return "select CWELCOMEPAGE from data_role where NROLENO='" + roleno
				+ "'";
	}

	// 根据角色号获取权限串
	public String getSQL_PowerString(String roleno) {
		return "select CPOWERSTRING from data_role where NROLENO='" + roleno
				+ "'";
	}

	// 根据功能号获取其安全标识
	public String getSQL_SafeMark(String functionid) {
		return "select CSAFEMARK from data_function where NFUNCTIONID="
				+ functionid + "";
	}

	// 获取功能信息
	public String getSQL_FuncitonInfo(String functionid) {
		return "select nfunctionid,cfunctionname,curl,nupfunctionid,crank,cnodetype,cstate,csafemark,cnote,cenabled,flo_order from data_function where nfunctionid="
				+ functionid + "";
	}

	// 根据功能号获取其子节点和叶,并按功能顺序号显示
	public String getSQL_AllSubFunctionID(String functionid) {
		return "select nfunctionid from data_function where nupfunctionid="
				+ functionid + "  order by flo_order";
	}

	// 根据用户名获取用户号
	public String getSQL_userID(String username) {
		return "select nusrno from data_user where cusrname='" + username + "'";
	}

	// 根据用户号，获取该用户所有信息
	public String getSQL_UserInfo_UserID(String userid) {
		return "select NUSRNO,CUSRNAME,CPASSWORD,NROLENO,NEMPLOYEEID,CSTATE,NLASTUPDATEUSER,DLASTUPDATETIME,CNOTE,CENABLED  from data_user where nusrno="
				+ userid + "";
	}

	// ==============================
	public String getSQL_updateUserInterface(String userid, String color) {
		return "update interface_user set CCSSFILE='" + color
				+ "' where NUSRNO=" + userid + "";
	}

}
