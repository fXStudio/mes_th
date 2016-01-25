package mes.tg.service.gatheritem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.tg.bean.GatherItem;
import mes.tg.factory.GatherFactory;
import mes.tg.factory.GatherItemFactory;
import mes.util.SerializeAdapter;

/**
 * 添加采集点属性
 * 
 * @author lida
 * 
 */
public class AddGatherItem extends AdapterService {

	// 获得连接
	private Connection con = null;

	// 采集点号
	private String int_gatherid = null;

	// 属性个数
	private String attr_count = null;

	// 主物料标识规则号
	private String int_materielruleid = null;

	// 属性存到map中(顺序号,物料标识号),将map对象转化成String类型
	HashMap<String, String> attr_val = new HashMap<String, String>();
	SerializeAdapter sa = new SerializeAdapter();
	//日志
	private final Log log = LogFactory.getLog(AddGatherItem.class);

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		int_gatherid = message.getOutputParameterValue("int_gatherid");
		attr_count = message.getUserParameterValue("attr_count");
		int_materielruleid = message
				.getUserParameterValue("int_materielruleid");
		try {
			//将参数转换为Map<顺序号><物料标识号>类型
			attr_val = (HashMap<String, String>) sa.toObject(message
					.getUserParameterValue("str_attr_val"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		//输出log信息
	    String debug="采集点号：" + int_gatherid + "；"
		+ "物料标识规则号："+int_materielruleid+ ";"+"采集点属性个数："+attr_count;
	    if(Integer.parseInt(attr_count)!=0)debug+=";采集点属性：\n";
	    for(int j=1;j<=Integer.parseInt(attr_count);j++){
	    	debug+="采集点属性顺序号："+attr_val.get("int_order"+j)+";";
	    	debug+="采集点属性物料规则："+attr_val.get("int_materialruleid"+j);
	    	if(j!=Integer.parseInt(attr_count))debug+=";\n";
	    }
	    log.debug("添加采集点时用户提交的参数: " + debug);
	    
		if (int_gatherid == null || attr_count == null || attr_val == null
				|| int_materielruleid == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("采集点号、采集点属性数量、采集点属性、物料标识规则有为空参数，退出服务。");
			return false;
		}

		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
				GatherItemFactory factory = new GatherItemFactory();
				GatherFactory factory1 = new GatherFactory();
				//循环取出Map值并创建采集点属性
				for (int i = 1; i <= Integer.parseInt(attr_count); i++) {
					GatherItem gatherItem = new GatherItem();
					gatherItem.setGatherId(new Integer(int_gatherid));
					gatherItem.setOrder(new Integer(attr_val.get("int_order"
							+ i)));
					gatherItem.setMaterielruleId(new Integer(attr_val
							.get("int_materialruleid" + i)));
					int fcount = 0;
					fcount = factory1.checkGatherItemByMaterialId(Integer
							.parseInt(int_gatherid), new Integer(attr_val
							.get("int_materialruleid" + i)), con);
					if (fcount > 0) {
						message.addServiceException(new ServiceException(
								ServiceExceptionType.UNKNOWN,
								"生产单元中该采集点子物料规则是其主物料规则号!", this.getId(),
								processid, new Date(), null));
						log.fatal("生产单元中该采集点子物料规则是其主物料规则号。");
						continue;
					} else {
						int count = 0;
						count = factory.checkGatherItemByOrder(Integer
								.parseInt(int_gatherid), new Integer(attr_val
								.get("int_order" + i)), con);
						int count1 = 0;
						count1 = factory.checkGatherItemBySubMaterialId(Integer
								.parseInt(int_gatherid), new Integer(attr_val
								.get("int_materialruleid" + i)), con);
						if (count > 0) {
							message.addServiceException(new ServiceException(
									ServiceExceptionType.UNKNOWN,
									"生产单元中该采集点已经存在此顺序号内容!", this.getId(),
									processid, new Date(), null));
							log.fatal("生产单元中该采集点已经存在此顺序号内容。");
							return ExecuteResult.fail;
						} else if (count1 > 0) {
							message.addServiceException(new ServiceException(
									ServiceExceptionType.UNKNOWN,
									"生产单元中该采集点已经存在此物料规则号内容!", this.getId(),
									processid, new Date(), null));
							log.fatal("生产单元中该采集点已经存在此物料规则号内容。");
							return ExecuteResult.fail;
						} else {
							factory.saveGatherItem(gatherItem, con);
							log.info("添加采集点服务成功！");
						}
					}
				}
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
		int_gatherid = null;
		attr_count = null;
		attr_val = null;
		con = null;

	}

}
