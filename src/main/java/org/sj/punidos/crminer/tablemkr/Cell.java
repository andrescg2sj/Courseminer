package org.sj.punidos.crminer.tablemkr;

import java.util.Vector;

import org.sj.punidos.crminer.sectorizer.GraphicString;

public class Cell {
	

	int horizSpan = 1;
	int vertSpan = 1;
	
	//TODO: What class?
	Vector<GraphicString> contents;
	//String content;
	
	public Cell(int hspan, int vspan) {
		horizSpan = hspan;
		vertSpan = vspan;
		contents = new Vector<GraphicString>();
	}
	
	public void add(GraphicString gstr) {
		contents.add(gstr);
	}
	
    void addAll(Vector<GraphicString> cont)
    {
    	/*
    	if(this.contains(gstr.getBounds()))
    		content.add(gstr);
    		*/
    	if(cont == null) {
    		System.err.println("Warning! Trying to add null content");
    	} else {
    		contents = cont;
    	}
    }
    
    public int numStrings() {
    	return contents.size();
    }
    
    public String fullText() {
    	String t = "";
    	for(GraphicString gs : contents) {
    		t += gs.getText();
    	}
    	return t;
    }

	
	public String getString(int i) {
		if(i>=contents.size()) throw new ArrayIndexOutOfBoundsException("getString: i="+i);
		GraphicString gstr = contents.get(i);
		if(gstr.getText() == null)
			throw new NullPointerException("GStr has null text. i="+i);
		return contents.get(i).getText();
	}
	
	
	
}
