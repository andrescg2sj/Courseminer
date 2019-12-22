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
    
    Frame buildFrame() {
    	/* convenience array that stores all marks */
    	int i=0;
    	int j = lines.size()-1;
    	int allMarks[] = new int[lines.size()];
    	for(Line l: lines) {
    		if(l.isHoriz()) {
    			System.out.println(l.toString() + " is horziontal.");
    			allMarks[i++] = (int) l.getA().getY();
    			
    		} else {
    			System.out.println(l.toString() + " is vertical.");
    			allMarks[j--] = (int) l.getA().getX();
    		}
    	}
    	//int x[] = new int[i];
    	//int y[] = new int[allMarks.length - i];
    	System.out.println(String.format("lines.size=%d, i=%d, j=%d", lines.size(), i,j));
    	System.out.println(String.format("marks.length=%d", allMarks.length));
    	int x[] = Arrays.copyOfRange(allMarks, i, allMarks.length);
    	if(x.length != j) {
    		System.err.println("Warning! x.length="+x.length);
    	}
    	int y[] = Arrays.copyOf(allMarks, i);
    	Arrays.sort(x);
    	Arrays.sort(y);
    	
    	return new Frame(x,y);
    }


}
