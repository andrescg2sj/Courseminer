package org.sj.punidos.crminer.cepi;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CepiWeb {
	
	String link;
	
	public CepiWeb(String link) {
		this.link = link;
	}
	
	public String findCourses() {
		String selected = null;
			
		Document doc;
		try {
			doc = Jsoup.connect(link).get();
			Elements links = doc.select("a[href~=.\\.pdf]");
			if(links.size() > 0) {
				selected = links.get(0).attr("href");
			}
			
			for (Element link : links) {  
			    System.out.println("\nlink : " + link.attr("href"));  
			    System.out.println("text : " + link.text());  
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return selected;
	}
	
	public static void main(String args[]) {
		System.out.println("testing urls");
		CepiWeb cepi = new CepiWeb("https://www.comunidad.madrid/centros/cepi-madrid-arganzuela");
		String pdf = cepi.findCourses();
		System.out.println("course: "+ pdf);
	}

}
