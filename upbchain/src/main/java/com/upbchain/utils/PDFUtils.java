package com.upbchain.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;



import sun.misc.BASE64Decoder;

public class PDFUtils {
	 /**
     * Description: 将base64编码内容转换为Pdf文件
     * @param  base64编码内容，文件的存储路径（含文件名）
     */
    public static boolean saveBase64ToFtpPDF(String base64Content,String file){
        BASE64Decoder decoder = new BASE64Decoder();
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            byte[] bytes = decoder.decodeBuffer(base64Content);//base64编码内容转换为字节数组
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);
            bis = new BufferedInputStream(byteInputStream);
            fos = new FileOutputStream(file);//(ofile);
            bos = new BufferedOutputStream(fos);
            byte[] buffer = new byte[1024];
            int length = bis.read(buffer);
            while(length != -1){
                bos.write(buffer, 0, length);
                length = bis.read(buffer);
            }
            bos.flush();
            fos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
            	if(bos != null){
            		bos.close();
            	}
            	if(fos != null){
            		fos.close();
            	}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
