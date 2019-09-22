package org.sj.punidos.crminer.sectorizer;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Vector;

//import org.sj.punidos.crminer.omio.pdfboximpl.beta.PDFString;

public class RegionCluster {
	
	Vector<ContentRegion> regions;
	Vector<GraphicString> remaining; 
	
	public RegionCluster() {
		regions = new Vector<ContentRegion>();
		remaining = new Vector<GraphicString>();
	}
	
	public void push(GraphicString gs) {
		ContentRegion selected = null;
		for(ContentRegion r: regions) {
			if(r.contains(gs)) {
				if(selected == null || selected.contains(r)) {
					selected = r;
				}
			}
		}
		if(selected != null) {
			selected.add(gs);
		} else {
			remaining.add(gs);
		}
	}
	
	
	public void pushRegion(Rectangle r) {
		ContentRegion cr = new ContentRegion(r);
		pushRegion(cr);
	}
	
	public void pushRegion(ContentRegion cr) {
		// TODO: optimize
		for(GraphicString s: remaining) {
			if(cr.contains(s)) {
				cr.add(s);
				remaining.remove(s);
			}
		}
		regions.add(cr);
	}
	
	public String regionToHTML(ContentRegion cr) {
		StringBuilder s = new StringBuilder();
		s.append("<div style=\"border-style: solid;\">\n");
		for(GraphicString g: cr.getStrings()) {
			s.append(g.getText()+"<br/>\n");
		}
		
		s.append("</div>\n");
		
		return s.toString();
	}
	
	public String toHTML() {
		StringBuilder s = new StringBuilder();
		for(ContentRegion r: regions) {
			s.append(regionToHTML(r));
		}
		return s.toString();
	}
	
	


}
