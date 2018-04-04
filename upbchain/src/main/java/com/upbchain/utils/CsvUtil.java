package com.upbchain.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jxl.Workbook;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class CsvUtil{
	private static final Log logger = LogFactory.getLog(CsvUtil.class);
	private static final  DateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static WritableCellFormat doublewcf = null;
	private static WritableCellFormat doubleTwoWcf = null;
	/**
	 * description csv文件的导出。- jxl插件
	 * @param list 待导出的bean集合
	 * @param props 属性配置数据，格式为 Object [] props = new Object{{},{},...};
	 * @param os
	 * @throws IOException 
	 * @throws WriteException 
	 */
	public static <E> void exportExcel(String[] headers,String[] formats,String[] fieldNames,List<E> list,OutputStream os) throws IOException{
		
		Long start = System.currentTimeMillis();
		WritableWorkbook workbook = null;
		try {
			
			
			DecimalFormat df = new DecimalFormat("0.00000000");
			//创建工作薄
			workbook = Workbook.createWorkbook(os);
			WritableFont contentFont = new WritableFont
	                (WritableFont.ARIAL, 11,  
	                          WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,  
	                          jxl.format.Colour.BLACK);
			//单元格数值格式化成8位小数
		    NumberFormat doubleFormat=new NumberFormat("0.00000000");
		    doublewcf=new WritableCellFormat(contentFont,doubleFormat);
		    doublewcf.setBorder(jxl.format.Border.ALL, BorderLineStyle.NONE,Colour.AUTOMATIC);
		    //单元格数值格式化成2位小数
		    NumberFormat doubleFormatTwo=new NumberFormat("0.00");
		    doubleTwoWcf=new WritableCellFormat(contentFont,doubleFormatTwo);
		    doubleTwoWcf.setBorder(jxl.format.Border.ALL, BorderLineStyle.NONE,Colour.AUTOMATIC);
		    
			//创建新的一页
		    WritableSheet sheet = workbook.createSheet("第 1 页", 0);
			
			int labelIndex=0;
			for (String headerName : headers) {
				sheet.addCell(new Label(labelIndex, 0, headerName));
				labelIndex++;
			}
			
			int k = 0;
			int m = 1;
			if(list != null && list.size()>0){
				for (int list_index = 1, row = 1; list_index < list.size() + 1; list_index++, row++) {
					if (row == 50000 ) {
						labelIndex = 0;
						row = 1;
						k = k + 1;
						m = m + 1;
						sheet = workbook.createSheet("第 " + m + " 页", m);
						//创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
						{
							for (String headerName : headers) {
								sheet.addCell(new Label(labelIndex, 0, headerName));
								labelIndex++;
							}
						}
					}
					
					sheet = workbook.getSheet(k);
					//处理内容数据					
					E entity = list.get(list_index - 1);
					int column=0;
					for(String fieldName : fieldNames){
						Method getMethod = getGetMethod(entity, fieldName);
						Object value = getMethod.invoke(entity, new Object[0]);
						sheet.addCell(processCell(value,formats[column],column,row));
						column++;
					}
				}
			}
			
			workbook.write();
			
		} catch (JAXBException e1) {
			logger.error("happend exception when try export csv",e1);
		}catch (Exception e) {
			logger.error("happend exception when try export csv",e);
			e.printStackTrace();
		} finally {
			try {
				if (workbook != null)
					workbook.close();
				if (os != null)
					os.close();
			} catch (IOException e) {
				logger.error("happend exception when close I/O",e);
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Long end = System.currentTimeMillis();
		System.out.println("导出数据处理耗时:"+(end-start)/1000+"秒");
		
	}
	

	/**
	 * 自定义单元格格式
	 * @param sheet
	 * @param value
	 * @param formats
	 * @param lableIndex
	 * @param pageIndex
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private static WritableCell processCell(final Object value, String format, int column, int row) throws RowsExceededException, WriteException {
		try{
			switch(format){
			case "normal":
//				sheet.addCell(new Label(lableIndex, pageIndex, (String) value));
				if(Double.class.isInstance(value)){
					double dv = (double) value;
					return new Label(column, row, String.valueOf(dv));
				}else if(Integer.class.isInstance(value)){
					Integer iv = (Integer) value;
					return new Label(column, row, String.valueOf(iv));
				}else{
					return new Label(column, row, (String) value);
				}
			case "date":
				try{
					if(Date.class.isInstance(value)){
						Date date = (Date)value;
						return new Label(column, row, dateTime.format(date));
					}else{
						return new Label(column, row, (String)value);
					}
				}catch(Exception e){
					e.printStackTrace();
					return new Label(column, row, "");
				}
			case "txt":
				String v = (String)(value == null?"":value);
				String special_symbol = "";
				if(!isBlank(v)){
					special_symbol ="'";//用于处理导出身份证时自动补0在末端的情况
				}
				return new Label(column, row,special_symbol.concat(v));
			case "double_scale2":
				try{
					String numValue = (String) value;
					return new Number(column, row, Double.parseDouble((numValue == null || numValue.trim().length()< 1)?"0":numValue), doubleTwoWcf);
				}catch(Exception e){
					e.printStackTrace();
					return new Number(column, row, 0, doubleTwoWcf);
				}
			case "double_scale8":
				try{
					String numValue = (String) value;
					return new Number(column, row, Double.parseDouble((numValue == null || numValue.trim().length()< 1)?"0":numValue), doublewcf);
				}catch(Exception e){
					e.printStackTrace();
					return new Number(column, row, 0, doublewcf);
				}
				default:
					return new Label(column, row, "");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new Label(column, row, "");
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
//			if (getter == null) {
//				throw new Exception(String.format(
//						"unable to find getter for field %s in class %s - check that the corresponding nameMapping element matches the field name in the bean",
//						new Object[]{fieldName, clazz.getName()}));
//			} else {
				return getter;
//			}
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
