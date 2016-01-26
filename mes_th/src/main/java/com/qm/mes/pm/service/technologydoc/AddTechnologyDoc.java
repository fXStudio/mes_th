package com.qm.mes.pm.service.technologydoc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.AdapterService;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.ServiceException;
import com.qm.mes.framework.ServiceExceptionType;
import com.qm.mes.pm.bean.TechDocItem;
import com.qm.mes.pm.bean.TechItemFile;
import com.qm.mes.pm.bean.TechnologyDoc;
import com.qm.mes.pm.factory.TechnologyDocFactory;
import com.qm.mes.util.SerializeAdapter;

/**
 * 添加工艺操作说明书
 * 
 * @author Ypeng
 * 
 */
public class AddTechnologyDoc extends AdapterService {
	/**
	 * 获得连接
	 */
	private Connection con = null;
	/**
	 * 工艺操作说明书号
	 */
	private int int_id ;
	/**
	 * 工艺操作说明书名
	 */
	private String name = null;
	/**
	 * 产品类别标示
	 */
	private String materiel = null;
	/**
	 * 描述信息
	 */
	private String description = null;
	/**
	 * String型工艺操作项Map
	 */
	private String str_attr_val = null;
	/**
	 * 工艺操作项Map
	 */
	private HashMap<?, ?> attr_val = new HashMap<String, String>();
	/**
	 * 类型转换对象
	 */
	private SerializeAdapter sa = new SerializeAdapter();
	/**
	 * 工艺操作项数量
	 */
	private int attr_count = 0;
	/**
	 * 用户ID
	 */
	private int userid = 0;
	/**
	 * 日志
	 */
	private final Log log = LogFactory.getLog(AddTechnologyDoc.class);
	/**
	 * 工艺操作说明书名称数量
	 */
	int name_count=0;
	/**
	 * 产品类别标示数量
	 */
	int materiel_count=0;
	/**
	 * 工艺操作说明书工厂
	 */
	TechnologyDocFactory factory = new TechnologyDocFactory();
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		name = message.getUserParameterValue("name");
		materiel = message.getUserParameterValue("materiel");
		description = message.getUserParameterValue("description");
		str_attr_val = message.getUserParameterValue("str_attr_val");
		try {
			attr_val = (HashMap<?,?>)sa.toObject(str_attr_val);
			userid = Integer.parseInt(message.getUserParameterValue("userid"));
			attr_count = Integer.parseInt(message.getUserParameterValue("attr_count"));
			name_count = factory.getTechnologyDocCountByName(name, con);
			materiel_count = factory.getTechnologyDocCountByMateriel(materiel,con);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("UpdateTechnologyDoc的check中发生异常如下："+e.toString());
			return false;
		} 
		if(name_count>0){
			message.addServiceException(new ServiceException(
				ServiceExceptionType.PARAMETERERROR, "工艺操作说明书名称已经存在，请重新输入！", this.getId(),
				processid, new java.util.Date(), null));
			log.fatal("工艺操作说明书名称已经存在");
			return false;
		}
		if(materiel_count>0){
			message.addServiceException(new ServiceException(
				ServiceExceptionType.PARAMETERERROR, "产品类别标示已经存在，请重新输入！", this.getId(),
				processid, new java.util.Date(), null));
			log.fatal("产品类别标示已经存在");
			return false;
		}
		//输出log信息
	    String debug="修改用户ID："+userid+"；名称：" + name + "；"
		+ "描述信息："+description;
	    log.debug("添加工艺操作项时用户提交的参数: " + debug);
	    
		return true;
	}
	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
				TechnologyDoc technologyDoc = new TechnologyDoc();
				technologyDoc.setCreateUID(userid);
				technologyDoc.setName(name);
				technologyDoc.setMateriel(materiel);
				technologyDoc.setDescription(description);
				factory.saveTechnologyDoc(technologyDoc, con);
				log.info("添加工艺操作说明书成功！");
				int_id = factory.getTechnologyDocIdByName(name, con);
				for(int i=1;i<=attr_count;i++){
					TechDocItem techDocItem = new TechDocItem();
					log.debug("第"+i+"个工艺操作项---名字："+attr_val.get("int_itemprodUnitId"+i)+
							"；子件标示："+attr_val.get("str_itemcontent"+i));
					techDocItem.setTechDocId(int_id);
					techDocItem.setProduceUnitId(Integer.parseInt(attr_val.get("int_itemprodUnitId"+i).toString()));
					techDocItem.setContent(attr_val.get("str_itemcontent"+i).toString());
					factory.saveTechDocItem(techDocItem,con);
					if(attr_val.get("file"+i)!=null){
						TechItemFile techItemFile = new TechItemFile();
						techItemFile.setTechDocItemId(factory.getTechDocItemMaxId(con));
						techItemFile.setPathName(attr_val.get("file"+i).toString());
						factory.saveTechItemFile(techItemFile, con);
					}
				}
				log.info("添加工艺操作项成功");
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), processid, new Date(), sqle));
				log.error("数据库异常，中断服务。"+sqle.toString());
				return ExecuteResult.fail;
			} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error("未知异常，中断服务。"+e.toString());
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

	@Override
	public void relesase() throws SQLException {
		con = null;

	}

}
