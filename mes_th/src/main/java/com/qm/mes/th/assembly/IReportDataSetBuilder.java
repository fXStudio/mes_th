package com.qm.mes.th.assembly;

import java.util.List;

import th.pz.bean.JConfigure;

public interface IReportDataSetBuilder {
	public void buildQueryExpression();
	
	/**
	 * 构建业务数据集合
	 */
	public void buildBusinessDataSet();

	/**
	 * @return 返回数据集合
	 */
	public List<JConfigure> getDataSet();
}
