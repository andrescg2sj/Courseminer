package org.sj.punidos.crminer.sectorizer;

import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

public class PosRegionCluster<E extends Positionable>  {
	
	Vector<ContentRegion<E>> regions;
	Vector<E> remaining; 
	
	public PosRegionCluster() {
		regions = new Vector<ContentRegion<E>>();
		remaining = new Vector<E>();
	}
	
	
	public void push(E gs) {
		ContentRegion<E> selected = null;
		for(ContentRegion<E> r: regions) {
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
		//ContentRegion<E><E> cr = new ContentRegion<E><E>(r);
		ContentRegion<E> cr = new ContentRegion<E>(r);
		pushRegion(cr);
	}
	
	public void pushRegion(ContentRegion<E> cr) {
		// TODO: optimize
		for(E s: remaining) {
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

	public ContentRegion<E> getRegion(int i)
	{
		return regions.get(i);
	}
	
	public int countRemaining()
	{
		return remaining.size();
	}

	
	
	//TODO: better name
	public static boolean previousPos(Positionable a, Positionable b) {
		return (a.getPosition().getY() < b.getPosition().getY()) ||
				(a.getPosition().getY() == b.getPosition().getY()) &&
				(a.getPosition().getX() < b.getPosition().getX());
	
	}

	/*
	public void filterOutEmptyRegions() 
	{
		int i = 0;
		while(i < regions.size()) {
			ContentRegion<E> r = regions.get(i);
			if(r.isEmpty()) {
				regions.removeElementAt(i);
			} else {
				i++;
			}
		}
	}*/

	public void filterOutEmptyRegions() 
	{
		ListIterator<ContentRegion<E>> it = regions.listIterator();
		while(it.hasNext()) {
			ContentRegion<E> r = it.next();
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
	
	public void sortRegions()
	{
		sortRegions(false);
	}
	
	public void sortRegions(boolean reverse) {
		//TODO: See https://stackoverflow.com/questions/2072032/what-function-can-be-used-to-sort-a-vector
		// See Comparable, Comparator
		
		/* bubble algorithm. Low efficiency, but acceptable for this purpose */
		for(int i=0; i< regions.size(); i++) {
			
			ContentRegion<E> r = regions.get(i);
			
			for(int j=i+1; j<regions.size();j++) {
				ContentRegion<E> s = regions.get(j);
	
				//TODO: possible useless swaps when reverse sorting.
				if(previousPos(s,r) ^ reverse) {
					//FIXME: Is this clear enough? Good design?
					swap(regions,i,j);
					r = regions.get(i);
				}
			}
			
		}
		
	}
	
	
	
	
	/*
	private boolean previous(ContentRegion<E> r, ContentRegion<E> s) {
		ContentRegion<Positionable> a = (ContentRegion<Positionable>) r;
		ContentRegion<Positionable> b = s;
		return previousPos(a,b);
	}*/

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
		System.out.println("mwf:"+i);
		ContentRegion<E> r = regions.get(i);
		int j = i+1;
		while(j < regions.size()) {
			ContentRegion<E> s = regions.get(j);
			if(r.intersects(s)) {
				System.out.println("  inters:"+j);
				r.add(s);
				regions.remove(j);
			} else {
				System.out.println("  no inter:"+j);
				j++;
			}
		}
	}
	
	
	public void remainingToRegions(double threshold)
	{
		Vector<ContentRegion<E>> newRegions = new Vector<ContentRegion<E>>();
		for(E obj: remaining) {
			newRegions.add(new ContentRegion<E>(obj,threshold));
			//this.pushRegion();
		}
		remaining.clear();
		regions.addAll(newRegions);
	}
	
	public void partitionContent(double threshold)
	{
		partitionContent(threshold, false);
	}

	
	public void partitionContent(double threshold, boolean reverse)
	{
		remainingToRegions(threshold);
		sortRegions(reverse);
		System.out.println("Sorted: ");
		for(ContentRegion<E> cr: regions) {
			System.out.println("  "+cr.getBounds());
		}
		meltRegions();
	}
	
	


}
