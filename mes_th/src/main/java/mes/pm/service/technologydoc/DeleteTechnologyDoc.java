package mes.pm.service.technologydoc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.pm.factory.TechnologyDocFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DeleteTechnologyDoc extends AdapterService {
    /**
     * 数据库连接对象
     */
    Connection con = null;
    /**
     * 工艺操作说明书号
     */
    private String int_id = null;
	/**
	 * 日志
	 */
	private final Log log = LogFactory.getLog(DeleteTechnologyDoc.class);
	/**
	 * 工艺操作说明书工厂
	 */
	TechnologyDocFactory tdFactory = new TechnologyDocFactory();
    /**
     * 垃圾回收
     *
     * @throws java.sql.SQLException
     */
    @Override
    public void relesase() throws SQLException {
        con = null;
        int_id = null;
    }

    /**
     * 检查参数
     *
     * @param message
     *              使用IMessage传递相关属性
     * @param processid
     *              流程ID
     * @return boolean值
     *              返回boolean值以显示成功与否
     */
    @Override
    public boolean checkParameter(IMessage message, String processid) {
        con = (Connection) message.getOtherParameter("con");
        int_id = message.getUserParameterValue("int_id");
        if (int_id == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			return false;
		}

        return true;
    }

    /**
     * 执行服务
     *
     * @param message
     *              使用IMessage传递相关属性
     * @param processid
     *              流程ID
     * @return ExecuteResult
     *                  执行结果
     * @throws java.sql.SQLException
     *                          抛出SQL异常
     * @throws java.lang.Exception
     *                          抛出其他异常
     */
    @Override
    public ExecuteResult doAdapterService(IMessage message, String processid) throws SQLException, Exception {
        try {
			try {
				//仅逻辑删除工艺操作说明书，并未对工艺操作项项进行删除
				tdFactory.delTechnologyDocById(Integer.parseInt(int_id), con);
				log.info("删除工艺操作说明书成功！");
				//通过工艺操作说明书序号删除工艺操作项文件
				tdFactory.delTechItemFileByTechDoc(Integer.parseInt(int_id), con);
				log.info("删除工艺操作项文件成功！");
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), processid, new Date(), sqle));
				log.error("数据库异常");
				return ExecuteResult.fail;
			}
		} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error("未知异常");
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
    }
}
