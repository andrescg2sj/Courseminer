package org.sj.punidos.crminer.tablemkr;

import java.util.Vector;

public class Table {

	//Vector<Area> areas;
	Cell contents[][];
	
	public Table(Cell cont[][]) {
		contents = cont;
	}
	
	public int getRows() {
		return contents.length;
	}
	
	public int getCols() {
		return contents[0].length;
	}
	
	public Cell getCell(int col, int row) {
		return contents[row][col];
	}
	

	public String toHTML() {
		String data = "<table style=\"\">";
		for(int i=0; i<contents.length;i++) {
			data += "<tr>" ;
			for(int j=0; j<contents[i].length;j++) {
				if(contents[i][j] != null) {
					Cell cell = contents[i][j];
					data += "<td>" + cell.fullText() +"</td>";
				}
			}
			data += "</tr>" ;
		}
		return data + "</table>";
		//throw new UnsupportedOperationException("Table.toHTML");
	}
}
