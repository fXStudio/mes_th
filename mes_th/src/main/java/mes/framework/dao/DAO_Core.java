package mes.framework.dao;

abstract class DAO_Core implements IDAO_Core {

	// 查询流程定义中未使用的命名空间
	public String getSQL_QueryNameSpaceForProcessStatement(String nnamespaceid) {
		return "select NNAMESPACEID,CNAMESPACE from namespace_statement "
				+ "where NNAMESPACEID not in "
				+ "(select NNAMESPACEID from process_statement where nnamespaceid="
				+ nnamespaceid + ")";
	}

	public String getSQL_QueryNameSpaceForProcessStatement() {
		return "select NNAMESPACEID,CNAMESPACE,cdescription from namespace_statement ";
	}

	// 验证服务是否被某一流程使用
	public String getSQL_QueryCountServiceIsUsed(int serviceid) {
		return "select count(*) from PROCESS_SERVERS where NSERVERID="
				+ serviceid + "";
	}

	// 添加服务定义信息
	public String getSQL_InsertService(int serviceid, String servicename,
			String classname, String servicedesc, String namespace) {

		return "insert into SERVER_STATEMENT values(" + serviceid + ",'"
				+ servicename + "','" + classname + "','" + servicedesc + "','"
				+ namespace + "')";
	}

	// 更新服务定义信息
	public String getSQL_UpdateService(int serviceid, String servicename,
			String classname, String servicedesc, int namespace) {
		return "update SERVER_STATEMENT set CSERVERNAME='" + servicename
				+ "',CCLASSNAME='" + classname + "',CDESCRIPTION='"
				+ servicedesc + "' ,NNAMESPACEID=" + namespace
				+ " where NSERVERID=" + serviceid + "";
	}

	// 删除服务定义信息
	public String getSQL_DeleteService(int serviceid) {
		return "delete from SERVER_STATEMENT where NSERVERID=" + serviceid + "";
	}

	// 验证同一服务同类型的参数是否唯一
	public String getSQL_QueryCountServiceParaIsUnique(int serviceid,
			String paraname, String paratype) {
		return "select count(*) from parameter_statement where nserverid="
				+ serviceid + " and ctype='" + paratype + "'and cparameter='"
				+ paraname + "'";
	}

	// 向服务中添加参数
	public String getSQL_InsertServicePara(int serviceid, String paraname,
			String paratype, String paradesc) {
		return "insert into parameter_statement values(" + serviceid + ",'"
				+ paraname + "','" + paratype + "','" + paradesc + "')";
	}

	// 更新服务参数
	public String getSQL_UpdateServicePara(int serviceid, String paratype,
			String setparatype, String paraname, String setparaname,
			String paradesc) {
		return "update PARAMETER_STATEMENT set CPARAMETER ='" + paraname
				+ "',CTYPE = '" + paratype + "',CDESCRIPTION ='" + paradesc
				+ "' where NSERVERID ='" + serviceid + "' and CTYPE='"
				+ setparatype + "' and CPARAMETER='" + setparaname + "'";
	}

	// 删除服务参数
	public String getSQL_DeleteServicePara(int serviceid, String paratype,
			String paraname) {
		return "delete PARAMETER_STATEMENT where NSERVERID=" + serviceid
				+ " and CTYPE = '" + paratype + "' and CPARAMETER ='"
				+ paraname + "'";
	}

	// 查询服务参数列表
	public String getSQL_QueryAllServiceParas(String info, String method) {
		String sql = "";
		if (info != null && (!info.equals(""))) {
			if (method.equals("ByParas")) {
				sql = "select a.nserverid,b.cSERVERNAME,a.CPARAMETER,a.CTYPE from "
						+ "PARAMETER_STATEMENT a inner join server_statement b on a.nserverid= b.nserverid where a.CPARAMETER like '%"
						+ info + "%' order by a.nserverid";
			}
			if (method.equals("ByService")) {
				sql = "select a.nserverid,b.cSERVERNAME,a.CPARAMETER,a.CTYPE from "
						+ "PARAMETER_STATEMENT a inner join server_statement b on a.nserverid= b.nserverid where b.CSERVERNAME like '%"
						+ info + "%' order by a.nserverid";
			}
		} else {
			sql = "select a.nserverid,b.cSERVERNAME,a.CPARAMETER,a.CTYPE from "
					+ "PARAMETER_STATEMENT a inner join server_statement b on a.nserverid= b.nserverid order by a.nserverid";
		}
		return sql;
	}

	// 查看单个服务参数信息
	public String getSQL_QueryServicePara(int serviceid, String paratype,
			String paraname) {
		return "select CPARAMETER,CTYPE,CDESCRIPTION from PARAMETER_STATEMENT where NSERVERID="
				+ serviceid
				+ " and CTYPE='"
				+ paratype
				+ "' and CPARAMETER='"
				+ paraname + "'";
	}

	// 查询服务列表
	public String getSQL_QueryAllServices(String serviceinfo, String method) {
		String sql = "";
		if (serviceinfo != null && (!serviceinfo.equals(""))) {
			// 按服务号查询
			if (method.equals("ById"))
				sql = "select NSERVERID,CSERVERNAME,CCLASSNAME,"
						+ "CDESCRIPTION,NNAMESPACEID from "
						+ "SERVER_STATEMENT  where  nserverid ='" + serviceinfo
						+ "'";
			// 按服务名称查询
			if (method.equals("ByName"))
				sql = "select NSERVERID,CSERVERNAME,CCLASSNAME,"
						+ "CDESCRIPTION,NNAMESPACEID from "
						+ "SERVER_STATEMENT where cservername like '%"
						+ serviceinfo + "%' order by nserverid";

		} else
			sql = "select NSERVERID,CSERVERNAME,CCLASSNAME,"
					+ "CDESCRIPTION,NNAMESPACEID from "
					+ "SERVER_STATEMENT order by nserverid";

		return sql;
	}

	public String getSQL_InsertProcess(String processid, String sid,
			String serverid, String aliasname, String actid) {
		return "insert into PROCESS_SERVERS values(" + processid + ",'" + sid
				+ "','" + serverid + "','" + aliasname + "','" + actid + "')";
	}

	public String getSQL_DeleteProcess(String processid, String sid) {
		return "delete from PROCESS_SERVERS where NPROCESSID=" + processid
				+ " and NSID='" + sid + "'";
	}

	public String getSQL_UpdateProcess(String processid, String sid,
			String serverid, String aliasname, String actid) {
		return "update PROCESS_SERVERS set NSERVERID=" + serverid
				+ ",CALIASNAME='" + aliasname + "', NACTID='" + actid
				+ "' where NPROCESSID=" + processid + " and NSID='" + sid + "'";
	}

	public String getSQL_QueryCountProcessServerForProcessidAndSid(
			String processid, String sid) {
		return "select count(*) from process_servers where NPROCESSID="
				+ processid + " and NSID=" + sid;
	}

	public String getSQL_QueryCountProcessStatementForProcessid(String processid) {
		return "select count(*) from process_statement where NPROCESSID="
				+ processid;
	}

	public String getSQL_DeleteProcessStatement(String processid) {
		return "delete from PROCESS_STATEMENT where NPROCESSID=" + processid;
	}

	public String getSQL_InsertProcessStatement(String processid,
			String processname, String description, String namespace) {
		return "insert into PROCESS_STATEMENT values(" + processid + ",'"
				+ processname + "','" + description + "','" + namespace + "')";
	}

	public String getSQL_UpdateProcessStatement(String processid,
			String processname, String description, String namespace) {
		return "update PROCESS_STATEMENT set CPROCESSNAME='" + processname
				+ "',CDESCRIPTION='" + description + "', NNAMESPACEID='"
				+ namespace + "' where NPROCESSID=" + processid;
	}

	public String getSQL_DeleteAdapter(String processid, String aliasname) {
		return "delete adapter_statement where nprocessid=" + processid
				+ " and cialiasname='" + aliasname + "'";
	}

	// 查询所有流程
	public String getSQL_QueryAllProcess() {
		return "select NPROCESSID,CPROCESSNAME,CDESCRIPTION,nnamespaceid"
				+ " from PROCESS_STATEMENT ";
	}

	public String getSQL_QueryProcess(String processid) {
		return "select NPROCESSID,CPROCESSNAME,CDESCRIPTION,NNAMESPACEID"
				+ " from PROCESS_STATEMENT" + " where nprocessid=" + processid;
	}

	// 查询某个流程的对应的服务信息
	public String getSQL_QueryProcessItem(String id) {
		return "select NPROCESSID ,NSID, NSERVERID , CALIASNAME ,	cdescription from	"
				+ "process_servers , act_statement where "
				+ "process_servers.nactid= act_statement.nactid and nprocessid="
				+ id + " order by nsid";
	}

	// 从适配器信息表中查询所有适配器信息
	public String getSQL_QueryAllAdapterInfos() {
		return "select nprocessid,cialiasname,ciparameter,nsourceid,COALIASNAME,"
				+ "COPARAMETER from adapter_statement order by nprocessid,cialiasname";
	}

	/**
	 * 查询适配器所有信息或根据流程名查询适配器信息
	 * 
	 * @param nprocessname
	 * @return 返回的结果集合包含以下字段<br>
	 *         nprocessid 流程号<br>
	 *         cialiasname 输入服务别名<br>
	 *         ciparameter 输入参数<br>
	 *         nsourceid 数据来源号<br>
	 *         COALIASNAME 输出服务号<br>
	 *         COPARAMETER 输出参数号<br>
	 * @author 于丽达
	 */
	public String getSQL_QueryAllAdapterInfos(String nprocessname) {
		String sql = "";
		if (nprocessname != null && (!nprocessname.equals(""))) {
			sql = "select a.nprocessid,a.cialiasname,a.ciparameter,a.nsourceid,a.COALIASNAME,"
					+ "a.COPARAMETER from adapter_statement a,PROCESS_STATEMENT b where b.CPROCESSNAME='"
					+ nprocessname
					+ "' and a.nprocessid=b.nprocessid order by nprocessid,cialiasname";
		} else {
			sql = "select nprocessid,cialiasname,ciparameter,nsourceid,COALIASNAME,"
					+ "COPARAMETER from adapter_statement order by nprocessid,cialiasname";
		}

		return sql;
	}

	/**
	 * 根据流程号获取流程名
	 * 
	 * @param processid
	 *            流程号
	 * @return 返回的结果集合包含以下字段<br>
	 *         cprocessname 流程名
	 */
	public String getSQL_QueryProcessNameForProcessID(String processid) {
		return "select cprocessname from process_statement where nprocessid="
				+ processid + "";
	}

	/**
	 * 根据服务号获取服务名
	 * 
	 * @param serverid
	 *            服务号
	 * @return 返回的结果集合包含以下字段<br>
	 *         cservername 服务名称
	 */
	public String getSQL_QueryServerNameForServerID(String serverid) {
		return "select cservername from server_statement where nserverid="
				+ serverid + "";
	}

	/**
	 * 根据流程号，服务别名获取服务号
	 * 
	 * @param processid
	 *            流程号
	 * @param serveralias
	 *            服务别名
	 * @return 返回的结果集合包含以下字段<br>
	 *         nserverid 服务号
	 */

	public String getSQL_QueryProcessServerForProcessIDServerAlias(
			String processid, String serveralias) {
		return "select NPROCESSID,NSID,NSERVERID,CALIASNAME,NACTID from "
				+ "process_servers where nprocessid=" + processid
				+ " and caliasname='" + serveralias + "'";
	}

	/**
	 * 根据数据来源号，获取数据来源描述
	 * 
	 * @param sourceid
	 *            数据来源号
	 * @return 返回的结果集合包含以下字段<br>
	 *         cdescription 数据来源描述
	 */
	public String getSQL_QuerySourceDescriptionForSourceID(String sourceid) {
		return "select cdescription from source_statement where nsourceid="
				+ sourceid + "";
	}

	/**
	 * 返回查询所有业务流程信息的SQL语句
	 * 
	 * @return 返回的结果集合包含以下字段<br>
	 *         nprocessid 流程号<br>
	 *         cprocessname 流程名<br>
	 *         CDESCRIPTION 描述<br>
	 *         NNAMESPACEID 命名空间号
	 */
	public String getSQL_QueryAllProcessInfos() {
		return "select nprocessid,cprocessname,CDESCRIPTION,NNAMESPACEID from "
				+ "process_statement order by nprocessid";
	}

	// 返回查询所有数据来源信息的SQL语句
	public String getSQL_QueryAllSourceInfos() {
		return "select nsourceid,cdescription from source_statement";
	}

	// 根据流程号，输入服务别名和输入参数获取适配器信息
	public String getSQL_QueryAdapterInfo(String processid, String aliasname,
			String parametername) {
		return "select nsourceid,COALIASNAME,COPARAMETER from ADAPTER_STATEMENT where NPROCESSID="
				+ processid
				+ " and CIALIASNAME='"
				+ aliasname
				+ "' and CIPARAMETER='" + parametername + "'";
	}

	// 根据流程号，获取该流程的所有适配器信息
	public String getSQL_QueryAdepterInfo(String processid) {
		return "select nprocessid,CIALIASNAME,CIPARAMETER,NSOURCEID, "
				+ "COALIASNAME,COPARAMETER from adapter_statement "
				+ "where nprocessid=" + processid + " order by CIALIASNAME";
	}

	/**
	 * 根据流程号,输入服务别名,输入参数 从适配器信息表中获取记录数
	 * 
	 * @param processid
	 *            流程号
	 * @param serveralias
	 *            服务别名
	 * @param parameter
	 *            参数名
	 * @return 返回的结果集合包含以下字段<br>
	 *         count(*) 记录数
	 */
	public String getSQL_QueryCountAdeptInfo(String processid,
			String serveralias, String parameter) {
		return "select count(*) from ADAPTER_STATEMENT where NPROCESSID="
				+ processid + " and CIALIASNAME='" + serveralias
				+ "' and CIPARAMETER='" + parameter + "'";
	}

	/**
	 * 添加适配器信息
	 * 
	 * @param processid
	 *            流程号
	 * @param i_serveralias
	 *            输入服务别名
	 * @param i_parameter
	 *            输入参数名
	 * @param source
	 *            数据来源号
	 * @param o_serverlias
	 *            输出服务别名
	 * @param o_parameter
	 *            输出参数名
	 * @return
	 */
	public String getSQL_InsertAdapterInfo(String processid,
			String i_serveralias, String i_parameter, String source,
			String o_serverlias, String o_parameter) {
		return "insert into ADAPTER_STATEMENT values(" + processid + ",'"
				+ i_serveralias + "','" + i_parameter + "'," + source + ",'"
				+ o_serverlias + "','" + o_parameter + "')";
	}

	/**
	 * 根据流程号,输入服务别名,输入参数,删除适配器信息
	 * 
	 * @param processid
	 *            流程号
	 * @param i_serveralias
	 *            输入服务别名
	 * @param i_parameter
	 *            输入参数
	 * @return
	 */
	public String getSQL_DeleteAdapterInfo(String processid,
			String i_serveralias, String i_parameter) {
		return "delete from ADAPTER_STATEMENT where NPROCESSID=" + processid
				+ " and CIALIASNAME='" + i_serveralias + "' and CIPARAMETER='"
				+ i_parameter + "'";
	}

	/**
	 * 根据流程号,输入服务别名,输入参数名,更新数据来源号,输出服务别名,输出参数名
	 * 
	 * @param processid
	 *            流程号
	 * @param i_serveralias
	 *            输入服务别名
	 * @param i_parameter
	 *            输入参数名
	 * @param source
	 *            数据来源号
	 * @param o_serverlias
	 *            输出服务别名
	 * @param o_parameter
	 *            输出参数名
	 * @return
	 */
	public String getSQL_UpdateAdapterInfo(String processid,
			String i_serveralias, String i_parameter, String source,
			String o_serverlias, String o_parameter) {
		return "update ADAPTER_STATEMENT set NSOURCEID=" + source
				+ ",COALIASNAME='" + o_serverlias + "', COPARAMETER='"
				+ o_parameter + "' where NPROCESSID=" + processid
				+ " and CIALIASNAME='" + i_serveralias + "' and CIPARAMETER='"
				+ i_parameter + "'";
	}

	/**
	 * 根据流程号,服务别名从流程服务表中获取记录数
	 * 
	 * @param processid
	 *            流程号
	 * @param serveralias
	 *            服务别名
	 * @return 返回的结果集合包含以下字段<br>
	 *         count(*) 记录数
	 */
	public String getSQL_QueryCountProcessServerInfo(String processid,
			String serveralias) {
		return "select count(*) from process_servers where nprocessid="
				+ processid + " and CALIASNAME='" + serveralias + "'";
	}

	/**
	 * 根据服务号,参数及参数类型从服务参数表中获取记录数
	 * 
	 * @param serverid
	 *            服务号
	 * @param parameter
	 *            输入参数名
	 * @param type
	 *            参数类型(I/O)
	 * @return 返回的结果集合包含以下字段<br>
	 *         count(*) 记录数
	 */
	public String getSQL_QueryCountParameterInfo(String serverid,
			String parameter, String type) {
		return "select count(*) from parameter_statement where nserverid="
				+ serverid + " and cparameter='" + parameter + "' and ctype='"
				+ type + "'";
	}

	/**
	 * 根据流程号,服务别名获取运行顺序号
	 * 
	 * @param processid
	 *            流程号
	 * @param aliasname
	 *            服务别名
	 * @return 返回的结果集合包含以下字段<br>
	 *         nsid 顺序号
	 */
	public String getSQL_QuerySID(String processid, String aliasname) {
		return "select nsid from process_servers where nprocessid=" + processid
				+ " and CALIASNAME='" + aliasname + "'";
	}

	public String getSQL_QueryAdepterInfo(String processid, String serviceName) {
		return "select nprocessid,cialiasname,ciparameter,nsourceid,COALIASNAME,COPARAMETER"
				+ " from adapter_statement  where nprocessid="
				+ processid
				+ " and cialiasname='"
				+ serviceName
				+ "'  order by nprocessid,cialiasname";
	}

	// 作者:于丽达
	public String getSQL_QueryProcessStatementInfo(String processid,
			String nnamespaceid) {
		String sql = "";
		if (nnamespaceid.equals("null")) {
			sql = "select NPROCESSID,CPROCESSNAME,CDESCRIPTION "
					+ "from PROCESS_STATEMENT " + "where NPROCESSID='"
					+ processid + "' ";

		} else {
			sql = "select a.NPROCESSID,a.CPROCESSNAME,a.CDESCRIPTION,b.CNAMESPACE "
					+ "from PROCESS_STATEMENT a ,namespace_statement b "
					+ "where a.NPROCESSID='"
					+ processid
					+ "' and b.nnamespaceid='"
					+ new Integer(nnamespaceid)
					+ "'";
		}
		return sql;
	}

	// 于丽达
	public String getSQL_QueryAllProcessStatementIds(String processInfo,
			String method) {
		String sql = "";
		if (processInfo != null && (!processInfo.equals(""))) {
			if (method.equals("ById"))
				sql = "select NPROCESSID,CPROCESSNAME,CDESCRIPTION,NNAMESPACEID from PROCESS_STATEMENT"
						+ " where NPROCESSID='"
						+ processInfo
						+ "' order by nprocessid";
			if (method.equals("ByName"))
				sql = "select NPROCESSID,CPROCESSNAME,CDESCRIPTION,NNAMESPACEID from PROCESS_STATEMENT"
						+ " where cprocessname = '"
						+ processInfo
						+ "'order by nprocessid";
		} else
			sql = "select NPROCESSID,CPROCESSNAME,CDESCRIPTION,NNAMESPACEID from PROCESS_STATEMENT "
					+ "order by nprocessid";
		return sql;
	}

	// 陈鹏
	public String getSQL_QueryProcessServerInfoForProcessidAndSid(
			String processid, String sid) {
		return "select NPROCESSID,NSID,NSERVERID,CALIASNAME,NACTID from "
				+ "PROCESS_SERVERS where NPROCESSID=" + processid
				+ " and nsid=" + sid;
	}

	// 陈鹏
	public String getSQL_QueryProcessServerInfoForProcessid(String processid) {
		return "select NPROCESSID,NSID,NSERVERID,CALIASNAME,NACTID from "
				+ "PROCESS_SERVERS where NPROCESSID=" + processid;
	}

	// 于丽达
	public String getSQL_QueryAllProcessServerInfo(String processInfo,
			String method) {
		String sql = "";
		if (processInfo != null && (!processInfo.equals(""))) {
			if (method.equals("ById")) {
				sql = "select a.nprocessid,a.nsid,b.cprocessname,c.cservername from process_servers a,process_statement b,server_statement c where a.nprocessid=b.nprocessid and a.nserverid=c.nserverid "
						+ "and b.nprocessid='"
						+ processInfo
						+ "' order by a.nprocessid,a.nsid";
			}
			if (method.equals("ByName")) {
				sql = "select a.nprocessid,a.nsid,b.cprocessname,c.cservername from process_servers a,process_statement b,"
						+ "server_statement c where  b.cprocessname='"
						+ processInfo
						+ "' and a.nprocessid=b.nprocessid and a.nserverid=c.nserverid "
						+ "order by a.nprocessid,a.nsid";
			}
		} else
			sql = "select a.nprocessid,a.nsid,b.cprocessname,c.cservername from process_servers a,process_statement b,server_statement c where a.nprocessid=b.nprocessid and a.nserverid=c.nserverid"
					+ " order by a.nprocessid,a.nsid";
		return sql;
	}

	// 于丹

	public String getSQL_QueryAllNameSpaces(String namespaceinfo, String method) {
		String sql = "";
		if (namespaceinfo != null && (!namespaceinfo.equals(""))) {
			// 按功能号查询
			if (method.equals("ById"))
				sql = "select nnamespaceid, cnamespace,cdescription from namespace_statement  where  nnamespaceid ='"
						+ namespaceinfo + "' order by nnamespaceid";
			// 按功能名查询
			if (method.equals("ByName"))
				sql = "select nnamespaceid, cnamespace,cdescription from namespace_statement where cnamespace like '%"
						+ namespaceinfo + "%' order by nnamespaceid";
		} else
			sql = "select nnamespaceid, cnamespace,cdescription from namespace_statement order by nnamespaceid";
		return sql;
	}

	// 于丽达
	public String getSQL_QueryOtherNameSpaces(String cnnamespace) {
		return "select nnamespaceid, cnamespace,cdescription from namespace_statement where cnamespace<>'"
				+ cnnamespace + "' order by nnamespaceid";
	}

	// 于丹
	public String getSQL_InsertNameSpace(int id, String namespace,
			String description) {
		return "insert into namespace_statement values(" + id + ",'"
				+ namespace + "','" + description + "')";
	}

	// 于丹
	public String getSQL_DeleteNameSpace(int id) {
		return "delete from namespace_statement where nnamespaceid =" + id + "";
	}

	// 于丹
	public String getSQL_UpdateNameSpace(int id, String namespace,
			String descripition) {
		return "update namespace_statement set cnamespace ='" + namespace
				+ "', cdescription='" + descripition + "' where nnamespaceid="
				+ id + "";
	}

	// 于丹
	public String getSQL_QueryCountNameSpaceForNameSpace(int id,
			String namespace) {
		return "select count(*) from namespace_statement where cnamespace='"
				+ namespace + "' and nnamespaceid !=" + id + "";
	}

	// 于丹
	public String getSQL_QueryNameSpaceForNameSpace(int id) {
		return "select cnamespace,cdescription from namespace_statement where nnamespaceid='"
				+ id + "'";
	}

	// 于丽达
	public String getSQL_QueryActStatement() {
		return "select nactid,cdescription from act_statement order by nactid";
	}
	//谢静天
	public String getSQl_QueryProcessandserverbynprocessid(int id){
		String sql="select  ps.nprocessid,ps.nsid from  PROCESS_SERVERS ps,process_statement p  " +
				" where p.nprocessid=ps.nprocessid and p.nprocessid="+id;
		return sql;
	}
	//徐嘉
	public String getSQL_AllServices(){
	    String sql = "select NSERVERID,CSERVERNAME,CCLASSNAME,"
			+ "CDESCRIPTION,NNAMESPACEID from "
			+ "SERVER_STATEMENT ";
	    return sql;	
	}
	//徐嘉
	public String getSQL_AllProcessServerInfo(){
		String sql = "select a.nprocessid,a.nsid,b.cprocessname,c.cservername from process_servers a,process_statement b,server_statement c where a.nprocessid=b.nprocessid and a.nserverid=c.nserverid"
					+ " ";
		return sql;
	}
	//徐嘉
	public String getSQL_AllServiceParas(){
		String sql = "select a.nserverid,b.cSERVERNAME,a.CPARAMETER,a.CTYPE from "
			+ "PARAMETER_STATEMENT a inner join server_statement b on a.nserverid= b.nserverid";
    return sql;
	}
    //徐嘉
	 public String getSQL_AllAdapterInfos(){
		String sql="select a.nprocessid,a.cialiasname,a.ciparameter,a.nsourceid,a.COALIASNAME,"
          +" a.COPARAMETER  ,p.cprocessname ,s.cservername"
          +" from adapter_statement a"
          +" inner join PROCESS_STATEMENT p on p.nprocessid=a.nprocessid"
          +" inner join process_servers b on a.nprocessid=b.nprocessid and a.cialiasname = b.caliasname"
          +" inner join server_statement s on s.nserverid=b.nserverid" 
          +"";
		return sql;
	 }
}
