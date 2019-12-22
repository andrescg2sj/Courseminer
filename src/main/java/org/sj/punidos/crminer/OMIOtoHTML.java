package org.sj.punidos.crminer;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.sj.punidos.crminer.omio.pdfboximpl.beta.OMIOPageExtractor;

public class OMIOtoHTML {
	
    public static void main(String[] args) throws IOException
    {
    	//TODO: see omio.pdfboximpl.beta.PDFText2HTML
    	
        File file = new File("res/OMIO-CARAB-2.pdf");
        //CustomGraphicsStreamEngine engine = new CustomGraphicsStreamEngine(page);
        OMIOPageExtractor.firstPageToHTML(file, "out/omio1.htm");
    }


}
