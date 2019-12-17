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
