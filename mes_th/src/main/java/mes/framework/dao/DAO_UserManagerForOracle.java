package mes.framework.dao;

import java.util.List;

class DAO_UserManagerForOracle extends DAO_UserManager {

	// 更新功能(当节点为叶节点时)---sysdate
	public String getSQL_UpdateFunctionWhenLeaf(int functionid,
			String functionname, String url, String state, String note,
			String safemarkcode, int userid, String rank, Float flo_Order,
			int upfunctionid) {
		return "update data_function set cfunctionname='" + functionname
				+ "',curl='" + url + "',cstate='" + state + "',cnote='" + note
				+ "',csafemark='" + safemarkcode + "',nlastupdateuser="
				+ userid + ",dlastupdatetime=sysdate, crank='" + rank
				+ "',flo_order=" + flo_Order + ",NUPFUNCTIONID=" + upfunctionid
				+ "" + " where nfunctionid='" + functionid + "'";
	}

	// 更新功能(当节点非叶节点时)---sysdate
	public String getSQL_UpdateFunctionWhenNotLeaf(int functionid,
			String functionname, String note, int userid, String rank,
			Float flo_Order, int upfunctionid) {
		return "update data_function set cfunctionname='" + functionname
				+ "',cnote='" + note + "',nlastupdateuser=" + userid
				+ ",dlastupdatetime=sysdate ,crank='" + rank + "',flo_order="
				+ flo_Order + ",NUPFUNCTIONID=" + upfunctionid + ""
				+ "where nfunctionid='" + functionid + "'";
	}

	// 添加功能---sysdate
	public String getSQL_InsertFunction(int functionid, String functionname,
			String nodetype, String url, int upfunctionid, String rank,
			String state, int userid, String safemarkcode, String note,
			String enable, float flo_Order) {
		return "insert into data_function(nfunctionid,cfunctionName,curl,nupfunctionId,"
				+ "crank,cnodetype,cstate,NLASTUPDATEUSER,DLASTUPDATETIME,CSAFEMARK,CNOTE,CENABLED,FLO_ORDER)"
				+ " values("
				+ functionid
				+ ",'"
				+ functionname
				+ "','"
				+ url
				+ "',"
				+ upfunctionid
				+ ",'"
				+ rank
				+ "','"
				+ nodetype
				+ "','"
				+ state
				+ "',"
				+ userid
				+ ",sysdate,'"
				+ safemarkcode
				+ "','"
				+ note
				+ "','1'," + flo_Order + ")";
	}

	// 删除功能时更新权限串---substr(),length()
	public String getSQL_UpdatePowerStringWhenDelete(int userid, String node) {
		return "update data_role set nlastupdateuser="
				+ userid
				+ ",dlastupdatetime=sysdate,cpowerstring=substr(cpowerstring,1,"
				+ String.valueOf(Integer.parseInt(node) - 1)
				+ ")||'0'||substr(cpowerstring,"
				+ String.valueOf(Integer.parseInt(node) + 1)
				+ ",length(cpowerstring)-"
				+ String.valueOf(Integer.parseInt(node)) + ")";

	}

	// 生成功能号---nvl()
	public String getSQL_QueryNextFuntionId() {
		return "select nvl(max(nfunctionid),0)+1 as functionid from data_function";
	}

	// 获取权限串长度---length()
	public String getSQL_QueryPowerStringLength() {
		return "select max(length(cPOWERSTRING)) from data_role";
	}

	public String getSQL_QueryFunctionForFunctionIdAndRank(int functionid,
			String user_rolerank) {
		return "select a.nupfunctionid,a.nfunctionid,a.cfunctionname,a.cstate,a.cnodetype,a.curl,a.csafemark,"
				+ "a.nlastupdateuser,to_char(a.dlastupdatetime,'yyyy-mm-dd HH24:MI:ss'),a.cnote,a.crank,b.cusrname,a.flo_order"
				+ " from data_function a inner join data_user b on a.nlastupdateuser=b.NUSRNO"
				+ " where a.nfunctionid="
				+ functionid
				+ " and a.crank>='"
				+ user_rolerank + "'";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mes.framework.services.role.IDAORole#getSQL_InsertRole(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public String getSQL_InsertRole(String roleno, String rolename,
			String rank, String powerstring, String welcomepage, String userid,
			String note) {
		return "insert into data_role values(" + roleno + ",'" + rolename
				+ "','" + rank + "','" + powerstring + "','" + welcomepage
				+ "'," + userid + ",sysdate,'" + note + "','1')";
	}

	@Override
	public List<String> getSQL_DeleteRole(String roleno, String userid) {
		List<String> list = super.getSQL_DeleteRole(roleno, userid);
		String sql = "update data_user set NROLENO=1,NLASTUPDATEUSER=" + userid
				+ ",DLASTUPDATETIME=sysdate where NROLENO=" + roleno;
		list.add(sql);
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mes.framework.services.role.IDAORole#getSQL_UpdateRole(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public String getSQL_UpdateRole(String roleno, String rolename,
			String rank, String powerstring, String welcomepage, String userid,
			String note) {
		return "update data_role set crolename='" + rolename + "',crank='"
				+ rank + "',cpowerstring='" + powerstring + "',cwelcomepage='"
				+ welcomepage + "',NLASTUPDATEUSER=" + userid
				+ ",DLASTUPDATETIME=sysdate,CNOTE='" + note
				+ "' where nroleno=" + roleno;
	}

	public String getSQL_QueryNextRoleNO() {
		return "select nvl(max(nroleno),0)+1 as no from data_role";
	}

	// 添加功能时更新权限串
	public String getSQL_UpdatePowerStringWhenAdd(String appendString) {
		return "update data_role set cpowerstring=cpowerstring || '"
				+ appendString + "'";
	}

	public String getSQL_QueryUserRoleInfoForRank(String id,
			String user_rolerank) {
		return "select nusrno,cusrname,nemployeeid,u.nroleno,u.cstate,"
				+ "u.nlastupdateuser,to_char(u.dlastupdatetime,"
				+ "'yyyy-mm-dd HH24:MI:ss'),u.cnote from data_user "
				+ "u,data_role r	 where nusrno='" + id
				+ "'  and to_number(r.crank)>=" + user_rolerank;

		/*
		 * return "select nusrno,cusrname,nemployeeid,u.nroleno,u.cstate," +
		 * "u.nlastupdateuser,to_char(u.dlastupdatetime," + "'yyyy-mm-dd
		 * HH24:MI:ss'),u.cnote from data_user " + "u,data_role r where
		 * nusrno='" + id + "' and u.nroleno=r.nroleno and to_number(r.crank)>=" +
		 * user_rolerank;
		 */
	}

	public String getSQl_QueryRoleForRank(String user_rolerank) {
		return "select nroleno,crolename,CRANK,CPOWERSTRING,CWELCOMEPAGE,NLASTUPDATEUSER,DLASTUPDATETIME,CNOTE,CENABLED from data_role where to_number(crank)>="
				+ user_rolerank;
	}

	public String getSQL_QueryAllUserRoleNameNoForRank(String user_rolerank) {
		return "select nusrno,cusrname,u.nroleno,u.cenabled from data_user u,data_role r where u.nroleno=r.nroleno and to_number(r.crank)>="
				+ user_rolerank + " order by crank,cusrname";
	}

	public String getSQL_InsertUser(String usrno, String usrname,
			String password, String roleno, String employeeid, String state,
			String lastupdateuser, String lastupdatetime, String note,
			String enabled) {
		return "insert into data_user values(" + usrno + ",'" + usrname + "','"
				+ password + "','" + roleno + "','" + employeeid + "','"
				+ state + "','" + lastupdateuser + "',sysdate,'" + note + "','"
				+ enabled + "')";
	}

	// 更新user 包括密码
	public String getSQL_UpdateUser(String usrno, String usrname,
			String password, String employeeid, String state,
			String lastupdateuser, String lastupdatetime, String note,
			String enabled) {
		return "update data_user set CUSRNAME='" + usrname + "', CPASSWORD='"
				+ password + "',NEMPLOYEEID='" + employeeid + "',CSTATE='"
				+ state + "',NLASTUPDATEUSER='" + lastupdateuser
				+ "',DLASTUPDATETIME=sysdate,CNOTE='" + note + "',CENABLED='"
				+ enabled + "' where NUSRNO=" + usrno;
	}

	// 更新user 不包括密码
	public String getSQL_UpdateUser(String usrno, String usrname,
			String employeeid, String state, String lastupdateuser,
			String lastupdatetime, String note, String enabled) {
		return "update data_user set CUSRNAME='" + usrname + "',NEMPLOYEEID='"
				+ employeeid + "',CSTATE='" + state + "',NLASTUPDATEUSER='"
				+ lastupdateuser + "',DLASTUPDATETIME=sysdate,CNOTE='" + note
				+ "',CENABLED='" + enabled + "' where NUSRNO=" + usrno;
	}

	public String getSQL_insertDataUserRole(int usrno, int roleno,
			String cdefault) {
		return "insert into data_user_role values(" + usrno + "," + roleno
				+ ",'" + cdefault + "')";
	}
	//谢静天添加 在删除角色时查询是否与用户关联。有则不能删
	 public String getSQl_QueryUserByrole(int nroleno){
		String sql="select du.nusrno  from data_user  du,data_role  dr ,data_user_role dur  " +
				" where dr. nroleno=dur.nroleno and dur.nusrno=du.nusrno and dr.nroleno="+nroleno;
		return sql;
	 }
	 
	 //袁鹏添加 通过ID查询用户对象
	 public String getSQL_SelectUserById(int id){
		 String sql = "select * from data_user where NUSRNO = " + id;
		 return sql;
	 }
	 
	 //袁鹏添加 查询所有用户表中的数据
	 public String getAllUsers(){
		 String sql = "select * from data_user";
		 return sql;
	 }
}
