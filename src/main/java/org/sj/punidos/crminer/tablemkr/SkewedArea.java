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

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class SkewedArea extends Area
{
    Line top, left, right, bottom;


    public boolean collision(Line l) {
	throw new UnsupportedOperationException("Area.collision");
    }

    public boolean collision(Line l, double tolerance) {
	throw new UnsupportedOperationException("Area.collision(l,t)");
    }

    public Area[] split(Line l) {
	throw new UnsupportedOperationException("Area.split");
	
    }

    
    public  boolean outOrBound(Line l)
    {
	throw new UnsupportedOperationException("Area.split");
	
    }

    public boolean contains(Point2D p) {
	throw new UnsupportedOperationException("Area.contains");
    }


    public boolean strictlyContains(Line l) {
	throw new UnsupportedOperationException("Area.strictlyContains");
    }

    public Rectangle2D getBounds() {
	throw new UnsupportedOperationException("Area.getBounds");
    }

	@Override
	public boolean contains(Rectangle r) {
		// TODO Auto-generated method stub
		//return false;
		throw new UnsupportedOperationException("SkewedArea.contains(Rectangle)");
	}
    
}
