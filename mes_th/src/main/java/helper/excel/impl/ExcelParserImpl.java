package helper.excel.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import helper.excel.base.BaseExcelParser;
import helper.excel.entities.FATHBean;

/**
 * 解析Excel文件
 *
 * @author AjaxFan
 */
public class ExcelParserImpl extends BaseExcelParser {
	/**
	 * 计划模板的标题栏定义
	 *
	 * 标题格式: 批次顺序, 计划编号, 计划批次号, 生产单元, 订单号, 物料号, 物料流水起始, 物料流水结束, 班组, 班次, 数量, 备注
	 */
	private String[] TEMPLATE_HEADERS = { "id", "Status", "Seq", "CP5A Date", "CP5A Time", "Model", "KNR", "Color",
	        "ColorDesc", "Type", "Chassi" };

	/**
	 * 解析Excel文件，并生成一个对象列表
	 *
	 * @param in
	 *            文件流
	 */
	public List<FATHBean> parseExcel(InputStream in) throws Exception {
		// 创建结果列表
		List<FATHBean> list = new ArrayList<FATHBean>();

		// 获得文档中的第一个工作簿
		Sheet sheet = createWorkbook(in).getSheetAt(0);

		// 判断文件的格式
		if (!checkExcelHeader(sheet)) {
			return list;
		}

		// 移除工作簿的首行 - 标题行
		sheet.removeRow(sheet.getRow(sheet.getFirstRowNum()));

		// 遍历工作簿中的行对象，取得每行的所有有效单元格
		for (Iterator<Row> rowite = sheet.rowIterator(); rowite.hasNext();) {
			// 创建一个行记录对象
			list.add(createPlanBeanByExcelRow(rowite.next()));
		}
		return list;
	}

	/**
	 * 通过Excel表格头，判断表格类型是否符合要求
	 *
	 * @param sheet
	 * @return
	 */
	private boolean checkExcelHeader(Sheet sheet) {
		// 文件的标题行
		Row row = sheet.getRow(sheet.getFirstRowNum());
		// 表格第一行不能为空
		if (row == null) {
			return false;
		}

		// 和定义的模板格式进行对比，只有符合模板要求的文档才能被正确解析
		for (int i = 0; i < TEMPLATE_HEADERS.length; i++) {
			if (!TEMPLATE_HEADERS[i].equals(getCellValue(row.getCell(i)))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 生成FATHBean对象
	 *
	 * @param row
	 * @return
	 */
	private FATHBean createPlanBeanByExcelRow(Row row) {
		// 创建数据对象
		FATHBean p = new FATHBean();

		// 填充数据对象
		p.setId(getCellValue(row.getCell(0)));
		p.setStatus(getCellValue(row.getCell(1)));
		p.setSeq(getCellValue(row.getCell(2)));
		p.setCp5adate(org.apache.poi.ss.usermodel.DateUtil.getJavaDate(row.getCell(3).getNumericCellValue()));
		p.setCp5atime(org.apache.poi.ss.usermodel.DateUtil.getJavaDate(row.getCell(4).getNumericCellValue()));
		p.setModel(getCellValue(row.getCell(5)));
		p.setKnr(getCellValue(row.getCell(6)));
		p.setColor(getCellValue(row.getCell(7)));
		p.setColorDesc(getCellValue(row.getCell(8)));
		p.setType(getCellValue(row.getCell(9)));
		p.setChassi(getCellValue(row.getCell(10)));

		return p;
	}
}