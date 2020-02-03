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
 

package org.sj.punidos.crminer.sectorizer;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import org.sj.punidos.crminer.CommonInfo;

//import org.sj.punidos.crminer.omio.pdfboximpl.beta.PDFString;

public class RegionCluster implements CommonInfo {
	
	Vector<StringRegion> regions;
	Vector<GraphicString> remaining; 
	
	public RegionCluster() {
		regions = new Vector<StringRegion>();
		remaining = new Vector<GraphicString>();
	}
	
	public void push(GraphicString gs) {
		StringRegion selected = null;
		for(StringRegion r: regions) {
			if(r.contains(gs)) {
				if(selected == null || selected.contains(r)) {
					selected = r;
				}
			}
		}
		if(selected != null) {
			selected.add(gs);
		} else {
			remaining.add(gs);
		}
	}
	
	
	public void pushRegion(Rectangle2D r) {
		StringRegion cr = new StringRegion(r);
		pushRegion(cr);
	}
	
	public void pushRegion(StringRegion cr) {
		// TODO: optimize
		for(GraphicString s: remaining) {
			if(cr.contains(s)) {
				cr.add(s);
				remaining.remove(s);
			}
		}
		regions.add(cr);
	}
	
	public int getNumberOfRegions()
	{
		return regions.size();
	}

	public StringRegion getRegion(int i)
	{
		return regions.get(i);
	}
	

	
	
	//TODO: better name
	public static boolean previous(StringRegion a, StringRegion b) {
		return (a.getPosition().getY() < b.getPosition().getY()) ||
				(a.getPosition().getY() == b.getPosition().getY()) &&
				(a.getPosition().getX() > b.getPosition().getX());
	
	}

	/*
	public void filterOutEmptyRegions() 
	{
		int i = 0;
		while(i < regions.size()) {
			ContentRegion r = regions.get(i);
			if(r.isEmpty()) {
				regions.removeElementAt(i);
			} else {
				i++;
			}
		}
	}*/

	public void filterOutEmptyRegions() 
	{
		ListIterator<StringRegion> it = regions.listIterator();
		while(it.hasNext()) {
			StringRegion r = it.next();
			if(r.isEmpty()) {
				it.remove();
			}
		}
	}

	
	public static <E> void swap(Vector<E> v, int i, int j) {
		E s = v.get(i);
		v.set(i, v.get(j));
		v.set(j, s);
	}
	
	public void sortRegions() {
		//TODO: See https://stackoverflow.com/questions/2072032/what-function-can-be-used-to-sort-a-vector
		// See Comparable, Comparator
		
		/* bubble algorithm. Low efficiency, but acceptable for this purpose */
		for(int i=0; i< regions.size(); i++) {
			StringRegion r = regions.get(i);
			for(int j=i+1; j<regions.size();j++) {
				StringRegion s = regions.get(j);
					
				if(previous(r,s)) {
					//FIXME: Is this clear enough? Good design?
					swap(regions,i,j);
					r = regions.get(i);
				}
			}
			
		}
		
	}
	
	public List<String> allStrings() {
		List<String> all = new LinkedList<String>();
		for(StringRegion r: regions) {
			all.addAll(r.getStrings());
		}
		return all;
	}
	
	public String regionToHTML(StringRegion cr) {
		StringBuilder s = new StringBuilder();
		s.append("<div style=\"border-style: solid;\">\n");
		for(GraphicString g: cr.getGraphicStrings()) {
			s.append(g.getText()+"<br/>\n");
		}
		
		s.append("</div>\n");
		
		return s.toString();
	}
	
	public String toHTML() {
		filterOutEmptyRegions();
		sortRegions();
		StringBuilder s = new StringBuilder();
		System.out.println("Regions: "+ regions.size());
		for(StringRegion r: regions) {
			s.append(regionToHTML(r));
		}

		System.out.println("Remainin: "+ remaining.size());
		for(GraphicString gs: remaining) {
			s.append("<p>"+gs.getText()+"</p>"+ NEW_LINE);
		}
		return s.toString();
	}
	
	public Rectangle2D getBounds() {
		if(regions.size() == 0)
			return null;
		Rectangle2D r = regions.get(0).getBounds();
		for(int i=1;i<regions.size();i++) {
			r.add(regions.get(i).getBounds());
		}
		return r;
	}
	
	public void meltRegions() {
		int i = 0;
		while(i < regions.size()) {
			meltWithFollowing(i);
			i++;
		}
	}
	
	public void meltWithFollowing(int i) {
		StringRegion r = regions.get(i);
		int j = i+1;
		while(j < regions.size()) {
			StringRegion s = regions.get(j);
			if(r.intersects(s)) {
				r.add(s);
				regions.remove(j);
			} else {
				j++;
			}
		}
	}
	
	public void remainingToRegions()
	{
		for(GraphicString gs: remaining) {
			//TODO
		}

	}
	
	public RegionCluster partitionRemaining()
	{
		
		RegionCluster cluster = new RegionCluster();
		//TODO: add all
		cluster.remainingToRegions();
		
		//TODO: 
		
		/* distribute remaining */
		Iterator<GraphicString> it = remaining.iterator();
		while(it.hasNext()) {
			GraphicString gs = it.next();
			cluster.push(gs);
			it.remove();
		}
		return cluster;
	}
	
	


}
