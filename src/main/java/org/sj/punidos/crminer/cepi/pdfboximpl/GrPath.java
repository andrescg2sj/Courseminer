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

    public void clear() {
	elements.clear();
    }

    
	
}

