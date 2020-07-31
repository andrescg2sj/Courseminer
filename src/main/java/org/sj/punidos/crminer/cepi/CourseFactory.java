package org.sj.punidos.crminer.cepi;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.sj.punidos.crminer.courses.Course;
import org.sj.tools.graphics.tablemkr.Cell;
import org.sj.tools.graphics.tablemkr.Table;
import org.sj.tools.graphics.tablemkr.frompdf.PDFTableExtractor;

public class CourseFactory {
	
	List<Table> tables = new LinkedList<Table>();
	List<Course> courses;

	public static final int STAT_NONE = 0;

	public static final int STAT_GEN_HEAD = 1;
	public static final int STAT_TABLE_HEAD = 2;
	public static final int STAT_DATA_TITLES = 3;
	public static final int STAT_DATA = 4;
	
	int status = STAT_NONE;
	String titles[];
	
	int i_date = 1;
	int i_hours= 2;
	int i_register = 3;
	int i_name = 0;
	
	int failed = 0;
	
	String centerName;
	
	public CourseFactory(String centerName, Collection<Table> t) {
		this.centerName = centerName;
		tables.addAll(t);
	}
	
	List<Course> processTable(Table t) {
		//System.out.println(t.toHTML());
		LinkedList<Course> crs = new LinkedList<Course>();
		if(t.getCols() == 1 && t.getRows() == 1) {
			//System.out.println("Processiong general");

			processGeneralHeader(t.get(0,0));
			return crs;
		} else {
			//System.out.println("Processing table: rows:" + t.getRows());
			for(int r = 0; r<t.getRows(); r++) {
				Cell c = t.get(0, r);
				if(c.getColSpan() == t.getCols()) {
					//System.out.println("Processiong header");
					processTableHeader(c);
				} else {
					//System.out.println("Reading course");
					Course course = readData(t,r);
					if(course != null) {
						crs.add(course);
					}
				}
			}
		}
		return crs; 
	}
	
	
	Vector<String> readRow(Table t, int row) {
		Vector<String> data = new Vector<String>(t.getCols());
		for(int i =0; i < t.getCols(); i++) {
			Vector<String> cnt = t.getContents(i, row);
			String fullStr = String.join(" ", cnt);
			data.add(fullStr);
		}
		return data;
	}
	
	//TODO regex
	int find(String str, Vector<String> data) {
		
		for(int i=0;i<data.size();i++) {
			if(data.get(i).trim().equals(str)) {
				return i;
			}
		}
		return -1;
	}
	
	public int countFailed() {
		return failed;
	}
	
	
	void readTitles(Table t, int row) {
		Vector<String> titles = readRow(t,row);
		System.out.println("Titles: "+ String.join(" - ", titles));
		
		i_hours = find("Horario", titles);
		i_date = find("Fecha", titles);
		i_register = find("Inscripción", titles);
		System.out.println("hours:"+i_hours);
		System.out.println("date:"+i_date);
		System.out.println("reg:"+i_register);
	}
	
	boolean validIndexes() {
		return i_hours >= 0 && i_date >= 0 && i_register >= 0;
	}
	
	boolean isTitleRow(Vector<String> data) {
		if(!data.get(i_date).contains("Fecha"))
			return false;
		if(!data.get(i_register).contains("Inscripción"))
			return false;
		if(!data.get(i_hours).contains("Horario"))
			return false;
		return true;
	}

	
	Course readData(Table t, int row) {
		Vector<String> data = readRow(t,row);
		
		if(!validIndexes()) {
			failed++;
			return null;
		}
		if(isTitleRow(data)) {
			return null;
		}
		
		String name = "";
		String date = "";
		String hours = "";
		String register = "";
		
		//TODO: make a more robust malformed table management.
		try {
			name = data.get(i_name).trim();
			date = data.get(i_date).trim();
			hours = data.get(i_hours).trim();
			register = data.get(i_register).trim();
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Warning: "+ e.toString());
		}
		
		Course c = new Course(name, date, hours, register); 
		c.setEntity(centerName.trim());
		return c;
	}
	
	boolean afterHeader() {
		return (status == STAT_TABLE_HEAD || status == STAT_GEN_HEAD);
	}
	
	
	void processTableHeader(Cell c) {
		status = STAT_TABLE_HEAD;
	}
	
	void processGeneralHeader(Cell c) {
		status = STAT_GEN_HEAD;
	}
	
	
	
	
	public List<Course> getCourses() {
		if(status == STAT_NONE) {
			System.out.println("Processing");
			courses = new LinkedList<Course>();
			for(Table t: tables) {
				courses.addAll(processTable(t));
			}
		} else {
			System.out.println("Unexpected status: " + status);

		}
		return courses;
	}
	
	public static void main(String arg[]) {
		String example = "res/CEPI-1-1.pdf";
		File file = new File(example);
		PDFTableExtractor extractor = new PDFTableExtractor(file);
		CourseFactory factory;
		try {
			List<Table> tables = extractor.getAllTables();
			System.out.println("Tables: " + tables.size());
			factory = new CourseFactory("ARGANZUELA", tables);
			List<Course> courses = factory.getCourses();
			System.out.println("COURSES:");
			for(Course c : courses) {
				System.out.println(c.toString());
			}

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
