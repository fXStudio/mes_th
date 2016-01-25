package mes.util.tree;

public interface IDAO_UserManage {
	//检查用户名是否存在
	public String getSQL_ExistUser(String username);
	
	//返回用户状态 是否已停用
	public String getSQL_UserState(String userid);
	
	//检查用户名，密码是否正确
	public String getSQL_UserProof(String username,String password) ;
	
	//根据用户名，获取其对应的样式表
	public String getSQL_CssFile(String userid);
	
	//根据用户名，获取其角色号
	public String getSQL_RoleNo(String userid);
	
	//根据角色号，获取角色的级别
	public String getSQL_Rank(String roleno);
	
	//获取系统参数值
	public String getSQL_ParameterValue(String key);
	
	//根据角色号，获取欢迎页
	public String getSQL_WelcomePage(String roleno);
	
	//根据角色号获取权限串
	public String getSQL_PowerString(String roleno);
		
	//根据功能号获取其安全标识
	public String getSQL_SafeMark(String functionid);
	
	//获取功能信息
	public String getSQL_FuncitonInfo(String functionid);
	
	//根据功能号获取其子节点和叶
	public String getSQL_AllSubFunctionID(String functionid);
	
	//根据用户名获取用户号
	public String getSQL_userID(String username);
	
	//根据用户号，获取该用户所有信息
	public String getSQL_UserInfo_UserID(String userid);
	
	
	//===========================================================
	//更新用户页面风格数据
	public String getSQL_updateUserInterface(String userid,String color);

}
