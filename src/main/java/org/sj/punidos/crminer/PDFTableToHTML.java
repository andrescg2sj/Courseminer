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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.sj.punidos.crminer.cepi.pdfboximpl.CustomGraphicsStreamEngine;

public class PDFTableToHTML implements CommonInfo
{
    static String DEFAULT_PATH = "res/CEPI-1-1.pdf";
    //static String DEFAULT_PATH = "res/test-1cell.pdf";
    
    public static void createDestDirectory() {
    	 File dir = new File(DST_PATH);
    	 if(!dir.isDirectory()) {
    		 dir.mkdirs();
    	 }
    }
    
    public static void test1() {
    	//Rectangle clipArea = new Rectangle(0,108,583, 720-108);

    }


    public static void main(String args[]) {
	String path = DEFAULT_PATH;
	//Rectangle clipArea =  new Rectangle(0,193,583, 544-193);
	Rectangle clipArea =  null;
	
	
	if(args.length > 0) {
	    path = args[0];
	    clipArea = null;
	} else {
	    System.out.println("No arguments. Using default path.");
	}

	try {
		createDestDirectory();

	    File file = new File(path);
	    PDDocument doc = PDDocument.load(file);
	    PDPage page = doc.getPage(0);
	    //CustomGraphicsStreamEngine engine = new CustomGraphicsStreamEngine(page);
	    // 108,0,720,583
	    CustomGraphicsStreamEngine engine =
		new CustomGraphicsStreamEngine(page, clipArea);
	    engine.run();
	    doc.close();
	    //System.out.println("------------------");
	    //System.out.println(engine.cluster.toHTML());
        
	    engine.writeHTML(DST_PATH + "doc"+Utils.getTimestamp()+".htm");
	} catch (Exception e) {
	    e.printStackTrace();
	}


	
    }
}
