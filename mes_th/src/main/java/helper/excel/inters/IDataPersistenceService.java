package helper.excel.inters;

import java.util.List;

import helper.excel.entities.FATHBean;

/**
 * 数据持久化接口
 * 
 * @author Ajaxfan
 */
public interface IDataPersistenceService {
	/**
	 * 数据存储
	 * 
	 * @param list
	 * @return
	 */
	public int storeData(List<FATHBean> list) throws Exception;
}
