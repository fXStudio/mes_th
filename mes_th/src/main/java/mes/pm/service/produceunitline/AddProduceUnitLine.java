package mes.pm.service.produceunitline;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.pm.bean.ProLineItem;
import mes.pm.bean.ProduceUnitLine;
import mes.pm.factory.ProduceUnitLineFactory;
import mes.util.SerializeAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AddProduceUnitLine extends AdapterService {
	/**
	 * 获得连接
	 */
	private Connection con = null;
	/**
	 * 生产单元线性配置号
	 */
	private int int_id ;
	/**
	 * 生产单元线性配置名
	 */
	private String name = null;
	/**
	 * 描述信息
	 */
	private String description = null;
	/**
	 * String型生产单元线性内容Map
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
	 * 生产单元线性内容数量
	 */
	private int attr_count = 0;
	/**
	 * 日志
	 */
	private final Log log = LogFactory.getLog(AddProduceUnitLine.class);
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
		name = message.getUserParameterValue("name");
		description = message.getUserParameterValue("description");
		str_attr_val = message.getUserParameterValue("str_attr_val");
		try {
			attr_val = (HashMap<?,?>)sa.toObject(str_attr_val);
			attr_count = Integer.parseInt(message.getUserParameterValue("attr_count"));
			count = factory.getProduceUnitLineCountByName(name, con);
		} catch (Exception e) {
			log.error("AddProduceUnitLine Check中异常："+e.toString());
			e.printStackTrace();
			return false;
		}
		
		if(count>0){
			message.addServiceException(new ServiceException(
				ServiceExceptionType.PARAMETERERROR, "生产单元线性配置名称已经存在，请重新输入！", this.getId(),
				processid, new java.util.Date(), null));
			log.fatal("生产单元线性配置名称已经存在");
			return false;
		}
		//输出log信息
	    String debug="名称：" + name + "；描述信息："+description;
	    log.debug("添加生产单元线性配置时用户提交的参数: " + debug);
	    
		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
				ProduceUnitLine assembleDoc = new ProduceUnitLine();
				assembleDoc.setName(name);
				assembleDoc.setDescription(description);
				factory.saveProduceUnitLine(assembleDoc, con);
				log.info("添加生产单元线性配置服务成功！");
				int_id = factory.getProduceUnitLineIdByName(name, con);
				for(int i=1;i<=attr_count;i++){
					ProLineItem proLineItem = new ProLineItem();
					log.debug("第"+i+"个生产单元线性内容---顺序号："+attr_val.get("int_itemorder"+i)+
							"；生产单元号："+attr_val.get("int_itemprodUnitId"+i));
					proLineItem.setLineId(int_id);
					proLineItem.setOrder(Integer.parseInt(attr_val.get("int_itemorder"+i).toString()));
					proLineItem.setProduceUnitId(Integer.parseInt(attr_val.get("int_itemprodUnitId"+i).toString()));
					factory.saveProLineItem(proLineItem,con);
				}
				log.info("添加生产单元线性内容成功");
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), processid, new Date(), sqle));
				log.error("数据库异常，中断服务。"+sqle.toString());
				return ExecuteResult.fail;
			}
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
