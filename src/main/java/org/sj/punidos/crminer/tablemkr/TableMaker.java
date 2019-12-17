
package org.sj.punidos.crminer.tablemkr;


import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Vector;

import org.sj.punidos.crminer.sectorizer.GraphicString;
import org.sj.punidos.crminer.tablemkr.graphtrace.SvgTrace;

public abstract class TableMaker
{
    float collisionThreshold = 2;
    
    Vector<Line> lines;
    Vector<GraphicString> gstrings;

    /* debug */
    SvgTrace tracer = new SvgTrace();

    public TableMaker() {
    	lines = new Vector<Line>();
    	gstrings = new Vector<GraphicString>();
       
    }

    public abstract Table makeTable();

}
