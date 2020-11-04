package org.sj.punidos.crminer.cepi;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sj.punidos.crminer.courses.Course;
import org.sj.tools.graphics.tablemkr.frompdf.PDFTableExtractor;
import org.sj.tools.graphics.tablemkr.util.PDFTableExporter;
import org.sj.tools.pdfjuice.ExampleGenerator;

public class CepiWeb {
	
	String name;
	String link;
	File dstPath;
	WebResource pdf;
	
	CepiWeb(CepiWeb cepi) {
		name = cepi.name;
		link = cepi.link;
		pdf = cepi.pdf;
		dstPath = cepi.dstPath;
	}
	
	public CepiWeb(String name, String link) {
		this.name = name;
		this.link = link;
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
	
	
	
	
	/**
	 * 
	 * @param pdfPath
	 * @return
	 */
	public void exportCoursesToHTML(String pdfPath, String dstPath) {
		PDFTableExporter exp = new PDFTableExporter();
		exp.setDestination(dstPath);
		exp.run(pdfPath);
	}
	
	
	public String downloadPDF(File base) {
		File dstDir = new File(base, "pdf/");
		CepiList.createDirectory(dstDir);
		    
		//File storePath = new File(dstDir);
		pdf = new WebResource(findCourses());
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
		File dstLocation = new File(base, "html/");
		CepiList.createDirectory(dstLocation);
		String pdfName = pdf.getFilename();
		dstPath = new File(dstLocation, ExampleGenerator.changeExtension(pdfName, ".html"));
		if(!dstPath.exists()) {
			exportCoursesToHTML(pdfPath, dstPath.getAbsolutePath());
		}
		System.out.println("File: "+ dstPath);
		}
	}
	
	public List<Course> getCourses() {
		File base = new File(CepiList.BASE_DIR);
		downloadPDF(base);

		// ---
		PDFTableExtractor extractor = new PDFTableExtractor(pdf.getFile());
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
	
	public String getName() {
		return name;		
	}
	
	public String getDstPath() {
		return dstPath.getAbsolutePath();
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
