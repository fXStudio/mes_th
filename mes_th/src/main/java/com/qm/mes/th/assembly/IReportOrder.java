package com.qm.mes.th.assembly;

import java.util.List;

import com.qm.mes.th.assembly.entities.ReportBaseInfo;
import com.qm.mes.th.assembly.entities.RequestParam;

import th.pz.bean.JConfigure;
import th.pz.bean.PrintSet;

/**
 * 作为接口协议使用，没有具体的实现
 * 
 * @author Ajaxfan
 */
public interface IReportOrder {
    public PrintSet getPrintSet();

    public RequestParam getRequestParam();

    public ReportBaseInfo getReportBaseInfo();

    public List<JConfigure> getDatas();
}
