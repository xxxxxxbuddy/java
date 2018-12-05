package FormatConverter.util;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;

public class ExcelToSql {
    /**
     * 根据Excel表格内容生成对应的sql插入语句
     *
     * @param file      传入的xlsx或xls后缀的Excel文件
     * @param tableName 数据表名
     * @return 插入的sql语句
     */
    public static String convert(File file, String tableName) {
        StringBuffer buffer = new StringBuffer();

        // 传入文件格式不匹配返回null
        if (!file.getName().endsWith(".xls") && !file.getName().endsWith(".xlsx")) return null;

        Workbook workbook;
        try {
            // 读取Excel文件
            workbook = WorkbookFactory.create(file);
            // 读取第一个工作表
            Sheet sheet = workbook.getSheetAt(0);
            // 获取行数
            int rowCount = sheet.getLastRowNum() + 1;
            // 获取第一行即列名
            Row columnNames = sheet.getRow(0);
            if (columnNames == null) return null;

            // 获取列数
            int columnCount = columnNames.getPhysicalNumberOfCells();
            // 读取第一行生成insert语句中的列名
            buffer.append(String.format("insert into %s (", tableName));
            for (int col = 0; col < columnCount; col++)
                buffer.append(String.format("%s%c", columnNames.getCell(col).getStringCellValue(),
                        col + 1 == columnCount ? ')' : ','));
            buffer.append(" values ");

            // 读取剩下的行，生成插入的值
            for (int r = 1; r < rowCount; r++) {
                Row row = sheet.getRow(r);
                buffer.append("(");
                for (int col = 0; col < columnCount; col++)
                    buffer.append(String.format("'%s'%c", row.getCell(col).getStringCellValue(),
                            col + 1 == columnCount ? ')' : ','));
                buffer.append(r + 1 == rowCount ? " ; " : " , ");
            }
        } catch (EncryptedDocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }
}
