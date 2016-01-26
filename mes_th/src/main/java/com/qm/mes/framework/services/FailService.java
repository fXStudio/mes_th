package com.qm.mes.framework.services;

import com.qm.mes.framework.DefService;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.IService;

/**
 * 这是一个返回失败状态的类型，用于测试流程回滚的。
 * @author 张光磊
 * 2007-12-18
 */
public class FailService extends DefService implements IService {

	public ExecuteResult doService(IMessage message, String processid) {
		return ExecuteResult.fail;
	}
}
