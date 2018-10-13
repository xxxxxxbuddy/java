package copyFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class CopyFile {
	
	public static void copyDir(String sourcePath, String targetPath) throws IOException {
		File file = new File(sourcePath);
		String[] filePath = file.list();	//��ȡԴ�ļ����е������ļ���
		/* ���Ŀ���ļ����Ƿ��Ѿ�����  */
		if (!(new File(targetPath)).exists()) {
			(new File(targetPath)).mkdir();
		}
		
		/* ���Ŀ�����ļ������ļ���  */
		for (int i = 0; i < filePath.length; i++) {
			if ((new File(sourcePath + File.separator + filePath[i])).isDirectory()) {	//Ŀ¼
				copyDir(sourcePath + File.separator + filePath[i], targetPath + File.separator + filePath[i]);
			}
			if (new File(sourcePath + File.separator + filePath[i]).isFile()) {			//�ļ�
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
	 System.out.println("������ԴĿ¼����·�����磺d:/test��");
	 String sourcePath = sc.nextLine();
	 System.out.println("��������Ŀ¼��");
	 String targetPath = sc.nextLine();
	 copyDir(sourcePath, targetPath);
	 System.out.println("���Ƴɹ���");
	}
	

}