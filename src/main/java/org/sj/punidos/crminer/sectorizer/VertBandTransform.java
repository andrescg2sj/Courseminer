package org.sj.punidos.crminer.sectorizer;

import java.awt.geom.Rectangle2D;

public class VertBandTransform extends BoundTransform {

	public VertBandTransform(Rectangle2D r) {
		super(r);
	}
	
	@Override
	public Rectangle2D transform(Rectangle2D r) {
		return mixXY(r, bounds);
	}

}
