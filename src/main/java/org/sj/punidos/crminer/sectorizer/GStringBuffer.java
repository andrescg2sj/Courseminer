package org.sj.punidos.crminer.sectorizer;

import java.awt.Point;
import java.awt.Rectangle;

public class GStringBuffer {

	Rectangle region;
	StringBuilder text;
	
	public GStringBuffer() {
		reset();
	}
	
	public void add(String s) {
		text.append(s);
	}
	

	public void add(Point p) {
		if(p == null) return;
		if(region == null) {
			region = new Rectangle((int) p.getX(), (int) p.getY(), 0, 0);
		} else if(region.contains(p)) {
			return;
		} else {
			region.add(p);
			/*
			int x1 = (int) region.getX();
			int y1 = (int) region.getY();
			int x2 = (int) (region.getX()+region.getWidth());
			int y2 = (int) (region.getY()+region.getHeight());
			if(p.getX() < x1)
				x1 = (int) p.getX();
			if(p.getY() < y1)
				y1 = (int) p.getY();
			if(p.getX() > x2)
				x2 = (int) p.getX();
			if(p.getX() > x2)
				y2 = (int) p.getY();
			region = new Rectangle(x1, y1, x2-x1, y2-y1);
			*/	
 		}
	}
	
	public void add(Rectangle r) {
		if(r==null) return;
		if(region==null) {
			region = r;
			return;
		}
		region  = (Rectangle) region.createUnion(r);
	}
	
	public void reset() {
		region = null;
		text = new StringBuilder();
	}
	
	
	public String getText() {
		return text.toString();
		
	}
	
	public Rectangle getRegion() {
		return region;
	}
	
}
