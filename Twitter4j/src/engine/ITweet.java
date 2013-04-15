package engine;

/**
 * @author Ben
 *
 * Creates the Interface for the Twitter Engine class
 */
public interface ITweet {
	/**
	 * Returns created Tweet Date.
	 * @return tweet creation time
	 */
	java.util.Date getCreatedAt();
	/**
	 * Returns login name.
	 * @return login name
	 */
	String getLoginName();
	/**
	 * Returns display name.
	 * @return display name
	 */
	String getDisplayName();
	/**
	 * Returns tweet text.
	 * @return tweet text
	 */
	String getText();
	/**
	 * Returns number of friends of tweeter.
	 * @return number of friends
	 */
	int getFriendsCount();
	/**
	 * Returns number of followers of tweeter.
	 * @return number of followers
	 */
	int getFollowersCount();
	/**
	 * Returns favorite tweet.
	 * @return favorite tweet
	 */
	String getFavorite();
}
