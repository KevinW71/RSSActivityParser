package conf.java;

import java.util.List;
import java.util.Map;

public class RSSFeedDataProcessed {

	private int inactivityTime;
	private Map<String, List<String>> rssFeedMap;
	
	public int getInactivityTime() {
		return inactivityTime;
	}
	public void setInactivityTime(int inactivityTime) {
		this.inactivityTime = inactivityTime;
	}
	public Map<String, List<String>> getRssFeedMap() {
		return rssFeedMap;
	}
	public void setRssFeedMap(Map<String, List<String>> rssFeedMap) {
		this.rssFeedMap = rssFeedMap;
	}
	
}
