package com.qm.mes.th.assembly;

import java.util.List;

import com.qm.mes.th.assembly.entities.RequestParam;

import net.sf.jasperreports.engine.JasperPrint;

public interface IAssemblyPrintFacade {
    public List<JasperPrint> assemblyPrint(RequestParam requestParam);
}
