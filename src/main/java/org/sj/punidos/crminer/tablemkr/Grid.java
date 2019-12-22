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

public class Grid {
	
	CellLimits grid[][];
	
	CellLimits[][] createMatrix(int cols, int rows) {
		CellLimits lim[][] = new CellLimits[rows][cols];
    	for(int r=0; r<rows;r++) {
    		for(int c=0; c<cols;c++) {
    			lim[r][c] = new CellLimits();
    		}
    	}
		return lim;
	}
	
	public void setAll() {
    	for(int r=0; r<numRows();r++) {
    		for(int c=0; c<numCols();c++) {
    			grid[r][c].setBottom();
    			grid[r][c].setRight();
    		}
    	}
		
	}
	
	public int numRows() {
		return grid.length;
	}
	
	public int numCols() {
		return grid[0].length;
	}
	
	//FIXME: use consistent cols/rows order.
	public Grid(int rows, int cols) {
		//grid = new CellLimits[rows][cols];
		grid = createMatrix(cols, rows);
	}
	
	public void setBottom(int row, int col) {
		grid[row][col].setBottom();
	}

	public void setBottom(int row, int col, boolean value) {
		grid[row][col].bottom = value;
	}
	

	public void setRight(int row, int col) {
		grid[row][col].setRight();
	}
	
	public void setRight(int row, int col, boolean value) {
		grid[row][col].right = value;
	}
	
	public boolean getRight(int row, int col) {
		return grid[row][col].right;
	}

	public boolean getBottom(int row, int col) {
		return grid[row][col].bottom;
	}

	Cell getMaxCell(int row, int col) {
		return getMaxCell(grid, row, col);
	}
	
	/*TODO: This static method doesn't look like a good design. 
	 * Maybe another class that encapsulates CellLimits matrix operations is desirable.
	 */
	static Cell getMaxCell(CellLimits matrix[][], int row, int col) {
		int c=col;
		int r=row;
		int colSpan,rowSpan;
		int lastCol;
		boolean lineFound = false;
		boolean vertLine = false;
		
		while(c < matrix[0].length && !vertLine) {
			lineFound |= matrix[row][c].bottom;
			vertLine |= matrix[row][c].right;
			c++;
		}
		lastCol = c;
		colSpan = c-col;
		r++;

		while(r < matrix.length && !lineFound) {
			lineFound = false;
			vertLine = false;
			c = col;			
			while(c < lastCol  && !vertLine) {
				lineFound |= matrix[r][c].bottom;
				vertLine |= matrix[r][c].right;
				c++;
			}
			if(c < lastCol) lineFound = true;
			r++;
		}

		rowSpan = r-row;
		return new Cell(colSpan,rowSpan);

	}

}
