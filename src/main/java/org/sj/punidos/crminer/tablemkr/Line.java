/*
 * Apache License
 *
 * Copyright (c) 2019 andrescg2sj
 *
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    https://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 


package org.sj.punidos.crminer.tablemkr;

import java.awt.geom.Point2D;
import java.util.Locale;
import java.awt.geom.Line2D;

public class Line
{
    Point2D a, b;

    private static Point2D[] sorted(Point2D c, Point2D d) {
	Point2D same[] = new Point2D[2];
	Point2D inverse[] = new Point2D[2];
	same[0] = c; same[1] = d;
	inverse[1] = c; inverse[0] = d;
	
	if(c.getY() == d.getY()) {
	    if(c.getX() <= d.getX()) return same;
	    return inverse;
	}
	if(c.getY() < d.getY()) return same;
	return inverse;
    }
	    
    /*
    private static Point2D min(Point2D c, Point2D d) {
	if(c.getY() == d.getY()) {
	    if(c.getX() <= d.getX()) return c;
	    return d;
	}
	if(c.getY() < d.getY()) return c;
	return d;
    }

        private static Point2D max(Point2D c, Point2D d) {
	if(c.getY() == d.getY()) {
	    if(c.getX() <= d.getX()) return c;
	    return d;
	}
	if(c.getY() < d.getY()) return c;
	return d;
    }
    */

    public Point2D getA() {
	return a;
    }
    
    public Point2D getB() {
	return b;
    }

    public Line2D getLine2D() {
	// TODO: Line should extend Line2D
	return new Line2D.Double(a, b);
    }

    public Line(double x1, double y1, double x2, double y2)
    {
	Point2D points[] = sorted(new Point2D.Double(x1,y1),
				  new Point2D.Double(x2,y2));
	this.a = points[0];
	this.b = points[1];
    }
    
    public Line(Point2D _a, Point2D _b) {
	Point2D points[] = sorted(_a,_b);
	this.a = points[0];
	this.b = points[1];
    }

    public double getWidth() {
	return Math.abs(b.getX() - a.getX());
    }

    public double  getHeight() {
	return Math.abs(b.getY() - a.getY());
    }


    public boolean isAxisParallel() {
	return (getWidth() == 0) || (getHeight() == 0);
    }
    
    public boolean isHoriz() {
	return getWidth() > getHeight();
    }

    /**
     * Calculate intersection between rects defined by this Line and b.
     *
     */
    public Point2D outIntersection(Line b)
    {
	if(!(isAxisParallel() && b.isAxisParallel())) {
	    //TODO: intersection of oblique rects
	    throw new UnsupportedOperationException("out intersection - oblique");
	}
	
	if(isHoriz()) {
	    if(b.isHoriz()) {
		//horizontal parallels
		//TODO: check if lines are coincident.
		return null;
	    } else {
		// orthogonal
		return new Point2D.Double(b.getA().getX(), getA().getY());
		
	    }
	} else {
	    if(b.isHoriz()) {
		// orthogonal
		return new Point2D.Double(getA().getX(), b.getA().getY());
	    } else {
		//parallels
		//TODO: check if lines are coincident.
		return null;
	    }
	}
    }

    public String toString() {
	return String.format(Locale.ROOT, "Line(%.2f,%.2f,%.2f,%.2f)",
			     a.getX(), a.getY(),
			     b.getX(), b.getY());
    }
}
