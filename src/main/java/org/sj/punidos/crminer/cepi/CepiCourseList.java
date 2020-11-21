package org.sj.punidos.crminer.cepi;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.sj.punidos.crminer.courses.Course;
import org.sj.tools.graphics.tablemkr.frompdf.PDFTableExtractor;
import org.sj.tools.graphics.tablemkr.util.PDFTableExporter;

public class CepiCourseList {
	
	File pdf;
	String name;
	
	CepiCourseList(CepiCourseList cepi) {
		name = cepi.name;
		pdf = cepi.pdf;
		//dstHtml = cepi.dstHtml;
	}
	
	public CepiCourseList(String _name, File _pdf) {
		name = _name;
		pdf = _pdf;
	}
	

	
	
	public void exportCoursesToHTML(String dstHtmlPath)
	{
		PDFTableExporter exp = new PDFTableExporter();
		exp.setDestination(dstHtmlPath);
		exp.run(pdf.getAbsolutePath());
	}

	
	public List<Course> getCourses() 
	{
		// ---
		PDFTableExtractor extractor = new PDFTableExtractor(pdf);
		CourseFactory factory;
		try {
			factory = new CourseFactory(name, extractor.getAllTables());
			List<Course> courses = factory.getCourses();
			System.out.println("Failed: "+factory.countFailed());
			return courses;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		
	}

}
