package org.sj.punidos.crminer.sectorizer;

import static java.util.stream.Collectors.toList;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Vector;

public class ContentRegion implements Positionable {

	Rectangle2D region;
	Vector<Positionable> contents;
	
	public static double min(double a, double  b, double  c, double d) {
		return Math.min(Math.min(a, b), Math.min(c, d));
	}

	public static double max(double a, double  b, double c, double d) {
		return Math.max(Math.max(a, b), Math.max(c, d));
	}

	public static Rectangle2D rectangleFromPoints(Point2D p0, Point2D p1, Point2D p2, Point2D p3) {
		Point2D a = new Point2D.Double(
				min(p0.getX(),p1.getX(),p2.getX(),p3.getX()),
				min(p0.getY(),p1.getY(),p2.getY(),p3.getY()) );
		
		Point2D z = new Point2D.Double(
				max(p0.getX(),p1.getX(),p2.getX(),p3.getX()),
				max(p0.getY(),p1.getY(),p2.getY(),p3.getY()));
		return new Rectangle2D.Double(a.getX(),a.getY(),
				(z.getX()-a.getX()), (z.getY()-a.getY()));
		
	}
	
	public boolean isEmpty() 
	{
		return contents.isEmpty();
	}

	
	public ContentRegion(Rectangle2D r) {
		region = r;
		contents = new Vector<Positionable>();
	}
	
	public boolean contains(StringRegion cr) {
		return region.contains(cr.region);
	}
	
	public boolean contains(Point2D p) {
		return region.contains(p);
	}

	
	public boolean intersects(StringRegion cr) {
		return region.intersects(cr.region);
	}
	

	
	public boolean contains(Positionable gs) {
		return region.contains(gs.getBounds());
	}
	
	public void add(ContentRegion cr) {
		contents.addAll(cr.contents);
		region.add(cr.region);
	}

	
	public void add(Positionable gs) {
		contents.add(gs);
	}
	
	//public static String getText(GraphicsString )
	

	

	@Override
	public Point2D getPosition() {
		return new Point2D.Double(region.getX(),region.getY());	
	}
	
	public Rectangle2D getBounds() {
		return region;
	}
	

}
