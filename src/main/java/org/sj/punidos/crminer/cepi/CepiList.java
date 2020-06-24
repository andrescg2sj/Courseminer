package org.sj.punidos.crminer.cepi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.sj.punidos.crminer.CommonInfo;
import org.sj.tools.graphics.tablemkr.frompdf.PDFPageTableExtractor;

public class CepiList implements CommonInfo {
	
	List<CepiWeb> cepis = new LinkedList<CepiWeb>();
	
	public CepiList(String listfile) throws IOException {
		load(listfile);
	}
	
	public static String unQuote(String str) {
		if(str.startsWith("\"")) {
			str = str.substring(1);
		}
		if(str.endsWith("\"")) {
			str = str.substring(0, str.length()-1);
		}
		return str;
	}
	
	
	void load(String path) throws IOException {
		File f = new File(path);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		
		while(br.ready()) {
			String line = br.readLine();
			line = line.trim();
			if(line.length() > 0) {
				String parts[] = line.split(",");
				String link = parts[0];
				String name = unQuote(parts[1]);
				CepiWeb cepi = new CepiWeb(name, link);
				cepis.add(cepi);
			}
		}
		br.close();
	}
	
	public void processAll() {
		StringBuilder report = new StringBuilder();
		report.append("<table>");
		for(CepiWeb cepi : cepis) {
			System.out.println("Cepi: "+cepi.link);
			cepi.downloadAndProcess();
			CepiWebReport rep = new CepiWebReport(cepi);
			report.append(rep.getHtmlRow(cepi));
		}
		report.append("</table>");
		writeFullReport("out/report.html", report.toString());
	}
	
	public void writeFullReport(String dstFilename, String tablehtml) {
		try {
			File dstFile = new File(dstFilename);
	    	FileOutputStream fos = new FileOutputStream(dstFile);
	    	OutputStreamWriter out = new OutputStreamWriter(fos);
	
	    	//write head
	    	out.write("<body><html>" + NEW_LINE);
	    	
	    	out.write(tablehtml);
	
		    //write tail
	    	out.write("</body></html>");
	    	out.close();
		}	catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String args[]) {
		
		try {
			CepiList list = new CepiList("res/cepi-list.txt");
			list.processAll();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
