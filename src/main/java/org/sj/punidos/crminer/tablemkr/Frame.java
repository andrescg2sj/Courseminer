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

import org.sj.punidos.crminer.sectorizer.GraphicString;

public class Frame {

	int x[];
	int y[];

	public Frame(int _x[], int _y[]) {
		x = _x;
		y = _y;
	}
	
    static int getLastIndexBelow(double value, int marks[]) 
    {
    	int i=0;
    	if(value < marks[0]) throw new IllegalArgumentException("Value is too low.");
    	if(value > marks[marks.length-1]) throw new IllegalArgumentException("Value is too high.");
    	while((i <= marks.length) && (marks[i] < value)) {
    		i++;
    	}
    	return i-1;
    }
    
    int reverseIndexY(int i) {
    	return y.length-1-i;
    }
    
    static int getFirstIndexAbove(double value, int marks[])
    {
    	return 1 + getLastIndexBelow(value, marks);
    }
    
    public CellLocation rectToLoc(Rectangle2D rect, double threshold) {
    	int x_index = getLastIndexBelow(rect.getX()+threshold, this.x);
		int y_index = getLastIndexBelow(rect.getY()+threshold, this.y);
		int max_x_index = getFirstIndexAbove(rect.getMaxX()-threshold, this.x);
		int max_y_index = getFirstIndexAbove(rect.getMaxY()-threshold, this.y);
		int horizSpan = max_x_index - x_index;
		int vertSpan = max_y_index - y_index;
		

		//return new CellLocation(x_index, y_index, horizSpan, vertSpan);
		return new CellLocation(x_index, reverseIndexY(max_y_index), horizSpan, vertSpan);
    }
    
    public CellLocation lineToLoc(Line line, double threshold) {
    	int x_index = getLastIndexBelow(line.getA().getX()+threshold, this.x);
		int y_index = getLastIndexBelow(line.getA().getY()+threshold, this.y);
		int max_x_index = getFirstIndexAbove(line.getB().getX()-threshold, this.x);
		int max_y_index = getFirstIndexAbove(line.getB().getY()-threshold, this.y);
		int horizSpan = max_x_index - x_index;
		int vertSpan = max_y_index - y_index;

		return new CellLocation(x_index, reverseIndexY(max_y_index), horizSpan, vertSpan);

    }
    
    
    public CellLocation textLocation(GraphicString gstr, double threshold) {
    	Rectangle2D r = gstr.getBounds();
    	CellLocation cloc = rectToLoc(r, threshold);
    	cloc.add(gstr);
    	return cloc;
    }
	
	public CellLocation areaToCellLoc(Area a, double threshold) {
		Rectangle2D r = a.getBounds();
    	CellLocation cloc = rectToLoc(r, threshold);
    	cloc.cell.addAll(a.content);
    	return cloc;
	}
	
	public int numRows()
	{
		return y.length - 1;
	}
	
	public int numCols()
	{
		return x.length - 1;
	}

	
	public Cell areaToCell(Area a, double threshold) {
		Rectangle2D r = a.getBounds();
    	CellLocation cloc = rectToLoc(r, threshold);
    	return cloc.cell;
	}
	

}
