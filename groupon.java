import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class groupon {
@SuppressWarnings({ "unchecked", "resource" })
public static void main(String args[])throws Exception
{
	final WebClient webClient = new WebClient();
	webClient.setThrowExceptionOnFailingStatusCode(false);
    webClient.setThrowExceptionOnScriptError(false);
    //navigating to all deals page
	final HtmlPage page = webClient.getPage("http://www.groupon.com/chicago/all");
	//declaring a file writer buffer for storing output
	BufferedWriter buf = new BufferedWriter(new FileWriter("deals.html"));
	//getting page elements with div tag with class=info
	List<HtmlDivision> result1 = (List<HtmlDivision>) page.getByXPath("//div[@class='info']");
	Iterator<HtmlDivision> iterator1 = result1.iterator();
	//creating table for output file
	buf.append("<table border=1 align=center><tr><th>Deal Name</th><th>Deal Description</th><th>Location</th></tr>");
	//Iterating through the <div class=info> tag
	while(iterator1.hasNext())
	{		
		HtmlDivision a = iterator1.next();
		buf.append("<tr>");
		
		//Getting elements with class=title inside the <div class=info> tag
		List<HtmlDivision> b = (List<HtmlDivision>) a.getByXPath("div[@class='title']");
		Iterator<HtmlDivision> itr1 = b.iterator();
		String title[] = itr1.next().asXml().split(">");
		String finalTitle[] = title[2].split("</");
		buf.append("<td>"+finalTitle[0]+"</td>");
		
		//Getting elements with class=descriptor inside the <div class=info> tag
		List<HtmlDivision> d = (List<HtmlDivision>) a.getByXPath("div[@class='descriptor']");
		Iterator<HtmlDivision> itr2 = d.iterator();
		if(itr2.hasNext())
		{
			String descriptor[] = itr2.next().asXml().split(">");
			String finalDescriptor[] = descriptor[2].split("</");
			buf.append("<td>"+finalDescriptor[0]+"</td>");
			
		}
		//if descriptor tag is not present (for first deal the tag is of class, description and not descriptor
		else
		{
			//Getting elements with class=description inside the <div class=info> tag
			d = (List<HtmlDivision>) a.getByXPath("div[@class='description']");
			itr2 = d.iterator();
			String descriptor[] = itr2.next().asXml().split(">");
			String finalDescriptor[] = descriptor[2].split("</");
			buf.append("<td>"+finalDescriptor[0]+"</td>");
			
		}
		
		//Getting elements with class=subtitle inside the <div class=info> tag
		List<HtmlDivision> c = (List<HtmlDivision>) a.getByXPath("div[@class='subtitle']");
		Iterator<HtmlDivision> itr4 = c.iterator();
		String subTitle[] = itr4.next().asXml().split(">");
		String finalSub[] = subTitle[2].split("</");
		buf.append("<td>"+finalSub[0]+"</td>");
		
		
		buf.append("</tr>");

		
	}
	buf.append("</table>");
	buf.flush();

	webClient.closeAllWindows();
	
}
}