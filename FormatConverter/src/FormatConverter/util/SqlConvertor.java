package FormatConverter.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 数据库与Excel的相关转换
 */
public final class SqlConvertor
{
    private static Logger logger = Logger.getLogger(SqlConvertor.class);

    /**
     * 通过数据集生成对应的Excel文件 （默认以 ".xlsx" 为后缀）
     * 
     * @param resultSet    查询结果数据集
     * @param desDirectory 生成文件的父目录
     * @param desFileName  文件名（不含后缀）
     * @return 返回生成的文件File
     * @throws SQLException 数据库异常
     * @throws IOException  文件读写异常
     */
    public static File ReusltSetToExcel(ResultSet resultSet, String desDirectory, String desFileName)
	    throws SQLException, IOException
    {
	logger.info("开始根据ResultSet生成Excel文件...");

	if (resultSet == null)
	{
	    logger.info("数据集不存在...");
	    return null;
	}

	logger.info("初始化要生成的Excel文件...");
	File file = new File(desDirectory, desFileName + ".xlsx");
	ResultSetMetaData data;

	logger.info("初始化Excel工作簿...");
	try (XSSFWorkbook workbook = new XSSFWorkbook(); FileOutputStream fout = new FileOutputStream(file);)
	{
	    logger.info("获取数据集元数据...");
	    data = resultSet.getMetaData();

	    logger.info("建立工作表sheet1...");
	    XSSFSheet sheet = workbook.createSheet("sheet1");

	    logger.info("读取数据集并向Excel写入查询数据表的列名...");
	    int rowIndex = 0;
	    XSSFRow columnNames = sheet.createRow(rowIndex++);
	    for (int col = 0; col < data.getColumnCount(); col++)
		columnNames.createCell(col).setCellValue(data.getColumnName(col + 1));

	    logger.info("读取数据集并向Excel写入每一行数据...");
	    while (resultSet.next())
	    {
		XSSFRow row = sheet.createRow(rowIndex++);
		for (int col = 0; col < data.getColumnCount(); col++)
		    row.createCell(col).setCellValue(resultSet.getString(col + 1));
	    }

	    logger.info("将数据写入文件...");
	    workbook.write(fout);
	    fout.flush();
	}
	catch (SQLException e)
	{
	    logger.error("数据库异常：" + e.getMessage());
	    throw e;
	}
	catch (FileNotFoundException e)
	{
	    logger.error("文件未找到：" + e.getMessage());
	}
	catch (IOException e)
	{
	    logger.error("文件读写异常：" + e.getMessage());
	    throw e;
	}
	finally
	{
	    logger.info("ResultSet生成Excel文件完毕...");
	    resultSet.close();
	}

	return file;
    }

    /**
     * 根据Excel表格内容生成对应的sql插入语句
     * 
     * @param file      传入的xlsx或xls后缀的Excel文件
     * @param tableName 数据表名
     * @return 插入的sql语句
     * @throws IOException 文件读写异常
     */
    public static String ExcelToSql(File file, String tableName) throws IOException
    {
	logger.info("开始根据Excel表格生成sql插入语句...");

	if (!file.getName().endsWith(".xls") && !file.getName().endsWith(".xlsx"))
	{
	    logger.info("传入文件格式不是xls或xlsx...");
	    return null;
	}

	StringBuffer buffer = new StringBuffer();
	logger.info("读取Excel文件...");
	try (Workbook workbook = WorkbookFactory.create(file);)
	{
	    logger.info("读取第一个工作表...");
	    Sheet sheet = workbook.getSheetAt(0);

	    // 获取行数
	    int rowCount = sheet.getLastRowNum() + 1;
	    // 获取第一行即列名
	    Row columnNames = sheet.getRow(0);
	    if (columnNames == null)
	    {
		logger.info("Excel第一个工作表中没有第一行...");
		return null;
	    }

	    logger.info("生成Sql语句...");
	    // 获取列数
	    int columnCount = columnNames.getPhysicalNumberOfCells();
	    // 读取第一行生成insert语句中的列名
	    buffer.append(String.format("insert into %s (", tableName));
	    for (int col = 0; col < columnCount; col++)
		buffer.append(String.format("%s%c", columnNames.getCell(col).getStringCellValue(),
			col + 1 == columnCount ? ')' : ','));
	    buffer.append(" values ");

	    // 读取剩下的行，生成插入的值
	    for (int r = 1; r < rowCount; r++)
	    {
		Row row = sheet.getRow(r);
		buffer.append("(");
		for (int col = 0; col < columnCount; col++)
		    buffer.append(String.format("'%s'%c", row.getCell(col).getStringCellValue(),
			    col + 1 == columnCount ? ')' : ','));
		buffer.append(r + 1 == rowCount ? " ; " : " , ");
	    }
	}
	catch (EncryptedDocumentException e)
	{
	    logger.error("文档编码异常：" + e.getMessage());
	    throw e;
	}
	catch (IOException e)
	{
	    logger.error("文件读写异常：" + e.getMessage());
	    throw e;
	}
	finally
	{
	    logger.info("读取Excel表格生成sql插入语句完成...");
	}

	return buffer.toString();
    }
}
