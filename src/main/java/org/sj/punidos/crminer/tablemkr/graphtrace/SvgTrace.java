package org.sj.punidos.crminer.tablemkr.graphtrace;

import java.awt.geom.Rectangle2D;
import java.util.Date;
import java.util.Vector;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import org.sj.punidos.crminer.tablemkr.Area;
import java.util.Locale;



public class SvgTrace {

    //String path;
    //FileWriter fwriter;
    float drawAreaFactor = 1.1f;

    public SvgTrace() {
	//this.path = path;

    }

    public String generateLogFilename() {
	DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
	Date date = new Date();
	String timestamp = dateFormat.format(date); 
	return "out/log" + timestamp +".svg";
    }

    public void exportAreas(Vector<Area> areas) {
	exportAreas(generateLogFilename(), areas);
    }

    public void exportAreas(String filename, Vector<Area> areas) {
	//TODO: calculate content bounds
	String content = "";
	Rectangle2D bounds = areas.get(0).getBounds();
    	for(Area a: areas) {
	    content += areaToSvg(a, bounds);
    	}
	String s = String.format("<svg width=\"%.2f\" height=\"%.2f\">",
				 (float) bounds.getMaxX()*drawAreaFactor,
				 (float) bounds.getMaxY()*drawAreaFactor);

    	s = s+content+"</svg>";
	try {
	    FileWriter fwriter = new FileWriter(new File(filename));
	    fwriter.write(s);
	    fwriter.close();
	} catch(IOException ioe) {
	    ioe.printStackTrace();
	}
    }
      

    public String areaToSvg(Area area, Rectangle2D bounds) {
	
	Rectangle2D rect = area.getBounds();
	bounds.add(rect);
    	return String.format(Locale.ROOT, "<rect x=\"%.2f\" y=\"%.2f\" width=\"%.2f\" height=\"%.2f\" stroke=\"green\" stroke-width=\"3\" />\n", 
    			rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    
} 
