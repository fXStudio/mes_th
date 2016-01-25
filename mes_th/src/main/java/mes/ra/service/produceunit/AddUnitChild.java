package mes.ra.service.produceunit;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.ra.factory.ProduceUnitFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class AddUnitChild extends AdapterService {

    private Connection conn = null;

    // 主生产单元ID
    private String intId = null;

    // 子生产单元
    private String c_unit = null;

    // 日志
    private final Log log = LogFactory.getLog(AddUnitChild.class);

    @Override
    public boolean checkParameter(IMessage message, String processid) {

            conn = (Connection) message.getOtherParameter("con");
            intId = message.getUserParameterValue("intId");
            c_unit = message.getUserParameterValue("c_unit");

            log.info("主生产单元ID: " + intId + ";子生产单元：" + c_unit);

            if (intId == null || c_unit == null) {
                    message.addServiceException(new ServiceException(ServiceExceptionType.PARAMETERLOST, "输入参数为空",
                                    this.getId(), processid, new java.util.Date(), null));

                    log.fatal("添加子生产单元中-主生产单元id、子生产单元有为空参数");
                    return false;
            }
            return true;
    }

    @Override
    public ExecuteResult doAdapterService(IMessage message, String processid) throws SQLException, Exception {
            try {

                    new ProduceUnitFactory().delCunit(intId, conn);
                    new ProduceUnitFactory().addCunit(intId, c_unit, conn);
                    log.info("添加子生产单元成功");

                    return ExecuteResult.sucess;
            } catch (Exception e) {
                    message.addServiceException(new ServiceException(ServiceExceptionType.DATABASEERROR, "数据库操作异常",
                                    this.getId(), processid, new Date(), null));

                    log.error("数据库异常");
                    e.printStackTrace();
                    return ExecuteResult.fail;
            }
    }

    /*
     * 资源清理
     */
    @Override
    public void relesase() throws SQLException {
            intId = null;
            c_unit = null;
            conn = null;
    }
}
