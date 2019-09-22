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

		return new CellLocation(x_index, y_index, horizSpan, vertSpan);
    	
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
	
	public Cell areaToCell(Area a, double threshold) {
		Rectangle2D r = a.getBounds();
    	CellLocation cloc = rectToLoc(r, threshold);
    	return cloc.cell;
	}
	

}
