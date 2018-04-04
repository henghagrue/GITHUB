package com.upbchain.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;


public class RequestURL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * get请求远程资源，返回byte[]
	 * @param url
	 *            String 远程地址
	 * @param param
	 *            JSONObject json参数
	 * @return String
	 * */
	public static byte[] getDataFromServer(String url) {
		InputStream inputStream = null;
		ByteArrayOutputStream baos = null;
		byte[] result = null;
		try {
			URL reqUrl = new URL(url);
			HttpURLConnection connection = null;
			if(url.startsWith("https")){
				
				// 下面是忽略证书信任问题的代码
				HostnameVerifier hv = new HostnameVerifier() {

					@Override
					public boolean verify(String urlHostName, SSLSession session) {
						// TODO Auto-generated method stub
						System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
						return true;
					}
				};
				HttpsCertificates.trustAllHttpsCertificates();//
				HttpsURLConnection.setDefaultHostnameVerifier(hv);
				connection = (HttpsURLConnection) reqUrl.openConnection();
			}else{
				connection = (HttpURLConnection) reqUrl
						.openConnection();
			}
			

			int rec = connection.getResponseCode();
			if (rec == 200) {
				inputStream = connection.getInputStream();
				int aReadbytes = 100;
				byte[] data = new byte[aReadbytes];
				baos = new ByteArrayOutputStream();
				while (true) {
					int length = inputStream.read(data, 0, data.length);
					if (length == -1) {
						break;
					}
					baos.write(data, 0, length);
				}
				inputStream.close();
				connection.disconnect();

				result = baos.toByteArray();

				// 返回的结果字符串
//				result = new String(responseData, "UTF-8");
			}
			connection.disconnect();
		} catch (ConnectException connectEx) {
			connectEx.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != inputStream) {
					inputStream.close();
				}
				if (null != baos) {
					baos.close();
				}

			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return result;
	}
	
	
	/**
	 * post请求远程资源，返回byte[]
	 * @param url
	 *            String 远程地址
	 * @param param
	 *            JSONObject json参数
	 * @return String
	 * */
	public static byte[] getDataFromServerByPost(String url) {
		InputStream inputStream = null;
		ByteArrayOutputStream baos = null;
		byte[] result = null;
		try {
			URL reqUrl = new URL(url);
			HttpURLConnection connection = null;
			if(url.startsWith("https")){
				
				// 下面是忽略证书信任问题的代码
				HostnameVerifier hv = new HostnameVerifier() {

					@Override
					public boolean verify(String urlHostName, SSLSession session) {
						// TODO Auto-generated method stub
						System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
						return true;
					}
				};
				HttpsCertificates.trustAllHttpsCertificates();//
				HttpsURLConnection.setDefaultHostnameVerifier(hv);
				connection = (HttpsURLConnection) reqUrl.openConnection();
			}else{
				connection = (HttpURLConnection) reqUrl
						.openConnection();
			}
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type",
                    "application/json;charset=utf-8");
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);


			int rec = connection.getResponseCode();
			if (rec == 200) {
				inputStream = connection.getInputStream();
				int aReadbytes = 100;
				byte[] data = new byte[aReadbytes];
				baos = new ByteArrayOutputStream();
				while (true) {
					int length = inputStream.read(data, 0, data.length);
					if (length == -1) {
						break;
					}
					baos.write(data, 0, length);
				}
				inputStream.close();
				connection.disconnect();

				result = baos.toByteArray();

				// 返回的结果字符串
//				result = new String(responseData, "UTF-8");
			}
			connection.disconnect();
		} catch (ConnectException connectEx) {
			connectEx.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != inputStream) {
					inputStream.close();
				}
				if (null != baos) {
					baos.close();
				}

			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return result;
	}
}
