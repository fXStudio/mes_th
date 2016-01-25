package mes.tg.service.gather;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mes.framework.AdapterService;
import mes.framework.DataBaseType;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.system.dao.DAOFactoryAdapter;
import mes.tg.bean.Gather;
import mes.tg.dao.IDAO_Gather;
import mes.tg.factory.GatherFactory;

/**
 * 修改采集点
 * 
 * @author lida
 * 
 */
public class UpdateGather extends AdapterService {
	// 获得连接
	private Connection con = null;
	// 采集点号
	private String int_id = null;
	// 采集点名
	private String str_name = null;
	// 描述信息
	private String str_desc = null;
	// 生产单元号
	private String int_produnitid = null;
	// 物料标识号
	private String int_materielruleid = null;
	//质量状态序号
	private String quality_status = null;
	//日志
	private final Log log = LogFactory.getLog(UpdateGather.class);
	
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		int_id = message.getUserParameterValue("int_id");
		str_name = message.getUserParameterValue("str_name");
		str_desc = message.getUserParameterValue("str_desc");
		int_produnitid = message.getUserParameterValue("int_produnitid");
		int_materielruleid = message.getUserParameterValue("int_materielruleid");
		quality_status = message.getUserParameterValue("quality_check");
		//输出log信息
	    String debug="采集点ID："+int_id+"采集点名：" + str_name + "；"+ "生产单元号："+int_produnitid+ ";"
		+ "物料标识规则号："+int_materielruleid+ ";"+"采集点描述："+str_desc+ "；质量状态号" +  "："+quality_status;
	    log.debug("更新采集点时用户提交的参数: " + debug);
		
		if (str_name == null || str_desc == null || int_produnitid == null
				|| int_materielruleid == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("采集点名、生产单元号、物料标识规则号、采集点描述中有为空参数，退出服务。");
			return false;
		}
	
		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
				GatherFactory factory = new GatherFactory();
				Gather gather = new Gather();
				gather.setId(new Integer(int_id));
				gather.setName(str_name);
				gather.setDesc(str_desc);
				gather.setProdunitId(new Integer(int_produnitid));
				gather.setMaterielruleId(new Integer(int_materielruleid));
				factory.updateGather(gather, con);
				log.info("更新采集点服务成功！");
				
				IDAO_Gather dao = (IDAO_Gather) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_Gather.class);
				Statement stmt = con.createStatement();
				String[] qualitys = quality_status.split(","); // 质量拆分成组
				// 先删除采集点和质量关系
				log.debug("删除qualitys:"+dao.delGather_Q(Integer.parseInt(int_id)));
				stmt.execute(dao.delGather_Q(Integer.parseInt(int_id)));
				log.info("删除采集点和质量关系成功！");
				
                // 添加采集点和质量的关系
				for (int j = 0; j < qualitys.length; j++) {
				log.debug("创建qualitys: "+dao.saveGather_Q((Integer.parseInt(int_id)),(Integer.parseInt(qualitys[j])),j+1));
				stmt.execute(dao.saveGather_Q((Integer.parseInt(int_id)),(Integer.parseInt(qualitys[j])),j+1));
				}
				log.info("添加采集点和质量的关系成功！");
				
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
		int_id = null;
		str_name = null;
		str_desc = null;
		int_produnitid = null;
		int_materielruleid = null;
		con = null;

	}

}
