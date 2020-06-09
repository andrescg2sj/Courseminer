package org.sj.punidos.crminer.sectorizer;

import java.util.Comparator;

public class ReverseYComparator implements Comparator<Positionable> {
	
	private static ReverseYComparator instance;
	
	private ReverseYComparator() {
	}
	
	public static ReverseYComparator getInstance() {
		if(instance == null) {
			instance = new ReverseYComparator();
		}
		return instance;
	}


	@Override
	public int compare(Positionable a, Positionable b) {
		if (a.getPosition().getY() < b.getPosition().getY()) {
			return 1;
		}else if (a.getPosition().getY() > b.getPosition().getY()) {
			return -1;
		} else {
			if (a.getPosition().getX() < b.getPosition().getX()) {
				return -1;
			} else if (a.getPosition().getX() > b.getPosition().getX()) {
				return 1;
			} 
			return 0;
		}
	}
	

}
=======
package org.sj.punidos.crminer.sectorizer;

import java.util.Comparator;

public class ReverseYComparator implements Comparator<Positionable> {
	
	private static ReverseYComparator instance;
	
	private ReverseYComparator() {
	}
	
	public static ReverseYComparator getInstance() {
		if(instance == null) {
			instance = new ReverseYComparator();
		}
		return instance;
	}


	@Override
	public int compare(Positionable a, Positionable b) {
		if (a.getPosition().getY() < b.getPosition().getY()) {
			return 1;
		}else if (a.getPosition().getY() > b.getPosition().getY()) {
			return -1;
		} else {
			if (a.getPosition().getX() < b.getPosition().getX()) {
				return -1;
			} else if (a.getPosition().getX() > b.getPosition().getX()) {
				return 1;
			} 
			return 0;
		}
	}
	

}
>>>>>>> 18c9f66dcb13ae633dae5ca0fd69decdf6e3788a
