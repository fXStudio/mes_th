package com.qm.mes.pm.service.produceunitline;


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
import com.qm.mes.pm.bean.ProLineItem;
import com.qm.mes.pm.bean.ProduceUnitLine;
import com.qm.mes.pm.factory.ProduceUnitLineFactory;
import com.qm.mes.util.SerializeAdapter;

/**
 * 更新生产单元线性配置 
 * 
 * @author YuanPeng
 *
 */

public class UpdateProduceUnitLine extends AdapterService {
	/**
	 * 获得连接
	 */
	private Connection con = null;
	/**
	 * 生产单元线性配置号
	 */
	private String int_id ;
	/**
	 * 生产单元线性配置名
	 */
	private String name = null;
	/**
	 * 描述信息
	 */
	private String description = null;
	/**
	 * String型生产单元线性配置Map
	 */
	private String str_attr_val = null;
	/**
	 * 生产单元线性内容Map
	 */
	private HashMap<?, ?> attr_val = new HashMap<String, String>();
	/**
	 * 类型转换对象
	 */
	private SerializeAdapter sa = new SerializeAdapter();
	/**
	 * 生产单元线性配置数量
	 */
	private int attr_count = 0;
	/**
	 * 生产单元线性配置名是否被改变
	 */
	private String change = null;
	/**
	 * 日志
	 */
	private final Log log = LogFactory.getLog(UpdateProduceUnitLine.class);
	/**
	 * 生产单元线性配置名称数量
	 */
	int count=0;
	/**
	 * 生产单元线性配置工厂
	 */
	ProduceUnitLineFactory factory = new ProduceUnitLineFactory();
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		int_id = message.getUserParameterValue("int_id");
		name = message.getUserParameterValue("name");
		description = message.getUserParameterValue("description");
		str_attr_val = message.getUserParameterValue("str_attr_val");
		change = message.getUserParameterValue("change");
		try {
			attr_val = (HashMap<?,?>)sa.toObject(str_attr_val);
			attr_count = Integer.parseInt(message.getUserParameterValue("attr_count"));
			count = factory.getProduceUnitLineCountByName(name, con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(change.equals("false"))
			count--;
		if(count>0){
			message.addServiceException(new ServiceException(
				ServiceExceptionType.PARAMETERERROR, "生产单元线性配置名称已经存在，请重新输入！", this.getId(),
				processid, new java.util.Date(), null));
			log.fatal("生产单元线性配置名称已经存在");
			return false;
		}
		//输出log信息
	    String debug="修改名称：" + name + "；描述信息："+description;
	    log.debug("添加生产单元线性内容时用户提交的参数: " + debug);
	    
		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
				ProduceUnitLine produceUnitLine = new ProduceUnitLine();
				produceUnitLine.setId(Integer.parseInt(int_id));
				produceUnitLine.setName(name);
				produceUnitLine.setDescription(description);
				factory.updateProduceUnitLine(produceUnitLine, con);
				log.info("更新生产单元线性配置成功！");
				factory.delProLineItemByProduceUnitLineId(Integer.parseInt(int_id), con);
				log.info("删除生产单元线性内容成功");
				for(int i=1;i<=attr_count;i++){
					ProLineItem proLineItem = new ProLineItem();
					log.debug("第"+i+"个生产单元线性内容---顺序号："+attr_val.get("int_itemorder"+i)+
							"；生产单元号："+attr_val.get("int_itemprodUnitId"+i));
					proLineItem.setLineId(Integer.parseInt(int_id));
					proLineItem.setOrder(Integer.parseInt(attr_val.get("int_itemorder"+i).toString()));
					proLineItem.setProduceUnitId(Integer.parseInt(attr_val.get("int_itemprodUnitId"+i).toString()));
					factory.saveProLineItem(proLineItem,con);
				}
				log.info("添加生产单元线性内容成功");
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
