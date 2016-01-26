package com.qm.mes.tg.service;

import java.util.ArrayList;
import java.util.List;

import com.qm.mes.tg.bean.MaterielRule;
import com.qm.mes.tg.util.IMaterielValidate;

public abstract class AbstractTgService implements ITgService {
	List<String> errorList = new ArrayList<String>();// 错误列表
	List<String> validateList = new ArrayList<String>();// 验证信息的错误列表
	int userId;
	int gatherId;

	/**
	 * 将用户，采集点与该采集事件绑定
	 * 
	 * @param userId
	 *            用户id
	 * @param gatherId
	 *            采集点序号
	 */
	AbstractTgService(int userId, int gatherId) {
		this.userId = userId;
		this.gatherId = gatherId;
	}

	public List<String> getErrorList() {
		errorList.addAll(validateList);
		return errorList;
	}

	/**
	 * 存储错误信息至errorList
	 */
	public void saveErrorMessage(String message) {
		if (message != null) {
			errorList.add(message);
		}
	}

	/**
	 * 数据验证方法
	 */
	public boolean validate(List<String> in, List<MaterielRule> validates) {
		boolean result = true;
		if (in == null || in.size() == 0) {
			validateList.add("输入参数不正确");
			return false;
		}
		if (validates == null || validates.size() == 0) {
			validateList.add("验证参数不正确");
			return false;
		}
		if (in.size() != validates.size()) {
			validateList.add("输入与验证不匹配");
			return false;
		}
		for (int i = 0; i < validates.size(); i++) {
			MaterielRule mr = validates.get(i);
			IMaterielValidate mv = null;
			try {
				mv = (IMaterielValidate) Class.forName(mr.getValidate())
						.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if (!mv.validate(in.get(i))) {
				if (result)
					result = false;
				validateList.add(mr.getName() + ":" + in.get(i) + "  没有通过验证");
			}
		}

		return result;
	}
}
