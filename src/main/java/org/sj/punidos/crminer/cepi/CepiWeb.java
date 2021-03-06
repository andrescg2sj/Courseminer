package org.sj.punidos.crminer.cepi;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sj.punidos.crminer.courses.Course;
import org.sj.tools.pdfjuice.ExampleGenerator;

public class CepiWeb  {
	
	public static final String OUT_PDF_DIR = "pdf";  
	public static final String OUT_HTML_DIR= "html";  
	
	String name;
	CepiCourseList courseList;
	String link;
	WebResource pdf;
	File dstHtml;

	
	CepiWeb(CepiWeb cepi) {
		name = cepi.name;
		link = cepi.link;
		pdf = cepi.pdf;
		dstHtml = cepi.dstHtml;
	}
	
	public CepiWeb(String _name, String _link) {
		name = _name;
		link = _link;
	}
	
	public String findCourses() {
		String selected = null;
			
		Document doc;
		try {
			doc = Jsoup.connect(link).get();
			Elements links = doc.select("a[href~=.\\.pdf]");
			if(links.size() > 0) {
				selected = links.get(0).attr("href");
			}
			
			for (Element link : links) {  
			    System.out.println("\nlink : " + link.attr("href"));  
			    System.out.println("text : " + link.text());  
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return selected;
	}
	
	
	public String formatDstPath(String folder) {
		String month = DateManager.getMonth(pdf.url);
		if("".equals(month)) {
			System.out.println("Warning! no month in: "+pdf.url);
			return folder + "/";
		}
		DateManager datem = new DateManager(pdf.url);
		return String.format("%s/%d/%02d/", folder, datem.getYear(), datem.getMonthNum());
	}
	

	
	
	public String downloadPDF(File base) {
		pdf = new WebResource(findCourses());
		File dstDir = new File(base, formatDstPath(OUT_PDF_DIR));
		CepiList.createDirectory(dstDir);
		    
		//File storePath = new File(dstDir);
		return pdf.download(dstDir);
	}
	
	public void downloadAndExportToHTML() {
		File base = new File(CepiList.BASE_DIR);
		downloadAndExportToHTML(base);
	}

	
	public void downloadAndExportToHTML(File base) {
		String pdfPath = downloadPDF(base);
		if(pdfPath == null) {
		    System.err.println("null resource for " + link);
		} else {
			File dstLocation = new File(base, formatDstPath(OUT_HTML_DIR));
			CepiList.createDirectory(dstLocation);
			String pdfName = pdf.getFilename();

			dstHtml = new File(dstLocation, ExampleGenerator.changeExtension(pdfName, ".html"));
			if(!dstHtml.exists()) {
				courseList = new CepiCourseList(name, pdf.getFile());
				courseList.exportCoursesToHTML(dstHtml.getAbsolutePath());
			}
			System.out.println("File: "+ dstHtml);
		}
	}
	
	public String getDstPath() {
		return dstHtml.getAbsolutePath();
	}

	
	
	public List<Course> getCourses() {
		File base = new File(CepiList.BASE_DIR);
		downloadPDF(base);
		courseList = new CepiCourseList(name, pdf.file);
		return courseList.getCourses();
	}
	
	public String getName() {
		return name;		
	}
	
	
	public static void main(String args[]) {
		System.out.println("testing urls");
		if(args.length == 2) {
			String name = args[0];
			String url = args[1];
			CepiWeb cepi = new CepiWeb(name, url);
			//cepi.downloadAndProcess();
			System.out.println("Showing courses:");
			List<Course> courses = cepi.getCourses();
			for(Course c : courses) {
				System.out.println(c.toString());
			}
		}
			
	}

}
