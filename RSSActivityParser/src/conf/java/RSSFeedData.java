package conf.java;

import java.util.ArrayList;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "RSSFeedData")
public class RSSFeedData {

	private int inactivityTime;
	private ArrayList<RSSFeed> rssFeeds;
	
	@XmlElement(name = "InactivityTime")
	public int getInactivityTime() {
		return inactivityTime;
	}

	public void setInactivityTime(int inactivityTime) {
		this.inactivityTime = inactivityTime;
	}

	@XmlElementWrapper(name = "RSSFeeds")
	@XmlElement(name = "RSSFeed")
	public ArrayList<RSSFeed> getRssFeeds() {
		return rssFeeds;
	}

	public void setRssFeeds(ArrayList<RSSFeed> rssFeeds) {
		this.rssFeeds = rssFeeds;
	}
	
}
