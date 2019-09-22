
package org.sj.punidos.crminer.tablemkr;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class SkewedArea extends Area
{
    Line top, left, right, bottom;


    public boolean collision(Line l) {
	throw new UnsupportedOperationException("Area.collision");
    }

    public boolean collision(Line l, double tolerance) {
	throw new UnsupportedOperationException("Area.collision(l,t)");
    }

    public Area[] split(Line l) {
	throw new UnsupportedOperationException("Area.split");
	
    }

    
    public  boolean outOrBound(Line l)
    {
	throw new UnsupportedOperationException("Area.split");
	
    }

    public boolean contains(Point2D p) {
	throw new UnsupportedOperationException("Area.contains");
    }


    public boolean strictlyContains(Line l) {
	throw new UnsupportedOperationException("Area.strictlyContains");
    }

    public Rectangle2D getBounds() {
	throw new UnsupportedOperationException("Area.getBounds");
    }

	@Override
	public boolean contains(Rectangle r) {
		// TODO Auto-generated method stub
		//return false;
		throw new UnsupportedOperationException("SkewedArea.contains(Rectangle)");
	}
    
}
