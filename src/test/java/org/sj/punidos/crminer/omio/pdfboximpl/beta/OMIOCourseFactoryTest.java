package org.sj.punidos.crminer.omio.pdfboximpl.beta;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sj.punidos.crminer.omio.pdfboximpl.beta.OMIOCourseFactory;


public class OMIOCourseFactoryTest {

	@Test
	public void testingIsHourData() {
		String hour = "12:01";
		String other = "12asb:01";
		
		OMIOCourseFactory factory = new OMIOCourseFactory();
		 
		assertTrue(factory.isHourData(hour));
		assertFalse(factory.isHourData(other));
	}
}
=======
package org.sj.punidos.crminer.omio.pdfboximpl.beta;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sj.punidos.crminer.omio.pdfboximpl.beta.OMIOCourseFactory;


public class OMIOCourseFactoryTest {

	@Test
	public void testingIsHourData() {
		String hour = "12:01";
		String other = "12asb:01";
		
		OMIOCourseFactory factory = new OMIOCourseFactory();
		 
		assertTrue(factory.isHourData(hour));
		assertFalse(factory.isHourData(other));
	}
}
>>>>>>> 18c9f66dcb13ae633dae5ca0fd69decdf6e3788a
