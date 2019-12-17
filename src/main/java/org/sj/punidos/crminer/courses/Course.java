package org.sj.punidos.crminer.courses;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Course {
	String name;
	String date;
	String hours;
	LocalTime start;
	LocalTime end;
	String register;
	
	private static String nullProtect(String in) {
		if(in==null) return "";
		return in;
	}
	
	public Course(String _name, String _date, String _hours) {
		this.name = _name;
		this.date = _date;
		if(_hours != null) {
			this.hours = _hours.trim();
			parseHours();
		}
	}

	
	public Course(String _name, String _date, String _hours, String _register) {
		this.name = _name;
		this.date = _date;
		if(_hours != null) {
			this.hours = _hours.trim();
			parseHours();
		}
		this.register = _register;
	}
	
	private void parseHours() {
		try {
			String parts[] = hours.split("-");
			start = LocalTime.parse(parts[0]);
			end = LocalTime.parse(parts[1]);
		} catch(DateTimeParseException e) {
			
		}
	}
	
	public String[] getData() {
		String data[] = new String[4];
		data[0] = name;
		data[1] = date;
		data[2] = hours;
		data[3] = register;
		return data;
 	}
	
	public String toString() {
		return "course("+ name+","+date+","+hours+","+register+")";
	}

}
