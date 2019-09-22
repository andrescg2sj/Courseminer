package org.sj.punidos.crminer.sectorizer;

import java.util.Vector;

import java.awt.Point;

public class RegionSorter {

	static void swap(Vector<Positionable> regs, int a, int b) {
		Positionable temp;
		temp = regs.get(a);
		regs.set(a, regs.get(b));
		regs.set(b,temp);
	}
	
	static boolean greater(Positionable a, Positionable b) {
		return greater(a.getPosition(), b.getPosition());
	}
	
	static boolean greater(Point a, Point b) {
		if(a.getY() > b.getY()) return true;
		else if(a.getY() == b.getY()) {
			if(a.getX() > b.getX()) return true;
		}
		return false;
	}

	static boolean lowerOrEqual(Positionable a, Positionable b) {
		return greater(b,a);
	}
	
	static void qsort(Vector<Positionable> regs, int left, int right) {
		/*
		 * Ref:
		 * http://puntocomnoesunlenguaje.blogspot.com/2012/12/java-quicksort.html
		 */
		Positionable pivot = regs.get(left);
		int i = left;
		int j = right;
		
		while(i<j) {
			while(lowerOrEqual(regs.get(i),pivot) && i<j) i++;
			while(greater(regs.get(j),pivot)) j--;
			
			if(i<j) {
				swap(regs, i,j);
			}
		}
		regs.set(left, regs.get(j));
		regs.set(j,pivot);
		
		if(left<(j-1))
			qsort(regs, left,j-1);
		if((j+1)>right)
			qsort(regs,j+1,right);
	}
	
	public static void sortRegions(Vector<Positionable> regs) {
		//throw new UnsupportedOperationException("sortRegions");
		qsort(regs, 0, regs.size());
	}
}
