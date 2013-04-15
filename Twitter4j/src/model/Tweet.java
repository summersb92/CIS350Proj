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
	/**  */
	private BigInteger id;
	/** Stores the tweet date.*/
	private Date date;
	/** Stores the username.*/
	private String loginName;
	/** Stores a user's actual name. */
	private String displayName;
	/** Stores the text of a Status. */
	private String text;
	/** Stores the number of friends. */
	private int friendsCount;
	/** Stores the number of followers. */
	private int followersCount;
	/**
	 * favorite tweet flag.
	 */
	private boolean favorite;
	/**
	 * The Tweet constructor.
	 * 
	 * @param dte - stores the tweet date.
	 * @param lginName - stores the username
	 * @param dsplayName - stores the actual name
	 * @param frendsCount - stores amount of friends
	 * @param fllowersCount - stores amount of followers
	 * @param txt - stores the text of the tweet
	 * @param fave - flag for favorite tweet
	 */
	public Tweet(final Date dte, final String lginName,
			final String dsplayName, final int frendsCount,
			final int fllowersCount, final String txt,
			final boolean fave) {
		this.date = dte;
		this.loginName = lginName;
		this.displayName = dsplayName;
		this.friendsCount = frendsCount;
		this.followersCount = fllowersCount;
		this.text = txt;
		this.favorite = fave;
	}

	
	/**
	 * Gets the user id.
	 * @return id - the user id
	 */
	public final  BigInteger getId() {
		return id;
	}
	
	/**
	 * Gets the date.
	 * @return date - date of the tweet
	 */
	public final Date getCreatedAt() {
		return date;
	}
	
	/**
	 * Gets the login name.
	 * @return loginName - username of tweeter
	 */
	public final String getLoginName() {
		return loginName;
	}
	
	/**
	 * Gets the display name.
	 * @return displayName - actual name of tweeter
	 */
	public final String getDisplayName() {
		return displayName;
	}
	
	/**
	 * Gets the status text.
	 * @return text - the text of a tweet
	 */
	public final String getText() {
		return text;
	}
	
	/**
	 * Gets the friends count.
	 * @return friendsCount - the number of friends
	 */
	public final int getFriendsCount() {
		return friendsCount;
	}
	
	/**
	 * Gets the followers count.
	 * @return followersCount - the number of followers
	 */
	public final int getFollowersCount() {
		return followersCount;
	}
	@Override
	public final boolean isFavorite() {
		return favorite;
	}
}