package org.sj.punidos.crminer.omio.pdfboximpl.beta;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

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
 * Ref:
 * https://apache.googlesource.com/pdfbox/+/trunk/examples/src/main/java/org/apache/pdfbox/examples/rendering
 *
 */

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.pdfbox.contentstream.PDFGraphicsStreamEngine;
import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.state.PDGraphicsState;
import org.apache.pdfbox.pdmodel.graphics.state.PDTextState;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.util.Matrix;
import org.apache.pdfbox.util.Vector;
import org.sj.punidos.crminer.sectorizer.ContentRegion;
import org.sj.punidos.crminer.sectorizer.GStringBuffer;
import org.sj.punidos.crminer.sectorizer.RegionCluster;
/**
 *
 * 
 * @author John Hewson
 * @author Andres Gonzalez
 */
public class OMIOPageExtractor extends PDFGraphicsStreamEngine
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
	
	File input;
	
	GStringBuffer region;
	RegionCluster cluster;
	

	/*
    public static void main(String[] args) throws IOException
    {
        File file = new File("res/OMIO-CARAB-2.pdf");
        PDDocument doc = PDDocument.load(file);
        PDPage page = doc.getPage(0);
        CustomGraphicsStreamEngine engine = new CustomGraphicsStreamEngine(page);
        engine.run();
        doc.close();
        //System.out.println("------------------");
        //System.out.println(engine.cluster.toHTML());
        engine.writeHTML("out/doc1.htm");
    }
    */
    
    protected void writeHTML(String filename) throws IOException {
    	File f = new File(filename);
    	FileOutputStream fos = new FileOutputStream(f);
    	String s = cluster.toHTML();
    	fos.write(s.getBytes());
    	fos.close();
    	
    }
    
    
    //TODO: Is this a good design?
    public static void firstPageToHTML(File f, String fnameout) throws InvalidPasswordException, IOException {
        PDDocument doc = PDDocument.load(f);
        PDPage page = doc.getPage(0);
        OMIOPageExtractor engine = new OMIOPageExtractor(page);
        engine.run();
        doc.close();
        engine.writeHTML(fnameout);
        //return engine;
    	
    }
    
    
    /**
     * Constructor.
     *
     * @param page PDF Page
     */
    protected OMIOPageExtractor(PDPage page)
    {
        super(page);
        region = new GStringBuffer();
        cluster = new RegionCluster();
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
    }
    
    @Override
    public void appendRectangle(Point2D p0, Point2D p1, Point2D p2, Point2D p3) throws IOException
    {
        System.out.printf("appendRectangle %.2f %.2f, %.2f %.2f, %.2f %.2f, %.2f %.2f\n",
                p0.getX(), p0.getY(), p1.getX(), p1.getY(),
                p2.getX(), p2.getY(), p3.getX(), p3.getY());
        Rectangle r = ContentRegion.rectangleFromPoints(p0,p1,p2,p3);
        cluster.pushRegion(r);
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
    	region.reset();
    	region.add(new Point((int)x,(int)y));
        System.out.printf("moveTo %.2f %.2f\n", x, y);
    }
    @Override
    public void lineTo(float x, float y) throws IOException
    {
    	region.add(new Point((int)x,(int)y));
        System.out.printf("lineTo %.2f %.2f\n", x, y);
    }
    @Override
    public void curveTo(float x1, float y1, float x2, float y2, float x3, float y3) throws IOException
    {
    	region.add(new Point((int)x1,(int)y1));
    	region.add(new Point((int)x2,(int)y2));
    	region.add(new Point((int)x3,(int)y3));
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
    	Rectangle r = region.getRegion();
    	if(r != null) {
    		cluster.pushRegion(r);
    		region.reset();
    	}
    }
    
    @Override
    public void strokePath() throws IOException
    {
    	pushCurrent();
        System.out.println("strokePath");
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
    /**
     * Overridden from PDFStreamEngine.
     */
    @Override
    public void showTextString(byte[] string) throws IOException
    {
        System.out.print("showTextString \"");
        super.showTextString(string);
        System.out.println("\"");
        
        //FIXME
        //cluster.push(new PDFString(region.getText(),r , null));
    }
    /**
     * Overridden from PDFStreamEngine.
     */
    @Override
    public void showTextStrings(COSArray array) throws IOException
    {
        
        System.out.print("showTextStrings \"");
        region.reset();
        super.showTextStrings(array);
        Rectangle r = region.getRegion();
        //System.out.println("  "+r.toString());
        cluster.push(new PDFString(region.getText(),r , null));
        System.out.println("\"");
        System.out.println(r.toString());

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
        
        region.add(unicode);
        region.add(bbox.getBounds());

    }
    
    protected void showVector(Vector d)
    {
    	System.out.print(d.toString());
    }
    
    // NOTE: there are may more methods in PDFStreamEngine which can be overridden here too.
}