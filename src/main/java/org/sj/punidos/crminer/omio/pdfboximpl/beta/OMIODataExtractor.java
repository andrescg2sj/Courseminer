package org.sj.punidos.crminer.omio.pdfboximpl.beta;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

public class OMIODataExtractor {
	
    PDDocument doc;

	public OMIODataExtractor(File f) throws InvalidPasswordException, IOException 
	{
		doc = PDDocument.load(f);
	}
	
	public void run() throws IOException {
		for(int i=0; i<doc.getNumberOfPages(); i++) {
			PDPage page = doc.getPage(0);
			OMIOPageExtractor engine = new OMIOPageExtractor(page);
			engine.run();
			//TODO: Do something ...
		}
        doc.close();
	}
	
	//public Vector<ContentRegion> getBlocks() {

}
