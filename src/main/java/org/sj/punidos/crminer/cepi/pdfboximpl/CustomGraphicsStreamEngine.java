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
import org.sj.punidos.crminer.sectorizer.GStringBuffer;


import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.pdfbox.contentstream.PDFGraphicsStreamEngine;
import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.state.PDGraphicsState;
import org.apache.pdfbox.pdmodel.graphics.state.PDTextState;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.util.Matrix;
import org.apache.pdfbox.util.Vector;
import org.sj.punidos.crminer.CommonInfo;
import org.sj.punidos.crminer.sectorizer.ContentRegion;
import org.sj.punidos.crminer.sectorizer.GStringBuffer;
import org.sj.punidos.crminer.sectorizer.RegionCluster;
import org.sj.punidos.crminer.tablemkr.GridTableMaker;
import org.sj.punidos.crminer.tablemkr.Line;
import org.sj.punidos.crminer.tablemkr.SplitTableMaker;
import org.sj.punidos.crminer.tablemkr.Table;
import org.sj.punidos.crminer.tablemkr.TableMaker;
import org.sj.punidos.crminer.tablemkr.SplitTableMaker;

/**
 * Example of a custom PDFGraphicsStreamEngine subclass. Allows text and graphics to be processed
 * in a custom manner. This example simply prints the operations to stdout.
 *
 * <p>See {@link PDFStreamEngine} for further methods which may be overridden.
 * 
 * @author John Hewson
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
	//RegionCluster cluster;
	
	int rectCount = 0;
	int lineCount = 0;
	int strokeCount = 0;
	
	ClippingArea clip;
    GrPath path = new GrPath();
	
	//java.util.Vector<Line> lines = new java.util.Vector<Line>();
	//TableMaker tmaker = new SplitTableMaker();
    TableMaker tmaker = new GridTableMaker();
	
    
    public void writeHTML(String filename) throws IOException {
    	File f = new File(filename);
    	FileOutputStream fos = new FileOutputStream(f);
    	Table t = tmaker.makeTable();
    	String s = t.toHTML();
    	
    	fos.write((HTML_HEAD + "<body>").getBytes());
    	fos.write(s.getBytes());
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
    
    public double transf_Yd(double y) {
    	PDRectangle r = getPage().getBBox();
    	return r.getHeight() - y;
    }

    public float transf_Yf(float y) {
    	PDRectangle r = getPage().getBBox();
    	return r.getHeight() - y;
    }
    
    public Point2D transform(Point2D p) {
    	if(p instanceof Point2D.Float) {
    		return new Point2D.Float((float) p.getX(), transf_Yf((float)p.getY()));
    	} else {
    		return new Point2D.Double(p.getX(), transf_Yd(p.getY()));
    	}
    }
    
    public Line buildLine(Point2D a, Point2D b) {
    	//return new Line(transform(a), transform(b));
    	return new Line(a, b);
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
	//strokeRectangle(p0,p1,p2,p3);
    }

    public void strokeRectangle(Point2D p0, Point2D p1, Point2D p2, Point2D p3) throws IOException
    {

        Rectangle r = ContentRegion.rectangleFromPoints(p0,p1,p2,p3);
        if(clip.clip(r)) {
            System.out.printf("appendRectangle %.2f %.2f, %.2f %.2f, %.2f %.2f, %.2f %.2f\n",
                    p0.getX(), p0.getY(), p1.getX(), p1.getY(),
                    p2.getX(), p2.getY(), p3.getX(), p3.getY());
            /* add rectangle */
            tmaker.add(buildLine(p0, p1));
            tmaker.add(buildLine(p1, p2));
            tmaker.add(buildLine(p2, p3));
            tmaker.add(buildLine(p3, p0));
            
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

	Iterable<Line2D> lines = path.getIterable();
	int linCnt = 0;
	for(Line2D l : lines) {
	    tmaker.add(buildLine(l.getP1(), l.getP2()));
	    linCnt++;
	}
	strokeCount += linCnt;
        System.out.println("strokePath("+linCnt+")");
	path.clear();
    }
    @Override
    public void fillPath(int windingRule) throws IOException
    {
    	pushCurrent();
        System.out.println("fillPath");
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
        tmaker.add(new GraphicString(regionText.getText(), r));
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
        regionText.reset();
        super.showTextStrings(array);

        Rectangle r = regionText.getRegion();
        if(r == null) {
        	System.err.println("No region");
        	throw new NullPointerException("regionText.region");
        }
        tmaker.add(new GraphicString(regionText.getText(), r));
        System.out.println(regionText.getText());
        System.out.println("\"");
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
