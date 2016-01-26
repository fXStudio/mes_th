package com.qm.th.bs.bom;

import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Excel Sheet
 * 
 * @author Gaohf
 * @date 2009-10-12
 */
public class ThSheet {
    private Sheet sheet = null;
    boolean found = false;

    ThSheet(String sheetName, Workbook w) {
        this.sheet = w.getSheet(sheetName);
        found = this.sheet != null;
    }

    /**
     * 取得一列的结果集
     * 
     * @param colName
     */
    public List<String> getColumnContents(String colName) throws Exception {
        Cell cell = getColumn(colName);
        
        if (cell.getType().equals(CellType.EMPTY))
            throw new Exception("列 "+  colName + " 不存在!");

        List<String> list = new ArrayList<String>();
        Cell[] cells = this.getColumn(cell.getColumn());

        for (int i = 0; i < cells.length; i++) {
            Cell ce = cells[i];
            list.add(ce.getContents());
        }

        return list;
    }

    /**
     * 获得一行的数据集
     * 
     * @param index
     * @return
     */
    public List<String> getRowContents(int index) {

        List<String> list = new ArrayList<String>();
        Cell[] cells = this.getRow(index);

        for (Cell ce : cells) {
            list.add(ce.getContents());
        }

        return list;
    }

    /**
     * 有效行数
     */
    public int getRows() {
        return sheet.getRows();
    }

    /**
     * 有效列数
     */
    public int getCols() {
        return sheet.getColumns();
    }

    /**
     * 获得一个单元格
     * 
     * @param colIndex
     * @param rowIndex
     * @return
     */
    public Cell getCell(int colIndex, int rowIndex) {
        return sheet.getCell(colIndex, rowIndex);
    }

    /**
     * 获得一行所有单元格对象数组
     * 
     * @param rowIndex
     * @return
     */
    public Cell[] getRow(int rowIndex) {
        return sheet.getRow(rowIndex);
    }

    /**
     * 获得一列所有单元格数组
     * 
     * @param colIndex
     * @return
     */
    public Cell[] getColumn(int colIndex) {
        return sheet.getColumn(colIndex);
    }

    /**
     * 获得列索引
     * 
     * @param colName
     * @return
     */
    public int getColumnIndex(String colName) {
        Cell cell = getColumn(colName);
        return cell == null ? -1 : cell.getColumn();
    }

    /**
     * 获得拥有指名称的列头单元格
     * 
     * @param colName
     * @return
     */
    private Cell getColumn(String colName) {
        Cell cell = null;
        for (int i = 0; i < this.getCols(); i++) {
            cell = this.getCell(i, 0);
            if (cell.getContents().trim().equalsIgnoreCase(colName)) {
                break;
            }
        }
        return cell;
    }
}
