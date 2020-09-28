package org.sj.punidos.crminer.courses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class CourseXlsxWriter extends CourseSheetWriter {
	
	//FileOutputStream fos;
	File fout;
    XSSFWorkbook workbook;
    XSSFSheet sheet; 
    
	public CourseXlsxWriter(String filename) {
		fout = new File(filename);
		workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `
		sheet = workbook.createSheet();
	}

	@Override
	public void write(Course c) throws IOException {
		// TODO Auto-generated method stub
		int rownum = sheet.getLastRowNum();  
		Row row = sheet.createRow(rownum+1);
		int colnum = 0;
		Vector<String> data  = formatVector(c);
		for(String d: data) {
			Cell cell = row.createCell(colnum++);
			cell.setCellValue(d);
		}
		
	}
	
	public void writeAndClose() throws IOException {
		FileOutputStream fos = new FileOutputStream(fout);
		workbook.write(fos);
		workbook.close();
	}

}
