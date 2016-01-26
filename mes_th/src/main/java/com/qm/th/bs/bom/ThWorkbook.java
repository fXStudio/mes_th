package com.qm.th.bs.bom;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Excel
 * 
 * @author Gaohf
 * @date 2009-10-12
 */
public class ThWorkbook {
    private Workbook workbook = null;

    /**
     * default constructor
     */
    protected ThWorkbook() {
    };

    /**
     * @param fileName
     * @throws BiffException
     * @throws IOException
     */
    public ThWorkbook(String fileName) throws BiffException, IOException {
        this(new File(fileName));
    }

    /**
     * @param fileName
     * @throws BiffException
     * @throws IOException
     */
    public ThWorkbook(File file) throws BiffException, IOException {
        this.workbook = Workbook.getWorkbook(file);
    }

    /**
     * @param fileName
     * @throws BiffException
     * @throws IOException
     */
    public ThWorkbook(InputStream in) throws BiffException, IOException {
        this.workbook = Workbook.getWorkbook(in);
    }

    /**
     * @param sheetName
     * @return
     */
    public ThSheet getSheet(int n) throws Exception{
        ThSheet ds = new ThSheet(workbook.getSheet(n).getName(), workbook);
        if (ds.found)
            return ds;
        else
            throw new Exception("工作表 Sheet1 不存在");
    }
}
