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
		String data = "<table>";
		for(int i=0; i<contents[0].length;i++) {
			data += "<tr>" ;
			for(int j=0; j<contents.length;j++) {
				data += "<td>"  +"</td>";
			}
		}
		return data + "</table>";
		//throw new UnsupportedOperationException("Table.toHTML");
	}
}
