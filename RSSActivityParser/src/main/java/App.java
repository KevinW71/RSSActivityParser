// Runs the Application. Top level method calls and runtime variable processing
// Zhanran Wen Feb 2021

package main.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.java.RSSFeedUtility;

public class App {

	public static void main(String arg[]) {
		
		try {
			
			// Test Variables
			int daysSinceActive = 1;
			
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
			
			// End Test Variables
			
			RSSFeedUtility rssFeedParser = new RSSFeedUtility();
			List<String> feedList = rssFeedParser.filterFeedsByLastActive(rssFeedMap, daysSinceActive);
			
			System.out.println("List of Inactive feeds as of " + daysSinceActive + " days.");
			
			if (feedList.size() > 0) {
				for (int n = 0; n < feedList.size(); n++) {
					System.out.println(feedList.get(n));
				}
			} else {
				System.out.println("None");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
		}
	}
	
}
