package model;

import java.math.BigInteger;
import java.util.Date;

/**
 * Creates the Arraylist Parmaters called Tweet and this
 * class impliments the MyTweet interface.
 * 
 * @author Ben
 */
public class Tweet implements MyTweet {

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

	@Override
	/**
	 * gets the user id
	 * @return id
	 */
	public BigInteger getId() {
		return id;
	}
	@Override
	/**
	 * gets the date
	 * @return date
	 */
	public Date getCreatedAt() {
		return date;
	}
	@Override
	/**
	 * gets the login name
	 * @return loginName
	 */
	public String getLoginName() {
		return loginName;
	}
	@Override
	/**
	 * gets the display name
	 * @return displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	@Override
	/**
	 * gets the status text
	 * @return text
	 */
	public String getText() {
		return text;
	}
	@Override
	/**
	 * gets the friends count
	 * @return freindsCount
	 */
	public int getFriendsCount() {
		return friendsCount;
	}
	@Override
	/**
	 * gets the followers count
	 * @return followersCount
	 */
	public int getFollowersCount() {
		return followersCount;
	}
}