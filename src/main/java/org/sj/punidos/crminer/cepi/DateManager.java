package org.sj.punidos.crminer.cepi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateManager {
	
	String text;
	int year;
	int monthNum;
	
	public DateManager(String _text) {
		text = _text;
		year = getYear(text);
		String monthName = getMonth(text);
		monthNum = getMonthNumber(monthName);
	}
	
	public int getYear() {
		return year;
	}
	
	public int getMonthNum() {
		return monthNum;
	}
	
	
	public static final int NONE = -1;
	
	public static final String MONTHS[] = {"enero", "febrero","marzo","abril","mayo",
			"junio","julio","agosto","septiembre","octubre","noviembre","diciembre"};
	
	public static int getYear(String text) {
	      String pattern = "(\\d+)";

	      // Create a Pattern object
	      Pattern r = Pattern.compile(pattern);

	      // Now create matcher object.
	      Matcher m = r.matcher(text);
	      if (m.find( )) {
	    	  return Integer.parseInt(m.group(0));
	    	  
	      } 
	      throw new IllegalArgumentException("No year in string: '"+text+"'");
	}
	


	public static String getMonth(String str) {
		for(String month: MONTHS) {
			if(str.contains(month)) {
				return month;
			}
		}
		return "";
	}
	
	public static int getMonthNumber(String month) {
		for(int i=0; i<MONTHS.length; i++) {
			if(MONTHS[i].equals(month)) {
				return i+1;
			}
		}
		return NONE;
	}
	

}
