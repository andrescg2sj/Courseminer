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
