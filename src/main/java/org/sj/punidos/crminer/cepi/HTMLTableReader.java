package org.sj.punidos.crminer.cepi;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sj.punidos.crminer.courses.Course;
import org.sj.punidos.crminer.courses.CourseXlsxWriter;
import org.sj.tools.graphics.tablemkr.Table;

public class HTMLTableReader {
	
	public HTMLTableReader() {
		
	}
	
	int countColumns(Element firstRow) {
		int columns = 0;
		Elements cells = firstRow.children();
		System.out.println("columns:");
		for(Element c: cells) {
			if(c.hasAttr("colspan")) {
				int inc = Integer.parseInt(c.attr("colspan"));
				System.out.println("  inc:"+inc);
				columns +=  inc;
			} else {
				System.out.println("  +1:");
				columns++;
			}
		}
		return columns;
	}
	
	public Table parseTable(Element htmlTable) {
		//return null;
		// - count rows
		//- cont columns in first row
		//- create table

		Elements rows = htmlTable.select("tr");
		int countRows = rows.size(); // assuming all children ar <tr>
		//htmlTable.c
		int cols = countColumns(rows.first());
		System.out.println("create table:"+cols+","+countRows);
		Table t = new Table(cols, countRows);
		
		int numRow = 0;
		for(Element row: rows) {
			int numCol = 0;
			for(Element cell: row.select("td")) {
				//System.out.println("   data:"+cell.ownText());
				int colSpan = 1;
				int rowSpan = 1;
				
				t.add(numCol, numRow, cell.ownText());
				
				if(cell.hasAttr("colspan")) {
					colSpan = Integer.parseInt(cell.attr("colspan"));
				}
				if(cell.hasAttr("rowspan")) {
					rowSpan = Integer.parseInt(cell.attr("rowspan"));
				}

				if(rowSpan > 1 || colSpan > 1) {
					t.spanCell(numCol, numRow, colSpan, rowSpan);
				}
				
				numCol +=  colSpan;

			}
			numRow++;
		}
		
		return t;
	}
	
	public List<Table> read(String filename) {
		Document doc;
		List<Table> tables = new LinkedList<Table>();
		//File f = new File(filename);
		try { 
			doc = Jsoup.parse(new File(filename), "UTF-8");
			Elements htmlTables = doc.select("table");
		
		for (Element t : htmlTables) { 
			tables.add(parseTable(t));
		}
		} catch(IOException e) {
			e.printStackTrace();
		}
		return tables;
		//throw new UnsupportedOperationException("TO-DO");
	}
	
	public static void main(String arg[]) throws IOException {
		//String filename = "out/cepi-demo/html/agosto/programacion_cepi_alcala_agosto_2020_0.html";
		String filename = "out/cepi-demo/html/programacion_cepi_madrid_usera_villaverde_octubre_2020.html";
		//String filename = "res/test_table2.html";
		
		HTMLTableReader reader = new HTMLTableReader();
		
		List<Table> tables = reader.read(filename);
		
		CourseFactory factory = new CourseFactory("CEPI USERA-VILLAVERDE", tables);
		System.out.println("Tables: " +tables.size());

		List<Course> courses = factory.getCourses();
		for(Course c: courses) {
			System.out.println(c.toString());
		}
		
		CourseXlsxWriter writer = new CourseXlsxWriter("out/html-test.xlsx");
		writer.write(courses);
		writer.writeAndClose();
		
		
		
	}

}
