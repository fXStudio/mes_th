package th.report.api;

import th.report.entities.ReportBaseInfo;

/**
 * 报表基本信息构建
 * 
 * @author Ajaxfan
 */
public interface IReportBaseInfoBuilder {
	/**
	 * 总装信息
	 */
	public void buildDAInfo();

	/**
	 * 最大架子号
	 */
	public void buildMaxCarNo();

	/**
	 * 天合零件号
	 */
	public void buildTFassId();

	/**
	 * 单子号
	 */
	public void buildMaxPageNo();

	/**
	 * 底盘号
	 */
	public void buildChassisNumber();

	/**
	 * VIN与车型的对照关系
	 */
	public void buildVinAndCarTypePair();

	/**
	 * @return 报表基本信息
	 */
	public ReportBaseInfo getReportBaseInfo();
}
