package org.sj.punidos.crminer.tablemkr;

public class GridTableMaker extends TableMaker {

	public GridTableMaker()
	{
		
	}
	
	public Frame buildFrame() {
		// TODO
		throw new UnsupportedOperationException("Grid.buildFrame");
	}
	
	public Table makeTable() {
		
		//make frame from lines
		Frame f = buildFrame();

		// volver a recorrer. Rellenar CellBorders
		for(Line l: lines) {
			/*if(LineDir.isVertical(l)) {
			} else {
			}*/
			
		}

		return null;
	}
}
