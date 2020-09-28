package org.sj.punidos.crminer.courses;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

public abstract class CourseSheetWriter implements CourseWriter {

	public static String PATTERN[] = "period,entity,name,category,subcategory,addressee,ReqAdd,register,CatReg,price,CatDurat,duration,date,CatSched,schedule,ContAddr,ContTransp,ContTel,ContInternet".split(",");
	
	public HashMap<String,String> toDict(Course c) {
		HashMap<String,String> dict = new HashMap<String,String>();
		dict.put("entity", c.entity);
		dict.put("category", c.category);
		dict.put("name", c.name);
		dict.put("schedule", c.hours);
		dict.put("register", c.register);
		dict.put("date", c.date);

		return dict;
	}

	
	public Vector<String> formatVector(Course c) {
		Vector<String> data = new Vector<String>(PATTERN.length);
		
		HashMap<String,String> dict = toDict(c);
		
		for(String key : PATTERN) {
			if(dict.containsKey(key)) {
				data.add(dict.get(key));
			} else {
				data.add("");
			}
		}

		return data;
	}
	
	public void write(Collection<Course> courses) throws IOException {
		for(Course c: courses) {
			write(c);
		}
	}

}
