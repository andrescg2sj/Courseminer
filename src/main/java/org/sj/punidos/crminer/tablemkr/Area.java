package org.sj.punidos.crminer.tablemkr;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import org.sj.punidos.crminer.sectorizer.GraphicString;


public abstract class Area
{
    //Line top, left, right, bottom;
	Vector<GraphicString> content;
	
	Area() {
		content = new Vector<GraphicString>();
	}


    public abstract Area[] split(Line l);

    public abstract boolean contains(Point2D p);

    
    public abstract boolean contains(Rectangle r);

    public abstract boolean outOrBound(Line l);


    
    public abstract boolean strictlyContains(Line l);

    public  abstract Rectangle2D getBounds();

    /**
     * Decide whether l collides with this area
     * collisionThreshold is used in order not to detect a collision
     * with a line very close to the border.
     */
    public abstract boolean collision(Line l);

    /**
     * @param tolerance width of a border of this Area in which 
     * collsion is not detected.
     */
    public abstract boolean collision(Line l, double tolerance);

    
    public static Rectangle2D getMaximumRect(Line lines[]) {
	Point2D start = lines[0].getA();
	Rectangle2D r = new Rectangle2D.Double(start.getX(), start.getY(),
					       0,0);
	
	for(int i=0; i<lines.length; i++) {
	    r.add(lines[i].getA());	
	    r.add(lines[i].getB());
	    for(int j=i+1; j<lines.length; j++) {
		Point2D z = lines[i].outIntersection(lines[j]);
		if(z != null)
		    r.add(z);
	    }
	}
	return r;
    }
    
    public void addContent(GraphicString gstr)
    {
    	if(this.contains(gstr.getBounds()))
    		content.add(gstr);
    }



    public String toString() {
	return getBounds().toString();
    }
    
}
