package mes.tg.dao;

/**
 * @author Xjia
 * 
 */
public class IDAO_QueryUnitForSqlserver extends IDAO_QueryUnitForOracle {
	public String QueryDays(String date1, String date2) {
		String sql = "select floor(convert(datetime,'"+ date2+ "',20) - convert(datetime,'"+ date1+ "',20)) as days" + " from T_TG_GATHERRECORD ";
		return sql;
	}

	public String QueryOnlineProduct(String date, int gatherid) {
		String sql = "select gr.INT_ID,gr.INT_GATHERID,gr.STR_MATERIELVALUE,gr.INT_USERID,"
				+ "cast(DAT_DATE as datetime)DAT_DATE,gr.STR_MATERIELNAME,ga.str_name "
				+ " from T_TG_GATHERRECORD gr inner join t_tg_gather ga on gr.int_gatherid=ga.int_id"
				+ " where  INT_GATHERID="
				+ gatherid
				+ " and  convert(datetime,DAT_DATE,20)=convert(datetime,'"+ date+ "',20)";
		
		return sql;
	}
	public String QueryProducePathByMaterielValue(String materielvalue) {
		String sql = " select STR_MATERIELVALUE,DAT_DATE,convert(varchar,DAT_DATE,20) as str_date,STR_MATERIELNAME,INT_GATHERID,INT_USERID "
				+ "from T_TG_GATHERRECORD"
				+ " where STR_MATERIELVALUE='"
				+ materielvalue + "' order by DAT_DATE desc";
		return sql;
	}

	public String QueryAllProducePath() {
		String sql = "select STR_MATERIELVALUE,DAT_DATE,convert(varchar,DAT_DATE,20) as str_date,STR_MATERIELNAME,INT_GATHERID,INT_USERID "
				+ "from T_TG_GATHERRECORD order by DAT_DATE desc";
		return sql;
	}

	public String QueryProduceUnitComplete(int produnitId, String strDate,
			String endDate) {
		String sql = "select gr.STR_MATERIELVALUE,gr.DAT_DATE,convert(varchar,gr.DAT_DATE,20) as str_date"
				+ " from T_TG_GATHER g inner join T_TG_GATHERRECORD gr on "
				+ "g.INT_ID=gr.INT_GATHERID where g.INT_PRODUNITID = "
				+ produnitId
				+ " and convert(datetime,gr.DAT_DATE,20)>=convert(datetime,'"+ strDate+ "',20) and convert(datetime,gr.dat_date,20)<=convert(datetime,'"+ endDate+ "',20)";
		return sql;
	}

	public String QueryProductRecordByMaterielValue(String materielvalue) {
		String sql = " select gr.STR_MATERIELVALUE,pr.STR_MATERIELVALUE,gr.DAT_DATE,"
				+ "convert(varchar,gr.DAT_DATE,20) as str_date,gr.int_gatherid,ga.str_name,pr.str_materielname"
				+ " from T_TG_GATHERRECORD gr inner join T_TG_PEDIGREERECORD pr on"
				+ " gr.INT_ID = pr.INT_GATHERRECORDID inner join t_tg_gather ga on  gr.int_gatherid = ga.int_id"
				+ " where gr.STR_MATERIELVALUE ='"
				+ materielvalue
				+ "' order by gr.dat_date desc";
		return sql;
	}

	public String QueryProductRecord_MaterialValue(String materialvalue) {
		String sql = "select top 1 gr.STR_MATERIELVALUE,gr.dat_date,convert(varchar,gr.DAT_DATE,20)as str_date,"
				+ "gr.int_gatherid,ga.str_name,gr.str_materielname from("
				+ "select STR_MATERIELVALUE,dat_date,convert(varchar,DAT_DATE,20)as str_date,int_gatherid"
				+ ",str_materielname from T_TG_GATHERRECORD where STR_MATERIELVALUE='"
				+ materialvalue
				+ "' )gr"
				+ " inner join t_tg_gather ga on gr.int_gatherid=ga.int_id  order by DAT_DATE desc ";
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
	       +"and datediff(day,convert(datetime,'"+strDate+"',20),convert(datetime,tg.DAT_DATE,20))>=0"
	       +"and datediff(day,convert(datetime,'"+ endDate + "',20),convert(datetime,tg.DAT_DATE,20))<=0"
	       +" order by dat_date desc";  	      
	     return sql;
	 }
}
