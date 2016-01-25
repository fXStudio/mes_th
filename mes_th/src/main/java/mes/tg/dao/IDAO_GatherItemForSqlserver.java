package mes.tg.dao;

import mes.tg.bean.GatherItem;

public class IDAO_GatherItemForSqlserver extends IDAO_GatherItemForOracle {

	public String saveGatherItem(GatherItem item) {
		String sql = "insert into t_tg_GatherItem(int_gatherid,int_order,int_materielruleid) "
				+ "values("
				+ item.getGatherId()
				+ "," + item.getOrder() + "," + item.getMaterielruleId() + ")";
		return sql;
	}
}
