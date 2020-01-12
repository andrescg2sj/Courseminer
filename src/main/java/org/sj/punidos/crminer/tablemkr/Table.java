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

public class Table implements CommonInfo {

	//Vector<Area> areas;
	Cell cells[][];
	
	public Table(Cell cont[][]) {
		cells = cont;
	}
	
	public Table(int cols, int rows) {
		cells = new Cell[cols][rows];
	}
	
	public Table(Table t) {
		//TODO: clone?
		cells = t.cells;
	}
	
	public Cell get(int col, int row)
	{
		return cells[col][row];
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
