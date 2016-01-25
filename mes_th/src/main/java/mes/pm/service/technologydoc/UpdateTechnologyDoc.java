package mes.pm.service.technologydoc;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.pm.bean.TechDocItem;
import mes.pm.bean.TechItemFile;
import mes.pm.bean.TechnologyDoc;
import mes.pm.factory.TechnologyDocFactory;
import mes.util.SerializeAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UpdateTechnologyDoc extends AdapterService {
	/**
	 * 获得连接
	 */
	private Connection con = null;
	/**
	 * 工艺操作说明书号
	 */
	private String int_id ;
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
	 * String型工艺操作项单Map
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
	 * 生产单元线性配置名是否被改变
	 */
	private String change = null;
	/**
	 * 产品类别标示是否被改变
	 */
	private String change_materiel = null;
	/**
	 * 日志
	 */
	private final Log log = LogFactory.getLog(UpdateTechnologyDoc.class);
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
		int_id = message.getUserParameterValue("int_id");
		name = message.getUserParameterValue("name");
		materiel = message.getUserParameterValue("materiel");
		description = message.getUserParameterValue("description");
		str_attr_val = message.getUserParameterValue("str_attr_val");
		change = message.getUserParameterValue("change");
		change_materiel = message.getUserParameterValue("change_materiel");
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
		//名称在update中未被更改则count-1
		if(change.equals("false"))
			name_count--;
		if(name_count>0){
			message.addServiceException(new ServiceException(
				ServiceExceptionType.PARAMETERERROR, "工艺操作说明书名称已经存在，请重新输入！", this.getId(),
				processid, new java.util.Date(), null));
			log.fatal("工艺操作说明书名称已经存在");
			return false;
		}
		//产品类别标示在update中未被更改则count-1
		if(change_materiel.equals("false"))
			materiel_count--;
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
			try {
				TechnologyDoc technologyDoc = new TechnologyDoc();
				technologyDoc.setId(Integer.parseInt(int_id));
				technologyDoc.setUpdateUID(userid);
				technologyDoc.setName(name);
				technologyDoc.setMateriel(materiel);
				technologyDoc.setDescription(description);
				factory.updateTechnologyDoc(technologyDoc, con);
				log.info("更新工艺操作说明书成功！");
				factory.delTechDocItemByTechnologyDocId(Integer.parseInt(int_id), con);
				log.info("删除工艺操作项成功");
				for(int i=1;i<=attr_count;i++){
					int techItemId = 0;
					TechDocItem techDocItem = new TechDocItem();
					log.debug("第"+i+"个工艺操作项---" +"序号："+attr_val.get("techitemid"+i)+
							"名字："+attr_val.get("int_itemprodUnitId"+i)+
							"；子件描述："+attr_val.get("str_itemcontent"+i));
					
					techDocItem.setId(attr_val.get("techitemid"+i)==null||attr_val.get("techitemid"+i).equals("")?0:Integer.parseInt(attr_val.get("techitemid"+i).toString()));
					techDocItem.setTechDocId(Integer.parseInt(int_id));
					techDocItem.setProduceUnitId(Integer.parseInt(attr_val.get("int_itemprodUnitId"+i).toString()));
					techDocItem.setContent(attr_val.get("str_itemcontent"+i)==null?"":attr_val.get("str_itemcontent"+i).toString());
					factory.saveTechDocItem(techDocItem,con);
					log.debug(attr_val.get("file"+i));
					log.debug(attr_val.get("changefile"+i));
					//判断该工艺操作项是否存在文件
					if(attr_val.get("file"+i)!=null&&!attr_val.get("file"+i).equals("")){
						log.info("第"+i+"个工艺操作项有文件");
						//获得刚刚添加的工艺操作项序号
						techItemId = techDocItem.getId();
						//判断文件更改类型是否为删除
						if(attr_val.get("changefile"+i)!=null&&attr_val.get("changefile"+i).equals("del")){
							//删除该子项的文件
							factory.delTechItemFile(techItemId, con);
							log.info("删除工艺操作项文件成功");
						}
						TechItemFile techItemFile = new TechItemFile();
						techItemFile.setTechDocItemId(factory.getTechDocItemMaxId(con));
						techItemFile.setPathName(attr_val.get("file"+i).toString());
						factory.saveTechItemFile(techItemFile, con);
						log.info("创建工艺操作项文件成功");
					}
					if(attr_val.get("changefile"+i)!=null&&attr_val.get("changefile"+i).equals("")){
							factory.updateTechItemId(techDocItem.getId(),con);
					}
				}
				log.info("添加工艺操作项成功");
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), processid, new Date(), sqle));
				log.error("UpdateTechnologyDoc的do中发生数据库异常如下："+sqle.toString());
				return ExecuteResult.fail;
			}
		} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error("UpdateTechnologyDoc的do中发生异常如下:"+e.toString());
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

	@Override
	public void relesase() throws SQLException {
		con = null;

	}

}