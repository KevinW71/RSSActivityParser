// Top level method definitions

package util.java;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import conf.java.RSSFeedData;
import conf.java.RSSFeedDataProcessed;

public class RSSFeedUtility {

	
	public RSSFeedDataProcessed loadRSSFeedsFromXML() throws Exception {
		RSSFeedDataProcessed rssFeedDataProcessed = new RSSFeedDataProcessed();;
		
		// Discontinued properties upload
//		Properties props = new Properties();
//		props.load(null)getClass().getClassLoader().getResourceAsStream("");
		
		
		RSSFeedData rssFeedDataRaw = new RSSFeedData();
		
		//RSS feeds stored in XML file.
		JAXBContext jaxbContext = JAXBContext.newInstance(RSSFeedData.class);
		Unmarshaller jaxbUnmarshaller  = jaxbContext.createUnmarshaller();
		rssFeedDataRaw = (RSSFeedData) jaxbUnmarshaller.unmarshal(new File("conf/testFeeds.xml"));
		
		// Using a processed Conf Object so we can manipulate it as we want. Right now it's just a 1-1 translation, but in the future for example change inactivityTime to "3m2w1d" and translate it to 95 days.
		if (rssFeedDataRaw != null && rssFeedDataRaw.getRssFeeds() != null) {
			
			rssFeedDataProcessed.setInactivityTime(rssFeedDataRaw.getInactivityTime());
			Map<String, List<String>> rssFeedMap = new HashMap<String, List<String>>();
			
			System.out.println("Feeds to Analyze:");
			
			for (int n = 0; n < rssFeedDataRaw.getRssFeeds().size(); n++) {
				rssFeedMap.put(rssFeedDataRaw.getRssFeeds().get(n).getRssFeedName(), rssFeedDataRaw.getRssFeeds().get(n).getRssFeedURLs());
				System.out.println(rssFeedDataRaw.getRssFeeds().get(n).getRssFeedName() + ": " + rssFeedDataRaw.getRssFeeds().get(n).getRssFeedURLs());
				
			}
			rssFeedDataProcessed.setRssFeedMap(rssFeedMap);
		}
		
		return rssFeedDataProcessed;
	}
	
	
	// Wrapper Function, for last active
	public List<String> filterFeedsByLastActive(Map<String, List<String>> rssFeedMap, int daysInactive) throws Exception {
		List<String> filteredFeeds = new LinkedList<String>();
		
		LocalDate currentLocalDate = LocalDate.now();
		LocalDate lowerBound = currentLocalDate.minusDays(daysInactive);
		
		// get lower bound by subtracting days inactive from current date. No upper bound needs to be specified. filterMissing is set to true, since we're looking for days that don't fall into the filter
		// setting upperBound to our current date minus daysInactive specified and lowerBound to null (open) would not work, since that assumes rss feeds have had activity before then. The rss feed can be empty and still qualify as not having been "active" in the past X days, for example.
		filteredFeeds = filterFeedsByLatestActivityInDateRange(rssFeedMap, lowerBound, null, true);
		
		return filteredFeeds;
	}
	
	// Central Hub Function, filters feeds based on activity status within a specific date range-- This is a more general function, and the wrapper function can call this one for the more narrow purpose defined in the assignment
	public List<String> filterFeedsByLatestActivityInDateRange(Map<String, List<String>> rssFeedMap, LocalDate lowerBound, LocalDate upperBound, boolean filterMissing) throws Exception {
		
		System.out.println("Retrieving " + (filterMissing ? "entries not" : "entries") + " between " + (lowerBound != null ? lowerBound : " the beginning of time") + " and " + (upperBound != null ? upperBound : "today"));
		
		List<String> filteredFeedNames = new LinkedList<String>();
		
		LocalDate currentLocalDate = LocalDate.now();
		// Problems with parameters, i.e. no feeds specified feeds, lower bound is after upper bound, lower bound is after current date etc.
		if (rssFeedMap == null || !(rssFeedMap.size() > 0) || (lowerBound != null && lowerBound.isAfter(currentLocalDate)) || (upperBound != null && upperBound.isBefore(lowerBound))) {
			return null;
		}
		
		for (Map.Entry<String, List<String>> rssFeedEntry: rssFeedMap.entrySet()) {
			
			boolean withinLocalDateRange = false;
			
			for(String rssFeedURL : rssFeedEntry.getValue()) {
				LocalDate latestRSSFeedDate = getLatestUpdateDate(rssFeedURL);
				// RSS feed date is within date range boundaries. If lower or upper limit is not specified, then we assume that end is open, i.e. no lower bound means mythical 2000 year old rss feeds would be matched
				if(latestRSSFeedDate != null && ((lowerBound == null || latestRSSFeedDate.isAfter(lowerBound)) && (upperBound == null || latestRSSFeedDate.isBefore(upperBound)))) {
					withinLocalDateRange = true;
					break;
				}
			}
			
			// FilterMissing flag determines whether we return the list of matching (false) or missing (true) feeds
			
			String filteredFeedName = rssFeedEntry.getKey(); // + includeLatestDateInfo ? lastRSSFeedDate : ""
			
			if (filterMissing && !withinLocalDateRange) {
				filteredFeedNames.add(filteredFeedName);
			} else if (!filterMissing && withinLocalDateRange) {
				filteredFeedNames.add(filteredFeedName);
			}
			
		}
		
		return filteredFeedNames;
	}
	
	private LocalDate getLatestUpdateDate(String rssFeedURL) throws Exception {
		RSSFeedParser rssFeedParser = new RSSFeedParser();
		return rssFeedParser.getLatestUpdateDate(rssFeedURL);
	}

}
