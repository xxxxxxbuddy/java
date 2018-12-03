package converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public final class WordtoTxt {
    public WordtoTxt() {
    }

    public static boolean Excute(File srcFile, String desDirectory,String desFileName) {
        File tarFile = null;
        PrintWriter pw;
        System.out.println(desDirectory + desFileName);
        try{
            if (srcFile.getPath().endsWith(".doc")) {
                tarFile = new File(desDirectory + desFileName);
                pw = new PrintWriter(tarFile);
                WordExtractor ex = new WordExtractor(new FileInputStream(srcFile));
                pw.print(ex.getText());
                pw.close();
                ex.close();
                return true;
            } else if (srcFile.getPath().endsWith(".docx")) {
                tarFile = new File(desDirectory + desFileName);

                pw = new PrintWriter(tarFile);
                XWPFDocument docx = new XWPFDocument(new FileInputStream(srcFile));
                XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
                pw.print(extractor.getText());
                pw.close();
                extractor.close();
                return true;
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

        return true;
    }
}
