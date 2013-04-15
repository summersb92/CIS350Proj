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
	 * Get tweet id.
	 * @return tweet id
	 */
	BigInteger getId();
	/**
	 * Get time tweet was created.
	 * @return creation time
	 */
	Date getCreatedAt();
	/**
	 * Get user name of tweeter.
	 * @return user name
	 */
	String getLoginName();
	/**
	 * Get display name of tweeter.
	 * @return display name
	 */
	String getDisplayName();
	/**
	 * Get the text of the tweet.
	 * @return tweet text
	 */
	String getText();
	/**
	 * Get friends count of tweeter.
	 * @return number of friends
	 */
	int getFriendsCount();
	/**
	 * Get followers count of tweeter.
	 * @return number of followers
	 */
	int getFollowersCount();
	/**
	 * Flag whether or not tweet is favorite.
	 * @return favorite flag
	 */
	boolean isFavorite();
}
