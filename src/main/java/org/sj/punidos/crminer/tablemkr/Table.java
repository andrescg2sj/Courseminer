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

import java.util.Vector;

import org.sj.punidos.crminer.CommonInfo;
import org.sj.punidos.crminer.sectorizer.GraphicString;

public class Table implements CommonInfo {

	//Vector<Area> areas;
	Cell cells[][];
	
	public Table(Cell cont[][]) {
		cells = cont;
	}
	
	public Table(int cols, int rows) {
		cells = new Cell[cols][rows];
		init();
	}
	
	public static Table emptyTable(int cols, int rows) {
		Cell c[][] = new Cell[cols][rows];
		return new Table(c);
	}
	
	
	
	public void init() {
		for(int r=0;r<getRows();r++) {
			for(int c=0;c<getCols();c++) {
				cells[c][r] = new Cell(1,1);
			}
		}
	}
	
	
	
	
	public Table(Table t) {
		//TODO: clone?
		cells = t.cells;
	}
	
	public void add(int col, int row, String str) {
		if(cells[col][row] == null) {
			
		} else if(cells[col][row] instanceof HiddenCell) {
			
		} else {
			cells[col][row].add(new GraphicString("a",null));

		}
	}
	
	
	public Cell get(int col, int row)
	{
		return cells[col][row];
	}
	
	//TODO: Test
	public Table subTable(int col, int row, int numCols, int numRows) {
		Cell newCells[][] = new Cell[numCols][numRows];
		
		for(int r=0;r<numRows;r++) {
			for(int c=0;c<numCols;c++) {
				//FIXME
				Cell model = cells[c+col][r+row];
				
				if((r + model.rowSpan >= getRows()) ||
						(c + model.colSpan >= getCols())) {
					int newCols = Math.min(model.colSpan, numCols - c);
					int newRows = Math.min(model.rowSpan, numRows - r);
					newCells[c][r] = new Cell(newCols, newRows, model.contents);
					
				} else {
					newCells[c][r] = model;
				}
			}
		}
		
		return new Table(newCells);
	}
	
	public Table trim() {
		int col = 0;
		int row = 0;
		while(col < getCols() && isEmptyCol(col)) col++;
		if(col >= getCols()) return null;
		while(row < getRows() && isEmptyRow(row)) row++;
		
		int lastCol = getCols() - 1;
		int lastRow = getRows() - 1;
		while(isEmptyCol(lastCol) && lastCol > col) lastCol--;
		while(isEmptyRow(lastRow) && lastRow > row) lastRow--;
		int numCols = lastCol - col + 1;
		int numRows = lastRow - row + 1;
		return subTable(col, row, numCols, numRows);
	}
	
	public boolean isEmptyRow(int row) {
		for(int c=0;c<getCols();c++) {
			if(!cells[c][row].isEmpty()) return false;
		}
		return true;
	}

	public boolean isEmptyCol(int col) {
		for(int r=0;r<getRows();r++) {
			if(!cells[col][r].isEmpty()) return false;
		}
		return true;
	}

	
	public void fillCells(Cell cell, int col, int row)
	{
		HiddenCell hidden = new HiddenCell(col, row, cell);
		for(int r=0;r<cell.rowSpan;r++) {
			for(int c=0;c<cell.colSpan;c++) {
				if(r == 0 && c == 0) {
					cells[c+col][r+row] = cell;
				} else {
					cells[c+col][r+row] = hidden;  
				}
			}
		}
	}

	
	/*Cell[][] copy(Cell data[][]) {
		//Cell newData[][] = new
		
	}*/
	
	public int getRows() {
		return cells[0].length;
	}
	
	public int getCols() {
		return cells.length;
	}
	
	public Cell getCell(int col, int row) {
		return cells[col][row];
	}
	

	public String toHTML() {
		String data = "<table style=\"\">";
		for(int row=0; row<getRows();row++) {
			data += "<tr>" ;
			for(int col=0; col<getCols();col++) {
				Cell cell = cells[col][row];
				if(cell != null && !(cell instanceof HiddenCell)) {
					String opentag = "<td";
					if(cell.colSpan > 1) opentag+=String.format(" colspan=\"%d\"", cell.colSpan);
					if(cell.rowSpan > 1) opentag+=String.format(" rowspan=\"%d\"", cell.rowSpan);
					opentag +=">";
					data += opentag + cell.fullText() +"</td>";
				}
			}
			data += "</tr>" + NEW_LINE;
		}
		return data + "</table>";
		//throw new UnsupportedOperationException("Table.toHTML");
	}
}
