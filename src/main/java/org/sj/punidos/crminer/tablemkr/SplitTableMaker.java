package org.sj.punidos.crminer.tablemkr;

import java.util.Arrays;
import java.util.Vector;

import org.sj.punidos.crminer.sectorizer.GraphicString;
import org.sj.punidos.crminer.tablemkr.graphtrace.SvgTrace;

public class SplitTableMaker extends TableMaker {


    public SplitTableMaker() {
    	super();
    }


    private void logLines(Line lines[]) {
    	System.out.println("lines:" + lines.length);
    	for(Line l: lines) {
    		System.out.println("   "+l.toString());
    	}
    }
    
    
    public Table makeTable()
    {
    	Vector<Area> areas = buildAreas();
    	return areasToTable(areas);
    }
    
    public Table MakeTable_impl2()
    {
    	
    	return null;
    }
    
    public static void fillSpan(CellLocation ltable[][], CellLocation value,
    		int col, int row, int hspan, int vspan)
    {
    	
    	for(int i=row;i<=(row+hspan); i++) {
        	for(int j=col;j<=(col+vspan); j++) {
        		try {
        			ltable[i][j] = value;
        		} catch(ArrayIndexOutOfBoundsException ie) {
        			System.err.println("Index exception: i="+i+", j="+j);
        			System.err.println(" hspan="+hspan+", vspan="+vspan);
        		}
        	}
    	}
    	
    }
    
    static Cell[][] locToCells(CellLocation loct[][])
    {
    	Cell table[][] = new Cell[loct.length][loct[0].length];
    	for(int i=0; i<loct.length;i++) {
    		for(int j=0; j<loct[0].length;j++) {
    			table[i][j] = loct[i][j].cell;
    		}
    	}
    	return table;
    }
    
    
    public Table areasToTable(Vector<Area> areas)
    {
    	frame = buildFrame();
    	
    	int cols = frame.x.length - 1;
    	int rows = frame.y.length - 1;
    	System.out.println(String.format("cols=%d rows=%d",cols, rows));
    	

    	System.out.println("Adding Strings...");
    	addStringsToAreas(areas);
    	
    	Cell table[][] = new Cell[rows][cols];
    	//CellLocation loctable[][] = new CellLocation[rows][cols];

    	addAreasToMatrix(areas, table);
    	
    	return new Table(table);
    }
    
    
    
   public Vector<Area> buildAreas() {
    	Line lineArray[] = 	lines.toArray(new Line[lines.size()]);
    	return buildAreas(lineArray);
    }

    public void reset() {
    	if(lines.size() > 0)
    		lines = new Vector<Line>();
    }
    
    
    public Vector<Area> buildAreas(Line lineArray[]) {
    	//TODO: manage bad tables
    	Area fullArea = new RectArea(Area.getMaximumRect(lineArray));
    	System.out.println("FullArea:");
    	System.out.println(fullArea);

    	//TODO: remove. Not necessary.
    	//lineArray = removeBounds(fullArea, lineArray);
    	//System.out.println("Without boundaries:");
    	logLines(lineArray);

    	Vector<Area> areas = new Vector<Area>();
    	
    	

    	areas.add(fullArea);

    	Vector<Line> lines = new Vector<Line>(Arrays.asList(lineArray));
    	
    	//TODO: Is this algorithm optimal? Is there any redundancy?
    	while(lines.size() > 0) {
    		
    		for(int i=0; i<lines.size(); i++) {
    			int hits = 0;
				Line l = lines.get(i);
	    	    for(int j=0;j<areas.size(); j++) {
	    	    	Area a = areas.get(j);

    				if(a.collision(l, collisionThreshold)) {
    					Area parts[] = a.split(l);
    					areas.remove(j);
    					areas.addAll(Arrays.asList(parts));
    					hits++;
    				}
    				    				
    			}
    			if(hits == 0)
    				lines.remove(l);
    		}
    	}

    	
    	System.out.println("returning areas: "+ areas.size());
    	return areas;
    }


    public Vector<Area> buildAreas_old(Line lineArray[]) {
    	//TODO: manage bad tables
    	Area fullArea = new RectArea(Area.getMaximumRect(lineArray));
    	System.out.println("FullArea:");
    	System.out.println(fullArea);

    	//TODO: remove. Not necessary.
    	//lineArray = removeBounds(fullArea, lineArray);
    	//System.out.println("Without boundaries:");
    	logLines(lineArray);

    	Vector<Area> areas = new Vector<Area>();

    	areas.add(fullArea);

    	
    	//TODO: Refactor algorithm. Remove lines that don't collide with any Rectangle. 
    	for(Line l: lineArray) {
    	    //Vector<Area> newAreas = new Vector<Area>();
    	    
    	    for(int i=0;i<areas.size(); i++) {
	    		Area a = areas.get(i);
	    		if(a.collision(l, collisionThreshold)) {
	    		    Area parts[] = a.split(l);
	
	    		    // this should not happen, if collision does its job.
	    		    if(parts == null)
	    			throw new NullPointerException("Failed to split area");
	
	    		    /* remove area */
	    		    areas.remove(i);
	    		    
	    		    /* add parts */
	    		    for(Area p: parts) {
	    		    	areas.add(p);
	    		    }
	    		}
    	    }
    	    //areas.addAll(newAreas);
    	}
    	System.out.println("returning areas: "+ areas.size());
    	return areas;
    }
    
    /**
     * 
     * For debug purposes.
     * 
     * @param areas
     */
    public void logAreas(Vector<Area> areas) {
    	System.out.println("Areas: "+ areas.size());
    	for(org.sj.punidos.crminer.tablemkr.Area a: areas) {
    		System.out.println(a.toString());
    	}
    }




    /**
     * Remove bounding lines of 'a' in array
     */
    public Line[] removeBounds(Area a, Line lines[]) {
    	Vector<Line> out = new Vector<Line>(lines.length - 4);
    	for(Line l: lines) {
    		if(!a.outOrBound(l))
    			out.add(l);
    	}
    	return out.toArray(new Line[out.size()]);
    }

    
    
    public static void show(Vector<Area> parts) {
    	for(Area a: parts) {
    		System.out.println(a.toString());
    	}
    }


}
