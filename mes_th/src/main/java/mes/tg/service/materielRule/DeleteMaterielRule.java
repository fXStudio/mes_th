package mes.tg.service.materielRule;

import java.sql.Connection;
import java.sql.ResultSet;
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
import mes.tg.dao.IDAO_MaterielRule;
import mes.tg.factory.MaterielRuleFactory;
import mes.tg.factory.RecordFactory;
import mes.ra.factory.ProduceUnitFactory;


/**
 * 删除物料标识规则
 * 
 * @author lida
 * 
 */
public class DeleteMaterielRule extends AdapterService {
	// 获得连接
	private Connection con = null;
	// 物料标识规则id
	private String int_id = null;

	Statement stmt = null;
	ResultSet rs = null;
	//日志
	private final Log log = LogFactory.getLog(DeleteMaterielRule.class);

	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		int_id = message.getUserParameterValue("int_id");
		//输出log信息
	    String debug="物料标识规则号：" + int_id;
	    log.debug("删除物料标识规则时用户提交的参数: " + debug);
		if (int_id == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("物料标识规则号为空，退出服务。");
			return false;
		}
		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
				IDAO_MaterielRule dao = (IDAO_MaterielRule) DAOFactoryAdapter
						.getInstance(DataBaseType.getDataBaseType(con),
								IDAO_MaterielRule.class);
				stmt = con.createStatement();
				rs = stmt.executeQuery(dao
						.checkGather(Integer.parseInt(int_id)));
				int gather_count = 0;
				if (rs.next()) {
					gather_count = rs.getInt(1);
				}
				rs = stmt.executeQuery(dao.checkGatherItem(Integer
						.parseInt(int_id)));
				int gatherItem_count = 0;
				if (rs.next()) {
					gatherItem_count = rs.getInt(1);
				}
				RecordFactory RecordFactory = new RecordFactory();
				MaterielRuleFactory materielRuleFactory = new MaterielRuleFactory();
				String MaterielRuleName = materielRuleFactory.findMaterielRule(Integer.parseInt(int_id),con).getName();
				int count_MaterielRuleName = RecordFactory.countByMaterielRuleName(MaterielRuleName,con);
				int count_GatherRecord = RecordFactory.countGatherReByMaterielRuleName(MaterielRuleName,con);
				ProduceUnitFactory produceUnitFactory = new ProduceUnitFactory();
				int count_ProduceUnit = produceUnitFactory.countProduceUnitByMateritelRuleID(Integer.parseInt(int_id),con);
				// 在采集点gather表中有关联此物料标识规则号的内容
				if (gather_count > 0) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWN, "该物料标识号在采集点中有应用，不允许删除!\n请在维护采集点中先删除再操作。",
							this.getId(), processid, new Date(), null));
					log.fatal("该物料标识号在采集点中有应用，不允许删除");
					return ExecuteResult.fail;
				}
				//采集点属性gatherItem表中有此物料标识规则号的应用
				else if (gatherItem_count > 0) {
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWN, "该物料标识号在采集点属性中有应用，不允许删除!\n请在维护采集点属性中先删除再操作。",
							this.getId(), processid, new Date(), null));
					log.fatal("该物料标识号在采集点属性中有应用，不允许删除");
					return ExecuteResult.fail;
				}
				//谱系表与物料标识规则表存在外键，谱系表中存在物料标识规则名时不允许删除
				else if(count_MaterielRuleName > 0){
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWN, "谱系中存在此物料标识规则名，不允许删除!",
							this.getId(), processid, new Date(), null));
					log.fatal("谱系中存在此物料标识规则名，不允许删除");
					return ExecuteResult.fail;
				}
				//判断过点记录表中是否有当前物料标识规则名
				else if(count_GatherRecord > 0){
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWN, "过点记录中存在此物料标识规则名，不允许删除!",
							this.getId(), processid, new Date(), null));
					log.fatal("过点记录中存在此物料标识规则名，不允许删除");
					return ExecuteResult.fail;
				}
				//判断生产单元中是否有当前物料标识规则号
				else if(count_ProduceUnit > 0){
					message.addServiceException(new ServiceException(
							ServiceExceptionType.UNKNOWN, "生产单元中存在此物料标识规则名，不允许删除!",
							this.getId(), processid, new Date(), null));
					log.fatal("生产单元中存在此物料标识规则名，不允许删除");
					return ExecuteResult.fail;
				}
				else {
					MaterielRuleFactory factory = new MaterielRuleFactory();
					factory.deleteMaterielRule(Integer.parseInt(int_id), con);
					log.info("删除物料标识规则服务成功！");
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
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
		}
		return ExecuteResult.sucess;
	}

	@Override
	public void relesase() throws SQLException {
		int_id = null;
		con = null;

	}

}
