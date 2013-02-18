package engine;

/**
 * @author Ben
 *
 * Creates the Interface for the Twitter Engine class
 */
public interface ITweet {
	/**
	 * Gets the date.
	 * @return date - date of the tweet
	 */
	java.util.Date getCreatedAt();
	
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
