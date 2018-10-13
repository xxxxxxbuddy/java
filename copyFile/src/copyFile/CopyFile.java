package copyFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class CopyFile {
	
	public static void copyDir(String sourcePath, String targetPath) throws IOException {
		File file = new File(sourcePath);
		String[] filePath = file.list();	//获取源文件夹中的所有文件名
		/* 检测目标文件夹是否已经存在  */
		if (!(new File(targetPath)).exists()) {
			(new File(targetPath)).mkdir();
		}
		
		/* 检测目标是文件还是文件夹  */
		for (int i = 0; i < filePath.length; i++) {
			if ((new File(sourcePath + File.separator + filePath[i])).isDirectory()) {	//目录
				copyDir(sourcePath + File.separator + filePath[i], targetPath + File.separator + filePath[i]);
			}
			if (new File(sourcePath + File.separator + filePath[i]).isFile()) {			//文件
			copyFile(sourcePath + File.separator + filePath[i], targetPath + File.separator + filePath[i]);
			}
		}
	}
	
	public static void copyFile(String oldPath, String targetPath) throws IOException {
		File oldFile = new File(oldPath);
		File newFile = new File(targetPath);
		FileInputStream in = new FileInputStream(oldFile);
		FileOutputStream out = new FileOutputStream(newFile);;
		
		byte[] buffer=new byte[1073741824];
		int readByte = 0;
		while((readByte = in.read(buffer)) != -1){
			out.write(buffer, 0, readByte);
		}
		
		in.close();
		out.close();
		
	}

	public static void main(String[] args) throws IOException {
	 Scanner sc = new Scanner(System.in);
	 System.out.println("请输入源目录完整路径（如：d:/test）");
	 String sourcePath = sc.nextLine();
	 System.out.println("请输入新目录：");
	 String targetPath = sc.nextLine();
	 copyDir(sourcePath, targetPath);
	 System.out.println("复制成功！");
	}
	

}