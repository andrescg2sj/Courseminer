package org.sj.punidos.crminer.cepi;

public class Month {
	
	public static final String months[] = {"enero", "febrero","marzo","abril","mayo",
			"junio","julio","agosto","septiembre","octubre","noviembre","diciembre"};

	public static String getMonth(String str) {
		for(String month: months) {
			if(str.contains(month)) {
				return month;
			}
		}
		return "";
	}

}
