/*
 * Apache License
 *
 * Copyright (c) 2019 andrescg2sj
 *
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    https://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.sj.punidos.crminer;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.sj.punidos.crminer.cepi.pdfboximpl.CustomGraphicsStreamEngine;



public class PDFTableToHTML implements CommonInfo
{
    static String DEFAULT_PATH = "res/CEPI-1-1.pdf";
    //static String DEFAULT_PATH = "res/test-1cell.pdf";
    
	public static final String HTML_HEAD = 
			"<html><head>"
			+ "<style>table {\r\n" + 
			"  border-collapse: collapse;\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"table, th, td {\r\n" + 
			"  border: 1px solid black;\r\n" + 
			"}</style>"
			+ "</head>";

    File file; 
    PDDocument doc;
    
    public PDFTableToHTML(String path) {
    	file = new File(path);
    }
    
    public static void createDestDirectory() {
    	 File dir = new File(DST_PATH);
    	 if(!dir.isDirectory()) {
    		 dir.mkdirs();
    	 }
    }
    
    public static void test1() {
    	//Rectangle clipArea = new Rectangle(0,108,583, 720-108);

    }
    
    /**
     * Parse
     */
    public static Rectangle parseDimensions(String spec) {
    	String parts[] = spec.split(",");
    	if(parts.length != 4) {
    		throw new IllegalArgumentException("Expected: 4 numbers separated by comma. Given: "+parts.length);
    	}
    	int x = Integer.parseInt(parts[0]);
    	int y = Integer.parseInt(parts[1]);
    	int width = Integer.parseInt(parts[2]);
    	int height = Integer.parseInt(parts[3]);
    	return new Rectangle(x,y,width,height);
    }
    
    public void writeHTMLHead(OutputStreamWriter out) throws IOException
    {
    	out.write(HTML_HEAD + "<body>");
    }

    public void writeHTMLTail(OutputStreamWriter out) throws IOException
    {
    	out.write("</body></html>");
    }

    
    public void run(Rectangle clipRect)
    {
    	String filename = DST_PATH + "doc"+Utils.getTimestamp()+".htm";
    	
    	try {
    		createDestDirectory();

    		File f = new File(filename);
        	FileOutputStream fos = new FileOutputStream(f);
        	OutputStreamWriter out = new OutputStreamWriter(fos);

        	writeHTMLHead(out);

    	    doc = PDDocument.load(file);
    	    for(int i=0; i< doc.getNumberOfPages(); i++) {
    	    	PDPage page = doc.getPage(i);
    	    	CustomGraphicsStreamEngine engine =
    	    			new CustomGraphicsStreamEngine(page, clipRect);
    	    	engine.run();
        	    engine.writeHTMLTables(out);
    	    }
    	    doc.close();
    	    
    	    writeHTMLTail(out);
        	out.close();

    	    	
    	    //System.out.println("------------------");
    	    //System.out.println(engine.cluster.toHTML());
            
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}

    }


    public static void main(String args[]) {
		String path = DEFAULT_PATH;
		Rectangle clipRect =  null;	
		
		Options options = new Options();

        Option output = new Option("o", "output", true, "output file");
        output.setRequired(false);
        options.addOption(output);

        Option clip = new Option("c", "clip", true, "format: x,y,width,height");
        output.setRequired(false);
        options.addOption(clip);

        
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
            
            String remaining[] = cmd.getArgs();

            if(cmd.hasOption("c")) {
		    	clipRect = parseDimensions(cmd.getOptionValue("c"));
		    	System.out.println("Clipping rectangle:"+ clipRect.toString());
		    } else {
		    	System.out.println("NO CLIP");
		    }
    		
    		if(remaining.length > 0) {
    		    path = args[0];
    		} else {
    		    System.out.println("No filename given. Using default path.");
    		}
    		
    		System.out.println("Reading: "+path);
    		PDFTableToHTML proc = new PDFTableToHTML(path);
    		proc.run(clipRect);
    	

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            
            System.out.println("PDFTableToHTML [OPTIONS] [PDF-filename-in]");
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }
		

	
    }
}
