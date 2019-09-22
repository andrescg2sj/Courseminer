package org.sj.punidos.crminer.omio.pdfboximpl.beta;

import java.awt.Point;
import java.awt.Rectangle;

import org.apache.pdfbox.pdmodel.font.PDFont;
//import org.sj.punidos.crminer.sectorizer.Positionable;
import org.sj.punidos.crminer.sectorizer.GraphicString;

public class PDFString extends GraphicString {
	
	PDFont font;
	
	public PDFString(String t, Rectangle b, PDFont f) {
		super(t, b);
		font = f;
	}
}
