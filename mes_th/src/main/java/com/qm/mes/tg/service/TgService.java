package com.qm.mes.tg.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.qm.mes.tg.bean.GatherRecord;
import com.qm.mes.tg.bean.MaterielRule;
import com.qm.mes.tg.factory.MaterielRuleFactory;
import com.qm.mes.tg.factory.RecordFactory;

public class TgService extends AbstractTgService {

	TgService(int userId, int gatherId) {
		super(userId, gatherId);
	}

	/**
	 * in为采集的数据列表，存储数据，在此过程中将错误存储，并返回一个错误列表， 如果列表长度为零说明保存成功，否则可以将错误信息迭代反馈给调用者
	 */
	public List<String> savaTgRecord(List<String> in) {
		try {
			MaterielRuleFactory imrdao = null;
			//这个Connection是临时的
			Connection con = null;
			// 取得验证规则
			List<MaterielRule> mrs;
			mrs = imrdao.getListByGid(gatherId, con);
			if (mrs.size() == 0) {
				this.saveErrorMessage("不存在验证规则");
				return getErrorList();
			}
			if (validate(in, mrs)) {
				RecordFactory irdao = null;
				GatherRecord gr = new GatherRecord();
				gr.setGatherId(gatherId);
				gr.setUserId(userId);
				irdao.saveRecord(gr, in, mrs, con);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getErrorList();
	}
}
