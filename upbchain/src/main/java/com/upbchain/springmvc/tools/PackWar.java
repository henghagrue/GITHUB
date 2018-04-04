package com.upbchain.springmvc.tools;



import java.io.File;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.FileWriter;

import java.io.PrintWriter;



//import com.example.demo.utils.ZipUtil;



/**

 * Run as : java -jar Increment.jar /ROOT/SOURCE/src /ROOT/SOURCE/WebRoot /ROOT/TARGET /ROOT/PROJECT/WebRoot/WEB-INF/classes

 * @param args[0] GIT_EXPORT\\src (e.x F:\\ROOT\\SOURCE\\src)

 * @param args[1] GIT_EXPORT\\WebRoot (e.x F:\\ROOT\\SOURCE\\WebRoot)

 * @param args[2] SVN_PUBLISH_FLODER  to save the ROOT floder (e.x F:\\ROOT\\TARGET)

 * @param args[3] compile classes floder (e.x F:\\Develop\\Project\\trunk_membersystem\\WebRoot\\WEB-INF\\classes)

 */

class PackWar {

	static int count = 0;

	

	//编译项目classes文件路径

	private static String classPath = PackMain.BASEDIR + "/WebRoot/WEB-INF/classes";//"F:\\Develop\\Project\\trunk_membersystem\\WebRoot\\WEB-INF\\classes";

	//待编译java文件路径

	private static String javaPath = PackMain.SOURCE_FLODER + "/src";//"F:\\ROOT\\SOURCE\\src";

	private static String htmlPath = PackMain.SOURCE_FLODER + "/WebRoot";//"F:\\ROOT\\SOURCE\\WebRoot";

	//目标文件夹,ROOT.zip

	private static String DATEPATH = PackMain.TARGET_FLODER;//"F:\\ROOT\\TARGET";



	

	public static void main(String[] args) {
//		pack();
		/*if ( null != args && args.length > 3){

			for( String arg : args ){

				if(null == arg || !new File(arg).exists()){

					System.out.println(  "参数错误或文件夹不存在： " + arg);

					return ;

				}

				System.out.println(  "接收到的参数： " + arg);

			}

			javaPath = args[0];

			htmlPath = args[1];

			DATEPATH = args[2];

			classPath = args[3];

			System.out.println("开始进行文件比对...START");

			pack();

		}else{

			System.out.println("缺少参数");

		}*/

	}

	

	static void pack(){
		//路径重新更新
		classPath = PackMain.BASEDIR + "/WebRoot/WEB-INF/classes";
		
		File f1 = new File(javaPath);

		File f2 = new File(classPath);

		if (f1.exists() && f2.exists()) {

			createPath(f1);

		}

		System.out.println("共复制了" + count + "个class文件");

		copyFolder(javaPath, classPath);

		//目标文件夹

		

		copyFolder(javaPath,DATEPATH+"\\ROOT\\WEB-INF\\classes");

		copyFolder(htmlPath,DATEPATH+"\\ROOT");

		System.out.println("操作结束...END");

/*		try {

			 ZipUtil.zip(DATEPATH+"\\ROOT",DATEPATH,"ROOT.zip");

		} catch (Exception arg8) {

			arg8.printStackTrace();

		}*/

	}

	public static void createPath(File file) {

		if (file.exists()) {

			File[] files = file.listFiles();

			File[] arg4 = files;

			int arg3 = files.length;



			for (int arg2 = 0; arg2 < arg3; ++arg2) {

				File f = arg4[arg2];

				if (f.isDirectory()) {

					createPath(f);

				} else if (f.getName().endsWith(".java")) {

					String temp = f.getName().substring(0, f.getName().indexOf(".java"));

					String path = classPath

							+ f.getPath().substring(javaPath.length(), f.getPath().lastIndexOf("\\") + 1);

					File[] filels = (new File(path)).listFiles();



					for (int i = 0; i < filels.length; ++i) {

						if (filels[i].isFile() && filels[i].getName().indexOf(temp) != -1) {

							++count;

							File classfile = new File(path + filels[i].getName());

							File javafile = new File(

									f.getPath().substring(0, f.getPath().lastIndexOf("\\") + 1) + filels[i].getName());



							try {

								String e = classfile.getName();

								if (!e.substring(e.lastIndexOf(".") + 1).equals("bak")) {

									copyFile(classfile, javafile);

									f.delete();

								}

							} catch (Exception arg12) {

								arg12.printStackTrace();

							}



							System.out.println(filels[i].getName());

						}

					}

				} else if (f.getName().equals("config.properties")) {

					System.out.println("config.properties有更新，请手动增加部署说明");

				}

			}

		}



	}



	public static void copyFile(File f1, File f2) throws Exception {

		int length = 2097152;

		FileInputStream in = new FileInputStream(f1);

		FileOutputStream out = new FileOutputStream(f2);

		byte[] buffer = new byte[length];

		boolean len = false;



		int len1;

		while ((len1 = in.read(buffer)) != -1) {

			out.write(buffer, 0, len1);

		}



		in.close();

		out.flush();

		out.close();

	}



	public void newFolder(String folderPath) {

		try {

			String e = folderPath.toString();

			File myFilePath = new File(e);

			if (!myFilePath.exists()) {

				myFilePath.mkdir();

			}

		} catch (Exception arg3) {

			System.out.println("新建目录操作出错");

			arg3.printStackTrace();

		}



	}



	public void newFile(String filePathAndName, String fileContent) {

		try {

			String e = filePathAndName.toString();

			File myFilePath = new File(e);

			if (!myFilePath.exists()) {

				myFilePath.createNewFile();

			}



			FileWriter resultFile = new FileWriter(myFilePath);

			PrintWriter myFile = new PrintWriter(resultFile);

			myFile.println(fileContent);

			resultFile.close();

		} catch (Exception arg7) {

			System.out.println("新建文件操作出错");

			arg7.printStackTrace();

		}



	}



	public void delFile(String filePathAndName) {

		try {

			String e = filePathAndName.toString();

			File myDelFile = new File(e);

			myDelFile.delete();

		} catch (Exception arg3) {

			System.out.println("删除文件操作出错");

			arg3.printStackTrace();

		}



	}



	public static void delFolder(String folderPath) {

		try {

			delAllFile(folderPath);

			String e = folderPath.toString();

			File myFilePath = new File(e);

			myFilePath.delete();

		} catch (Exception arg2) {

			System.out.println("删除文件夹操作出错");

			arg2.printStackTrace();

		}



	}



	public static void delAllFile(String path) {

		File file = new File(path);

		if (file.exists()) {

			if (file.isDirectory()) {

				String[] tempList = file.list();

				File temp = null;



				for (int i = 0; i < tempList.length; ++i) {

					if (path.endsWith(File.separator)) {

						temp = new File(path + tempList[i]);

					} else {

						temp = new File(path + File.separator + tempList[i]);

					}



					if (temp.isFile()) {

						temp.delete();

					}



					if (temp.isDirectory()) {

						delAllFile(path + "/" + tempList[i]);

						delFolder(path + "/" + tempList[i]);

					}

				}



			}

		}

	}



	public void copyFile(String oldPath, String newPath) {

		try {

			boolean e = false;

			File oldfile = new File(oldPath);

			if (oldfile.exists()) {

				FileInputStream inStream = new FileInputStream(oldPath);

				FileOutputStream fs = new FileOutputStream(newPath);

				byte[] buffer = new byte[1444];



				int e1;

				while ((e1 = inStream.read(buffer)) != -1) {

					fs.write(buffer, 0, e1);

				}



				inStream.close();

			}

		} catch (Exception arg7) {

			System.out.println("复制单个文件操作出错");

			arg7.printStackTrace();

		}



	}



	public static void copyFolder(String oldPath, String newPath) {

		try {

			(new File(newPath)).mkdirs();

			File e = new File(oldPath);

			String[] file = e.list();
			if(file == null)return;
			File temp = null;



			for (int i = 0; i < file.length; ++i) {

				if (oldPath.endsWith(File.separator)) {

					temp = new File(oldPath + file[i]);

				} else {

					temp = new File(oldPath + File.separator + file[i]);

				}



				if (temp.isFile()) {

					FileInputStream input = new FileInputStream(temp);

					FileOutputStream output = new FileOutputStream(newPath + "/" + temp.getName().toString());

					byte[] b = new byte[5120];



					int len;

					while ((len = input.read(b)) != -1) {

						output.write(b, 0, len);

					}



					output.flush();

					output.close();

					input.close();

				}



				if (temp.isDirectory()) {

					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);

				}

			}

		} catch (Exception arg9) {

			System.out.println("复制整个文件夹内容操作出错");

			arg9.printStackTrace();

		}



	}



	public void moveFile(String oldPath, String newPath) {

		this.copyFile(oldPath, newPath);

		this.delFile(oldPath);

	}



	public void moveFolder(String oldPath, String newPath) {

		copyFolder(oldPath, newPath);

		delFolder(oldPath);

	}



	private void copyFile2(String source, String dest) {

		try {

			File in = new File(source);

			File out = new File(dest);

			FileInputStream inFile = new FileInputStream(in);

			FileOutputStream outFile = new FileOutputStream(out);

			byte[] buffer = new byte[10240];

			boolean i = false;



			int i1;

			while ((i1 = inFile.read(buffer)) != -1) {

				outFile.write(buffer, 0, i1);

			}



			inFile.close();

			outFile.close();

		} catch (Exception arg8) {

			;

		}



	}

}