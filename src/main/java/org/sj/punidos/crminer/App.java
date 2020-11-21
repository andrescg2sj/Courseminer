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
 

package org.sj.punidos.crminer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.sj.punidos.crminer.cepi.CepiCourseList;
import org.sj.punidos.crminer.cepi.CepiWeb;
import org.sj.punidos.crminer.courses.Course;
import org.sj.punidos.crminer.courses.CourseCsvWriter;
import org.sj.punidos.crminer.courses.CourseXlsxWriter;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		Options options = new Options();
		
		Option optOutput = new Option("o", "output_filename", true, "output file");
        optOutput.setRequired(true);
        options.addOption(optOutput);

		Option optFormat = new Option("f", "output_format", true, "output format");
        optFormat.setRequired(false);
        options.addOption(optFormat);
        
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

		try {
			cmd = parser.parse(options, args);
	        String filename_out = cmd.getOptionValue("o");
	        String filenames[] = cmd.getArgs(); 
	        
	        if(filenames.length == 0) {
	        	System.err.println("Expected: list of PDF filenames.");
	        	
	        } else {
	        	List<Course> courses = new LinkedList<Course>();
	        	for(String filename : filenames) {
	        		File file = new File(filename);
	        		CepiCourseList clist = new CepiCourseList("default", file);
	        		courses.addAll(clist.getCourses());
	        	}
	        	//TODO: csv
	    		CourseXlsxWriter cw = new CourseXlsxWriter(filename_out);
	    		cw.write(courses);
	    		cw.writeAndClose();

	        }
	        
	        
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        

        
    }
}
