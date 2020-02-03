package org.sj.punidos.crminer.cepi.pdfboximpl;

import java.awt.Rectangle;
import java.util.LinkedList;

import org.sj.punidos.crminer.tablemkr.TableMaker;
import org.sj.punidos.crminer.sectorizer.StringRegion;
import org.sj.punidos.crminer.sectorizer.RegionCluster;
import org.sj.punidos.crminer.tablemkr.Line;

class LineManager {
	
	public static final int MIN_THICKNESS = 1;
	
	LinkedList<Line> lines = new LinkedList<Line>();
	
	public LineManager()
	{
		
	}
	
	public void add(Line l) {
		lines.add(l);
	}
	
	public void feedTableMaker(TableMaker tmaker) {
    	for(Line l: lines) {
    		tmaker.add(l);
    	}
	}
	
	public Rectangle rectFromLine(Line l) {
		//TODO: Rectangle2D?
		Rectangle r = new Rectangle();
		r.setLocation((int) l.getA().getX(), (int) l.getA().getY());
		r.add((int) l.getB().getX(), (int) l.getB().getY());;
		r.grow(MIN_THICKNESS, MIN_THICKNESS); 
		return r;
	}
	
	public void organize()
	{
		RegionCluster rc = new RegionCluster();
    	for(Line l: lines) {
    		rc.pushRegion(rectFromLine(l));
    	}
    	rc.meltRegions();
    	
    	for(int i=0;i<rc.getNumberOfRegions(); i++) {
    		StringRegion r = rc.getRegion(i);
    		
        	for(Line l: lines) {
        		if(r.contains(l.getA()) || r.contains(l.getB())) {
        			
        		}
        	}
    	}
    	
	
	}
	

}
