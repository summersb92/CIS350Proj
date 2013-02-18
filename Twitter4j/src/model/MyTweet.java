package model;
import java.math.BigInteger;
import java.util.Date;


/**
 * MyTweet creates the basic interface for a users status.
 * 
 * @author Ben
 *
 */
public interface MyTweet {
	/**
	 * Gets the user id.
	 * @return id - the user id
	 */
	BigInteger getId();
	
	/**
	 * Gets the date.
	 * @return date - date of the tweet
	 */
	Date getCreatedAt();
	
	/**
	 * Gets the login name.
	 * @return loginName - username of tweeter
	 */
	String getLoginName();
	
	/**
	 * Gets the display name.
	 * @return displayName - actual name of tweeter
	 */
	String getDisplayName();
	
	/**
	 * Gets the status text.
	 * @return text - the text of a tweet
	 */
	String getText();
	
	/**
	 * Gets the friends count.
	 * @return friendsCount - the number of friends
	 */
	int getFriendsCount();
	
	/**
	 * Gets the followers count.
	 * @return followersCount - the number of followers
	 */
	int getFollowersCount();
}
