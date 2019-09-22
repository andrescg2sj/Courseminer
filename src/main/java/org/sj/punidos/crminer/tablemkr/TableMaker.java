
package org.sj.punidos.crminer.tablemkr;


import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Vector;

import org.sj.punidos.crminer.sectorizer.GraphicString;

public class TableMaker
{

    float collisionThreshold = 2;
    
    Vector<Line> lines;
    Vector<GraphicString> gstrings;

    public TableMaker() {
    	lines = new Vector<Line>();
    	gstrings = new Vector<GraphicString>();
       
    }


    private void logLines(Line lines[]) {
    	System.out.println("lines:" + lines.length);
    	for(Line l: lines) {
    		System.out.println(l.toString());
    	}
    }
    
    public void add(Line line) {
    	lines.add(line);
    }
    
    public void add(GraphicString gstr) {
    	gstrings.add(gstr);
    }
    
    
    private Frame getMarks() {
    	/* convenience array that stores all marks */
    	int i=0;
    	int j = lines.size()-1;
    	int allMarks[] = new int[lines.size()];
    	for(Line l: lines) {
    		if(l.isHoriz()) {
    			System.out.println(l.toString() + " is horziontal.");
    			allMarks[i++] = (int) l.getA().getY();
    			
    		} else {
    			System.out.println(l.toString() + " is vertical.");
    			allMarks[j--] = (int) l.getA().getX();
    		}
    	}
    	//int x[] = new int[i];
    	//int y[] = new int[allMarks.length - i];
    	System.out.println(String.format("lines.size=%d, i=%d, j=%d", lines.size(), i,j));
    	System.out.println(String.format("marks.length=%d", allMarks.length));
    	int x[] = Arrays.copyOfRange(allMarks, i, allMarks.length);
    	if(x.length != j) {
    		System.err.println("Warning! x.length="+x.length);
    	}
    	int y[] = Arrays.copyOf(allMarks, i);
    	Arrays.sort(x);
    	Arrays.sort(y);
    	
    	return new Frame(x,y);
    }
    
    public Table makeTable()
    {
    	Vector<Area> areas = buildAreas();
    	return areasToTable(areas);
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
    
    boolean addStringToOneArea(Vector<Area> areas, GraphicString gstr) {
		for(Area a: areas) {
			if(a.contains(gstr.getBounds())) {
				a.addContent(gstr);
				return true;
			}
		}
    	return false;
    }
    
    void addStringsToAreas(Vector<Area> areas) {
    	int count = 0;
    	for(GraphicString gstr: this.gstrings)
    	{
    		if(addStringToOneArea(areas, gstr)) count++;
    	}
    	System.out.println("added "+count+ " strings");
    }
    
    
    public Table areasToTable(Vector<Area> areas)
    {
    	Frame frame = getMarks();
    	
    	int cols = frame.x.length - 1;
    	int rows = frame.y.length - 1;
    	System.out.println(String.format("cols=%d rows=%d",cols, rows));
    	

    	System.out.println("Adding Strings...");
    	addStringsToAreas(areas);
    	
    	Cell table[][] = new Cell[rows][cols];
    	//CellLocation loctable[][] = new CellLocation[rows][cols];

    	
    	for(Area a: areas) {
			System.out.println("Build location: " + a.toString());
    		CellLocation clo = frame.areaToCellLoc(a, this.collisionThreshold);
    		if(clo == null)
    			throw new NullPointerException("Frame created a null CellLocation");
    		try {
				System.out.println("Testing:" + clo.row+","+clo.col);
    			if(table[clo.row][clo.col] != null) {
    				System.out.println("Warning! repeated.");
    			} else {
    				System.out.println("cell: "+clo.toString());
    				System.out.println("   indices:" + clo.row+","+clo.col);
    				table[clo.row][clo.col] = clo.cell;
    				//loctable[clo.row][clo.col] = clo;
    				/*fillSpan(loctable, clo, clo.col, clo.row,
    						clo.cell.horizSpan, clo.cell.vertSpan);*/
    				
    			}
    		} catch(Exception ie) {
    			ie.printStackTrace();
    		}
    	}
    	
    	
    	//return new Table(locToCells(loctable));
		System.out.println("Building table...");
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
     * 
     * For debug purposes.
     * 
     * @param areas
     */
    public String toSVG(Vector<Area> areas) {
    	String s = "<svg>";
    	for(Area a: areas) {
    		s += toSVG(a);
    	}
    	return s+"</svg>";
    }
    
    public String toSVG(Area area) {
    	Rectangle2D rect = area.getBounds();
    	return String.format("<rect x=\"%f\" y=\"%f\" width=\"%f\" height=\"%f\" />", 
    			rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
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

    public static void main(String arg[]) {
	System.out.println("Hello World Table Maker");

	Line strokes[] = new Line[5];

	strokes[0] = new Line(10,10,200,10);
	strokes[1] = new Line(10,10,200,10);
	strokes[2] = new Line(200,10,200,100);
	strokes[3] = new Line(10,100,200,100);
	strokes[4] = new Line(50,10,50,100);

	TableMaker maker = new TableMaker();
	Vector<Area> regions = maker.buildAreas(strokes);
	System.out.println("Regions:");
	show(regions);
	
    }
}
