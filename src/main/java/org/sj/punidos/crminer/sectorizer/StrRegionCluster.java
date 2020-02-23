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
 

package org.sj.punidos.crminer.sectorizer;

import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import org.sj.punidos.crminer.CommonInfo;

//import org.sj.punidos.crminer.omio.pdfboximpl.beta.PDFString;

public class StrRegionCluster extends PosRegionCluster<GraphicString> implements CommonInfo {
	
	
	public StrRegionCluster() {
		super();
	}
	
	

	

	
	public List<String> allStrings() {
		List<String> all = new LinkedList<String>();
		//FIXME
		for(ContentRegion<GraphicString> r: regions) {
			StringRegion sr = new StringRegion(r);
			all.addAll(sr.getStrings());
		}
		return all;
	}
	
	public String regionToHTML(StringRegion cr) {
		StringBuilder s = new StringBuilder();
		s.append("<div style=\"border-style: solid;\">\n");
		for(GraphicString g: cr.getGraphicStrings()) {
			s.append(g.getText()+"<br/>\n");
		}
		
		s.append("</div>\n");
		
		return s.toString();
	}
	
	public String toHTML() {
		filterOutEmptyRegions();
		sortRegions();
		StringBuilder s = new StringBuilder();
		System.out.println("Regions: "+ regions.size());
		for(ContentRegion<GraphicString> r: regions) {
			StringRegion sr = new StringRegion(r);
			s.append(regionToHTML(sr));
		}

		System.out.println("Remainin: "+ remaining.size());
		for(GraphicString obj: remaining) {
			if(obj instanceof GraphicString) {
				GraphicString gs = (GraphicString) obj;
				s.append("<p>"+gs.getText()+"</p>"+ NEW_LINE);
			}
		}
		return s.toString();
	}
	
	

}
