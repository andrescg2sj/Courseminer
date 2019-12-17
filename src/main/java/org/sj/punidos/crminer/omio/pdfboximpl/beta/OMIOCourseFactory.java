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
 

package org.sj.punidos.crminer.omio.pdfboximpl.beta;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sj.punidos.crminer.courses.Course;

public class OMIOCourseFactory {
	
	public final static String TARGET = "Destinada a:"; 
	public final static String TIME = "time";
	public final static String GENERAL_DATA = "general";
	public final static String days[] = {"Lunes","Martes","Miércoles","Jueves",
			"Viernes","Sábado","Domingo"};
	public final static String CITE_MARKER ="cita previa";
	
	public final static String TIME_PATTERN_REGEX = "([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]";
	Pattern timep;
			

	public OMIOCourseFactory() {
		timep = Pattern.compile(TIME_PATTERN_REGEX);
		
	}
	
	public boolean isHourData(String s) {
		//Regex to find hours
		Matcher m = timep.matcher(s);
		return m.find();
	}
	
	public boolean isDayData(String s) {
		int dayChars = 0;
		for(String d: days) {
			if(s.contains(d)) {
				dayChars += d.length();
			}
		}
		// True if most data contains day names.
		return dayChars > (s.length()/2);
	}
	
	
	public boolean isTimeData(String s) {
		return isHourData(s) || isDayData(s);
	}
	
	public Course buildCourse(List<String> text) {
		/* assume each string in the list contains no more than one line */
		String title = null;
		String hours = ""; //TODO
		String date = ""; //TODO
		String status = "";
		StringBuilder general = new StringBuilder();
		for(String s: text) {
			s = s.trim();
			// detect status-changing events
			if(s.length() == 0) {
				// nothing
			} else if(title == null) { 
				title = s;
			} else if(isTimeData(s)) {
				status = TIME;		
				//TODO: feed "time" data factory
			} else if(s.contains(CITE_MARKER)) {
				//TODO
				
			} else if(s.startsWith(TARGET)) {
			} else {
				//TODO: process depending on state
				if(status == TIME) {
					/* After time data follows target information, but there is
					 * no specific status-triggering marker. 
					 */
					status = GENERAL_DATA;
				}
				if(status == GENERAL_DATA) {
					general.append(s);
				}
			}
		}
		return new Course(title, date, hours);
	}
}
