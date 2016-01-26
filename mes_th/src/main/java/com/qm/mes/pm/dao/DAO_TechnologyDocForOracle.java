package com.qm.mes.pm.dao;

import com.qm.mes.pm.bean.TechDocItem;
import com.qm.mes.pm.bean.TechItemFile;
import com.qm.mes.pm.bean.TechnologyDoc;

public class DAO_TechnologyDocForOracle implements DAO_TechnologyDoc {

	/**
	 * 创建工艺操作说明书的sql语句
	 * 
	 * @param technologyDoc 工艺操作说明书对象
	 * @param CreateUID 创建用户ID
	 * @return
	 */
	public String saveTechnologyDoc(TechnologyDoc technologyDoc){
		String sql = "insert into t_pm_TechnologyDoc(int_id,Str_Name,STR_MATERIEL,Str_Description,Dat_createDate," +
				"Dat_upDate,Int_CreateUID,INT_UPDATEUID,Int_delete) " +
				"values(seq_pm_TechnologyDoc.nextval,'"+technologyDoc.getName()+"','"+technologyDoc.getMateriel()
				+"','"+technologyDoc.getDescription()+"',sysdate,sysdate,"
				+technologyDoc.getCreateUID()+","+technologyDoc.getCreateUID()+",0)";
		return sql;
	}
	
	/**
	 * 通过序号查出工艺操作说明书的sql语句
	 * 
	 * @param id 工艺操作说明书序号
	 * @return 通过序号工艺操作说明书对象
	 */
	public String getTechnologyDocById(int id){
		String sql = "select * from t_pm_TechnologyDoc where int_id ="+id;
		return sql;
	}
	
	/**
	 * 通过序号删除工艺操作说明书的sql语句
	 * 
	 * @param id 工艺操作说明书序号
	 * @return
	 */
	public String delTechnologyDocById(int id){
		String sql = "update t_pm_TechnologyDoc set Int_delete = 1 where int_id ="+id;
		return sql;
	}
	
	/**
	 * 倒叙查询所有工艺操作说明书
	 * 
	 * @return 倒叙查询所有工艺操作说明书的列表
	 */
	public String getAllTechnologyDocsByDESC(){
		String sql = "select * from t_pm_TechnologyDoc where int_delete<>1 order by int_id desc";
		return sql;
	}
	
	/**
	 * 倒叙查询所有工艺操作说明书
	 * 
	 * @return 倒叙查询所有工艺操作说明书的列表
	 */
	public String getAllTechnologyDocs(){
		String sql = "select * from "+
			"(select tpt.INT_ID as tpt_id,tpt.STR_NAME as tpt_name,tpt.STR_MATERIEL as STR_MATERIEL,tpt.STR_DESCRIPTION,"+
			"to_char(tpt.DAT_CREATEDATE,'yyyy-mm-dd hh24:mi:ss') as createdate,"+
			"to_char(tpt.DAT_UPDATE,'yyyy-mm-dd hh24:mi:ss') as updatedate,"+
			"(select cusrname from data_user where tpt.int_createuid = nusrno ) createname,"+
			"(select cusrname from data_user where tpt.int_updateuid = nusrno ) updatename "+ 
			"from T_PM_TECHNOLOGYDOC tpt where tpt.int_delete=0 order by tpt_id asc) a order by tpt_id asc";
		return sql;
	}
	
	/**
	 * 通过工艺操作说明书名查询序号
	 * 
	 * @param name
	 * @return 通过工艺操作说明书名查询的序号
	 */
	public String getTechnologyDocIdByName(String name){
		String sql = "select int_id from t_pm_TechnologyDoc where str_name='"+name+"' and int_delete<>1";
		return sql;
	}
	
	/**
	 * 通过工艺操作说明书名查询装配指示单数量
	 * 
	 * @param name 工艺操作说明书名 
	 * @return 工艺操作说明书数量
	 */
	public String getTechnologyDocCountByName(String name){
		String sql = "select count(*) from t_pm_TechnologyDoc where str_name='"+name+"' and int_delete<>1";
		return sql;
	}
	
	/**
	 * 更新工艺操作说明书
	 * 
	 * @param technologyDoc 工艺操作说明书对象
	 * @return 
	 */
	public String updateTechnologyDoc(TechnologyDoc technologyDoc){
		String sql = "update t_pm_TechnologyDoc set str_name='"+technologyDoc.getName()+"',STR_MATERIEL='"+
		technologyDoc.getMateriel()+
		"', Str_Description='"+technologyDoc.getDescription()+"', Dat_upDate=sysdate"+
		", Int_UpdateUID= "+technologyDoc.getUpdateUID()+" where int_id="+technologyDoc.getId();
		return sql;
	}
	
	/**
	 * 通过产品类别标示查询工艺操作说明书数量
	 * 
	 * @param materiel 产品类别标示
	 * @return 工艺操作说明书数量
	 */
	public String getTechnologyDocCountByMateriel(String materiel){
		String sql = "select count(*) from t_pm_TechnologyDoc where STR_MATERIEL='"+materiel+"' and int_delete<>1";
		return sql;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 创建工艺操作项
	 * 
	 * @param techDocItem 工艺操作项对象
	 * @return
	 */
	public String saveTechDocItem(TechDocItem techDocItem){
		String sql = "insert into t_pm_TechDocItem(int_id,Int_produceUnit,Str_Content,Int_delete,Int_TechDocId)" +
				"values(seq_pm_TechDocItem.nextval,"+techDocItem.getProduceUnitId()+",'"+techDocItem.getContent()
				+"',0,"+techDocItem.getTechDocId()+")";
		return sql;		
	}
	
	/**
	 * 通过工艺操作说明书号查出工艺操作项对象列表的sql语句
	 * 
	 * @param id 工艺操作说明书号
	 * @return 通过工艺操作说明书号查出工艺操作项对象列表
	 */
	public String getTechDocItemByTechnologyDocId(int id){
		String sql = "select * from t_pm_TechDocItem where Int_TechDocId ="+id+" and int_delete<>1";
		return sql;
	}
	
	/**
	 * 通过工艺操作说明书号删除工艺操作项的sql语句
	 * 
	 * @param id 工艺操作说明书序号
	 * @return
	 */
	public String delTechDocItemByTechnologyDocId(int id){
		String sql = "update t_pm_TechDocItem set Int_delete=1 where Int_TechDocId ="+id;
		return sql;
	}
	
	/**
	 * 查询工艺操作项最大序号
	 * 
	 * @return 工艺操作项最大序号
	 */
	public String getTechDocItemMaxId(){
		String sql = "select max(int_id) from t_pm_TechDocItem";
		return sql;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 通过工艺操作项序号查询工艺操作项文件
	 * 
	 * @param techDocItemId 工艺操作项序号
	 * @return 工艺操作项文件
	 */
	public String getTechItemFileByTechDocItemId(int techDocItemId){
		String sql = "select int_ID,int_TECHITEMID,str_PATHNAME from T_PM_TECHDOCITEMFILE where int_TECHITEMID="+techDocItemId;
		return sql;
	}
	
	/**
	 * 创建工艺操作项文件
	 * 
	 * @param tif 工艺操作项文件对象
	 * @return 
	 */
	public String saveTechItemFile(TechItemFile tif){
		String sql = "insert into T_PM_TECHDOCITEMFILE(int_ID,int_TECHITEMID,str_PATHNAME) values(SEQ_PM_TECHDOCITEMFILE.nextval," +
		tif.getTechDocItemId()+",'"+tif.getPathName()+"')";
		return sql;
	}
	
	/**
	 * 通过工艺操作项删除工艺操作项文件
	 * 
	 * @param techDocItemId	工艺操作项序号
	 * @return
	 */
	public String delTechItemFile(int techDocItemId){
		String sql = "delete from T_PM_TECHDOCITEMFILE where int_TECHITEMID="+techDocItemId;
		return sql;
	}
	
	/**
	 * 通过工艺操作说明书序号删除工艺操作项
	 * 
	 * @param techDocId  工艺操作说明书序号
	 * @return
	 */
	public String delTechItemFileByTechDoc(int techDocId){
		String sql = "delete from T_PM_TECHDOCITEMFILE where int_TECHITEMID in(select INT_ID from T_PM_TECHDOCITEM where INT_TECHDOCID="+techDocId+")";
		return sql;
	}
	
	/**
	 * 通过旧工艺操作项序号更新新工艺操作项文件
	 * 
	 * @param oldItemId 旧工艺操作项
	 * @return
	 */
	public String updateTechItemId(int oldItemId){
		String sql = "update T_PM_TECHDOCITEMFILE set int_TECHITEMID=(select max(int_id) from T_PM_TECHDOCITEM) where int_TECHITEMID="+oldItemId;
		return sql;
	}

}