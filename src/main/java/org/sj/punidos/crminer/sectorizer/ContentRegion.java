package org.sj.punidos.crminer.sectorizer;


import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class ContentRegion<E extends Positionable> implements Positionable {

	Rectangle2D region;
	Vector<E> contents;
	
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
	
	public ContentRegion(E obj, double border) {
		region = expandRect(obj.getBounds(), border);
		contents = new Vector<E>();
		contents.add(obj);
	}
	
	public ContentRegion(ContentRegion<E> cr) {
		region = cr.region;
		contents = cr.contents;
	}

	
	public ContentRegion(Rectangle2D r) {
		region = r;
		contents = new Vector<E>();
	}
	
	public boolean contains(ContentRegion<E> r) {
		return region.contains(r.region);
	}
	
	public boolean contains(Point2D p) {
		return region.contains(p);
	}

	
	public boolean intersects(ContentRegion cr) {
		return region.intersects(cr.region);
	}
	

	
	public boolean contains(E gs) {
		return region.contains(gs.getBounds());
	}
	
	public int countElements()
	{
		return contents.size();
	}
	
	public void add(ContentRegion cr) {
		contents.addAll(cr.contents);
		region.add(cr.region);
	}

	
	public void add(E gs) {
		contents.add(gs);
	}
	
	//public static String getText(GraphicsString )
	

	public static Rectangle2D expandRect(Rectangle2D rect, double border) {
		return new Rectangle2D.Double(rect.getX() - border, rect.getY() - border,
				rect.getWidth() + border*2,
				rect.getHeight() + border*2);

	}
	

	@Override
	public Point2D getPosition() {
		return new Point2D.Double(region.getX(),region.getY());	
	}
	
	public Rectangle2D getBounds() {
		return region;
	}
	
	public Iterator<E> contentIterator()
	{
		return contents.iterator();
	}
	

}
