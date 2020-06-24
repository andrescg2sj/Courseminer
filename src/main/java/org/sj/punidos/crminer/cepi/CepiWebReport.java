package org.sj.punidos.crminer.cepi;

public class CepiWebReport extends CepiWeb {
	
	String month;

	public CepiWebReport(CepiWeb cepi) {
		super(cepi);
		month = Month.getMonth(cepi.link);
	}
	
	public String getHtmlRow(CepiWeb cepi) {
		StringBuilder data = new StringBuilder();
		data.append("<tr>");
		
		data.append("<td>");
		data.append(String.format("<a href=\"%s\">%s</a>",link,name));
		data.append("</td>");

		data.append("<td>");
		data.append(String.format("<a href=\"%s\">%s</a>",pdfURL,pdfFile.getName()));
		data.append("</td>");

		data.append("<td>");
		data.append(String.format("<a href=\"file://%s\">%s</a>",dstPath.getAbsoluteFile(),dstPath.getName()));
		data.append("</td>");

		data.append("</tr>");
		return data.toString();
	}

}
