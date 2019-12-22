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
