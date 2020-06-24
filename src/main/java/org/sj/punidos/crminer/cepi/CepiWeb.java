package org.sj.punidos.crminer.cepi;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sj.tools.graphics.tablemkr.util.PDFTableExporter;
import org.sj.tools.pdfjuice.ExampleGenerator;

public class CepiWeb {
	
	String name;
	String link;
	File dstPath;
	File pdfFile;
	String pdfURL;
	
	CepiWeb(CepiWeb cepi) {
		name = cepi.name;
		link = cepi.link;
		pdfFile = cepi.pdfFile;
		dstPath = cepi.dstPath;
		pdfURL = cepi.pdfURL;
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
	public void exportCourses(String pdfPath, String dstPath) {
		PDFTableExporter exp = new PDFTableExporter();
		exp.setDestination(dstPath);
		exp.run(pdfPath);
	}
	
	public static String getURLFilename(String url) {
		int j = url.lastIndexOf("/");
		if(j == -1) return url;
		return url.substring(j+1);
	}
	
	public void downloadAndProcess() {
		pdfURL = findCourses();
		String pdfName = getURLFilename(pdfURL);
		String dstDir = "res/cepi/";
		File storePath = new File(dstDir); 
		pdfFile = new File(storePath, pdfName);
		String pdfPath = pdfFile.getPath();
		if(pdfFile.exists()) {
			System.out.println("Present: " +pdfPath);
		} else {
			System.out.println("Downloading...");
			pdfPath = downloadCourses(dstDir);
		}
		File dstLocation = new File("out/cepi");
		dstPath = new File(dstLocation, ExampleGenerator.changeExtension(pdfName, ".html"));
		if(!dstPath.exists()) {
			exportCourses(pdfPath, dstPath.getAbsolutePath());
		}
		System.out.println("File: "+ dstPath);
		
	}
	
	public String getDstPath() {
		return dstPath.getAbsolutePath();
	}
	
	public static void main(String args[]) {
		System.out.println("testing urls");
		CepiWeb cepi = new CepiWeb("Arganzuela", "https://www.comunidad.madrid/centros/cepi-madrid-arganzuela");
		cepi.downloadAndProcess();
			
	}

}
