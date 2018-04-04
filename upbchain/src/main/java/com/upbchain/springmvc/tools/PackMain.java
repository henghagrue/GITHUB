package com.upbchain.springmvc.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class PackMain {
	public static String CHECKOUT_BRANCH ="V2.0.00";//V1.6.50";//要打包的分支
	
	public static final String ANT = "F:/Develop/apache-ant-1.10.1/bin/ant.bat";
	public static String BASEDIR = "F:/ROOT/PROJECT/membersystem";
	public static final String BASEDIR_MS = "F:/ROOT/PROJECT/membersystem";
	public static final String BASEDIR_CA = "F:/ROOT/PROJECT/camembers";
	public static final String BASEDIR_H5 = "F:/ROOT/PROJECT/h5Backend";
	public static final String SOURCE_FLODER = "F:/ROOT/SOURCE";
	public static final String TARGET_FLODER = "F:/ROOT/TARGET";//最终获取的ROOT文件夹
	
	public static final String MS_BUILD_XML = "F:/ROOT/MS_build.xml";
	public static final String CA_BUILD_XML = "F:/ROOT/CA_build.xml";
	public static final String H5_BUILD_XML = "F:/ROOT/H5_build.xml";
	
	public static String COMMIT_NEW = "874aa7d2d42b5663e01b1db041a5f8a80336b419";//开始提交ID
	public static String COMMIT_OLD = "874aa7d2d42b5663e01b1db041a5f8a80336b419";//结束提交ID
	/**
	 * args[0] 1：MS 2:CA 3:H5
	 * args[1] oldcommintId
	 * args[2] newcommintId
	 * args[3] branch
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
//		int packIndex = 1;//default membersystem
//		if(null != args && args.length > 2){
//			try{
//				packIndex = Integer.parseInt(args[0]);
//			}catch(Exception e){
//				System.out.println("项目选择参数错误，不执行");
//				System.exit(0);
//			}
//			COMMIT_OLD = args[1];
//			COMMIT_NEW = args[2];
//			if(args.length > 3){
//				try{
//					CHECKOUT_BRANCH = args[3];
//				}catch(Exception e){
//					System.out.println("参数错误，不执行");
//					System.exit(0);
//				}
//			}else{
//				System.out.println("没有选择分支，默认master");
//			}
//		}else{
//			System.out.println("缺少参数错误，不执行");
//			System.exit(0);
//		}
		emptySource();
		emptyTarget();
//		project(1);//会员系统
		project(2);//旧接口
//		project(3);//h5后台
		PackWar.pack();
	}
	/**
	 * 编译代码
	 * @param projectId
	 * @throws IOException 
	 */
	public static void project(int projectId) throws IOException{
		switch(projectId){
			case 1://MS
				System.out.println("#######################获取【会员系统】最新代码......");
				BASEDIR = BASEDIR_MS;
				checkoutAndPullGit();
				exportDiff(diffTree());
				build_MS_Project();
				break;
			case 2://oldinterface
				System.out.println("#######################获取【旧接口】最新代码......");
				BASEDIR = BASEDIR_CA;
				checkoutAndPullGit();
				exportDiff(diffTree());
				build_OLDINTERFACE_Project();
				break;
			case 3://H5Backend
				System.out.println("#######################获取【H5Backend】最新代码......");
				BASEDIR = BASEDIR_H5;
				checkoutAndPullGit();
				exportDiff(diffTree());
				build_H5BACKEND_Project();
				break;
		}
	}
	
	public static void checkoutAndPullGit() throws IOException{
		
		System.out.println("检出分支【"+CHECKOUT_BRANCH+"】*****开始");
		List<String> fileList = new ArrayList<String>();
		Runtime rt = Runtime.getRuntime();
		Process pcs = rt.exec( "git checkout "+CHECKOUT_BRANCH, null, new File(BASEDIR));
		InputStream is = pcs.getInputStream();
		BufferedReader br =  new BufferedReader(new InputStreamReader(is));
		String line = null;
		while((line= br.readLine()) != null){
			System.out.println(line);
			fileList.add(line);
		}
		if(fileList.isEmpty()){
			System.out.println("切换分支异常，请手动处理");
			System.exit(0);
		}
		System.out.println("拉取最新代码****");
		fileList.clear();
		pcs = rt.exec( "git pull ", null, new File(BASEDIR));
		is = pcs.getInputStream();
		br =  new BufferedReader(new InputStreamReader(is));
		line = null;
		while((line= br.readLine()) != null){
			System.out.println(line);
			fileList.add(line);
		}
		
		System.out.println("检出分支【"+CHECKOUT_BRANCH+"】*****结束");
		if(fileList.isEmpty()){
			System.out.println("拉取代码异常，请手动处理");
			System.exit(0);
		}
	}
	
	public static void emptySource(){
		System.out.println("删除待编译文件夹*****开始");
		File file = new File(SOURCE_FLODER);
		deleteSubFloder(file);
		System.out.println("删除待编译文件夹*****结束");
		
	}
	public static void emptyTarget(){
		System.out.println("删除目标文件夹*****开始");
		File file = new File(TARGET_FLODER);
		deleteSubFloder(file);
		System.out.println("删除目标文件夹*****结束");
		
	}
	
	
	/**
	 * 编译旧接口
	 * @throws IOException 
	 */
	public static void build_H5BACKEND_Project() throws IOException{
		System.out.println("编译H5后台系统*****开始");
		List<String> fileList = new ArrayList<String>();
		Runtime rt = Runtime.getRuntime();
		Process pc = rt.exec(ANT + " -buildfile " + H5_BUILD_XML , null, new File(BASEDIR));
		InputStream is = pc.getInputStream();
		BufferedReader br =  new BufferedReader(new InputStreamReader(is));
		StringBuffer sb = new StringBuffer();
		String line = null;
		while((line= br.readLine()) != null){
			sb.append(line);
			System.out.println(line);
		}
		if(sb.toString().indexOf("BUILD SUCCESSFUL") == -1){
			System.out.println("编译异常，请手工处理");
			System.exit(0);
		}
		
		System.out.println("编译H5后台系统*****结束");
	}
	/**
	 * 编译旧接口
	 * @throws IOException 
	 */
	public static void build_OLDINTERFACE_Project() throws IOException{
		System.out.println("编译旧接口系统*****开始");
		List<String> fileList = new ArrayList<String>();
		Runtime rt = Runtime.getRuntime();
		Process pc = rt.exec(ANT + " -buildfile " + CA_BUILD_XML , null, new File(BASEDIR));
		InputStream is = pc.getInputStream();
		BufferedReader br =  new BufferedReader(new InputStreamReader(is));
		StringBuffer sb = new StringBuffer();
		String line = null;
		while((line= br.readLine()) != null){
			sb.append(line);
			System.out.println(line);
		}
		if(sb.toString().indexOf("BUILD SUCCESSFUL") == -1){
			System.out.println("编译异常，请手工处理");
			System.exit(0);
		}
		System.out.println("编译旧接口系统*****结束");
	}
	/**
	 * 编译会员系统
	 * @throws IOException 
	 */
	public static void build_MS_Project() throws IOException{
		System.out.println("编译会员系统*****开始");
		List<String> fileList = new ArrayList<String>();
		Runtime rt = Runtime.getRuntime();
		Process pc = rt.exec(ANT + " -buildfile " + MS_BUILD_XML , null, new File(BASEDIR));
		InputStream is = pc.getInputStream();
		BufferedReader br =  new BufferedReader(new InputStreamReader(is));
		StringBuffer sb = new StringBuffer();
		String line = null;
		while((line= br.readLine()) != null){
			sb.append(line);
			System.out.println(line);
		}
		if(sb.toString().indexOf("BUILD SUCCESSFUL") == -1){
			System.out.println("编译异常，请手工处理");
			System.exit(0);
		}
		System.out.println("编译会员系统*****结束");
	}
	public static void deleteFloder(File file){
		if(file.exists() && file.isDirectory() && file.list().length > 0){
			for(File ftemp : file.listFiles()){
				if(ftemp.isDirectory())deleteFloder(ftemp);
				else{ ftemp.delete();System.out.println("删除B:"+ftemp.getPath());}
			}
		}else{
			System.gc();
			file.delete();
			System.out.println("删除C:"+file.getPath());
		}
		
	}
	
	public static void deleteSubFloder(File file){
		if(file.exists() && file.isDirectory() && file.list().length > 0){
			for(File ftemp : file.listFiles()){
				if(ftemp.isDirectory())deleteFloder(ftemp);
				else{ ftemp.delete();System.out.println("删除A:"+ftemp.getPath());}
			}
		}
	}
	
	public static List<String> diffTree() throws IOException{
		System.out.println("导出差异文件*****开始");
		List<String> fileList = new ArrayList<String>();
		Runtime rt = Runtime.getRuntime();
		Process pc = rt.exec("git diff-tree -r --no-commit-id --name-only "+COMMIT_OLD+"^.."+COMMIT_NEW, null, new File(BASEDIR));
		InputStream is = pc.getInputStream();
		BufferedReader br =  new BufferedReader(new InputStreamReader(is));
		System.out.println("差异文件列表：");
		String line = null;
		while((line= br.readLine()) != null){
			System.out.println(line);
			fileList.add(line);
		}
		System.out.println("导出差异文件*****结束");
		if(fileList.isEmpty()){
			System.out.println("没有差异文件，退出");
			System.exit(0);
		}
		return fileList;
	}
	
	public static void exportDiff(List<String> fileList) throws IOException{
		System.out.println("复制差异文件*****开始");
		for(String filepath : fileList){
			String path = "";
			if(filepath.indexOf("/") > 0){
				path = filepath.substring(0,filepath.lastIndexOf("/"));
			}
			File newPath = new File(SOURCE_FLODER + "/" + path);
			File srcFile = new File(BASEDIR + "/" +filepath);
			if(!newPath.exists())newPath.mkdirs();
			if (srcFile.isFile()) {
				FileInputStream input = new FileInputStream(srcFile);
				FileOutputStream output = new FileOutputStream(newPath + "/" + srcFile.getName().toString());
				byte[] b = new byte[5120];
				int len;
				while ((len = input.read(b)) != -1) {
					output.write(b, 0, len);
				}
				output.flush();
				output.close();
				input.close();
			}
			
		}
		System.out.println("复制差异文件*****结束");
	}

}
