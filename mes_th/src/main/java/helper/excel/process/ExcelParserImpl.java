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
import helper.excel.entities.FATHBean;
import helper.excel.inters.IDataPersistenceService;
import helper.excel.persistence.BoraPersistenceServcie;
import helper.excel.persistence.CA3PersistenceServcie;
import helper.excel.persistence.Q5PersistenceServcie;
import helper.excel.persistence.SihuanPersistenceServcie;

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
	private String[] TEMPLATE_HEADERS = { "-", "Status", "Seq", "CP5A Date", "CP5A Time", "Model", "KNR", "Color",
			"\u989c\u8272", "Type", "Chassi" };

	/**
	 * 数据处理映射表
	 */
	private static Map<String, IDataPersistenceService> stores;

	/**
	 * 初始化数据处理映射表
	 */
	static {
		if (stores == null) {
			stores = new HashMap<String, IDataPersistenceService>();

			// 按业务需要，我们需处理四个sheet，每个sheet有自己的处理规则
			stores.put("CA3", new CA3PersistenceServcie());
			stores.put("Q5", new Q5PersistenceServcie());
			stores.put("BORA", new BoraPersistenceServcie());
			stores.put("SiHuan", new SihuanPersistenceServcie());
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
				List<FATHBean> list = new ArrayList<FATHBean>();

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
		// CA3需要特殊处理一下
		if ("BORA".equals(sheet.getSheetName())) {
			sheet.setColumnHidden(7, true);
		}

		// 文件的标题行
		Row row = sheet.getRow(sheet.getFirstRowNum());

		// 表格第一行不能为空
		if (row == null) {
			return false;
		}
		// 和定义的模板格式进行对比，只有符合模板要求的文档才能被正确解析
		for (int i = 0, j = 0; i < TEMPLATE_HEADERS.length; i++, j++) {
			// 如果column被隐藏，则需要忽略
			if (sheet.isColumnHidden(j)) {
				j++;
			}
			if (!TEMPLATE_HEADERS[i].equals(getCellValue(row.getCell(j)))) {
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

		int i = 0;

		// 填充数据对象
		p.setId(getCellValue(row.getCell(i++)));
		p.setStatus(getCellValue(row.getCell(i++)));
		p.setSeq(getCellValue(row.getCell(i++)));
		p.setCp5adate(org.apache.poi.ss.usermodel.DateUtil.getJavaDate(row.getCell(i++).getNumericCellValue()));
		p.setCp5atime(org.apache.poi.ss.usermodel.DateUtil.getJavaDate(row.getCell(i++).getNumericCellValue()));
		p.setModel(getCellValue(row.getCell(i++)));
		p.setKnr(getCellValue(row.getCell(i++)));
		
		if (row.getSheet().isColumnHidden(i)) {
			i++;
		}
		
		p.setColor(getCellValue(row.getCell(i++)));
		p.setColorDesc(getCellValue(row.getCell(i++)));
		p.setType(getCellValue(row.getCell(i++)));
		p.setChassi(getCellValue(row.getCell(i++)));

		return p;
	}
}