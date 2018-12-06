package FormatConverter.util;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultSetToExcel {
    /**
     * 通过数据集生成对应的Excel文件 （默认以 ".xlsx" 为后缀）
     *
     * @param rs   查询结果数据集
     * @param path 生成文件的父目录
     * @param name 文件名（不含后缀）
     * @return 生成的Excel文件File类
     */
    public static File convert(ResultSet rs, String path, String name) {
        File file = null;

        if (rs == null) return file;

        // 初始化要生成的Excel文件
        file = new File(path, name + ".xlsx");
        ResultSetMetaData data;

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            // 获取数据集元数据
            data = rs.getMetaData();
            // 建立工作表，默认为sheet1
            XSSFSheet sheet = workbook.createSheet("sheet1");

            // 获取列名
            int rowIndex = 0;
            XSSFRow columnNames = sheet.createRow(rowIndex++);
            for (int col = 0; col < data.getColumnCount(); col++)
                columnNames.createCell(col).setCellValue(data.getColumnName(col + 1));

            // 读取每一列数据
            while (rs.next()) {
                XSSFRow row = sheet.createRow(rowIndex++);
                for (int col = 0; col < data.getColumnCount(); col++)
                    row.createCell(col).setCellValue(rs.getString(col + 1));
            }

            // 写入文件
            FileOutputStream fout = new FileOutputStream(file);
            workbook.write(fout);
            fout.flush();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }
}
