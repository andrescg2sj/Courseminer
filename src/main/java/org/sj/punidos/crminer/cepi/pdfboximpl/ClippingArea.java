package org.sj.punidos.crminer.cepi.pdfboximpl;

import java.awt.Rectangle;

public class ClippingArea {
	
	
	/**
	 * 
	 * null area means infinite valid clipping area.
	 */
	Rectangle area = null;
	
	public ClippingArea() {
	}

	public ClippingArea(Rectangle r) {
		area = r;
	}
	
	public boolean clip(Rectangle r) {
		if(area == null)
			return true;
		return area.contains(r);
 	}

}
