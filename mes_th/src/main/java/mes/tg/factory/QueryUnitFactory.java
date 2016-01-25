package mes.tg.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mes.framework.DataBaseType;
import mes.system.dao.DAOFactoryAdapter;
import mes.tg.bean.GatherRecord;
import mes.tg.dao.IDAO_QueryUnit;

/**
 * @author lida
 *
 */
public class QueryUnitFactory {

	//日志
	private final Log log = LogFactory.getLog(QueryUnitFactory.class);
	
	@SuppressWarnings("unchecked")
	public List getProducePathByGMID(String materielvalue,Connection con) throws SQLException{
		List list = new ArrayList();
		IDAO_QueryUnit dao = (IDAO_QueryUnit) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), IDAO_QueryUnit.class);
		Statement stmt = con.createStatement();
		log.debug("通过物料值查询生产路径"+dao.QueryProducePathByMaterielValue(materielvalue));
		ResultSet rs = stmt.executeQuery(dao.QueryProducePathByMaterielValue(materielvalue));
		if(rs.next()){
			GatherRecord gr = new GatherRecord();
			gr.setMaterielValue(rs.getString("STR_MATERIELVALUE"));
			gr.setDate(rs.getDate("DAT_DATE"));
			log.debug("物料值："+rs.getString(1)+"；过点日期："+rs.getDate(2));
			list.add(gr);
		}
		if(rs!=null){
			rs.close();
			rs=null;
		}
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
		return list;
	}

}
