package mes.framework.dao;

import java.util.List;

class DAO_UserManagerForSqlserver extends DAO_UserManager {

	// 更新功能(当节点为叶节点时)---getdate()
	public String getSQL_UpdateFunctionWhenLeaf(int functionid,
			String functionname, String url, String state, String note,
			String safemarkcode, int userid, String rank, Float flo_Order,int upfunctionid) {
		return "update data_function set cfunctionname='" + functionname
				+ "',curl='" + url + "',cstate='" + state + "',cnote='" + note
				+ "',csafemark='" + safemarkcode + "',nlastupdateuser="
				+ userid + ",dlastupdatetime=getdate(), crank='" + rank
				+ "',flo_order=" + flo_Order + ",NUPFUNCTIONID=" + upfunctionid
				+ "" + " where nfunctionid='"
				+ functionid + "'";
	}

	public String getSQL_UpdatePowerStringWhenAdd(String appendString) {
		return "update data_role set cpowerstring=cpowerstring + '"
				+ appendString + "'";
	}

	// 更新功能(当节点非叶节点时)---getdate()
	public String getSQL_UpdateFunctionWhenNotLeaf(int functionid,
			String functionname, String note, int userid, String rank,
			Float flo_Order,int upfunctionid) {
		return "update data_function set cfunctionname='" + functionname
				+ "',cnote='" + note + "',nlastupdateuser=" + userid
				+ ",dlastupdatetime=getdate(),crank='" + rank + "',flo_order="
				+ flo_Order + ",NUPFUNCTIONID=" + upfunctionid
				+ "" + " where nfunctionid='" + functionid + "'";
	}

	// 添加功能---getdate()
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
				+ ",getdate(),'"
				+ safemarkcode
				+ "','"
				+ note
				+ "','1'," + flo_Order + ")";
	}

	// 删除功能时更新权限串---substring(),len()
	public String getSQL_UpdatePowerStringWhenDelete(int userid, String node) {
		return "update data_role set nlastupdateuser="
				+ userid
				+ ",dlastupdatetime=getdate(),cpowerstring=substring(cpowerstring,1,"
				+ String.valueOf(Integer.parseInt(node) - 1)
				+ ")+'0'+substring(cpowerstring,"
				+ String.valueOf(Integer.parseInt(node) + 1)
				+ ",len(cpowerstring)-"
				+ String.valueOf(Integer.parseInt(node)) + ")";
	}

	// 生成功能号---isnull()
	public String getSQL_QueryNextFuntionId() {
		return "select isnull(max(nfunctionid),0)+1 as functionid from data_function";
	}

	// 获取权限串长度---len()
	public String getSQL_QueryPowerStringLength() {
		return "select max(len(cPOWERSTRING)) from data_role";
	}

	// 查看单个功能信息---convert()
	public String getSQL_QueryFunctionForFunctionIdAndRank(int functionid,
			String user_rolerank) {
		return "select a.nupfunctionid,a.nfunctionid,a.cfunctionname,a.cstate,a.cnodetype,a.curl,a.csafemark,"
				+ "a.nlastupdateuser,convert(char(19),a.dlastupdatetime,20) value,a.cnote,a.crank,b.cusrname,a.flo_order"
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
				+ "'," + userid + ",getdate(),'" + note + "','1')";
	}

	@Override
	public List<String> getSQL_DeleteRole(String roleno, String userid) {
		List<String> list = super.getSQL_DeleteRole(roleno, userid);
		String sql = "update data_user set NROLENO=1,NLASTUPDATEUSER=" + userid
				+ ",DLASTUPDATETIME=getdate() where NROLENO=" + roleno;
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
				+ ",DLASTUPDATETIME=getdate(),CNOTE='" + note
				+ "' where nroleno=" + roleno;
	}

	public String getSQL_QueryNextRoleNO() {
		return "select isnull(max(nroleno),0)+1 as no from data_role";
	}

	public String getSQL_QueryUserRoleInfoForRank(String id,
			String user_rolerank) {
		return "select nusrno,cusrname,nemployeeid,u.nroleno,u.cstate,"
				+ "u.nlastupdateuser,convert(char(20),u.dlastupdatetime,"
				+ "20),u.cnote from data_user "
				+ "u,data_role r  where nusrno='" + id
				+ "' and u.nroleno=r.nroleno and r.crank>=" + user_rolerank;
	}

	public String getSQl_QueryRoleForRank(String user_rolerank) {
		return "select nroleno,crolename,CRANK,CPOWERSTRING,CWELCOMEPAGE,NLASTUPDATEUSER,DLASTUPDATETIME,CNOTE,CENABLED from data_role where convert(int,crank)>="
				+ user_rolerank;
	}

	public String getSQL_QueryAllUserRoleNameNoForRank(String user_rolerank) {
		return "select nusrno,cusrname,u.nroleno,u.cenabled from data_user u,data_role r where u.nroleno=r.nroleno and convert(int,r.crank)>="
				+ user_rolerank + " order by crank,cusrname";
	}

	public String getSQL_InsertUser(String usrno, String usrname,
			String password, String roleno, String employeeid, String state,
			String lastupdateuser, String lastupdatetime, String note,
			String enabled) {
		return "insert into data_user values(" + usrno + ",'" + usrname + "','"
				+ password + "','" + roleno + "','" + employeeid + "','"
				+ state + "','" + lastupdateuser + "',getdate(),'" + note
				+ "','" + enabled + "')";
	}

	// 更新用户 包括密码
	public String getSQL_UpdateUser(String usrno, String usrname,
			String password, String employeeid, String state,
			String lastupdateuser, String lastupdatetime, String note,
			String enabled) {
		return "update data_user set CUSRNAME='" + usrname + "', CPASSWORD='"
				+ password + "',NEMPLOYEEID='" + employeeid + "',CSTATE='"
				+ state + "',NLASTUPDATEUSER='" + lastupdateuser
				+ "',DLASTUPDATETIME=getdate(),CNOTE='" + note + "',CENABLED='"
				+ enabled + "' where NUSRNO=" + usrno;
	}

	// 更新用户 不包括密码
	public String getSQL_UpdateUser(String usrno, String usrname,
			String employeeid, String state, String lastupdateuser,
			String lastupdatetime, String note, String enabled) {
		return "update data_user set CUSRNAME='" + usrname + "',NEMPLOYEEID='"
				+ employeeid + "',CSTATE='" + state + "',NLASTUPDATEUSER='"
				+ lastupdateuser + "',DLASTUPDATETIME=getdate(),CNOTE='" + note
				+ "',CENABLED='" + enabled + "' where NUSRNO=" + usrno;
	}

	public String getSQL_insertDataUserRole(int roleno, int usrno,
			String cdefault) {
		return "insert into data_user_role values(" + roleno + "," + usrno
				+ ",'" + cdefault + "')";
	}

	//谢静天添加 在删除角色时查询是否与用户关联。有则不能删
	 public String getSQl_QueryUserByrole(int nroleno){
		String sql="select du.nusrno  from data_user  du,data_role  dr ,data_user_role dur  " +
				" where dr. nroleno=dur.nroleno and dur.nusrno=du.nusrno and dr.nroleno="+nroleno;
		System.out.println(sql);
		return sql;
		
	 }
}
