package org.sj.punidos.crminer.cepi;

import java.io.File;
import java.io.IOException;

public class WebResource {

	
	File file;
	String url;
	
	public WebResource(String _URL) {
		url = _URL;

	}

	
	
	/**
	 * 
	 * @return path to file
	 */
	public String download(File dstDir) {
		String pdfName = getURLFilename(url);
		file = new File(dstDir, pdfName);
		String pdfPath = file.getPath();
		if(file.exists()) {
			System.out.println("Present: " +pdfPath);
		} else {
			System.out.println("Downloading...");
			pdfPath = downloadCourses(dstDir.getAbsolutePath());
		}
		return pdfPath;

	}
	
	public String getURL() {
		return url;
	}
	
	public File getFile() {
		return file;
	}
	
	
	public String getFilename() {
		return file.getName();
	}
	
	/**
	 * 
	 * @param dstDir directory to store file into. 
	 * @return path to saved file.
	 */
	public String downloadCourses(String dstDir) {
	    try {
	    	return HttpDownloadUtility.downloadFile(url, dstDir);
	    } catch (IOException ex) {
	    	ex.printStackTrace();
	    }
		return null;
	}

	

	public static String getURLFilename(String url) {
		int j = url.lastIndexOf("/");
		if(j == -1) return url;
		return url.substring(j+1);
	}

}
