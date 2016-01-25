package mes.framework.dao;

public class DAO_CoreForOracle extends DAO_Core {

	// 查看单个服务信息
	public String getSQL_QueryServiceForServiceid(int serviceid) {
		return "select a.NSERVERID,a.CSERVERNAME,a.CCLASSNAME,"
				+ "a.CDESCRIPTION,nvl(b.CNAMESPACE,'null') as CNAMESPACE,"
				+ "a.NNAMESPACEID "
				+ "from SERVER_STATEMENT a  left join namespace_statement b on a.NNAMESPACEID=b.NNAMESPACEID  where  a. NSERVERID="
				+ serviceid + "";

	}

	public String getSQL_QueryNextServiceId() {
		return "select nvl(max(nserverid),0)+1 as serviceid from SERVER_STATEMENT";
	}

	public String getSQL_QueryNextProcessid() {
		return "select nvl(max(nprocessid),0)+1 from process_statement";
	}

	public String getSQL_QueryNextNameSpaceId() {
		return "select nvl(max(nnamespaceid),0)+1 as nnamespaceid from namespace_statement";
	}
	/**
	 * 查询业务流程服务表
	 * 
	 * @return 1、流程id <br>
	 *         
	 * @author 谢静天
	 */
	public String getSQl_QueryProcessandserverbynprocessid(int id){
		String sql="select  ps.nprocessid,ps.nsid from  PROCESS_SERVERS ps,process_statement p  " +
				" where p.nprocessid=ps.nprocessid and p.nprocessid="+id;
		return sql;
	}
}
