package org.sj.punidos.crminer.tablemkr;

import org.sj.punidos.crminer.sectorizer.GraphicString;

public class CellLocation {
	
	int row = -1;
	int col = -1;
	
	Cell cell;

	public CellLocation(int c, int r, int hspan, int vspan) {
		row = r;
		col = c;
		cell = new Cell(hspan, vspan);
		
	}
	
	public String toString() {
		return String.format("cell(%d,%d,{%s})", row, col, cell.toString());
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void add(GraphicString gstr)
	{
		cell.add(gstr);
	}
	
	public boolean contains(CellLocation cloc) 
	{
		return ((cloc.getRow() >= this.getRow()) && (cloc.getCol() >= this.getCol()) &&
			((cloc.getRow()+cloc.cell.vertSpan) <= (this.getRow()+this.cell.vertSpan))  && 
			((cloc.getCol()+cloc.cell.horizSpan) <= (this.getCol()+this.cell.horizSpan)) );
	}
	
	
}
