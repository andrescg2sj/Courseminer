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
 

package org.sj.punidos.crminer.sectorizer;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import static java.util.stream.Collectors.toList;

public class ContentRegion implements Positionable {

	Rectangle region;
	Vector<GraphicString> strings;
	
	public static double min(double a, double  b, double  c, double d) {
		return Math.min(Math.min(a, b), Math.min(c, d));
	}

	public static double max(double a, double  b, double c, double d) {
		return Math.max(Math.max(a, b), Math.max(c, d));
	}

	public static Rectangle rectangleFromPoints(Point2D p0, Point2D p1, Point2D p2, Point2D p3) {
		Point2D a = new Point2D.Double(
				min(p0.getX(),p1.getX(),p2.getX(),p3.getX()),
				min(p0.getY(),p1.getY(),p2.getY(),p3.getY()) );
		
		Point2D z = new Point2D.Double(
				max(p0.getX(),p1.getX(),p2.getX(),p3.getX()),
				max(p0.getY(),p1.getY(),p2.getY(),p3.getY()));
		return new Rectangle((int) a.getX(),(int) a.getY(),
				(int) (z.getX()-a.getX()), (int) (z.getY()-a.getY()));
		
	}
	
	public boolean isEmpty() 
	{
		return strings.isEmpty();
	}

	
	public ContentRegion(Rectangle r) {
		region = r;
		strings = new Vector<GraphicString>();
	}
	
	public boolean contains(ContentRegion cr) {
		return region.contains(cr.region);
	}

	
	public boolean contains(GraphicString gs) {
		return region.contains(gs.getBounds());
	}
	
	public void add(GraphicString gs) {
		strings.add(gs);
	}
	
	//public static String getText(GraphicsString )
	
	List<String> getStrings() 
	{
		// https://www.java67.com/2015/01/java-8-map-function-examples.html
		return strings.stream().map(GraphicString::getText).collect(toList());
	}

	
	Vector<GraphicString> getGraphicStrings() {
		return strings;
	}

	@Override
	public Point getPosition() {
		return region.getLocation();
	}
	
	
}
