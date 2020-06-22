package org.sj.punidos.crminer.cepi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CepiList {
	
	List<CepiWeb> cepis = new LinkedList<CepiWeb>();
	
	public CepiList(String listfile) throws IOException {
		load(listfile);
	}
	
	void load(String path) throws IOException {
		File f = new File(path);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		
		while(br.ready()) {
			String line = br.readLine();
			line = line.trim();
			if(line.length() > 0) {
				CepiWeb cepi = new CepiWeb(line);
				cepis.add(cepi);
			}
		}
		br.close();
	}

}
