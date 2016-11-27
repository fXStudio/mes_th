package helper.excel.process;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import helper.excel.base.BaseExcelParser;
import helper.excel.entities.SpecialKinBean;
import helper.excel.inters.IDataPersistenceService;
import helper.excel.persistence.SpecialKinPersistence;

/**
 * 解析Excel文件
 *
 * @author AjaxFan
 */
public class SpecialKinExcelParserImpl extends BaseExcelParser {
	/**
	 * 计划模板的标题栏定义
	 *
	 * 标题格式: Kin号、是否监控、备注
	 */
	private String[] TEMPLATE_HEADERS = { "KIN\u53f7", "\u662f\u5426\u76d1\u63a7", "\u5907\u6ce8"};

	/**
	 * 数据处理映射表
	 */
	private static Map<String, IDataPersistenceService<SpecialKinBean>> stores;

	/**
	 * 初始化数据处理映射表
	 */
	static {
		if (stores == null) {
			stores = new HashMap<String, IDataPersistenceService<SpecialKinBean>>();

			// 按业务需要，我们需处理四个sheet，每个sheet有自己的处理规则
			stores.put("spec_kincode", new SpecialKinPersistence());
		}
	}

	/**
	 * 解析Excel文件，并生成一个对象列表
	 *
	 * @param in
	 *            文件流
	 */
	public String parseExcel(InputStream in) throws Exception {
		Workbook workbook = createWorkbook(in);
		List<String> messages = new ArrayList<String>();

		// 处理数据集合
		for (String key : stores.keySet()) {
			Sheet sheet = workbook.getSheet(key);

			// 检验表格格式
			if (sheet != null && checkExcelHeader(sheet)) {
				List<SpecialKinBean> list = new ArrayList<SpecialKinBean>();

				// 移除工作簿的首行 - 标题行
				sheet.removeRow(sheet.getRow(sheet.getFirstRowNum()));

				// 遍历工作簿中的行对象，取得每行的所有有效单元格
				for (Iterator<Row> rowite = sheet.rowIterator(); rowite.hasNext();) {
					list.add(createPlanBeanByExcelRow(rowite.next()));
				}
				// 记录处理的数据结果
				messages.add(key + ": " + stores.get(key).storeData(list));
			}
		}
		return java.util.Arrays.toString(messages.toArray());
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
	private SpecialKinBean createPlanBeanByExcelRow(Row row) {
		// 创建数据对象
		SpecialKinBean p = new SpecialKinBean();

		int i = 0;

		// 填充数据对象
		p.setKincode(getCellValue(row.getCell(i++)));
		p.setEnabled(getCellValue(row.getCell(i++)));
		p.setRemark(getCellValue(row.getCell(i++)));

		return p;
	}
}