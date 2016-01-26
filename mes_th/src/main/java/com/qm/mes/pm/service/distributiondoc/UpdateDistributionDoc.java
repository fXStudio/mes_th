package com.qm.mes.pm.service.distributiondoc;


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
import com.qm.mes.pm.bean.DistriItem;
import com.qm.mes.pm.bean.DistributionDoc;
import com.qm.mes.pm.factory.DistributionDocFactory;
import com.qm.mes.util.SerializeAdapter;

/**
 * 更新配送指示单
 * 
 * @author Ypeng
 * UpDistributionDoc
 */
public class UpdateDistributionDoc extends AdapterService {
	/**
	 * 获得连接
	 */
	private Connection con = null;
	/**
	 * 配送指示单号
	 */
	private String int_id ;
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
	 * String型配送指示单Map
	 */
	private String str_attr_val = null;
	/**
	 * 配送指示单Map
	 */
	private HashMap<?, ?> attr_val = new HashMap<String, String>();
	/**
	 * 类型转换对象
	 */
	private SerializeAdapter sa = new SerializeAdapter();
	/**
	 * 配送指示单数量
	 */
	private int attr_count = 0;
	/**
	 * 用户ID
	 */
	private int userid = 0;
	/**
	 * 配送指示单名是否被改变
	 */
	private String change = null;
	/**
	 * 日志
	 */
	private final Log log = LogFactory.getLog(UpdateDistributionDoc.class);
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
		con = (Connection) message.getOtherParameter("con");
		int_id = message.getUserParameterValue("int_id");
		name = message.getUserParameterValue("name");
		materiel = message.getUserParameterValue("materiel");
		description = message.getUserParameterValue("description");
		str_attr_val = message.getUserParameterValue("str_attr_val");
		change = message.getUserParameterValue("change");
		try {
			requestProUnit = Integer.parseInt(message.getUserParameterValue("requestProUnit"));
			responseProUnit = Integer.parseInt(message.getUserParameterValue("responseProUnit"));
			targetProUnit = Integer.parseInt(message.getUserParameterValue("targetProUnit"));
			attr_val = (HashMap<?,?>)sa.toObject(str_attr_val);
			userid = Integer.parseInt(message.getUserParameterValue("userid"));
			attr_count = Integer.parseInt(message.getUserParameterValue("attr_count"));
			count = factory.getDistributionDocCountByName(name, con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(change.equals("false"))
			count--;
		if(count>0){
			message.addServiceException(new ServiceException(
				ServiceExceptionType.PARAMETERERROR, "配送指示单名称已经存在，请重新输入！", this.getId(),
				processid, new java.util.Date(), null));
			log.fatal("配送指示单名称已经存在");
			return false;
		}
		//输出log信息
	    String debug="修改用户ID："+userid+"；名称：" + name + "；"+ "物料类型标示："+materiel+ ";"
		+ "描述信息："+description;
	    log.debug("添加配送物料项时用户提交的参数: " + debug);
	    
		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
				DistributionDoc distributionDoc = new DistributionDoc();
				distributionDoc.setId(Integer.parseInt(int_id));
				distributionDoc.setUpdateUID(userid);
				distributionDoc.setName(name);
				distributionDoc.setMaterielType(materiel);
				distributionDoc.setDescription(description);
				distributionDoc.setRequest_proUnit(requestProUnit);
				distributionDoc.setResponse_proUnit(responseProUnit);
				distributionDoc.setTarget_proUnit(targetProUnit);
				factory.updateDistributionDoc(distributionDoc, con);
				log.info("更新配送指示单成功！");
				factory.delDistriItemByDistributionDocId(Integer.parseInt(int_id), con);
				log.info("删除配送物料项成功");
				for(int i=1;i<=attr_count;i++){
					DistriItem distriItem = new DistriItem();
					log.debug("第"+i+"个配送物料项---名字："+attr_val.get("str_itemname"+i)+
							"；物料类型标示："+attr_val.get("str_itemmatitem"+i)+
							"；物料数量："+attr_val.get("int_itemcount"+i)+"；描述："+attr_val.get("str_itemdes"+i));
					distriItem.setDistributionDocId(Integer.parseInt(int_id));
					distriItem.setName(attr_val.get("str_itemname"+i).toString());
					distriItem.setMatitem(attr_val.get("str_itemmatitem"+i).toString());
					distriItem.setCount(Integer.parseInt(attr_val.get("int_itemcount"+i).toString()));
					distriItem.setDescription(attr_val.get("str_itemdes"+i).toString());
					factory.saveDistriItem(distriItem,con);
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
