package org.sj.punidos.crminer.tablemkr;

import java.awt.geom.Point2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;


public  class RectArea extends Area
{
    //Line top, left, right, bottom;
    Rectangle2D bounds;


    public RectArea(Rectangle2D r) {
	bounds = r;
    }

    public RectArea(Line top, Line left, Line right, Line bottom) {
	/*
	top = t;
	left = l;
	right = r;
	b = b;
	*/
	if(!(top.isAxisParallel() && left.isAxisParallel() &&
	     right.isAxisParallel() && bottom.isAxisParallel()))
	    throw new IllegalArgumentException("Lines are not parallel to axis");
	if(!(top.isHoriz() && bottom.isHoriz()) ||
	   left.isHoriz() || right.isHoriz())
	    throw new IllegalArgumentException("Lines don't have proper direction.");

	bounds = new Rectangle2D.Double(left.getA().getX(),
				 top.getA().getY(),
				 right.getA().getX(),
				 bottom.getA().getY());
    }

    public RectArea(double x, double y, double width, double height) {
	/*
	if(x2 < x1) {
	    double swap = x2;
	    x2 = x1;
	    x1 = swap;
	}
	if(y2 < y1) {
	    double swap = y2;
	    y2 = y1;
	    y1 = swap;
	    }*/

	//bounds = new Rectangle2D.Double(x1, y1, x2, y2);
	bounds = new Rectangle2D.Double(x, y, width, height);
    }

    

    public boolean collision(Line l) {
	return bounds.intersectsLine(l.getLine2D());
    }

    public boolean collision(Line l, double tolerance) {
	Rectangle2D safeRect = reduceRect(bounds, tolerance);
	return safeRect.intersectsLine(l.getLine2D());
    }

    public static Rectangle2D reduceRect(Rectangle2D r, double border) {
	return new Rectangle2D.Double(r.getX() + border, r.getY() + border,
				      r.getWidth() - border*2,
				      r.getHeight() - border*2);
    }


    public  Area[] split(Line l) {
	//Rectangle2D bounds = getBounds();
	
	if(!l.isAxisParallel())
	    throw new IllegalArgumentException("Line is not axis paralell");

	if(!collision(l))
	    return null;

	Area parts[] = new Area[2];
	System.out.println("Before split:" + bounds);
	if(l.isHoriz()) {
	    System.out.println("Horizontal split");
	    //if(l.getA().getY() < bounds.get
	    double height1 = l.getA().getY() - bounds.getY();
	    double height2 = bounds.getHeight() - height1;
	       
	    parts[0] = new RectArea(bounds.getX(), bounds.getY(),
				    bounds.getWidth(), height1);
	    parts[1] = new RectArea(bounds.getX(), bounds.getY()+height1,
				    bounds.getWidth(), height2);
	    
	} else {
	    System.out.println("Vertical split");
	    double width1 = l.getA().getX() - bounds.getX();
	    double width2 = bounds.getWidth() - width1;
	       
	    parts[0] = new RectArea(bounds.getX(), bounds.getY(),
				    width1, bounds.getHeight());	
	    parts[1] = new RectArea(bounds.getX()+width1, bounds.getY(),
				    width2, bounds.getHeight());
    
	}
	return parts;
    }

    public boolean contains(Point2D p) {
	return bounds.contains(p);
    }

    public boolean strictlyContains(Point2D p) {
	return bounds.contains(p) && (p.getX() > bounds.getX()) &&
	    (p.getY() > bounds.getY());
    }

    public boolean outOrBound(Line l) {
	return ((l.getA().getY() <= bounds.getY()) &&
		(l.getB().getY() <= bounds.getY())) ||
	    ((l.getA().getY() >= bounds.getMaxY()) &&
		(l.getB().getY() >= bounds.getMaxY())) ||
	    ((l.getA().getX() <= bounds.getX()) &&
		(l.getB().getX() <= bounds.getX())) ||
	    ((l.getA().getX() >= bounds.getMaxX()) &&
	     (l.getB().getX() >= bounds.getMaxX())) ||
	    !bounds.intersectsLine(l.getLine2D());
	    
    }

    public  boolean strictlyContains(Line l)
    {
	return strictlyContains(l.getA()) && strictlyContains(l.getB());
    }



    public Rectangle2D getBounds() {
	return bounds;
    }

    public Area getMaximumArea(Line lines[])
    {
	return new RectArea(getMaximumRect(lines));
    }

	@Override
	public boolean contains(Rectangle r) {
		System.out.println("RectArea.contains");
		System.out.println("   this: "+ bounds);
		System.out.println("      r: "+ r);
		boolean test = bounds.contains(r);
		System.out.println("      result: "+ test);
		return test;
	}

    
}