package org.sj.punidos.crminer.sectorizer;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

public class HorizBandTransform extends BoundTransform {
	
	
	public HorizBandTransform(Rectangle2D r) {
		super(r);
	}

	@Override
	public Rectangle2D transform(Rectangle2D r) {
		return mixXY(bounds, r);
	}


}
