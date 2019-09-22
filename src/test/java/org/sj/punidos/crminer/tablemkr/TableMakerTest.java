package org.sj.punidos.crminer.tablemkr;

import static org.junit.Assert.assertEquals;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import org.sj.punidos.crminer.sectorizer.GraphicString;
import org.sj.punidos.crminer.tablemkr.Area;
import org.sj.punidos.crminer.tablemkr.Line;
import org.sj.punidos.crminer.tablemkr.RectArea;
import org.sj.punidos.crminer.tablemkr.Table;
import org.sj.punidos.crminer.tablemkr.TableMaker;
import org.junit.Test;

public class TableMakerTest {
	
	public static Line[] strokesExample1() {
		Line strokes[] = new Line[4];
		strokes[0] = new Line(10,10,200,10);
		strokes[1] = new Line(10,10,10,100);
		strokes[2] = new Line(200,10,200,100);
		strokes[3] = new Line(10,100,200,100);
		return strokes;

	}
	
	public static Line[] strokesExample2() {
		Line strokes[] = new Line[5];
		strokes[0] = new Line(10,10,200,10);
		strokes[1] = new Line(10,10,10,100);
		strokes[2] = new Line(200,10,200,100);
		strokes[3] = new Line(10,100,200,100);
		strokes[4] = new Line(50,10,50,100);
		return strokes;
	}
	
	public static Line[] badTable1() {
		Line strokes[] = new Line[4];
		strokes[0] = new Line(10,10,200,10);
		strokes[1] = new Line(10,10,200,10);
		strokes[2] = new Line(200,10,200,100);
		strokes[3] = new Line(10,100,200,100);
		return strokes;

	}


	
	@Test
	public void testingBuildAreas2() {
		Line strokes[] = strokesExample2();

		//Vector<Area> areas = new Vector<Area>();
		//areas.add(new RectArea(new Rectangle2D.Double(10,10,40,90)));
		//areas.add(new RectArea(new Rectangle2D.Double(50,10,150,90)));
		//TODO
	}

	
	@Test
	public void testingAreasToTable() {
		Line strokes[] = strokesExample2();

		Vector<Area> areas = new Vector<Area>();
		areas.add(new RectArea(new Rectangle2D.Double(10,10,40,90)));
		areas.add(new RectArea(new Rectangle2D.Double(50,10,150,90)));
	}
	

	@Test
	public void testingBuildAreas1() {
		
		Line strokes[] = strokesExample1();

		TableMaker maker = new TableMaker();
		
		for(Line s: strokes) {
			maker.add(s);
		}
		Vector<Area> areas = maker.buildAreas(strokes);
		//Table table = maker.makeTable();
		assertEquals("areas", 1, areas.size());
		//assertEquals("rows", 1,table.getRows());
		
	}
	
	@Test
	public void testingContent() {
		
		Line strokes[] = strokesExample2();
		TableMaker maker = new TableMaker();
		
		GraphicString s1 = new GraphicString("A", new Rectangle(15,20,15,20));
		GraphicString s2 = new GraphicString("B", new Rectangle(60,20,40,40));
		
		for(Line s: strokes) {
			maker.add(s);
		}
		maker.add(s1);
		maker.add(s2);
		//Vector<Area> areas = maker.buildAreas(strokes);
		Table table = maker.makeTable();
		
		//assertEquals("areas", 2, areas.size());
		assertEquals("rows", 1,table.getRows());
		assertEquals("cols", 2,table.getCols());
		assertEquals("Content(0,0)", "A", table.getCell(0, 0).getString(0));
		assertEquals("Content(1,0)", "B", table.getCell(1, 0).getString(0));
	}

	
	
	public void testTable(Line strokes[], int expectedCols, int expectedRows) {
		
		//Line strokes[] = strokesExample1();

		TableMaker maker = new TableMaker();
		
		for(Line s: strokes) {
			maker.add(s);
		}
		Vector<Area> areas = maker.buildAreas(strokes);
		Table table = maker.areasToTable(areas);
		
		assertEquals("cols", expectedCols, table.getCols());
		assertEquals("rows", expectedRows,table.getRows());
		
	}
	
	@Test
	public void testingMakeTable1() {
		
		Line strokes[] = strokesExample1();
		testTable(strokes, 1,1);
	}

	
	

	
	@Test
	public void testingMakeTable2() {
		
		Line strokes[] = strokesExample2();
		testTable(strokes, 2,1);
		
	}

    //TODO: make test for complex situations...
    /*
	@Test
	public void testingMakeBadTable1() {
		
		Line strokes[] = badTable1();
		testTable(strokes, 1,1);
	}
    */
}
