package org.sj.punidos.crminer;

import java.awt.Rectangle;
import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.sj.punidos.crminer.cepi.pdfboximpl.CustomGraphicsStreamEngine;

public class PDFTableToHTML
{
    static String DEFAULT_PATH = "res/CEPI-1-1.pdf";

    public static void main(String args[]) {
	String path = DEFAULT_PATH;
	
	if(args.length > 0) {
	    path = args[0];
	} else {
	    System.out.println("No arguments. Using default path.");
	}

	try {
	    File file = new File(path);
	    PDDocument doc = PDDocument.load(file);
	    PDPage page = doc.getPage(0);
	    //CustomGraphicsStreamEngine engine = new CustomGraphicsStreamEngine(page);
	    // 108,0,720,583
	    CustomGraphicsStreamEngine engine =
		new CustomGraphicsStreamEngine(page,								       new Rectangle(0,108,583, 720-108));
	    engine.run();
	    doc.close();
	    //System.out.println("------------------");
	    //System.out.println(engine.cluster.toHTML());
        
	    engine.writeHTML("out/doc1.htm");
	} catch (Exception e) {
	    e.printStackTrace();
	}


	
    }
}
