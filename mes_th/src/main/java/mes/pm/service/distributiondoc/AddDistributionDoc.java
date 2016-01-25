package mes.pm.service.distributiondoc;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.pm.bean.DistriItem;
import mes.pm.bean.DistributionDoc;
import mes.pm.factory.DistributionDocFactory;
import mes.util.SerializeAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AddDistributionDoc extends AdapterService {
	/**
	 * 获得连接
	 */
	private Connection con = null;
	/**
	 * 配送指示单号
	 */
	private int int_id ;
	/**
	 * 配送指示单名
	 */
	private String name = null;
	/**
	 * 物料类型标示
	 */
	private String materiel = null;
	/**
	 * 描述信息
	 */
	private String description = null;
	/**
	 * 请求生产单元
	 */
	private int requestProUnit ;
	/**
	 * 响应生产单元
	 */
	private int responseProUnit;
	/**
	 * 接收物料生产单元
	 */
	private int targetProUnit;
	/**
	 * String型配送物料项Map
	 */
	private String str_attr_val = null;
	/**
	 * 配送物料项Map
	 */
	private HashMap<?, ?> attr_val = new HashMap<String, String>();
	/**
	 * 类型转换对象
	 */
	private SerializeAdapter sa = new SerializeAdapter();
	/**
	 * 配送物料项数量
	 */
	private int attr_count = 0;
	/**
	 * 用户ID
	 */
	private int userid = 0;
	/**
	 * 日志
	 */
	private final Log log = LogFactory.getLog(AddDistributionDoc.class);
	/**
	 * 配送指示单名称数量
	 */
	int count=0;
	/**
	 * 配送指示单工厂
	 */
	DistributionDocFactory factory = new DistributionDocFactory();
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		//接收参数
		con = (Connection) message.getOtherParameter("con");
		name = message.getUserParameterValue("name");
		materiel = message.getUserParameterValue("materiel");
		description = message.getUserParameterValue("description");
		requestProUnit = Integer.parseInt(message.getUserParameterValue("requestProUnit"));
		responseProUnit = Integer.parseInt(message.getUserParameterValue("responseProUnit"));
		targetProUnit = Integer.parseInt(message.getUserParameterValue("targetProUnit"));
		str_attr_val = message.getUserParameterValue("str_attr_val");
		try {
			//转换为配送物料项Map
			attr_val = (HashMap<?,?>)sa.toObject(str_attr_val);
			userid = Integer.parseInt(message.getUserParameterValue("userid"));
			attr_count = Integer.parseInt(message.getUserParameterValue("attr_count"));
			count = factory.getDistributionDocCountByName(name, con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//判断名称是否已经存在
		if(count>0){
			message.addServiceException(new ServiceException(
				ServiceExceptionType.PARAMETERERROR, "配送指示单名称已经存在，请重新输入！", this.getId(),
				processid, new java.util.Date(), null));
			log.fatal("配送指示单名称已经存在");
			return false;
		}
		//输出log信息
	    String debug="创建用户ID："+userid+"；名称：" + name + "；"+ "物料类型标示："+materiel+ ";"
		+ "描述信息："+description;
	    log.debug("添加配送指示单时用户提交的参数: " + debug);
	    
		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
				DistributionDoc distributionDoc = new DistributionDoc();
				distributionDoc.setCreateUID(userid);
				distributionDoc.setName(name);
				distributionDoc.setMaterielType(materiel);
				distributionDoc.setDescription(description);
				distributionDoc.setRequest_proUnit(requestProUnit);
				distributionDoc.setResponse_proUnit(responseProUnit);
				distributionDoc.setTarget_proUnit(targetProUnit);
				factory.saveDistributionDoc(distributionDoc, con);
				log.info("添加配送指示单服务成功！");
				//通过名字查询序号
				int_id = factory.getDistributionDocIdByName(name, con);
				//循环创建配送指示项
				for(int i=1;i<=attr_count;i++){
					DistriItem DistriItem = new DistriItem();
					log.debug("第"+i+"个配送物料项---名字："+attr_val.get("str_itemname"+i)+
							"；物料标示："+attr_val.get("str_itemmatitem"+i)+
							"；物料数量："+attr_val.get("int_itemcount"+i)+
							"；描述："+attr_val.get("str_itemdes"+i));
					DistriItem.setDistributionDocId(int_id);
					DistriItem.setName(attr_val.get("str_itemname"+i).toString());
					DistriItem.setCount(Integer.parseInt(attr_val.get("int_itemcount"+i).toString()));
					DistriItem.setMatitem(attr_val.get("str_itemmatitem"+i).toString());
					DistriItem.setDescription(attr_val.get("str_itemdes"+i).toString());
					factory.saveDistriItem(DistriItem,con);
				}
				log.info("添加配送物料项成功");
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), processid, new Date(), sqle));
				log.error("数据库异常，中断服务。");
				return ExecuteResult.fail;
			}
		} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error("未知异常，中断服务。");
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

	@Override
	public void relesase() throws SQLException {
		con = null;

	}

}
