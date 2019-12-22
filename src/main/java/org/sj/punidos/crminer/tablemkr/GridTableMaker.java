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

//TODO: decide a consistent order for column and row parameters
public class GridTableMaker extends TableMaker {
	
	Grid grid;

	public GridTableMaker()
	{
		
	}
	
	

	//TODO: Must be a method of Table
	static void fillCells(Cell matrix[][], Cell cell, int row, int col)
	{
		HiddenCell hidden = new HiddenCell(col, row, cell);
		for(int r=0;r<cell.rowSpan;r++) {
			for(int c=0;c<cell.colSpan;c++) {
				if(r == 0 && c == 0) {
					matrix[r+row][c+col] = cell;
				} else {
					matrix[r+row][c+col] = hidden;  
				}
			}
		}
	}
	
	static Table makeFromGrid(Grid g) {
    	Cell table[][] = new Cell[g.numRows()][g.numCols()];
    	for(int r=0; r<g.numRows();r++) {
    		for(int c=0; c<g.numCols();c++) {
    			if(table[r][c] == null) {
    				Cell cell = g.getMaxCell(r,c);
    				fillCells(table, cell, r,c);
    			}
    		}
    	}
		return new Table(table);
	}
	
	public Table makeFromGrid() {
		return makeFromGrid(grid);
	}
	
	public Table makeTable() {
		
		//make frame from lines
		Frame f = buildFrame();

		//grid = new CellLimits[f.numRows()][f.numCols()];
		grid = new Grid(f.numRows(), f.numCols());
		// volver a recorrer. Rellenar CellBorders
		for(Line l: lines) {
			CellLocation cloc = f.lineToLoc(l, collisionThreshold);
			if(l.isHoriz()) {
				for(int i=0; i<=cloc.cell.colSpan;i++) {
					//grid[cloc.row][cloc.col+i].setBottom();
					grid.setBottom(cloc.row,cloc.col+i);
				}
			} else {
				for(int i=0; i<=cloc.cell.rowSpan;i++) {
					//grid[cloc.row+i][cloc.col].setRight();
					grid.setRight(cloc.row+i,cloc.col);
				}
			}
		}
		
		return makeFromGrid();
		
	}
}
