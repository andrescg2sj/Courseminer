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

import org.junit.Test;

public class GridTableMakerTest {

	@Test
	public void testingMakeFromGrid() {
		Grid g = new Grid(2,2);
		g.setAll();
		
		Table t = GridTableMaker.makeFromGrid(g);
		assertEquals("full table. colSpan", 1,t.getCell(0, 0).colSpan);
		assertEquals("full table. rowSpan", 1,t.getCell(0, 0).rowSpan);
		
		g.setRight(0,0,false);
		assertEquals("right", false,g.getRight(0, 0));

		t = GridTableMaker.makeFromGrid(g);
		assertEquals("table. colSpan", 2,t.getCell(0, 0).colSpan);
		assertEquals("table. rowSpan", 1,t.getCell(0, 0).rowSpan);
		
	}
}
