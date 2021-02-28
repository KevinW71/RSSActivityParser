package test.java;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import util.java.RSSFeedUtility;

public class TestRSSFeedUtility {

	RSSFeedUtility rssFeedUtility;
	
//	@Test
//	public void testAdd1Plus1() 
//	{
//	    int y = 1;
//	    RSSFeedUtility rssFeedUtility = new RSSFeedUtility();
//	    assertEquals(1, rssFeedUtility.returnInt1());
//	}

	@Test 
	// USE TESTFEED PATH ONLY for unit tests. Do not change this.
	public void testLoadRSSFeedsFromXML() throws Exception {
		try {
			RSSFeedUtility rssFeedUtility = new RSSFeedUtility();
			
			Map<String, List<String>> rssFeedMap = new HashMap<String, List<String>>();
			
			List<String> feed1List = new ArrayList<String>();
			feed1List.add("https://rss.art19.com/apology-line");
			rssFeedMap.put("The Apology Line", feed1List);
			
			List<String> feed2List = new ArrayList<String>();
			feed2List.add("https://feeds.simplecast.com/54nAGcIl");
			rssFeedMap.put("The Daily by The New York Times", feed2List);
			
			List<String> feed3List = new ArrayList<String>();
			feed3List.add("https://feeds.fireside.fm/bibleinayear/rss");
			rssFeedMap.put("The Bible in a Year", feed3List);
			
			assertEquals(15, rssFeedUtility.loadRSSFeedsFromXML("conf/unit tests/testFeeds.xml").getInactivityTime());
			assertEquals(rssFeedMap, rssFeedUtility.loadRSSFeedsFromXML("conf/unit tests/testFeeds.xml").getRssFeedMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testFilterFeedsByLastActive() throws Exception {
		try {
			RSSFeedUtility rssFeedUtility = new RSSFeedUtility();
			
			Map<String, List<String>> rssFeedMap = rssFeedUtility.loadRSSFeedsFromXML("conf/unit tests/testFeeds.xml").getRssFeedMap();
			List<String> inactiveFeedsZero = rssFeedUtility.filterFeedsByLastActive(rssFeedMap, 0);
			
			assertEquals(3, inactiveFeedsZero.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testfilterFeedsByLatestActivityInDateRange() {
		try {
			RSSFeedUtility rssFeedUtility = new RSSFeedUtility();
			Map<String, List<String>> rssFeedMap = rssFeedUtility.loadRSSFeedsFromXML("conf/unit tests/testFeeds.xml").getRssFeedMap();
			
			assertEquals(0, rssFeedUtility.filterFeedsByLatestActivityInDateRange(rssFeedMap, LocalDate.now(), null, false).size());
			assertEquals(3, rssFeedUtility.filterFeedsByLatestActivityInDateRange(rssFeedMap, LocalDate.now(), null, true).size());
			
			LocalDate localDateDistantPast = LocalDate.of(1970, 1, 1);
			assertEquals(0, rssFeedUtility.filterFeedsByLatestActivityInDateRange(rssFeedMap, null, localDateDistantPast, false).size());
			assertEquals(3, rssFeedUtility.filterFeedsByLatestActivityInDateRange(rssFeedMap, null, localDateDistantPast, true).size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
