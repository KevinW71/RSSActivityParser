package test.java;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Date;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;

import org.junit.jupiter.api.Test;

import util.java.RSSFeedParser;


public class TestRSSFeedParser {
	
	@Test
	public void testGetLatestUpdateDate() {
		try {
			RSSFeedParser rssFeedParser = new RSSFeedParser();
			LocalDate guaranteedBeforeDate = LocalDate.of(2021, 2, 27);
			assertEquals(true, rssFeedParser.getLatestUpdateDate("feeds.simplecast.com/54nAGcIl").isAfter(guaranteedBeforeDate));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testGetMaxReturnDateFromEventReader() {
		try {
			RSSFeedParser rssFeedParser = new RSSFeedParser();
			
			File file = new File("conf/unit tests/nyt.txt");
		    InputStream rssFeedInputStream = new FileInputStream(file);
			XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(rssFeedInputStream);
			LocalDate localDateNYTLast = LocalDate.of(2021, 2, 28);
			
			assertEquals(localDateNYTLast, rssFeedParser.getMaxReturnDateFromEventReader(eventReader));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testParseDate() {
		try {
			RSSFeedParser rssFeedParser = new RSSFeedParser();
			
			LocalDate localDate090221 = LocalDate.of(2021, 2, 9);
			assertEquals(localDate090221, rssFeedParser.parseDate("Tue, 9 Feb 2021 10:45:00 +0000"));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	@Test // Not hard to conduct unit test on this.
	public void testConvertDateToLocalDate() {
		try {
			RSSFeedParser rssFeedParser = new RSSFeedParser();	
			
			@SuppressWarnings("deprecation")
			Date date = new Date(0, 0, 0);
			LocalDate localDate = LocalDate.of(1899, 12, 31);
			
			assertEquals(LocalDate.class, rssFeedParser.convertDateToLocalDate(date).getClass());
			assertEquals(localDate, rssFeedParser.convertDateToLocalDate(date));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
