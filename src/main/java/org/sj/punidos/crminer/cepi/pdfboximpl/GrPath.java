package org.sj.punidos.crminer.cepi.pdfboximpl;

import java.awt.geom.Point2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import java.util.Vector;
//import java.util.Iterator;

public class GrPath {


    Point2D position;
    Vector<Line2D> elements;

    public GrPath() {
	elements = new Vector<Line2D>();

    }

    public void reset() {
	elements.removeAllElements();
    }

    public void moveTo(Point2D p) {
	position = p;
    }


    public void lineTo(Point2D q) {
	if(position == null) {
	    //TODO: Warning
	} else {
	    Line2D line = new Line2D.Double(position, q);
	    elements.add(line);
	}
	moveTo(q);

    }

    public Iterable<Line2D> getIterable() {
	return elements;
    }
	
}

