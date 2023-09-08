package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	private static File filepath = new File(System.getProperty("user.dir")+"\\src\\test\\java\\resources\\testData.xlsx");
	private FileInputStream excel;
	private XSSFWorkbook wb;
	private XSSFSheet sheet;
	
	public ExcelUtils(String sheetName) {
		try {
			excel = new FileInputStream(filepath);
			wb = new XSSFWorkbook(excel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sheet = wb.getSheet(sheetName);
	}
	
	private int getRowNumber(String rowText) {
		int totalRows = sheet.getLastRowNum();
		int rowIndex = 0;
		for(int i=1; i<=totalRows; i++) {
			String cv = sheet.getRow(i).getCell(0).toString();
			if(cv.equals(rowText)) {
				rowIndex = i;
				break;
			}
		}
		System.out.println("Row Number : "+rowIndex);
		return rowIndex;
	}
	
	private int getColNumber(String colText) {
		int totalCols = sheet.getRow(0).getLastCellNum();
		int colIndex = 0;
		for(int i=1; i<totalCols; i++) {
			String cv = sheet.getRow(0).getCell(i).toString();
			if(cv.equals(colText)) {
				colIndex = i;
				break;
			}
		}
		System.out.println("Col Number : "+colIndex);
		return colIndex;
	}
	
	public String getCellValue(String rowId, String colId) {
		return sheet.getRow(getRowNumber(rowId)).getCell(getColNumber(colId)).toString();
	}
}
