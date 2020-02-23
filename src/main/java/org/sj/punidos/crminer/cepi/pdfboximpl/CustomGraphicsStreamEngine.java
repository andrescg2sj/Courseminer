/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * This file is a modified version from:
 *
 * https://apache.googlesource.com/pdfbox/+/trunk/examples/src/main/java/org/apache/pdfbox/examples/rendering
 *
 */


package org.sj.punidos.crminer.cepi.pdfboximpl;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
//import java.util.Iterator;

import org.sj.punidos.crminer.sectorizer.GraphicString;
import org.sj.punidos.crminer.sectorizer.PosRegionCluster;
import org.sj.punidos.crminer.sectorizer.Positionable;
import org.sj.punidos.crminer.sectorizer.ContentRegion;
import org.sj.punidos.crminer.sectorizer.GStringBuffer;


import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.pdfbox.contentstream.PDFGraphicsStreamEngine;
import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.state.PDGraphicsState;
import org.apache.pdfbox.pdmodel.graphics.state.PDTextState;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.util.Matrix;
import org.apache.pdfbox.util.Vector;
import org.sj.punidos.crminer.CommonInfo;
import org.sj.punidos.crminer.sectorizer.StringRegion;
import org.sj.punidos.crminer.sectorizer.StrRegionCluster;
import org.sj.punidos.crminer.tablemkr.GridTableMaker;
import org.sj.punidos.crminer.tablemkr.TLine;
import org.sj.punidos.crminer.tablemkr.SplitTableMaker;
import org.sj.punidos.crminer.tablemkr.Table;
import org.sj.punidos.crminer.tablemkr.TableMaker;

/**
 * 
 */
public class CustomGraphicsStreamEngine extends PDFGraphicsStreamEngine implements CommonInfo
{
	
	/*
	 * 
	 * 
	 * refs:
	 * PDFStreamEngine
	 * https://apache.googlesource.com/pdfbox/+/35025f10c2f5fff264a0b42a73e1d7133fd1c41b/pdfbox/src/main/java/org/apache/pdfbox/contentstream/PDFStreamEngine.java
	 * 
	 * PDFGraphicStreamEngine
	 * https://apache.googlesource.com/pdfbox/+/a3241d612d3ae387525d58d64b93b7804dde5939/pdfbox/src/main/java/org/apache/pdfbox/contentstream/PDFGraphicsStreamEngine.java
	 */
	
	public static final String HTML_HEAD = 
			"<html><head>"
			+ "<style>table {\r\n" + 
			"  border-collapse: collapse;\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"table, th, td {\r\n" + 
			"  border: 1px solid black;\r\n" + 
			"}</style>"
			+ "</head>";
	
    GStringBuffer regionText = new GStringBuffer();
    LinkedList<TLine> lines = new LinkedList<TLine>();
    LinkedList<GraphicString> gstrings = new LinkedList<GraphicString>();
    
    LinkedList<Table> generatedTables = new LinkedList<Table>(); 
    
	//RegionCluster cluster;
	
	int rectCount = 0;
	int lineCount = 0;
	int strokeCount = 0;
	
	ClippingArea clip;
    GrPath path = new GrPath();
    
    /**
     * maximum thickness of a rectangle in order to be considered as a line.
     */
    double maxLineThickness = 2;

    /**
     * maximum distance of two objects to be considered in the same region (table).
     */
    double tableThreshold = 0.5;

	
	//java.util.Vector<Line> lines = new java.util.Vector<Line>();
	//TableMaker tmaker = new SplitTableMaker();
    //TableMaker tmaker = new GridTableMaker();
    

    
    public PosRegionCluster<Positionable> organizeContents()
    {
		PosRegionCluster<Positionable> rc = new PosRegionCluster<Positionable>();
		
    	for(TLine l: lines) {
    		if(l == null) {
    			System.err.println("Attepmting ot add null line");
    		} else {
    			rc.push(l);
    		}
    	}
    	System.out.println("lines added: "+rc.countRemaining());

    	for(GraphicString gs: gstrings) {
    		if(gs == null) {
    			System.err.println("Attepmting ot add null GraphicString");
    		} else {
    			rc.push(gs);
    		}
    	}
    	System.out.println("objects added: "+rc.countRemaining());

    	rc.partitionContent(tableThreshold);
    	
    	return rc;
    }
    
    public List<Table> createTables() {
    	List<TableMaker> makers = createTableMakers();
    	LinkedList<Table> tables = new LinkedList<Table>();
    	for(TableMaker tm: makers) {
    		Table t = tm.makeTable();
    		if(t != null) {
    			tables.add(t);
    		}
    	}
    	System.out.println("Tables created: "+tables.size());
    	return tables;
    }
    
    public List<TableMaker> createTableMakers() {
    	PosRegionCluster<Positionable> cluster = organizeContents();
    	LinkedList<TableMaker> makers = new LinkedList<TableMaker>();

    	System.out.println("Regions: "+cluster.getNumberOfRegions());

    	for(int i=0;i<cluster.getNumberOfRegions(); i++) {
    		ContentRegion<Positionable> r = cluster.getRegion(i);
    		GridTableMaker tmaker = new GridTableMaker();
        	System.out.println("  Region: "+i);
        	System.out.println("    elements: "+r.countElements());

    		Iterator<Positionable> it = r.contentIterator();
    		
    		while(it.hasNext()) {
    			Positionable obj = it.next();
    			if(obj instanceof TLine) {
    				TLine line = (TLine) obj;
    				System.out.println("      line: "+line.toString());
        			tmaker.add(line);
    			} else if(obj instanceof GraphicString) {
    				GraphicString gs = (GraphicString) obj;
    				System.out.println("      gs: "+gs.toString());
    				tmaker.add(gs);
    			}
    		}
    		
    		makers.add(tmaker);
    	}

    	System.out.println("Makers: "+makers.size());
   	
    	return makers;

    }
    
    public void writeHTML(String filename) throws IOException {
    	File f = new File(filename);
    	FileOutputStream fos = new FileOutputStream(f);
    	
    	List<Table> tables = createTables();
    	
    	
    	fos.write((HTML_HEAD + "<body>").getBytes());

    	for(Table t: tables) {
    		Table clean = t.trim();
    		if(clean == null) {
    			fos.write("null table".getBytes());
    		} else {
    			String s = clean.toHTML();
    			fos.write(s.getBytes());
    		}
			fos.write("<br/>".getBytes());

    	}
    	fos.write("</body></html>".getBytes());

    	
    	fos.close();
    	
    }

    
    /**
     * Constructor.
     *
     * @param page PDF Page
     */
    protected CustomGraphicsStreamEngine(PDPage page)
    {
        super(page);
        clip = new ClippingArea();
    }
    
    /**
     * Constructor.
     *
     * @param page PDF Page
     */
    public CustomGraphicsStreamEngine(PDPage page, Rectangle clipRect)
    {
        super(page);
        clip = new ClippingArea(clipRect);
    }
    

    
    /**
     * Runs the engine on the current page.
     *
     * @throws IOException If there is an IO error while drawing the page.
     */
    public void run() throws IOException
    {
        processPage(getPage());
        for (PDAnnotation annotation : getPage().getAnnotations())
        {
            showAnnotation(annotation);
        }
        showStats();
        //java.util.Vector<org.sj.punidos.crminer.tablemkr.Area> areas = tmaker.buildAreas();
        //logAreas(areas);
        //tmaker.toSVG(areas);
    }
    
    void showStats() {
    	System.out.println("strokes:"+strokeCount);
    	System.out.println("lines:"+lineCount);
    	System.out.println("rectangles:"+rectCount);
    	System.out.println("size H:"+getPage().getBBox().getHeight());
    }
    
    @Deprecated
    public double transf_Yd(double y) {
    	PDRectangle r = getPage().getBBox();
    	return r.getHeight() - y;
    }

    @Deprecated
    public float transf_Yf(float y) {
    	PDRectangle r = getPage().getBBox();
    	return r.getHeight() - y;
    }

    @Deprecated
    public Point2D transform(Point2D p) {
    	if(p instanceof Point2D.Float) {
    		return new Point2D.Float((float) p.getX(), transf_Yf((float)p.getY()));
    	} else {
    		return new Point2D.Double(p.getX(), transf_Yd(p.getY()));
    	}
    }
    
    //FIXME: not necessary anymore
    public TLine buildLine(Point2D a, Point2D b) {
    	//return new Line(transform(a), transform(b));
    	return new TLine(a, b);
    }
    
    public TLine buildVertLine(Rectangle2D rect) {
    	return new TLine(new Point2D.Double(rect.getCenterX(), rect.getY()),
    			new Point2D.Double(rect.getCenterX(), rect.getMaxY()));
    }

    public TLine buildHorizLine(Rectangle2D rect) {
    	return new TLine(new Point2D.Double(rect.getX(), rect.getCenterY()),
    			new Point2D.Double(rect.getMaxX(), rect.getCenterY()));
    }


    public Rectangle transfRect(Rectangle r) {
    	//Rectangle z = new Rectangle(1,2,3,4);
    	
    	/*return new Rectangle((int) r.getX(), (int) transf_Yd(r.getY()),
    			(int) r.getWidth(), (int) r.getHeight());*/
    	return r;
    }

    
    @Override
    public void appendRectangle(Point2D p0, Point2D p1, Point2D p2, Point2D p3) throws IOException
    {
         System.out.printf("appendRectangle %.2f %.2f, %.2f %.2f, %.2f %.2f, %.2f %.2f\n",
                p0.getX(), p0.getY(), p1.getX(), p1.getY(),
                p2.getX(), p2.getY(), p3.getX(), p3.getY()); 
        //FIXME:
    	//strokeRectangle(p0,p1,p2,p3);
        /*
    	path.moveTo(p0);
    	path.lineTo(p1);
    	path.lineTo(p2);
    	path.lineTo(p3);
    	path.lineTo(p0);
    	*/
        path.appendRectangle(p0, p1, p2, p3);
    	
    	
    }

    public void strokeRectangle(Point2D p0, Point2D p1, Point2D p2, Point2D p3) throws IOException
    {

        Rectangle2D r = StringRegion.rectangleFromPoints(p0,p1,p2,p3);
        if(clip.clip(r)) {
            System.out.printf("strokeRectangle %.2f %.2f, %.2f %.2f, %.2f %.2f, %.2f %.2f\n",
                    p0.getX(), p0.getY(), p1.getX(), p1.getY(),
                    p2.getX(), p2.getY(), p3.getX(), p3.getY());
            /* add rectangle */
            /*tmaker.add(buildLine(p0, p1));
            tmaker.add(buildLine(p1, p2));
            tmaker.add(buildLine(p2, p3));
            tmaker.add(buildLine(p3, p0));*/
            
            lines.add(buildLine(p0, p1));
            lines.add(buildLine(p1, p2));
            lines.add(buildLine(p2, p3));
            lines.add(buildLine(p3, p0));
            
            
            rectCount++;

        } else {
        	System.out.printf("discarded rect");
        }
        //cluster.pushRegion(r);
    }
    
    @Override
    public void drawImage(PDImage pdImage) throws IOException
    {
        System.out.println("drawImage");
    }
    
    @Override
    public void clip(int windingRule) throws IOException
    {
        System.out.println("clip");
    }
    
    @Override
    public void moveTo(float x, float y) throws IOException
    {
    	//region.reset();
    	//region.add(new Point((int)x,(int)y));
	path.moveTo(new Point2D.Float(x,y));
        System.out.printf("moveTo %.2f %.2f\n", x, y);
    }
    @Override
    public void lineTo(float x, float y) throws IOException
    {
    	//region.add(new Point((int)x,(int)y));
    	lineCount++;
    	path.lineTo(new Point2D.Float(x,y));

        System.out.printf("lineTo %.2f %.2f\n", x, y);
    }
    @Override
    public void curveTo(float x1, float y1, float x2, float y2, float x3, float y3) throws IOException
    {
    	//region.add(new Point((int)x1,(int)y1));
    	//region.add(new Point((int)x2,(int)y2));
    	//region.add(new Point((int)x3,(int)y3));
    	System.out.printf("curveTo %.2f %.2f, %.2f %.2f, %.2f %.2f\n", x1, y1, x2, y2, x3, y3);
    }
    
    @Override
    public Point2D getCurrentPoint() throws IOException
    {
        // if you want to build paths, you'll need to keep track of this like PageDrawer does
        return new Point2D.Float(0, 0);
    }
    
    @Override
    public void closePath() throws IOException
    {
        System.out.println("closePath");
    }
    
    @Override
    public void endPath() throws IOException
    {
        System.out.println("endPath");
    }
    
    void pushCurrent() {
    	/*Rectangle r = region.getRegion();
    	if(r != null) {
    		cluster.pushRegion(r);
    		region.reset();
    	}*/
    }
    
    @Override
    public void strokePath() throws IOException
    {
    	
    	//pushCurrent();o

	Iterable<Shape> elems = path.getIterable();
	int linCnt = 0;
	for(Shape s : elems) {
		if(s instanceof Line2D) {
			Line2D l = (Line2D) s;
			lines.add(buildLine(l.getP1(), l.getP2()));
			linCnt++;
		} else if(s instanceof Rectangle2D) {
			//TODO
		}
	}
	strokeCount += linCnt;
        System.out.println("strokePath("+linCnt+")");
	path.clear();
    }
    
    @Override
    public void fillPath(int windingRule) throws IOException
    {
    	pushCurrent();
    	//strokePath();
    	
    	/*
    	 * TODO:
    	 * - detect closed sub-paths (repeated point...)
    	 * - detect rectangles.
    	 * 	 - detect thin rectangles -> generate lines.
    	 */
    	PDGraphicsState gs = this.getGraphicsState();
    	PDColor clr = gs.getStrokingColor();
        System.out.println("fillPath (color="+clr.toRGB()+")");
        PDColor nsclr = gs.getNonStrokingColor();
        System.out.println("         (non stroking="+nsclr.toRGB()+")");
        
        System.out.println("         (path:"+path.numElements()+")");
    	Iterable<Shape> elems = path.getIterable();
    	int linCnt = 0;
    	for(Shape s : elems) {
    		if(s instanceof Line2D) {
    			//TODO
    		} else if(s instanceof Rectangle2D) {
    			//TODO
    			Rectangle2D rect = (Rectangle2D) s;
    			if(isVerticalStrip(rect,maxLineThickness)) {
    				if(nsclr.toRGB() == 0) {
    					//tmaker.add(buildVertLine(rect));
    					lines.add(buildVertLine(rect));
    					this.lineCount++;
    				} else {
    					System.out.println("Non black vert. line");
    				}
    			} else if(isHorizontalStrip(rect,maxLineThickness)){
    				if(nsclr.toRGB() == 0) {
    					//tmaker.add(buildHorizLine(rect));
    					lines.add(buildHorizLine(rect));
    					this.lineCount++;
    				} else {
    					System.out.println("Non black horiz. line");
    				}
    			}
    		}
    		
    	}

        path.clear();
    }
    
    public static boolean isVerticalStrip(Rectangle2D rect, double threshold) {
    	return (rect.getWidth() < threshold);
    }
    
    public static boolean isHorizontalStrip(Rectangle2D rect, double threshold) {
    	return (rect.getHeight() < threshold);
    }

    
    @Override
    public void fillAndStrokePath(int windingRule) throws IOException
    {
        System.out.println("fillAndStrokePath");
    }
    
    @Override
    public void shadingFill(COSName shadingName) throws IOException
    {
        System.out.println("shadingFill " + shadingName.toString());
    }
    
    
    static void dbg_showbytes(byte data[]) {
    	for(byte b: data) {
    		System.out.println(String.format("0x%x ",b));
    	}
    	
		System.out.println();
    }
    
    /**
     * Overridden from PDFStreamEngine.
     */
    
    
    @Override
    public void showTextString(byte[] string) throws IOException
    {
        System.out.print("showTextString \"");
        super.showTextString(string);
        System.out.println("\"");
        //COSArray str = new COSArray();
        
        Rectangle r = regionText.getRegion();
        if(r == null) {
        	System.err.println("No region");
        	throw new NullPointerException("regionText.region");
        }
        gstrings.add(new GraphicString(regionText.getText(), r));
        System.out.println("  -REG:"+regionText.getText());
         
        regionText.reset();
    }
	

    /**
     * Overridden from PDFStreamEngine.
     */
    @Override
    public void showTextStrings(COSArray array) throws IOException
    {
        System.out.print("showTextStrings \"");
        super.showTextStrings(array);
        System.out.println("\"");

        Rectangle r = regionText.getRegion();
        if(r == null) {
        	System.err.println("No region");
        	throw new NullPointerException("regionText.region");
        }
        if(clip.clip(r)) {
        	gstrings.add(new GraphicString(regionText.getText(), r));
        	System.out.println(regionText.getText());
        }
        
        regionText.reset();

        //System.out.println("  "+r.toString());
  
    }
    /**
     * Overridden from PDFStreamEngine.
     */
    @Override
    protected void showGlyph(Matrix textRenderingMatrix, PDFont font, int code, String unicode,
                             Vector displacement) throws IOException
    {
        System.out.print(unicode);

        //showVector(displacement);
        //Vector w = font.getDisplacement(code);
       	//showVector(w);

        super.showGlyph(textRenderingMatrix, font, code, unicode, displacement);

        // from CustomPageDrawer
        // bbox in EM -> user units
        Shape bbox = new Rectangle2D.Float(0, 0, font.getWidth(code) / 1000, 1);
        AffineTransform at = textRenderingMatrix.createAffineTransform();
        bbox = at.createTransformedShape(bbox);
        
        regionText.add(unicode);
        regionText.add(transfRect(bbox.getBounds()));

    }
    
    protected void showVector(Vector d)
    {
    	System.out.print(d.toString());
    }	
    
    // NOTE: there are may more methods in PDFStreamEngine which can be overridden here too.
}
