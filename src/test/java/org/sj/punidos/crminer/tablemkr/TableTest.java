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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TableTest {
	
	@Test
	public void testingFillCells()
	{
		Table table = new Table(3,3);
		
		Cell cell = new Cell(2,2);
		
		table.fillCells(cell, 0, 0);
		
		assertEquals("Corner ", cell, table.get(0, 0));
		assertTrue("0,1 ", table.get(0, 1) instanceof HiddenCell);
		assertTrue("1,1 ", table.get(1, 1) instanceof HiddenCell);
		assertTrue("1,0 ", table.get(1, 0) instanceof HiddenCell);

	}
}
