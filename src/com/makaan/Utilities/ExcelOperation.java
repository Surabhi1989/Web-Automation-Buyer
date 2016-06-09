package com.makaan.Utilities;

import java.io.FileInputStream;

import java.io.IOException;

import java.util.ArrayList;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;




 public class ExcelOperation {
	 
	 
	
	

	/*public static void Write_Excel(String URL, String Result)
			throws FileNotFoundException, IOException {
		FileInputStream fis = new FileInputStream(File);
		// FileInputStream fis = new FileInputStream("D://sheet22.xls");
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		Sheet currentSheet = workbook.getSheetAt(0);

		for (int i = 1; i <= currentSheet.getLastRowNum(); i++) {
			Row currentRow = currentSheet.getRow(i);
			// System.out.println(currentRow);

			int currentHTTPStatus = (int) currentRow.getCell(1)
					.getNumericCellValue();

			String currentURL = currentRow.getCell(2).getStringCellValue();

			if (currentURL.equals(URL)) {
				currentRow.createCell(3).setCellValue(Result);
				System.out.println("pass");

			}

		}
			System.out.println("URL not found in file");
		
		FileOutputStream fos = new FileOutputStream(File);
		// FileOutputStream fos = new FileOutputStream("D://sheet22.xls");
		workbook.write(fos);
		System.out.println("File is been written");
		fos.close();
		fis.close();
	}*/

	public static List<String> Read_Excel(String File) throws IOException {
		FileInputStream fis = new FileInputStream(File);

		HSSFWorkbook wb = new HSSFWorkbook(fis);
		Sheet currentSheet = wb.getSheetAt(0);

		List<String> str = new ArrayList();
		for (int i = 1; i <= currentSheet.getLastRowNum(); i++) {
			Row currentRow = currentSheet.getRow(i);

			String Name = currentRow.getCell(0).getStringCellValue();
			// String code = String.valueOf(currentHTTPStatus);
			//String currentURL = currentRow.getCell(2).getStringCellValue();

			str.add(Name);

		}

		return str;

	}

	
	/*public static void Compare_ValuesinExcel() throws IOException {
		FileInputStream fis = new FileInputStream(File);

		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		Sheet currentSheet = workbook.getSheetAt(0);
		String response_string = null;

		for (int i = 1; i <= currentSheet.getLastRowNum(); i++) {
			Row currentRow = currentSheet.getRow(i);

			response_string = currentRow.getCell(3).getStringCellValue();
			String res = currentRow.getCell(1).getNumericCellValue() + "";

			if (res.contains(response_string)) {
				currentRow.createCell(4).setCellValue("Pass");
			} else
				currentRow.createCell(4).setCellValue("Fail");
		}

		FileOutputStream fos = new FileOutputStream(File);

		workbook.write(fos);
		System.out.println("File is been written");
		fos.close();
		fis.close();
	}*/
/*public static String Read_env() throws IOException {

		FileInputStream fis = new FileInputStream(File);

		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		Sheet currentSheet = workbook.getSheetAt(0);

		String Environment = currentSheet.getRow(0).getCell(0)
				.getStringCellValue();
		return Environment;

	}*/

}