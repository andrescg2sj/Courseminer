package org.sj.punidos.crminer;

import java.io.File;
import java.nio.file.Paths;
import org.sj.tools.graphics.tablemkr.util.PDFTableExporter;

import java.nio.file.Path;

public class ExampleGenerator {
	
	String dstPath = "examples/html/";
	
	public static String changeExtension(String path, String newExt) {
		String[] tokens = path.split("\\.(?=[^\\.]+$)");
		if(!newExt.startsWith("."))
			newExt = "." + newExt;
		return tokens[0]+ newExt;
	}
	
	public void processAll(String myDirectoryPath) {
		File dir = new File(myDirectoryPath);
		  File[] directoryListing = dir.listFiles();
		  if (directoryListing != null) {
		    for (File child : directoryListing) {
		      // Do something with child
		    	if(child.getName().endsWith(".pdf")) {
		    		String newFilename = changeExtension(child.getName(), "html");
		    		Path currentPath = (Path) Paths.get(System.getProperty("user.dir"));
		    		Path filePath = (Path) Paths.get(currentPath.toString(), dstPath, newFilename);
		    		//System.out.print(child.getAbsolutePath() + " will be: ");
		    		PDFTableExporter proc = new PDFTableExporter();
		    		System.out.println("Writing " + filePath.toString());
		    		proc.setDestination(filePath.toString());
		    		proc.run(child.getAbsolutePath());
		    		
		    		//filterNotes(child);
		    		
		    		// TODO
		    		//convert()
		    	}
		    	
		    }
		  } else {
		    // Handle the case where dir is not really a directory.
		    // Checking dir.isDirectory() above would not be sufficient
		    // to avoid race conditions with another process that deletes
		    // directories.
		  }
	}

	
	public static void main(String args[]) {
		ExampleGenerator eg = new ExampleGenerator();
		eg.processAll("examples/pdf/");
		
	}

}
