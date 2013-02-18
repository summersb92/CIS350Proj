package model;

import java.math.BigInteger;
import java.util.Date;

/**
 * Creates the Arraylist Parmaters called Tweet and this
 * class impliments the MyTweet interface.
 * 
 * @author Ben
 */
public class Tweet implements MyTweet{

	private BigInteger id;
	private Date date;
	private String loginName;
	private String displayName;
	private String text;
	private int friendsCount;
	private int followersCount;


	/**
	 * The Tweet constructor
	 * 
	 * @param date
	 * @param loginName
	 * @param displayName
	 * @param friendsCount
	 * @param followersCount
	 * @param text
	 */
	public Tweet(Date date, String loginName,
			String displayName, int friendsCount,
			int followersCount, String text) {
		this.date = date;
		this.loginName = loginName;
		this.displayName = displayName;
		this.friendsCount = friendsCount;
		this.followersCount = followersCount;
		this.text = text;
	}

	
	/**
	 * gets the user id
	 * @return id
	 */
	public BigInteger getId() {
		return id;
	}
	
	/**
	 * gets the date
	 * @return date
	 */
	public Date getCreatedAt() {
		return date;
	}
	
	/**
	 * gets the login name
	 * @return loginName
	 */
	public String getLoginName() {
		return loginName;
	}
	
	/**
	 * gets the display name
	 * @return displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * gets the status text
	 * @return text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * gets the friends count
	 * @return freindsCount
	 */
	public int getFriendsCount() {
		return friendsCount;
	}
	
	/**
	 * gets the followers count
	 * @return followersCount
	 */
	public int getFollowersCount() {
		return followersCount;
	}
}