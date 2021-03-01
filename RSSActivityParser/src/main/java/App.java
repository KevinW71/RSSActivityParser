// Runs the Application. Top level method calls and runtime variable processing
// Zhanran Wen Feb 2021

package main.java;

import java.util.List;

import conf.java.RSSFeedDataProcessed;
import util.java.RSSFeedUtility;

public class App {

	public static void main(String arg[]) {
		
		try {
			
			RSSFeedDataProcessed config = new RSSFeedDataProcessed();
			
			RSSFeedUtility rssFeedUtility = new RSSFeedUtility();
			config = rssFeedUtility.loadRSSFeedsFromXML("conf/rssFeeds.xml");
			
			RSSFeedUtility rssFeedParser = new RSSFeedUtility();
			List<String> feedList = rssFeedParser.filterFeedsByLastActive(config.getRssFeedMap(), config.getInactivityTime());
			
			System.out.println("List of Inactive feeds as of " + config.getInactivityTime() + " days.");
			
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
