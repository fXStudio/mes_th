package mes.pm.dao;

public class DAO_BomForOracle implements DAO_Bom {
	
	/**
	 * 通过BOM序号查询BOM对象
	 * 
	 * @param id	BOM序号
	 * @return	BOM对象
	 */
	public String getBomById(int id){
		String sql = "select * from t_pm_Bom where int_id="+id;
		return sql;
	}
	
	/**
	 * 通过组件标示查询BOM对象列表
	 * 
	 * @param component 组件标示
	 * @return  BOM对象列表
	 */
	public String getBomsBycomponent(String component){
		String sql = "select * from t_pm_Bom where Str_component="+component;
		return sql;
	}
	
	/**
	 *  通过上级序号标示查询BOM对象
	 * 
	 * @param parentid 上级序号标示
	 * @return BOM对象
	 */
	public String getBomsByParentId(int parentid){
		String sql ="select * from t_pm_Bom where Int_parentid="+parentid;
		return sql;
	}
	
	/**
	 * 	倒叙查询所有Bom
	 * 
	 * @return  倒叙查询所有Bom列表  
	 */
	public String getAllBomsByDESC(){
		String sql = "select * from t_pm_bom order by int_id desc";
		return sql;
	}
	
	/**
	 * 通过组件标示查询下级BOM子项
	 * 
	 * @param Component	组件标示
	 * @return 通过组件标示查询下级BOM子项列表
	 */
	public String getBomByComponent(String Component){ 
	String sql = "Select * From T_PM_BOM Start With STR_COMPONENT='"+Component+"' Connect By Prior INT_ID=INT_PARENTID order by STR_COMPONENT";
		return sql;
	}
	
	/**
	 * 查询所有BOM组件标识按组件标识排序
	 * 
	 * @return 查询所有BOM组件标识按组件标识排序列表
	 */
	public String getBomsGroupByComponent(){
		String sql = "Select STR_COMPONENT,min(STR_DISCRIPTION) as STR_DISCRIPTION From T_PM_BOM group by STR_COMPONENT order by STR_COMPONENT";
		return sql;
	}
	
	
}
