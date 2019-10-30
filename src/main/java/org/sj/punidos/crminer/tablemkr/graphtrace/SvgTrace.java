package org.sj.punidos.crminer.tablemkr.graphtrace;

import java.awt.geom.Rectangle2D;
import java.awt.Point;
import java.util.Date;
import java.util.Vector;
import java.util.Iterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import org.sj.punidos.crminer.tablemkr.Area;
import org.sj.punidos.crminer.sectorizer.GraphicString;
import java.util.Locale;



public class SvgTrace {

    //String path;
    //FileWriter fwriter;
    float drawAreaFactor = 1.1f;
    public static final String NEW_LINE = "\n";

    public SvgTrace() {
	//this.path = path;

    }

    public String generateLogFilename() {
	DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
	Date date = new Date();
	String timestamp = dateFormat.format(date); 
	return "out/log" + timestamp +".svg";
    }

    public void exportAreasAndGStrings(Vector<Area> areas,
				      Vector<GraphicString> gstrs)
    {
	exportAreasAndGStrings(generateLogFilename(), areas, gstrs);
	
    }

    public void exportAreas(Vector<Area> areas) {
	exportAreas(generateLogFilename(), areas);
    }

    public void exportAreasAndGStrings(String filename,
				       Vector<Area> areas,
				       Vector<GraphicString> gstrs) {
	//TODO: calculate content bounds
	String content = "";
	Rectangle2D bounds = areas.get(0).getBounds();
    	for(Area a: areas) {
	    content += areaToSvg(a, bounds);
    	}
	String s = String.format("<svg width=\"%.2f\" height=\"%.2f\">",
				 (float) bounds.getMaxX()*drawAreaFactor,
				 (float) bounds.getMaxY()*drawAreaFactor);

	if(gstrs != null) {
	    System.out.println("Trace: gstrings: " + gstrs.size());
	    for(GraphicString gs: gstrs) {
		content += gstrToSvg(gs);
		System.out.println("  gstr: " + gs.getText());
	    }
	}

    	s = s+content+"</svg>";
	try {
	    FileWriter fwriter = new FileWriter(new File(filename));
	    fwriter.write(s);
	    fwriter.close();
	} catch(IOException ioe) {
	    ioe.printStackTrace();
	}
    }

    public void exportAreas(String filename, Vector<Area> areas) {
	exportAreasAndGStrings(filename, areas, null);
    }

    
    public String gstrToSvg(GraphicString gstr) {
	Point p = gstr.getPosition();
	return  String.format(Locale.ROOT, "<text x=\"%.2f\" y=\"%.2f\" fill=\"red\">%s</text>",
			      p.getX(), p.getY(), gstr.getText());
 
    }

    public String areaToSvg(Area area, Rectangle2D bounds) {
	
	Rectangle2D rect = area.getBounds();
	bounds.add(rect);
	String xml = String.format(Locale.ROOT, "<rect x=\"%.2f\" y=\"%.2f\" width=\"%.2f\" height=\"%.2f\" stroke=\"green\" stroke-width=\"3\" />"+NEW_LINE, 
    			rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
	Iterator<GraphicString> cnt = area.getContents();
	while(cnt.hasNext()) {
	    GraphicString gstr = cnt.next();
	    xml += gstrToSvg(gstr)+NEW_LINE;
	    
	}
	 return xml;
    }

    
} 
