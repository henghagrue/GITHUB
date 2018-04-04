package com.upbchain.utils;


import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class CsvUtil_Poi {
	private static final Log logger = LogFactory.getLog(CsvUtil.class);
	private static final String CONFIG_PATH = "config/csv.xml";
	private static final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static XSSFCellStyle doublewcfStyle = null;
	private static XSSFCellStyle doubleTwoWcfStyle = null;

	/**
	 * description csv文件的导出 - poi 插件 - 导出Excel 2007格式，文件Size更小
	 * 
	 * @param list
	 *            待导出的bean集合
	 * @param props
	 *            属性配置数据，格式为 Object [] props = new Object{{},{},...};
	 * @param os
	 * @throws IOException
	 * @throws WriteException
	 */
	public static <E> void exportCSV(String[] headers,String[] formats,String[] fieldNames, List<E> list, OutputStream os) throws IOException {

		Long start = System.currentTimeMillis();

		try {

			// 创建工作薄
			XSSFWorkbook wb = new XSSFWorkbook();
			CreationHelper createHelper = wb.getCreationHelper();
			doublewcfStyle = wb.createCellStyle();
			doubleTwoWcfStyle = wb.createCellStyle();

			doublewcfStyle.setDataFormat(createHelper.createDataFormat().getFormat("0.00000000"));
			doubleTwoWcfStyle.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));

			// 创建新的一页
			XSSFCell cell;
			XSSFSheet sheet = wb.createSheet("第1页");
			XSSFRow tempRow = sheet.createRow(0);
			int labelIndex = 0;
			for (String headerName : headers) {
				cell = tempRow.createCell(labelIndex);
				cell.setCellValue(headerName);
				labelIndex++;
			}

			int k = 0;
			int m = 1;
			if (list != null && list.size() > 0) {
				for (int list_index = 1, row = 1; list_index < list.size() + 1; list_index++, row++) {
					if (row == 50000) {
						labelIndex = 0;
						row = 1;
						k = k + 1;
						m = m + 1;
						sheet = wb.createSheet("第" + m + "页");
						tempRow = sheet.createRow(row - 1);
						// 创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
						{
							for (String headerName : headers) {
								cell = tempRow.createCell(labelIndex);
								cell.setCellValue(headerName);
								labelIndex++;
							}
						}
					}
					sheet = wb.getSheetAt(k);
					tempRow = sheet.createRow(row);
					// 处理内容数据
					E entity = list.get(list_index - 1);
					int column = 0;
					for (String fieldName : fieldNames) {
						Method getMethod = getGetMethod(entity, fieldName);
						Object value = getMethod.invoke(entity, new Object[0]);
						cell = tempRow.createCell(column);
						processCell(cell, value, formats[column]);
						column++;
					}
				}
			}
			wb.write(os);
			os.flush();

		} catch (JAXBException e1) {
			logger.error("happend exception when try export csv", e1);
		} catch (Exception e) {
			logger.error("happend exception when try export csv", e);
			e.printStackTrace();
		} finally {
			try {
				if (os != null)
					os.close();
			} catch (IOException e) {
				logger.error("happend exception when close I/O", e);
				e.printStackTrace();
			}
		}

		Long end = System.currentTimeMillis();
		System.out.println("导出数据处理耗时:" + (end - start) / 1000 + "秒");

	}

	/**
	 * 自定义单元格格式
	 * 
	 * @param sheet
	 * @param value
	 * @param formats
	 * @param lableIndex
	 * @param pageIndex
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private static void processCell(XSSFCell cell, Object value, String format) {
		try {
			switch (format) {
			case "normal":
				// sheet.addCell(new Label(lableIndex, pageIndex, (String)
				// value));
				if (Double.class.isInstance(value)) {
					cell.setCellType(CellType.NUMERIC);
					double dv = (double) value;
					cell.setCellValue(dv);
				} else if (Integer.class.isInstance(value)) {
					cell.setCellType(CellType.NUMERIC);
					Integer iv = (Integer) value;
					cell.setCellValue(iv);
				} else {
					cell.setCellType(CellType.STRING);
					cell.setCellValue((String) value);
				}
				break;
			case "date":
				cell.setCellType(CellType.STRING);
				if (Date.class.isInstance(value)) {
					Date date = (Date) value;
					cell.setCellValue(dateTimeFormat.format(date));
				} else {
					cell.setCellValue((String) value);
				}
				break;
			case "txt":
				cell.setCellType(CellType.STRING);
				String v = (String) (value == null ? "" : value);
				String special_symbol = "";
				if (!isBlank(v)) {
					special_symbol = "'";// 用于处理导出身份证时自动补0在末端的情况
				}
				// cell.setCellValue(v.concat("\t"));
				cell.setCellValue(special_symbol.concat(v));
				break;
			case "double_scale2":
				cell.setCellType(CellType.NUMERIC);
				cell.setCellStyle(doubleTwoWcfStyle);
				try {
					String numValue = (String) value;
					cell.setCellValue(Double.parseDouble(isBlank(numValue) ? "0" : numValue));
				} catch (Exception e) {
					e.printStackTrace();
					cell.setCellValue(0);
				}
				break;
			case "double_scale8":
				cell.setCellType(CellType.NUMERIC);
				cell.setCellStyle(doublewcfStyle);
				try {
					String numValue = (String) value;
					cell.setCellValue(Double.parseDouble(isBlank(numValue) ? "0" : numValue));
				} catch (Exception e) {
					e.printStackTrace();
					cell.setCellValue(0);
				}
				break;
			default:
				cell.setCellType(CellType.STRING);
				cell.setCellValue("");
			}
		} catch (Exception e) {
			e.printStackTrace();
			cell.setCellValue("");
			cell.setCellType(CellType.STRING);
		}

	}

	public static Method getGetMethod(Object object, String fieldName) throws Exception {
		if (object == null) {
			throw new NullPointerException("object should not be null");
		} else if (fieldName == null) {
			throw new NullPointerException("fieldName should not be null");
		} else {
			Method method = findGetter(object, fieldName);
			return method;
		}
	}

	private static String getMethodNameForField(String prefix, String fieldName) {
		return prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	private static Method findGetter(Object object, String fieldName) throws Exception {
		if (object == null) {
			throw new NullPointerException("object should not be null");
		} else if (fieldName == null) {
			throw new NullPointerException("fieldName should not be null");
		} else {
			Class clazz = object.getClass();
			String standardGetterName = getMethodNameForField("get", fieldName);
			Method getter = findGetterWithCompatibleReturnType(standardGetterName, clazz, false);
			if (getter == null) {
				String booleanGetterName = getMethodNameForField("is", fieldName);
				getter = findGetterWithCompatibleReturnType(booleanGetterName, clazz, true);
			}
			return getter;
		}
	}

	private static Method findGetterWithCompatibleReturnType(String getterName, Class<?> clazz,
			boolean enforceBooleanReturnType) {
		Method[] allMethod = clazz.getMethods();
		int methodLength = allMethod.length;

		for (int i = 0; i < methodLength; ++i) {
			Method method = allMethod[i];
			if (getterName.equalsIgnoreCase(method.getName()) && method.getParameterTypes().length == 0
					&& !method.getReturnType().equals(Void.TYPE)
					&& (!enforceBooleanReturnType || Boolean.TYPE.equals(method.getReturnType())
							|| Boolean.class.equals(method.getReturnType()))) {
				return method;
			}
		}
		return null;
	}
	
	public static boolean isBlank(CharSequence cs) {
		int strLen;
		if (cs != null && (strLen = cs.length()) != 0) {
			for (int i = 0; i < strLen; ++i) {
				if (!Character.isWhitespace(cs.charAt(i))) {
					return false;
				}
			}

			return true;
		} else {
			return true;
		}
	}
}
