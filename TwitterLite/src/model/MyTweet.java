package model;
import java.math.BigInteger;
import java.util.Date;


/**
 * MyTweet creates the basic interface for a users status
 * 
 * @author Ben
 *
 */
public interface MyTweet {
	BigInteger getId();
	
	Date getCreatedAt();
	
	String getLoginName();
	
	String getDisplayName();
	
	String getText();
	
	int getFriendsCount();
	
	int getFollowersCount();
}
