package com.upbchain.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {
	/**
	 * 获取文件byte[]
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static byte[] readFile(String filename) throws IOException {
		File file = new File(filename);
		if (filename == null || filename.equals("")) {
			throw new NullPointerException("无效的文件路径");
		}
		long len = file.length();
		byte[] bytes = new byte[(int) len];

		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				new FileInputStream(file));
		int r = bufferedInputStream.read(bytes);
		if (r != len) {
			bufferedInputStream.close();
			throw new IOException("读取文件不正确");
		}
		bufferedInputStream.close();

		return bytes;
	}
}
