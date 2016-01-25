package mes.pm.dao;

import mes.pm.bean.AssDocItem;
import mes.pm.bean.AssembleDoc;

public class DAO_AssembleDocForOracle implements DAO_AssembleDoc {

	/**
	 * 创建装配指示单的sql语句
	 * 
	 * @param assembleDoc 装配指示单对象
	 * @param CreateUID 创建用户ID
	 * @return
	 */
	public String saveAssembleDoc(AssembleDoc assembleDoc){
		String sql = "insert into t_pm_AssembleDoc(int_id,Str_Name,Int_delete,Str_materiel," +
				"Str_description,Dat_createDate,Dat_upDate,Int_CreateUID,Int_UpdateUID) " +
				"values(seq_pm_ASSEMBLEDOC.nextval,'"+assembleDoc.getName()+"',0,'"+
				assembleDoc.getMateriel()+"','"+assembleDoc.getDescription()+"',sysdate,sysdate,"
				+assembleDoc.getCreateUID()+","+assembleDoc.getCreateUID()+")";
		return sql;
	}
	
	/**
	 * 通过序号查出装配指示单的sql语句
	 * 
	 * @param id 装配指示单序号
	 * @return 通过序号查出装配指示单对象
	 */
	public String getAssembleDocById(int id){
		String sql = "select * from t_pm_AssembleDoc where int_id ="+id;
		return sql;
	}
	
	/**
	 * 通过序号删除装配指示单的sql语句
	 * 
	 * @param id 装配指示单序号
	 * @return
	 */
	public String delAssembleDocById(int id){
		String sql = "update t_pm_AssembleDoc set int_delete=1 where int_id ="+id;
		return sql;
	}
	
	/**
	 * 通过物料标示查出装配指示单的sql语句
	 * 
	 * @param materiel 物料标示 
	 * @return 通过物料标示查出装配指示单对象
	 */
	public String getAssembleDocByMateriel(String materiel){
		String sql = "select * from t_pm_AssembleDoc where Str_materiel ='"+materiel+"' and int_delete<>1";
		return sql;
	}
	
	/**
	 * 倒叙查询所有装配指示单
	 * 
	 * @return 倒叙查询所有装配指示单的列表
	 */
	public String getAllAssembleDocsByDESC(){
		String sql = "select tpm.*,to_char(tpm.DAT_CREATEDATE,'yyyy-mm-dd hh24:mi:ss') as createdate,"+
			"to_char(tpm.DAT_UPDATE,'yyyy-mm-dd hh24:mi:ss') as updatedate,"+
			"(select cusrname from data_user where tpm.int_createuid = nusrno ) createname,"+
			"(select cusrname from data_user where tpm.int_updateuid = nusrno ) updatename "+ 
			"from t_pm_assembledoc tpm "+
			"where tpm.int_delete=0 order by int_id desc";
		System.out.println(sql);
		return sql;
	}
	
	/**
	 * 倒叙查询所有装配指示单
	 * 
	 * @return 倒叙查询所有装配指示单的列表
	 */
	public String getAllAssembleDocs(){
		String sql = "select tpm.*,to_char(tpm.DAT_CREATEDATE,'yyyy-mm-dd hh24:mi:ss') as createdate,"+
			"to_char(tpm.DAT_UPDATE,'yyyy-mm-dd hh24:mi:ss') as updatedate,"+
			"(select cusrname from data_user where tpm.int_createuid = nusrno ) createname,"+
			"(select cusrname from data_user where tpm.int_updateuid = nusrno ) updatename "+ 
			"from t_pm_assembledoc tpm "+
			"where tpm.int_delete=0 order by int_id asc";
		return sql;
	}
	
	/**
	 * 通过装配指示单名查询序号
	 * 
	 * @param name
	 * @return 通过装配指示单名查询的序号
	 */
	public String getAssembleDocIdByName(String name){
		String sql = "select int_id from t_pm_assembledoc where str_name='"+name+"' and int_delete<>1";
		return sql;
	}
	
	/**
	 * 通过装配指示单名查询装配指示单数量
	 * 
	 * @param name 装配指示单名 
	 * @return 装配指示单数量
	 */
	public String getAssembleDocCountByName(String name){
		String sql = "select count(*) from t_pm_assembledoc where str_name='"+name+"' and int_delete<>1";
		return sql;
	}
	
	/**
	 * 更新装配指示单
	 * 
	 * @param assembleDoc 装配指示单对象
	 * @return 
	 */
	public String updateAssembleDoc(AssembleDoc assembleDoc){
		String sql = "update t_pm_assembledoc set str_name='"+assembleDoc.getName()+"', Str_materiel='"+
		assembleDoc.getMateriel()+"', Str_description='"+assembleDoc.getDescription()+"', Dat_upDate=sysdate"+
		", Int_UpdateUID= "+assembleDoc.getUpdateUID()+" where int_id="+assembleDoc.getId();
		return sql;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 创建装配指示单号
	 * 
	 * @param assDocItem 装配指示单对象
	 * @return
	 */
	public String saveAssDocItem(AssDocItem assDocItem){
		String sql = "insert into t_pm_AssDocItem(int_id,Int_AssDocId,Str_Name,Str_code,Str_description)" +
				"values(seq_pm_ASSDOCITEM.nextval,"+assDocItem.getAssDocId()+",'"+assDocItem.getName()+"','"+
				assDocItem.getCode()+"','"+assDocItem.getDescription()+"')";
		return sql;		
	}
	
	/**
	 * 通过装配指示单号查出装配指示项列表的sql语句
	 * 
	 * @param id 装配指示单号
	 * @return 通过装配指示单号查出装配指示项列表
	 */
	public String getAssDocItemByAssembleDocId(int id){
		String sql = "select * from t_pm_AssDocItem where Int_AssDocId ="+id;
		return sql;
	}
	
	/**
	 * 通过装配指示单号删除装配指示项的sql语句
	 * 
	 * @param id 装配指示单序号
	 * @return
	 */
	public String delAssDocItemByAssembleDocId(int id){
		String sql = "delete from t_pm_AssDocItem where Int_AssDocId ="+id;
		return sql;
	}

}
