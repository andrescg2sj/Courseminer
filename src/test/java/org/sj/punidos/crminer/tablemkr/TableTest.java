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
import org.junit.Assert;

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
	
	@Test
	public void testingAdd() 
	{
		Table t = new Table(4,4);
		t.add(1, 1, "a");
		Assert.assertEquals("a", t.get(1, 1).fullText());
	}
	
	@Test
	public void testingSubTable() 
	{
		Table t = new Table(4,4);
		t.add(1, 1, "a");
		
		Table u = t.subTable(1, 1, 2, 2);
		Assert.assertEquals("cols", 2, u.getCols());
		Assert.assertEquals("rowss", 2, u.getRows());
		Assert.assertEquals("content 0,0", "a", u.getCell(0, 0).fullText());
		
		
	}
	
	@Test
	public void testingTableTrim() 
	{
		Table t = new Table(4,4);
		t.add(1, 1, "a");
		t.add(1, 2, "b");
		t.add(2, 1, "c");
		t.add(2, 2, "d");
		
		Table u = t.trim();
		Assert.assertEquals("cols", 2, u.getCols());
		Assert.assertEquals("rowss", 2, u.getRows());
		Assert.assertEquals("content 0,0", "a", u.getCell(0, 0).fullText());
		
		
		
	}

	
}
