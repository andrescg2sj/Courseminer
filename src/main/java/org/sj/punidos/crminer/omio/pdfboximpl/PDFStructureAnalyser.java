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
 

package org.sj.punidos.crminer.omio.pdfboximpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;

public class PDFStructureAnalyser {

	/*
	 * 
	 * 
	 * Ref:
	 * https://stackoverflow.com/questions/3549541/best-tool-for-inspecting-pdf-files 
	 */
	
	public static void show(File f) throws InvalidPasswordException, IOException {
		// load the document
		System.out.println("Reading document: " + f.getName());
		PDDocument doc = null;                                                                                                                                                                                                          
		doc = PDDocument.load(f);

		// look at all the document information
		PDDocumentInformation info = doc.getDocumentInformation();
		COSDictionary dict = info.getCOSObject();
		Set l = dict.keySet();
		for (Object o : l) {
		    //System.out.println(o.toString() + " " + dict.getString(o));
		    System.out.println(o.toString());
		}

		// look at the document catalog
		PDDocumentCatalog cat = doc.getDocumentCatalog();
		System.out.println("Catalog:" + cat);

		PDPageTree tree = cat.getPages();
		//List<PDPage> lp = tree.
		System.out.println("# Pages: " + tree.getCount());
		PDPage page = tree.get(4);
		System.out.println("Page: " + page);
		System.out.println("\tCropBox: " + page.getCropBox());
		System.out.println("\tMediaBox: " + page.getMediaBox());
		System.out.println("\tResources: " + page.getResources());
		
		System.out.println("\tRotation: " + page.getRotation());
		System.out.println("\tArtBox: " + page.getArtBox());
		System.out.println("\tBleedBox: " + page.getBleedBox());
		System.out.println("\tContents: " + page.getContents());
		System.out.println("\tTrimBox: " + page.getTrimBox());
		List<PDAnnotation> la = page.getAnnotations();
		System.out.println("\t# Annotations: " + la.size());
		
	}
	
	
	public static void showContents(InputStream contents) {
		
	}
	
	public static void main(String args[]) throws Exception {
		File f = new File("res/CARAB.pdf");
		show(f);
		
	}
}
