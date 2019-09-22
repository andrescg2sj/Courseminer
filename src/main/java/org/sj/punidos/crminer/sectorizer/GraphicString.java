package org.sj.punidos.crminer.sectorizer;

import java.awt.Point;
import java.awt.Rectangle;

//import org.apache.pdfbox.pdmodel.font.PDFont;

public class GraphicString {

	String text;
	Rectangle bounds;
	
	public GraphicString(String t, Rectangle b) {
		text = t;
		bounds = b;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public String getText() {
		return text;
	}

	public Point getPosition() {
		if(bounds == null) {
			return null;
		}
		return bounds.getLocation();
	}
}
