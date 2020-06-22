package org.sj.punidos.crminer.cepi;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sj.tools.graphics.tablemkr.util.PDFTableExporter;

public class CepiWeb {
	
	String link;
	
	public CepiWeb(String link) {
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
	 * @param dstDir directory to store file into. 
	 * @return path to saved file.
	 */
	public String downloadCourses(String dstDir) {
	    String fileURL = this.findCourses();
	    try {
	    	return HttpDownloadUtility.downloadFile(fileURL, dstDir);
	    } catch (IOException ex) {
	    	ex.printStackTrace();
	    }
		return null;
	}
	
	/**
	 * 
	 * @param pdfPath
	 * @return
	 */
	public String exportCourses(String pdfPath) {
		PDFTableExporter exp = new PDFTableExporter();
		exp.run(pdfPath);
		return null; // TODO
	}
	
	public static void main(String args[]) {
		System.out.println("testing urls");
		CepiWeb cepi = new CepiWeb("https://www.comunidad.madrid/centros/cepi-madrid-arganzuela");
		String pdf = cepi.findCourses();
		System.out.println("Downloading...");
		String pdfPath = cepi.downloadCourses("res/"); 
		cepi.exportCourses(pdfPath);
		
		System.out.println("File: "+ pdfPath);
	}

}
