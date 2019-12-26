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

//TODO: decide a consistent order for column and row parameters
public class GridTableMaker extends TableMaker {
	
	Grid grid;

	public GridTableMaker()
	{
		
	}
	
	public GridTableMaker(Grid g)
	{
		grid = g;
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
	
	public static Table fromGrid(Grid g) {
		GridTableMaker maker = new GridTableMaker(g);
		//return maker.makeFromGrid();
		return new Table(maker.cellMatrix());
	}
	
	public Cell[][] cellMatrix() {
    	Cell table[][] = new Cell[grid.numRows()][grid.numCols()];
    	for(int r=0; r<grid.numRows();r++) {
    		for(int c=0; c<grid.numCols();c++) {
    			if(table[r][c] == null) {
    				Cell cell = grid.getMaxCell(r,c);
    				fillCells(table, cell, r,c);
    			}
    		}
    	}
    	return table;
	}
	
	/** 
	 * Make the table, once the grid is built.
	 * @return
	 */
	Table makeFromGrid() {
    	Cell table[][] = cellMatrix();
    	
    	
    	Vector<Area> areas = buildAreas(table);
    	addStringsToAreas(areas);

    	// debug
    	toSVG(areas);

    	
       	for(Area a: areas) {
			System.out.println("Build location: " + a.toString());
    		CellLocation clo = frame.areaToCellLoc(a, this.collisionThreshold);
    		if(clo == null)
    			throw new NullPointerException("Frame created a null CellLocation");
    		try {
				System.out.println("Testing:" + clo.row+","+clo.col);
    			if(table[clo.row][clo.col] != null) {
    				table[clo.row][clo.col].contents = clo.cell.contents;
    			} else {
    				// TODO
    				System.err.println("Warning! Trying to fill empty cell.");
    			}
    		} catch(Exception ie) {
    			ie.printStackTrace();
    		}
    	}
    	


    	
		return new Table(table);
	}
	
	/*public Table makeFromGrid() {
		return makeFromGrid(grid);
	}*/
	
	public Table makeTable() {
		
		//make frame from lines
		frame = buildFrame();
		frame.log();
		
		//grid = new CellLimits[f.numRows()][f.numCols()];
		grid = new Grid(frame.numRows(), frame.numCols());
		// volver a recorrer. Rellenar CellBorders
		for(Line l: lines) {
			CellLocation cloc = frame.lineToLoc(l, collisionThreshold);
			System.out.println("*line: "+l.toString());
			System.out.println("*location: "+cloc.toString());

			if(l.isHoriz()) {
				for(int i=0; i<=cloc.cell.colSpan-1;i++) {
					//grid[cloc.row][cloc.col+i].setBottom();
					System.out.println("set bottom: "+cloc.row+","+(cloc.col+i));
					grid.setBottom(cloc.row,cloc.col+i);
				}
			} else {
				for(int i=0; i<=cloc.cell.rowSpan-1;i++) {
					//grid[cloc.row+i][cloc.col].setRight();
					System.out.println("set right: "+(cloc.row+i)+","+cloc.col);
					grid.setRight(cloc.row+i,cloc.col);
				}
			}
		}
		
		return makeFromGrid();
		
	}
	
	public Vector<Area> buildAreas(Cell cells[][]) {
	    //Line lineArray[] = 	lines.toArray(new Line[lines.size()]);
		// return buildAreas(lineArray);
		Vector<Area> areas = new Vector<Area>();
		for(int col=0;col<grid.numCols();col++) {
			for(int row=0;row<grid.numRows();row++) {
				if(!(cells[row][col] instanceof HiddenCell)) {
					Cell cell = cells[row][col];
					Area a = frame.cellToArea(col, row, cell.colSpan, cell.rowSpan);
					System.out.println("built area: "+a);
					areas.add(a);
				}
			}
		}
		return areas;
	}

}
