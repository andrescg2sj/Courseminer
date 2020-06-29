package org.sj.punidos.crminer.courses;

import java.io.IOException;
import java.io.Writer;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

public class CourseCsvWriter implements CourseWriter {
	
	String pattern[] = "period,entity,name,category,subcategory,addressee,ReqAdd,register,CatReg,price,CatDurat,duration,date,CatSched,schedule,ContAddr,ContTransp,ContTel,ContInternet".split(","); 
			
	Writer writer;
	
	public CourseCsvWriter(Writer w) {
		writer = w;
	}
	
	HashMap<String,String> toDict(Course c) {
		HashMap<String,String> dict = new HashMap<String,String>();
		dict.put("entity", c.entity);
		dict.put("category", c.category);
		dict.put("name", c.name);
		dict.put("schedule", c.hours);
		dict.put("register", c.register);
		dict.put("date", c.date);

		return dict;
	}
	
	public String toCsvLine(Course c) {
		Vector<String> data = new Vector<String>(pattern.length);
		
		HashMap<String,String> dict = toDict(c);
		
		for(String key : pattern) {
			if(dict.containsKey(key)) {
				data.add('"' + dict.get(key) + '"');
			} else {
				data.add("\"\"");
			}
		}
		return String.join(",", data);
	}
	
	public void write(Course c) throws IOException {
		String line = toCsvLine(c);
		//TODO: write
		writer.write(line+"\r\n");
	}
	
	public void close() throws IOException {
		writer.close();
	}


	public void write(Collection<Course> courses) throws IOException {
		for(Course c: courses) {
			write(c);
		}
	}

	
}
