package com.wucl.stdmis.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
/**
 * Excel工具类
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
public class ExcelUtil {
	/**
	 * Freemarker的方式生成excel文件，使用了模板文件生成
	 * 
	 * @param dataMap
	 *            数据
	 * @param templetPath
	 *            模板文件的存放路径，是一个xml文件
	 * @param templetFileName
	 *            模板文件的名称
	 * @param outputFileRealPath
	 *            输出文件的物理路径和名称
	 */
	@SuppressWarnings("unchecked")
	public static void exportToExcelByFreemarker(Map dataMap,
			String templetPath, String templetFileName,
			String outputFileRealPath) {
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");

		if (templetPath == null || templetFileName == null
				|| outputFileRealPath == null) {
			return;
		}
		Template t = null;
		try {
			configuration.setDirectoryForTemplateLoading(new File(templetPath));
			t = configuration.getTemplate(templetFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 输出文档路径及名称
		File outFile = new File(outputFileRealPath);
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(outFile), "utf-8"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			t.process(dataMap, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * JExcelApi的方式生成excel文件，目前支持javax.servlet.http.HttpServletResponse</br>
	 * 和java.io.File两种文件输出方式。javax.servlet.http.HttpServletResponse输出文件</br>
	 * 到浏览器客户端；java.io.File输出文件到本地路径
	 * 
	 * @param obj
	 *            输出文件方式
	 * @param excelFileName
	 *            输出文件名称
	 * @param headText
	 *            标题信息
	 * @param dataText
	 *            数据信息
	 * @param columnWidth
	 *            数据列宽度
	 */
	public static void exportToExcelByJExcelApi(Object obj,
			String excelFileName, String[] headText,
			List<List<String>> dataText, int[] columnWidth) {
		try {
			WritableWorkbook workbook = null;
			if (obj instanceof HttpServletResponse) {
				HttpServletResponse response = (HttpServletResponse) obj;
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-disposition",
						"attachment;filename="
								+ new String(excelFileName.getBytes(),
										"iso-8859-1"));
				OutputStream os = response.getOutputStream();
				workbook = Workbook.createWorkbook(os);
			} else if (obj instanceof File) {
				File file = (File) obj;
				workbook = Workbook.createWorkbook(file);
			}
			WritableFont wf = new WritableFont(WritableFont.TIMES, 12,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);
			WritableCellFormat wcf = new WritableCellFormat(wf); // 单元格定义
			wcf.setBackground(jxl.format.Colour.YELLOW); // 设置单元格的背景颜色
			wcf.setAlignment(jxl.format.Alignment.CENTRE); // 设置水平居中
			wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 设置垂直居中
			wcf.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN);
			WritableSheet sheet = workbook.createSheet("sheet1", 1);
			for (int i = 0; i < headText.length; i++) {
				sheet.addCell(new Label(i, 0, headText[i]));
			}
			if (columnWidth != null && columnWidth.length > 0) {
				for (int i = 0; i < columnWidth.length; i++) {
					sheet.setColumnView(i, columnWidth[i]);
				}
			}
			for (int i = 0; i < dataText.size(); i++) {
				for (int j = 0; j < headText.length; j++) {
					sheet.addCell(new Label(j, i + 1, dataText.get(i).get(j)));
				}
			}
			workbook.write();
			workbook.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void importFromExcel(String inputFileRealPath, int beginRow,
			int begColunm, PreparedStatement pstmt, String dbTableName)
			throws SQLException {
		if (!(new File(inputFileRealPath)).isFile() || pstmt == null
				|| pstmt.isClosed()) {
			return;
		}

	}

	/**
	 * 获取excel文件中数据
	 * 
	 * @param workBook
	 *            工作表
	 * @param beginRow
	 *            读取数据开始行号
	 * @param begColunm
	 *            读取数据开始列号
	 * @return
	 */
	public static List<List<List<String>>> getWorkbookData(Workbook workBook,
			int beginRow, int begColunm) {
		Sheet[] sheets = workBook.getSheets();
		List<List<List<String>>> workbookData = new ArrayList<List<List<String>>>();
		for (Sheet sheet : sheets) {
			workbookData.add(getSheetData(sheet, beginRow, begColunm));
		}
		return workbookData;
	}

	/**
	 * 获取excel文件中数据
	 * 
	 * @param inputFileRealPath
	 *            excel文件物理路径
	 * @param beginRow
	 *            读取数据开始行号
	 * @param begColunm
	 *            读取数据开始列号
	 * @return
	 */
	public static List<List<List<String>>> getWorkbookData(
			String inputFileRealPath, int beginRow, int begColunm)
			throws BiffException, IOException {
		Workbook workBook = Workbook.getWorkbook(new File(inputFileRealPath));
		return getWorkbookData(workBook, beginRow, begColunm);
	}

	/**
	 * 获取excel文件中数据
	 * 
	 * @param file
	 *            excel文件
	 * @param beginRow
	 *            读取数据开始行号
	 * @param begColunm
	 *            读取数据开始列号
	 * @return
	 */
	public static List<List<List<String>>> getWorkbookData(File file,
			int beginRow, int begColunm) throws BiffException, IOException {
		Workbook workBook = Workbook.getWorkbook(file);
		return getWorkbookData(workBook, beginRow, begColunm);
	}

	/**
	 * 获取excel文件工作簿中数据
	 * 
	 * @param sheet
	 *            工作簿名称
	 * @param beginRow
	 *            读取数据开始行号
	 * @param begColunm
	 *            读取数据开始列号
	 * @return
	 */
	public static List<List<String>> getSheetData(Sheet sheet, int beginRow,
			int begColunm) {
		if (sheet == null) {
			return null;
		}
		int rows = sheet.getRows();
		List<List<String>> sheetData = new ArrayList<List<String>>();
		for (int i = beginRow; i < rows; i++) {
			List<String> rowList = new ArrayList<String>();
			Cell[] rowCells = sheet.getRow(i);
			for (int j = begColunm; j < rowCells.length; j++) {
				rowList.add(rowCells[j].getContents());
			}
			sheetData.add(rowList);
		}
		return sheetData;
	}

}
