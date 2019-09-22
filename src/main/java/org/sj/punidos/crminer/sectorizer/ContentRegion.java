package org.sj.punidos.crminer.sectorizer;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Vector;


public class ContentRegion implements Positionable {

	Rectangle region;
	Vector<GraphicString> strings;
	
	public static double min(double a, double  b, double  c, double d) {
		return Math.min(Math.min(a, b), Math.min(c, d));
	}

	public static double max(double a, double  b, double c, double d) {
		return Math.max(Math.max(a, b), Math.max(c, d));
	}

	public static Rectangle rectangleFromPoints(Point2D p0, Point2D p1, Point2D p2, Point2D p3) {
		Point2D a = new Point2D.Double(
				min(p0.getX(),p1.getX(),p2.getX(),p3.getX()),
				min(p0.getY(),p1.getY(),p2.getY(),p3.getY()) );
		
		Point2D z = new Point2D.Double(
				max(p0.getX(),p1.getX(),p2.getX(),p3.getX()),
				max(p0.getY(),p1.getY(),p2.getY(),p3.getY()));
		return new Rectangle((int) a.getX(),(int) a.getY(),
				(int) (z.getX()-a.getX()), (int) (z.getY()-a.getY()));
		
	}

	
	public ContentRegion(Rectangle r) {
		region = r;
		strings = new Vector<GraphicString>();
	}
	
	public boolean contains(ContentRegion cr) {
		return region.contains(cr.region);
	}

	
	public boolean contains(GraphicString gs) {
		return region.contains(gs.getBounds());
	}
	
	public void add(GraphicString gs) {
		strings.add(gs);
	}
	
	Vector<GraphicString> getStrings() {
		return strings;
	}

	@Override
	public Point getPosition() {
		return region.getLocation();
	}
	
	
}
