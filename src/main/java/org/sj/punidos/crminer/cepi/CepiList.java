package org.sj.punidos.crminer.cepi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.sj.punidos.crminer.CommonInfo;
import org.sj.punidos.crminer.courses.Course;
import org.sj.punidos.crminer.courses.CourseCsvWriter;
import org.sj.punidos.crminer.courses.CourseXlsxWriter;
import org.sj.tools.graphics.tablemkr.frompdf.PDFPageTableExtractor;

public class CepiList implements CommonInfo {
	
	List<CepiWeb> cepis = new LinkedList<CepiWeb>();
    public static final String BASE_DIR = "out/cepi-demo/";
	
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

        public static void createDirectory(File dir) {
	    //TODO: avoid redundancy of this kind of function.
	    //File dir = new File(path);
	    if(!dir.isDirectory()) {
		dir.mkdirs();
	    }
	}

	
	public void exportAllToHTML() {
		exportCepisToHTML(cepis);
	}
	
	public void exportCepisToHTML(List<CepiWeb> selected) {
	    File base = new File(BASE_DIR);
	    createDirectory(base);

	    StringBuilder report = new StringBuilder();
		report.append("<table>");
		for(CepiWeb cepi : selected) {
			System.out.println("Cepi: "+cepi.link);
			cepi.downloadAndExportToHTML(base);
			CepiWebReport rep = new CepiWebReport(cepi);
			report.append(rep.getHtmlRow(cepi));
		}
		report.append("</table>");
		writeFullReport(report.toString(), base);
		
	}
	
	public void writeFullReport(String tablehtml, File base) {
	    
		try {
		    File dstFile = new File(base, "report.html");
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
	
	public void allToCsv(String filename) throws IOException {
		cepisToCsv(cepis, filename);
	}
	
	public void cepisToCsv(List<CepiWeb> selected, String filename) throws IOException {
		File file = new File(filename);
		FileWriter fw = new FileWriter(file);
		CourseCsvWriter cw = new CourseCsvWriter(fw);
		for(CepiWeb cepi : selected) {
			System.out.println("Processing: "+cepi.getName());
			List<Course> courses = cepi.getCourses();
			cw.write(courses);
		}
		cw.close();
		
	}
	
	public void cepisToXlsx(List<CepiWeb> selected, String filename) throws IOException {
		CourseXlsxWriter xw = new CourseXlsxWriter(filename);
		for(CepiWeb cepi : selected) {
			System.out.println("Processing: "+cepi.getName());
			List<Course> courses = cepi.getCourses();
			xw.write(courses);
		}
		xw.writeAndClose();
	}

	
	
	public CepiWeb getCepi(String name) {
		for (CepiWeb c: cepis) {
			if(c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	public List<CepiWeb> selectCepis(String names[]) {
		List<CepiWeb> selected = new LinkedList<CepiWeb>(); 
        if(names.length > 0) {
        	selected = new LinkedList<CepiWeb>();
        	for(String name: names) {
        		CepiWeb cepi = getCepi(name);
        		if(cepi == null) {
        			System.out.println("CEPI '"+name+"' not found!");
        		} else {
        			System.out.println("Loading CEPI '"+name+"'");
        			selected.add(cepi);
        		}
        	}
        } else {
			System.out.println("Loading all CEPIs");
        	selected = cepis;
        }
        return selected;

	}
	
	static {
	      System.setProperty("java.util.logging.config.file",
	              "./logging.properties");
	      //must initialize loggers after setting above property
	      //LOGGER = Logger.getLogger(MyClass.class.getName());
	  }
	
	public void export(List<CepiWeb> cepis, String format, String filename) throws IOException {
		if("csv".equals(format)) {
			cepisToCsv(cepis, filename);
		} else {
			cepisToXlsx(cepis, filename);
			
		}
	}
	
	public static void main(String args[]) {
		Options options = new Options();

		Option optOutput = new Option("o", "output_filename", true, "output CSV file");
        optOutput.setRequired(false);
        options.addOption(optOutput);

		Option optFormat = new Option("f", "output_format", true, "output format");
        optFormat.setRequired(false);
        options.addOption(optFormat);


        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

		
		try {
			CepiList list = new CepiList("res/cepi-list.txt");

			cmd = parser.parse(options, args);

            String remaining[] = cmd.getArgs();
            List<CepiWeb> selectedCepis = list.selectCepis(remaining);
			
			if(cmd.hasOption("o")) {
				String filename = cmd.getOptionValue("o");
				String format = "xlsx";
				if(cmd.hasOption("f")) {
					format = cmd.getOptionValue("f");
				}
				list.export(selectedCepis, format, filename);
			} else {
				list.exportCepisToHTML(selectedCepis);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
