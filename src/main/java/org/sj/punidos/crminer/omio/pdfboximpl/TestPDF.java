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

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.tools.PDFText2HTML;

/*
 * https://www.tutorialspoint.com/pdfbox/pdfbox_reading_text.htm
 * See also:
 * 
 * Code examples:
 * https://www.programcreek.com/java-api-examples/?api=org.apache.pdfbox.util.PDFTextStripper
 * 
 * *
 * PDFTextStripper
 * https://github.com/apache/pdfbox/blob/trunk/pdfbox/src/main/java/org/apache/pdfbox/text/PDFTextStripper.java
 * 
 * 
 * PDFTextToHTML source
 * https://github.com/apache/pdfbox/blob/trunk/tools/src/main/java/org/apache/pdfbox/tools/PDFText2HTML.java
 */

public class TestPDF {
	
	public static void showText(String filename) throws IOException {

	      //Loading an existing document
	      File file = new File(filename);
	      PDDocument document = PDDocument.load(file);

	      //Instantiate PDFTextStripper class
	      PDFTextStripper pdfStripper = new PDFTextStripper();

	      //Retrieving text from PDF document
	      String text = pdfStripper.getText(document);
	      System.out.println(text);

	      //Closing the document
	      document.close();

		
	}
	
	public static void pdfToHtml(String filename) throws IOException {
	      //Loading an existing document
	      File file = new File(filename);
	      PDDocument document = PDDocument.load(file);

	      //Instantiate PDFTextStripper class
	      PDFText2HTML reader = new PDFText2HTML();

	      /*
	      //Retrieving text from PDF document
	       * */
	      String text = reader.getText(document);
	      System.out.println(text);
	       
	      //Closing the document
	      document.close();

		
	}
	
	public static void main(String args[]) throws IOException {
		  //showText("res/CARAB.pdf");
		 pdfToHtml("res/OMIO-CARAB-2.pdf");
		 //pdfToHtml("res/CEPI-AlcSSR-1.pdf");
		  
	}
}
