package com.qm.mes.th.assembly.entities;

import java.util.ArrayList;
import java.util.List;

import com.qm.mes.th.assembly.IReportOrder;

import th.pz.bean.JConfigure;
import th.pz.bean.PrintSet;

/**
 * 装备单生产订单
 * 
 * @author Ajaxfan
 */
public class ReportOrder implements IReportOrder {
    /** 报表的打印参数信息 */
    private PrintSet printSet;
    /** 请求打印的报表信息 */
    private RequestParam requestParam;
    /** 报表的基本信息 */
    private ReportBaseInfo reportBaseInfo;

    /** 报表数据集合 */
    private List<JConfigure> datas = new ArrayList<JConfigure>();

    public PrintSet getPrintSet() {
        return printSet;
    }

    public void setPrintSet(PrintSet printSet) {
        this.printSet = printSet;
    }

    public RequestParam getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(RequestParam requestParam) {
        this.requestParam = requestParam;
    }

    public ReportBaseInfo getReportBaseInfo() {
        return reportBaseInfo;
    }

    public void setReportBaseInfo(ReportBaseInfo reportBaseInfo) {
        this.reportBaseInfo = reportBaseInfo;
    }

    public void add(JConfigure configure) {
        datas.add(configure);
    }

    public void addAll(List<JConfigure> configures) {
        datas.addAll(configures);
    }

    public List<JConfigure> getDatas() {
        return datas;
    }
}
