package conf.java;

import java.util.ArrayList;
import javax.xml.bind.annotation.*;

public class RSSFeed {
	
	private String rssFeedName;
	private ArrayList<String> rssFeedURLs;
	
	@XmlElement(name = "RSSFeedName")
	public String getRssFeedName() {
		return rssFeedName;
	}
	
	public void setRssFeedName(String rssFeedName) {
		this.rssFeedName = rssFeedName;
	}
	
	@XmlElementWrapper(name = "RSSFeedURLs")
	@XmlElement(name = "RSSFeedURL")
	public ArrayList<String> getRssFeedURLs() {
		return rssFeedURLs;
	}
	
	public void setRssFeedURLs(ArrayList<String> rssFeedURLs) {
		this.rssFeedURLs = rssFeedURLs;
	}
	
}
