package com.qm.mes.tg.dao;

/**
 * @author lida
 * 
 */
public class IDAO_QueryUnitForOracle implements IDAO_QueryUnit {

	public String QueryDays(String date1, String date2) {
		String sql = "select floor(to_date('" + date2
				+ "','yyyy-mm-dd') - to_date('" + date1
				+ "','yyyy-mm-dd')) as days" + " from T_TG_GATHERRECORD ";
		return sql;
	}

	public String QueryOnlineProduct(String date, int gatherid) {
		String sql = "select gr.INT_ID,gr.INT_GATHERID,gr.STR_MATERIELVALUE,gr.INT_USERID,"
				+ "to_char(DAT_DATE,'yyyy-MM-DD HH24:mi:ss')DAT_DATE,gr.STR_MATERIELNAME,ga.str_name "
				+ " from T_TG_GATHERRECORD gr inner join t_tg_gather ga on gr.int_gatherid=ga.int_id"
				+ " where  INT_GATHERID="
				+ gatherid
				+ " and to_date(DAT_DATE)=to_date('" + date + "','yyyy-MM-DD')";
		
		return sql;
	}

	public String QueryProducePathByMaterielValue(String materielvalue) {
		String sql = " select STR_MATERIELVALUE,DAT_DATE,to_char(DAT_DATE,'yyyy-MM-DD HH24:mi:ss') as str_date,STR_MATERIELNAME,INT_GATHERID,INT_USERID "
				+ "from T_TG_GATHERRECORD"
				+ " where STR_MATERIELVALUE='"
				+ materielvalue + "' order by DAT_DATE desc";
		return sql;
	}

	public String QueryAllProducePath() {
		String sql = "select STR_MATERIELVALUE,DAT_DATE,to_char(DAT_DATE,'yyyy-MM-DD HH24:mi:ss') as str_date,STR_MATERIELNAME,INT_GATHERID,INT_USERID "
				+ "from T_TG_GATHERRECORD order by DAT_DATE desc";
		return sql;
	}

	public String QueryProduceUnitComplete(int produnitId, String strDate,
			String endDate) {
		String sql = "select gr.STR_MATERIELVALUE,gr.DAT_DATE,to_char(gr.DAT_DATE,'yyyy-MM-DD HH24:mi:ss') as str_date"
				+ " from T_TG_GATHER g inner join T_TG_GATHERRECORD gr on "
				+ "g.INT_ID=gr.INT_GATHERID where g.INT_PRODUNITID = "
				+ produnitId
				+ " and to_date(gr.DAT_DATE)>=to_date('"
				+ strDate
				+ "','yyyy-MM-DD') and to_date(gr.dat_date)<=to_date('"
				+ endDate + "','yyyy-MM-DD')";
		return sql;
	}

	public String QueryProductRecordByMaterielValue(String materielvalue) {
		String sql = " select gr.STR_MATERIELVALUE,pr.STR_MATERIELVALUE,gr.DAT_DATE,"
				+ "to_char(gr.DAT_DATE,'yyyy-MM-DD HH24:mi:ss') as str_date,gr.int_gatherid,ga.str_name,pr.str_materielname"
				+ " from T_TG_GATHERRECORD gr inner join T_TG_PEDIGREERECORD pr on"
				+ " gr.INT_ID = pr.INT_GATHERRECORDID inner join t_tg_gather ga on  gr.int_gatherid = ga.int_id"
				+ " where gr.STR_MATERIELVALUE ='"
				+ materielvalue
				+ "' order by gr.dat_date desc";
		return sql;
	}

	public String QueryProductRecord_MaterialValue(String materialvalue) {
		String sql = "select gr.STR_MATERIELVALUE,gr.dat_date,to_char(gr.DAT_DATE,'yyyy-MM-DD HH24:mi:ss')as str_date,"
				+ "gr.int_gatherid,ga.str_name,gr.str_materielname from("
				+ "select STR_MATERIELVALUE,dat_date,to_char(DAT_DATE,'yyyy-MM-DD HH24:mi:ss')as str_date,int_gatherid"
				+ ",str_materielname from T_TG_GATHERRECORD where STR_MATERIELVALUE='"
				+ materialvalue
				+ "' order by DAT_DATE desc)gr"
				+ " inner join t_tg_gather ga on gr.int_gatherid=ga.int_id where rownum=1";
		return sql;
	}
	 public String QueryAllstate(int pruduceid){		 
		 String sql="select t.int_id,t.int_produnitid,t.startgo,t.compel,r.int_stateconversionid,"
				+" s.int_fromstate,s.int_tostate,r.defaultexcute"
	            +" from t_tg_gather t "
	            +" inner join t_ra_gatherstaterule r on t.int_id=r.int_gatherid and t.int_produnitid="+pruduceid+" and r.defaultexcute=1"
	            +" inner join t_ra_stateconversion s on s.int_id=r.int_stateconversionid"
	            +" order by t.int_id  ";
		 return sql;
	 }
	 public String QueryAllinstruction(int produnitId, String wh,String strDate,String endDate)
	 { 		  
		 if(wh.equals("()")){
			 wh="(-1)";
			 
		 }
	     String sql= "select t.int_produnitid,t.int_instructorder,t.str_producemarker,t.int_count,t.int_instructstateid"
	       +", r.str_statename,r.str_style,tg.dat_date ,tg.int_gatherid ,g.str_name"
	       +" from t_ra_instruction t"
	       +" inner join t_ra_state r on r.int_id=t.int_instructstateid and t.int_delete=0"   
	       +" inner join (  select tg.* from t_tg_gatherrecord  tg"
	       +" inner join ( select  str_materielvalue,max(Dat_date) as downtime from t_tg_gatherrecord group by str_materielvalue"
	       +" ) sub_tg on tg.str_materielvalue = sub_tg.str_materielvalue and tg.dat_date = sub_tg.downtime"
	       +") tg on t.str_producemarker = tg.str_materielvalue"	    
	       +" left join T_TG_GATHER g on tg.int_gatherid = g.int_id"	    
	       +" where t.int_produnitid="+produnitId+" and t.int_instructstateid in"+wh+""
	       +" and to_date(tg.DAT_DATE)>=to_date('"+ strDate+ "','yyyy-MM-DD')" 
	       +" and to_date(tg.dat_date)<=to_date('"+ endDate + "','yyyy-MM-DD') order by dat_date desc";  	      
	     return sql;
	 }
}
