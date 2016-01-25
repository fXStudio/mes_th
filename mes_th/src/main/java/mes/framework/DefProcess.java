package mes.framework;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class DefProcess extends DefElement implements IProcess {

	private List<IProcessItem> pis = new Vector<IProcessItem>();

	private final Log log = LogFactory.getLog(DefProcess.class);

	public ExecuteResult doProcess(IMessage message) {
		List<IProcessItem> pros = new ArrayList<IProcessItem>(5);
		IServiceBus bus = ServiceBusFactory.getInstance();
		log.debug("运行流程：" + getId() + "，" + this.getNameSpace() + "."
				+ this.getName());
		String preStr = "\t";
		for (IProcessItem pi : pis) {
			ExecuteResult result = doProcessItem(bus, pi, message, true, preStr
					+ "运行");

			// 若服务运行结果是异常，则可以根据针对此流程此服务配置的异常处理方式处理。
			log.debug(preStr + "服务运行结果：" + result);
			if (result == ExecuteResult.fail) {
				switch (pi.getExceptionDisposeType()) {
				case rollback:
					log.debug(preStr + "——回滚，流程开始反向运行。");
					rollback(pros, bus, message, preStr);
				case exit:
					log.debug(preStr + "——流程退出。");
					return result;
				case ignore:
					log.debug(preStr + "——忽略，流程继续进行。");
					break;
				}
			}
			pros.add(pi);
		}
		log.debug("流程运行完毕。");
		return ExecuteResult.sucess;
	}

	private void rollback(List<IProcessItem> pros, IServiceBus bus,
			IMessage message, String preStr) {
		log.debug(preStr + "开始流程回滚");
		for (int i = pros.size() - 1; i >= 0; i--) {
			log.debug(preStr
					+ "服务回滚运行结果："
					+ doProcessItem(bus, pros.get(i), message, false, preStr
							+ preStr + "回滚"));
		}
		log.debug(preStr + "结束流程回滚");
	}

	private ExecuteResult doProcessItem(IServiceBus bus, IProcessItem pi,
			IMessage message, boolean isdo, String preStr) {
		log.debug(preStr + "流程项目：" + pi.getServiceName() + "，服务ID："
				+ pi.getServicdId() + "，执行顺序：" + pi.getSort() + "，异常处理方式："
				+ pi.getExceptionDisposeType());
		ExecuteResult result = ExecuteResult.fail;
		IMessage message2 = null;
		try {
			// 获得适配后的消息对象
			message2 = MessageAdapterFactory.getMessage(this.getId(), pi
					.getServiceName(), message);
			if (isdo)
				result = bus.doService(pi.getServicdId(), this.getId(),
						message2);
			else {
				IService ser = ServiceFactory.getInstance(pi.getServicdId());
				if (ser != null)
					result = ser.undoService(message2, getId());
			}
		} catch (Exception e) {
			log.fatal("流程：" + this.getId() + "——" + this.getNameSpace() + "."
					+ this.getName() + "，的服务:" + pi.getServicdId() + ","
					+ pi.getServiceName() + "，非常结束！！，注意：服务不应抛出异常！\n" + "异常信息："
					+ e.getMessage());
			result = ExecuteResult.fail;
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, "服务抛出未知异常",
					pi.getServicdId(), this.getId(), new Date(), e));
		} finally {
			// 若适配成功则令此适配器对象释放对原消息对象的引用，释放资源
			if (message2 != null && message2 instanceof IMessageAdapter) {
				((IMessageAdapter) message2).setSource(null);
			}
		}
		return result;
	}

	public void addProcessItem(IProcessItem pi) {
		pis.add(pi);
	}

}
