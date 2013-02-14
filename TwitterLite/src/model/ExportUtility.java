package model;

import java.util.ArrayList;

/**
 * ExportUtility takes the arrayList MyTweet and creates
 * and XML file from the data
 * 
 * @author Ben
 */
public class ExportUtility implements IExportUtility{

	private ArrayList<MyTweet> myTweets;
	
	/**
	 * ExportUtility instantiates MyTweet
	 */
	public ExportUtility(){
		myTweets = new ArrayList<MyTweet>();
	}
	/**
	 * sends the strings into the XML builder
	 * 
	 * @return XMLcode()
	 */
	public String toXML() {
		return XMLcode();
	}
	@Override
	/**
	 * Constructs the text for an XML file from myTweets
	 * 
	 * @return sb.toString()
	 */
	public String XMLcode() {
		StringBuilder sb = new StringBuilder();

		sb.append("<?xml version=\"1.0\"?>\n");
		sb.append("<statuses>\n");
		for (int i = 0; i < myTweets.size(); i++) {
			sb.append("<status>\n");
			sb.append("<id>" + myTweets.get(i).getId() +
			"</id>\n");
			sb.append("<createdAt>" +
					myTweets.get(i).getCreatedAt() +
			"</createdAt>\n");
			sb.append("<loginName>" +
					myTweets.get(i).getLoginName() +
			"</loginName>\n");
			sb.append("<displayName>" +
					myTweets.get(i).getDisplayName() +
			"</displayName>\n");
			sb.append("<followersCount>" +
					myTweets.get(i).getFollowersCount() +
			"</followersCount>\n");
			sb.append("<freindsCount>" +
					myTweets.get(i).getFriendsCount() +
			"</freindsCount>\n");
			sb.append("</status>\n");
		}
		sb.append("</statuses>\n");
		return sb.toString();
	}
}
