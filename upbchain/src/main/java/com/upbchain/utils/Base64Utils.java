package com.upbchain.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;



import sun.misc.BASE64Encoder;

public class Base64Utils {
	/**
	 * 将byte[] 进行base64编码
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public static String getEnBASE64(byte[] b) throws Exception {
		if (b == null) {
			return null;
		}
		String result = new BASE64Encoder().encode(b);
		return result;
	}
	/**
	 * 获取文件base64编码
	 * @param file
	 * @return
	 */
	public static  String getPDFBase64(String file) {
		// TODO Auto-generated method stub
		String base64Result = "";
		try {
			base64Result = Base64Utils.getEnBASE64(FileUtils.readFile(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return base64Result;
	}
}
