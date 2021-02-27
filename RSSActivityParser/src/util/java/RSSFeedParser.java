// Utility methods here

package util.java;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;


public class RSSFeedParser {

	// Hardcode this??
	public static String dateStringFormat = "EEE, dd MMM yyyy HH:mm:ss Z";
	
	public LocalDate getLatestUpdateDate(String rssFeedURL) throws Exception {
		
		LocalDate rssFeedURLMaxLocalDate;
		
		InputStream rssFeedInputStream = connectRSSFeed(rssFeedURL);
		
		XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(rssFeedInputStream);
		rssFeedURLMaxLocalDate = getMaxReturnDateFromEventReader(eventReader);
		
		disconnectRSSFeed(rssFeedInputStream);
		
		return rssFeedURLMaxLocalDate;
		
	}

	// narrowly tailored, private.
	private LocalDate getMaxReturnDateFromEventReader(XMLEventReader eventReader) throws Exception {
		LocalDate returnDate = null;
		
		if(eventReader == null) {
			return returnDate;
		}
		
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			if (event.isStartElement()) {
				String feedItem = event.asStartElement().getName().getLocalPart();
				if (feedItem.toLowerCase().indexOf("date") > -1) {
					XMLEvent dateEvent = eventReader.nextEvent();
					if (dateEvent instanceof Characters) {
						String dateString = dateEvent.asCharacters().getData();
						// Finds max return date within RSSFeed
						LocalDate tempLocalDate = parseDate(dateString);
						if (returnDate == null || tempLocalDate.isAfter(returnDate)) {
							returnDate = tempLocalDate;
						}
					}
				}
			}
		}
		return returnDate;
	}
	
	private LocalDate parseDate(String dateString) throws Exception {
		
		LocalDate localDate = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat(dateStringFormat);
		localDate = convertDateToLocalDate(sdf.parse(dateString));
				
		return localDate;
	}
	
	public LocalDate convertDateToLocalDate(Date date) {
	    return Instant.ofEpochMilli(date.getTime())
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
	
	public InputStream connectRSSFeed(String rssFeedURL) throws Exception{
		InputStream inputStream = null;
		
		URL url = new URL(rssFeedURL);
		inputStream = url.openStream();
		
		return inputStream;
		
	}

	public void disconnectRSSFeed(InputStream rssFeedInputStream) throws Exception {
		rssFeedInputStream.close();
	}


}
