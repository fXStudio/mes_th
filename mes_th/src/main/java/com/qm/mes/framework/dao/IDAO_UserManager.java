package com.qm.mes.framework.dao;

import java.util.List;

public interface IDAO_UserManager {

	/**
	 * 更新功能(当节点为叶节点时)
	 * 
	 * @param functionid
	 *            功能号
	 * @param functionname
	 *            功能名
	 * @param url
	 *            文件url
	 * @param state
	 *            状态
	 * @param note
	 *            备注
	 * @param safemarkcode
	 *            安全标记
	 * @param userid
	 *            用户id
	 * @param rank
	 *            级别
	 * @param flo_Order
	 *            功能顺序
	 * @param nupfunctionid
	 *            父功能号
	 * @return
	 */
	String getSQL_UpdateFunctionWhenLeaf(int functionid, String functionname,
			String url, String state, String note, String safemarkcode,
			int userid, String rank, Float flo_Order, int upfunctionid);

	/**
	 * 更新功能(当节点非叶节点时)
	 * 
	 * @param functionid
	 *            功能号
	 * @param functionname
	 *            功能名
	 * @param note
	 *            备注
	 * @param userid
	 *            用户id
	 * @param rank
	 *            级别
	 * @param flo_Order
	 *            功能顺序
	 * @param nupfunctionid
	 *            父功能号
	 */
	String getSQL_UpdateFunctionWhenNotLeaf(int functionid,
			String functionname, String note, int userid, String rank,
			Float flo_Order, int upfunctionid);

	/**
	 * 删除功能
	 * 
	 * @param functionid
	 *            功能id
	 * @return
	 */
	String getSQL_DeleteFunction(int functionid);

	/**
	 * 添加功能
	 * 
	 * @param functionid
	 *            功能号
	 * @param functionname
	 *            功能名
	 * @param nodetype
	 *            节点类型
	 * @param url
	 *            文件url
	 * @param upfunctionid
	 *            父功能号
	 * @param rank
	 *            级别
	 * @param state
	 *            状态
	 * @param userid
	 *            用户ID
	 * @param safemarkcode
	 *            访问安全标记
	 * @param note
	 *            备注
	 * @param enable
	 *            系统标识
	 * @return
	 */
	String getSQL_InsertFunction(int functionid, String functionname,
			String nodetype, String url, int upfunctionid, String rank,
			String state, int userid, String safemarkcode, String note,
			String enable, float flo_Order);

	/**
	 * 生成功能号
	 * 
	 * @return
	 */
	String getSQL_QueryNextFuntionId();

	/**
	 * 获取系统中权限串长度
	 * 
	 * @return 返回的结果集只有一行和一个字段名——PowerStringSize 类型是Int
	 */
	String getSQL_QueryPowerStringLength();

	/**
	 * 更新权限串
	 * 
	 * @param appendString
	 *            添加的字符串
	 * @return
	 */
	String getSQL_UpdatePowerStringWhenAdd(String appendString);

	/**
	 * 删除功能时更新权限串
	 * 
	 * @param userid
	 *            用户ID
	 * @param node
	 *            备注
	 * @return
	 */
	String getSQL_UpdatePowerStringWhenDelete(int userid, String node);

	/**
	 * 获取功能号和节点类型
	 * 
	 * @param functionid
	 *            父节点的功能号
	 * @return 1、nfunctionid 功能号<br>
	 *         2、cnodetype 节点类型
	 */
	String getSQL_QueryFunctionForUpfunctionid(int functionid);

	/**
	 * 获取节点类型
	 * 
	 * @param functionid
	 *            功能号
	 * @return cnodetype 节点类型
	 */
	String getSQL_QueryNodeTypeForFunctionId(int functionid);

	/**
	 * 查询功能列表
	 * 
	 * @param userrolerank
	 * @param functioninfo
	 * @param method
	 *            按功能号查询或按功能名查询
	 * @return 结果集包含以下字段<br>
	 *         1、nfunctionid 功能号<br>
	 *         2、CFUNCTIONNAME 功能名<br>
	 *         3、cnodetype 节点类型<br>
	 *         4、cstate 状态<br>
	 *         5、cenabled 系统标识
	 */
	String getSQL_QueryAllFunctionsForRank(String userrolerank,
			String functioninfo, String method);

	/**
	 * 查看单个功能信息
	 * 
	 * @param functionid
	 *            功能号
	 * @param user_rolerank
	 *            用户级别
	 * @return 结果集包含以下字段<br>
	 *         1、nupfunctionid 父功能号<br>
	 *         2、nfunctionid 功能号<br>
	 *         3、cfunctionname 功能名<br>
	 *         4、cstate 状态<br>
	 *         5、cnodetype 节点类型<br>
	 *         6、curl 文件url<br>
	 *         7、csafemark 访问安全标记<br>
	 *         8、nlastupdateuser 用户ID<br>
	 *         9、dlastupdatetime 维护时间<br>
	 *         10、cnote 备注<br>
	 *         11、crank 级别<br>
	 *         12、cusrname 用户名<br>
	 *         13、flo_Order 功能顺序号
	 */
	String getSQL_QueryFunctionForFunctionIdAndRank(int functionid,
			String user_rolerank);

	/**
	 * 添加功能时获取上一级节点
	 * 
	 * @return 结果集包含以下字段<br>
	 *         1、nfunctionid功能号<br>
	 *         2、cfunctionname功能名
	 */
	String getSQL_QueryLastNodeForNodeType();

	/**
	 * 删除角色<br>
	 * 返回两条SQL语句：<br>
	 * 1、按照角色号删除角色信息。 <br>
	 * 2、更新删除角色后所影响的用户信息——将用户角色号置为1。
	 * 
	 * @param roleno
	 *            角色号
	 * @param userid
	 *            用户号 用于做更新记录
	 * @return 返回一组SQL语句
	 */
	List<String> getSQL_DeleteRole(String roleno, String userid);

	/**
	 * 插入角色信息
	 * 
	 * @param roleno
	 *            角色号
	 * @param rolename
	 *            角色名
	 * @param rank
	 *            角色级别
	 * @param powerstring
	 *            权限串
	 * @param welcomepage
	 *            欢迎页面
	 * @param userid
	 *            最后一次更新用户号
	 * @param note
	 *            描述
	 * @return 返回一条插入角色的SQL
	 */
	String getSQL_InsertRole(String roleno, String rolename, String rank,
			String powerstring, String welcomepage, String userid, String note);

	/**
	 * 查询系统中的存在功能，按照功能号排序
	 * 
	 * @return 查询的结果集合包含以下字段：<br>
	 *         1、NFUNCTIONID——功能号<br>
	 *         2、CFUNCTIONNAME——功能名称<br>
	 *         3、CURL——文件URL<br>
	 *         4、NUPFUNCTIONID——父功能号<br>
	 *         5、CRANK——级别<br>
	 *         6、CNODETYPE——节点类型<br>
	 *         7、CSTATE——状态<br>
	 *         8、NLASTUPDATEUSER——维护员<br>
	 *         9、DLASTUPDATETIME——维护时间<br>
	 *         10、CSAFEMARK——访问安全标记<br>
	 *         11、CNOTE——备注<br>
	 *         12、CENABLED——系统标识 是否可编辑
	 */
	String getSQL_QueryAllFunction();

	/**
	 * 查询下一个可用于插入操作的角色号
	 * 
	 * @return 返回的结果集中只有一行，并且只有一个字段名——“no” 类型是Int
	 */
	String getSQL_QueryNextRoleNO();

	/**
	 * 根据角色级别和角色号或角色名查询角色，按照角色号排序。<br>
	 * 
	 * @param rank
	 *            <br>
	 *            角色级别——若传入级别号则级别号参与过滤。 <br>
	 * @param roleno
	 *            <br>
	 *            角色号——若传入角色号则角色号参与过来。 <br>
	 * @return 返回的结果集中包含以下字段：<br>
	 *         1、NROLENO——角色编码<br>
	 *         2、CROLENAME——角色名称<br>
	 *         3、CENABLED--系统标识 是否可编辑<br>
	 * @author 于丽达 <br>
	 */
	String getSQL_QueryRole(String userrolerank, String roleinfo, String method);

	/**
	 * 查询角色及角色对应的用户信息
	 * 
	 * @param roleno
	 *            角色号
	 * @return 返回的结果集中有一行，包含以下字段：<br>
	 *         NROLENO—— 角色编码<br>
	 *         CROLENAME—— 角色名称<br>
	 *         CRANK——权限级别<br>
	 *         CPOWERSTRING——权限串<br>
	 *         CWELCOMEPAGE——欢迎页面<br>
	 *         NLASTUPDATEUSER ——维护员<br>
	 *         DLASTUPDATETIME ——维护时间<br>
	 *         CNOTE——备注<br>
	 *         CENABLED——系统标识 是否可编辑<br>
	 *         <br>
	 *         CUSRNAME——登陆帐号<br>
	 *         NEMPLOYEEID——员工ID<br>
	 *         CSTATE——状态<br>
	 *         NLASTUPDATEUSER ——维护员<br>
	 *         DLASTUPDATETIME ——维护时间<br>
	 */
	String getSQL_QueryRoleAndUser(String roleno);

	/**
	 * 更新角色，根据角色号更新其他角色信息
	 * 
	 * @param roleno
	 *            角色号
	 * @param rolename
	 *            角色名
	 * @param rank
	 *            级别
	 * @param powerstring
	 *            权限串
	 * @param welcomepage
	 *            欢迎页面
	 * @param userid
	 *            最后一次维护的用户号
	 * @param note
	 *            描述
	 * @return 返回更新角色信息的SQL语句
	 */
	String getSQL_UpdateRole(String roleno, String rolename, String rank,
			String powerstring, String welcomepage, String userid, String note);

	/**
	 * 根据用户名从用户表中获取记录数
	 * 
	 * @param username
	 *            用户名
	 * @return
	 */
	public String getSQL_QueryCountUserName(String username);

	/**
	 * 根据用户号从用户表中获取用户状态
	 * 
	 * @param userid
	 *            用户号
	 * @return 结果集包含以下字段 cstate
	 * @deprecated 建议使用getSQL_QueryUserInfoForUserID方法
	 */
	public String getSQL_QueryUserStateForUserID(String userid);

	/**
	 * 根据用户名和密码从用户表中获取记录数
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码(md5加密后的密码)
	 * @return
	 */
	public String getSQL_QueryCountUserNamePassword(String username,
			String password);

	/**
	 * 根据用户号，获取其对应的样式表
	 * 
	 * @param userid
	 *            用户号
	 * @return 结果集包含以下字段 ccssfile
	 */
	public String getSQL_QueryCssFileForUserID(String userid);

	/**
	 * 根据用户号，获取其角色号
	 * 
	 * @param userid
	 *            用户号
	 * @return 结果集包含以下字段 nroleno
	 * @deprecated 建议使用getSQL_QueryUserInfoForUserID方法
	 */
	public String getSQL_QueryRoleNoForUserID(String userid);

	/**
	 * 根据角色号，获取角色的级别
	 * 
	 * @param roleno
	 *            角色号
	 * @return 结果集包含以下字段 crank
	 * @deprecated 建议使用getSQL_QueryRoleForRoleNo方法
	 */
	public String getSQL_QueryRankForRoleNo(String roleno);

	/**
	 * 获取系统参数值
	 * 
	 * @param key
	 *            键名
	 * @return 结果集包含以下字段 cvalue
	 */
	public String getSQL_QueryParameterValueForKey(String key);

	/**
	 * 根据角色号，获取欢迎页
	 * 
	 * @param roleno
	 *            角色号
	 * @return 结果集包含以下字段 CWELCOMEPAGE
	 * @deprecated 建议使用getSQL_QueryRoleForRoleNo方法
	 */
	public String getSQL_QueryWelcomePageForRoleNo(String roleno);

	/**
	 * 根据角色号获取权限串
	 * 
	 * @param roleno
	 *            角色号
	 * @return 结果集包含以下字段 CPOWERSTRING
	 * @deprecated 建议使用getSQL_QueryRoleForRoleNo方法
	 */
	public String getSQL_QueryPowerStringForRoleNo(String roleno);

	/**
	 * 根据功能号获取其安全标识
	 * 
	 * @param functionid
	 *            功能号
	 * @return 结果集包含以下字段 CSAFEMARK
	 * @deprecated 建议使用getSQL_QueryFuncitonInfoForFunctionID方法
	 */
	// public String getSQL_QuerySafeMarkForFunctionID(String functionid);
	/**
	 * 获取功能信息
	 * 
	 * @param functionid
	 *            功能号
	 * @return 结果集包含以下字段<br>
	 *         1、nfunctionid 功能号<br>
	 *         2、cfunctionname 功能名<br>
	 *         3、curl URL<br>
	 *         4、nupfunctionid 父功能号<br>
	 *         5、crank 级别<br>
	 *         6、cnodetype 节点类型<br>
	 *         7、cstate 状态<br>
	 *         8、csafemark 安全标识<br>
	 *         9、cnote 备注<br>
	 *         10、cenabled 是否可删除<br>
	 *         11、NLASTUPDATEUSER 最后修改者<br>
	 *         12、flo_order 功能顺序号
	 */
	public String getSQL_QueryFuncitonInfoForFunctionID(String functionid);

	/**
	 * 根据功能号获取其子节点和叶
	 * 
	 * @param functionid
	 * @return 结果集包含以下字段 nfunctionid
	 */
	public String getSQL_QueryAllSubFunctionIDsForFunctionID(String functionid);

	/**
	 * 根据用户名获取用户号
	 * 
	 * @param username
	 *            用户名
	 * @return 结果集包含以下字段 nusrno
	 */
	public String getSQL_QueryUserIDForUserName(String username);

	/**
	 * 根据用户号，获取该用户所有信息
	 * 
	 * @param userid
	 *            用户号
	 * @return 结果集包含以下字段<br>
	 *         1、NUSRNO 用户号<br>
	 *         2、CUSRNAME 用户名<br>
	 *         3、CPASSWORD 密码<br>
	 *         4、NROLENO 角色号<br>
	 *         5、NEMPLOYEEID 员工号<br>
	 *         6、CSTATE 状态<br>
	 *         7、NLASTUPDATEUSER 最后修改者<br>
	 *         8、DLASTUPDATETIME 最后修改时间<br>
	 *         9、CNOTE 备注<br>
	 *         10、CENABLED 是否可删除
	 */
	public String getSQL_QueryUserInfoForUserID(String userid);

	/**
	 * 更新用户页面风格数据
	 * 
	 * @param userid
	 * @param color
	 * @return
	 */
	public String getSQL_UpdateUserInterface(String userid, String color);

	/**
	 * 根据角色号查询角色信息
	 * 
	 * @param roleno
	 *            角色号
	 * @return 返回一条角色信息，包含以下字段： NROLENO 角色编码<br>
	 *         1、CROLENAME 角色名称<br>
	 *         2、CRANK 权限级别<br>
	 *         3、CPOWERSTRING 权限串<br>
	 *         4、CWELCOMEPAGE 欢迎页面<br>
	 *         5、NLASTUPDATEUSER 维护员<br>
	 *         6、DLASTUPDATETIME 维护时间<br>
	 *         7、CNOTE 备注<br>
	 *         8、CENABLED 系统标识 是否可编辑<br>
	 */
	String getSQL_QueryRoleForRoleNo(String roleno);

	/**
	 * 插入用户信息表
	 * 
	 * @param usrno
	 *            用户ID
	 * @param usrname
	 *            登陆帐号
	 * @param password
	 *            登陆密码
	 * @param roleno
	 *            角色号
	 * @param employeeid
	 *            员工ID
	 * @param state
	 *            状态 0 停用 1正常
	 * @param lastupdateuser
	 *            维护员
	 * @param lastupdatetime
	 *            维护时间
	 * @param note
	 *            备注
	 * @param roleno
	 *            所属角色
	 * @param enabled
	 *            系统标识 是否可编辑 0不可编辑(删除)1可编辑
	 * @return
	 */
	String getSQL_InsertUser(String usrno, String usrname, String password,
			String roleno, String employeeid, String state,
			String lastupdateuser, String lastupdatetime, String note,
			String enabled);

	/**
	 * 根据用户roleno删除用户表 <br>
	 * 原版:根据用户id删除用户表
	 * 
	 * @param usrno
	 *            用户号
	 * @return
	 */
	String getSQL_DeleteUser(String usrno);

	/**
	 * 根据角色号,删除角色时,该用户仅存在该角色删除用户
	 * 
	 * @param roleno
	 *            角色号
	 * @return
	 */
	String getSQL_DeleteUser_Roleno(int roleno);

	/**
	 * 根据角色号,删除DataUserRole表中存在该角色项
	 * 
	 * @param roleno
	 *            角色号
	 * @return
	 */
	String getSQL_DeleteDataUserRole_Roleno(int roleno);

	/**
	 * 根据角色号删除用户-角色对应表
	 * 
	 * @param usrno
	 *            用户号
	 * @return
	 */
	String getSQL_DeleteDataUserRole(int usrno);

	/**
	 * 插入用户与角色关系表的信息
	 * 
	 * @param usrno
	 *            用户号<br>
	 * @param roleno
	 *            角色号<br>
	 * @param cdefault
	 *            是否为默认角色:0 候选角色 1 默认角色<br>
	 * @return
	 */
	String getSQL_insertDataUserRole(int usrno, int roleno, String cdefault);

	/**
	 * 查询用户号的所有角色
	 * 
	 * @param usrno
	 *            用户号
	 * @return usrno 用户号 <br>
	 *         roleno 角色号<br>
	 *         cdefault 默认角色 0: 候选角色 1: 默认角色<br>
	 *         rolename 角色名
	 */
	String getSQL_selectDataUserRole(int usrno);

	/**
	 * 查询DataUserRole表的所有信息
	 * 
	 * @param roleno
	 * @return usrno 用户号 <br>
	 *         roleno 角色号<br>
	 *         cdefault 默认角色 0: 候选角色 1: 默认角色<br>
	 */
	String getSQL_selectDataUserRole_Roleno(int roleno);

	/**
	 * 根据用户号查询已有的角色号
	 * 
	 * @param usrno
	 *            用户号
	 * @return usrno 用户号<br>
	 *         roleno 角色号<br>
	 *         cdefault 默认角色 0: 候选角色 1: 默认角色
	 */
	String getSQL_selectHaveRole(int usrno);

	// update include password
	/**
	 * 根据用户id，更改用户信息，包括密码。
	 * 
	 * @param usrno
	 *            用户id
	 * @param usrname
	 *            登陆帐号
	 * @param password
	 *            登陆密码
	 * @param employeeid
	 *            员工ID
	 * @param state
	 *            状态 0 停用 1正常
	 * @param lastupdateuser
	 *            维护员
	 * @param lastupdatetime
	 *            维护时间
	 * @param note
	 *            备注
	 * @param enabled
	 *            系统标识 是否可编辑 0不可编辑(删除)1可编辑
	 * @return
	 */
	String getSQL_UpdateUser(String usrno, String usrname, String password,
			String employeeid, String state, String lastupdateuser,
			String lastupdatetime, String note, String enabled);

	// 取得用户id是否重复
	/**
	 * 根据用户id查找记录条数
	 * 
	 * @param usrno
	 * @return
	 */
	String getSQL_QueryCountForUserNo(String usrno);

	/**
	 * 根据用户名查找记录条数
	 * 
	 * @param usrname
	 * @return
	 * 
	 */
	String getSQL_QueryCountForUserName(String usrname);

	// update no password;
	/**
	 * 根据用户id 更改用户信息，不包括密码
	 * 
	 * @param usrno
	 *            用户id
	 * @param usrname
	 *            登陆帐号
	 * @param employeeid
	 *            员工ID
	 * @param state
	 *            状态
	 * @param lastupdateuser
	 *            维护员
	 * @param lastupdatetime
	 *            维护时间
	 * @param note
	 *            备注
	 * @param enabled
	 *            系统标识 是否可编辑
	 * @return
	 */
	String getSQL_UpdateUser(String usrno, String usrname, String employeeid,
			String state, String lastupdateuser, String lastupdatetime,
			String note, String enabled);

	// 取最大usrno号
	/**
	 * 查找最大的usrno
	 * 
	 * @return
	 */
	String getSQL_QueryMaxusrno();

	/**
	 * 插入css样式表
	 * 
	 * @param usrno
	 * @param cssfile
	 * @return
	 */
	String getSQL_insertCss(String usrno, String cssfile);

	/**
	 * 根据用户id删除css样式
	 * 
	 * @param usrno
	 * @return
	 */
	String getSQL_DeleteCss(String usrno);

	/**
	 * 根据权限级别查找角色编码和角色名称
	 * 
	 * @param user_rolerank
	 *            权限级别
	 * @return NROLENO 角色编码<br>
	 *         CROLENAME 角色名称
	 */
	public String getSQl_QueryRoleForRank(String user_rolerank);

	/**
	 * 根据角色级别和用户代码或用户名称查询用户，按照用户代码排序。
	 * 
	 * @param user_rolerank
	 *            权限信息-若传入级别号则级别号参与过滤 <br>
	 * @param userinfo
	 *            为用户代码或用户名称 <br>
	 * @param method
	 *            为采用哪种方法(用户代码查询或用户名称查询) <br>
	 * @return 1、nusrno 用户ID<br>
	 *         2、cusrname 登陆帐号<br>
	 *         3、cenabled 系统标识<br>
	 * @author 于丽达 <br>
	 */
	public String getSQL_QueryAllUserRoleNameNoForRank(String user_rolerank,
			String userinfo, String method);

	/**
	 * 根绝权限级别查找用户角色信息
	 * 
	 * @param id
	 * @param user_rolerank
	 *            权限级别
	 * @return 1、nusrno 用户id<br>
	 *         2、cusrname 登陆帐号<br>
	 *         3、nemployeeid 员工ID<br>
	 *         4、nroleno 角色编码<br>
	 *         5、cstate 状态<br>
	 *         6、nlastupdateuser 维护员<br>
	 *         7、dlastupdatetime 维护时间<br>
	 *         8、cnote 备注
	 */
	public String getSQL_QueryUserRoleInfoForRank(String id,
			String user_rolerank);
    public String getSQl_QueryUserByrole(int nroleno);
    
  	/**
  	 * 通过用户ID查询用户对象
  	 * 
  	 * @param id
  	 * @return 用户对象
  	 * @author 袁鹏 <br>
  	 */
	public String getSQL_SelectUserById(int id);
	
	/**
  	 * 查询所有用户对象
  	 * 
  	 * @return 用户对象
  	 * @author 袁鹏 <br>
  	 */
	 public String getAllUsers();
	/**
  	 * 查功能信息
  	 * 
  	 * @param userrolerank
  	 * @return sql
  	 * @author 徐嘉 <br>
  	 */
	public String getSQL_AllFunctionsForRank(String userrolerank);
	/**
  	 * 查user信息  	
  	 * @return sql
  	 * @author 徐嘉 <br>
  	 */
	public String getSQL_AllUserRoleNameNoForRank();
	/**
  	 * 查role信息  	
  	 * @return sql
  	 * @author 徐嘉 <br>
  	 */
	public String getSQL_AllRoleNameNoForRank(String user_rolerank);
}
