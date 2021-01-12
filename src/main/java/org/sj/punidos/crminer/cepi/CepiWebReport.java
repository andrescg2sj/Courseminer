package org.sj.punidos.crminer.cepi;

import java.io.File;

public class CepiWebReport extends CepiWeb {
	
	String month;

	public CepiWebReport(CepiWeb cepi) {
		super(cepi);
		month = DateManager.getMonth(cepi.link);
	}
	
	public String getHtmlRow(CepiWeb cepi) {
		StringBuilder data = new StringBuilder();
		data.append("<tr>");
		
		data.append("<td>");
		data.append(String.format("<a href=\"%s\">%s</a>",link,name));
		data.append("</td>");

		data.append("<td>");
		data.append(String.format("<a href=\"%s\">%s</a>",pdf.getURL(),pdf.getFilename()));
		data.append("</td>");

		data.append("<td>");
		//TODO: Create class Resources to manage this.
		File base = new File(CepiList.BASE_DIR);
		String dstFullpath = dstHtml.getAbsolutePath();
		String relPath = dstFullpath.substring(base.getAbsolutePath().length()+1);
		data.append(String.format("<a href=\"./%s\">%s</a>",relPath,dstHtml.getName()));
		data.append("</td>");

		data.append("</tr>");
		return data.toString();
	}

}
