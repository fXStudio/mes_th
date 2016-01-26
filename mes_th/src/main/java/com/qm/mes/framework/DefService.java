package com.qm.mes.framework;

/**
 * 抽象的服务，此服务实现了服务接口中的相对通用的方法。<br>
 * 便于用户更方便的创建服务。<br>
 * <font color="red"><b>建议继承</b></font>
 * 
 * @author 张光磊 2007-6-21
 */
public abstract class DefService extends DefElement implements IService {

	private String className = "";

	public String getClassName() {
		return className;
	}

	public void setClassName(String classname) {
		this.className = classname;
	}

	@SuppressWarnings("deprecation")
	public ExecuteResult redoService(IMessage message, String processid) {
		this.undoService(message, processid);
		this.doService(message, processid);
		return ExecuteResult.sucess;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see zgl.realtool.mes.framework.IService#undoService(zgl.realtool.mes.framework.IMessage,
	 *      java.lang.String)
	 */
	public ExecuteResult undoService(IMessage message, String processid) {
		return ExecuteResult.fail;
	}

}
