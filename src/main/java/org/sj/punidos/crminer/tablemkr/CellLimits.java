package org.sj.punidos.crminer.tablemkr;

class CellLimits {
	boolean bottom, right;
	
	public CellLimits() {
		bottom = false;
		right = false;
	}
	
	public String toString() {
		return (bottom? "_":" ") + (right?"|":" ");
	}
	
	void setBottom() {
		bottom = true;
	}
	
	void setRight() {
		right = true;
	}
	
	

}
