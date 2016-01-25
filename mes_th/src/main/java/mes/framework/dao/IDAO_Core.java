package mes.framework.dao;

public interface IDAO_Core {
	/**
	 * 删除适配器
	 * 
	 * @param processid
	 *            流程号
	 * @param aliasname
	 *            服务别名
	 * @return 根据流程号和服务别名删除出适配器
	 */
	String getSQL_DeleteAdapter(String processid, String aliasname);

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
			String i_serveralias, String i_parameter);

	/**
	 * 删除流程服务
	 * 
	 * @param processid
	 *            流程号
	 * @param sid
	 *            运行顺序号
	 * @return 按流程号和运行顺序号删除流程服务
	 */
	String getSQL_DeleteProcess(String processid, String sid);

	/**
	 * 删除流程定义
	 * 
	 * @param processid
	 *            流程号
	 * @return 根据流程号删除流程定义表中的数据
	 */
	String getSQL_DeleteProcessStatement(String processid);

	/**
	 * 删除服务定义信息
	 * 
	 * @param serviceid
	 *            服务号
	 * @return 删除一条服务定义信息
	 */
	String getSQL_DeleteService(int serviceid);

	/**
	 * 删除服务参数
	 * 
	 * @param serviceid
	 *            服务号
	 * @param paratype
	 *            参数类型
	 * @param paraname
	 *            参数名
	 * @return 删除一条服务参数
	 */
	String getSQL_DeleteServicePara(int serviceid, String paratype,
			String paraname);

	/**
	 * 添加适配器信息
	 * 
	 * @param 1、
	 *            processid 流程号
	 * @param 2、
	 *            i_serveralias 输入服务别名
	 * @param 3、
	 *            i_parameter 输入参数名
	 * @param 4、
	 *            source 数据来源号
	 * @param 5、
	 *            o_serverlias 输出服务别名
	 * @param 6、
	 *            o_parameter 输出参数名
	 * @return
	 */
	public String getSQL_InsertAdapterInfo(String processid,
			String i_serveralias, String i_parameter, String source,
			String o_serverlias, String o_parameter);

	/**
	 * 插入流程服务
	 * 
	 * @param 1、
	 *            processid 流程号
	 * @param 2、
	 *            sid 运行顺序号
	 * @param 3、
	 *            serverid 服务号
	 * @param 4、
	 *            aliasname 服务别名
	 * @param 5、
	 *            actid 异常处理
	 * @return 插入流程服务
	 */
	String getSQL_InsertProcess(String processid, String sid, String serverid,
			String aliasname, String actid);

	/**
	 * 插入流程定义表
	 * 
	 * @param 1、processid
	 *            流程号
	 * @param 2、processname
	 *            流程名
	 * @param 3、description
	 *            业务描述
	 * @param 4、namespace
	 *            命名空间
	 * @return
	 */
	String getSQL_InsertProcessStatement(String processid, String processname,
			String description, String namespace);

	/**
	 * 添加服务定义信息
	 * 
	 * @param 1、
	 *            serviceid 服务号
	 * @param 2、
	 *            servicename 服务名
	 * @param 3、
	 *            classname 类名
	 * @param 4、
	 *            servicedesc 服务描述
	 * @param 5、
	 *            namespace 命名空间
	 * @return 插入一条服务信息
	 */

	String getSQL_InsertService(int serviceid, String servicename,
			String classname, String servicedesc, String namespace);

	/**
	 * 添加服务参数
	 * 
	 * @param 1、
	 *            serviceid 服务号
	 * @param 2、
	 *            paraname 参数名
	 * @param 3、
	 *            paratype 参数类型
	 * @param 4、
	 *            paradesc 参数描述
	 * @return
	 */
	String getSQL_InsertServicePara(int serviceid, String paraname,
			String paratype, String paradesc);

	/**
	 * 根据流程号，输入服务别名和输入参数获取适配器信息
	 * 
	 * @param processid
	 *            流程号
	 * @param aliasname
	 *            输入服务别名
	 * @param parametername
	 *            输入参数名
	 * @return 返回的结果集合包含以下字段<br>
	 *         1、 NSOURCEID 数据来源号 <br>
	 *         2、 COALIASNAME 输出服务别名<br>
	 *         3、 COALIASNAME 输出参数
	 */
	public String getSQL_QueryAdapterInfo(String processid, String aliasname,
			String parametername);

	/**
	 * 根据流程号，获取该流程的所有适配器信息
	 * 
	 * @param processid
	 *            流程号
	 * @return 返回的结果集合包含以下字段<br>
	 *         1、 nprocessid 流程号 <br>
	 *         2、 CIALIASNAME 输入服务别名<br>
	 *         3、 CIPARAMETER 输入参数<br>
	 *         4、 NSOURCEID 数据来源号 <br>
	 *         5、 COALIASNAME 输出服务别名<br>
	 *         6、 COALIASNAME 输出参数
	 */
	public String getSQL_QueryAdepterInfo(String processid);

	/**
	 * 查询消息适配器
	 * 
	 * @param processid
	 *            流程id
	 * @param serviceName
	 *            服务别名
	 * @return 通过指定的流程id和服务别名查询 。返回的结果集合包含以下字段<br>
	 *         1、nprocessid 流程号<br>
	 *         2、cialiasname 输入服务别名<br>
	 *         3、ciparameter 输入参数<br>
	 *         4、nsourceid 数据来源号<br>
	 *         5、COALIASNAME 输出服务号<br>
	 *         6、COPARAMETER 输出参数号<br>
	 */
	String getSQL_QueryAdepterInfo(String processid, String serviceName);

	/**
	 * 从适配器信息表中查询所有适配器信息
	 * 
	 * @return 返回的结果集合包含以下字段<br>
	 *         1、 nprocessid 流程号<br>
	 *         2、 cialiasname 输入服务别名<br>
	 *         3、 ciparameter 输入参数<br>
	 *         4、 nsourceid 数据来源号<br>
	 *         5、 COALIASNAME 输出服务号<br>
	 *         6、 COPARAMETER 输出参数号<br>
	 */
	public String getSQL_QueryAllAdapterInfos();

	/**
	 * 从适配器信息表中查询所有适配器信息
	 * 
	 * @return 返回的结果集合包含以下字段<br>
	 *         1、nprocessid 流程号<br>
	 *         2、cialiasname 输入服务别名<br>
	 *         3、ciparameter 输入参数<br>
	 *         4、nsourceid 数据来源号<br>
	 *         5、COALIASNAME 输出服务号<br>
	 *         6、COPARAMETER 输出参数号<br>
	 */
	public String getSQL_QueryAllAdapterInfos(String nprocessname);

	/**
	 * 查询所有流程
	 * 
	 * @return 结果集包含一下字段<br>
	 *         1、NPROCESSID 流程号 <br>
	 *         2、CPROCESSNAME 流程名<br>
	 *         3、CDESCRIPTION 业务描述 <br>
	 *         4、CNAMESPACE 命名空间
	 */
	String getSQL_QueryAllProcess();

	/**
	 * 返回查询所有业务流程信息的SQL语句
	 * 
	 * @return 返回的结果集合包含以下字段<br>
	 *         1、nprocessid 流程号<br>
	 *         2、cprocessname 流程名<br>
	 *         3、CDESCRIPTION 描述<br>
	 *         4、CNAMESPACE 命名空间
	 */
	public String getSQL_QueryAllProcessInfos();

	/**
	 * 查询服务参数列表
	 * 
	 * @return 结果集包含以下字段<br>
	 *         1、nserverid 服务号<br>
	 *         2、cSERVERNAME 服务名<br>
	 *         3、CPARAMETER 参数名<br>
	 *         4、CTYPE 参数类型<br>
	 */
	String getSQL_QueryAllServiceParas(String info, String method);

	/**
	 * 查询服务列表
	 * 
	 * @return 结果集包含以下字段 <br>
	 *         1、NSERVERID 服务号 <br>
	 *         2、CSERVERNAME 服务名 <br>
	 *         3、CCLASSNAME 类名<br>
	 *         4、CDESCRIPTION 服务描述 <br>
	 *         5、CNAMESPACE 命名空间
	 */
	String getSQL_QueryAllServices(String serviceinfo, String method);

	/**
	 * 返回查询所有数据来源信息的SQL语句
	 * 
	 * @return 返回的结果集合包含以下字段<br>
	 *         1、nsourceid 数据来源号<br>
	 *         2、cdescription 数据来源描述
	 */
	public String getSQL_QueryAllSourceInfos();

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
			String serveralias, String parameter);

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
			String parameter, String type);

	/**
	 * 检查流程服务表是否有重复数据
	 * 
	 * @param processid
	 *            流程号
	 * @param sid
	 *            运行顺序号
	 * @return 根据流程号，运行顺序号检验记录条数<br>
	 *         count(*)
	 */
	String getSQL_QueryCountProcessServerForProcessidAndSid(String processid,
			String sid);

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
			String serveralias);

	/**
	 * 检查流程定义表是否有重复数据
	 * 
	 * @param processid
	 *            流程号
	 * @return 根据流程号检查流程定义表中记录条数
	 */
	String getSQL_QueryCountProcessStatementForProcessid(String processid);

	/**
	 * 验证服务是否被某一流程使用
	 * 
	 * @param serviceid
	 *            服务号
	 * @return 该服务应用于流程的个数
	 */
	String getSQL_QueryCountServiceIsUsed(int serviceid);

	/**
	 * 验证同一服务同类型的参数是否唯一
	 * 
	 * @param serviceid
	 *            服务号
	 * @param paraname
	 *            参数名
	 * @param paratype
	 *            参数类型
	 * @return 同一服务同类型同名的参数的个数
	 */
	String getSQL_QueryCountServiceParaIsUnique(int serviceid, String paraname,
			String paratype);

	/**
	 * 返回流程定义表中最大的流程号
	 * 
	 * @return 返回流程定义表中最大的流程号
	 */
	String getSQL_QueryNextProcessid();

	/**
	 * 创建服务号，值为当前服务号最大值加1
	 * 
	 * @return 服务号
	 */
	String getSQL_QueryNextServiceId();

	/**
	 * 查询某一个流程信息
	 * 
	 * @param processid
	 *            流程id
	 * @return 结果集包含一下字段<br>
	 *         1、NPROCESSID 流程号 <br>
	 *         2、CPROCESSNAME 流程名<br>
	 *         3、CDESCRIPTION 业务描述 <br>
	 *         4、CNAMESPACE 命名空间
	 */
	String getSQL_QueryProcess(String processid);

	/**
	 * 查询某个流程的对应的服务信息
	 * 
	 * @param id
	 *            流程id
	 * @return 返回的结果集合包含以下字段<br>
	 *         1、NPROCESSID 流程号<br>
	 *         2、NSID 运行顺序号<br>
	 *         3、NSERVERID 服务号<br>
	 *         4、CALIASNAME 服务别名<br>
	 *         5、cdescription 异常处理的描述信息
	 */
	String getSQL_QueryProcessItem(String id);

	/**
	 * 根据流程号获取流程名
	 * 
	 * @param processid
	 *            流程号
	 * @return 返回的结果集合包含以下字段<br>
	 *         cprocessname 流程名
	 * @deprecated 这个方法结果集合被方法getSQL_QueryProcess(String)返回的集合包含，不建议使用
	 */
	public String getSQL_QueryProcessNameForProcessID(String processid);

	/**
	 * 根据服务号获取服务名
	 * 
	 * @param serverid
	 *            服务号
	 * @return 返回的结果集合包含以下字段<br>
	 *         cservername 服务名称
	 * @deprecated 这个方法结果集合被方法getSQL_QueryServiceForServiceid返回的集合包含，不建议使用
	 */
	public String getSQL_QueryServerNameForServerID(String serverid);

	/**
	 * 查看单个服务信息
	 * 
	 * @param serviceid
	 *            服务号
	 * @return 结果集包含以下字段 <br>
	 *         1、NSERVERID 服务号 <br>
	 *         2、CSERVERNAME 服务名 <br>
	 *         3、CCLASSNAME 类名<br>
	 *         4、CDESCRIPTION 服务描述 <br>
	 *         5、CNAMESPACE 命名空间
	 */
	String getSQL_QueryServiceForServiceid(int serviceid);

	/**
	 * 查看单个服务参数信息
	 * 
	 * @param serviceid
	 *            服务号<br>
	 * @param paratype
	 *            参数类型<br>
	 * @param paraname
	 *            参数名<br>
	 * @return CPARAMETER 参数名<br>
	 *         CTYPE 参数类型<br>
	 *         CDESCRIPTION 描述
	 */
	String getSQL_QueryServicePara(int serviceid, String paratype,
			String paraname);

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
	public String getSQL_QuerySID(String processid, String aliasname);

	/**
	 * 根据数据来源号，获取数据来源描述
	 * 
	 * @param sourceid
	 *            数据来源号
	 * @return 返回的结果集合包含以下字段<br>
	 *         cdescription 数据来源描述
	 */
	public String getSQL_QuerySourceDescriptionForSourceID(String sourceid);

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
			String o_serverlias, String o_parameter);

	/**
	 * 更新流程服务
	 * 
	 * @param processid
	 *            流程号
	 * @param sid
	 *            运行顺序号
	 * @param serverid
	 *            服务号
	 * @param aliasname
	 *            服务别名
	 * @param actid
	 *            异常处理
	 * @return 根据流程号，运行顺序号更新流程服务
	 */
	String getSQL_UpdateProcess(String processid, String sid, String serverid,
			String aliasname, String actid);

	/**
	 * 更新流程定义表
	 * 
	 * @param processid
	 *            流程号
	 * @param processname
	 *            流程名
	 * @param description
	 *            业务描述
	 * @param namespace
	 *            命名空间
	 * @return 根据流程号更新流程定义表
	 */
	String getSQL_UpdateProcessStatement(String processid, String processname,
			String description, String namespace);

	/**
	 * 更新服务定义信息
	 * 
	 * @param serviceid
	 *            服务号
	 * @param servicename
	 *            服务名
	 * @param classname
	 *            类名
	 * @param servicedesc
	 *            服务描述
	 * @param namespace
	 *            命名空间
	 * @return 更新一条服务定义信息
	 */
	String getSQL_UpdateService(int serviceid, String servicename,
			String classname, String servicedesc, int namespace);

	/**
	 * 更新服务参数
	 * 
	 * @param serviceid
	 *            服务号
	 * @param paratype
	 *            更新后的参数类型
	 * @param setparatype
	 *            更新前的参数类型
	 * @param paraname
	 *            更新后的参数名
	 * @param setparaname
	 *            更新前的参数名
	 * @param paradesc
	 *            更新后的参数描述
	 * @return 更新一条服务参数
	 */
	String getSQL_UpdateServicePara(int serviceid, String paratype,
			String setparatype, String paraname, String setparaname,
			String paradesc);

	/**
	 * 根据流程号查询流程定义表中字段
	 * 
	 * @param processid
	 *            流程id <br>
	 * @param nnamespaceid
	 *            命名空间id<br>
	 * @return 返回结果集包含以下字段<br>
	 *         1、NPROCESSID 流程号<br>
	 *         2、CPROCESSNAME 流程名<br>
	 *         3、CDESCRIPTION 流程描述<br>
	 *         4、CNAMESPACE 命名空间 <br>
	 * @author 于丽达 <br>
	 */
	public String getSQL_QueryProcessStatementInfo(String processidInfo,
			String nnamespaceid);

	/**
	 * 查询流程号和流程名
	 * 
	 * @return 返回结果集包含以下字段<br>
	 *         1、NPROCESSID 流程号<br>
	 *         2、CPROCESSNAME 流程名<br>
	 *         3、CDESCRIPTION 描述<br>
	 *         4、NNAMESPACEID 命名空间号
	 * @author 陈鹏
	 */
	public String getSQL_QueryAllProcessStatementIds(String processInfo,
			String method);

	/**
	 * 根据流程号运行顺序号查询流程服务信息
	 * 
	 * @param Processid
	 *            流程号
	 * @param Sid
	 *            运行顺序号
	 * @return 返回以下字段<br>
	 *         1、NPROCESSID 流程号<br>
	 *         2、NSID 运行顺序号<br>
	 *         3、NSERVERID 服务号<br>
	 *         4、CALIASNAME 服务别名<br>
	 *         5、NACTID 异常处理
	 * @author 陈鹏
	 */
	public String getSQL_QueryProcessServerInfoForProcessidAndSid(
			String processid, String sid);

	/**
	 * 根据流程号查询流程服务信息
	 * 
	 * @param Processid
	 *            流程号
	 * @param Sid
	 *            运行顺序号
	 * @return 返回以下字段<br>
	 *         1、NPROCESSID 流程号<br>
	 *         2、NSID 运行顺序号<br>
	 *         3、NSERVERID 服务号<br>
	 *         4、CALIASNAME 服务别名<br>
	 *         5、NACTID 异常处理<br>
	 * @author 陈鹏
	 */
	public String getSQL_QueryProcessServerInfoForProcessid(String processid);

	/**
	 * 根据流程号或流程名查询流程服务信息
	 * 
	 * @param processid
	 *            流程号
	 * @param processname
	 *            流程名
	 * @return 返回以下字段<br>
	 *         1、nprocessid 流程号<br>
	 *         2、nsid 运行顺序号<br>
	 *         3、cprocessname 流程名<br>
	 *         4、cservername 服务名<br>
	 * @author 于丽达
	 */
	public String getSQL_QueryAllProcessServerInfo(String processInfo,
			String method);

	/**
	 * 根据流程号和服务别名查询流程服务信息
	 * 
	 * @param Processid
	 *            流程号
	 * @param CALIASNAME
	 *            服务别名
	 * @return 返回以下字段<br>
	 *         1、NPROCESSID 流程号<br>
	 *         2、NSID 运行顺序号<br>
	 *         3、NSERVERID 服务号<br>
	 *         4、CALIASNAME 服务别名<br>
	 *         5、NACTID 异常处理<br>
	 * @author 陈鹏
	 */
	String getSQL_QueryProcessServerForProcessIDServerAlias(String processid,
			String serveralias);

	/**
	 * 查询所有的命名空间名称及其描述
	 * 
	 * @param namespaceinfo
	 *            命名空间信息
	 * @param method
	 *            查询方式
	 * @return 结果集包含以下字段 <br>
	 *         1、nnamespaceid 命名空间号<br>
	 *         2、cnamespace 命名空间<br>
	 *         3、cdescription 描述
	 */
	String getSQL_QueryAllNameSpaces(String namespaceinfo, String method);

	/**
	 * 查询除己选定以外的命名空间号、名
	 * 
	 * @param cnnamespace
	 * @return 结果集包含以下字段<br>
	 *         1、 nnamespaceid 命名空间号<br>
	 *         2、 cnnamespace 命名空间名<br>
	 * @author 于丽达
	 */
	String getSQL_QueryOtherNameSpaces(String cnnamespace);

	/**
	 * 根据命名空间号查询命名空间信息
	 * 
	 * @param NameSpace
	 *            命名空间
	 * @return 结果集包含以下字段 <br>
	 *         1、nnamespaceid 命名空间号<br>
	 *         2、cnamespace 命名空间<br>
	 *         3、cdescription 描述
	 */
	String getSQL_QueryNameSpaceForNameSpace(int id);

	/**
	 * 添加新的命名空间
	 * 
	 * @param namespace
	 *            命名空间
	 * @param description
	 *            描述
	 * @return
	 */
	String getSQL_InsertNameSpace(int id, String namespace, String description);

	/**
	 * 删除命名空间
	 * 
	 * @param id
	 *            命名空间号
	 * @return
	 */
	String getSQL_DeleteNameSpace(int id);

	/**
	 * 更新命名空间
	 * 
	 * @param id
	 *            更新的命名空间对应的命名空间号
	 * @param namespace
	 *            命名空间
	 * @param descripition
	 *            描述
	 * @return
	 */
	String getSQL_UpdateNameSpace(int id, String namespace, String descripition);

	/**
	 * 验证命名空间名称
	 * 
	 * @param NameSpace
	 *            命名空间
	 * @return 结果集包含以下字段 <br>
	 *         count(*) 记录数
	 */
	String getSQL_QueryCountNameSpaceForNameSpace(int id, String namespace);

	/**
	 * 生成命名空间号
	 * 
	 * @param id
	 *            命名空间号
	 * @return
	 */
	String getSQL_QueryNextNameSpaceId();

	/**
	 * 查询流程定义中未使用的命名空间 <br>
	 * 
	 * @param nnamespaceid
	 *            使用的命名空间id <br>
	 * @return 1、nNameSpaceId 命名空间id <br>
	 *         2、cNameSpace 命名空间名称 <br>
	 * 
	 */
	String getSQL_QueryNameSpaceForProcessStatement(String nnamespaceid);

	/**
	 * 查询流程定义中所有的命名空间 <br>
	 * 
	 * @return 1、nNameSpaceId 命名空间id <br>
	 *         2、cNameSpace 命名空间名称
	 * @author 于丽达
	 */
	String getSQL_QueryNameSpaceForProcessStatement();

	/**
	 * 查询所有异常处理类型
	 * 
	 * @return 1、nactid 异常id <br>
	 *         2、cdescription 异常描述
	 * @author 于丽达
	 */
	String getSQL_QueryActStatement();
	/**
	 * 查询业务流程服务表
	 * 
	 * @return 1、流程id <br>
	 *         
	 * @author 谢静天
	 */
	String getSQl_QueryProcessandserverbynprocessid(int id);
	/**
	 * 查询server_statement表（因关键字查询加）
	 * 
	 * @return sql <br>
	 *         
	 * @author 徐嘉
	 */
	 String getSQL_AllServices();
	 /**
	 * 查询server_statement、process_servers等表
	 * @return sql <br>
	 *         
	 * @author 徐嘉
	 */
	 String getSQL_AllProcessServerInfo();
	 /**
	 * 查询参数表
	 * 
	 * @return sql <br>
	 *         
	 * @author 徐嘉
	 */
	 String getSQL_AllServiceParas();
	 /**
	  * 查询适配表
	  * 
	  * @return sql <br>
	  *         
	  * @author 徐嘉
	  */
	 String getSQL_AllAdapterInfos();
	 
}
     
