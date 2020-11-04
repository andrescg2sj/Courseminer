package org.sj.punidos.crminer.cepi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Vector;

import org.sj.punidos.crminer.courses.Course;
import org.sj.tools.graphics.tablemkr.Table;
import org.junit.Test;

public class CourseFactoryTest {
	

	public Vector<Table> oneElementVector(Table t) {
		Vector<Table> v = new Vector<Table>(1);
		v.add(t);
		return v;
	}
	
	
	@Test
	public void testingReadRow() {
		Table t = Table.parseTable("a,b,c,d");
		CourseFactory cf = new CourseFactory("Centro", oneElementVector(t));
		Vector<String> data = cf.readRow(t, 0);
		assertEquals("first", "a", data.get(0));
		assertEquals("first", "b", data.get(1));
		assertEquals("first", "c", data.get(2));
		assertEquals("first", "d", data.get(3));
	}
	
	@Test
	public void testingProcessTable() {
		Table t = Table.parseTable("Curso,Fecha,Horario,Inscripción;"+
				"Modulo1,martes,20,lunes;"+
				"Modulo2,Miercoles,18,lunes;"+
				"Modulo3,Jueves,10,lunes"
				);
		CourseFactory cf = new CourseFactory("Centro", oneElementVector(t));
		List<Course> courses = cf.processTable(t);
		assertEquals("number of courses", 3, courses.size());
		assertEquals("Name. Course 1", "Modulo1", courses.get(0).getName());
		assertEquals("Name. Course 1", "Modulo3", courses.get(2).getName());
	}
	
	

}
