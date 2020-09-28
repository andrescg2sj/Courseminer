package org.sj.punidos.crminer.courses;

import java.io.IOException;
import java.io.Writer;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

public class CourseCsvWriter extends CourseSheetWriter {
	
			
	Writer writer;
	
	public CourseCsvWriter(Writer w) {
		writer = w;
	}
	
	
	public String toCsvLine(Course c) {
		//Vector<String> data = new Vector<String>(PATTERN.length);
		
		Vector<String> data = formatVector(c);
		Vector<String> quoted = new Vector<String>(data.size());
		
		for(String d : data) {
			quoted.add('"'+ d+ '"');
		}
		
		return String.join(",", quoted);
	}
	
	public void write(Course c) throws IOException {
		String line = toCsvLine(c);
		//TODO: write
		writer.write(line+"\r\n");
	}
	
	public void close() throws IOException {
		writer.close();
	}



	
}
