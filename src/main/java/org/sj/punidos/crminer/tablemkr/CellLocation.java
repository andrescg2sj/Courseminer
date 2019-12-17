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
