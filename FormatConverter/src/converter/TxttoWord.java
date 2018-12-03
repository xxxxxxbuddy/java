package converter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.swing.*;

public class TxttoWord {
    public TxttoWord() {
    }

    public static boolean Excute(File srcFile,String desDirectory , String desFileName){
        File tarFile = null;
        if (!srcFile.getName().endsWith(".txt")) {
            return false;
        } else {
            try{
                tarFile = new File(desDirectory + desFileName);
                //tarFile = new File(srcFile.getParentFile(), srcFile.getName().replace(".txt", ".docx"));
                Scanner sc = new Scanner(srcFile);
                FileOutputStream fout = new FileOutputStream(tarFile);
                XWPFDocument document = new XWPFDocument();

                while(sc.hasNextLine()) {
                    XWPFParagraph paragraph = document.createParagraph();
                    XWPFRun run = paragraph.createRun();
                    run.setText(sc.nextLine());
                }

                document.write(fout);
                document.close();
                fout.close();
                sc.close();
                return true;
            }catch (Exception ex){
                ex.printStackTrace();
                return false;
            }
        }
    }
}
